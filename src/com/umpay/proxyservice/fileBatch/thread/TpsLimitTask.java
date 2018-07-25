package com.umpay.proxyservice.fileBatch.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.fileBatch.tactics.writer.impl.QueryDataWriter;
import com.umpay.proxyservice.fileBatch.util.ThreadUtil;
import com.umpay.proxyservice.limit.LimitCheckHandler;
import com.umpay.proxyservice.util.HttpClientUtil;
import com.umpay.proxyservice.util.StringUtil;
import com.umpay.proxyservice.util.XmlUtils;
import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;

/**
 * tps限制的请求线程
 * 
 * @author xuxiaojia
 */
public class TpsLimitTask extends Thread {
	private final static Logger log = LoggerFactory.getLogger(TpsLimitTask.class);
	private final static Logger queryDataSimple = LoggerFactory.getLogger("query_data_simple");

	protected boolean isRun;
	protected QueryDataWriter queryDataWriter;
	protected TransferResponse transferResponse;
	protected ConfigInfoPO configInfoPO;

	public TpsLimitTask(QueryDataWriter queryDataWriter, ConfigInfoPO configPo) {
		this.isRun = true;
		this.queryDataWriter = queryDataWriter;
		this.configInfoPO = configPo;
		transferResponse = createTransferResponse(configPo);
	}

	private TransferResponse createTransferResponse(ConfigInfoPO configPo) {
		try {
			if(StringUtil.isNotEmpty(configPo.getTransferResponseClass())){
				return (TransferResponse) Class.forName(configPo.getTransferResponseClass()).newInstance();
			}
		} catch (Exception e) {
			log.error(queryDataWriter.getTaskId()+":创建定制结果转换对象失败，返回结果不转换",e);
		}
		return null;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	@Override
	public void run() {
		while (isRun()) {
			// 在没有请求信息或超频的情况下休息10毫秒
			if (!queryDataWriter.haveQueryInfo()||!LimitCheckHandler.getInstance().execute(queryDataWriter.getLimitInfo())) {
				ThreadUtil.sleep(10);
				continue;
			}
			String reqXML = queryDataWriter.pollOneQueryInfo();
			if (reqXML == null) {
				continue;
			}
			try {
				doQuery(reqXML);
			} catch (Exception e) {
				log.error(queryDataWriter.getTaskId()+":请求" + configInfoPO.getQueryDataUrl() + "出现异常，报文：" + reqXML, e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行查询
	 * 
	 * @throws Exception
	 */
	protected void doQuery(String reqXML) throws Exception {
		//查询之前和之后打印请求和响应的xml报文
		queryDataSimple.info("0"+Constant.LOG_ARG_XML_SEPARATOR+queryDataWriter.getTaskId()+Constant.LOG_ARG_XML_SEPARATOR+reqXML.replace("\r\n", "").replace("\n", ""));
		String respXml = HttpClientUtil.sendByPost(configInfoPO.getQueryDataUrl(), reqXML);
		queryDataSimple.info("1"+Constant.LOG_ARG_XML_SEPARATOR+queryDataWriter.getTaskId()+Constant.LOG_ARG_XML_SEPARATOR+respXml.replace("\r\n", "").replace("\n", ""));
		String recodeString = StringUtil.getMepValue4list(configInfoPO.getFileMustHaveField(),
				XmlUtils.xmlToMap(reqXML));
		String respData = "";
		if (transferResponse == null) {
			respData = respXml.replace("\n", "").replace("\r\n", "");
		} else {
			try {
				respData = transferResponse.transfer(respXml);
			} catch (Exception e) {
				log.error(queryDataWriter.getTaskId()+":任务的请求:" + reqXML + "的响应报文转换出错", e);
			}
		}
		recodeString = recodeString + Constant.LOG_ARG_XML_SEPARATOR + respData;
		queryDataWriter.offerNewRespXml(recodeString);
	}

}