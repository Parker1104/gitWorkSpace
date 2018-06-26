package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackagefailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackagefailEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PtreadypackagefailEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    long countByExample(PtreadypackagefailEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int deleteByExample(PtreadypackagefailEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("terminalno") String terminalno, @Param("packageid") String packageid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int insert(PtreadypackagefailEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int insertSelective(PtreadypackagefailEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    List<PtreadypackagefailEntity> selectByExample(PtreadypackagefailEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    PtreadypackagefailEntity selectByPrimaryKey(@Param("terminalno") String terminalno, @Param("packageid") String packageid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PtreadypackagefailEntity record, @Param("example") PtreadypackagefailEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PtreadypackagefailEntity record, @Param("example") PtreadypackagefailEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PtreadypackagefailEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ptreadypackagefail
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PtreadypackagefailEntity record);
}