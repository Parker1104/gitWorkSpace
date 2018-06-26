package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample;

public interface IBusinessModel {

	void add(BusinessModelEntity business);

	void update(BusinessModelEntity business);

	void delete(int code);

	BusinessModelEntity get(int code, String name);
	
	List<BusinessModelEntity> findAll();

	List<BusinessModelEntity> findByExample(BusinessModelEntityExample example);

	long count(BusinessModelEntityExample example);

	List<BusinessModelEntity> findAll(BusinessModelEntityExample example);
	
	BusinessModelEntity findLastInsertOne();

	List<BusinessModelEntity> find(Integer code);
	/**
	 * 删除业务类型
	 * @param businesscode
	 * @param configname
	 */
	void deleteModel(Integer businesscode,String configname);
	
	List<BusinessModelEntity> findAllByGroup();
}
