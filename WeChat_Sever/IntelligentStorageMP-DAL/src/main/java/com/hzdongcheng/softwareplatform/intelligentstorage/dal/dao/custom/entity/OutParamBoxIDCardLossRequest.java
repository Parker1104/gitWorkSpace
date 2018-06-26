package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

public class OutParamBoxIDCardLossRequest {
	private String cardID;
	private String areaName;
	private String displayName;
	private String dispalyName;
	private String storeInTime;
	private float usedTime;
	private float paymentedMoney;
	private float needMoney;
	private long errorCode;
	
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDispalyName() {
		return dispalyName;
	}
	public void setDispalyName(String dispalyName) {
		this.dispalyName = dispalyName;
	}
	public String getStoreInTime() {
		return storeInTime;
	}
	public void setStoreInTime(String storeInTime) {
		this.storeInTime = storeInTime;
	}
	public float getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(float usedTime) {
		this.usedTime = usedTime;
	}
	public float getPaymentedMoney() {
		return paymentedMoney;
	}
	public void setPaymentedMoney(float paymentedMoney) {
		this.paymentedMoney = paymentedMoney;
	}
	public float getNeedMoney() {
		return needMoney;
	}
	public void setNeedMoney(float needMoney) {
		this.needMoney = needMoney;
	}
	public long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}
	
}
