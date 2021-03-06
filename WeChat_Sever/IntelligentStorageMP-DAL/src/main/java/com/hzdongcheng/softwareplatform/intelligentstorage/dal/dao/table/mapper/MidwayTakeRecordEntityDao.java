package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntityExample;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MidwayTakeRecordEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    long countByExample(MidwayTakeRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int deleteByExample(MidwayTakeRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("terminalid") String terminalid, @Param("boxid") Integer boxid, @Param("usercardid") String usercardid, @Param("storeintime") Date storeintime, @Param("taketime") Date taketime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int insert(MidwayTakeRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int insertSelective(MidwayTakeRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    List<MidwayTakeRecordEntity> selectByExample(MidwayTakeRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    MidwayTakeRecordEntity selectByPrimaryKey(@Param("terminalid") String terminalid, @Param("boxid") Integer boxid, @Param("usercardid") String usercardid, @Param("storeintime") Date storeintime, @Param("taketime") Date taketime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MidwayTakeRecordEntity record, @Param("example") MidwayTakeRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MidwayTakeRecordEntity record, @Param("example") MidwayTakeRecordEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MidwayTakeRecordEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table midwaytakerecord
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MidwayTakeRecordEntity record);
}