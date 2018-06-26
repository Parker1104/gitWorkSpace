package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.Date;
import java.util.List;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PunishRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;

public interface IPunishRecord {
	
	void saveOrUpdate(PunishRecordEntity punishRecordEntity);
	
	PunishRecordEntity get(String terminalid, Integer boxid, String usercardid, Date storeintime, Date enddate);
 
	void delete(String terminalid, Integer boxid,  String usercardid, Date storeintime, Date enddate);

	List<PunishRecordEntity> findAll();

	int count(PunishRecordEntityExample example);

	List<PunishRecordEntity> findByExampleEntity(PunishRecordEntityExample example);

	List<PunishRecordEx> findByExampleEx(PunishRecordEntityExample example);
	
	void dischargePunishStatus(String terminalid, Integer boxid, String usercardid, Date storeintime);

	Page<PunishRecordEx> selectList(Page<PunishRecordEx> page);
}
