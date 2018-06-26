package com.hzdongcheng.softwareplatform.controller.terminal;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
 
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.HttpClient4Guotong;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPExternalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.PropertiesUtil;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AppPartnerEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalEntityDao;


@Controller
@RequestMapping("/appapies")
public class AppApiExternalBusiness {
	//private final static Logger logger = Logger.getLogger(AppApiExternalBusiness.class);
	public static Log4jUtils logger = Log4jUtils.createInstanse(AppApiExternalBusiness.class);
	@Autowired
	@Qualifier("APPExternalService")  
	IAPPExternalService impl;
    @Resource
    private AppPartnerEntityDao partnerDao;
    
    @Resource
    private TerminalEntityDao terminalEntityDao;
	/**
	 * 外部移动设备api
	 * @param request
	 * @param response
	 * @throws IOException  
	 */
	@RequestMapping(value = "/apies.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String apies(HttpServletRequest request,HttpServletResponse response)  {
		try {
		    /** @ResponseBody    
			    Content-Type=application/x-www-form-urlencoded
				consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
				produces: 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回;
				1. Accept属于请求头， Content-Type属于实体头。 
					Http报头分为通用报头，请求报头，响应报头和实体报头。 
					请求方的http报头结构：通用报头|请求报头|实体报头 
					响应方的http报头结构：通用报头|响应报头|实体报头 */
			Map<String, String> results= null;
			String  resultsString= null;
			String method = request.getParameter("method"); //API接口名称  
			if(StringUtils.isNotEmpty(method)){
				
				if(method.equals("dcdz.app.mg.box.open")){
					results=  openBox(request,response ) ;
					resultsString=results.get("results");
				}else if(method.equals("dcdz.app.mg.box.query")  ){
					results= queryBox(request,response ) ;
					resultsString=results.get("results");
				}else if(method.equals("dcdz.app.mg.box.query.log")  ){
					results= queryBoxLog(request,response ) ;
					resultsString=results.get("results");
				}else if(method.equals("dcdz.app.mg.box.query.gps")  ){
					results= queryGps(request,response ) ;
					resultsString=results.get("results");
				}
			}
 
			String resultes= JsonUtils.toJSONString(resultsString);
			String bToObject=(String) JSON.parse(resultes);
			
			logger.info("  appapies/apies.do  请求方法     method="+method+"   resultes="+bToObject);		
			
        	System.out.println("  appapies/apies.do  请求方法     method="+method+"   resultes="+bToObject);
        	return bToObject;
			
		 } catch (Exception e) {
			 logger.debug("异常："+e.getMessage());
			 logger.error("error  AppApiExternalBusiness  apies  fail the reason is:  "+e );
		 }
		return null;
	}
	
	public static void main(String[] args) {
		 
		 String results ="  {\"err_code\":\"-1\", \"err_msg\":\"123444\" }  " ;
		 System.out.println("------1---"+results);
		 String resultes= JsonUtils.toJSONString(results);
		 System.out.println("------2---"+resultes);
	     String bToObject=(String) JSON.parse(resultes);
	     System.out.println("------3---"+bToObject);
		 
	}
	
	
	
	/**
	 * 外部移动    查询gps
	 */
	public Map<String, String> queryGps(HttpServletRequest request,HttpServletResponse response )  {
		try {
				Map<String, String> inparams = new HashMap<String, String>();
				String app_key = request.getParameter("app_key");         //分配给应用的AppKey 
				String method = request.getParameter("method");           //API接口名称   值="dcdz.app.mg.box.query.log"
				String timestamp = request.getParameter("timestamp");     //时间戳，例如：2016-01-01 12:00:00   5分钟后失效
				String sign = request.getParameter("sign");               //签名算法值     参考 sign说明
				String format = request.getParameter("format");           //响应格式  值="json"
				String sign_method = request.getParameter("sign_method"); //签名算法: 值="1" 表示md5算法加密
 
				String gui_no = request.getParameter("gui_no");           //终端编号
 
				//TerminalEntity terminalEntitys=terminalEntityDao.selectByPrimaryKey(gui_no);
				//System.out.println("-------Terminalid-------terminalEntitys="+terminalEntitys.getTerminalid());
				
				
				StringBuffer sbs=new StringBuffer();
				if(StringUtils.isEmpty(app_key))  sbs.append(" app_key is null, ");
				if(StringUtils.isEmpty(method))  sbs.append(" method is null, ");
				if(StringUtils.isEmpty(timestamp))  sbs.append(" timestamp is null, ");
				if(StringUtils.isEmpty(sign))  sbs.append(" sign is null, ");
				if(StringUtils.isEmpty(format))  sbs.append(" format is null, ");
				if(StringUtils.isEmpty(sign_method))  sbs.append(" sign_method is null, ");
 
				if(StringUtils.isEmpty(gui_no))  sbs.append(" gui_no is null, ");
	  
				if(!StringUtils.isEmpty(sbs.toString())){
					Map<String, String> results= new HashMap<String, String>();
					//results.put("err_code", "-1");
					//results.put("err_msg",sbs.toString());
					 results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\""+sbs.toString()+"\" }  ");
					return  results;
				}
 
				inparams.put("app_key", app_key);
				inparams.put("format", format);
				inparams.put("gui_no", gui_no);
				
				inparams.put("method", method);
				inparams.put("sign_method",sign_method);
				inparams.put("timestamp", timestamp);
 
			    AppPartnerEntity entity = partnerDao.selectByPrimaryKey(app_key);
	            if(null==entity){
					Map<String, String> results= new HashMap<String, String>();
					/*results.put("err_code", "-1");
					results.put("err_msg","secret is null");*/
					 results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\"secret is null\" }  ");
					
					return  results;
	            }
	            inparams.put("secret", entity.getUserKey());
 
	            if(getTestEnvironment()){
	            	 sign = HttpClient4Guotong.get_sign_md5queryGps(entity.getUserKey(),app_key,format,gui_no,   method,sign_method,timestamp) ;  
	            	 logger.error("  注意是测试环境    AppApiExternalBusiness  queryGps  sign="+sign );
	            }
			   
 
				inparams.put("sign", sign);
				
 
		        /*System.out.println("1 -openTheBox---app_key="+app_key+"  format="+format);
		        System.out.println("  app_key="+app_key+ " method="+method+" open_seq="+open_seq+" open_type="+open_type );
		        System.out.println("  open_user="+open_user+" sign_method="+sign_method+" timestamp="+timestamp+" gui_no="+gui_no+"  box_no="+box_no );
		        System.out.println("1  sign="+sign );*/

				Map<String, String> results=impl.query_gps_doBusiness(inparams);
				return results;
		 } catch (Exception e) {
			 logger.debug("异常："+e.getMessage());
			 logger.error("error  AppApiExternalBusiness  queryGps  fail the reason is:  "+e );
		 }
		
		return null;
	}
	/**
	 * 外部移动    查询操作日志
	 */
	public Map<String, String> queryBoxLog(HttpServletRequest request,HttpServletResponse response )  {
		try {
				Map<String, String> inparams = new HashMap<String, String>();
				String app_key = request.getParameter("app_key");         //分配给应用的AppKey 
				String method = request.getParameter("method");           //API接口名称   值="dcdz.app.mg.box.query.log"
				String timestamp = request.getParameter("timestamp");     //时间戳，例如：2016-01-01 12:00:00   5分钟后失效
				String sign = request.getParameter("sign");               //签名算法值     参考 sign说明
				String format = request.getParameter("format");           //响应格式  值="json"
				String sign_method = request.getParameter("sign_method"); //签名算法: 值="1" 表示md5算法加密
 
				String gui_no = request.getParameter("gui_no");           //终端编号
				String datetime = request.getParameter("datetime");       //查询时间
				
				StringBuffer sbs=new StringBuffer();
				if(StringUtils.isEmpty(app_key))  sbs.append(" app_key is null, ");
				if(StringUtils.isEmpty(method))  sbs.append(" method is null, ");
				if(StringUtils.isEmpty(timestamp))  sbs.append(" timestamp is null, ");
				if(StringUtils.isEmpty(sign))  sbs.append(" sign is null, ");
				if(StringUtils.isEmpty(format))  sbs.append(" format is null, ");
				if(StringUtils.isEmpty(sign_method))  sbs.append(" sign_method is null, ");
 
				if(StringUtils.isEmpty(gui_no))  sbs.append(" gui_no is null, ");
				if(StringUtils.isEmpty(datetime))  sbs.append(" datetime is null, ");

				if(!StringUtils.isEmpty(sbs.toString())){
					Map<String, String> results= new HashMap<String, String>();
					/*results.put("err_code", "-1");
					results.put("err_msg",sbs.toString());*/
					 results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\""+sbs.toString()+"\" }  ");
					
					return  results;
				}
 
				inparams.put("app_key", app_key);
				inparams.put("datetime",datetime);
				inparams.put("format", format);
				inparams.put("gui_no", gui_no);
				
				inparams.put("method", method);
				inparams.put("sign_method",sign_method);
				inparams.put("timestamp", timestamp);
 
			    AppPartnerEntity entity = partnerDao.selectByPrimaryKey(app_key);
	            if(null==entity){
					Map<String, String> results= new HashMap<String, String>();
					/*results.put("err_code", "-1");
					results.put("err_msg","secret is null");*/
					 results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\"secret is null\" }  ");
					return  results;
	            }
	            inparams.put("secret", entity.getUserKey());
 
	            
	            if(getTestEnvironment()){
	            	sign = HttpClient4Guotong.get_sign_md5queryBoxLog(entity.getUserKey(),app_key,datetime,format,gui_no,  method,sign_method,timestamp) ;
	                logger.error("  注意是测试环境    AppApiExternalBusiness  queryBoxLog  sign="+sign );
	            }
				inparams.put("sign", sign);
				
 
		        /*System.out.println("1 -openTheBox---app_key="+app_key+"  format="+format);
		        System.out.println("  app_key="+app_key+ " method="+method+" open_seq="+open_seq+" open_type="+open_type );
		        System.out.println("  open_user="+open_user+" sign_method="+sign_method+" timestamp="+timestamp+" gui_no="+gui_no+"  box_no="+box_no );
		        System.out.println("1  sign="+sign );*/

				Map<String, String> results=impl.query_log_box_doBusiness(inparams);
				return results;
		 } catch (Exception e) {
			 logger.debug("异常："+e.getMessage());
			 logger.error("error  AppApiExternalBusiness  queryBoxLog  fail the reason is:  "+e );
		 }
		
		return null;
	}
 
	/**
	 * 外部移动    开箱
	 */
	public Map<String, String> openBox(HttpServletRequest request,HttpServletResponse response )  {
		try {
				Map<String, String> inparams = new HashMap<String, String>();
				String app_key = request.getParameter("app_key");     //分配给应用的AppKey   
				String format = request.getParameter("format");       //响应格式  值="json"
				String method = request.getParameter("method");       //API接口名称   值="dcdz.app.mg.box.open"
				String open_seq = request.getParameter("open_seq");   //开箱流水号     值=""
				String open_type = request.getParameter("open_type"); //值="9" 1-用户存物开箱；2-用户取物开箱；9-管理开箱(格口维护)
		
				
				String open_user = request.getParameter("open_user");     //开箱人员标识     值=""
				String sign_method = request.getParameter("sign_method"); //签名算法: 值="1" 表示md5算法加密
				String timestamp = request.getParameter("timestamp");     //时间戳，例如：2016-01-01 12:00:00   5分钟后失效
				String sign = request.getParameter("sign");               //签名算法值     参考 sign说明
				String gui_no = request.getParameter("gui_no");           //终端编号
				
				String box_no = request.getParameter("box_no");           //箱号编号
				
				StringBuffer sbs=new StringBuffer();
				if(StringUtils.isEmpty(app_key))  sbs.append(" app_key is null, ");
				if(StringUtils.isEmpty(format))  sbs.append(" format is null, ");
				if(StringUtils.isEmpty(method))  sbs.append(" method is null, ");
				if(StringUtils.isEmpty(open_seq))  sbs.append(" open_seq is null, ");//开箱流水号     值=""
				if(StringUtils.isEmpty(open_type))  sbs.append(" open_type is null, ");
				
				if(StringUtils.isEmpty(open_user))  sbs.append(" open_user is null, ");//开箱人员标识     值=""
				if(StringUtils.isEmpty(sign_method))  sbs.append(" sign_method is null, ");
				if(StringUtils.isEmpty(timestamp))  sbs.append(" timestamp is null, ");
				if(StringUtils.isEmpty(sign))  sbs.append(" sign is null, ");
				if(StringUtils.isEmpty(gui_no))  sbs.append(" gui_no is null, ");
				
				if(StringUtils.isEmpty(box_no))  sbs.append(" box_no is null, ");

				if(!StringUtils.isEmpty(sbs.toString())){
					//inparams.put("msg", sbs.toString());
					Map<String, String> results= new HashMap<String, String>();
					//results.put("err_code", "-1");
					//results.put("err_msg",sbs.toString());
					
				    results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\""+sbs.toString()+"\" }  ");
					 
					return  results;
				}
 
				inparams.put("app_key", app_key);
				inparams.put("format", format);
				inparams.put("method", method);
				inparams.put("open_seq", open_seq);
				inparams.put("open_type", open_type);
				
				inparams.put("open_user", open_user);
				//inparams.put("sign_method", sign_method.equals("1")?"md5":"hmac");
				inparams.put("sign_method",sign_method);
				inparams.put("timestamp", timestamp);
				
			    AppPartnerEntity entity = partnerDao.selectByPrimaryKey(app_key);
	            if(null==entity){
					 //Map<String, String> results= new HashMap<String, String>();
					 //results.put("err_code", "-1");
					 //results.put("err_msg","secret is null  ");
	            	
	            	 Map<String, String> results= new HashMap<String, String>();
					 results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\"secret is null\" }  ");
					 return  results;
	            }
	            inparams.put("secret", entity.getUserKey());
 
	            if(getTestEnvironment()){
	            	 sign = HttpClient4Guotong.get_sign_md5openBox(entity.getUserKey(),app_key,box_no,format,gui_no, 
                        	method,  open_seq,open_type,open_user,sign_method,timestamp );
	            	 logger.error("  注意是测试环境    AppApiExternalBusiness  openBox  sign="+sign );
	            }
			    
 
	          /*  System.out.println("3  testEnvironment="+testEnvironment );
	            System.out.println("  secret="+entity.getUserKey()+"  app_key="+app_key+"  box_no="+box_no+"  format="+format+"  gui_no="+gui_no);
	            System.out.println("  method="+method+"  open_seq="+open_seq+"  open_type="+open_type+"  open_user="+open_user+"  sign_method="+sign_method);
	            System.out.println("  timestamp="+timestamp +"  sign="+sign);*/
	            
	            
	            
				inparams.put("sign", sign);
				inparams.put("gui_no", gui_no);
				
				inparams.put("box_no", box_no);
 
		        /*System.out.println("1 -openTheBox---app_key="+app_key+"  format="+format);
		        System.out.println("  app_key="+app_key+ " method="+method+" open_seq="+open_seq+" open_type="+open_type );
		        System.out.println("  open_user="+open_user+" sign_method="+sign_method+" timestamp="+timestamp+" gui_no="+gui_no+"  box_no="+box_no );
		        System.out.println("1  sign="+sign );*/

				Map<String, String> results=impl.open_box_doBusiness(inparams);
				return results;
		 } catch (Exception e) {
			 logger.debug("异常："+e.getMessage());
			 logger.error("error  AppApiExternalBusiness  openBox  fail the reason is:  "+e );
		 }
		
		return null;
	}
	
	/**
	 * 外部移动 查询箱子接口
	 */
	public Map<String, String> queryBox(HttpServletRequest request,HttpServletResponse response )  {
		try {
				Map<String, String> params=new HashMap<String, String>();
				/*String app_key="app_cqjdhygy";
				String gui_no="21000600864530032f040000";
				String method="dcdz.app.mg.box.query";
				String timestamp="2017-10-24 13:53:00";
				String format="json";
				String sign_method="1";*/
 
				String app_key = request.getParameter("app_key");      
				String gui_no = request.getParameter("gui_no");     
				String method = request.getParameter("method");      
				String timestamp = request.getParameter("timestamp");   
				String format = request.getParameter("format");   
				String sign_method = request.getParameter("sign_method");   
				String sign = request.getParameter("sign");   
				
				
				StringBuffer sbs=new StringBuffer();
				if(StringUtils.isEmpty(app_key))  sbs.append(" app_key is null, ");
				if(StringUtils.isEmpty(gui_no))  sbs.append(" gui_no is null, ");
				if(StringUtils.isEmpty(method))  sbs.append(" method is null, ");
				if(StringUtils.isEmpty(timestamp))  sbs.append(" timestamp is null, ");
				if(StringUtils.isEmpty(format))  sbs.append(" format is null, ");
				if(StringUtils.isEmpty(sign_method))  sbs.append(" sign_method is null, ");
 
				if(StringUtils.isEmpty(sign))  sbs.append(" sign is null, ");
				
				if(!StringUtils.isEmpty(sbs.toString())){
					Map<String, String> results= new HashMap<String, String>();
					//params.put("msg", sbs.toString());
					 results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\""+sbs.toString()+"\" }  ");
					return  params;
				}
			    AppPartnerEntity entity = partnerDao.selectByPrimaryKey(app_key);
	            if(null==entity){
	            	Map<String, String> results= new HashMap<String, String>();
	            	// params.put("msg", "secret is null ,");
	            	 results.put("results","{ \"err_code\":\"-1\", \"err_msg\":\"secret is null\" }  ");
					 return  params;
	            }
	            
	            params.put("secret", entity.getUserKey());
		        params.put("app_key", app_key);
		        params.put("gui_no", gui_no);
		        params.put("method", method);
		        params.put("timestamp",timestamp);
		        params.put("format",format);
		        params.put("sign_method",sign_method);
		        
		        if(getTestEnvironment()){
		        	 sign=HttpClient4Guotong.get_sign_md5queryBox(entity.getUserKey(),app_key,gui_no,method,timestamp,format,sign_method);
		        	 logger.error("  注意是测试环境    AppApiExternalBusiness  queryBox  sign="+sign );
		        }
		        params.put("sign",sign);
		        
		        Map<String, String> results= impl.query_box_doBusiness(params);
			   // System.out.println("-----返回结果----results-----"+results);
	            
				return results;
		 } catch (Exception e) {
			 logger.debug("异常："+e.getMessage());
			 logger.error("error  AppApiExternalBusiness  queryBox  fail the reason is:  "+e );
		 }
		
		return null;
	}
	
	public Boolean getTestEnvironment(){
		 PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
		 Map maps = propertiesUtil.getProMap();  
		 String test_environment=maps.get("test_environment").toString();
	     // 测试环境  0是 1否
	     if("0".equals(test_environment)){
	    	 return true;
	     }
	     
		 return false;
	}
 
}









