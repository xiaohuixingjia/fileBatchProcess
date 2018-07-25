package com.umpay.proxyservice;

import java.util.List;

/**
 * 短信告警配置
 * 
 * @author xuxiaojia
 */
public class MsgWarnConfBean {
	private String warnUrl;
	private List<String> warnPhoneNumList;

	public String getWarnUrl() {
		return warnUrl;
	}

	public void setWarnUrl(String warnUrl) {
		this.warnUrl = warnUrl;
	}

	public List<String> getWarnPhoneNumList() {
		return warnPhoneNumList;
	}

	public void setWarnPhoneNumList(List<String> warnPhoneNumList) {
		this.warnPhoneNumList = warnPhoneNumList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((warnPhoneNumList == null) ? 0 : warnPhoneNumList.hashCode());
		result = prime * result + ((warnUrl == null) ? 0 : warnUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MsgWarnConfBean other = (MsgWarnConfBean) obj;
		if (warnPhoneNumList == null) {
			if (other.warnPhoneNumList != null)
				return false;
		} else if (!warnPhoneNumList.equals(other.warnPhoneNumList))
			return false;
		if (warnUrl == null) {
			if (other.warnUrl != null)
				return false;
		} else if (!warnUrl.equals(other.warnUrl))
			return false;
		return true;
	}

	public MsgWarnConfBean() {
		super();
	}

	@Override
	public String toString() {
		return "MsgWarnConfBean [warnUrl=" + warnUrl + ", warnPhoneNumList=" + warnPhoneNumList + "]";
	}

}
