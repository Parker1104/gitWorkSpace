package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntityExample;


public interface IMenuService {
	//添加或者更新菜单
	void saveOrUpdate(MenuEntity menuEntity);

	//根据账号code获取菜单信息
	MenuEntity get(String menuCode);

	//根据账号code删除菜单信息
	void delete(String menuCode);

	//查询所有菜单信息
	List<MenuEntity> findAll();
	
	//查询所有菜单信息(支持分页)
	List<MenuEntity> findAll(Page page);

	//根据条件查询菜单信息(支持分页)
	List<MenuEntity> findAll(MenuEntityExample cdt, Page page);

}
