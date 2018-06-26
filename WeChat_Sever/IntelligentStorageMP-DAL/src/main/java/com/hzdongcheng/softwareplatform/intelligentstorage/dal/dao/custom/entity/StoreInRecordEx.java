package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;

public class StoreInRecordEx {
	private String terminalid;
	private TakeOutRecordEntity takeEntity;
	private TerminalEntity terminalEntity;
	private BoxEntity boxEntity;
	private String dispalyname;
	private Date taketime;    
	private String startdate;
	private String enddate;
	private Date storeintime;
	private String usertype;
	private Integer idtype;
	private String telephone;
	private String address;
	private String company;
	private String department;
	private String subdepartment;
	private Date effectivedays;
	private Float money;
	private Float realmoney;
	private Byte state;
	private String makeopcode;
	private String cashierno;	
	private String memo;
	
	private String idcode;
	private String displayname;
	private Date makedate;
	private Date endDate;
	private Integer binding;
    private String usercardid;
    private String username;
    private Integer boxid;

    public String getCashierno() {
		return cashierno;
	}

	public void setCashierno(String cashierno) {
		this.cashierno = cashierno;
	}
	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getUsercardid() {
		return usercardid;
	}

	public void setUsercardid(String usercardid) {
		this.usercardid = usercardid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getBoxid() {
		return boxid;
	}

	public void setBoxid(Integer boxid) {
		this.boxid = boxid;
	}
	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getBinding() {
		return binding;
	}

	public void setBinding(Integer binding) {
		this.binding = binding;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public Date getTaketime() {
		return taketime;
	}

	public void setTaketime(Date taketime) {
		this.taketime = taketime;
	}

	private String type;

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getDispalyname() {
		return dispalyname;
	}

	public void setDispalyname(String dispalyname) {
		this.dispalyname = dispalyname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BoxEntity getBoxEntity() {
		return boxEntity;
	}

	public void setBoxEntity(BoxEntity boxEntity) {
		this.boxEntity = boxEntity;
	}

	public TerminalEntity getTerminalEntity() {
		return terminalEntity;
	}

	public void setTerminalEntity(TerminalEntity terminalEntity) {
		this.terminalEntity = terminalEntity;
	}

	public TakeOutRecordEntity getTakeEntity() {
		return takeEntity;
	}

	public void setTakeEntity(TakeOutRecordEntity takeEntity) {
		this.takeEntity = takeEntity;
	}

	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid == null ? null : terminalid.trim();
	}

	public Date getStoreintime() {
		return storeintime;
	}

	public void setStoreintime(Date storeintime) {
		this.storeintime = storeintime;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype == null ? null : usertype.trim();
	}
	public Integer getIdtype() {
		return idtype;
	}

	public void setIdtype(Integer idtype) {
		this.idtype = idtype;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department == null ? null : department.trim();
	}

	public String getSubdepartment() {
		return subdepartment;
	}

	public void setSubdepartment(String subdepartment) {
		this.subdepartment = subdepartment == null ? null : subdepartment.trim();
	}

	public Date getEffectivedays() {
		return effectivedays;
	}

	public void setEffectivedays(Date effectivedays) {
		this.effectivedays = effectivedays;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Float getRealmoney() {
		return realmoney;
	}

	public void setRealmoney(Float realmoney) {
		this.realmoney = realmoney;
	}

	public Byte getState() {
		return state;
	}


	public void setState(Byte state) {
		this.state = state;
	}


	public String getMakeopcode() {
		return makeopcode;
	}


	public void setMakeopcode(String makeopcode) {
		this.makeopcode = makeopcode == null ? null : makeopcode.trim();
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}
}
