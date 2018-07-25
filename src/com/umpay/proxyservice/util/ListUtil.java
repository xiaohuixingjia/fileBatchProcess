package com.umpay.proxyservice.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * list集合工具类
 * 
 * @author xuxiaojia
 */
public class ListUtil {

	/**
	 * 将list集合中的每个排放的值放到map中当做key value为该值在list的顺序
	 * 
	 * @param list
	 * @return
	 */
	public static <T> Map<T, Integer> list2indexMap(List<T> list) {
		Map<T, Integer> indexMap = new HashMap<T, Integer>();
		for (int i = 0; i < list.size(); i++) {
			indexMap.put(list.get(i), i);
		}
		return indexMap;
	}

	/**
	 * 将list中的元素拼成一个字符串，分隔符为separator list中空的元素位置拼defaultValue
	 * @param list
	 * @param separator
	 * @param defaultValue  传入null 则list中出现null会抛异常
	 * @return
	 */
	public static <T> String list2string(List<T> list, String separator,String defaultValue) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				builder.append(separator);
			}
			if(list.get(i)==null){
				builder.append(defaultValue.toString());
			}else{
				builder.append(list.get(i).toString());
			}
		}
		return builder.toString();
	}
	
	/**
	 * 将list中的元素拼成一个字符串，分隔符为separator list中空的元素位置拼空字符串
	 * @param list 
	 * @param separator
	 * @return
	 */
	public static <T> String list2string(List<T> list, String separator) {
		return list2string(list, separator, "");
	}
}
