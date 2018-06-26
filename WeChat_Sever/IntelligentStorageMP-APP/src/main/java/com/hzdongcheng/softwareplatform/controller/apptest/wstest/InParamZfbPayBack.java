package com.hzdongcheng.softwareplatform.controller.apptest.wstest;
/**
*  支付宝小程序回调
*/
public class InParamZfbPayBack   implements java.io.Serializable{
 
	private static final long serialVersionUID = 1L;
	
	public String  openId = "";         //用户唯一标识
	public String  tradewaterno = "";   //交易流水号  
	public int     paymentes;           //支付宝小程序，单位：元
	public String  remark = "";         //备注

	public int     servicetype;         //服务类型  1:微信小程序 2:支付宝小程序 3:微信公众号 4:支付宝生活号 
	public String  appIds  ;            //对应上面的服务类型     应用appId  
	public String  describes  ;         //对应上面的服务类型     描述
	
	
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}
	
	 
   
}