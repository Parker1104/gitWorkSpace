package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

import java.util.Date;

public class OutParamCustomerOpenFailQry implements java.io.Serializable
{
	public String CustomerID = ""; //用户编号(OpenID)
	public String PackageID = ""; //订单号
	public String StoredTime; //存物时间
	public String TakeOutTime; //结算时间
	public String TerminalNo = ""; //设备号
	public String BoxNo = ""; //箱门编号
	public String TradeWaterNo = ""; //交易流水号
	public String Paystatus = "";
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public String getPackageID() {
		return PackageID;
	}
	public void setPackageID(String packageID) {
		PackageID = packageID;
	}
	public String getStoredTime() {
		return StoredTime;
	}
	public void setStoredTime(String storedTime) {
		StoredTime = storedTime;
	}
	public String getTakeOutTime() {
		return TakeOutTime;
	}
	public void setTakeOutTime(String takeOutTime) {
		TakeOutTime = takeOutTime;
	}
	public String getTerminalNo() {
		return TerminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}
	public String getBoxNo() {
		return BoxNo;
	}
	public void setBoxNo(String boxNo) {
		BoxNo = boxNo;
	}
	public String getTradeWaterNo() {
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String tradeWaterNo) {
		TradeWaterNo = tradeWaterNo;
	}
	public String getPaystatus() {
		return Paystatus;
	}
	public void setPaystatus(String paystatus) {
		Paystatus = paystatus;
	}
	
}
