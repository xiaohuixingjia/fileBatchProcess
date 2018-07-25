package com.umpay.proxyservice.fileBatch.service;

import java.util.Map;

import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;

/**
 * 获取配置信息的服务
 * 
 * @author xuxiaojia
 */
public interface ConfigService {
	/**
	 * 根据funcode和merid获取配置信息
	 * 
	 * @param funcode
	 * @param merid
	 * @param childmerid
	 * @return
	 */
	public ConfigInfoPO getConfigInfo(String funcode, String merid,String childmerid);

	/**
	 * 根据请求信息的map对象获取配置信息
	 * 
	 * @param reqXmlMap
	 * @return
	 */
	public ConfigInfoPO getConfigInfo(Map<String, String> reqXmlMap);

}
