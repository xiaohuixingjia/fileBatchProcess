package com.umpay.proxyservice.xmlTransfer.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.umpay.proxyservice.xmlTransfer.bean.base.Model;
import com.umpay.proxyservice.xmlTransfer.bean.base.ResponseBean;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class Gup1006350RespBean extends ResponseBean {
	private Model model;

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "ResponseBean [model=" + model + "]";
	}
}
