package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

public class ReservedRegistration {
	private String usercardid;
	private String displayname;
	private Integer boxid;
	private Integer effectivedays;
	private String makedate;
	private String enddate;
    private String username;
	private String idcode;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getMakedate() {
		return makedate;
	}
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}
	public String getUsercardid() {
		return usercardid;
	}
	public void setUsercardid(String usercardid) {
		this.usercardid = usercardid;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public Integer getBoxid() {
		return boxid;
	}
	public void setBoxid(Integer boxid) {
		this.boxid = boxid;
	}
	public Integer getEffectivedays() {
		return effectivedays;
	}
	public void setEffectivedays(Integer effectivedays) {
		this.effectivedays = effectivedays;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	

}
