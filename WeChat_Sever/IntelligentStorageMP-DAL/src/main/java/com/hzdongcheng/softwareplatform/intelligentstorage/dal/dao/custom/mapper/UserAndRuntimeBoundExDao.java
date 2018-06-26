package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.UserAndRuntimeBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.UserAndRuntimeBoundEntityDao;

public interface UserAndRuntimeBoundExDao extends UserAndRuntimeBoundEntityDao {

	List<UserAndRuntimeBoundEx> findAll(UserAndRuntimeBoundEntityExample example);
	
}
