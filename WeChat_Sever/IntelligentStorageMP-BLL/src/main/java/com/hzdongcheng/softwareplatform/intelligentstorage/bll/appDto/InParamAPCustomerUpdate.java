package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
* 用户信息更新
*/

public class InParamAPCustomerUpdate implements java.io.Serializable
{
	public String FunctionID = "650102"; //功能编号

	public String CustomerID = ""; //用户编号(OpenID)
	public String CustomerName = ""; //用户姓名
	public String CustomerMobile = ""; //用户手机
	public String IDCard = ""; //身份证号
	public String UnionID = ""; //用户唯一编号
	public String TerminalNo = ""; //设备号
	public String Sex = ""; //性别:1-男，2-女，0-未知
	public String City = ""; //所属市
	public String Province = ""; //所属省直辖市
	public String Country = ""; //所在国家
	public String Language = ""; //用户的语言
	public String HeadImgUrl = ""; //用户头像
	public String NickName = ""; //用户昵称
	public String Remark = ""; //备注

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650102";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650102";
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

	public String getIDCard()
	{
		return IDCard;
	}
	public void setIDCard(String IDCard)
	{
		this.IDCard = IDCard;
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

	public String getNickName()
	{
		return NickName;
	}
	public void setNickName(String NickName)
	{
		this.NickName = NickName;
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