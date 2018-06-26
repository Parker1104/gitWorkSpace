package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

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
public class HttpClientesZGH {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(HttpClientesZGH.class);
	//public static final c CHARSET_UTF8     = "utf-8";
	//public static String apiURL = ""; 
	//public static String tcpUrl = ""; 
	public static SimpleDateFormat sdf = null;
	public static SimpleDateFormat sdf1 = null;
 
    //基础接口===================================
    /**
     *  断网请求 disconnect_url
    */
     public static String disconnect_url(Map<String, String> params) {	 
	        String resultMsg = null;
 
	        String terminalID=params.get("terminalID");
 
			Map maps = PropertiesUtil.getInstance().getProMap();  
	        String  storeRequset_urles=maps.get("disconnect_urles").toString();
	 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(storeRequset_urles);
	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
 
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
	                	 log.error("1 HttpClientesZGH  disconnect_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesZGH  disconnect_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesZGH   disconnect_url  post failure =" + e.getMessage().toString();
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
     *  心跳请求 keepAlive_url
    */
     public static String keepAlive_url(Map<String, String> params) {	 
	        String resultMsg = null;
 
			
	        String terminalID=params.get("terminalID");
	        String totalBoxNums  = params.get("totalBoxNums");
	       //String goodsStatus  = params.get("goodsStatus");
	        String openStatus  = params.get("openStatus");
	  
 
			Map maps = PropertiesUtil.getInstance().getProMap();  
	        String  storeRequset_urles=maps.get("keepAlive_urles").toString();
	 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(storeRequset_urles);
	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
	        nvps.add( new BasicNameValuePair("totalBoxNums", totalBoxNums));
	        //nvps.add( new BasicNameValuePair("goodsStatus", goodsStatus));
	        nvps.add( new BasicNameValuePair("openStatus", openStatus));
 
 
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
	                	 log.error("1 HttpClientesZGH  keepAlive_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesZGH  keepAlive_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesZGH   keepAlive_url  post failure =" + e.getMessage().toString();
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
     *  签到请求 sign_url
    */
     public static String sign_url(Map<String, String> params) {	 
	        String resultMsg = null;
	        
	        String terminalID=params.get("terminalID");
	        String dispalyName  = params.get("dispalyName");
	        String totalBoxNums  = params.get("totalBoxNums");
	        String ipAddress  = params.get("ipAddress");
	        String curDate  = params.get("curDate");
 
			Map maps = PropertiesUtil.getInstance().getProMap();  
	        String  storeRequset_urles=maps.get("signIn_urles").toString();
	 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(storeRequset_urles);
	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
	        nvps.add( new BasicNameValuePair("dispalyName", dispalyName));
	        nvps.add( new BasicNameValuePair("totalBoxNums", totalBoxNums));
	        nvps.add( new BasicNameValuePair("ipAddress", ipAddress));
	        nvps.add( new BasicNameValuePair("curDate", curDate));
 
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
	                	 log.error("1 HttpClientesZGH  sign_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesZGH  sign_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesZGH   sign_url  post failure =" + e.getMessage().toString();
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
     
     
     
     
     
	
	
	
	
    //对外接口===================================
    /**
     *  存请求 storeRequset_url
    */
     public static String storeRequset_url(Map<String, String> params) {	 
	        String resultMsg = null;
	        String terminalID=params.get("terminalID");
	        String openBoxCode  = params.get("openBoxCode");
			Map maps = PropertiesUtil.getInstance().getProMap();  
	        String  storeRequset_urles=maps.get("storeRequset_urles").toString();
	 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(storeRequset_urles);
	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
	        nvps.add( new BasicNameValuePair("openBoxCode", openBoxCode));
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
	                	 log.error("1 HttpClientesZGH  storeRequset_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesZGH  storeRequset_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesZGH   storeRequset_url  post failure =" + e.getMessage().toString();
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
      *  存请求确认 storeIn_url
     */
      public static String storeIn_url(Map<String, String> params) {	 
 	        String resultMsg = null;
 	        String terminalID=params.get("terminalID");
 	        String boxID  = params.get("boxID");
 	        String openBoxCode  = params.get("openBoxCode");
 	        String storeInTime  = params.get("storeInTime");
 	        String money  = params.get("money");
 	        
 			Map maps = PropertiesUtil.getInstance().getProMap();  
 	        String  storeRequset_urles=maps.get("storeIn_urles").toString();
 	 
 	        CloseableHttpClient httpClient = HttpClients.createDefault();
 	        HttpPost httpPost = new HttpPost(storeRequset_urles);
 	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
 		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
 	        httpPost.setConfig(requestConfig);
 	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
 	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
 	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
 	        nvps.add( new BasicNameValuePair("boxID", boxID));
 	        nvps.add( new BasicNameValuePair("openBoxCode", openBoxCode));
 	        nvps.add( new BasicNameValuePair("storeInTime", storeInTime));
 	        nvps.add( new BasicNameValuePair("money", money));
 	     
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
 	                	 log.error("1 HttpClientesZGH  storeIn_url  error    resultMsg = null ");
 	                 }
 	            } else {
 	                 return "2 HttpClientesZGH  storeIn_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
 	            }
 	        } catch (Exception e) {
 	            e.printStackTrace() ;
 	            return "3 HttpClientesZGH   storeIn_url  post failure =" + e.getMessage().toString();
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
       *  取请求  
      */
       public static String takeRequest_url(Map<String, String> params) {	 
  	        String resultMsg = null;
  	        String terminalID=params.get("terminalID");
  	       // String boxID  = params.get("boxID");
  	        String openBoxCode  = params.get("openBoxCode");
  	       // String storeInTime  = params.get("storeInTime");
  	  
  	        
  			Map maps = PropertiesUtil.getInstance().getProMap();  
  	        String  storeRequset_urles=maps.get("takeRequest_urles").toString();
  	 
  	        CloseableHttpClient httpClient = HttpClients.createDefault();
  	        HttpPost httpPost = new HttpPost(storeRequset_urles);
  	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
  		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
  	        httpPost.setConfig(requestConfig);
  	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
  	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
  	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
  	       // nvps.add( new BasicNameValuePair("boxID", boxID));
  	        nvps.add( new BasicNameValuePair("openBoxCode", openBoxCode));
  	       // nvps.add( new BasicNameValuePair("storeInTime", storeInTime));
 
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
  	                	 log.error("1 HttpClientesZGH  takeRequest_url  error    resultMsg = null ");
  	                 }
  	            } else {
  	                 return "2 HttpClientesZGH  takeRequest_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
  	            }
  	        } catch (Exception e) {
  	            e.printStackTrace() ;
  	            return "3 HttpClientesZGH   takeRequest_url  post failure =" + e.getMessage().toString();
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
        *  取请求  确认
       */
        public static String takeOut_url(Map<String, String> params) {	 
   	        String resultMsg = null;
   	        String terminalID=params.get("terminalID");
   	        String boxID  = params.get("boxID");
   	        String openBoxCode  = params.get("openBoxCode");
   	        String storeInTime  = params.get("storeInTime");
   	        String takeOutTime  = params.get("takeOutTime");
   	        String money  = params.get("money");
   	  
   	        
   			Map maps = PropertiesUtil.getInstance().getProMap();  
   	        String  storeRequset_urles=maps.get("takeOut_urles").toString();
   	 
   	        CloseableHttpClient httpClient = HttpClients.createDefault();
   	        HttpPost httpPost = new HttpPost(storeRequset_urles);
   	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
   		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
   	        httpPost.setConfig(requestConfig);
   	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
   	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
   	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
   	        nvps.add( new BasicNameValuePair("boxID", boxID));
   	        nvps.add( new BasicNameValuePair("openBoxCode", openBoxCode));
   	        nvps.add( new BasicNameValuePair("storeInTime", storeInTime));
   	        nvps.add( new BasicNameValuePair("takeOutTime", takeOutTime));
   	        nvps.add( new BasicNameValuePair("money", money));
  
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
   	                	 log.error("1 HttpClientesZGH  takeOut_url  error    resultMsg = null ");
   	                 }
   	            } else {
   	                 return "2 HttpClientesZGH  takeOut_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
   	            }
   	        } catch (Exception e) {
   	            e.printStackTrace() ;
   	            return "3 HttpClientesZGH   takeOut_url  post failure =" + e.getMessage().toString();
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
}













