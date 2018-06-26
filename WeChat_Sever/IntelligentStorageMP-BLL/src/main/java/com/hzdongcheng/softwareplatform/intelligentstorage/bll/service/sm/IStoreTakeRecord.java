package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;

public interface IStoreTakeRecord {

	long count(StoreInRecordEntityExample example);

	List<StoreInRecordEx> findAll(StoreInRecordEntityExample example);

	Object yesterdayCount();

	Object todayCount();//今天箱门使用次数

	void insert(StoreInRecordEntity storeInRecordEntity);//箱体预留登记

	Page<StoreInRecordEx> selectList(Page<StoreInRecordEx> page);

		
}
