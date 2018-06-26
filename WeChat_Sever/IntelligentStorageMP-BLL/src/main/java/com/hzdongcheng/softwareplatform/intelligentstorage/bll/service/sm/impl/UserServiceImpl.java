package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.UserEntityDao;

@Service
public class UserServiceImpl implements IUserService {
	
    @Autowired
    UserEntityDao userEntityDao;

	@Override
	public void save(UserEntity userEntity) {
		userEntityDao.insertSelective(userEntity);
	}

	@Override
	public List<UserEntity> findAll() {
		UserEntityExample example = new UserEntityExample();
		return userEntityDao.selectByExample(example);
	}

	@Override
	public void delete(String id) {
		userEntityDao.deleteByPrimaryKey(id);
	}

	@Override
	public void update(UserEntity userEntity) {
		userEntityDao.updateByPrimaryKeySelective(userEntity);
	}

	@Override
	public UserEntity select(String id) {
		return userEntityDao.selectByPrimaryKey(id);
	}

	@Override
	public long count(UserEntityExample example) {
		// TODO Auto-generated method stub
		return userEntityDao.countByExample(example);
	}

	@Override
	public List<UserEntity> findAll(UserEntityExample example) {
		// TODO Auto-generated method stub
		return userEntityDao.selectByExample(example);
	}
}
