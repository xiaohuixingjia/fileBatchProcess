package com.umpay.proxyservice.fileBatch.tactics.step;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.constant.Retcode;
import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.bean.FileProcessTask;
import com.umpay.proxyservice.handler.CheckInfoHandler;
import com.umpay.proxyservice.processStep.StepInfo;
import com.umpay.proxyservice.ruleTimeService.RuleTimeService;
import com.umpay.proxyservice.util.DateUtil;
import com.umpay.proxyservice.util.HttpClientUtil;
import com.umpay.proxyservice.util.HttpMap;
import com.umpay.proxyservice.util.XmlUtils;

/**
 * 回调响应商户步骤
 * 
 * 
 * 
 * @author xuxiaojia
 */
public class CallBackNoticeStep extends AbsFileProcessStep {
	private final static Logger log = LoggerFactory.getLogger("CallBackNoticeStep");

	public CallBackNoticeStep(StepInfo stepInfo) {
		super(stepInfo);
	}

	@Override
	protected FileProcessTask execute(FileProcessTask oneTask) throws BaseException {
		Map<String, String> noticeMap = new HashMap<String, String>();
		noticeMap.put(HttpMap.FUNCODE, oneTask.getReqMap().get(HttpMap.FUNCODE));
		noticeMap.put(HttpMap.DEALCODE, oneTask.getDealcode());
		noticeMap.put(HttpMap.TRANSID, oneTask.getReqMap().get(HttpMap.TRANSID));
		noticeMap.put(HttpMap.DATETIME, DateUtil.getDateString(new Date(), DateUtil.PARTEN_4_yyyymmddhhmmss));
		if ("0000".equals(oneTask.getDealcode())) {
			noticeMap.put(HttpMap.SIZE, "" + oneTask.getResuFileSize());
			String localFullFileName = oneTask.getResuFullFileName();
			noticeMap.put(HttpMap.FILE, localFullFileName.substring(localFullFileName.lastIndexOf(File.separator) + 1));
		}
		new CheckInfoHandler().signGener(noticeMap,
				RuleTimeService.getRts().getConfigPo(oneTask.getReqMap()).getReqSignCreateList());
		String reqXml = XmlUtils.mapToXml(noticeMap, "request");
		try {
			log.info(oneTask.getTaskId()+"：回调通知商户请求报文:"+reqXml.replace("\r\n", "").replace("\n", ""));
			String respXml = HttpClientUtil.sendByPost(RuleTimeService.getRts().getConfigPo(oneTask.getReqMap()).getNoticeUrl(), reqXml);
			log.info(oneTask.getTaskId()+"：回调通知商户响应报文:"+respXml.replace("\r\n", "").replace("\n", ""));
			Map<String, String> respMap = XmlUtils.xmlToMap(respXml);
			if(!respMap.get(HttpMap.RETCODE).equals(Retcode.SUCCESS.getCode())){
				throw new BaseException(Retcode.MER_RESP_RETCODE_ERROR);
			}
		} catch (Exception e) {
			throw new BaseException(Retcode.MER_RESP_ERROR,e);
		}
		return oneTask;
	}

}
