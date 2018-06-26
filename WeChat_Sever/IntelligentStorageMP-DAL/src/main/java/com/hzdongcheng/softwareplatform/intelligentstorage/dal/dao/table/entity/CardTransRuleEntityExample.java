package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity;

import java.util.ArrayList;
import java.util.List;

public class CardTransRuleEntityExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    public CardTransRuleEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
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
     * This method corresponds to the database table cardtransrule
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
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
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
     * This class corresponds to the database table cardtransrule
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

        public Criteria andTransrulecodeIsNull() {
            addCriterion("transRuleCode is null");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeIsNotNull() {
            addCriterion("transRuleCode is not null");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeEqualTo(Integer value) {
            addCriterion("transRuleCode =", value, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeNotEqualTo(Integer value) {
            addCriterion("transRuleCode <>", value, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeGreaterThan(Integer value) {
            addCriterion("transRuleCode >", value, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("transRuleCode >=", value, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeLessThan(Integer value) {
            addCriterion("transRuleCode <", value, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeLessThanOrEqualTo(Integer value) {
            addCriterion("transRuleCode <=", value, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeIn(List<Integer> values) {
            addCriterion("transRuleCode in", values, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeNotIn(List<Integer> values) {
            addCriterion("transRuleCode not in", values, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeBetween(Integer value1, Integer value2) {
            addCriterion("transRuleCode between", value1, value2, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulecodeNotBetween(Integer value1, Integer value2) {
            addCriterion("transRuleCode not between", value1, value2, "transrulecode");
            return (Criteria) this;
        }

        public Criteria andTransrulenameIsNull() {
            addCriterion("transRuleName is null");
            return (Criteria) this;
        }

        public Criteria andTransrulenameIsNotNull() {
            addCriterion("transRuleName is not null");
            return (Criteria) this;
        }

        public Criteria andTransrulenameEqualTo(String value) {
            addCriterion("transRuleName =", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameNotEqualTo(String value) {
            addCriterion("transRuleName <>", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameGreaterThan(String value) {
            addCriterion("transRuleName >", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameGreaterThanOrEqualTo(String value) {
            addCriterion("transRuleName >=", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameLessThan(String value) {
            addCriterion("transRuleName <", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameLessThanOrEqualTo(String value) {
            addCriterion("transRuleName <=", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameLike(String value) {
            addCriterion("transRuleName like", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameNotLike(String value) {
            addCriterion("transRuleName not like", value, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameIn(List<String> values) {
            addCriterion("transRuleName in", values, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameNotIn(List<String> values) {
            addCriterion("transRuleName not in", values, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameBetween(String value1, String value2) {
            addCriterion("transRuleName between", value1, value2, "transrulename");
            return (Criteria) this;
        }

        public Criteria andTransrulenameNotBetween(String value1, String value2) {
            addCriterion("transRuleName not between", value1, value2, "transrulename");
            return (Criteria) this;
        }

        public Criteria andCardtypeIsNull() {
            addCriterion("cardType is null");
            return (Criteria) this;
        }

        public Criteria andCardtypeIsNotNull() {
            addCriterion("cardType is not null");
            return (Criteria) this;
        }

        public Criteria andCardtypeEqualTo(Integer value) {
            addCriterion("cardType =", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeNotEqualTo(Integer value) {
            addCriterion("cardType <>", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeGreaterThan(Integer value) {
            addCriterion("cardType >", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("cardType >=", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeLessThan(Integer value) {
            addCriterion("cardType <", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeLessThanOrEqualTo(Integer value) {
            addCriterion("cardType <=", value, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeIn(List<Integer> values) {
            addCriterion("cardType in", values, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeNotIn(List<Integer> values) {
            addCriterion("cardType not in", values, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeBetween(Integer value1, Integer value2) {
            addCriterion("cardType between", value1, value2, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("cardType not between", value1, value2, "cardtype");
            return (Criteria) this;
        }

        public Criteria andCardlenIsNull() {
            addCriterion("cardLen is null");
            return (Criteria) this;
        }

        public Criteria andCardlenIsNotNull() {
            addCriterion("cardLen is not null");
            return (Criteria) this;
        }

        public Criteria andCardlenEqualTo(Integer value) {
            addCriterion("cardLen =", value, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenNotEqualTo(Integer value) {
            addCriterion("cardLen <>", value, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenGreaterThan(Integer value) {
            addCriterion("cardLen >", value, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenGreaterThanOrEqualTo(Integer value) {
            addCriterion("cardLen >=", value, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenLessThan(Integer value) {
            addCriterion("cardLen <", value, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenLessThanOrEqualTo(Integer value) {
            addCriterion("cardLen <=", value, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenIn(List<Integer> values) {
            addCriterion("cardLen in", values, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenNotIn(List<Integer> values) {
            addCriterion("cardLen not in", values, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenBetween(Integer value1, Integer value2) {
            addCriterion("cardLen between", value1, value2, "cardlen");
            return (Criteria) this;
        }

        public Criteria andCardlenNotBetween(Integer value1, Integer value2) {
            addCriterion("cardLen not between", value1, value2, "cardlen");
            return (Criteria) this;
        }

        public Criteria andStartsubstrIsNull() {
            addCriterion("startSubStr is null");
            return (Criteria) this;
        }

        public Criteria andStartsubstrIsNotNull() {
            addCriterion("startSubStr is not null");
            return (Criteria) this;
        }

        public Criteria andStartsubstrEqualTo(Integer value) {
            addCriterion("startSubStr =", value, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrNotEqualTo(Integer value) {
            addCriterion("startSubStr <>", value, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrGreaterThan(Integer value) {
            addCriterion("startSubStr >", value, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrGreaterThanOrEqualTo(Integer value) {
            addCriterion("startSubStr >=", value, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrLessThan(Integer value) {
            addCriterion("startSubStr <", value, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrLessThanOrEqualTo(Integer value) {
            addCriterion("startSubStr <=", value, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrIn(List<Integer> values) {
            addCriterion("startSubStr in", values, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrNotIn(List<Integer> values) {
            addCriterion("startSubStr not in", values, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrBetween(Integer value1, Integer value2) {
            addCriterion("startSubStr between", value1, value2, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andStartsubstrNotBetween(Integer value1, Integer value2) {
            addCriterion("startSubStr not between", value1, value2, "startsubstr");
            return (Criteria) this;
        }

        public Criteria andCardruleIsNull() {
            addCriterion("cardRule is null");
            return (Criteria) this;
        }

        public Criteria andCardruleIsNotNull() {
            addCriterion("cardRule is not null");
            return (Criteria) this;
        }

        public Criteria andCardruleEqualTo(Integer value) {
            addCriterion("cardRule =", value, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleNotEqualTo(Integer value) {
            addCriterion("cardRule <>", value, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleGreaterThan(Integer value) {
            addCriterion("cardRule >", value, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleGreaterThanOrEqualTo(Integer value) {
            addCriterion("cardRule >=", value, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleLessThan(Integer value) {
            addCriterion("cardRule <", value, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleLessThanOrEqualTo(Integer value) {
            addCriterion("cardRule <=", value, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleIn(List<Integer> values) {
            addCriterion("cardRule in", values, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleNotIn(List<Integer> values) {
            addCriterion("cardRule not in", values, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleBetween(Integer value1, Integer value2) {
            addCriterion("cardRule between", value1, value2, "cardrule");
            return (Criteria) this;
        }

        public Criteria andCardruleNotBetween(Integer value1, Integer value2) {
            addCriterion("cardRule not between", value1, value2, "cardrule");
            return (Criteria) this;
        }

        public Criteria andDecimalismIsNull() {
            addCriterion("decimalism is null");
            return (Criteria) this;
        }

        public Criteria andDecimalismIsNotNull() {
            addCriterion("decimalism is not null");
            return (Criteria) this;
        }

        public Criteria andDecimalismEqualTo(Integer value) {
            addCriterion("decimalism =", value, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismNotEqualTo(Integer value) {
            addCriterion("decimalism <>", value, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismGreaterThan(Integer value) {
            addCriterion("decimalism >", value, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismGreaterThanOrEqualTo(Integer value) {
            addCriterion("decimalism >=", value, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismLessThan(Integer value) {
            addCriterion("decimalism <", value, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismLessThanOrEqualTo(Integer value) {
            addCriterion("decimalism <=", value, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismIn(List<Integer> values) {
            addCriterion("decimalism in", values, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismNotIn(List<Integer> values) {
            addCriterion("decimalism not in", values, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismBetween(Integer value1, Integer value2) {
            addCriterion("decimalism between", value1, value2, "decimalism");
            return (Criteria) this;
        }

        public Criteria andDecimalismNotBetween(Integer value1, Integer value2) {
            addCriterion("decimalism not between", value1, value2, "decimalism");
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
     * This class corresponds to the database table cardtransrule
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
     * This class corresponds to the database table cardtransrule
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