package com.umpay.proxyservice.xmlTransfer.tactic.impl;

import org.apache.commons.collections.CollectionUtils;

import com.umpay.proxyservice.util.JaxbUtil;
import com.umpay.proxyservice.util.StringUtil;
import com.umpay.proxyservice.xmlTransfer.bean.YLZH_CID_RespBean;
import com.umpay.proxyservice.xmlTransfer.tactic.TransferResponse;

/**
 * 银联智慧 cid评分定制转换 （最大分，求和分，平均分）
 * 
 * @author xuxiaojia
 */
public class YLZH_CID_RespBeanTransfer implements TransferResponse {
	@Override
	public String transfer(String respXml) throws Exception {
		String resu = "";
		try {
			YLZH_CID_RespBean converyToJavaBean = JaxbUtil.converyToJavaBean(respXml, YLZH_CID_RespBean.class);
			if (converyToJavaBean.getRetcode().equals("0000")) {
				if (converyToJavaBean.getLabel() != null
						&& CollectionUtils.isNotEmpty(converyToJavaBean.getLabel().getValue())
						&& converyToJavaBean.getLabel().getValue().get(0) != null
						&& StringUtil.isNotEmpty(converyToJavaBean.getLabel().getValue().get(0).getValue())) {
					resu = converyToJavaBean.getLabel().getValue().get(0).getValue();
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return resu;
	}
}
