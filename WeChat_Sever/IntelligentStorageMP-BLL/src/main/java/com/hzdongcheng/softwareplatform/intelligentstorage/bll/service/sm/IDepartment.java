package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntityExample;

public interface IDepartment {

	void saveOrUpdate(DepartmentEntity rol);

	DepartmentEntity get(String id);

	List<DepartmentEntity> findAll();

	void delete(String id);

	List<DepartmentEntity> findAll(DepartmentEntityExample example);

	String findMaxChild(String pId);
	
}
