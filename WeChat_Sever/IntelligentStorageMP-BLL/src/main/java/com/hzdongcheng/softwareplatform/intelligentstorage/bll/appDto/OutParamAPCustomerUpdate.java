package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户信息更新
*/

public class OutParamAPCustomerUpdate implements java.io.Serializable
{
	public String CustomerID = ""; //用户编号(OpenID)
	public String CustomerName = ""; //用户姓名
	public String CustomerStatus = ""; //客户状态（0-正常，1-注销，2-注册未激活）
	public String CustomerMobile = ""; //用户手机
	public String BindMobile = ""; //绑定手机号
	public String UnionID = ""; //用户唯一编号
	public String TerminalNo = ""; //设备编号
	public String TerminalName = ""; //设备名称
	public String Location = ""; //设备安装地址
	public int Balance; //账户余额，单位：分。小于0，表示有欠费
	public String Remark = ""; //备注
	public String CreateTime = ""; //注册或关注时间（yyyy-MM-dd
	public String LastModifyTime = ""; //最后修改时间（yyyy-MM-dd

	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String CustomerID)
	{
		this.CustomerID = CustomerID;
	}

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String CustomerName)
	{
		this.CustomerName = CustomerName;
	}

	public String getCustomerStatus()
	{
		return CustomerStatus;
	}
	public void setCustomerStatus(String CustomerStatus)
	{
		this.CustomerStatus = CustomerStatus;
	}

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
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

	public int getBalance()
	{
		return Balance;
	}
	public void setBalance(int Balance)
	{
		this.Balance = Balance;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String Remark)
	{
		this.Remark = Remark;
	}

	public String getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(String CreateTime)
	{
		this.CreateTime = CreateTime;
	}

	public String getLastModifyTime()
	{
		return LastModifyTime;
	}
	public void setLastModifyTime(String LastModifyTime)
	{
		this.LastModifyTime = LastModifyTime;
	}

}