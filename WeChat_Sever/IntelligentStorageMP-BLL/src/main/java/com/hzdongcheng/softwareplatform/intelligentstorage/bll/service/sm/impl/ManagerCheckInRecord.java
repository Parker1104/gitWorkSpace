package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IManagerCheckInRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerCheckInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.ManagerCheckInRecordEntityDao;
@Service
public class ManagerCheckInRecord implements IManagerCheckInRecord {
	@Autowired
	ManagerCheckInRecordEntityDao dao;

	@Override
	public void add(ManagerCheckInRecordEntity manager) {
		dao.insertSelective(manager);
	}
}
