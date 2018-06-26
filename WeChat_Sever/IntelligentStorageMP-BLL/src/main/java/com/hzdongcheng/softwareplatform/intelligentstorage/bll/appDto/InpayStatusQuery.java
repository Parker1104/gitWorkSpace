package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

public class InpayStatusQuery {
	public String terminalno ="";
	public String boxno ="";
	public Byte openstatus;
	public String starttime ="";
	public String overtime ="";
	public String getTerminalno() {
		return terminalno;
	}
	public void setTerminalno(String terminalno) {
		this.terminalno = terminalno;
	}
	public String getBoxno() {
		return boxno;
	}
	public void setBoxno(String boxno) {
		this.boxno = boxno;
	}
	public Byte getOpenstatus() {
		return openstatus;
	}
	public void setOpenstatus(Byte openstatus) {
		this.openstatus = openstatus;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getOvertime() {
		return overtime;
	}
	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}
}
