package com.hzdongcheng.softwareplatform.controller.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.front.server.model.service.InParam;
import com.hzdongcheng.front.server.model.service.OutParam;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamAlarmNoticesRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInCheckRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirmByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirmOfMidway;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamAlarmNoticesRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamOpenBoxByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInCheckRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirmByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirmOfMidway;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.HttpClient4Guotong;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IJCGService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;

@Controller
@RequestMapping("/terminal")
public class TerminalBusinessBase {
	//private final static Logger logger = Logger.getLogger(TerminalBusiness.class);
	public static Log4jUtils logger = Log4jUtils.createInstanse(TerminalBusinessBase.class);
	@Autowired
	@Qualifier("CoinJCGService")  //@Qualifier("CoinJCGService")  @Qualifier("JCGService")  // @Qualifier("XBSDService") //@Qualifier("ShendaService")
	IJCGService impl;
 
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
 
 
	
	//=============================新的====================================
	/**
	 * 设备报警
	 * @param request
	 * @param response
	 * @throws IOException  
	 *  http://127.0.0.1:80/IntelligentStorageMP-APP/terminal/alarmNotices
     *  http://127.0.0.1:80/IntelligentStorageMP-APP/terminal/alarmNoticesTest
	 */
	@RequestMapping("/alarmNotices")
	@ResponseBody
	public void alarmNotices(HttpServletRequest request,HttpServletResponse response) throws IOException {
	   	//解析JSON字符串
 		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		
		logger.info(" TerminalBusiness alarmNotices  request : " + reqBody);	
		//转换成对象
		InParamAlarmNoticesRequest ps = JsonUtils.toBean(reqBody, InParamAlarmNoticesRequest.class); 
 
	    /**
		    InParamAlarmNoticesRequest ps = new InParamAlarmNoticesRequest();
			ps.setAlarmLevel(1);
			ps.setAlarmType(2);
			ps.setTerminalID("210017005b4031030c010000"); 
		*/
 
		//具体签到实现
		OutParamAlarmNoticesRequest outParam = impl.alarmNotices(ps);
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
	 * 设备报警回调测试
	 */
	@RequestMapping("/alarmNoticesTest")
	@ResponseBody
	public void alarmNoticesTest(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		
		String gui_no = request.getParameter("gui_no"); 
		String alarm_type = request.getParameter("alarm_type");  
		String alarm_level = request.getParameter("alarm_level"); 
		String app_key = request.getParameter("app_key");  
	    String sign = request.getParameter("sign"); 
		
	    String app_secret="app_cqjdhygy_180000";
	    StringBuffer signBuffer = new StringBuffer();
        signBuffer.append(app_secret)
        .append("alarm_level").append(alarm_level)
        .append("alarm_type").append(alarm_type)
        .append("app_key").append(app_key)
        .append("gui_no").append(gui_no)
        .append(app_secret);

        String sign_https = HttpClient4Guotong.signMD5(signBuffer.toString());
        System.out.println("  gui_no="+gui_no+" alarm_type="+alarm_type+" alarm_level="+alarm_level);
        System.out.println("  app_key="+app_key+" sign="+sign+" sign_https="+sign_https);
    
        if(sign.equals(sign_https)){
        	System.out.println("====(sign=sign_https)===========");
        }
		//对象转json数据
		String outJson= "{\"err_code\":\"0\",\"err_msg\":\"ok\"}";
		outJson=    JSON.parse(outJson).toString();
		//String outJson = JsonUtils.toJSONString(outParam);
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
	 * 新协议     存入检测请求
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/storeInCheckRequset")
	public void storeInCheckRequset(HttpServletRequest request,HttpServletResponse response) throws IOException {
	    //logger.info("----0--存入检测请求-storeInCheckRequset------------HttpClient4Guotong.apiURL="+HttpClient4Guotong.apiURL);
	    logger.info("  TerminalBusiness------storeInCheckRequset------存入检测请求---- - ------------");
 
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		//转换成对象
		InParamStoreInCheckRequest isr = JsonUtils.toBean(reqBody,InParamStoreInCheckRequest.class);
	    String outJson = "";
		try {
			//存入检测请求实现
			OutParamStoreInCheckRequest outRequest = impl.storeCheckRequset(isr);
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
			OutParamStoreInRequest outRequest = impl.storeRequset(isr);
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
	/**
	 * 取物请求
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
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
			/*InParamTakeOutRequest inRequest =new InParamTakeOutRequest();
		    inRequest.setTerminalID("1");
		    inRequest.setBoxID(1);
		    inRequest.setOpenBoxCode("1");
		    inRequest.setStoreInDate(new Date());*/
 
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
	/**
	 * 取物确认
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
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
 
			/*InParamTakeOutConfirm ptoc = new InParamTakeOutConfirm();
			ptoc.setTerminalID("1");
			ptoc.setBoxID(1);
			ptoc.setOpenCode("1");
			ptoc.setStorInDate(new Date());
			ptoc.setTakeOutDate(new Date());
			ptoc.setMoney(1);*/
 
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//=============================老的====================================
	/**
	 * 中途取物确认
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/takeOutOfMidway")
	@ResponseBody
	public void takeOutOfMidway(HttpServletRequest request,HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		//转换成对象
		InParamTakeOutConfirmOfMidway ptoc = JsonUtils.toBean(reqBody, InParamTakeOutConfirmOfMidway.class);
		String outJson = "";
		try {
			//取物确认实现
			OutParamTakeOutConfirmOfMidway outConfirm = impl.takeOutOfMidway(ptoc);
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

	/**
	 * 查询在用箱列表接口
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/queryInBoxList")
	public void queryInBoxList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		InParam inParam = JsonUtils.toBean(reqBody, InParam.class);
		List<BoxEntity> list = impl.queryInBoxList(inParam);
		String outJson = JsonUtils.toJSONString(list);
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
	 * 查询违规箱列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/queryViolationBoxList")
	public void queryViolationBoxList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		InParam inParam = JsonUtils.toBean(reqBody, InParam.class);
		List<BoxEntity> list = impl.queryViolationBoxList(inParam);
		String outJson = JsonUtils.toJSONString(list);
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
	 * 管理刷卡开箱
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/openBoxByManager")
	@ResponseBody
	public void openBoxByManager(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//解析JSON字符串
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		
		System.out.println("request : " + reqBody);	
		
		//转换成对象
		InParamTakeOutConfirmByManager ps = JsonUtils.toBean(reqBody, InParamTakeOutConfirmByManager.class);
		
		//具体实现
		OutParamOpenBoxByManager entity = impl.openBoxByManager(ps);

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
	 * 管理取物确认
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/takeOutConfirmByManager")
	@ResponseBody
	public void takeOutConfirmByManager(HttpServletRequest request,HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null){  
			sb.append(line);  
		}  
		String reqBody = sb.toString(); 
		reqBody = URLDecoder.decode(reqBody, "UTF-8");
		
		//转换成对象
		InParamTakeOutConfirmByManager inParam = JsonUtils.toBean(reqBody, InParamTakeOutConfirmByManager.class);
		System.out.println(inParam.getOpenCode());
		String outJson = "";
		try {
			//取物确认实现
			OutParamTakeOutConfirmByManager outConfirm = impl.takeOutConfirmByManager(inParam);		
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
	/**
	 * 设备连接
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
		
		System.out.println("request : " + reqBody);	
		
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
