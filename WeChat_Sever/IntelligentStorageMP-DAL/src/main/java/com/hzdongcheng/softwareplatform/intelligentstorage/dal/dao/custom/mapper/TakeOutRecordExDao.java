package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;

public interface TakeOutRecordExDao {
    
	
	@Select("SELECT * from takeoutrecord where takeTime=(SELECT MAX(takeTime)as takeTime from takeoutrecord where userCardID=#{cardid})")
	TakeOutRecordEntity selectByCardid(@Param("cardid")String cardid);

	
	@Select("select * from takeoutrecord where taketime = (select MAX(taketime) from takeoutrecord) AND terminalId = #{terminalid} AND userCardID  = #{usercardid}")
	TakeOutRecordEntity selectMaxTakeTime(@Param("terminalid")String terminalid, @Param("usercardid")String usercardid);

   @Select("select * from takeoutrecord where taketime = (select MAX(taketime) from takeoutrecord  WHERE "
   		+ " terminalId = #{terminalId} and  boxID = #{boxid})")
   TakeOutRecordEntity selectMaxByExample(@Param("terminalId")String terminalId,@Param("boxid")Integer boxid);
	
}
