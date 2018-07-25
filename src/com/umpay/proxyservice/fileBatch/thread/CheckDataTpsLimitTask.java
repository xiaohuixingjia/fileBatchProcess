package com.umpay.proxyservice.fileBatch.thread;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.fileBatch.tactics.writer.impl.QueryDataWriter;
import com.umpay.proxyservice.util.HttpClientUtil;
import com.umpay.proxyservice.util.StringUtil;
import com.umpay.proxyservice.util.XmlUtils;

/**
 * 核查数据的tps限制的请求线程
 * 
 * @author xuxiaojia
 */
public class CheckDataTpsLimitTask extends TpsLimitTask {
	private final static Logger log = LoggerFactory.getLogger(CheckDataTpsLimitTask.class);
	
	private final AtomicInteger diffCount; 
			
	public CheckDataTpsLimitTask(QueryDataWriter queryDataWriter, ConfigInfoPO configPo, AtomicInteger diffCount) {
		super(queryDataWriter, configPo);
		this.diffCount = diffCount;
	}
	private final static Logger queryDataSimple = LoggerFactory.getLogger("query_data_simple");


	/**
	 * 执行查询
	 * 
	 * @throws Exception
	 */
	protected void doQuery(String reqXML) throws Exception {
		//查询之前和之后打印请求和响应的xml报文
		queryDataSimple.info("0"+Constant.LOG_ARG_XML_SEPARATOR+queryDataWriter.getTaskId()+Constant.LOG_ARG_XML_SEPARATOR+reqXML.replace("\r\n", "").replace("\n", ""));
		String respXml = HttpClientUtil.sendByPost(configInfoPO.getQueryDataUrl(), reqXML);
		String respXml4check = HttpClientUtil.sendByPost(configInfoPO.getCheckDataUrl(), reqXML);
		queryDataSimple.info("1"+Constant.LOG_ARG_XML_SEPARATOR+queryDataWriter.getTaskId()+Constant.LOG_ARG_XML_SEPARATOR+respXml.replace("\r\n", "").replace("\n", ""));
		queryDataSimple.info("2"+Constant.LOG_ARG_XML_SEPARATOR+queryDataWriter.getTaskId()+Constant.LOG_ARG_XML_SEPARATOR+respXml4check.replace("\r\n", "").replace("\n", ""));
		String recodeString = StringUtil.getMepValue4list(configInfoPO.getFileMustHaveField(),
				XmlUtils.xmlToMap(reqXML));
		String respData="";
		String respData4check="";
		if (transferResponse == null) {
			respData = respXml.replace("\n", "").replace("\r\n", "");
			respData4check = respXml4check.replace("\n", "").replace("\r\n", "");
		} else {
			try {
				respData = transferResponse.transfer(respXml);
			} catch (Exception e) {
				log.error(queryDataWriter.getTaskId()+":任务的请求:" + reqXML + "的响应报文转换出错", e);
			}
			try {
				respData4check = transferResponse.transfer(respXml4check);
			} catch (Exception e) {
				log.error(queryDataWriter.getTaskId()+":任务的请求:" + respXml4check + "的响应报文转换出错", e);
			}
		}
		if(!respData.equals(respData4check)){
			diffCount.getAndIncrement();
			recodeString = recodeString + Constant.LOG_ARG_XML_SEPARATOR +"query"+ Constant.LOG_ARG_XML_SEPARATOR + respData;
			recodeString = recodeString + Constant.LOG_ARG_XML_SEPARATOR +"check"+ Constant.LOG_ARG_XML_SEPARATOR + respData4check;
			queryDataWriter.offerNewRespXml(recodeString);
		}
	}

}