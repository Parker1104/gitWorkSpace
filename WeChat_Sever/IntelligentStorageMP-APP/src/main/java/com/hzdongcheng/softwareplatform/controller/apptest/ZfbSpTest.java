package com.hzdongcheng.softwareplatform.controller.apptest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.softwareplatform.controller.apptest.wstest.OutParamLoginInfo;
import com.hzdongcheng.softwareplatform.controller.apptest.wstest.OutParamZfbPayBack;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/zfbsptest")
public class ZfbSpTest {
	public static Log4jUtils logger = Log4jUtils.createInstanse(ZfbSpTest.class);
	 /**
	  * 支付宝小程序 登录测试
	 */
	 @RequestMapping(value = "/logininfo" ,  method=RequestMethod.POST)  
	 @ResponseBody
	 //public void logininfo(@RequestBody InParamLoginInfo inParamLoginInfo,HttpServletRequest request, HttpServletResponse response) throws IOException {
	 public void logininfo(@RequestBody String jsonObj,HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/Json; charset=utf-8");
		JSONObject  rliJSONObject  = JSONObject.fromObject(jsonObj);
 		String nickName = rliJSONObject.getString("nickName");
		String terminalid = rliJSONObject.getString("terminalid");
		String openId = rliJSONObject.getString("openId");
		String city = rliJSONObject.getString("city");
		
		
		System.out.println("1-------ZfbSpTest---logininfo----------nickName="+nickName+" terminalid="+terminalid+" openId="+openId);
 
		OutParamLoginInfo opli=new OutParamLoginInfo();
		opli.setTerminalid(terminalid);
		opli.setOpenId(openId);
		Date dates=new Date();
		opli.setCreatetime(dates.toLocaleString());
		String outJson = "{\"err_code\":\"0\",\"err_msg\":\"ok\",\"datas\":{\"terminalid\":\""+terminalid+"\",\"openId\":\""+openId+"\",\"nickName\":\""+nickName+"\",\"city\":\""+city+"\"   } }";
		System.out.println("2-------ZfbSpTest---logininfo----------outJson="+outJson);
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
	  * 支付宝小程序 登录测试
	 */
	 @RequestMapping(value = "/payes" ,  method=RequestMethod.POST)  
	 @ResponseBody
	 //public void logininfo(@RequestBody InParamLoginInfo inParamLoginInfo,HttpServletRequest request, HttpServletResponse response) throws IOException {
	 public void payes(@RequestBody String jsonObj,HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("application/Json; charset=utf-8");
		JSONObject  rliJSONObject  = JSONObject.fromObject(jsonObj);
 		String openId = rliJSONObject.getString("openId");
		String tradewaterno = rliJSONObject.getString("tradewaterno");
		String paymentes = rliJSONObject.getString("paymentes");
 
		System.out.println("1 ZfbSpTest---payes  openId="+openId+" tradewaterno="+tradewaterno+" paymentes="+paymentes);
 
		OutParamZfbPayBack opzpb=new OutParamZfbPayBack();
 
		String outJson = "{\"err_code\":\"0\",\"err_msg\":\"ok\",\"datas\":{\"openId\":\""+openId+"\",\"tradewaterno\":\""+tradewaterno+"\",\"paymentes\":\""+paymentes+"\"   } }";
		System.out.println("2-------ZfbSpTest---payes----------outJson="+outJson);
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















