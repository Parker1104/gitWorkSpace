package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

public class BindingBoxEx {
    private String usercardid;
    private String idcode;
    private String username;
    private Date makedate;
    private Date endDate;
    private String displayname;
    private Integer boxid;
    private Integer binding;
	public String getUsercardid() {
		return usercardid;
	}
	public void setUsercardid(String usercardid) {
		this.usercardid = usercardid;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getMakedate() {
		return makedate;
	}
	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Integer getBinding() {
		return binding;
	}
	public void setBinding(Integer binding) {
		this.binding = binding;
	}
    
}
