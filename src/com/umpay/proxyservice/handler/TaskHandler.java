package com.umpay.proxyservice.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.fileBatch.service.FileProcessTaskService;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.LogArgsUtil;
import com.umpay.proxyservice.util.TimeCountUtil;
import com.umpay.proxyservice.util.XmlUtils;

/**
 * 任务处理类
 * 
 * @author xuxiaojia
 * @date 2016年6月15日 上午10:14:47
 */
public class TaskHandler {
	private static final Logger log = LoggerFactory.getLogger(TaskHandler.class);
	private static final Logger merReq_and_res_xml = LoggerFactory.getLogger("merReq_and_res_xml");
	/* 信息检查者 */
	private CheckInfoHandler checkInfoHandler = new CheckInfoHandler();
	/* 文件处理任务服务 */
	@Autowired
	private FileProcessTaskService fileProcessTaskService;

	public void setCheckInfoHandler(CheckInfoHandler checkInfoHandler) {
		this.checkInfoHandler = checkInfoHandler;
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	public String execute(String reqXml, String ip) {
		log.info("*****接收到接口层侧请求报文:*****\n" + reqXml);
		/* 打印请求参数的日志 */
		Map<String, String> reqMap = getReqXmlMap(reqXml);
		reqMap.put(HttpMap.REQ_IP, ip);
		LogArgsUtil.createLogArgs(reqMap);
		// 初始化返回信息
		Map<String, String> respMap = new HashMap<String, String>();
		respMap.put(HttpMap.RETCODE, Retcode.SUCCESS.getCode());
		respMap.put(HttpMap.FUNCODE, reqMap.get(HttpMap.FUNCODE));
		respMap.put(HttpMap.FILE_TASK_ID, "");
		try {
			// 信息校验
			checkInfoHandler.checkQueryInfo(reqMap);
			// 校验成功加入异步处理队列
			FileProcessTask newTask = fileProcessTaskService.addNewTask(reqMap);
			log.info(LogArgsUtil.getLogArgus()+"建立任务成功，任务id为："+newTask.getTaskId()+"请求信息为:"+reqXml.replace("\r\n","").replace("\n", ""));
			respMap.put(HttpMap.FILE_TASK_ID, newTask.getTaskId());
		} catch (BaseException e) {
			respMap.put(HttpMap.RETCODE, e.getRetcode().getCode());
			log.error(LogArgsUtil.getLogArgus()+"请求信息创建任务失败：" + e.getErrMsg(), e);
		} catch (Exception e) {
			respMap.put(HttpMap.RETCODE, Retcode.INNER_ERROR.getCode());
			log.error(LogArgsUtil.getLogArgus()+"请求信息创建任务失败：", e);
		}
		String respXml = endHandle(respMap);
		log_xml(reqXml, respXml);
		return respXml;
	}

	/**
	 * 记录请求和响应的xml报文日志
	 * 
	 * @param reqMap
	 * @param respXml
	 */
	private void log_xml(String reqXml, String respXml) {
		try {
			StringBuffer logBuff = new StringBuffer();
			logBuff.append(LogArgsUtil.getLogArgus()).append(HttpMap.REQUEST).append(Constant.LOG_ARG_XML_SEPARATOR)
					.append(reqXml.replaceAll("\r\n", "").replaceAll("\n", "")).append("\n");
			logBuff.append(LogArgsUtil.getLogArgus()).append(HttpMap.RESPONSE).append(Constant.LOG_ARG_XML_SEPARATOR)
					.append(respXml.replaceAll("\r\n", "").replaceAll("\n", "")).append("\n");
			logBuff.append("耗时：" + TimeCountUtil.getTimeConsuming()).append("\n");
			merReq_and_res_xml.info(logBuff.toString());
		} catch (Exception e) {
		}
	}

	/**
	 * 执行结束
	 * 
	 * @param transId
	 * @param respMap
	 * @return
	 * @throws BaseException
	 */
	private String endHandle(Map<String, String> respMap) {
		// 放入响应时间
		respMap.put(HttpMap.DATETIME, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 生成响应签名
		checkInfoHandler.respSignCreate(respMap);
		// 生成响应报文
		String respXml = XmlUtils.mapToXml(respMap, HttpMap.RESPONSE);
		log.info(LogArgsUtil.getLogArgus() + "*****返回给接口层测报文:*****\n" + respXml + "\n共耗时:"
				+ TimeCountUtil.getTimeConsuming());
		return respXml;
	}

	/**
	 * 将xml报文解析为hashmap对象 解析出错返回一个空的hashmap
	 * 
	 * @param reqXml
	 *            解析的xml文本
	 * @return
	 */
	private Map<String, String> getReqXmlMap(String reqXml) {
		try {
			Map<String, String> reqXmlMap = XmlUtils.xmlToMap(reqXml);
			return reqXmlMap;
		} catch (Exception e) {
			log.error("将请求报文解析为hashmap出错" + reqXml + e);
			return new HashMap<String, String>();
		}
	}

}
