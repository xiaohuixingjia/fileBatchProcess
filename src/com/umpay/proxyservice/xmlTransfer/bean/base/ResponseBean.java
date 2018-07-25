package com.umpay.proxyservice.xmlTransfer.bean.base;

import javax.xml.bind.annotation.XmlElement;

public abstract class ResponseBean {
	private String sign;
	private String retcode;
	private String funcode;
	private String datetime;
	private String transid;

	@XmlElement(name="sign")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@XmlElement(name="retcode")
	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	@XmlElement(name="funcode")
	public String getFuncode() {
		return funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	@XmlElement(name="datetime")
	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	@XmlElement(name="transid")
	public String getTransid() {
		return transid;
	}

	public void setTransid(String transid) {
		this.transid = transid;
	}

	@Override
	public String toString() {
		return "ResponseBean [sign=" + sign + ", retcode=" + retcode + ", funcode=" + funcode + ", datetime=" + datetime
				+ ", transid=" + transid + "]";
	}

	
}
