/*package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class httpClients {
	private static String  executePost(String msgContent){  
        String result = "";
        //System.out.println("postUrl:"+postUrl+","+msgContent);
        HttpClient client = new HttpClient();  
        HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams(); 
        // 设置连接超时时间(单位毫秒)
        managerParams.setConnectionTimeout(50000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(100000); 
        PostMethod post = new PostMethod(postUrl);
        post.addRequestHeader("Content-Type", "application/Json;charset=utf8");//在头文件中设置转码
        try {  
            RequestEntity requestEntity = new StringRequestEntity(msgContent, "application/Json",  "utf8");  ;
            post.setRequestEntity(requestEntity );
            int statusCode = client.executeMethod(post);
            if(statusCode == HttpStatus.SC_OK){
                result = post.getResponseBodyAsString();
                //System.out.println(result);
            }else{
                result = String.valueOf(statusCode);
            }
        } catch (HttpException e) {  
            System.err.println(" DcdzMobileProxy  executePost    Fatal protocol violation: " + e.getMessage());  
            log.error(" DcdzMobileProxy  executePost    Fatal protocol violation: " + e.getMessage());  
        } catch (IOException e) {  
            System.err.println(" DcdzMobileProxy  executePost  Fatal transport error: " + e.getMessage());  
            log.error(" DcdzMobileProxy  executePost  Fatal transport error: " + e.getMessage());  
        } finally {  
            post.releaseConnection();
        }  
        return result.trim();
    } 
}
*/