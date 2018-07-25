package com.umpay.proxyservice.xmlTransfer.bean.base;

import javax.xml.bind.annotation.XmlElement;

public class SimpData {
	
	private String data;

	@XmlElement(name = "data")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DataResp [data=" + data + "]";
	}

}
