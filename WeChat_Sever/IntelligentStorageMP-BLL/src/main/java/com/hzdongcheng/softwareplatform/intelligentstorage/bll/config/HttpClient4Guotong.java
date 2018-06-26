package com.hzdongcheng.softwareplatform.intelligentstorage.bll.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import com.hzdongcheng.components.toolkits.exception.error.ErrorCode;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.DateUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.PropertiesUtil;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AppPartnerEntityDao;
import net.sf.json.JSONObject;
import net.sf.json.util.WebUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("all")
public class HttpClient4Guotong {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(HttpClient4Guotong.class);
	public static final String CHARSET_UTF8     = "utf-8";
	//public static String apiURL = "http://127.0.0.1:80/dcdz-magui-api-server/app/mg/api";// app/mg/api/push
	public static String apiURL = "";// app/mg/api/push
	public static String tcpUrl = "";// 
	public static SimpleDateFormat sdf = null;
	public static SimpleDateFormat sdf1 = null;
	
/*	public static String  states_ok = "0"; //0:表示成功 
	public static String  states_no = "-1";//-1:表示失败 
*/    
    static {
    	 PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
		 Map maps = propertiesUtil.getProMap();  
         apiURL=maps.get("apiURL").toString();
         tcpUrl=maps.get("tcpUrl").toString();
         System.out.println(" apiURL="+apiURL+"   tcpUrl="+tcpUrl);  
    }
    
  //======================================远程移动api======================================

    /**
     *   -2  远程移动   查询gps接口  
    */
     public static String doSentJCGQueryGps_Remote_Filter(Map<String, String> params) {	 
	        String resultMsg = null;
	        
	        String app_key=params.get("app_key");
	        String format  = params.get("format");
	        String gui_no = params.get("gui_no");
	        
	        String method = params.get("method");
	        String sign_method = params.get("sign_method");
	        String timestamp = params.get("timestamp");
	        
	        String sign_md5 =params.get("sign");
	        //String secret =params.get("secret");
		      
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(apiURL);
	        RequestConfig requestConfig = RequestConfig.custom()
		                .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("app_key", app_key));
	        nvps.add( new BasicNameValuePair("format", format));
	        nvps.add( new BasicNameValuePair("gui_no", gui_no));
	        
	        nvps.add( new BasicNameValuePair("method", method));
	        nvps.add( new BasicNameValuePair("sign_method", sign_method));
	        nvps.add( new BasicNameValuePair("timestamp", timestamp));
 
	        nvps.add( new BasicNameValuePair("sign", sign_md5));
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	            HttpResponse response = httpClient.execute(httpPost);
	            if (response.getStatusLine().getStatusCode() == 200) {
	            	 resultMsg = StringUtils.trim(EntityUtils.toString(response.getEntity()));
	                 JSONObject resJson = JSONObject.fromObject(resultMsg);
	                 if(resJson!=null){
	                	 resultMsg= resJson.toString();
	                 }else{
	                	 resultMsg = null;
	                	 log.error("2 doSentJCGQueryGps_Remote_Filter  error  null      开箱错误,错误 null ");
	                 }
	            } else {
	                 return "3 doSentJCGQueryGps_Remote_Filter  getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "4 doSentJCGQueryGps_Remote_Filter    post failure =" + e.getMessage().toString();
	        }finally {
	            if(null != httpClient){
	                try {
	                    httpClient.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
       return resultMsg;
     }
    /**
     *-1  远程微信app查询接口
     *	@param  app_key    //分配给应用的AppKey
     *  @param  datetime    
     *  @param  format    
     *  @param  gui_no     //终端编号
     *  
	 *	@param  method     //API接口名称
	 *  @param  sign_method  
	 *	@param  timestamp  //时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2016-01-01 12:00:00
	 *	@param  sign        //API输入参数签名结果	    
    */
     public static String doSentJCGQueryLogBox_Remote_Filter(Map<String, String> params) {	 
	        String resultMsg = null;
	        
	        String app_key=params.get("app_key");
	        String datetime =params.get("datetime");
	        String format  = params.get("format");
	        String gui_no = params.get("gui_no");
	        
	        String method = params.get("method");
	        String sign_method = params.get("sign_method");
	        String timestamp = params.get("timestamp");
	        
	        String sign_md5 =params.get("sign");
	        //String secret =params.get("secret");
		      
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(apiURL);
	        RequestConfig requestConfig = RequestConfig.custom()
		                .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("app_key", app_key));
	        nvps.add( new BasicNameValuePair("datetime", datetime));
	        nvps.add( new BasicNameValuePair("format", format));
	        nvps.add( new BasicNameValuePair("gui_no", gui_no));
	        
	        nvps.add( new BasicNameValuePair("method", method));
	        nvps.add( new BasicNameValuePair("sign_method", sign_method));
	        nvps.add( new BasicNameValuePair("timestamp", timestamp));
 
	        nvps.add( new BasicNameValuePair("sign", sign_md5));
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	            HttpResponse response = httpClient.execute(httpPost);
	            if (response.getStatusLine().getStatusCode() == 200) {
	            	 resultMsg = StringUtils.trim(EntityUtils.toString(response.getEntity()));
	                 JSONObject resJson = JSONObject.fromObject(resultMsg);
	                 if(resJson!=null){
	                	 resultMsg= resJson.toString();
	                 }else{
	                	 resultMsg = null;
	                	 log.error("2 doSentJCGQueryLogBox_Remote_Filter  error  null      开箱错误,错误 null ");
	                 }
	            } else {
	                 return "3 doSentJCGQueryLogBox_Remote_Filter  getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "4  doSentJCGQueryLogBox_Remote_Filter    post failure =" + e.getMessage().toString();
	        }finally {
	            if(null != httpClient){
	                try {
	                    httpClient.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
       return resultMsg;
     }
	
    /**
     *-1  远程微信app查询接口
     *	@param  app_key    //分配给应用的AppKey
     *  @param  gui_no     //终端编号
	 *	@param  method     //API接口名称
	 *	@param  timestamp  //时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2016-01-01 12:00:00
	 *	@param  sign        //API输入参数签名结果	    
    */
     public static String doSentJCGQueryBox_Remote_Filter(Map<String, String> params) {	 
 
	        String resultMsg = null;
	        
	        String app_key=params.get("app_key");
	        String gui_no=params.get("gui_no");
	        String method = params.get("method");
	        String timestamp = params.get("timestamp");
	        String format = params.get("format");
	        String sign_method = params.get("sign_method");
	        
	        String sign_md5 =params.get("sign");
  
		      
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(apiURL);
	        RequestConfig requestConfig = RequestConfig.custom()
		                .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("app_key", app_key));
	        nvps.add( new BasicNameValuePair("gui_no", gui_no));
	        nvps.add( new BasicNameValuePair("method", method));
	        nvps.add( new BasicNameValuePair("timestamp", timestamp));
	        nvps.add( new BasicNameValuePair("format", format));
	        nvps.add( new BasicNameValuePair("sign_method", sign_method));
	        
	        nvps.add( new BasicNameValuePair("sign", sign_md5));
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	            HttpResponse response = httpClient.execute(httpPost);
	            if (response.getStatusLine().getStatusCode() == 200) {
	            	 resultMsg = StringUtils.trim(EntityUtils.toString(response.getEntity()));
	                 JSONObject resJson = JSONObject.fromObject(resultMsg);
	                 if(resJson!=null){
	                	 resultMsg= resJson.toString();
	                 }else{
	                	 resultMsg = null;
	                	 log.error("2 doSentJCGQueryBox_Remote_Filter error  null      开箱错误,错误 null ");
	                 }
	            } else {
	                 return "3 doSentJCGQueryBox_Remote_Filter  getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "4 doSentJCGQueryBox_Remote_Filter     post failure =" + e.getMessage().toString();
	        }finally {
	            if(null != httpClient){
	                try {
	                    httpClient.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
       return resultMsg;
     }
 
  /**
    *0   远程移动报警通知 api
    * @param  terminalID        //设备号
    * @param  alarmType         //报警类型
    * @param  alarmLevel        //报警等级
    * @param  alarmnoticeurls   //报警回调地址
    * @param  app_key           //应用app_key
    * @param  app_secret        //应用app_secret
    *
   */
    public static String to_Remote_Alarm_Notices(Map<String, String> params) {
		String terminalID=params.get("terminalID");                 //设备号
		int alarm_type= Integer.parseInt(params.get("alarmType"));  //报警类型
		int alarm_level= Integer.parseInt(params.get("alarmLevel"));//报警等级
	    String alarmnoticeurls=  params.get("alarmnoticeurls") ;    //报警回调地址
	    String app_key=  params.get("app_key") ;                    //应用app_key
	    String app_secret=  params.get("app_secret") ;              //应用app_secret
 
	    CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(alarmnoticeurls);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
        		                     .setSocketTimeout(10000).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
        
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add( new BasicNameValuePair("alarm_level", alarm_level+"")); //报警等级
        nvps.add( new BasicNameValuePair("alarm_type", alarm_type+""));   //报警类型
        nvps.add( new BasicNameValuePair("app_key", app_key));            //应用app_key
        nvps.add( new BasicNameValuePair("gui_no", terminalID));          //终端编号
        
	    StringBuffer signBuffer = new StringBuffer();
            signBuffer.append(app_secret)
	        .append("alarm_level").append(alarm_level)
	        .append("alarm_type").append(alarm_type)
	        .append("app_key").append(app_key)
	        .append("gui_no").append(terminalID)
	        .append(app_secret);

        String sign = signMD5(signBuffer.toString());
        nvps.add( new BasicNameValuePair("sign", sign));                  //签名
 
        String resultMsg = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
            	 resultMsg = StringUtils.trim(EntityUtils.toString(response.getEntity()));
                 /* JSONObject resJson = JSONObject.fromObject(resultMsg);   */
            } else {
                 return "1  to_Remote_Alarm_Notices   Error Response: " + response.getStatusLine().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return " 2 to_Remote_Alarm_Notices   post failure :caused by-->" + e.getMessage().toString();
        }finally {
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    	 return resultMsg;
    }
 
    /**
     *1  远程微信app开箱接口
     *  @param  gui_no     //终端编号
	 *  @param  box_no     //格口编号
	 *	@param  open_seq   //开箱流水号，同一个流水号只能开一次箱
	 *	@param  open_user  //开箱人员标识
	 *	@param  open_type  //开箱类型：1-用户存物开箱；2-用户取物开箱；9-管理开箱（格口维护）
	 *	 	
	 *	@param  method     //API接口名称
	 *	@param  app_key    //分配给应用的AppKey
	 *	@param  app_secret //分配给应用的AppSecret
	 *	@param  timestamp  //时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2016-01-01 12:00:00
	 *	@param  format     //响应格式。默认为JSON格式
	 *	 	    
	 *	@param  sign_method //签名算法，可选值为：md5，hmac。
	 *	@param  sign        //API输入参数签名结果	    
   */
     public static String doSentJCGOpenBox_Remote_Filter(Map<String, String> params) {	 
	        String resultMsg = null;
	 
	        String app_key=params.get("app_key");
	        String format=params.get("format");
	        String method = params.get("method");
	        String open_seq = params.get("open_seq");
	        String open_type=params.get("open_type");
	        
	        String open_user=params.get("open_user");
	        String sign_method=params.get("sign_method");
	        String timestamp = params.get("timestamp");
	        String sign_md5 =params.get("sign");
	        String gui_no=params.get("gui_no");
	        //System.out.println(" 1  sign_md5="+params.get("sign") );
	        
	        String box_no= params.get("box_no");
	        //String secret = params.get("secret");

	        //String sign_md5 =get_sign_md5(secret,app_key,box_no,format,gui_no,method, open_seq,open_type,open_user,sign_method,timestamp );
        
	        log.info(" 开箱请求参数 app_key="+app_key+"  format="+format +" method="+method+" open_seq="+open_seq+" open_type="+open_type );
	        log.info(" open_user="+open_user+" sign_method="+sign_method+" timestamp="+timestamp+" gui_no="+gui_no+"  box_no="+box_no+" sign="+sign_md5 );
 
 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(apiURL);
	        RequestConfig requestConfig = RequestConfig.custom().
	                setConnectTimeout(20000).setConnectionRequestTimeout(20000).setSocketTimeout(20000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("app_key", app_key));
	        nvps.add( new BasicNameValuePair("format", format));
	        nvps.add( new BasicNameValuePair("method", method));
	        nvps.add( new BasicNameValuePair("open_seq", open_seq));
	        nvps.add( new BasicNameValuePair("open_type", open_type));
	        		
	        nvps.add( new BasicNameValuePair("open_user", open_user));
	        nvps.add( new BasicNameValuePair("sign_method", sign_method));
	        nvps.add( new BasicNameValuePair("timestamp", timestamp));
	        nvps.add( new BasicNameValuePair("sign", sign_md5));
	        nvps.add( new BasicNameValuePair("gui_no", gui_no));
	        		
	        nvps.add( new BasicNameValuePair("box_no", box_no));

	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	            HttpResponse response = httpClient.execute(httpPost);
	            if (response.getStatusLine().getStatusCode() == 200) {
	            	 resultMsg = StringUtils.trim(EntityUtils.toString(response.getEntity()));
	            	 
	            	 if(!StringUtils.isEmpty(resultMsg)){
	            		 JSONObject resJson = JSONObject.fromObject(resultMsg);
		                 if(resJson!=null){
		                	 resultMsg=resJson.toString();
		                 }else{
		                	 resultMsg = null;
		                	 log.error("2 doSentJCGOpenBox_Remote_Filter  error  null      开箱错误,错误 null ");
		                 }
	            	 }else{
	            		 resultMsg = null;
	                	 log.error("3 doSentJCGOpenBox_Remote_Filter  error  null      开箱错误,错误 null ");
	            	 }
	            } else {
	                 return "4 doSentJCGOpenBox_Remote_Filter getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "5  doSentJCGOpenBox_Remote_Filter   post failure =" + e.getMessage().toString();
	        }finally {
	            if(null != httpClient){
	                try {
	                    httpClient.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
       return resultMsg;
     }
     
     
     public  static  String get_sign_md5openBox(String secret,String app_key,String box_no,String format,String gui_no,String method,
    		     String open_seq,String open_type,String open_user,String sign_method,String timestamp   ){
	          StringBuffer signBuffer = new StringBuffer();
                 signBuffer.append(secret)
		        .append("app_key").append(app_key)
		        .append("box_no").append(box_no)
		        .append("format").append(format)
		        .append("gui_no").append(gui_no)
		        .append("method").append(method)
		        
		        .append("open_seq").append(open_seq)
		        .append("open_type").append(open_type)
		        .append("open_user").append(open_user)
		        .append("sign_method").append(sign_method)
		        .append("timestamp").append(timestamp)
		        .append(secret);

                 String sign_md5 = signMD5(signBuffer.toString());
   
    	 return sign_md5;
     }
     
     
     public  static  String get_sign_md5queryGps(String secret,String app_key, String format,
             String gui_no, String method,String sign_method, String timestamp ){
			StringBuffer signBuffer = new StringBuffer();
			signBuffer.append(secret)
			.append("app_key").append(app_key)
			.append("format").append(format)
			.append("gui_no").append(gui_no)
			
			.append("method").append(method)
			.append("sign_method").append(sign_method)
			.append("timestamp").append(timestamp)
			
			.append(secret);
			
			
			//System.out.println("-3----get_sign_md5queryBoxLog----"+signBuffer.toString());
			
			
			
			String sign_md5 = signMD5(signBuffer.toString());
			
			return sign_md5;
	 }
     public  static  String get_sign_md5queryBoxLog(String secret,String app_key,String datetime,String format,
    		                                        String gui_no, String method,String sign_method, String timestamp ){
          StringBuffer signBuffer = new StringBuffer();
             signBuffer.append(secret)
             .append("app_key").append(app_key)
             .append("datetime").append(datetime)
             .append("format").append(format)
             .append("gui_no").append(gui_no)

             .append("method").append(method)
             .append("sign_method").append(sign_method)
             .append("timestamp").append(timestamp)

	        .append(secret);

             
             //System.out.println("-3----get_sign_md5queryBoxLog----"+signBuffer.toString());
             
             
             
             String sign_md5 = signMD5(signBuffer.toString());

	   return sign_md5;
   }
     
     
     
   //======================================本地移动api======================================
 
  /**
	* 2  本地微信app开箱接口
    */	 
	public static String doSentJCGOpenBox(String gui_no, String box_no, String open_user, String open_seq) {
		  /**
			    String gui_no    = "";//终端编号
			    String box_no    = "";//格口编号
			    String open_seq  = "";//开箱流水号，同一个流水号只能开一次箱
			    String open_user = "";//开箱人员标识
			    String open_type = "";//开箱类型：1-用户存物开箱；2-用户取物开箱；9-管理开箱（格口维护）
			
			    String method      = "";//API接口名称
			    String app_key     = "";//分配给应用的AppKey
			    String app_secret  = "";//分配给应用的AppSecret
			    String timestamp   = "";//时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2016-01-01 12:00:00
			    String format      = "";//响应格式。默认为JSON格式
			    String sign_method = "";//签名算法，可选值为：md5，hmac。
			    String sign        = "";//API输入参数签名结果
		   */	
 	        String resultMsg = "0";
 	        String open_type="9";
 	        String method = "dcdz.app.mg.box.open";
 	        String app_key="app_lsglmg";
 	        String secret = "60b02a3435bbafe2a8616e4e74e7dce3" ;
 	        String timestamp = DateUtils.timestampToString(new java.util.Date());
	        String format="json";
	        String sign_method="md5";
	       //String sign
	      
	        if(!StringUtils.isNotEmpty(open_user)){
	        	open_user=app_key;
	        }
	        if(!StringUtils.isNotEmpty(open_seq)){
	        	open_seq=generateTradeWaterNo();
	        }
	        
 	        CloseableHttpClient httpClient = HttpClients.createDefault();
 	        HttpPost httpPost = new HttpPost(apiURL);
 	        RequestConfig requestConfig = RequestConfig.custom().
 	                setConnectTimeout(10000).setConnectionRequestTimeout(10000)
 	                .setSocketTimeout(10000).setRedirectsEnabled(true).build();
 	        httpPost.setConfig(requestConfig);
 	        StringBuffer signBuffer = new StringBuffer();
	                 signBuffer.append(secret)
			        .append("app_key").append(app_key)
			        .append("box_no").append(box_no)
			        .append("format").append(format)
			        .append("gui_no").append(gui_no)
			        .append("method").append(method)
			        
			        .append("open_seq").append(open_seq)
			        .append("open_type").append(open_type)
			        .append("open_user").append(open_user)
			        .append("sign_method").append(sign_method)
			        .append("timestamp").append(timestamp)
			        .append(secret);
	
	        String sign_md5 = signMD5(signBuffer.toString());
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("app_key", app_key));
	        nvps.add( new BasicNameValuePair("format", format));
	        nvps.add( new BasicNameValuePair("method", method));
	        nvps.add( new BasicNameValuePair("open_seq", open_seq));
	        nvps.add( new BasicNameValuePair("open_type", open_type));
	        		
	        nvps.add( new BasicNameValuePair("open_user", open_user));
	        nvps.add( new BasicNameValuePair("sign_method", sign_method));
	        nvps.add( new BasicNameValuePair("timestamp", timestamp));
	        nvps.add( new BasicNameValuePair("sign", sign_md5));
	        nvps.add( new BasicNameValuePair("gui_no", gui_no));
	        		
	        nvps.add( new BasicNameValuePair("box_no", box_no));
 
 	        try {
 	            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
 	            //logger.info("httpPost ===**********===>>> " + EntityUtils.toString(httpPost.getEntity()));
 	            HttpResponse response = httpClient.execute(httpPost);
 	            if (response.getStatusLine().getStatusCode() == 200) {
 	            	 resultMsg = EntityUtils.toString(response.getEntity());
 	            	 resultMsg = StringUtils.trim(resultMsg);
 	            	 //System.out.println(resultMsg);
	                 log.debug(apiURL+":"+signBuffer.toString()+","+resultMsg);
	                 JSONObject resJson = JSONObject.fromObject(resultMsg);
	                 if(resJson!=null){
	                	 //System.out.println(resJson.toString());
	                	 if("0".equals(resJson.optString("err_code"))){
	                		 resultMsg = "0"; //开箱成功
	                	 }else{
	                		 resultMsg = resJson.optString("err_code");
	                		 log.error(resultMsg);
	                	 }
	                 }else{
	                	 resultMsg = "-11";
	                	 log.error(resultMsg);
	                 }
 	            } else {
 	            	 resultMsg = "-11";
 	                // return "Error Response: " + response.getStatusLine().toString();
 	            }
 	        } catch (Exception e) {
 	            e.printStackTrace();
 	            resultMsg = "-11";
 	           // return "post failure :caused by-->" + e.getMessage().toString();
 	        }finally {
 	            if(null != httpClient){
 	                try {
 	                    httpClient.close();
 	                } catch (IOException e) {
 	                    e.printStackTrace();
 	                }
 	            }
 	        }
           return resultMsg;
    }
	 
	
	
	
	
	
	//=======================================基础=======================================
    /**
     * 生成交易流水:yyyyMMddHHmmss+5位随机数
     * @param sysDateTime
     * @return
     */
    public  static String generateTradeWaterNo() {
    	Date sysDateTime=new Date();
        SimpleDateFormat datetimeFromat = new SimpleDateFormat("yyyyMMddHHmmss");
        String headStr = datetimeFromat.format(sysDateTime);  //20171018190727497242
        StringBuffer buff = new StringBuffer();
        buff.append(headStr).append(RandUtils.generateNumber(3)).append(RandUtils.generateNumber(3));
        return buff.toString();
    }
    public static String signMD5(String data){
    	byte[] bytes;
		try {
			bytes = encryptMD5(data);
			return byte2hex(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
	private static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			bytes = md5.digest(data.getBytes(CHARSET_UTF8));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.toString());
		}
	    return bytes;
	}
	private static String byte2hex(byte[] bytes) {
	    StringBuilder sign = new StringBuilder();
	    for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(bytes[i] & 0xFF);
	        if (hex.length() == 1) {
	            sign.append("0");
	        }
	        sign.append(hex.toUpperCase());
	    }
	    return sign.toString();
	}
	/*private static String encodeUrl(String url){
		String result = url;
		try{
			result = URLEncoder.encode(url, "utf-8");
		}catch(Exception e){}
		return result;
	}*/

	
	
	public static void main(String[] args) throws EduExceptions {
		String terminalNo  = "21001000b9493003ce040000";
		String boxNo = "5";
		String open_user ="123456";
		String open_seq  = "11111";
		String result = doSentJCGOpenBox(terminalNo,boxNo, open_user, open_seq);
		System.out.println("result="+result);
		
		
        /**  
			String terminalNo  = "21001000b9493003ce040000";
			String boxNo = "5";
			String open_user ="123456";
			String open_seq  = WebUtils.getUUID();
			String result = doSentJCGOpenBox(terminalNo,boxNo, open_user, open_seq);
			System.out.println("result="+result);
	 
		 	String terminalNo  = "21000600864530032f040000";
			String boxNo = "1";
			String open_user ="o6aBWt9OgZPfyt6Ne2rIduixIxGM";
			String open_seq  = "20170915163606987789"; 
	 
		    String terminalNo  = "21000600864530032f040000";
			String boxNo = "1";
			String open_user ="";
			String open_seq  = "";
			
			
			//String result = HttpClient4Guotong.doSentJCGOpenBox(p.TerminalNo, p.BoxNo, p.OpenID, p.TradeWaterNo);
			 String result = doSentJCGOpenBox(terminalNo,boxNo, open_user, open_seq);
 
	        String result = doSentJCGOpenBox_Test();
		    System.out.println("result="+result);
		 */
		
		  /**  
		    String gui_no = "21000600864530032f040000";
	        String box_no= "1";
	        String open_user="";
	        String open_seq = "";
	        String open_type="9";
	       
	        String method = "dcdz.app.mg.box.open";
	        String app_key="app_cqjdhygy";
	        String secret = "app_cqjdhygy_180000" ;
	        String timestamp = DateUtils.timestampToString(new java.util.Date());
	        String format="json";
	        String sign_method="md5";
	       //String sign
	     */	  
		/*Map<String, String> params=new HashMap<String, String>();
		String app_key="app_cqjdhygy";
		String gui_no="21000600864530032f040000";
		String method="dcdz.app.mg.box.query";
		String timestamp="2017-10-24 13:53:00";
		String format="json";
		String sign_method="1";
 
        params.put("app_key", app_key);
        params.put("gui_no", gui_no);
        params.put("method", method);
        params.put("timestamp",timestamp);
        params.put("format",format);
        params.put("sign_method",sign_method);
        
    	String sign=get_sign_md5queryBox("app_cqjdhygy_180000",app_key,gui_no,method,timestamp,format,sign_method);
        params.put("sign",sign);
        
	    String results= HttpClient4Guotong.doSentJCGQueryBox_Remote_Filter(params);
	    System.out.println("-----返回结果----results-----"+results);*/
	    
	}
 
	public  static  String get_sign_md5queryBox(String secret,String app_key, String gui_no,String method, String timestamp , String format,String sign_method){
	        StringBuffer signBuffer = new StringBuffer();
			        signBuffer.append(secret)
			       .append("app_key").append(app_key)
			       .append("format").append(format)
			       .append("gui_no").append(gui_no)
			       .append("method").append(method)
			       .append("sign_method").append(sign_method)
			       .append("timestamp").append(timestamp)
			       .append(secret);
	       String sign_md5 = signMD5(signBuffer.toString());
	
	       return sign_md5;
	}
	
	
	public static void mainffff(String[] args) {
	      Map<String, String> params = new HashMap<String, String>();
	      //secret
	      params.put("app_key", "app_key");
	      params.put("format","format");
	      params.put("gui_no", "gui_no");
	      params.put("datetime", "datetime");
	      
	      params.put("method", "method");
	      params.put("sign_method","sign_method");
	      params.put("timestamp", "timestamp");

	      //params.put("sign", "sign");
	      //secret
	      
		  String[] keys = params.keySet().toArray(new String[0]);
		  Arrays.sort(keys);
		  
			 
	     System.out.println("-1----keys----");
	     for (String key : keys) {
	        String value = params.get(key);
	        if (!StringUtils.isEmpty(value)) {
	        	 System.out.println("--  key="+key+" value="+value);
	        }
	     }
	     System.out.println("-2----keys----");
		  
	}
	
}













