package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 解绑定合作方用户编号
*/

public class InParamAPCustomerUnBindOpenID implements java.io.Serializable
{
	public String FunctionID = "650442"; //功能编号

	public String CustomerID = ""; //用户编号
	public String UnionID = ""; //用户在合作方的编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650442";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650442";
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

	public String getUnionID()
	{
		return UnionID;
	}
	public void setUnionID(String UnionID)
	{
		this.UnionID = UnionID;
	}

}