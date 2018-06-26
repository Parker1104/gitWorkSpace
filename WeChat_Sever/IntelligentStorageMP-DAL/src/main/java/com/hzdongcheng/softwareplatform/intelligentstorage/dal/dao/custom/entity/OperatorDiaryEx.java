package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;

public class OperatorDiaryEx {
	
    private String accountcode;

    private Date date;

    private String modlename;

    private String description;

    private String memo;
    
    private AccountEntity accountEntity;

	public String getAccountcode() {
		return accountcode;
	}

	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getModlename() {
		return modlename;
	}

	public void setModlename(String modlename) {
		this.modlename = modlename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public AccountEntity getAccountEntity() {
		return accountEntity;
	}

	public void setAccountEntity(AccountEntity accountEntity) {
		this.accountEntity = accountEntity;
	}
      
}
