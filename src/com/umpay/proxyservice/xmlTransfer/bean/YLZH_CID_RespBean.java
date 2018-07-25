package com.umpay.proxyservice.xmlTransfer.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.umpay.proxyservice.xmlTransfer.bean.base.Label;
import com.umpay.proxyservice.xmlTransfer.bean.base.ResponseBean;
/**
 * 银联智慧cid评分 
* @author xuxiaojia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class YLZH_CID_RespBean extends ResponseBean {

	private Label Label;

	public Label getLabel() {
		return Label;
	}

	@Override
	public String toString() {
		return "YLZH_CID_RespBean [Label=" + Label + "]";
	}

}
