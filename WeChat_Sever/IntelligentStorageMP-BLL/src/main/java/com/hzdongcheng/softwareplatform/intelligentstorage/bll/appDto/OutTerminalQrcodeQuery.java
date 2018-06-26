package com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto;

public class OutTerminalQrcodeQuery {
	public String TerminalNo = ""; 		//设备号
	public String TerminaUrl = ""; 		//设备返回Url
	
	public String getTerminalNo() {
		return TerminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}
	public String getTerminaUrl() {
		return TerminaUrl;
	}
	public void setTerminaUrl(String terminaUrl) {
		TerminaUrl = terminaUrl;
	}
}
