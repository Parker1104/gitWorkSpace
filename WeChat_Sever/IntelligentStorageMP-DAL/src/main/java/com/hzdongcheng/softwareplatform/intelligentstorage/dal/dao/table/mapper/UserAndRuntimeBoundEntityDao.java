package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAndRuntimeBoundEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    long countByExample(UserAndRuntimeBoundEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int deleteByExample(UserAndRuntimeBoundEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int insert(UserAndRuntimeBoundEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int insertSelective(UserAndRuntimeBoundEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    List<UserAndRuntimeBoundEntity> selectByExample(UserAndRuntimeBoundEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    UserAndRuntimeBoundEntity selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UserAndRuntimeBoundEntity record, @Param("example") UserAndRuntimeBoundEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UserAndRuntimeBoundEntity record, @Param("example") UserAndRuntimeBoundEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserAndRuntimeBoundEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userandruntimebound
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserAndRuntimeBoundEntity record);
}