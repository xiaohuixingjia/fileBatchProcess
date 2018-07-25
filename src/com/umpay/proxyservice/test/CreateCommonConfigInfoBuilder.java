package com.umpay.proxyservice.test;

import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.handler.ProcessExecuteHandler.ProcessType;

/**
 * 创建公共的文件处理服务
 * 
 * @author xuxiaojia
 */
public class CreateCommonConfigInfoBuilder {
	/**
	 * localPorcess流程默认配置
	 * @return
	 */
	public static ConfigInfoPO.ConfigInfoBuilder localPorcess(){
		return new ConfigInfoPO.ConfigInfoBuilder()
				.setQueryDataUrl("http://10.102.5.53:9005/umpaydc/dataQuery/")
				.setEncryKey("f9f6da61-9dfb-42")
				.setIvString("8b-a1a8-be121323")
				.setMaxTps(500)
				.setQueryThreads(15)
				.setProcessType(ProcessType.localProcess);
	}
	
	/**
	 * queryCheck流程默认配置
	 * @return
	 */
	public static ConfigInfoPO.ConfigInfoBuilder queryCheckPorcess(){
		return new ConfigInfoPO.ConfigInfoBuilder()
				.setQueryDataUrl("http://10.102.5.53:9005/umpaydc/dataQuery/")
				.setCheckDataUrl("http://10.102.5.52:9004/umpaydc/dataQuery/")
				.setEncryKey("f9f6da61-9dfb-42")
				.setIvString("8b-a1a8-be121323")
				.setMaxTps(20)
				.setQueryThreads(3)
				.setProcessType(ProcessType.queryCheck);
	}
	
	/**
	 * sftpProcess流程默认配置
	 * @return
	 */
	public static ConfigInfoPO.ConfigInfoBuilder sftpProcessPorcess(){
		return new ConfigInfoPO.ConfigInfoBuilder();
	}
	
	/**
	 * 根据流程创建默认配置
	 * @param processType
	 * @return
	 */
	public static ConfigInfoPO.ConfigInfoBuilder getCommenConfigInfoBuilder(ProcessType processType){
		switch (processType) {
		case localProcess:
			return localPorcess();
		case queryCheck:
			return queryCheckPorcess();
		case sftpProcess:
			return sftpProcessPorcess();
		default:
			return localPorcess();
		}
	}
}
