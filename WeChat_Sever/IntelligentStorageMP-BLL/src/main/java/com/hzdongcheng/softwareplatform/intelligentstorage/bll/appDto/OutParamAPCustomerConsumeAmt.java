package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 消费金额计算
*/

public class OutParamAPCustomerConsumeAmt implements java.io.Serializable
{
	public String PackageID = ""; //订单号
	public String PackageStatus = ""; //订单状态（0-正常，1-锁定，4-逾期）
	public int Amount; //待支付金额(分)
	public String StaOrderID = ""; //逾期支付单号
	public String Desc = ""; //费用详情描述
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String Location = ""; //设备安装地址
	public String StoredTime = ""; //存物时间
	public String ExpiredTime = ""; //逾期时间
	public String SysDateTime = ""; //当前时间
	public int PrepayAmt; //已支付金额（分)
	public int PreHours; //预租用时间（小时）
	public int UsedHours; //实际使用时间（小时）

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

	public int getAmount()
	{
		return Amount;
	}
	public void setAmount(int Amount)
	{
		this.Amount = Amount;
	}

	public String getStaOrderID()
	{
		return StaOrderID;
	}
	public void setStaOrderID(String StaOrderID)
	{
		this.StaOrderID = StaOrderID;
	}

	public String getDesc()
	{
		return Desc;
	}
	public void setDesc(String Desc)
	{
		this.Desc = Desc;
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

	public String getStoredTime()
	{
		return StoredTime;
	}
	public void setStoredTime(String StoredTime)
	{
		this.StoredTime = StoredTime;
	}

	public String getExpiredTime()
	{
		return ExpiredTime;
	}
	public void setExpiredTime(String ExpiredTime)
	{
		this.ExpiredTime = ExpiredTime;
	}

	public String getSysDateTime()
	{
		return SysDateTime;
	}
	public void setSysDateTime(String SysDateTime)
	{
		this.SysDateTime = SysDateTime;
	}

	public int getPrepayAmt()
	{
		return PrepayAmt;
	}
	public void setPrepayAmt(int PrepayAmt)
	{
		this.PrepayAmt = PrepayAmt;
	}

	public int getPreHours()
	{
		return PreHours;
	}
	public void setPreHours(int PreHours)
	{
		this.PreHours = PreHours;
	}

	public int getUsedHours()
	{
		return UsedHours;
	}
	public void setUsedHours(int UsedHours)
	{
		this.UsedHours = UsedHours;
	}

}