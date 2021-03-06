package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoxSizeEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    long countByExample(BoxSizeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int deleteByExample(BoxSizeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer boxtypecode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int insert(BoxSizeEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int insertSelective(BoxSizeEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    List<BoxSizeEntity> selectByExample(BoxSizeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    BoxSizeEntity selectByPrimaryKey(Integer boxtypecode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BoxSizeEntity record, @Param("example") BoxSizeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BoxSizeEntity record, @Param("example") BoxSizeEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BoxSizeEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table boxsize
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BoxSizeEntity record);
}