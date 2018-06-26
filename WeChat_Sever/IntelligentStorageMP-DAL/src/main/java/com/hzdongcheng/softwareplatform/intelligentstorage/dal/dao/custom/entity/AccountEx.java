package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntity;

public class AccountEx {

	private String accountcode;

	private AreaEntity areaEntity;

	private RoleEntity roleEntity;

	private String accountname;
	
	private Integer rolecode;

    private String areacode;
    
	private String password;

	private String address;

	private String telephone;

	private String email;

	private Date makedate;

	private String makeopcode;

	private String memo;

	private Date lastmodifytime;
	
	private Integer cashierflag;
	

	public Integer getRolecode() {
		return rolecode;
	}

	public void setRolecode(Integer rolecode) {
		this.rolecode = rolecode;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getAccountcode() {
		return accountcode;
	}

	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
	}

	public AreaEntity getAreaEntity() {
		return areaEntity;
	}

	public void setAreaEntity(AreaEntity areaEntity) {
		this.areaEntity = areaEntity;
	}

	public RoleEntity getRoleEntity() {
		return roleEntity;
	}

	public void setRoleEntity(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String getMakeopcode() {
		return makeopcode;
	}

	public void setMakeopcode(String makeopcode) {
		this.makeopcode = makeopcode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getLastmodifytime() {
		return lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}

	public Integer getCashierflag() {
		return cashierflag;
	}

	public void setCashierflag(Integer cashierflag) {
		this.cashierflag = cashierflag;
	}
	
}
