package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntityExample;

public interface IMidwayTakeRecord {

	long count(MidwayTakeRecordEntityExample example);

	List<MidwayTakeRecordEntity> findAll(MidwayTakeRecordEntityExample example);
	
}
