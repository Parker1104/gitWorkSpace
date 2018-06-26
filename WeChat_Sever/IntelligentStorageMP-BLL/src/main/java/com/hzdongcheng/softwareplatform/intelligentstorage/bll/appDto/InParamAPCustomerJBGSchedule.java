package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户预定格口
*/

public class InParamAPCustomerJBGSchedule implements java.io.Serializable
{
	public String FunctionID = "651031"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String TerminalNo = ""; //设备号
	public String BoxNo = ""; //箱门编号
	public String PackageID = ""; //订单号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651031";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651031";
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

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
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