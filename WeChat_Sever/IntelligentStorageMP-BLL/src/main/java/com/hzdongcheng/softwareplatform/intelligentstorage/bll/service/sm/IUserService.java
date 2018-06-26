package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample;

public interface IUserService {
	
	void save(UserEntity userEntity);

	List<UserEntity> findAll();

	void delete(String string);

	void update(UserEntity userEntity);

	UserEntity select(String id);

	long count(UserEntityExample example);

	List<UserEntity> findAll(UserEntityExample example);
}
