package com.hzdongcheng.softwareplatform.controller.apptest.wstest;

/**
*  用户登录信息
*/

public class InParamLoginInfo   implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String nickName = "";  //用户昵称
	public String avatarUrl = ""; //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	public int    gender  ;       //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	public String city = "";      //用户所在城市
	public String province = "";  //用户所在省份
	public String country = "";   //用户所在国家
	public String language = "";  //用户的语言，简体中文为zh_CN
	public String openId = "";     //用户唯一标识
	public String unionid = "";    //用户在开放平台的唯一标识符
	
	public String terminalid = ""; //设备号
	public String remark = "";     //备注
	
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
 
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
 
	public String getTerminalid() {
		return terminalid;
	}
	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
 
}