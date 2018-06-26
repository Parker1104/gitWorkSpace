package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;

public class JsonResult {
	public boolean success=false;
	public String msg;
	public String data;
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}