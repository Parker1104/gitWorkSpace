package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity;

public class ApppartnerTerminalEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apppartnerterminal.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apppartnerterminal.terminalID
     *
     * @mbg.generated
     */
    private String terminalid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apppartnerterminal.describe
     *
     * @mbg.generated
     */
    private String describe;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column apppartnerterminal.remarks
     *
     * @mbg.generated
     */
    private String remarks;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apppartnerterminal.user_id
     *
     * @return the value of apppartnerterminal.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apppartnerterminal.user_id
     *
     * @param userId the value for apppartnerterminal.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apppartnerterminal.terminalID
     *
     * @return the value of apppartnerterminal.terminalID
     *
     * @mbg.generated
     */
    public String getTerminalid() {
        return terminalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apppartnerterminal.terminalID
     *
     * @param terminalid the value for apppartnerterminal.terminalID
     *
     * @mbg.generated
     */
    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid == null ? null : terminalid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apppartnerterminal.describe
     *
     * @return the value of apppartnerterminal.describe
     *
     * @mbg.generated
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apppartnerterminal.describe
     *
     * @param describe the value for apppartnerterminal.describe
     *
     * @mbg.generated
     */
    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column apppartnerterminal.remarks
     *
     * @return the value of apppartnerterminal.remarks
     *
     * @mbg.generated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column apppartnerterminal.remarks
     *
     * @param remarks the value for apppartnerterminal.remarks
     *
     * @mbg.generated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}