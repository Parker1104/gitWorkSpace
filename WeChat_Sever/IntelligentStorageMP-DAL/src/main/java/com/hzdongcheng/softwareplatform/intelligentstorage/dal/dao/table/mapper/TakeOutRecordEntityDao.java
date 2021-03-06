package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntityExample;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TakeOutRecordEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    long countByExample(TakeOutRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int deleteByExample(TakeOutRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("terminalid") String terminalid, @Param("boxid") Integer boxid, @Param("usercardid") String usercardid, @Param("storeintime") Date storeintime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int insert(TakeOutRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int insertSelective(TakeOutRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    List<TakeOutRecordEntity> selectByExample(TakeOutRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    TakeOutRecordEntity selectByPrimaryKey(@Param("terminalid") String terminalid, @Param("boxid") Integer boxid, @Param("usercardid") String usercardid, @Param("storeintime") Date storeintime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TakeOutRecordEntity record, @Param("example") TakeOutRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TakeOutRecordEntity record, @Param("example") TakeOutRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TakeOutRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table takeoutrecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TakeOutRecordEntity record);
    
    @Select("SELECT ifnull(sum(Money),0) from takeoutrecord where TO_DAYS(NOW()) -TO_DAYS(takeTime) = 1")
	Object sumMoney();//昨日成交金额
}