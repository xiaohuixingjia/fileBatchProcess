package com.umpay.proxyservice.xmlTransfer.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Label {
	private String id;
	private List<LabelValue> value;

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<LabelValue> getValue() {
		return value;
	}

	public void setValue(List<LabelValue> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Label [id=" + id + ", value=" + value + "]";
	}

}
