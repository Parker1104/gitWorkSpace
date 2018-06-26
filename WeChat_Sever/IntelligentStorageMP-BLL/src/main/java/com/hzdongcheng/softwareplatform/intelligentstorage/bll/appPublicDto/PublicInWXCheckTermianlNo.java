package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appPublicDto;

public class PublicInWXCheckTermianlNo {
	public String TerminalNo = ""; //设备号
	public String OpenId = "";	//微信openid
	public String Nickname = "";
	public int Sex;
	public String City = "";
	public String Province = "";
	public String Country = "";
	public String Language = "";
	public String HeadImgUrl = "";
	
	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getHeadImgUrl() {
		return HeadImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		HeadImgUrl = headImgUrl;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getOpenId() {
		return OpenId;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public void setOpenId(String openId) {
		OpenId = openId;
	}

	public String getTerminalNo() {
		return TerminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}
}
