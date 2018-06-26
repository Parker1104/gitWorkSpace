package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import java.util.Date;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;

public class CardAndBoxBoundEx {
	    /**
	     *
	     * This field was generated by MyBatis Generator.
	     * This field corresponds to the database column cardandboxbound.CardID
	     *
	     * @mbg.generated
	     */
	    private String cardid;
	    private TerminalEntity terminalEntity;
	    private BoxEntity boxEntity;
        private AccountEntity accountEntity;
        private String displayname;
        
	    public String getDisplayname() {
			return displayname;
		}

		public void setDisplayname(String displayname) {
			this.displayname = displayname;
		}

		public TerminalEntity getTerminalEntity() {
			return terminalEntity;
		}

		public void setTerminalEntity(TerminalEntity terminalEntity) {
			this.terminalEntity = terminalEntity;
		}

		public BoxEntity getBoxEntity() {
			return boxEntity;
		}

		public void setBoxEntity(BoxEntity boxEntity) {
			this.boxEntity = boxEntity;
		}

		public AccountEntity getAccountEntity() {
			return accountEntity;
		}

		public void setAccountEntity(AccountEntity accountEntity) {
			this.accountEntity = accountEntity;
		}

		/**
	     *
	     * This field was generated by MyBatis Generator.
	     * This field corresponds to the database column cardandboxbound.terminalID
	     *
	     * @mbg.generated
	     */
	    private String terminalid;

	    /**
	     *
	     * This field was generated by MyBatis Generator.
	     * This field corresponds to the database column cardandboxbound.boxID
	     *
	     * @mbg.generated
	     */
	    private Integer boxid;

	    /**
	     *
	     * This field was generated by MyBatis Generator.
	     * This field corresponds to the database column cardandboxbound.makeOpCode
	     *
	     * @mbg.generated
	     */
	    private String makeopcode;

	    /**
	     *
	     * This field was generated by MyBatis Generator.
	     * This field corresponds to the database column cardandboxbound.makeDate
	     *
	     * @mbg.generated
	     */
	    private Date makedate;

	    /**
	     *
	     * This field was generated by MyBatis Generator.
	     * This field corresponds to the database column cardandboxbound.lastModifyTime
	     *
	     * @mbg.generated
	     */
	    private Date lastmodifytime;
	    private byte sync;
	    

	    public byte getSync() {
			return sync;
		}

		public void setSync(byte sync) {
			this.sync = sync;
		}

		/**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column cardandboxbound.CardID
	     *
	     * @return the value of cardandboxbound.CardID
	     *
	     * @mbg.generated
	     */
	    public String getCardid() {
	        return cardid;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column cardandboxbound.CardID
	     *
	     * @param cardid the value for cardandboxbound.CardID
	     *
	     * @mbg.generated
	     */
	    public void setCardid(String cardid) {
	        this.cardid = cardid == null ? null : cardid.trim();
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column cardandboxbound.terminalID
	     *
	     * @return the value of cardandboxbound.terminalID
	     *
	     * @mbg.generated
	     */
	    public String getTerminalid() {
	        return terminalid;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column cardandboxbound.terminalID
	     *
	     * @param terminalid the value for cardandboxbound.terminalID
	     *
	     * @mbg.generated
	     */
	    public void setTerminalid(String terminalid) {
	        this.terminalid = terminalid == null ? null : terminalid.trim();
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column cardandboxbound.boxID
	     *
	     * @return the value of cardandboxbound.boxID
	     *
	     * @mbg.generated
	     */
	    public Integer getBoxid() {
	        return boxid;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column cardandboxbound.boxID
	     *
	     * @param boxid the value for cardandboxbound.boxID
	     *
	     * @mbg.generated
	     */
	    public void setBoxid(Integer boxid) {
	        this.boxid = boxid;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column cardandboxbound.makeOpCode
	     *
	     * @return the value of cardandboxbound.makeOpCode
	     *
	     * @mbg.generated
	     */
	    public String getMakeopcode() {
	        return makeopcode;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column cardandboxbound.makeOpCode
	     *
	     * @param makeopcode the value for cardandboxbound.makeOpCode
	     *
	     * @mbg.generated
	     */
	    public void setMakeopcode(String makeopcode) {
	        this.makeopcode = makeopcode == null ? null : makeopcode.trim();
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column cardandboxbound.makeDate
	     *
	     * @return the value of cardandboxbound.makeDate
	     *
	     * @mbg.generated
	     */
	    public Date getMakedate() {
	        return makedate;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column cardandboxbound.makeDate
	     *
	     * @param makedate the value for cardandboxbound.makeDate
	     *
	     * @mbg.generated
	     */
	    public void setMakedate(Date makedate) {
	        this.makedate = makedate;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column cardandboxbound.lastModifyTime
	     *
	     * @return the value of cardandboxbound.lastModifyTime
	     *
	     * @mbg.generated
	     */
	    public Date getLastmodifytime() {
	        return lastmodifytime;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column cardandboxbound.lastModifyTime
	     *
	     * @param lastmodifytime the value for cardandboxbound.lastModifyTime
	     *
	     * @mbg.generated
	     */
	    public void setLastmodifytime(Date lastmodifytime) {
	        this.lastmodifytime = lastmodifytime;
	    }
}
