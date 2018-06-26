package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

public class BoxDoorUsage {
	private String terminalid;
	private String displayname;
	private String totalboxnumber;
	private int count;
	private int takecount;
	private Date storeintime;
	private String startdate;
	private String enddate;
	public String getTerminalid() {
		return terminalid;
	}
	public void setTerminalidl(String terminalid) {
		this.terminalid = terminalid;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getTotalboxnumber() {
		return totalboxnumber;
	}
	public void setTotalboxnumber(String totalboxnumber) {
		this.totalboxnumber = totalboxnumber;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTakecount() {
		return takecount;
	}
	public void setTakecount(int takecount) {
		this.takecount = takecount;
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
	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}
	
}
