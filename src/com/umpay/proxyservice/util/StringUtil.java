package com.umpay.proxyservice.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.umpay.proxyservice.Constant;
/**
 * 文字处理工具
* @date 2016年6月15日 上午10:18:47
 */
public class StringUtil {
	/**
	 * 判断是不是文件, .后面有文字
	 * @param str 
	 * @return
	* @date 2016年5月17日 上午11:24:35
	 */
	public static boolean isFileName(String str){
		Pattern pattern=Pattern.compile("\\.\\w+", Pattern.CASE_INSENSITIVE);
		return pattern.matcher(str).find();
	}
	
	/**
	 * 正则校验
	 * @param str
	 * @param reg
	 * @return
	* @date 2016年5月17日 下午4:26:15|2016年6月13日 下午2:47:15 修改
	 */
	public static boolean match(String str,String reg){
		Pattern pattern=Pattern.compile(reg);
		return pattern.matcher(str).matches();
	}
	
	
	
	/**
	 * 整数类型,最多10位
	 * @param str
	 * @return
	* @date 2016年5月17日 下午5:49:47
	 */
	public static boolean isInteger(String str){
		Pattern pattern=Pattern.compile("\\d{1,10}", Pattern.CASE_INSENSITIVE);
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 是否为空
	 * @param strs
	 * @return
	* @date 2016年5月18日 上午9:44:06 修改
	 */
	public static boolean isEmpty(String...strs){
		for(String str : strs)if(str == null || str.equals(""))return true;
		return false; 
	}
	
	/**
	 * 是否不为空
	 * @param strs
	 * @return
	 */
	public static boolean isNotEmpty(String...strs){
		return !isEmpty(strs);
	}
	
	/**
	 * 将集合转换为字符串，
	 * @param list
	 * @param separator 分隔符
	 * @return
	* @date 2016年5月18日 下午2:54:01   |  2016年6月13日 下午2:26:01 修改,最后一个分隔符去掉
	* 
	 */
	public static String toSpecificLine(List<String> list,String separator){
		if (list==null||list.isEmpty()||isEmpty(separator)) return null;
		StringBuffer buffer=new StringBuffer();
		int size=list.size();
		for(int index=0;index<size-1;index++){
			String e=list.get(index);
			buffer.append(e+separator);
		}
		buffer.append(list.get(size-1));
		return buffer.toString();
	}
	/**
	 * 将集合转换为字符串，
	 * @param strs
	 * @param separator
	 * @return
	 */
	public static String toSpecificLine(String[] strs,String separator){
		if (strs==null||strs.length<1||isEmpty(separator)) return null;
		StringBuffer buffer=new StringBuffer();
		
		int index = 0;
		for(index = 0 ; index < strs.length-1 ; index++){
			buffer.append(strs[index]+separator);
		}
		buffer.append(strs[index]);
		return buffer.toString();
	}
	
	/**
	 * 将map中所有keys的vlue值打横
	 * @param keys
	 * @param map
	 * @return
	 */
	public static String getMepValue4list( List<String> keys,Map<String, String> map){
		StringBuffer recodeString = new StringBuffer();
		for (String string : keys) {
			if (StringUtils.isNotEmpty(recodeString.toString())) {
				recodeString.append(Constant.LOG_SEPARATOR);
			}
			recodeString.append(map.get(string));
		}
		return recodeString.toString();
	}
}
