package com.hzdongcheng.softwareplatform.controller.industrialcontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.industrialcontrol.IIndustrialService;
 

public class IndustrialBase {
	public static Log4jUtils logger = Log4jUtils.createInstanse(IndustrialBase.class);
	@Autowired
	@Qualifier("IndustrialService")  
	IIndustrialService impl;
	
	
	/**
	 * 设备签到
	 * @param request
	 * @param response
	 * @throws IOException  
	 */
	@RequestMapping("/signIn")
	@ResponseBody
	public void signIn(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//解析JSON字符串
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		
		logger.info(" TerminalBusiness signIn  request : " + reqBody);	
		
		//转换成对象
		InParamSignIn ps = JsonUtils.toBean(reqBody, InParamSignIn.class);
		
		//具体签到实现
		//OutParamSignIn outParam = impl.sign(ps);
		OutParamSignIn outParam = new OutParamSignIn();
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		//对象转json数据
		String outJson = JsonUtils.toJSONString(outParam);
		PrintWriter out = null; 
		try {
			out = response.getWriter(); 
			out.write(outJson);
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			if (out != null) {
				out.close(); 
			} 
		}
	}
	/**
	 * 心跳
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/keepAlive")
	@ResponseBody
	public void keepsAlive(HttpServletRequest request,HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		InParamKeepAlive pka = JsonUtils.toBean(reqBody, InParamKeepAlive.class);
		String outJson = "";
		try {
			///OutParamKeepAlive outAlive = impl.keepAlive(pka);
			OutParamKeepAlive outAlive = new OutParamKeepAlive();
			outJson = JsonUtils.toJSONString(outAlive);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null; 
		try {
			out = response.getWriter(); 
			out.write(outJson);
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			if (out != null) {
				out.close(); 
			} 
		}
	}
	
	
	
	
}
