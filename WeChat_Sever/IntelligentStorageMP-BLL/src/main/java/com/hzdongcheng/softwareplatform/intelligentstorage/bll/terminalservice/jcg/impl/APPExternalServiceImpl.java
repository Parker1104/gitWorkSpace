package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.HttpClient4Guotong;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPExternalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentesDao;
 
/**
 * @author ysk
 * @Description: 外部app接入接口
 * @ClassName:  APPExternalServiceImpl
 * @date 2017年8月16日 下午13:09:00
 * 
 */
@Transactional
@Service("APPExternalService")
public class APPExternalServiceImpl implements IAPPExternalService{
	//private final static Logger logger = Logger.getLogger(APPExternalServiceImpl.class);
	public static Log4jUtils logger = Log4jUtils.createInstanse(APPExternalServiceImpl.class);
    @Autowired
	protected PaymentesDao paymentesDao;
 
 
	@Override
	public Map<String, String> query_gps_doBusiness(Map<String, String> params) {
		try {
     	      Map<String, String> resultsparams = new HashMap<String, String>();
	          String results= HttpClient4Guotong.doSentJCGQueryGps_Remote_Filter(params);
	          resultsparams.put("results", results);
	          return resultsparams; 
		 } catch (Exception e) {
			  logger.debug("异常："+e.getMessage());
			  logger.error("error  APPExternalServiceImpl  query_gps_doBusiness  fail the reason is:  "+e );
		 }
		return null;
	}

	@Override
	public Map<String, String> query_log_box_doBusiness(Map<String, String> params) {
		 try {
       	      Map<String, String> resultsparams = new HashMap<String, String>();
	          String results= HttpClient4Guotong.doSentJCGQueryLogBox_Remote_Filter(params);
	          resultsparams.put("results", results);
	          return resultsparams; 
		 } catch (Exception e) {
			  logger.debug("异常："+e.getMessage());
			  logger.error("error  APPExternalServiceImpl  query_log_box_doBusiness  fail the reason is:  "+e );
		 }
		return null;
	}

	@Override
	public Map<String, String> open_box_doBusiness(Map<String, String> params) {
		 try {
        	  Map<String, String> resultsparams = new HashMap<String, String>();
	          String results= HttpClient4Guotong.doSentJCGOpenBox_Remote_Filter(params);
	          resultsparams.put("results", results);
	          return resultsparams; 
		 } catch (Exception e) {
			  logger.debug("异常："+e.getMessage());
			  logger.error("error  APPExternalServiceImpl  open_box_doBusiness  fail the reason is:  "+e );
		 }
		return null;
	}

	@Override
	public Map<String, String> query_box_doBusiness(Map<String, String> params) {
		 try {
       	      Map<String, String> resultsparams = new HashMap<String, String>();
	          String results= HttpClient4Guotong.doSentJCGQueryBox_Remote_Filter(params);
	          resultsparams.put("results", results);
	          return resultsparams; 
		 } catch (Exception e) {
			  logger.debug("异常："+e.getMessage());
			  logger.error("error  APPExternalServiceImpl  query_box_doBusiness  fail the reason is:  "+e );
		 }
		return null;
	}
	 
 
/*	@Override
	public OutParamAlarmNoticesRequest alarmNotices(InParamAlarmNoticesRequest alarmNotices) {
	     System.out.println(" CoinJCGServiceImpl    alarmNotices ");
		 //初始返回值
	     OutParamAlarmNoticesRequest outParam = new OutParamAlarmNoticesRequest();
		 outParam.setErrorCode(JCGErrorCode.ERR_OK);
		 try {
			 String terminalID= alarmNotices.getTerminalID(); //设备号
			 int  alarmType =alarmNotices.getAlarmType();     //报警类型
			 int  alarmLevel =alarmNotices.getAlarmType();    //报警等级
			 
			 
		 } catch (Exception e) {
			 logger.debug("异常："+e.getMessage());
			 logger.error("error  CoinJCGServiceImpl  alarmNotices  fail the reason is:  "+e );
		 }
		 return outParam;
	}*/
	
	 
}

















