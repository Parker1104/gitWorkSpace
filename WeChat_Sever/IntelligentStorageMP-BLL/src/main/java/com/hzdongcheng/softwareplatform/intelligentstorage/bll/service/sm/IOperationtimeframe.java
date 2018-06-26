package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntityExample;

/**
 * 
 * @author wenheju
 *  运营时段信息接口
 */
public interface IOperationtimeframe {
	//添加或者更新运营时段信息
	void saveOrUpdate(RunTimeEntity runTimeEntity);

	//根据角色code获取运营时段信息
	RunTimeEntity get(Integer runTimeCode);

	//根据角色code删除运营时段信息
	void delete(Integer runTimeCode);

	//查询所有运营时段信息
	List<RunTimeEntity> findAll();

	//查询所有运营公司信息(支持分页)

	int count(RunTimeEntityExample example);

	List<RunTimeEntity> findByExampleEntity(RunTimeEntityExample example);


}
