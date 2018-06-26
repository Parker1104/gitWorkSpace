package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

public class InParamAPCustomerPayBack {
	public String TradeWaterNo = ""; //订单流水号
	public String Amt = ""; //订单金额
	public String getTradeWaterNo() {
		return TradeWaterNo;
	}
	public void setPackageID(String tradeWaterNo) {
		TradeWaterNo = tradeWaterNo;
	}
	public String getAmt() {
		return Amt;
	}
	public void setAmt(String amt) {
		Amt = amt;
	}
}
