package com.umpay.proxyservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.util.HttpClientUtil;
import com.umpay.proxyservice.util.SpringUtil;

/**
 * 发送短信的服务
 * 
 * @author xuxiaojia
 */
public class MsgWarnService {
	private static final Logger log = LoggerFactory.getLogger(MsgWarnService.class);
	private static final String URL_SEPARATOR = "/";
	/* 告警短信读取时间1s */
	private final static int PHONE_MSG_READ_TIME_OUT = 1000;
	/* 告警短信响应时间0.5s */
	private final static int PHONE_MSG_CONNECT_TIME_OUT = 500; 
	/* 告警短信发送配置 */
	private static MsgWarnConfBean warnConf;

	public static void sendMsg(String msg){
		try {
			if(warnConf==null){
				warnConf=SpringUtil.getInstance().getContext().getBean(MsgWarnConfBean.class);
			}
			for (String phoneNum : warnConf.getWarnPhoneNumList()) {
				String url=warnConf.getWarnUrl()+URL_SEPARATOR+phoneNum+URL_SEPARATOR+msg;
				log.info("发送短信"+url);
				HttpClientUtil.sendGet(url,PHONE_MSG_READ_TIME_OUT,PHONE_MSG_CONNECT_TIME_OUT);
			}
		} catch (Exception e) {
			log.error("发送短信异常",e);
		}
	}
	
}
