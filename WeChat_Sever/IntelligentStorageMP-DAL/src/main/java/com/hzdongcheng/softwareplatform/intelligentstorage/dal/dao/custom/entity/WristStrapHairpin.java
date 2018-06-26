package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

public class WristStrapHairpin {
	private String makeopcode;
	private String accountname;
	private String cashierno;
	private Date storeintime;
	private int sendcardcount; //发卡统计
	private int collectcardcount;
	private int notcollectcount;
	private String lossCardCount;//丢卡统计
	private String replaceCardCount;//补卡
	private String startdate;
	private String enddate;
	private float money;
	
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
	public String getCashierno() {
		return cashierno;
	}
	public void setCashierno(String cashierno) {
		this.cashierno = cashierno;
	}
	
	public Date getStoreintime() {
		return storeintime;
	}
	public void setStoreintime(Date storeintime) {
		this.storeintime = storeintime;
	}
	public int getSendcardcount() {
		return sendcardcount;
	}
	public void setSendcardcount(int sendcardcount) {
		this.sendcardcount = sendcardcount;
	}
	public int getCollectcardcount() {
		return collectcardcount;
	}
	public void setCollectcardcount(int collectcardcount) {
		this.collectcardcount = collectcardcount;
	}
	public int getNotcollectcount() {
		return notcollectcount;
	}
	public void setNotcollectcount(int notcollectcount) {
		this.notcollectcount = notcollectcount;
	}
	public String getLossCardCount() {
		return lossCardCount;
	}
	public void setLossCardCount(String lossCardCount) {
		this.lossCardCount = lossCardCount;
	}
	
	public String getReplaceCardCount() {
		return replaceCardCount;
	}
	public void setReplaceCardCount(String replaceCardCount) {
		this.replaceCardCount = replaceCardCount;
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
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	
}
