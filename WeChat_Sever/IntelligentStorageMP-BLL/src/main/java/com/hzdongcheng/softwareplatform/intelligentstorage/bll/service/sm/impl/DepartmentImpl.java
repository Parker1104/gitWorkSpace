package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDepartment;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.DepartmentExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntityExample;


@Service
public class DepartmentImpl implements IDepartment {
	
	@Autowired
	DepartmentExDao dao;

	@Override
	public void saveOrUpdate(DepartmentEntity rol) {
		if(null == get(rol.getId())){
			dao.insert(rol);
		}else {
			dao.updateByPrimaryKey(rol);
		}
	}

	@Override
	public DepartmentEntity get(String id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public List<DepartmentEntity> findAll() {
		DepartmentEntityExample example = new DepartmentEntityExample();
		return dao.selectByExample(example);
	}

	@Override
	public void delete(String id) {
		dao.deleteByPrimaryKey(id);
	}

	@Override
	public List<DepartmentEntity> findAll(DepartmentEntityExample example) {
		return dao.selectByExample(example);
	}

	@Override
	public String findMaxChild(String pId) {		
		return dao.findMaxChild(pId);
	}
}
