package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IRoleManagementService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.RoleEntityDao;

@Service
public class RoleManagement implements IRoleManagementService{

	@Autowired
	RoleEntityDao roledao;

	@Override
	public void saveOrUpdate(RoleEntity roleEntity) {
		if(null == get(roleEntity.getRolecode())){
			roledao.insert(roleEntity);
		}else {
			roledao.updateByPrimaryKeySelective(roleEntity);
		}
		
	}

	@Override
	public RoleEntity get(Integer roleCode) {		
		return roledao.selectByPrimaryKey(roleCode);
	}

	@Override
	public void delete(Integer roleCode) {
		roledao.deleteByPrimaryKey(roleCode);
		
	}

	@Override
	public List<RoleEntity> findAll() {
		RoleEntityExample example = new RoleEntityExample();
		/*example.createCriteria().andRolecodeEqualTo(1);*/
		return roledao.selectByExample(example);	
	}

	@Override
	public List<RoleEntity> findAll(RoleEntityExample example) {
		example.createCriteria().andRolecodeNotEqualTo(0);
		return roledao.selectByExample(example);
	}

	@Override
	public int count(RoleEntityExample example) {
		return (int) roledao.countByExample(example);
	}
}
