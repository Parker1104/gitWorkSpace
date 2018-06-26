package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.UserAndRuntimeBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntityExample;

public interface IUserAndRuntime {

	long count(UserAndRuntimeBoundEntityExample example);

	List<UserAndRuntimeBoundEx> findAll(UserAndRuntimeBoundEntityExample example);

	void insert(UserAndRuntimeBoundEntity entity);

	void update(UserAndRuntimeBoundEntity entity);

	void delete(int id);
	
}
