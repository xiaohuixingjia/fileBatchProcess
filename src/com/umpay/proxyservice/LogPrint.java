package com.umpay.proxyservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印日志
* @date 2016年7月1日 下午4:18:45
 */
public class LogPrint {
	private static final Logger log = LoggerFactory
			.getLogger("LogPrint");
	private static final Logger responseArgSimpleLog = LoggerFactory
			.getLogger("response_arg_simple");
	private static final Logger requestArgSimpleLog=LoggerFactory.getLogger("request_arg_simple");
	/**
	 * 在简要日至，打空字符串
	 */
	public  void execute() {
		log.info("对请求和响应简要日志打印空字符串");
		responseArgSimpleLog.info(" ");
		requestArgSimpleLog.info(" ");
	}
}
