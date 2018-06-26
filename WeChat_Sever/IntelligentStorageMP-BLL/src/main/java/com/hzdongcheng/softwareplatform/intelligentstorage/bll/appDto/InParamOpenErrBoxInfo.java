package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

public class InParamOpenErrBoxInfo implements java.io.Serializable{
	public String CustomerID = ""; //用户编号(OpenID)
	public String PackageID = ""; //订单号
	public String BoxNo = ""; //箱门编号
	public String TerminalNo = ""; //设备号
	
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
	public String getBoxNo() {
		return BoxNo;
	}
	public void setBoxNo(String boxNo) {
		BoxNo = boxNo;
	}
	public String getTerminalNo() {
		return TerminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}
}