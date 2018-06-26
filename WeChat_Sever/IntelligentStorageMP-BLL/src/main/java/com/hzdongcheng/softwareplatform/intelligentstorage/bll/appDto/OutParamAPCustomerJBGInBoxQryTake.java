package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 租箱记录查询 取
*/

public class OutParamAPCustomerJBGInBoxQryTake implements java.io.Serializable
{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -1546920685576952182L;
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String Location = ""; //投递地址
	public String BoxNo = ""; //箱门编号
	public String PackageID = ""; //订单号
	public String PackageStatus = ""; //订单状态（0-正常，1-锁定，4-逾期）
	public String PackageStatusName = ""; //订单状态
	public String TradeWaterNo = ""; //交易流水号
	public String StoredTime = ""; //租箱时间
	public String StoredDate = ""; //租箱日期
	public String ExpiredTime = ""; //到期时间
	public String TakedTime = ""; //退租时间
	public String CustomerID = ""; //会员编号
	public String CustomerMobile = ""; //会员手机
	public String CustomerName = ""; //会员姓名
	public int HireHours; //租用时间（小时）
	public int HireAmt; //格口租金（分)
	public int PayedAmt; //已付金额（分)
	public String Desc = ""; //租用详情描述
	public  int FlagNo; //0：已支付 并存开箱   1：已支付 并未存开箱
	
 
	public int getFlagNo() {
		return FlagNo;
	}
	public void setFlagNo(int flagNo) {
		FlagNo = flagNo;
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

	public String getPackageStatus()
	{
		return PackageStatus;
	}
	public void setPackageStatus(String PackageStatus)
	{
		this.PackageStatus = PackageStatus;
	}

	public String getPackageStatusName()
	{
		return PackageStatusName;
	}
	public void setPackageStatusName(String PackageStatusName)
	{
		this.PackageStatusName = PackageStatusName;
	}

	public String getTradeWaterNo()
	{
		return TradeWaterNo;
	}
	public void setTradeWaterNo(String TradeWaterNo)
	{
		this.TradeWaterNo = TradeWaterNo;
	}

	public String getStoredTime()
	{
		return StoredTime;
	}
	public void setStoredTime(String StoredTime)
	{
		this.StoredTime = StoredTime;
	}

	public String getStoredDate()
	{
		return StoredDate;
	}
	public void setStoredDate(String StoredDate)
	{
		this.StoredDate = StoredDate;
	}

	public String getExpiredTime()
	{
		return ExpiredTime;
	}
	public void setExpiredTime(String ExpiredTime)
	{
		this.ExpiredTime = ExpiredTime;
	}

	public String getTakedTime()
	{
		return TakedTime;
	}
	public void setTakedTime(String TakedTime)
	{
		this.TakedTime = TakedTime;
	}

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String CustomerName)
	{
		this.CustomerName = CustomerName;
	}

	public int getHireHours()
	{
		return HireHours;
	}
	public void setHireHours(int HireHours)
	{
		this.HireHours = HireHours;
	}

	public int getHireAmt()
	{
		return HireAmt;
	}
	public void setHireAmt(int HireAmt)
	{
		this.HireAmt = HireAmt;
	}

	public int getPayedAmt()
	{
		return PayedAmt;
	}
	public void setPayedAmt(int PayedAmt)
	{
		this.PayedAmt = PayedAmt;
	}

	public String getDesc()
	{
		return Desc;
	}
	public void setDesc(String Desc)
	{
		this.Desc = Desc;
	}

}