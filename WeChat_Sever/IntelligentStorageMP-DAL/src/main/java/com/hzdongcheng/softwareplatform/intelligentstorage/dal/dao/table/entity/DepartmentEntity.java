package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity;

import java.util.Date;

public class DepartmentEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.department
     *
     * @mbg.generated
     */
    private String department;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.makedate
     *
     * @mbg.generated
     */
    private Date makedate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.makeopcode
     *
     * @mbg.generated
     */
    private String makeopcode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.memo
     *
     * @mbg.generated
     */
    private String memo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.id
     *
     * @return the value of department.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.id
     *
     * @param id the value for department.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.department
     *
     * @return the value of department.department
     *
     * @mbg.generated
     */
    public String getDepartment() {
        return department;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.department
     *
     * @param department the value for department.department
     *
     * @mbg.generated
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.makedate
     *
     * @return the value of department.makedate
     *
     * @mbg.generated
     */
    public Date getMakedate() {
        return makedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.makedate
     *
     * @param makedate the value for department.makedate
     *
     * @mbg.generated
     */
    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.makeopcode
     *
     * @return the value of department.makeopcode
     *
     * @mbg.generated
     */
    public String getMakeopcode() {
        return makeopcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.makeopcode
     *
     * @param makeopcode the value for department.makeopcode
     *
     * @mbg.generated
     */
    public void setMakeopcode(String makeopcode) {
        this.makeopcode = makeopcode == null ? null : makeopcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.memo
     *
     * @return the value of department.memo
     *
     * @mbg.generated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.memo
     *
     * @param memo the value for department.memo
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}