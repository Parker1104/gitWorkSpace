package com.hzdongcheng.softwareplatform.controller.apptest;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.PropertiesUtil;

import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.net.URLEncoder;

/**
 * 开箱测试
 * @author kyx
 */

@Controller
@RequestMapping(value="/openBoxTest")
public class HttpPostTest  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	/*循环开箱*/	
	@RequestMapping("/loopOpenBox")
	public static void loopOpenBox(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String resultMsg = "";
		String terminalNo = request.getParameter("terminalid");
		String boxno = request.getParameter("boxno");
		String key = request.getParameter("key");
		String url = "";
		SimpleDateFormat doTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Date startTime = new Date();
		String startTimeStr = doTime.format(startTime).toString();
		PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();  
        Map maps = propertiesUtil.getProMap();  
		if(key.equals("1")){
			url =maps.get("apiIP")+ "/IntelligentStorageMP-APP/appapies/apies.do";
		}else{
			url =maps.get("apiIP")+ "/dcdz-magui-api-server/app/boxopen";
		}
		String reqInfo = keyRsult(key,boxno,terminalNo,startTimeStr);
		String result = HttpRequest.sendGet(url,reqInfo);
		String str = new String(result.getBytes("gbk"),"utf-8");
		System.out.println(str);
		if(!StringUtils.isEmpty(str)){
			Date overTime = new Date();
			String overTimeStr = doTime.format(overTime).toString();
			JSONObject returnMsg = new JSONObject();
			returnMsg.put("success","true");
			returnMsg.put("returnMsg",str);
			returnMsg.put("startTime",startTimeStr);
			returnMsg.put("overTime",overTimeStr);
			long timedif = (overTime.getTime()-startTime.getTime())/1000;
			returnMsg.put("timedif",timedif);
			resultMsg = returnMsg.toString();
		}

		PrintWriter out = response.getWriter();
		response.setHeader("Access-Control-Allow-Origin", "*");
		out.println(resultMsg);
        out.flush();
        out.close();
	}
	
	public static String keyRsult(String key,String boxno,String terminalNo,String startTimeStr){
		String reInfo ="";
		if(key.equals("1")){
			String testInfo = "method=dcdz.app.mg.box.open&app_key=app_jdhygy&format=json&open_seq=20171018190727497242&open_type=9&open_user=app_cqjdhygy&sign_method=1&sign=9&box_no=";
			reInfo = testInfo+boxno+"&gui_no="+terminalNo+"&timestamp="+java.net.URLEncoder.encode(startTimeStr);
		}else{
			reInfo = "terminalID="+terminalNo+"&boxID="+boxno;
		}
		return reInfo;
	}
	
	public String a(){
		String b ="1";
		return null;
	}
	
	public final static String abc = "JSP";
	public static void main(String[] args) {
		System.out.println(abc);
	}
}



