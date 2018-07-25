package com.umpay.proxyservice.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.util.GenerateUtil;
import com.umpay.proxyservice.handler.ProcessExecuteHandler.ProcessType;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.LogArgsUtil;
import com.umpay.proxyservice.util.MD5Utils;

/**
 * 信息校验操作者
 * 
 * @author xuxiaojia
 */
public class CheckInfoHandler {
	private final static Logger log = LoggerFactory.getLogger("CheckInfoHandler");
	/* 实时信息获取对象 */
	private RuleTimeService rts = RuleTimeService.getRts();
	private final static List<String> respSignList = new ArrayList<String>();

	static {
		respSignList.add(HttpMap.FUNCODE);
		respSignList.add(HttpMap.RETCODE);
		respSignList.add(HttpMap.DATETIME);
		Collections.sort(respSignList);
	}

	/**
	 * 信息校验 如果当前请求类型是sftp回调交互的则全部校验，是本地文件跑批的则只校验必输项
	 * 
	 * @param reqMap
	 * @throws Exception
	 */
	public void checkQueryInfo(Map<String, String> reqMap) throws Exception {
		// funcode 和merid校验
		checkFuncodeAndMeridAndLicense(reqMap);
		if (ProcessType.sftpProcess.equals(rts.getConfigPo(reqMap).getProcessType())) {
			// 授权ip校验
			ipAuth(reqMap);
			// 必输项校验
			reqMustHaveCheck(reqMap);
			// 请求条数校验
			sizeCheck(reqMap);
			// 签名校验
			reqSignCheck(reqMap);
			// 文件名校验
			checkFile(reqMap);
		} else if (ProcessType.localProcess.equals(rts.getConfigPo(reqMap).getProcessType())) {
			// 必输项校验
			reqMustHaveCheck(reqMap);
		}
		// 输入项校验格式校验
		checkReqElemPattern(reqMap);
	}

	/**
	 * 判断各个输入项格式是否正确
	 * 
	 * @param reqMap
	 * @throws BaseException
	 */
	private void checkReqElemPattern(Map<String, String> reqMap) throws BaseException {
		for (Entry<String, String> entry : reqMap.entrySet()) {
			if (!RuleTimeService.getRts().getPatternCheckService().elemValueCheck(entry.getValue(), entry.getKey())) {
				throw new BaseException(Retcode.PARAMETER_CHECH_ERROR,
						entry.getKey() + "的值：" + entry.getValue() + " 格式不正确");
			}
		}
		// 银联智慧 cid评分的type在T0001到T0491之间，不包含T0040
		if (reqMap.containsKey(HttpMap.LABEL_TYPE)) {
			String labelTypeValue = reqMap.get(HttpMap.LABEL_TYPE);
			if (Pattern.compile("^(T0[0-4][0-9][0-9](;T0[0-4][0-9][0-9])*)$").matcher(labelTypeValue).matches()
					&& Pattern.compile("^((?!T049[2-9]).)*$").matcher(labelTypeValue).matches()
					&& Pattern.compile("^((?!T0040).)*$").matcher(labelTypeValue).matches()) {
				return;
			}
			throw new BaseException(Retcode.PARAMETER_CHECH_ERROR,
					HttpMap.LABEL_TYPE + "的值：" + reqMap.get(HttpMap.LABEL_TYPE) + " 格式不正确");
		}
	}

	private void ipAuth(Map<String, String> reqMap) throws BaseException {
		if (!rts.getConfigPo(reqMap).getEmpowerIps().contains(reqMap.get(HttpMap.REQ_IP))) {
			throw new BaseException(Retcode.IP_AUTH_ERROR,
					Retcode.IP_AUTH_ERROR.getName() + "访问ip为:" + reqMap.get(HttpMap.REQ_IP));
		}
	}

	/**
	 * 请求文件条数校验
	 * 
	 * @param reqMap
	 * @throws BaseException
	 */
	private void sizeCheck(Map<String, String> reqMap) throws BaseException {
		try {
			if (rts.getConfigPo(reqMap).getFileMinSize() > Integer.parseInt(reqMap.get(HttpMap.SIZE))) {
				throw new BaseException(Retcode.NO_UP_TO_MINSIZE);
			}
			if (rts.getConfigPo(reqMap).getFileMaxSize() < Integer.parseInt(reqMap.get(HttpMap.SIZE))) {
				throw new BaseException(Retcode.OUT_OF_MAXSIZE);
			}
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			throw new BaseException(Retcode.INNER_ERROR, "请求数量与限制最小数量比较失败", e);
		}
	}

	/**
	 * 校验funcode和merid
	 * 
	 * @param reqMap
	 * @throws Exception
	 */
	private void checkFuncodeAndMeridAndLicense(Map<String, String> reqMap) throws Exception {
		if (rts.getConfigPo(reqMap) == null) {
			throw new BaseException(Retcode.FUNCODE_ERROR,
					"funcode和merid和childmerid的对应关系错误 此次请求对应的funcode：" + reqMap.get(HttpMap.FUNCODE) + " merId："
							+ reqMap.get(HttpMap.MERID) + " childmerid：" + reqMap.get(HttpMap.CHILDMERID));
		}
		if (!rts.getConfigPo(reqMap).getLicense().equals(reqMap.get(HttpMap.LICENSE))) {
			throw new BaseException(Retcode.LICENSE_ERROR);
		}
	}

	/**
	 * 检验xml报文中的文件名字是否格式正确并返回该文件名 如果格式不正确则抛出异常
	 * 
	 * @param reqMap
	 * 
	 * @throws BaseException
	 */
	private void checkFile(Map<String, String> reqMap) throws BaseException {
		String trueFileName = GenerateUtil.generFileName(reqMap.get(HttpMap.FUNCODE), reqMap.get(HttpMap.MERID),
				reqMap.get(HttpMap.DATETIME));
		if (!trueFileName.equals(reqMap.get(HttpMap.FILE))) {
			throw new BaseException(Retcode.FILE_NAME_ERROR);
		}
	}

	/**
	 * 请求报文的sign签名验证
	 * 
	 * @param reqMap
	 * @throws BaseException
	 */
	private void reqSignCheck(Map<String, String> reqMap) throws BaseException {
		List<String> list = rts.getConfigPo(reqMap).getReqSignCheckList();
		signCheck(reqMap, list);
	}

	/**
	 * 生成待转化为MD5的sign字符串
	 * 
	 * @param map
	 *            包含各个字段值的map集合
	 * @param list
	 *            需要生成sign的字段集合
	 * @return
	 */
	private String getSignString(Map<String, String> map, List<String> list) {
		StringBuilder signBuilder = new StringBuilder();
		for (String signField : list) {
			signBuilder.append(signField + map.get(signField));
		}
		return signBuilder.toString();
	}

	/**
	 * 请求报文的必输项校验
	 * 
	 * @param reqMap
	 *            请求信息封装的对象
	 * @throws BaseException
	 */
	private void reqMustHaveCheck(Map<String, String> reqMap) throws BaseException {
		List<String> list = rts.getConfigPo(reqMap).getReqFieldMustHaveList();
		xmlMapMustCheck(reqMap, list);
	}

	/**
	 * xml报文必输项校验
	 * 
	 * @param mustField
	 *            必输项字段
	 * @throws BaseException
	 */
	private void xmlMapMustCheck(Map<String, String> xmlMap, List<String> list) throws BaseException {
		if (CollectionUtils.isNotEmpty(list)) {
			for (String mustField : list) {
				if (StringUtils.isEmpty(xmlMap.get(mustField))) {
					throw new BaseException(Retcode.PARAMETER_INCOMPLETE, mustField + "字段为空");
				}
			}
		}
	}

	/**
	 * 响应报文生成funcode对应的sign串并放入map中
	 * 
	 * @param map
	 * @throws BaseException
	 */
	public void respSignCreate(Map<String, String> map) {
		signGener(map, respSignList);
	}

	/**
	 * 生成sign签名放入报文中
	 * 
	 * @param map
	 * @param list
	 */
	public void signGener(Map<String, String> map, List<String> list) {
		String signString = getSignString(map, list);
		String sign = MD5Utils.getMD5Str(signString);
		log.info(LogArgsUtil.getLogArgus() + "生成的sign字符串：{}", signString);
		map.put(HttpMap.SIGN, sign);
	}

	/**
	 * 签名校验
	 * 
	 * @param respMap
	 * @param list
	 * @throws BaseException
	 */
	private void signCheck(Map<String, String> respMap, List<String> list) throws BaseException {
		String signString = getSignString(respMap, list);
		String sign = MD5Utils.getMD5Str(signString);
		if (!sign.equals(respMap.get(HttpMap.SIGN))) {
			throw new BaseException(Retcode.SIGN_ERROR, Retcode.SIGN_ERROR.getName() + signString);
		}
	}

	public static void main(String[] args) {
		System.out.println(
				Pattern.compile("^(T0[0-4][0-9][0-9](;T0[0-4][0-9][0-9])*)$").matcher("T0040,T0041").matches());
	}

}
