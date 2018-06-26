package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 生成预付订单
*/

public class InParamAPCustomerPrepayAmtQry implements java.io.Serializable
{
	public String FunctionID = "651014"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String BoxNo = ""; //箱门编号
	public String ChargeType = ""; //收费类型（4-按时，5-按天，6-按周，7-按月，9-按年）
	public int Num; //租用时长
	public String PackageID = ""; //订单号
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "651014";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "651014";
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

	public String getTradeWaterNo() {
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String tradeWaterNo) {
		TradeWaterNo = tradeWaterNo;
	}
	public String getBoxNo()
	{
		return BoxNo;
	}
	public void setBoxNo(String BoxNo)
	{
		this.BoxNo = BoxNo;
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

	public String getPackageID()
	{
		return PackageID;
	}
	public void setPackageID(String PackageID)
	{
		this.PackageID = PackageID;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

}