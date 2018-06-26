package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntity;

public class CardTransRuleEx {
	
    private Integer transrulecode;

    private String transrulename;

    private Integer cardtype;

    private Integer cardlen;

    private Integer startsubstr;

    private Integer cardrule;

    private Integer decimalism;

    private String memo;
    
    private DictEntity dictEntity;

	public Integer getTransrulecode() {
		return transrulecode;
	}

	public void setTransrulecode(Integer transrulecode) {
		this.transrulecode = transrulecode;
	}

	public String getTransrulename() {
		return transrulename;
	}

	public void setTransrulename(String transrulename) {
		this.transrulename = transrulename;
	}

	public Integer getCardtype() {
		return cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}

	public Integer getCardlen() {
		return cardlen;
	}

	public void setCardlen(Integer cardlen) {
		this.cardlen = cardlen;
	}

	public Integer getStartsubstr() {
		return startsubstr;
	}

	public void setStartsubstr(Integer startsubstr) {
		this.startsubstr = startsubstr;
	}

	public Integer getCardrule() {
		return cardrule;
	}

	public void setCardrule(Integer cardrule) {
		this.cardrule = cardrule;
	}

	public Integer getDecimalism() {
		return decimalism;
	}

	public void setDecimalism(Integer decimalism) {
		this.decimalism = decimalism;
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
