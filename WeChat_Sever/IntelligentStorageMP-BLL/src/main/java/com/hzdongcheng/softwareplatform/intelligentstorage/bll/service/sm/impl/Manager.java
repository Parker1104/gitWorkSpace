package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IManager;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.ManagerEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.ManagerExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.ManagerEntityDao;

@Service
public class Manager implements IManager {
    @Autowired
    ManagerEntityDao managerEntityDao;
    @Autowired
    ManagerExDao managerExDao;
	@Override
	public ManagerEntity select(String areacode,String id) {		
		return managerEntityDao.selectByPrimaryKey(areacode, id);
	}
	
	public void insert(ManagerEntity managerEntity) {
		managerEntityDao.insert(managerEntity);
	}
	@Override
	public void update(ManagerEntity managerEntity) {
		managerEntityDao.updateByPrimaryKeySelective(managerEntity);
	}
	@Override
	public void delete(String managercardid, String areacode) {
		managerEntityDao.deleteByPrimaryKey(areacode, managercardid);
	}
	@Override
	public long count(ManagerEntityExample example) {
		return managerExDao.countByExample(example);
	}
	@Override
	public List<ManagerEx> findByExampleEx(ManagerEntityExample example) {
		return managerExDao.selectByExampleEx(example);
	}
}
