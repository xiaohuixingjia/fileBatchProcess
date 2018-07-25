package com.umpay.proxyservice.xmlTransfer.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.umpay.proxyservice.xmlTransfer.bean.base.Label;
import com.umpay.proxyservice.xmlTransfer.bean.base.ResponseBean;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class Gcs2000002RespBean extends ResponseBean {

	private Label Label;

	public Label getLabel() {
		return Label;
	}

	@Override
	public String toString() {
		return "Gcs2000002RespBean [Label=" + Label + "]";
	}

}
