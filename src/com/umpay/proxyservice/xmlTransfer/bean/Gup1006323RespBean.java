package com.umpay.proxyservice.xmlTransfer.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.umpay.proxyservice.xmlTransfer.bean.base.Label;
import com.umpay.proxyservice.xmlTransfer.bean.base.ResponseBean;

/**
 * 银联智慧cid评分
 * 
 * @author xuxiaojia
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class Gup1006323RespBean extends ResponseBean {

	private List<Label> Label;

	public List<Label> getLabel() {
		return Label;
	}

	public void setLabel(List<Label> label) {
		Label = label;
	}

	@Override
	public String toString() {
		return "Gup1006323RespBean [Label=" + Label + "]";
	}

}
