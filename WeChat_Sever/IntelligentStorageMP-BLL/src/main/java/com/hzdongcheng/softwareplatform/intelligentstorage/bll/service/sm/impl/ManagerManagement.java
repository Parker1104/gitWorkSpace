package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IManagerManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.ManagerEntityDao;

@Service
public class ManagerManagement implements IManagerManagement{
	
	@Autowired
	ManagerEntityDao  managerEntityDao;
	
	@Override
	public void saveOrUpdate(ManagerEntity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ManagerEntity get(String areaCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ManagerEntityExample example) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ManagerEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagerEntity> findAll(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagerEntity> findAll(ManagerEntityExample cdt, Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	



}
