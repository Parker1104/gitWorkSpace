package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.SystemInfoEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.SystemInfoEntityExample;

/**
 * 
 * @author wenheju
 * 系统信息  接口
 */
public interface ISystemMessage {
    //添加或者更新系统信息
	void saveOrUpdate(SystemInfoEntity systemInfoEntity);

	//根据角色code获取系统信息
	SystemInfoEntity get(String systemID);

	//根据角色code删除系统信息
	void delete(String systemID);

	//查询所有系统信息
	List<SystemInfoEntity> findAll();

	//查询所有系统信息(支持分页)
	int count(SystemInfoEntityExample example);

	List<SystemInfoEntity> findByExampleEntity(SystemInfoEntityExample example);

}
