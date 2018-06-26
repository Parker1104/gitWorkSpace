package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;

import java.util.Date;

public class StoreInRequestsTemp {
	
	public boolean isovertime=false;
	public String  openBoxCode ;
	public String terminalID;
	public Integer boxid;
	public Date startTime;
 
	public StoreInRequestsTemp(String terminalID, Integer boxid,  String openBoxCode ) {
		this.openBoxCode = openBoxCode;
		this.terminalID = terminalID;
		this.boxid = boxid;
		this.startTime=new Date();
	}
	//现在默认5分钟 是超时
	public boolean isIsovertime() {
		Date currentTime=new  Date();
		Integer storein_effective_time=PropertiesUtil.storein_effective_time();
		
		boolean isovertimes=CompareTime.compare(currentTime.toLocaleString(), startTime.toLocaleString(), storein_effective_time*1000);
		return isovertimes;
	}
	/*public void setIsovertime(boolean isovertime) {
		this.isovertime = isovertime;
	}*/
	public String getOpenBoxCode() {
		return openBoxCode;
	}
	public void setOpenBoxCode(String openBoxCode) {
		this.openBoxCode = openBoxCode;
	}
	public String getTerminalID() {
		return terminalID;
	}
	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}
	public Integer getBoxid() {
		return boxid;
	}
	public void setBoxid(Integer boxid) {
		this.boxid = boxid;
	}
 
}
