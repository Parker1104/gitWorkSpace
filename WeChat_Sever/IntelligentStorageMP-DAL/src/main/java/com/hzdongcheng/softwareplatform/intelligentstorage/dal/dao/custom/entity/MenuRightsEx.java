package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntity;

public class MenuRightsEx {

	private MenuEntity menu;

	private String menucode;

	private Integer rolecode;

	private String menuaccess;
	
	private String menuoperator;

	private String menuauthorize;

	public MenuEntity getMenu() {
		return menu;
	}

	public void setMenu(MenuEntity menu) {
		this.menu = menu;
	}

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	public Integer getRolecode() {
		return rolecode;
	}

	public void setRolecode(Integer rolecode) {
		this.rolecode = rolecode;
	}

	public String getMenuaccess() {
		return menuaccess;
	}

	public void setMenuaccess(String menuaccess) {
		this.menuaccess = menuaccess;
	}

	public String getMenuoperator() {
		return menuoperator;
	}

	public void setMenuoperator(String menuoperator) {
		this.menuoperator = menuoperator;
	}

	public String getMenuauthorize() {
		return menuauthorize;
	}

	public void setMenuauthorize(String menuauthorize) {
		this.menuauthorize = menuauthorize;
	}
	
}
