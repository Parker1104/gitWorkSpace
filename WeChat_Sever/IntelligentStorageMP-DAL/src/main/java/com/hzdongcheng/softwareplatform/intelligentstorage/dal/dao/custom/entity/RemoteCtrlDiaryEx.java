package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

public class RemoteCtrlDiaryEx {

	private String accountcode;

	private Date date;

	private String terminalid;

	private Integer boxid;

	private Integer type;

	private String content;

	private String memo;

	private AccountEx accountEntity;

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

	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}

	public Integer getBoxid() {
		return boxid;
	}

	public void setBoxid(Integer boxid) {
		this.boxid = boxid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public AccountEx getAccountEntity() {
		return accountEntity;
	}

	public void setAccountEntity(AccountEx accountEx) {
		this.accountEntity = accountEx;
	}

}
