package com.hzdongcheng.softwareplatform.controller.apptest.wstest;

/**
*   支付宝小程序回调
*/

public class OutParamZfbPayBack implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String  openId = "";         //用户唯一标识
	public String  tradewaterno = "";   //交易流水号 微信使用
	public int     paymentes;           //预支付费用，单位：分
	public int     servicepaystatus;    //服务器处理支付状态        0:支付处理成功，1:支付处理失败
 
	public String  description = "";    //支付回调处理详情描述
	public String  remark = "";         //备注
	
	
	public int     servicetype;         //服务类型  1:微信小程序 2:支付宝小程序 3:微信公众号 4:支付宝生活号 
	public String  appIds  ;            //对应上面的服务类型     应用appId  
	
	
 
	public int getServicetype() {
		return servicetype;
	}
	public void setServicetype(int servicetype) {
		this.servicetype = servicetype;
	}
	public String getAppIds() {
		return appIds;
	}
	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTradewaterno() {
		return tradewaterno;
	}
	public void setTradewaterno(String tradewaterno) {
		this.tradewaterno = tradewaterno;
	}
	public int getPaymentes() {
		return paymentes;
	}
	public void setPaymentes(int paymentes) {
		this.paymentes = paymentes;
	}
	public int getServicepaystatus() {
		return servicepaystatus;
	}
	public void setServicepaystatus(int servicepaystatus) {
		this.servicepaystatus = servicepaystatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
 
	
	
	
	
	
}



















