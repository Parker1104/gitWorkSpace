package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxDoorUsage;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxDoorUsage;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxDoorUsageDao;

@Service
public class BoxDoorUsageImpl implements IBoxDoorUsage{
	
	@Autowired
	BoxDoorUsageDao dao;

	@Override
	public Page<BoxDoorUsage> selectBoxUse(Page<BoxDoorUsage> page) {
		List<BoxDoorUsage> list = dao.getList(page);
		int count = dao.getCount(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}
}
