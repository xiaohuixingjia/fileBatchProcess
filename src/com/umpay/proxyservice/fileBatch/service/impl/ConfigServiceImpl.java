package com.umpay.proxyservice.fileBatch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.umpay.proxyservice.Constant;
import com.umpay.proxyservice.fileBatch.dao.ConfigInfoDao;
import com.umpay.proxyservice.fileBatch.dao.impl.ConfigInfoDaoImpl;
import com.umpay.proxyservice.fileBatch.po.ConfigInfoPO;
import com.umpay.proxyservice.fileBatch.service.ConfigService;
import com.umpay.proxyservice.ruleTimeService.InitService;
import com.umpay.proxyservice.util.HttpMap;

public class ConfigServiceImpl implements InitService, ConfigService {
	private Map<String, ConfigInfoPO> configMap;

	@Override
	public void init() throws Exception {
		configMap = new HashMap<String, ConfigInfoPO>();
		ConfigInfoDao dao = new ConfigInfoDaoImpl();
		List<ConfigInfoPO> allConfigInfo = dao.getAllConfigInfo();
		for (ConfigInfoPO configInfoPO : allConfigInfo) {
			configInfoPO.init();
			if (StringUtils.isNotEmpty(configInfoPO.getChildmerid())) {
				configMap.put(configInfoPO.getFuncode() + Constant.HENGXIAN_SEPARATOR + configInfoPO.getMerid()
						+ Constant.HENGXIAN_SEPARATOR + configInfoPO.getChildmerid(), configInfoPO);
			} else {
				configMap.put(configInfoPO.getFuncode() + Constant.HENGXIAN_SEPARATOR + configInfoPO.getMerid(),
						configInfoPO);
			}
		}
	}

	public ConfigInfoPO getConfigInfo(String funcode, String merid, String childmerid) {
		if (StringUtils.isNotEmpty(childmerid)) {
			return configMap
					.get(funcode + Constant.HENGXIAN_SEPARATOR + merid + Constant.HENGXIAN_SEPARATOR + childmerid);
		} else {
			return configMap.get(funcode + Constant.HENGXIAN_SEPARATOR + merid);
		}
	}

	public ConfigInfoPO getConfigInfo(Map<String, String> reqXmlMap) {
		return getConfigInfo(reqXmlMap.get(HttpMap.FUNCODE), reqXmlMap.get(HttpMap.MERID),
				reqXmlMap.get(HttpMap.CHILDMERID));
	}

}
