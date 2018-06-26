package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 箱状态查询
*/

public class InParamAPCustomerBoxStatusQry implements java.io.Serializable
{
	public String FunctionID = "651004"; //功能编号

	public int recordBegin; 
	public int recordNum; 

	public String CustomerID = ""; //用户编号(OpenID)
	public String TerminalNo = ""; //设备号
	public String BoxType = ""; //箱类型编号
	public String BoxStatus = ""; //箱状态
	public int Paystatus; //预支付状态
	
	public int getPaystatus() {
		return Paystatus;
	}
	public void setPaystatus(int paystatus) {
		Paystatus = paystatus;
	}
	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651004";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651004";
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

	public String getBoxType()
	{
		return BoxType;
	}
	public void setBoxType(String BoxType)
	{
		this.BoxType = BoxType;
	}

	public String getBoxStatus()
	{
		return BoxStatus;
	}
	public void setBoxStatus(String BoxStatus)
	{
		this.BoxStatus = BoxStatus;
	}

}