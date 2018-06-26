package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    long countByExample(DepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int deleteByExample(DepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int insert(DepartmentEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int insertSelective(DepartmentEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    List<DepartmentEntity> selectByExample(DepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    DepartmentEntity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DepartmentEntity record, @Param("example") DepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DepartmentEntity record, @Param("example") DepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DepartmentEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DepartmentEntity record);
}