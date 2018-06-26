package com.hzdongcheng.softwareplatform.util;
 
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
 
/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 * @version 1.0
 */
public class WebUtils {
    protected WebUtils() {
    }
    public static String readRequestData(HttpServletRequest request) {
        String json = null;
        StringBuilder sb = new StringBuilder();
        try {
            ServletInputStream in = request.getInputStream();
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1)
                sb.append(new String(buffer, 0, len, "UTF-8"));

            json = sb.toString();
        } catch (Exception e) { /* report an error */ }
        
        
        return json;
    }
   

}
