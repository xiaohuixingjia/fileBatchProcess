package com.umpay.proxyservice.xmlTransfer.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.umpay.proxyservice.xmlTransfer.bean.base.ResponseBean;
import com.umpay.proxyservice.xmlTransfer.bean.base.SimpData;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class Gup1006320RespBean extends ResponseBean {
	private SimpData Pro00113347;

	public SimpData getPro00113347() {
		return Pro00113347;
	}

	public void setPro00113347(SimpData pro00113347) {
		Pro00113347 = pro00113347;
	}

}
