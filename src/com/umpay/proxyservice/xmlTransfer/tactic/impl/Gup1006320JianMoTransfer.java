package com.umpay.proxyservice.xmlTransfer.tactic.impl;

import com.umpay.proxyservice.util.JaxbUtil;
import com.umpay.proxyservice.xmlTransfer.bean.Gup1006320RespBean;
import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;

public class Gup1006320JianMoTransfer implements TransferResponse {
	@Override
	public String transfer(String respXml) throws Exception{
		String resu = "";
		try {
			Gup1006320RespBean converyToJavaBean = JaxbUtil.converyToJavaBean(respXml, Gup1006320RespBean.class);
			if(converyToJavaBean.getRetcode().equals("0000")){
				if(converyToJavaBean.getPro00113347()!=null){
					resu=converyToJavaBean.getPro00113347().getData();
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return resu;
	}
}
