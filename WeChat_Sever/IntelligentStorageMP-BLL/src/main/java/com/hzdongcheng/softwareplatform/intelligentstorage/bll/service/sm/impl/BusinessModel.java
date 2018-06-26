package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBusinessModel;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample;

@Service
public class BusinessModel implements IBusinessModel {
    @Autowired
    BusinessModelEntityExDao dao;
    
	@Override
	public List<BusinessModelEntity> findAll() {
		BusinessModelEntityExample example = new BusinessModelEntityExample();
		return dao.selectByExample(example);
	}
	
	@Override
	public void add(BusinessModelEntity business) {
		dao.insertSelective(business);
	}
	@Override
	public BusinessModelEntity get(int code, String name) {
		BusinessModelEntity b = dao.selectByPrimaryKey(code, name);
		return b;
	}
	@Override
	public void update(BusinessModelEntity business) {
		dao.updateByPrimaryKey(business);		
	}
	@Override
	public void delete(int businessCode) {
		BusinessModelEntityExample example = new BusinessModelEntityExample();
		example.createCriteria().andBusinesscodeEqualTo(businessCode);
		dao.deleteByExample(example);
	}
	
	@Override
	public BusinessModelEntity findLastInsertOne() {
		return dao.getLastInsert();
	}
	@Override
	public List<BusinessModelEntity> findByExample(BusinessModelEntityExample example) {		
		return dao.selectByExample(example);
	}

	@Override
	public long count(BusinessModelEntityExample example) {
		// TODO Auto-generated method stub
		return dao.countByExample(example);
	}

	@Override
	public List<BusinessModelEntity> findAll(BusinessModelEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}

	@Override
	public List<BusinessModelEntity> find(Integer code) {
		// TODO Auto-generated method stub
		return dao.selectByKey(code);
	}

	@Override
	public void deleteModel(Integer businesscode, String configname) {
		dao.deleteByPrimaryKey(businesscode, configname);		
	}

	@Override
	public List<BusinessModelEntity> findAllByGroup() {
		// TODO Auto-generated method stub
		return dao.selectAllByGroup();
	}
	
}
