package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoreInRecordEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    long countByExample(StoreInRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int deleteByExample(StoreInRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("terminalid") String terminalid, @Param("boxid") Integer boxid, @Param("usercardid") String usercardid, @Param("storeintime") Date storeintime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int insert(StoreInRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int insertSelective(StoreInRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    List<StoreInRecordEntity> selectByExample(StoreInRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    StoreInRecordEntity selectByPrimaryKey(@Param("terminalid") String terminalid, @Param("boxid") Integer boxid, @Param("usercardid") String usercardid, @Param("storeintime") Date storeintime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") StoreInRecordEntity record, @Param("example") StoreInRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") StoreInRecordEntity record, @Param("example") StoreInRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(StoreInRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table storeinrecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StoreInRecordEntity record);
}