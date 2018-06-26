package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PunishRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PunishRecordEntityDao;

public interface PunishRecordExDao extends PunishRecordEntityDao{

	List<PunishRecordEx> selectByExampleEx(PunishRecordEntityExample example);
	
    @Select("select a.terminalID, a.boxID, a.userCardID, a.storeInTime,(case a.punishState when 1 then b.takeTime when 9 then a.endDate end) as endDate, a.money, "
    		+ "a.realMoney, a.punishState, a.makeOpCode, a.makeDate "
    		+ "from punishrecord a, takeoutrecord b "
    		+ "where a.punishState != 0 and a.terminalID = #{terminalID} "
    		+ "and a.terminalID = b.terminalID  and a.boxID = b.boxID and a.userCardID = b.userCardID "
    		+ "and a.storeInTime = b.storeInTime")
	List<PunishRecordEntity> selectWhenAlreadyTakeOut(@Param("terminalID")String terminalID);
    
    @Select("select terminalID, boxID, userCardID, storeInTime,endDate, money, realMoney, punishState, makeOpCode, makeDate "
    		+ " from punishrecord a "
    		+ " where punishState = 1 and terminalID = #{terminalID} "
    		+ " and not EXISTS(select terminalID from takeoutrecord b where "
    		+ "a.terminalID = b.terminalID  and a.boxID = b.boxID and a.userCardID = b.userCardID and a.storeInTime = b.storeInTime)")
	List<PunishRecordEntity> selectInBox(@Param("terminalID")String terminalID);

	void updateByPrimaryKeySelectiveEx(PunishRecordEntity punishRecordEntity);

	List<PunishRecordEx> getList(Page<PunishRecordEx> page);

	int getCount(Page<PunishRecordEx> page); 
	
	/**
	 * 计算费用
	 */
	@Select("call sp_CalculatePaymentMoney (#{storeInTime},#{takeTime},#{boxTypeCode},#{chargeCode})")
	float cost(@Param("storeInTime")  Date storeInTime, @Param("takeTime") Date takeTime, @Param("boxTypeCode") Integer boxTypeCode,@Param("chargeCode") Integer chargeCode);
}
