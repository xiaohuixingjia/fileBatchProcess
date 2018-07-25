package com.umpay.proxyservice.limit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.limit.bean.BaseLimitBean;
import com.umpay.proxyservice.limit.bean.DayLimit;
import com.umpay.proxyservice.limit.bean.LimitConfig;
import com.umpay.proxyservice.limit.bean.LimitInfo;
import com.umpay.proxyservice.limit.bean.SecondLimit;
import com.umpay.proxyservice.limit.exception.BaseLimitException;

/**
 * 处理风影请求类
 * 
 * @author
 * @date 2016年6月15日 上午10:14:47
 */
public class LimitCheckHandler {
	private static final Logger log = LoggerFactory.getLogger(LimitCheckHandler.class);
	
	private  static  LimitCheckHandler checkHandler=new LimitCheckHandler();
	public static LimitCheckHandler getInstance(){
		return checkHandler;
	}
	/*
	 * 默认的每日限制
	 */
	private int defaultDayMaxLimit =-1;
	/*
	 * 默认的每秒限制
	 */
	private int defaultSecondMaxLimit=100;
	/*
	 * 每日限制的配置map集合 key为merid+“,”+funcode
	 */
	private Map<String, LimitConfig> configMap;


	public Map<String, LimitConfig> getConfigMap() {
		return configMap;
	}

	private LimitCheckHandler() {
		configMap=new HashMap<String, LimitConfig>();
	}

	
	public void putTpsLimit2map(LimitInfo limitInfo){
		List<BaseLimitBean> limitList = new ArrayList<BaseLimitBean>();
		limitList.add(createLimitBean(BaseLimitBean.SECOND_LIMIT_BEAN, limitInfo.getTps(), getKeyFromXmlMap(limitInfo)));
		// 创建限流配置对象
		LimitConfig limitConfig = new LimitConfig(limitList);
		configMap.put(getKeyFromXmlMap(limitInfo), limitConfig);
	}
	/**
	 * 设置默认的限流配置信息到内存中
	 * 
	 * @param key
	 *            对应map中的key值
	 * @param map
	 *            需要存放的map集合
	 */
	public void setDefalutLimitConfig(String key, Map<String, LimitConfig> map) {
		List<BaseLimitBean> limitList = new ArrayList<BaseLimitBean>();
		// tps限制
		limitList.add(createLimitBean(BaseLimitBean.SECOND_LIMIT_BEAN, defaultSecondMaxLimit, key));
		// 每日访问限制
		limitList.add(createLimitBean(BaseLimitBean.DAY_LIMIT_BEAN, defaultDayMaxLimit, key.toString()));
		// 创建限流配置对象
		LimitConfig limitConfig = new LimitConfig(limitList);
		map.put(key, limitConfig);
	}

	/**
	 * 根据limitType来创建包含限制信息的对象
	 * 
	 * @param limitType
	 *            创建限制信息的类型
	 * @param maxLimitConf
	 *            最大限制数量
	 * @param key
	 *            此对象对应map中的key值
	 * @return
	 */
	private BaseLimitBean createLimitBean(int limitType, Integer maxLimitConf, String key) {
		BaseLimitBean bean = null;
		switch (limitType) {
		case BaseLimitBean.SECOND_LIMIT_BEAN:
			bean = new SecondLimit(BaseLimitBean.SECOND_LIMIT_BEAN);
			break;
		case BaseLimitBean.DAY_LIMIT_BEAN:
			bean = new DayLimit(BaseLimitBean.DAY_LIMIT_BEAN);
			break;
		}
		bean.setMapKey(key);
		bean.setCurrDate(new Date());
		bean.setAtomicInteger(new AtomicInteger(0));
		bean.setMaxLimit(maxLimitConf);
		return bean;
	}

	/**
	 * @param reqXML
	 * @return
	 * 
	 */
	public boolean execute(LimitInfo limitInfo) {
		try {
			// 获取根据规则生成的key
			String key = getKeyFromXmlMap(limitInfo);
			// 获取限制配置信息
			LimitConfig limitConfig = getLimitConfigByKey(key);
				try {
					limitConfig.checkLimit();
				} catch (BaseLimitException e) {
					log.info(e.getErrorCode());
					return false;
					// 校验不通过
				}
		} catch (Exception e) {
			log.error("校验出现异常" + limitInfo.toString(), e);
		}
		return true;
	}

	private LimitConfig getLimitConfigByKey(String key) {
		LimitConfig limitConfig = configMap.get(key);
		// 如果配置中允许使用默认配置，则判断限制对象是否为空，如果为空则根据默认创建新的默认配置对象
		if (limitConfig == null) {
			setDefalutLimitConfig(key, configMap);
			limitConfig = configMap.get(key);
		}
		return limitConfig;
	}

	/**
	 * 从xmlmap中获取merid和funcode拼装成的key
	 * 
	 * @param xmlMap
	 * @return
	 */
	private String getKeyFromXmlMap(LimitInfo limitInfo) {
		String merid = limitInfo.getMerid();
		String funcode = limitInfo.getFuncode();
		return merid + Constant.LOG_SEPARATOR + funcode;
	}



}
