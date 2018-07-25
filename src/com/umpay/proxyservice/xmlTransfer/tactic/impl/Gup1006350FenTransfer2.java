package com.umpay.proxyservice.xmlTransfer.tactic.impl;

import com.umpay.proxyservice.util.JaxbUtil;
import com.umpay.proxyservice.xmlTransfer.bean.Gup1006350RespBean;
import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;

public class Gup1006350FenTransfer2 implements TransferResponse {
	@Override
	public String transfer(String respXml) throws Exception{
		String resu = "";
		try {
			Gup1006350RespBean converyToJavaBean = JaxbUtil.converyToJavaBean(respXml, Gup1006350RespBean.class);
			if(converyToJavaBean.getRetcode().equals("0000")){
				if(converyToJavaBean.getModel()!=null&&converyToJavaBean.getModel().getRetcode().equals("000")){
					resu=converyToJavaBean.getModel().getScore();
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return resu;
	}
}
