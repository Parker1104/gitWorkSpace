package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxDoorUsage;

public interface IBoxDoorUsage {

	Page<BoxDoorUsage> selectBoxUse(Page<BoxDoorUsage> page);
	
}
