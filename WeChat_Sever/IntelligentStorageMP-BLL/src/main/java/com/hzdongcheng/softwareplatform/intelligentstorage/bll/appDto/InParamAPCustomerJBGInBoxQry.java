package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 租箱记录查询
*/

public class InParamAPCustomerJBGInBoxQry implements java.io.Serializable
{
	public String FunctionID = "651044"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String CustomerID = ""; //用户编号(OpenID)
	public String PackageID = ""; //订单号
	public String PackageStatus = ""; //订单状态（0-正常，1-锁定，4-逾期）
	public String TerminalNo = ""; //设备号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651044";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651044";
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

	public String getPackageStatus()
	{
		return PackageStatus;
	}
	public void setPackageStatus(String PackageStatus)
	{
		this.PackageStatus = PackageStatus;
	}

	public String getTerminalNo()
	{
		return TerminalNo;
	}
	public void setTerminalNo(String TerminalNo)
	{
		this.TerminalNo = TerminalNo;
	}

}