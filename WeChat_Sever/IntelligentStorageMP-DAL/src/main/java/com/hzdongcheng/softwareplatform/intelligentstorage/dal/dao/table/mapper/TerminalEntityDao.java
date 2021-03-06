package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TerminalEntityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    long countByExample(TerminalEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int deleteByExample(TerminalEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String terminalid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int insert(TerminalEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int insertSelective(TerminalEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    List<TerminalEntity> selectByExample(TerminalEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    TerminalEntity selectByPrimaryKey(String terminalid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TerminalEntity record, @Param("example") TerminalEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TerminalEntity record, @Param("example") TerminalEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TerminalEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table terminal
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TerminalEntity record);
}