package com.umpay.proxyservice.xmlTransfer.tactic.impl;

import com.umpay.proxyservice.util.JaxbUtil;
import com.umpay.proxyservice.xmlTransfer.bean.XiGuaFenBean;
import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;

public class XiGuaFenTransfer implements TransferResponse {
	@Override
	public String transfer(String respXml) throws Exception{
		String resu = "";
		try {
			XiGuaFenBean converyToJavaBean = JaxbUtil.converyToJavaBean(respXml, XiGuaFenBean.class);
			if(converyToJavaBean.getRetcode().equals("0000")){
				if(converyToJavaBean.getModel()!=null&&converyToJavaBean.getModel().getRetcode().equals("0000")){
					resu=converyToJavaBean.getModel().getScore();
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return resu;
	}
}
