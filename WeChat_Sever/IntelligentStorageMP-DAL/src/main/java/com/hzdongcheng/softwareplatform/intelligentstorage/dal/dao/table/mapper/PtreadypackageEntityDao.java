package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PtreadypackageEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    long countByExample(PtreadypackageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int deleteByExample(PtreadypackageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("terminalno") String terminalno, @Param("packageid") String packageid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int insert(PtreadypackageEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int insertSelective(PtreadypackageEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    List<PtreadypackageEntity> selectByExample(PtreadypackageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    PtreadypackageEntity selectByPrimaryKey(@Param("terminalno") String terminalno, @Param("packageid") String packageid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PtreadypackageEntity record, @Param("example") PtreadypackageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PtreadypackageEntity record, @Param("example") PtreadypackageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PtreadypackageEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackage
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PtreadypackageEntity record);
}