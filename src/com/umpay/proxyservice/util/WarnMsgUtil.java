package com.umpay.proxyservice.util;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umpay.proxyservice.Constant;

/**
 * 告警工具类
 * 
 * @author xuxiaojia
 */
public class WarnMsgUtil {
	private static final Logger log = LoggerFactory.getLogger(WarnMsgUtil.class);

	/* 短信告警服务的路径 */
	private static final String smsMsgUrl = PropertiesUtil.getInstance("config.properties").getConfigItem("smsMsgUrl");
	/* 默认下告警短信的手机号 */
	private static final String phoneNums = PropertiesUtil.getInstance("config.properties").getConfigItem("phoneNums");
	/* 邮件告警服务的路径 */
	private static final String emailMsgUrl = PropertiesUtil.getInstance("config.properties")
			.getConfigItem("emailMsgUrl");
	/* 默认下告警邮件的邮箱 */
	private static final String mails = PropertiesUtil.getInstance("config.properties").getConfigItem("mails");

	/**
	 * 下发告警短信
	 * 
	 * @param phoneNum
	 * @param msg
	 */
	public static void sendSmsMsg(String phoneNum, String msg) {
		try {
			log.info("下发告警短信,手机号："+phoneNum+"短信内容:"+msg);
			HttpUtil.sendGet(new StringBuilder(smsMsgUrl).append("/").append(phoneNum).append("/").append(msg).toString());
		} catch (Exception e) {
			log.error("短信下发异常",e);
		}
	}

	/**
	 * 下发告警短信
	 * 
	 * @param phoneNums
	 * @param msg
	 */
	public static void sendSmsMsg(List<String> phoneNums, String msg) {
		try {
			for (String string : phoneNums) {
				sendSmsMsg(string, msg);
			}
		} catch (Exception e) {
			log.error("短信下发异常",e);
		}
	}
	/**
	 * 给默认的收告警服务的手机号下发告警信息
	 * 
	 * @param phoneNums
	 * @param msg
	 */
	public static void sendSmsMsg(String msg) {
		try {
			sendSmsMsg(Arrays.asList(phoneNums.split(Constant.LOG_SEPARATOR)), msg);
		} catch (Exception e) {
			log.error("短信下发异常",e);
		}
	}

	/**
	 * 下发告警邮件
	 * 
	 * @param mail
	 * @param title
	 * @param content
	 */
	public static void sendEmail(String mail, String title, String content) {
		try {
			log.info("下发告警邮件,邮箱："+mail+"主题:"+title+"邮件内容:"+content);
			HttpUtil.post(emailMsgUrl, mail + "|" + title + "|" + content);
		} catch (Exception e) {
			log.error("邮件下发异常",e);
		}
	}

	/**
	 * 下发告警邮件
	 * 
	 * @param mails
	 * @param title
	 * @param content
	 */
	public static void sendEmail(List<String> mails, String title, String content) {
		try {
			for (String string : mails) {
				sendEmail(string, title, content);
			}
		} catch (Exception e) {
			log.error("邮件下发异常",e);
		}
	}

	/**
	 * 给默认的告警服务的邮箱下发告警邮件
	 * 
	 * @param title
	 * @param content
	 */
	public static void sendEmail(String title, String content) {
		try {
			sendEmail(Arrays.asList(mails.split(Constant.LOG_SEPARATOR)), title, content);
		} catch (Exception e) {
			log.error("邮件下发异常",e);
		}
	}
}
