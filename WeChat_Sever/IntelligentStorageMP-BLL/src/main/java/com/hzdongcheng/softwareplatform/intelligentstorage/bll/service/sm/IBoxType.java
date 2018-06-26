package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntityExample;

/**
 * 
 * @author wenheju
 *  
 */
public interface IBoxType {
	//添加或者更新箱子信息 
	void saveOrUpdate(BoxSizeEntity boxSizeEntity);

	//根据角色code获取箱子信息 
	BoxSizeEntity get(Integer boxTypeCode);

	//根据角色code删除箱子信息 
	void delete(Integer boxTypeCode);

	//查询所有箱子信息 
	List<BoxSizeEntity> findAll();

	int count(BoxSizeEntityExample example);

	List<BoxSizeEntity> findByExampleEntity(BoxSizeEntityExample example);
}
