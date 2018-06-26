package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;

public class PunishRecordEx {
	
	    private String terminalid;

	    private Integer boxid;

	    private String usercardid;

	    private Date storeintime;

	    private Date enddate;

	    private Float money;

	    private Float realmoney;

	    private Byte punishstate;

	    private String makeopcode;

	    private Date makedate;
	    
	    private String username;
	    
	    private StoreInRecordEntity storeInRecordEntity;
	    
	    private TerminalEntity terminalEntity;
	    
	    private String displayname;

	    
		public String getDisplayname() {
			return displayname;
		}

		public void setDisplayname(String displayname) {
			this.displayname = displayname;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
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

		public String getUsercardid() {
			return usercardid;
		}

		public void setUsercardid(String usercardid) {
			this.usercardid = usercardid;
		}

		public Date getStoreintime() {
			return storeintime;
		}

		public void setStoreintime(Date storeintime) {
			this.storeintime = storeintime;
		}

		public Date getEnddate() {
			return enddate;
		}

		public void setEnddate(Date enddate) {
			this.enddate = enddate;
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

		public Byte getPunishstate() {
			return punishstate;
		}

		public void setPunishstate(Byte punishstate) {
			this.punishstate = punishstate;
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

		public StoreInRecordEntity getStoreInRecordEntity() {
			return storeInRecordEntity;
		}

		public void setStoreInRecordEntity(StoreInRecordEntity storeInRecordEntity) {
			this.storeInRecordEntity = storeInRecordEntity;
		}

		public TerminalEntity getTerminalEntity() {
			return terminalEntity;
		}

		public void setTerminalEntity(TerminalEntity terminalEntity) {
			this.terminalEntity = terminalEntity;
		}


}
