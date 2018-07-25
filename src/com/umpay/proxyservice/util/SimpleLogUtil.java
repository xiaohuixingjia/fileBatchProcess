package com.umpay.proxyservice.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLogUtil {
	/* 需要定时打印简要日志的文件名 */
	private List<String> logList;
	/* 打印的信息 */
	private String logMsg;

	public List<String> getLogList() {
		return logList;
	}

	public void setLogList(List<String> logList) {
		this.logList = logList;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

	public void printTimerLog() {
		for (String string : logList) {
			Logger log_funcode_simple = LoggerFactory.getLogger(string);
			log_funcode_simple.info(logMsg);
		}
	}
}
