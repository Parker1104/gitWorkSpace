package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

public class StateCashierStatistic {	
	
	private String type;
	private String action;
	private String unit;
	private float sumprice;
	private int incount; 
	private float inmoney;	
	private String makeopcode;
	private String accountname;
	private String startdate;//开始时间
	private String enddate;//结束时间
	private Date storeintime;//存物时间
	private String cashierno;//终端号
	private String starttime;
	private String endtime;
	
	
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
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
	public Date getStoreintime() {
		return storeintime;
	}
	public void setStoreintime(Date storeintime) {
		this.storeintime = storeintime;
	}
	public String getCashierno() {
		return cashierno;
	}
	public void setCashierno(String cashierno) {
		this.cashierno = cashierno;
	}
	public String getMakeopcode() {
		return makeopcode;
	}
	public void setMakeopcode(String makeopcode) {
		this.makeopcode = makeopcode;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public int getIncount() {
		return incount;
	}
	public void setIncount(int incount) {
		this.incount = incount;
	}
	public float getInmoney() {
		return inmoney;
	}
	public void setInmoney(float inmoney) {
		this.inmoney = inmoney;
	}		
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public float getSumprice() {
		return sumprice;
	}
	public void setSumprice(float sumprice) {
		this.sumprice = sumprice;
	}
	
}
