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
public class HttpClientesPULE {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(HttpClientesPULE.class);
	//public static final c CHARSET_UTF8     = "utf-8";
	//public static String apiURL = ""; 
	//public static String tcpUrl = ""; 
	public static SimpleDateFormat sdf = null;
	public static SimpleDateFormat sdf1 = null;
 
    //基础接口===================================
	
    
    /**
     *  垃圾箱    1:二维码 2称重  3:箱满提示
     *  签到请求 typeIn_url
    */
     public static String typeIn_url(Map<String, String> params) {	 
	        String resultMsg = null;
	        
	        String terminalID=params.get("terminalID");
	        String boxId  = params.get("boxId");
	        String types  = params.get("types");
	        //String typesLength  = params.get("typesLength");
	        String typesDataArray  = params.get("typesDataArray");

 
			Map maps = PropertiesUtil.getInstance().getProMap();  
	        String  storeRequset_urles=maps.get("typeIn_urles").toString();
	 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(storeRequset_urles);
	        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
		                                 .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add( new BasicNameValuePair("terminalID", terminalID));
	        nvps.add( new BasicNameValuePair("boxId", boxId));
	        nvps.add( new BasicNameValuePair("types", types));
	        //nvps.add( new BasicNameValuePair("typesLength", typesLength));
	        nvps.add( new BasicNameValuePair("typesDataArray", typesDataArray));
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
	                	 log.error("1 HttpClientesPULE  typeIn_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesPULE  typeIn_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesPULE   typeIn_url  post failure =" + e.getMessage().toString();
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
	                	 log.error("1 HttpClientesPULE  disconnect_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesPULE  disconnect_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesPULE   disconnect_url  post failure =" + e.getMessage().toString();
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
	        String goodsStatus  = params.get("goodsStatus");
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
	        nvps.add( new BasicNameValuePair("goodsStatus", goodsStatus));
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
	                	 log.error("1 HttpClientesPULE  keepAlive_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesPULE  keepAlive_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesPULE   keepAlive_url  post failure =" + e.getMessage().toString();
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
	                	 log.error("1 HttpClientesPULE  sign_url  error    resultMsg = null ");
	                 }
	            } else {
	                 return "2 HttpClientesPULE  sign_url   getStatusCode() != 200   Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace() ;
	            return "3 HttpClientesPULE   sign_url  post failure =" + e.getMessage().toString();
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













