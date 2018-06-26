package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户预定格口
*/

public class OutParamAPCustomerJBGSchedule implements java.io.Serializable
{
	public String CustomerID = ""; //用户编号(OpenID)
	public String PackageID = ""; //订单号
	public String BoxNo = ""; //箱门编号
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String Location = ""; //投递地址
	public String TradeWaterNo = ""; //交易流水号
	public int Amount; //支付费用，单位：分
	public String Detail = ""; //订单信息（订单号，租用时间，费用等）
	public String Remark = ""; //备注

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

	public String getTerminalName()
	{
		return TerminalName;
	}
	public void setTerminalName(String TerminalName)
	{
		this.TerminalName = TerminalName;
	}

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public int getAmount()
	{
		return Amount;
	}
	public void setAmount(int Amount)
	{
		this.Amount = Amount;
	}

	public String getDetail()
	{
		return Detail;
	}
	public void setDetail(String Detail)
	{
		this.Detail = Detail;
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