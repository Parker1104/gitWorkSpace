package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

public class GradeEx {
	
    private String terminalid;

    private String subdepartment;

    private Integer gradeid;

    private String enddate;

    private Byte state;

    private Integer discountrate;

    private Date makedate;

    private Date lastmodifytime;

    private String displayname;

    private Integer boxid;

    private String memo;

	public String getTerminalid() {
		return terminalid;
	}

	public void setTerminalid(String terminalid) {
		this.terminalid = terminalid;
	}

	public String getSubdepartment() {
		return subdepartment;
	}

	public void setSubdepartment(String subdepartment) {
		this.subdepartment = subdepartment;
	}

	public Integer getGradeid() {
		return gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid = gradeid;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Integer getDiscountrate() {
		return discountrate;
	}

	public void setDiscountrate(Integer discountrate) {
		this.discountrate = discountrate;
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

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Integer getBoxid() {
		return boxid;
	}

	public void setBoxid(Integer boxid) {
		this.boxid = boxid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
