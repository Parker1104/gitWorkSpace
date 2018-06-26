package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntityExample;

/**
 * 
 * @author wenheju
 *  数据字典的接口
 */
public interface IDatadic {
	// 添加或者更新运营公司信息
	void saveOrUpdate(DictEntity dictEntity);

	// 查询所有运营公司信息
	List<DictEntity> findAll();
	
	List<DictEntity> findAll(String dictTypeCode);

	DictEntity get(String dictTypeCode, Integer dictCode);

	void delete(String dictTypeCode, Integer dictCode);

	int count(DictEntityExample example);

	List<DictEntity> findByExampleEntity(DictEntityExample example);
	
	List<DictEntity> getCardType();
	
	List<DictEntity> getChargecode();
	
	List<DictEntity> getUsedState();

}
