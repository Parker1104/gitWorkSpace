package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ITerminalLog;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxEntityDao;

@Service
public class TerminalLogService implements ITerminalLog{
	
	@Autowired
	BoxEntityDao boxEntityDao;
    
	@Override
	public List<BoxEntity> findAll(BoxEntityExample example) {
		// TODO Auto-generated method stub
		return boxEntityDao.selectByExample(example);
	}

	@Override
	public int count(BoxEntityExample example) {
		// TODO Auto-generated method stub
		return (int) boxEntityDao.countByExample(example);
	}

}
