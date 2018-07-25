package com.umpay.proxyservice.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.Constant;

/**
 * 处理日志参数常用工具类
 * 
 * @author xuxiaojia
 */
public class LogArgsUtil {
	private static final Logger log = LoggerFactory
			.getLogger(LogArgsUtil.class);
	
	/* 保证并发的情况下日志打印的参数能准确 */
	private static final ThreadLocal<String> logArgs = new ThreadLocal<String>();

	/**
	 * 将传入的map中的所有的value拼接 Constant.LOG_SEPARATOR常量值 生成一个字符串并放入本地线程logArgs中
	 * 
	 * @param xmlMap
	 *            map集合
	 */
	public static void createLogArgs(Map<String, String> xmlMap) {
		createLogArgs(xmlMap, Constant.LOG_SEPARATOR);
	}
	/**
	 *  taskid + 将传入的map中的所有的value拼接 Constant.LOG_SEPARATOR常量值 生成一个字符串并放入本地线程logArgs中 
	 * 
	 * @param xmlMap
	 *            map集合
	 */
	public static void createLogArgsAndTaskId(Map<String, String> xmlMap,String taskId) {
		createLogArgs(xmlMap, Constant.LOG_SEPARATOR);
		logArgs.set(taskId+Constant.LOG_SEPARATOR+logArgs.get());
	}

	/**
	 * 将传入的map中的所有的value拼接logSeparator参数生成一个字符串并放入本地线程logArgs中
	 * 
	 * @param xmlMap
	 *            map集合
	 * @param logSeparator
	 *            分隔符
	 */
	public static void createLogArgs(Map<String, String> xmlMap, String logSeparator) {
		String result="";
		try {
			if (xmlMap==null) {
				logArgs.set("");
			}else{
				String transId=xmlMap.get(HttpMap.TRANSID);
				String merId = xmlMap.get(HttpMap.MERID);
				String funcode=xmlMap.get(HttpMap.FUNCODE);
				String[] reqArgs={transId,funcode,merId};
				result=StringUtil.toSpecificLine(reqArgs, Constant.LOG_SEPARATOR);
			}
		} catch (Exception e) {
			log.error("生成日志前缀出错",e);
		}
		logArgs.set(result.toString());
	}

	/**
	 * 返回本地线程保存的日志参数
	 * 
	 * @return
	 */
	public static String getLogArgus() {
		return logArgs.get();
	}
	
	public static void setLogArgs(String logPrefix){
		logArgs.set(logPrefix);
	}
}
