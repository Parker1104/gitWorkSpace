package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户租箱（续租）
*/

public class InParamAPCustomerJBGStore implements java.io.Serializable
{
	public String FunctionID = "651033"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String TerminalNo = ""; //设备号
	public String BoxNo = ""; //箱门编号
	public String PackageID = ""; //订单号
	public String ChargeType = ""; //收费类型（4-按时，5-按天，6-按周，7-按月，9-按年）
	public int Num; //租用时长
	public String TransactionNo = ""; //交易流水号
	public int TransactionAmt; //支付金额（分)

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651033";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651033";
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

	public String getChargeType()
	{
		return ChargeType;
	}
	public void setChargeType(String ChargeType)
	{
		this.ChargeType = ChargeType;
	}

	public int getNum()
	{
		return Num;
	}
	public void setNum(int Num)
	{
		this.Num = Num;
	}

	public String getTransactionNo()
	{
		return TransactionNo;
	}
	public void setTransactionNo(String TransactionNo)
	{
		this.TransactionNo = TransactionNo;
	}

	public int getTransactionAmt()
	{
		return TransactionAmt;
	}
	public void setTransactionAmt(int TransactionAmt)
	{
		this.TransactionAmt = TransactionAmt;
	}

}