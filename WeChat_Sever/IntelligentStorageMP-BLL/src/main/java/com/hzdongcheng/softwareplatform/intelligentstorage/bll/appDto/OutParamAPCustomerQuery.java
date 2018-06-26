package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户信息查询
*/

public class OutParamAPCustomerQuery implements java.io.Serializable
{
	public String CustomerID = ""; //用户编号(OpenID)
	public String CustomerName = ""; //用户姓名
	public String CustomerMobile = ""; //用户手机
	public String CustomerStatus = ""; //客户状态
	public String CustomerStatusName = ""; //客户状态名称
	public String BindMobile = ""; //绑定手机号
	public String BindCardID = ""; //绑定卡号
	public String UnionID = ""; //用户唯一编号
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String MBDeviceNo = ""; //运营方设备编号
	public String Location = ""; //设备安装地址
	public String Sex = ""; //性别:1-男，2-女，0-未知
	public String City = ""; //所属市
	public String Province = ""; //所属省直辖市
	public String Country = ""; //所在国家
	public String Language = ""; //用户的语言
	public String HeadImgUrl = ""; //用户头像
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

	public String getCustomerMobile()
	{
		return CustomerMobile;
	}
	public void setCustomerMobile(String CustomerMobile)
	{
		this.CustomerMobile = CustomerMobile;
	}

	public String getCustomerStatus()
	{
		return CustomerStatus;
	}
	public void setCustomerStatus(String CustomerStatus)
	{
		this.CustomerStatus = CustomerStatus;
	}

	public String getCustomerStatusName()
	{
		return CustomerStatusName;
	}
	public void setCustomerStatusName(String CustomerStatusName)
	{
		this.CustomerStatusName = CustomerStatusName;
	}

	public String getBindMobile()
	{
		return BindMobile;
	}
	public void setBindMobile(String BindMobile)
	{
		this.BindMobile = BindMobile;
	}

	public String getBindCardID()
	{
		return BindCardID;
	}
	public void setBindCardID(String BindCardID)
	{
		this.BindCardID = BindCardID;
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

	public String getTerminalName() {
		return TerminalName;
	}
	public void setTerminalName(String terminalName) {
		TerminalName = terminalName;
	}
	public String getMBDeviceNo()
	{
		return MBDeviceNo;
	}
	public void setMBDeviceNo(String MBDeviceNo)
	{
		this.MBDeviceNo = MBDeviceNo;
	}

	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String Location)
	{
		this.Location = Location;
	}

	public String getSex()
	{
		return Sex;
	}
	public void setSex(String Sex)
	{
		this.Sex = Sex;
	}

	public String getCity()
	{
		return City;
	}
	public void setCity(String City)
	{
		this.City = City;
	}

	public String getProvince()
	{
		return Province;
	}
	public void setProvince(String Province)
	{
		this.Province = Province;
	}

	public String getCountry()
	{
		return Country;
	}
	public void setCountry(String Country)
	{
		this.Country = Country;
	}

	public String getLanguage()
	{
		return Language;
	}
	public void setLanguage(String Language)
	{
		this.Language = Language;
	}

	public String getHeadImgUrl()
	{
		return HeadImgUrl;
	}
	public void setHeadImgUrl(String HeadImgUrl)
	{
		this.HeadImgUrl = HeadImgUrl;
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