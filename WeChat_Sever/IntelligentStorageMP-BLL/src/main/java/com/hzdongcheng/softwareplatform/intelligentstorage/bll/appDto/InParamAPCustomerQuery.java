package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户信息查询
*/

public class InParamAPCustomerQuery implements java.io.Serializable
{
	public String FunctionID = "650103"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String BindMobile = ""; //绑定手机号
	public String UnionID = ""; //用户唯一编号

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650103";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650103";
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

	public String getBindMobile()
	{
		return BindMobile;
	}
	public void setBindMobile(String BindMobile)
	{
		this.BindMobile = BindMobile;
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