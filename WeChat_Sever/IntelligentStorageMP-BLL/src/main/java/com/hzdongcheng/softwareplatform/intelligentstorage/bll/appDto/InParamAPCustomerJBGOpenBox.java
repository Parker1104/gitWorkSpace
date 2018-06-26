package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户开箱
*/

public class InParamAPCustomerJBGOpenBox implements java.io.Serializable
{
	public String FunctionID = "651037"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String PackageID = ""; //订单号
	public java.sql.Timestamp StoredTime; //存物时间
	public String TerminalNo = ""; //设备号
	public String BoxNo = ""; //箱门编号
	public String TradeWaterNo = ""; //交易流水号
	public String OpenStatus = "";//开箱状态

	public String getOpenStatus() {
		return OpenStatus;
	}
	public void setOpenStatus(String openStatus) {
		OpenStatus = openStatus;
	}
	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651037";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651037";
		else
			this.FunctionID = FunctionID;
	}

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public java.sql.Timestamp getStoredTime()
	{
		return StoredTime;
	}
	public void setStoredTime(java.sql.Timestamp StoredTime)
	{
		this.StoredTime = StoredTime;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

}