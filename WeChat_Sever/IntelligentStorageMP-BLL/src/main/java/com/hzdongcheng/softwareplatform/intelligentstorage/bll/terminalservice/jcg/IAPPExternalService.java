package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import java.util.Map;
 
public interface IAPPExternalService {
	 public Map<String,String> open_box_doBusiness(Map<String,String>  params);
	 public Map<String,String> query_box_doBusiness(Map<String,String>  params);
	 public Map<String,String> query_log_box_doBusiness(Map<String,String>  params);
	 public Map<String,String> query_gps_doBusiness(Map<String,String>  params);
	 
}
