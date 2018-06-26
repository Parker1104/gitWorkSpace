package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntity;

public class MenuRightsManagerEx {
	private RoleEntity roleEntity;
	private MenuEntity menuEntity;
	private String menuCode;
	private Integer roleCode;
	private String menuAccess;
	private String menuOperator;
	private String menuAuthorize;

	public RoleEntity getRoleEntity() {
		return roleEntity;
	}

	public void setRoleEntity(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}

	public MenuEntity getMenuEntity() {
		return menuEntity;
	}

	public void setMenuEntity(MenuEntity menuEntity) {
		this.menuEntity = menuEntity;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public Integer getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

	public String getMenuAccess() {
		return menuAccess;
	}

	public void setMenuAccess(String menuAccess) {
		this.menuAccess = menuAccess;
	}

	public String getMenuOperator() {
		return menuOperator;
	}

	public void setMenuOperator(String menuOperator) {
		this.menuOperator = menuOperator;
	}

	public String getMenuAuthorize() {
		return menuAuthorize;
	}

	public void setMenuAuthorize(String menuAuthorize) {
		this.menuAuthorize = menuAuthorize;
	}

}
