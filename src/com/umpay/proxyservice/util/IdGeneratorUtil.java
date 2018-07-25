package com.umpay.proxyservice.util;

import java.util.Date;

/**
 * 唯一id生成工具
* @author xuxiaojia
 */
public class IdGeneratorUtil {
	private static long sequenceId = System.currentTimeMillis();
	private final static Object syncLock = new Object();

	private IdGeneratorUtil() {}

	private static volatile IdGeneratorUtil idGeneratorUtil = null;

	public static IdGeneratorUtil getIdGeneratorUtil() {
		if (idGeneratorUtil == null) {
			synchronized (syncLock) {
				if (idGeneratorUtil == null) {
					idGeneratorUtil = new IdGeneratorUtil();
				}
			}
		}
		return idGeneratorUtil;
	}

	/**
	 * 获得20位的唯一id 格式为：yyyymmddhhmmss_xxxxx
	 * @return
	 */
	public String getId() {

		String date = DateUtil.getDateString(new Date(), DateUtil.PARTEN_4_yyyymmddhhmmss);
		String nextTime = getNextId() + "";
		date = date + "_" + nextTime.substring(nextTime.length() - 5);

		return date;

	}

	private synchronized long getNextId() {
		return sequenceId++;
	}
}
