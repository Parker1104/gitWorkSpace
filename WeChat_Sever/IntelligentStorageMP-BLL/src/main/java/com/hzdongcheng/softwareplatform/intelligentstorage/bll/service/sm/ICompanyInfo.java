package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntityExample;

/**
 * 
 * @author wenheju
 * 运营公司的信息操作接口
 */
public interface ICompanyInfo {
	
	    //添加或者更新运营公司信息
		void saveOrUpdate(CompanyEntity companyEntity);

		//根据运营公司code获取运营公司信息
		CompanyEntity get(String companyCode);

		//根据运营公司code删除运营公司信息
		void delete(String companyCode);

		//查询所有运营公司信息
		List<CompanyEntity> findAll();

		List<CompanyEntity> findByExampleEntity(CompanyEntityExample example);

		int count(CompanyEntityExample example);


}
