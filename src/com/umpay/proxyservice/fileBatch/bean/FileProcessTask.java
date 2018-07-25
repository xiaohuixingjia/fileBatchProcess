package com.umpay.proxyservice.fileBatch.bean;

import java.util.Map;

import com.umpay.proxyservice.processStep.StepInfo;

/**
 * 文件处理任务
 * 
 * @author xuxiaojia
 */
public class FileProcessTask extends FileInfo {
	/**
	 * 任务id 唯一
	 */
	private String taskId;
	/**
	 * 请求xml报文解析的hashmap
	 */
	private Map<String, String> reqMap;
	/**
	 * 步骤信息
	 */
	private StepInfo stepInfo;
	/**
	 * 处理结果的文件名
	 */
	private String resuFullFileName;
	/**
	 * 文件处理结果码
	 */
	private String dealcode;

	public String getDealcode() {
		return dealcode;
	}

	public void setDealcode(String dealcode) {
		this.dealcode = dealcode;
	}

	public String getResuFullFileName() {
		return resuFullFileName;
	}

	public void setResuFullFileName(String resuFullFileName) {
		this.resuFullFileName = resuFullFileName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, String> getReqMap() {
		return reqMap;
	}

	public void setReqMap(Map<String, String> reqMap) {
		this.reqMap = reqMap;
	}

	public StepInfo getStepInfo() {
		return stepInfo;
	}

	public void setStepInfo(StepInfo stepInfo) {
		this.stepInfo = stepInfo;
	}

}
