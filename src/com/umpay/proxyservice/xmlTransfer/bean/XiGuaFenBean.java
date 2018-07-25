package com.umpay.proxyservice.xmlTransfer.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.umpay.proxyservice.xmlTransfer.bean.base.Model;
import com.umpay.proxyservice.xmlTransfer.bean.base.ResponseBean;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
/**
 * 西瓜分的三个funcode
 * Gml2000005，10001001，q14y07ddqjhx594fdpqm弹性
 * Gml2000006，10001001，ktit53gaqmen8yiquigc弹性+
 * Gup1006315,10001001,k7049tuyi1s8efxyjq51弹性+plus
 * 
 * @author xuxiaojia
 */
public class XiGuaFenBean extends ResponseBean {
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
