package com.umpay.proxyservice.fileBatch.dao.impl;

import java.util.List;

import com.umpay.proxyservice.fileBatch.dao.ConfigInfoDao;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.util.ReadLocalConfigFileUtil;

public class ConfigInfoDaoImpl implements ConfigInfoDao{

	@Override
	public List<ConfigInfoPO> getAllConfigInfo() throws Exception {
		ReadLocalConfigFileUtil localConfig=new ReadLocalConfigFileUtil();
		return localConfig.getConfig(ConfigInfoPO.class);
	}

}
