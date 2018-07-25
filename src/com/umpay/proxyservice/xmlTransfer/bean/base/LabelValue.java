package com.umpay.proxyservice.xmlTransfer.bean.base;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class LabelValue {
	private String m;
	private String value;
	@XmlAttribute
	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}
	@XmlValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "LabelValue [m=" + m + ", value=" + value + "]";
	}
	
	

}
