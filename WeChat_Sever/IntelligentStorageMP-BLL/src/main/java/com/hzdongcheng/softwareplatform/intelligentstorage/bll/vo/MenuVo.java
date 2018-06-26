package com.hzdongcheng.softwareplatform.intelligentstorage.bll.vo;

import java.io.Serializable;

public class MenuVo implements Serializable{
//	"F_ModuleId": "65",
//    "F_ParentId": "6",
//    "F_EnCode": "ReportManage",
//    "F_FullName": "系统信息",
//    "F_Icon": "fa fa-area-chart",
//    "F_UrlAddress": "web/log/systemMessage.jsp",
//    "F_Target": "expand",
//    "F_IsMenu": 0,
//    "F_AllowExpand": 1,
//    "F_IsPublic": 0,
//    "F_AllowEdit": null,
//    "F_AllowDelete": null,
//    "F_SortCode": 6,
//    "F_DeleteMark": 0,
//    "F_EnabledMark": 1,
//    "F_Description": null,
//    "F_CreateDate": null,
//    "F_CreateUserId": null,
//    "F_CreateUserName": null,
//    "F_ModifyDate": "2016-04-11 10:21:54",
//    "F_ModifyUserId": "System",
//    "F_ModifyUserName": "超级管理员"

	private static final long serialVersionUID = 1L;
	
	String moduleId;
	String parentId;
	String fullName;
	String icon;
	String urlAddress;
	String target;
	String isMenu;
	int allowExpand;
	int sortCode;
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrlAddress() {
		return urlAddress;
	}
	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}
	public int getAllowExpand() {
		return allowExpand;
	}
	public void setAllowExpand(int allowExpand) {
		this.allowExpand = allowExpand;
	}
	public int getSortCode() {
		return sortCode;
	}
	public void setSortCode(int sortCode) {
		this.sortCode = sortCode;
	}
}
