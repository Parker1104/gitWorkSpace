package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntityExample;

public interface IRoleManagementService {

	//添加或者更新角色
	void saveOrUpdate(RoleEntity roleEntity);

	//根据角色code获取角色信息
	RoleEntity get(Integer roleCode);

	//根据角色code删除账号信息
	void delete(Integer roleCode);

	//查询所有角色信息
	List<RoleEntity> findAll();

	List<RoleEntity> findAll(RoleEntityExample example);

	int count(RoleEntityExample example);
}
