package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 消费金额计算
*/

public class InParamAPCustomerConsumeAmt implements java.io.Serializable
{
	public String FunctionID = "651016"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String TerminalNo = ""; //设备号
	public String PackageID = ""; //订单号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651016";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651016";
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

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

}