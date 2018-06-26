package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;

public interface IManagerManagement {
	//管理员信息
		//添加或者更新角色
		void saveOrUpdate(ManagerEntity entity);
	
		//根据角色code获取角色信息
		ManagerEntity get(String areaCode);
	
		//根据角色code删除账号信息
		void delete(ManagerEntityExample example);
	
		//查询所有角色信息
		List<ManagerEntity> findAll();
	
		//查询所有角色信息(支持分页)
		List<ManagerEntity> findAll(Page page);
	
		//根据条件查询角色信息(支持分页)
		List<ManagerEntity> findAll(ManagerEntityExample cdt, Page page);

}
