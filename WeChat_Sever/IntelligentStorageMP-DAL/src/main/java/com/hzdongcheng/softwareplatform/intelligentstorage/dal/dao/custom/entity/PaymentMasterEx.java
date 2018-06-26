package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntity;

public class PaymentMasterEx {

	private Integer masterid;

	private Integer chargecode;

	private Integer ordernumber;

	private Integer starttime;

	private Integer endtime;

	private Integer usedstate;

	private Integer timevalue;

	private Integer discountrate;

	private String makeopcode;

	private Date makedate;

	private Date lastmodifytime;

	private String memo;

	private DictEntity dictEntity;
	
	private DictEntity dictEntity1;
	

	public DictEntity getDictEntity1() {
		return dictEntity1;
	}

	public void setDictEntity1(DictEntity dictEntity1) {
		this.dictEntity1 = dictEntity1;
	}

	public Integer getMasterid() {
		return masterid;
	}

	public void setMasterid(Integer masterid) {
		this.masterid = masterid;
	}

	public Integer getChargecode() {
		return chargecode;
	}

	public void setChargecode(Integer chargecode) {
		this.chargecode = chargecode;
	}

	public Integer getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}

	public Integer getStarttime() {
		return starttime;
	}

	public void setStarttime(Integer starttime) {
		this.starttime = starttime;
	}

	public Integer getEndtime() {
		return endtime;
	}

	public void setEndtime(Integer endtime) {
		this.endtime = endtime;
	}

	public Integer getUsedstate() {
		return usedstate;
	}

	public void setUsedstate(Integer usedstate) {
		this.usedstate = usedstate;
	}

	public Integer getTimevalue() {
		return timevalue;
	}

	public void setTimevalue(Integer timevalue) {
		this.timevalue = timevalue;
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

	public DictEntity getDictEntity() {
		return dictEntity;
	}

	public void setDictEntity(DictEntity dictEntity) {
		this.dictEntity = dictEntity;
	}

}
