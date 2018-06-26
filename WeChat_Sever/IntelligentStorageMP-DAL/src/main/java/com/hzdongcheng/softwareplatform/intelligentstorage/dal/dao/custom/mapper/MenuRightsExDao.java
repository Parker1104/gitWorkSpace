package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.MenuRightsEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.MenuRightsEntityDao;

public interface MenuRightsExDao extends MenuRightsEntityDao{

	void deleteByMenuCode(String menuCode);
	
	int deleteByRoleCode(@Param("roleCode") String roleCode);
	
	List<MenuRightsEx> selectByRoleCode(Integer roleCode);

	MenuRightsEx selectByMenuCode(String menuCode);
}
