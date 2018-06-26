package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;
 
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class Utils {
    /** 
     * areacodes    04  总部
     * displayname  1001
     * address      安装地址
     */  
    public static String locations(String   areacodes,String   displayname,String   address  )   {  
    	String Location="";
    	if(StringUtils.isEmpty(areacodes)&&StringUtils.isEmpty(displayname)&&StringUtils.isEmpty(address)){
    		Location="暂无";
        }else{
        	Location = (StringUtils.isEmpty(address)?  areacodes+displayname :address );
        }
        return Location;  
    }  
}
