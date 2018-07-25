package com.umpay.proxyservice.limit.bean;

public class LimitInfo {
	private String funcode;
	private String merid;
	private int tps;

	public LimitInfo(String funcode, String merid, int tps) {
		super();
		this.funcode = funcode;
		this.merid = merid;
		this.tps = tps;
	}

	public String getFuncode() {
		return funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public int getTps() {
		return tps;
	}

	public void setTps(int tps) {
		this.tps = tps;
	}

	@Override
	public String toString() {
		return "LimitInfo [funcode=" + funcode + ", merid=" + merid + ", tps=" + tps + "]";
	}

}
