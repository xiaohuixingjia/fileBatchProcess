package com.umpay.proxyservice.processStep;

/**
 * 步骤信息
 * 
 * @author xuxiaojia
 */
public class StepInfo {
	/* 当前步骤名称 */
	private String stepName;
	/* 当前步骤在整体步骤的第几步 */
	private int setpNum;

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public int getSetpNum() {
		return setpNum;
	}

	public void setSetpNum(int setpNum) {
		this.setpNum = setpNum;
	}

	public StepInfo(String stepName, int setpNum) {
		super();
		this.stepName = stepName;
		this.setpNum = setpNum;
	}

	public StepInfo() {
		super();
	}

	@Override
	public String toString() {
		return "[stepName=" + stepName + ", setpNum=" + setpNum + "]";
	}

}
