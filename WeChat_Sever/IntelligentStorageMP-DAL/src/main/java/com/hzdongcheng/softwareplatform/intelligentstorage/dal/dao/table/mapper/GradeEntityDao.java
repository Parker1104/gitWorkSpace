package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GradeEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    long countByExample(GradeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int deleteByExample(GradeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("terminalid") String terminalid, @Param("subdepartment") String subdepartment);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int insert(GradeEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int insertSelective(GradeEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    List<GradeEntity> selectByExample(GradeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    GradeEntity selectByPrimaryKey(@Param("terminalid") String terminalid, @Param("subdepartment") String subdepartment);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") GradeEntity record, @Param("example") GradeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") GradeEntity record, @Param("example") GradeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GradeEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table grade
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(GradeEntity record);
}