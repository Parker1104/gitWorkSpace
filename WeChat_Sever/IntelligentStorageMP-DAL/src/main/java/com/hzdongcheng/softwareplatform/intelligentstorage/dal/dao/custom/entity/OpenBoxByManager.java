package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

public class OpenBoxByManager {
	private int type;
	private int[] boxid;
    private long errorCode;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int[] getBoxid() {
		return boxid;
	}
	public void setBoxid(int[] boxid) {
		this.boxid = boxid;
	}
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	
}
