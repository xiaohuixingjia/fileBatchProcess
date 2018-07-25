package com.umpay.proxyservice.fileBatch.dao;

import java.util.List;

import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;

public interface ConfigInfoDao {
	/**
	 * 获取所有的配置信息
	 * 
	 * @return
	 */
	public List<ConfigInfoPO> getAllConfigInfo() throws Exception;
}
