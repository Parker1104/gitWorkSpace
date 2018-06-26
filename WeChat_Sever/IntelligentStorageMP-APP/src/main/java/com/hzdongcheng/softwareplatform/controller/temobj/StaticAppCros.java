package com.hzdongcheng.softwareplatform.controller.temobj;

public class StaticAppCros {
	public String  terminalId;
	public String  startTime;
	public String  overTime;
	public String  wxMoney;
	public String  coinMoney;
	public String  countMoney;

	public StaticAppCros(String terminalId, String startTime, String overTime, String wxMoney, String coinMoney,
			String countMoney) {
		super();
		this.terminalId = terminalId;
		this.startTime = startTime;
		this.overTime = overTime;
		this.wxMoney = wxMoney;
		this.coinMoney = coinMoney;
		this.countMoney = countMoney;
	}


	public String getTerminalId() {
		return terminalId;
	}


	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getOverTime() {
		return overTime;
	}


	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}


	public String getWxMoney() {
		return wxMoney;
	}


	public void setWxMoney(String wxMoney) {
		this.wxMoney = wxMoney;
	}


	public String getCoinMoney() {
		return coinMoney;
	}


	public void setCoinMoney(String coinMoney) {
		this.coinMoney = coinMoney;
	}


	public String getCountMoney() {
		return countMoney;
	}


	public void setCountMoney(String countMoney) {
		this.countMoney = countMoney;
	}
 
	
}
