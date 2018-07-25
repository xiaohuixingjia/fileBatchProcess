package com.umpay.proxyservice.xmlTransfer.tactic.impl;

import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;
/**
 * 中证征信的模型输出分
* @author xuxiaojia
 */
public class ZhongZhengScoreTransfer implements TransferResponse {
	@Override
	public String transfer(String respXml) throws Exception{
		String resu = "";
		try {
			if (respXml.indexOf("<model>") != -1) {
				 resu = respXml.substring(respXml.indexOf("<model>") + "<model>".length(),
						 respXml.indexOf("</model>"));
			}
		} catch (Exception e) {
			throw e;
		}
		return resu;
	}
}
