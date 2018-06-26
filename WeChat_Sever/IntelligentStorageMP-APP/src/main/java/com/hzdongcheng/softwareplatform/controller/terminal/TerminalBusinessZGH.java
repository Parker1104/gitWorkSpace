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
import com.hzdongcheng.front.server.model.service.jcg.up.InParamDKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInDRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IZGHService;
 
@Controller
@RequestMapping("/terminalzgh")
public class TerminalBusinessZGH {
	public static Log4jUtils logger = Log4jUtils.createInstanse(TerminalBusinessZGH.class);
	@Autowired
	@Qualifier("ZGHService")   
	IZGHService impl;
 
	
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
		InParamDKeepAlive pka = JsonUtils.toBean(reqBody, InParamDKeepAlive.class); 
		String outJson = "";
		try {
			/*InParamKeepAlive pka = new InParamKeepAlive(); 
			pka.setTerminalID("1");
			pka.setTotalBoxNums(1);

			byte [] goodsStatus =new byte [2];
			goodsStatus[0]=0;
			goodsStatus[1]=1;
			pka.setGoodsStatusArray(goodsStatus);
			
		    byte [] openStatus =new byte [2];
			openStatus[0]=1;
			openStatus[1]=0;
			pka.setOpenStatusArray(openStatus); */
			
			OutParamKeepAlive outAlive = impl.keepDAlive(pka);
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
	 * 存入请求
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/storeInRequset")
	public void storesRequset(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 logger.info("  TerminalBusiness------storesRequset------存入请求---- - ------------");
  	   // logger.info("----1--存入请求-storesRequset------------");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		//转换成对象
		InParamStoreInRequest isr = JsonUtils.toBean(reqBody,InParamStoreInRequest.class); 
	    String outJson = "";
		try {
			/*InParamStoreInRequest isr=new InParamStoreInRequest();
			isr.setTerminalID("1");
			isr.setOpenBoxCode("1");*/
			
			//存入请求实现
			OutParamStoreInDRequest outRequest = impl.storeRequsetNew(isr);
			outJson=JsonUtils.toJSONString(outRequest);			
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
	 * 存入确认
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/storeInConfirm")
	@ResponseBody
	public void storesIn(HttpServletRequest request,HttpServletResponse response) throws IOException {
	    logger.info("  TerminalBusiness------storesIn------存入确认---- - ------------");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		//转换成对象
		InParamStoreInConfirm isc = JsonUtils.toBean(reqBody,InParamStoreInConfirm.class); 
		String outJson = "";
		try {
			/*InParamStoreInConfirm isc=new InParamStoreInConfirm();
			isc.setTerminalID("1");
			isc.setBoxID(1);
			isc.setOpenBoxCode("1");
			isc.setStoreInDate(new Date());
			isc.setMoney(1);*/
			//存入确认实现
			OutParamStoreInConfirm outConfirm = impl.storeIn(isc);
			outJson = JsonUtils.toJSONString(outConfirm);
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
	
	
	
	
	
	//=====================取消下面协议=========================
 
	/**
	 * 取物请求
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 *//*
	@RequestMapping("/takeRequest")
	public void takesRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("  TerminalBusiness------takesRequest-----取物请求---- - ------------");
		 
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		//转换成对象
		InParamTakeOutRequest inRequest = JsonUtils.toBean(reqBody, InParamTakeOutRequest.class);
		String outJson = "";
		try {
			InParamTakeOutRequest inRequest =new InParamTakeOutRequest();
		    inRequest.setTerminalID("1");
		    // inRequest.setBoxID(1);     //要删除的
		    inRequest.setOpenBoxCode("1");
		    //inRequest.setStoreInDate(new Date());   //要删除的
		    
			//取物请求实现
			OutParamTakeOutRequest outRequest = impl.takeRequest(inRequest);
			outJson = JsonUtils.toJSONString(outRequest);
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
	*//**
	 * 取物确认
	 * @param request
	 * @param response
	 * @throws IOException 
	 *//*
	@RequestMapping("/takeOut")
	@ResponseBody
	public void takesOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("  TerminalBusiness------takeOut-----取物确认---- - ------------");
		
	    BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		//转换成对象
		InParamTakeOutConfirm ptoc = JsonUtils.toBean(reqBody, InParamTakeOutConfirm.class); 
		String outJson = "";
		try {
			
			InParamTakeOutConfirm ptoc = new InParamTakeOutConfirm();
 
			ptoc.setTerminalID("1");
			ptoc.setBoxID(1);
			ptoc.setOpenCode("1");
			ptoc.setStorInDate(new Date());
			ptoc.setTakeOutDate(new Date());
			ptoc.setMoney(1);
			
			
			//取物确认实现
			OutParamTakeOutConfirm outConfirm = impl.takeOut(ptoc);
			outJson = JsonUtils.toJSONString(outConfirm);
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
	*/
	
 
	 
}
