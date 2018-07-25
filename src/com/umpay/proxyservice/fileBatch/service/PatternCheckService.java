package com.umpay.proxyservice.fileBatch.service;

import com.umpay.proxyservice.exception.BaseException;

/**
 * 正则校验服务
 * 
 * @author xuxiaojia
 */
public interface PatternCheckService {
	/**
	 * 对传入的value进行patternKey配置规则的正则校验  如果没有patternKey对应的正则表达式 则返回true
	 *   
	 * @param value 
	 * @param patternKey
	 *  return true 表示校验通过，false 标识校验不通过
	 */
	public boolean elemValueCheck(String value, String patternKey) throws BaseException ;
}
