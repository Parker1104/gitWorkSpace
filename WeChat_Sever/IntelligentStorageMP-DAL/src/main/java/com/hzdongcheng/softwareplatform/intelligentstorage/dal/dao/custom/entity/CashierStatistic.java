package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

public class CashierStatistic {
	private String makeopcode;
	private String cashierno;
	private Date storeintime;
	private String accountname;
	private float money;
	private float realmoney;
	private float takemoney;
	private float takerealmoney;
	private String startdate;
	private String enddate;
	private String count;
	private float cancelmoney;
	public String getMakeopcode() {
		return makeopcode;
	}
	public void setMakeopcode(String makeopcode) {
		this.makeopcode = makeopcode;
	}
	public String getCashierno() {
		return cashierno;
	}
	public void setCashierno(String cashierno) {
		this.cashierno = cashierno;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public float getRealmoney() {
		return realmoney;
	}
	public void setRealmoney(float realmoney) {
		this.realmoney = realmoney;
	}
	public float getTakemoney() {
		return takemoney;
	}
	public void setTakemoney(float takemoney) {
		this.takemoney = takemoney;
	}
	public float getTakerealmoney() {
		return takerealmoney;
	}
	public void setTakerealmoney(float takerealmoney) {
		this.takerealmoney = takerealmoney;
	}
	public Date getStoreintime() {
		return storeintime;
	}
	public void setStoreintime(Date storeintime) {
		this.storeintime = storeintime;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public float getCancelmoney() {
		return cancelmoney;
	}
	public void setCancelmoney(float cancelmoney) {
		this.cancelmoney = cancelmoney;
	}
	
}
