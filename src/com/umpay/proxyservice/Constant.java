package com.umpay.proxyservice;

/**
 * 常量
 * 
 * @author xuxiaojia
 */
public class Constant {
	/**
	 * 通用内容分隔符 横线 _
	 */
	public static final String HENGXIAN_SEPARATOR = "_";
	/**
	 * 一千
	 */
	public static final int ONE_THOUSAND = 1000;

	public static final String LOG_SEPARATOR = ",";
	public static final String POINT_SEPARATOR = "\\.";
	public static final String LOG_PARAM_SEPARATOR = "&";
	public static final String LOG_ARG_XML_SEPARATOR = "|";
	/**
	 * utf-8 字符集编码
	 */
	public static final String UTF_8 = "UTF-8";
	/**
	 * 不同操作系统的换行符
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	public static void main(String[] args) {
		System.out.println("aabb.txt".split(POINT_SEPARATOR)[0]);
	}
}
