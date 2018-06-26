package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 远程开箱(通过柜号和箱号直接开箱，终端保存开箱记录)
*/

public class InParamAPDepositOpenBox implements java.io.Serializable
{
	public String FunctionID = "651037"; //功能编号

	public String OpenType = ""; //开箱类型 0 存 1 取
    public String OpenID   =""; //请求开箱用户ID
    public String BoxNo   = ""; //包裹编号
    public String TerminalNo = ""; //终端编号
    public java.sql.Timestamp OccurTime ;//请求时间
    public String TradeWaterNo = ""; //流水号
    public String CheckCode="";

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