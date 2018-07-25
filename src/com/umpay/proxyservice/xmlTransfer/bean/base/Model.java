package com.umpay.proxyservice.xmlTransfer.bean.base;

import javax.xml.bind.annotation.XmlElement;

public class Model {


	private String retcode;
	private String score;
	private String funcode;
	private String transid;
	private String datetime;
	
	@XmlElement(name="retcode")
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	@XmlElement(name="score")
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	@XmlElement(name="funcode")
	public String getFuncode() {
		return funcode;
	}
	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}
	@XmlElement(name="transid")
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	@XmlElement(name="datetime")
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
}
