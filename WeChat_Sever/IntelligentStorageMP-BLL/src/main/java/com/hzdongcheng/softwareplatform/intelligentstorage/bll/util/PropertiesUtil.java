package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;
import java.io.IOException;  
import java.util.Enumeration;    
import java.util.Map;  
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
 
public class PropertiesUtil {  
    private static PropertiesUtil propertiesUtil = null;  
    private static Map<String, String> proMap = new ConcurrentHashMap<String, String>();  
    private PropertiesUtil() {  
    	String fileName="/propertieses.properties";
        Properties pro = new Properties();  
        try {  
            pro.load(this.getClass().getResourceAsStream(fileName));
            Enumeration e = pro.propertyNames();  
            while (e.hasMoreElements()) {  
                String key = (String) e.nextElement();  
                String value = (String) pro.get(key);  
                proMap.put(key, value);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        }  
    }  
    public static synchronized   PropertiesUtil getInstance() {  
        if (propertiesUtil == null) {  
        	propertiesUtil = new PropertiesUtil();  
        	return propertiesUtil;
        }  
        return propertiesUtil;  
   }  
    public static Map<String, String> getProMap() {  
        return proMap;  
    }  
    
    public static int storein_effective_time() {  
	   PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
	   Map maps = propertiesUtil.getProMap();  
       String  parames=maps.get("storein_effective_time").toString();
       return Integer.parseInt(parames);
     }  
    public static int weixin_effective_time() {  
	   PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
	   Map maps = propertiesUtil.getProMap();  
       String  parames=maps.get("weixin_effective_time").toString();
       return Integer.parseInt(parames);
     }  
    public static int test_environment() {  
	   PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
	   Map maps = propertiesUtil.getProMap();  
       String  parames=maps.get("test_environment").toString();
       return Integer.parseInt(parames);
     }  
    
    public static void main(String[] args) {  
        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
        Map maps = propertiesUtil.getProMap();  
        System.out.println(" apiURL="+maps.get("apiURL"));  
        System.out.println(" storein_effective_time="+maps.get("storein_effective_time"));  
        System.out.println(" weixin_effective_time="+maps.get("weixin_effective_time"));  
        
        
    }  
}  











