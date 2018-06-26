package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CardTransRuleEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    long countByExample(CardTransRuleEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int deleteByExample(CardTransRuleEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer transrulecode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int insert(CardTransRuleEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int insertSelective(CardTransRuleEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    List<CardTransRuleEntity> selectByExample(CardTransRuleEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    CardTransRuleEntity selectByPrimaryKey(Integer transrulecode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CardTransRuleEntity record, @Param("example") CardTransRuleEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CardTransRuleEntity record, @Param("example") CardTransRuleEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CardTransRuleEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cardtransrule
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CardTransRuleEntity record);
}