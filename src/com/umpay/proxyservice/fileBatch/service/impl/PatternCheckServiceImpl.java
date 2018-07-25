package com.umpay.proxyservice.fileBatch.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.umpay.proxyservice.exception.BaseException;
import com.umpay.proxyservice.fileBatch.service.PatternCheckService;
import com.umpay.proxyservice.ruleTimeService.InitService;
import com.umpay.proxyservice.util.PropertiesUtil;

public class PatternCheckServiceImpl implements PatternCheckService, InitService {
	private Map<String, Pattern> patternMap;

	public PatternCheckServiceImpl() {
		super();
	}

	@Override
	public void init() throws Exception {
		patternMap = new HashMap<String, Pattern>();
		Properties properties = PropertiesUtil.getInstance("patternCheck.properties").getProperties();
		for (Object object : properties.keySet()) {
			patternMap.put(object.toString(), Pattern.compile(properties.getProperty(object.toString())));
		}
	}

	public boolean elemValueCheck(String str, String elemName) throws BaseException {
		if (patternMap.containsKey(elemName)) {
			Matcher matcher = patternMap.get(elemName).matcher(str);
			return matcher.matches();
		}
		return true;
	}

}
