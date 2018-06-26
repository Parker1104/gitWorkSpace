package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMidwayTakeRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.MidwayTakeRecordEntityDao;
@Service
public class MidwayTakeRecord implements IMidwayTakeRecord {
    @Autowired
    MidwayTakeRecordEntityDao dao;

	@Override
	public long count(MidwayTakeRecordEntityExample example) {
		// TODO Auto-generated method stub
		return dao.countByExample(example);
	}

	@Override
	public List<MidwayTakeRecordEntity> findAll(MidwayTakeRecordEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}
}
