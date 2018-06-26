package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxDoorUsage;

public interface BoxDoorUsageDao {

	List<BoxDoorUsage> getList(Page<BoxDoorUsage> page);

	int getCount(Page<BoxDoorUsage> page);
	
}
