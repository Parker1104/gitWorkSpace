package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompanyEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    long countByExample(CompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int deleteByExample(CompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String companycode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int insert(CompanyEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int insertSelective(CompanyEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    List<CompanyEntity> selectByExample(CompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    CompanyEntity selectByPrimaryKey(String companycode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CompanyEntity record, @Param("example") CompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CompanyEntity record, @Param("example") CompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CompanyEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CompanyEntity record);
}