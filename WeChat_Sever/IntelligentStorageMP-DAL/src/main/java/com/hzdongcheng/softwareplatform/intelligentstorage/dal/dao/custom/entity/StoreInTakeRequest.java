package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

public class StoreInTakeRequest {
	//卡号
	private String openBoxCode;
	//姓名
	private String userName;
	//班级
	private String classid;
	//柜号
	private String displayName;
	
	public String getOpenBoxCode() {
		return openBoxCode;
	}
	public void setOpenBoxCode(String openBoxCode) {
		this.openBoxCode = openBoxCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
	
}
