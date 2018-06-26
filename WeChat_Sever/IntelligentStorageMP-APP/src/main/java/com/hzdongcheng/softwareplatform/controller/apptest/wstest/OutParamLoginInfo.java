package com.hzdongcheng.softwareplatform.controller.apptest.wstest;

/**
*  返回用户登录信息
*/

public class OutParamLoginInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String openId = "";         //用户唯一标识
	public String unionid = "";         //用户在开放平台的唯一标识符
	public String status = "";          //客户状态（0-正常，1-注销，2-注册未激活）
	public String mobiles = "";         //用户手机
	public String bindmobile = "";      //绑定手机号
	public String terminalid = "";      //设备号
	public String terminalname = "";    //设备名称
	public String location = "";        //设备安装地址
	public int    balance;              //账户余额，单位：分。小于0，表示有欠费
	public String remark = "";          //备注
	public String createtime = "";      //注册或关注时间（yyyy-MM-dd
	public String lastmodifytime = "";  //最后修改时间（yyyy-MM-dd
	
	
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getBindmobile() {
		return bindmobile;
	}
	public void setBindmobile(String bindmobile) {
		this.bindmobile = bindmobile;
	}
	public String getTerminalid() {
		return terminalid;
	}
	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}
	public String getTerminalname() {
		return terminalname;
	}
	public void setTerminalname(String terminalname) {
		this.terminalname = terminalname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastmodifytime() {
		return lastmodifytime;
	}
	public void setLastmodifytime(String lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}
	
	
	
 
}