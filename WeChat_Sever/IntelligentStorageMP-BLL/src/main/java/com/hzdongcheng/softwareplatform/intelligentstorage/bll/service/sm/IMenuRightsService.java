package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.MenuRightsEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuRightsEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuRightsEntityExample;

public interface IMenuRightsService {
	 //添加或者更新菜单权限
	void saveOrUpdate(MenuRightsEntity menuRightsEntity);
	
/*	//根据菜单编号删除菜单权限信息
	void delete(String menuCode);*/
	
	//查询所有菜单权限信息
	List<MenuRightsEntity> findAll();
	
	//查询所有菜单权限信息(支持分页)
	List<MenuRightsEntity> findAll(Page page);
	
	//根据条件查询菜单权限信息(支持分页)
	List<MenuRightsEntity> findAll(MenuRightsEntityExample cdt, Page page);
	
	//根据角色编号查询所有用户菜单权限信息
	List<MenuRightsEx> get(Integer RoleCode);
   
	//根据菜单编号查询
	MenuRightsEx getMenucode(String menuCode);
	
/*	List<MenuRightsEntity> selectByPrimaryKeyRoleCode(Integer RoleCode);*/

	void deleteMenuRightsMenuCode(String menuCode);
	
	MenuRightsEx selectByPrimaryKeyMenuCode(String menuCode);

	int deleteByPrimaryKeyRoleCode(String roleCode);

}
