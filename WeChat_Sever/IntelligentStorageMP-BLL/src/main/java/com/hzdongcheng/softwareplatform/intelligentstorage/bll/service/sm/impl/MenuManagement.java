package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMenuService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.MenuEntityDao;


@Service
public class MenuManagement implements IMenuService {
	
	@Autowired
	MenuEntityDao dao;

	@Override
	public void saveOrUpdate(MenuEntity menuEntity) {
		if(null == get(menuEntity.getMenucode())){
			dao.insert(menuEntity);
		}else {
			dao.updateByPrimaryKey(menuEntity);
		}
		
	}

	@Override
	public MenuEntity get(String menuCode) {
		return dao.selectByPrimaryKey(menuCode);
	}

	@Override
	public void delete(String menuCode) {
		
		dao.deleteByPrimaryKey(menuCode);
		
	}

	@Override
	public List<MenuEntity> findAll() {
		MenuEntityExample example = new MenuEntityExample();
		return dao.selectByExample(example);
	}

	@Override
	public List<MenuEntity> findAll(Page page) {
		MenuEntityExample example = new MenuEntityExample();
		example.createCriteria().andMenucodeEqualTo("1");
		PageHelper.startPage(page.getStartPage(), page.getPageSize());
		return dao.selectByExample(example);
	}

	@Override
	public List<MenuEntity> findAll(MenuEntityExample cdt, Page page) {
		cdt.createCriteria().andMenucodeEqualTo("1");
		PageHelper.startPage(page.getStartPage(), page.getPageSize());
		return dao.selectByExample(cdt);
	}

}
