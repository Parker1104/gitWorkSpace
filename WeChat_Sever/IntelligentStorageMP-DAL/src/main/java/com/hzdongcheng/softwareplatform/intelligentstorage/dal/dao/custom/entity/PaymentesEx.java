package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;


public class PaymentesEx {
	private Integer masterID;
	private Integer detailID;
	private Integer boxtypecode;
	private Integer usedState;
	
	private Integer orderNumber;
	private Integer startTime;
	private Integer endTime;
	private Integer timeValue;
 
	private Float money;
	private long errorCode;
	
	
	
	public Integer getTimeValue() {
		return timeValue;
	}
	public void setTimeValue(Integer timeValue) {
		this.timeValue = timeValue;
	}
	public Integer getUsedState() {
		return usedState;
	}
	public void setUsedState(Integer usedState) {
		this.usedState = usedState;
	}
	public Integer getMasterID() {
		return masterID;
	}
	public void setMasterID(Integer masterID) {
		this.masterID = masterID;
	}
	public Integer getDetailID() {
		return detailID;
	}
	public void setDetailID(Integer detailID) {
		this.detailID = detailID;
	}
	public Integer getBoxtypecode() {
		return boxtypecode;
	}
	public void setBoxtypecode(Integer boxtypecode) {
		this.boxtypecode = boxtypecode;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Integer getStartTime() {
		return startTime;
	}
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	public Integer getEndTime() {
		return endTime;
	}
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
 
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	 
	
}
