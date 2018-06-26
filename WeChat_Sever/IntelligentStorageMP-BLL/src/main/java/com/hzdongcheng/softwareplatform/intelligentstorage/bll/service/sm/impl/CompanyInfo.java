package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICompanyInfo;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.CompanyEntityDao;
/**
 * 
 * @author wenheju
 * 运营公司信息的实现类
 */

@Service
public class CompanyInfo implements ICompanyInfo {
	
	@Autowired
	CompanyEntityDao dao;
	
	@Override
	public void saveOrUpdate(CompanyEntity companyEntity) {
		if(null == get(companyEntity.getCompanycode())){
			dao.insert(companyEntity);
		}else {
			dao.updateByPrimaryKeySelective(companyEntity);
		}
		
	}

	@Override
	public CompanyEntity get(String companyCode) {
		return dao.selectByPrimaryKey(companyCode);
	}

	@Override
	public void delete(String companyCode) {
		dao.deleteByPrimaryKey(companyCode);
		
	}

	@Override
	public List<CompanyEntity> findAll() {
		CompanyEntityExample example = new CompanyEntityExample();
		/*example.createCriteria().andCompanycodeEqualTo("1");*/
		return dao.selectByExample(example);	
	}

	@Override
	public List<CompanyEntity> findByExampleEntity(CompanyEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}

	@Override
	public int count(CompanyEntityExample example) {
		// TODO Auto-generated method stub
		return (int) dao.countByExample(example);
	}

}
