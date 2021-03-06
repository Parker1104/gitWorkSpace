package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity;

import java.util.Date;

public class SystemInfoEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.systemID
     *
     * @mbg.generated
     */
    private String systemid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.softwareVersion
     *
     * @mbg.generated
     */
    private String softwareversion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.updateContent
     *
     * @mbg.generated
     */
    private String updatecontent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.InitPasswd
     *
     * @mbg.generated
     */
    private String initpasswd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.terminalPasswd
     *
     * @mbg.generated
     */
    private String terminalpasswd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.serverSSL
     *
     * @mbg.generated
     */
    private String serverssl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.lastModifyTime
     *
     * @mbg.generated
     */
    private Date lastmodifytime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column systeminfo.memo
     *
     * @mbg.generated
     */
    private String memo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.systemID
     *
     * @return the value of systeminfo.systemID
     *
     * @mbg.generated
     */
    public String getSystemid() {
        return systemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.systemID
     *
     * @param systemid the value for systeminfo.systemID
     *
     * @mbg.generated
     */
    public void setSystemid(String systemid) {
        this.systemid = systemid == null ? null : systemid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.softwareVersion
     *
     * @return the value of systeminfo.softwareVersion
     *
     * @mbg.generated
     */
    public String getSoftwareversion() {
        return softwareversion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.softwareVersion
     *
     * @param softwareversion the value for systeminfo.softwareVersion
     *
     * @mbg.generated
     */
    public void setSoftwareversion(String softwareversion) {
        this.softwareversion = softwareversion == null ? null : softwareversion.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.updateContent
     *
     * @return the value of systeminfo.updateContent
     *
     * @mbg.generated
     */
    public String getUpdatecontent() {
        return updatecontent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.updateContent
     *
     * @param updatecontent the value for systeminfo.updateContent
     *
     * @mbg.generated
     */
    public void setUpdatecontent(String updatecontent) {
        this.updatecontent = updatecontent == null ? null : updatecontent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.InitPasswd
     *
     * @return the value of systeminfo.InitPasswd
     *
     * @mbg.generated
     */
    public String getInitpasswd() {
        return initpasswd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.InitPasswd
     *
     * @param initpasswd the value for systeminfo.InitPasswd
     *
     * @mbg.generated
     */
    public void setInitpasswd(String initpasswd) {
        this.initpasswd = initpasswd == null ? null : initpasswd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.terminalPasswd
     *
     * @return the value of systeminfo.terminalPasswd
     *
     * @mbg.generated
     */
    public String getTerminalpasswd() {
        return terminalpasswd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.terminalPasswd
     *
     * @param terminalpasswd the value for systeminfo.terminalPasswd
     *
     * @mbg.generated
     */
    public void setTerminalpasswd(String terminalpasswd) {
        this.terminalpasswd = terminalpasswd == null ? null : terminalpasswd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.serverSSL
     *
     * @return the value of systeminfo.serverSSL
     *
     * @mbg.generated
     */
    public String getServerssl() {
        return serverssl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.serverSSL
     *
     * @param serverssl the value for systeminfo.serverSSL
     *
     * @mbg.generated
     */
    public void setServerssl(String serverssl) {
        this.serverssl = serverssl == null ? null : serverssl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.lastModifyTime
     *
     * @return the value of systeminfo.lastModifyTime
     *
     * @mbg.generated
     */
    public Date getLastmodifytime() {
        return lastmodifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.lastModifyTime
     *
     * @param lastmodifytime the value for systeminfo.lastModifyTime
     *
     * @mbg.generated
     */
    public void setLastmodifytime(Date lastmodifytime) {
        this.lastmodifytime = lastmodifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column systeminfo.memo
     *
     * @return the value of systeminfo.memo
     *
     * @mbg.generated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column systeminfo.memo
     *
     * @param memo the value for systeminfo.memo
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}