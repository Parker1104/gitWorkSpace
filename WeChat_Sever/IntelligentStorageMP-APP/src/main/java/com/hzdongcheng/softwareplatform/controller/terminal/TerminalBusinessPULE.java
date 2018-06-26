package com.hzdongcheng.softwareplatform.controller.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.front.server.model.service.InParam;
import com.hzdongcheng.front.server.model.service.OutParam;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamType;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamType;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IPULEService;
 
@Controller
@RequestMapping("/terminalpule")
public class TerminalBusinessPULE {
	//localhost/IntelligentStorageMP-APP/terminalpule/typeIn
	public static Log4jUtils logger = Log4jUtils.createInstanse(TerminalBusinessPULE.class);
	@Autowired
	@Qualifier("PULEService")   
	IPULEService impl;
 
	 
		/**
		 * 垃圾箱    1:二维码 2称重  3:箱满提示
		 * @param request
		 * @param response
		 * @throws IOException  
		 */
		/*@RequestMapping("/typeIn")
		@ResponseBody
		public void typeIn(HttpServletRequest request,HttpServletResponse response) throws IOException {
		    //解析JSON字符串
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
			String line = null;  
			StringBuilder sb = new StringBuilder();  
			while((line = br.readLine())!=null){  
				sb.append(line);  
			}  
			String reqBody = sb.toString(); 
			reqBody = URLDecoder.decode(reqBody, "UTF-8");
			logger.info(" TerminalBusiness typeIn  request : " + reqBody);	
			//转换成对象
		   InParamType inParam = JsonUtils.toBean(reqBody, InParamType.class); 
 
	    	InParamType inParam=new InParamType();
	        inParam.setBoxId(1);
	    	inParam.setTerminalID("11111");
	    	inParam.setTypes(1);
	    	inParam.setTypesLength(5);
	    	byte typesDataArray []=new byte[inParam.getTypesLength()];
	    	//accii码   ABC 12 十六进制    41,42,43,  31,32 10进制      65,66,67,  49,50
	    	typesDataArray [0]=65;
	    	typesDataArray [1]=66;
	    	typesDataArray [2]=67;
	    	typesDataArray [3]=49;
	    	typesDataArray [4]=50;
	    	inParam.setTypesDataArray(typesDataArray);
		
			//具体签到实现
			OutParamType outParam = impl.typeIn(inParam);

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
	   }*/
		
		
		
		
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
		
		/*InParamSignIn ps =new InParamSignIn();
		ps.setTerminalID("1");
		ps.setDisplayName("1");
		ps.setTotalBoxNums(1);
		ps.setIpAddr("1");*/
 
		//具体签到实现
		OutParamSignIn outParam = impl.sign(ps);

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
			OutParamKeepAlive outAlive = impl.keepAlive(pka);
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
 
	/**
	 * 设备断网
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/disconnect")
	@ResponseBody
	public void disconnect(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//解析JSON字符串
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		
		//System.out.println("request : " + reqBody);	
		//转换成对象
		InParam ps = JsonUtils.toBean(reqBody, InParam.class);
		//具体实现
		OutParam entity = impl.disconnect(ps);
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		//对象转json数据
		String outJson = JsonUtils.toJSONString(entity);
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
