package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntity;

public class PaymentDetailEx {
	private Integer masterid;
	private String detailid;
	private Integer boxtypecode;
	private Float feevalue;
	private Integer discountrate;
	private String makeopcode;
	private Date makedate;
	private Date lastmodifytime;
	private String memo;
	private BoxSizeEntity boxSizeEntity;
	private PaymentMasterEntity paymentMasterEntity;

	public Integer getMasterid() {
		return masterid;
	}

	public void setMasterid(Integer masterid) {
		this.masterid = masterid;
	}

	public String getDetailid() {
		return detailid;
	}

	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}

	public Integer getBoxtypecode() {
		return boxtypecode;
	}

	public void setBoxtypecode(Integer boxtypecode) {
		this.boxtypecode = boxtypecode;
	}

	public Float getFeevalue() {
		return feevalue;
	}

	public void setFeevalue(Float feevalue) {
		this.feevalue = feevalue;
	}

	public Integer getDiscountrate() {
		return discountrate;
	}

	public void setDiscountrate(Integer discountrate) {
		this.discountrate = discountrate;
	}

	public String getMakeopcode() {
		return makeopcode;
	}

	public void setMakeopcode(String makeopcode) {
		this.makeopcode = makeopcode;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Date getLastmodifytime() {
		return lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BoxSizeEntity getBoxSizeEntity() {
		return boxSizeEntity;
	}

	public void setBoxSizeEntity(BoxSizeEntity boxSizeEntity) {
		this.boxSizeEntity = boxSizeEntity;
	}

	public PaymentMasterEntity getPaymentMasterEntity() {
		return paymentMasterEntity;
	}

	public void setPaymentMasterEntity(PaymentMasterEntity paymentMasterEntity) {
		this.paymentMasterEntity = paymentMasterEntity;
	}
}
