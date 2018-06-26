package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

/**
*  获取菜单
*/

public class InParamAPWxmenuInfo implements java.io.Serializable
{
	public String FunctionID = "650209"; //功能编号

	public String companyId = "";  
	public String areaCode = "";  
	public String flags = "";  

	public String getFunctionID()
	{
		if(this.FunctionID == null || this.FunctionID.compareTo("") == 0)
			return "650209";
		else
			return FunctionID;
	}
	public void setFunctionID(String FunctionID)
	{
		if(FunctionID == null || FunctionID.compareTo("") == 0)
			this.FunctionID = "650209";
		else
			this.FunctionID = FunctionID;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}

 
}
