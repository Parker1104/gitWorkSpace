package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserAndRuntime;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.UserAndRuntimeBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserAndRuntimeBoundExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntityExample;

@Service
public class UserAndRuntimeImpl implements IUserAndRuntime {
	
	@Autowired
	UserAndRuntimeBoundExDao dao;

	@Override
	public long count(UserAndRuntimeBoundEntityExample example) {
		// TODO Auto-generated method stub
		return dao.countByExample(example);
	}

	@Override
	public List<UserAndRuntimeBoundEx> findAll(UserAndRuntimeBoundEntityExample example) {
		// TODO Auto-generated method stub
		return dao.findAll(example);
	}

	@Override
	public void insert(UserAndRuntimeBoundEntity entity) {
		dao.insertSelective(entity);
	}

	@Override
	public void update(UserAndRuntimeBoundEntity entity) {
		dao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public void delete(int id) {
		dao.deleteByPrimaryKey(id);
	}
}
