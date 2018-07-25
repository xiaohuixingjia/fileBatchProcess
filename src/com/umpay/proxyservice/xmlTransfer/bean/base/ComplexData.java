package com.umpay.proxyservice.xmlTransfer.bean.base;

import java.util.List;

public class ComplexData {
	private List<IdData> data;

	public List<IdData> getData() {
		return data;
	}

	public void setData(List<IdData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ComplexData [data=" + data + "]";
	}

	
}
