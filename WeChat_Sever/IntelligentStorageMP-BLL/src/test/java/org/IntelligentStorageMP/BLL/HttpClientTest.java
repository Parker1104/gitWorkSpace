package org.IntelligentStorageMP.BLL;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class HttpClientTest {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(HttpClientTest.class);
	public static final String CHARSET_UTF8     = "utf-8";
	//public static String apiURL = "http://127.0.0.1:80/dcdz-magui-api-server/app/mg/api";// app/mg/api/push
	public static String apiURL = "";// app/mg/api/push
	public static String tcpUrl = "";// 
	public static SimpleDateFormat sdf = null;
	public static SimpleDateFormat sdf1 = null;
 
    
    static {
    	 PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
		 Map maps = propertiesUtil.getProMap();  
         apiURL=maps.get("apiURL").toString();
         tcpUrl=maps.get("tcpUrl").toString();
         System.out.println(" apiURL="+apiURL+"   tcpUrl="+tcpUrl);  
    }
 
    /**
     *1  远程微信app开箱接口
     */
     public static String doSentJCGOpenBox_Remote_Filter(Map<String, String> params) {
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
	 
	        String app_key=params.get("app_key");
	        String format=params.get("format");
	        String method = params.get("method");
	        String open_seq = params.get("open_seq");
	        String open_type=params.get("open_type");
	        
	        String open_user=params.get("open_user");
	        String sign_method=params.get("sign_method");
	        String timestamp = params.get("timestamp");
	       // String sign_md5 =params.get("sign");
	        String gui_no=params.get("gui_no");
	        
	        String box_no= params.get("box_no");
 
	        String secret = params.get("secret");
 
	        if(StringUtils.isEmpty(open_user)){
	        	open_user=app_key;
	        }
	        if(StringUtils.isEmpty(open_seq)){
	        	open_seq=generateTradeWaterNo();
	        }
	        
	        String sign_md5 =get_sign_md5(secret,app_key,box_no,format,gui_no,method, open_seq,open_type,open_user,sign_method,timestamp );
	        
        
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(apiURL);
	        RequestConfig requestConfig = RequestConfig.custom().
	                setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
 

	       /**
           String sign_md5 = get_sign_md5(secret,app_key,box_no,format,gui_no,method, open_seq,open_type,open_user,sign_method,timestamp );
	   		*/
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
                // log.debug(apiURL+":"+signBuffer.toString()+","+resultMsg);
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
                	 resultMsg = null;
                	 log.error(resultMsg);
                 }
	            } else {
	                 return "Error Response: " + response.getStatusLine().toString();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "post failure :caused by-->" + e.getMessage().toString();
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
     
     
     public  static  String get_sign_md5(String secret,String app_key,String box_no,String format,String gui_no,String method,
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
     
     
     
  /**
   	* 2   远程报警接口
   	*   String terminalID   //设备号
	*   int  alarmType      //报警类型
	*   int  alarmLevel     //报警等级	 
   */	 
   	public static String doAlarmNotices( String terminalID,  int  alarm_type, int  alarm_level) {
	      /** 
	        int    alarmLevel    //报警等级
	        int    alarmType     //报警类型
	        String app_key       //分配给应用的AppKey

		    String box_no        //格口编号
            String gui_no        //终端编号
		    String sign          //API输入参数签名结果
		    
		    String app_secret    //分配给应用的AppSecret
	       */	
	        String resultMsg = "0";
 
	        alarm_type=0;       //报警等级
	        alarm_level=0;      //报警类型
	        String app_key="";
	        
	        String gui_no=terminalID ; //终端编号
		    String sign  ="1"   ;      //API输入参数签名结果
		    
		   
		    String  secret ="" ;   //分配给应用的AppSecret
   	      
   	     
   	        
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(apiURL);
	        RequestConfig requestConfig = RequestConfig.custom().
	                setConnectTimeout(10000).setConnectionRequestTimeout(10000)
	                .setSocketTimeout(10000).setRedirectsEnabled(true).build();
	        httpPost.setConfig(requestConfig);
	        StringBuffer signBuffer = new StringBuffer();
                 signBuffer.append(secret)
		        .append("alarm_level").append(alarm_level)
		        .append("alarm_type").append(alarm_type)
		        .append("app_key").append(app_key)
		        .append("gui_no").append(gui_no)
		        .append(secret);
   	
   	        String sign_md5 = signMD5(signBuffer.toString());
   	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
   	        
   	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
   	        nvps.add( new BasicNameValuePair("alarm_level", alarm_level+""));
   	        nvps.add( new BasicNameValuePair("alarm_type", alarm_type+""));
   	        nvps.add( new BasicNameValuePair("app_key", app_key));
   	        nvps.add( new BasicNameValuePair("gui_no", gui_no));
   	        
   	        nvps.add( new BasicNameValuePair("sign", sign_md5));
    
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
    	                 return "Error Response: " + response.getStatusLine().toString();
    	            }
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	            return "post failure :caused by-->" + e.getMessage().toString();
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
 	        String app_key="app_test";
 	        String secret = "app_test_170101" ;
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
 	                 return "Error Response: " + response.getStatusLine().toString();
 	            }
 	        } catch (Exception e) {
 	            e.printStackTrace();
 	            return "post failure :caused by-->" + e.getMessage().toString();
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
    private static String signMD5(String data){
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
		
		
		
	}
}













