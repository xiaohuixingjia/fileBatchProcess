package com.umpay.proxyservice.xmlTransfer.tactic.impl;

import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;
/**
 * 适用于建模商户输出分数
* @author xuxiaojia
 */
public class FicoResuTransfer implements TransferResponse {
	@Override
	public String transfer(String respXml) throws Exception{
		String resu = "";
		try {
			if (respXml.indexOf("<result>") != -1) {
				 resu = respXml.substring(respXml.indexOf("<result>") + "<result>".length(),
						 respXml.indexOf("</result>"));
			}
		} catch (Exception e) {
			throw e;
		}
		return resu;
	}
}
