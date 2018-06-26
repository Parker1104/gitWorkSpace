package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 格口租金标准查询
*/

public class InParamAPCustomerBoxHireAmtQry implements java.io.Serializable
{
	public String FunctionID = "651001"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String BoxNo = ""; //箱门编号
	public String TerminalNo = ""; //设备号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651001";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651001";
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

	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
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