package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemInfoEntityExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public SystemInfoEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSystemidIsNull() {
            addCriterion("systemID is null");
            return (Criteria) this;
        }

        public Criteria andSystemidIsNotNull() {
            addCriterion("systemID is not null");
            return (Criteria) this;
        }

        public Criteria andSystemidEqualTo(String value) {
            addCriterion("systemID =", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidNotEqualTo(String value) {
            addCriterion("systemID <>", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidGreaterThan(String value) {
            addCriterion("systemID >", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidGreaterThanOrEqualTo(String value) {
            addCriterion("systemID >=", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidLessThan(String value) {
            addCriterion("systemID <", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidLessThanOrEqualTo(String value) {
            addCriterion("systemID <=", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidLike(String value) {
            addCriterion("systemID like", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidNotLike(String value) {
            addCriterion("systemID not like", value, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidIn(List<String> values) {
            addCriterion("systemID in", values, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidNotIn(List<String> values) {
            addCriterion("systemID not in", values, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidBetween(String value1, String value2) {
            addCriterion("systemID between", value1, value2, "systemid");
            return (Criteria) this;
        }

        public Criteria andSystemidNotBetween(String value1, String value2) {
            addCriterion("systemID not between", value1, value2, "systemid");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionIsNull() {
            addCriterion("softwareVersion is null");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionIsNotNull() {
            addCriterion("softwareVersion is not null");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionEqualTo(String value) {
            addCriterion("softwareVersion =", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionNotEqualTo(String value) {
            addCriterion("softwareVersion <>", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionGreaterThan(String value) {
            addCriterion("softwareVersion >", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionGreaterThanOrEqualTo(String value) {
            addCriterion("softwareVersion >=", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionLessThan(String value) {
            addCriterion("softwareVersion <", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionLessThanOrEqualTo(String value) {
            addCriterion("softwareVersion <=", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionLike(String value) {
            addCriterion("softwareVersion like", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionNotLike(String value) {
            addCriterion("softwareVersion not like", value, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionIn(List<String> values) {
            addCriterion("softwareVersion in", values, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionNotIn(List<String> values) {
            addCriterion("softwareVersion not in", values, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionBetween(String value1, String value2) {
            addCriterion("softwareVersion between", value1, value2, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andSoftwareversionNotBetween(String value1, String value2) {
            addCriterion("softwareVersion not between", value1, value2, "softwareversion");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentIsNull() {
            addCriterion("updateContent is null");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentIsNotNull() {
            addCriterion("updateContent is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentEqualTo(String value) {
            addCriterion("updateContent =", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentNotEqualTo(String value) {
            addCriterion("updateContent <>", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentGreaterThan(String value) {
            addCriterion("updateContent >", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentGreaterThanOrEqualTo(String value) {
            addCriterion("updateContent >=", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentLessThan(String value) {
            addCriterion("updateContent <", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentLessThanOrEqualTo(String value) {
            addCriterion("updateContent <=", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentLike(String value) {
            addCriterion("updateContent like", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentNotLike(String value) {
            addCriterion("updateContent not like", value, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentIn(List<String> values) {
            addCriterion("updateContent in", values, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentNotIn(List<String> values) {
            addCriterion("updateContent not in", values, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentBetween(String value1, String value2) {
            addCriterion("updateContent between", value1, value2, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andUpdatecontentNotBetween(String value1, String value2) {
            addCriterion("updateContent not between", value1, value2, "updatecontent");
            return (Criteria) this;
        }

        public Criteria andInitpasswdIsNull() {
            addCriterion("InitPasswd is null");
            return (Criteria) this;
        }

        public Criteria andInitpasswdIsNotNull() {
            addCriterion("InitPasswd is not null");
            return (Criteria) this;
        }

        public Criteria andInitpasswdEqualTo(String value) {
            addCriterion("InitPasswd =", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdNotEqualTo(String value) {
            addCriterion("InitPasswd <>", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdGreaterThan(String value) {
            addCriterion("InitPasswd >", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdGreaterThanOrEqualTo(String value) {
            addCriterion("InitPasswd >=", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdLessThan(String value) {
            addCriterion("InitPasswd <", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdLessThanOrEqualTo(String value) {
            addCriterion("InitPasswd <=", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdLike(String value) {
            addCriterion("InitPasswd like", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdNotLike(String value) {
            addCriterion("InitPasswd not like", value, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdIn(List<String> values) {
            addCriterion("InitPasswd in", values, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdNotIn(List<String> values) {
            addCriterion("InitPasswd not in", values, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdBetween(String value1, String value2) {
            addCriterion("InitPasswd between", value1, value2, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andInitpasswdNotBetween(String value1, String value2) {
            addCriterion("InitPasswd not between", value1, value2, "initpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdIsNull() {
            addCriterion("terminalPasswd is null");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdIsNotNull() {
            addCriterion("terminalPasswd is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdEqualTo(String value) {
            addCriterion("terminalPasswd =", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdNotEqualTo(String value) {
            addCriterion("terminalPasswd <>", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdGreaterThan(String value) {
            addCriterion("terminalPasswd >", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdGreaterThanOrEqualTo(String value) {
            addCriterion("terminalPasswd >=", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdLessThan(String value) {
            addCriterion("terminalPasswd <", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdLessThanOrEqualTo(String value) {
            addCriterion("terminalPasswd <=", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdLike(String value) {
            addCriterion("terminalPasswd like", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdNotLike(String value) {
            addCriterion("terminalPasswd not like", value, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdIn(List<String> values) {
            addCriterion("terminalPasswd in", values, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdNotIn(List<String> values) {
            addCriterion("terminalPasswd not in", values, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdBetween(String value1, String value2) {
            addCriterion("terminalPasswd between", value1, value2, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andTerminalpasswdNotBetween(String value1, String value2) {
            addCriterion("terminalPasswd not between", value1, value2, "terminalpasswd");
            return (Criteria) this;
        }

        public Criteria andServersslIsNull() {
            addCriterion("serverSSL is null");
            return (Criteria) this;
        }

        public Criteria andServersslIsNotNull() {
            addCriterion("serverSSL is not null");
            return (Criteria) this;
        }

        public Criteria andServersslEqualTo(String value) {
            addCriterion("serverSSL =", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslNotEqualTo(String value) {
            addCriterion("serverSSL <>", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslGreaterThan(String value) {
            addCriterion("serverSSL >", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslGreaterThanOrEqualTo(String value) {
            addCriterion("serverSSL >=", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslLessThan(String value) {
            addCriterion("serverSSL <", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslLessThanOrEqualTo(String value) {
            addCriterion("serverSSL <=", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslLike(String value) {
            addCriterion("serverSSL like", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslNotLike(String value) {
            addCriterion("serverSSL not like", value, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslIn(List<String> values) {
            addCriterion("serverSSL in", values, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslNotIn(List<String> values) {
            addCriterion("serverSSL not in", values, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslBetween(String value1, String value2) {
            addCriterion("serverSSL between", value1, value2, "serverssl");
            return (Criteria) this;
        }

        public Criteria andServersslNotBetween(String value1, String value2) {
            addCriterion("serverSSL not between", value1, value2, "serverssl");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeIsNull() {
            addCriterion("lastModifyTime is null");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeIsNotNull() {
            addCriterion("lastModifyTime is not null");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeEqualTo(Date value) {
            addCriterion("lastModifyTime =", value, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeNotEqualTo(Date value) {
            addCriterion("lastModifyTime <>", value, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeGreaterThan(Date value) {
            addCriterion("lastModifyTime >", value, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lastModifyTime >=", value, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeLessThan(Date value) {
            addCriterion("lastModifyTime <", value, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeLessThanOrEqualTo(Date value) {
            addCriterion("lastModifyTime <=", value, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeIn(List<Date> values) {
            addCriterion("lastModifyTime in", values, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeNotIn(List<Date> values) {
            addCriterion("lastModifyTime not in", values, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeBetween(Date value1, Date value2) {
            addCriterion("lastModifyTime between", value1, value2, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andLastmodifytimeNotBetween(Date value1, Date value2) {
            addCriterion("lastModifyTime not between", value1, value2, "lastmodifytime");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("memo is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("memo is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("memo =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("memo <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("memo >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("memo >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("memo <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("memo <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("memo like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("memo not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("memo in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("memo not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("memo between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("memo not between", value1, value2, "memo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table systeminfo
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table systeminfo
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}