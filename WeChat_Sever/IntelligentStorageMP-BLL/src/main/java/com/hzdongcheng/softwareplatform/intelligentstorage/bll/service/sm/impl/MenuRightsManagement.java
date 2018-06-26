package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMenuRightsService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.MenuRightsEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.MenuRightsExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuRightsEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuRightsEntityExample;

@Service
public class MenuRightsManagement implements IMenuRightsService{

	@Autowired
	MenuRightsExDao menuRightsExDao;

	@Override
	public void saveOrUpdate(MenuRightsEntity menuRightsEntity) {

		if ( null == menuRightsExDao.selectByPrimaryKey(menuRightsEntity.getMenucode(), menuRightsEntity.getRolecode())) {
			//添加
			menuRightsExDao.insert(menuRightsEntity);
		} else {
			//更新
			menuRightsExDao.updateByPrimaryKeySelective(menuRightsEntity);
		}
	}



	@Override
	public List<MenuRightsEntity> findAll() {
		MenuRightsEntityExample example = new MenuRightsEntityExample();
		/*example.createCriteria().andMenuaccessEqualTo("1");*/
		return menuRightsExDao.selectByExample(example);
	}

	@Override
	public List<MenuRightsEntity> findAll(Page page) {
		MenuRightsEntityExample example = new MenuRightsEntityExample();
		/*example.createCriteria().andMenucodeNotEqualTo("1");*/
		PageHelper.startPage(page.getStartPage(), page.getPageSize());
		return menuRightsExDao.selectByExample(example);
	}

	@Override
	public List<MenuRightsEntity> findAll(MenuRightsEntityExample cdt, Page page) {
		cdt.createCriteria().andMenucodeNotEqualTo("1");
		PageHelper.startPage(page.getStartPage(), page.getPageSize());
		return menuRightsExDao.selectByExample(cdt);
	}

	@Override
	public List<MenuRightsEx> get(Integer RoleCode) {
		// TODO Auto-generated method stub
		return menuRightsExDao.selectByRoleCode(RoleCode);
	}

	@Override
	public MenuRightsEx getMenucode(String menuCode) {
		return menuRightsExDao.selectByMenuCode(menuCode);
	}


	@Override
	public void deleteMenuRightsMenuCode(String menuCode) {
		menuRightsExDao.deleteByMenuCode(menuCode);
	}

	@Override
	public MenuRightsEx selectByPrimaryKeyMenuCode(String menuCode) {
		return menuRightsExDao.selectByMenuCode(menuCode);
	}
	
	@Override
	public int deleteByPrimaryKeyRoleCode(String roleCode) {
		return menuRightsExDao.deleteByRoleCode(roleCode);
	}


}
