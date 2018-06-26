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

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/terminalWixinSpTest")
public class TerminalWixinSpTest {
	public static Log4jUtils logger = Log4jUtils.createInstanse(TerminalWixinSpTest.class);
	/**
	 * 微信小程序 登录测试
	 */
	@RequestMapping(value = "/logininfo" ,  method=RequestMethod.POST)  
	@ResponseBody
	//public void logininfo(@RequestBody InParamLoginInfo inParamLoginInfo,HttpServletRequest request, HttpServletResponse response) throws IOException {
	 public void logininfo(@RequestBody String jsonObj,HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		//response.setContentType("application/Json; charset=utf-8");
		JSONObject  rliJSONObject  = JSONObject.fromObject(jsonObj);
 		String nickName = rliJSONObject.getString("nickName");
		String terminalid = rliJSONObject.getString("terminalid");
		String openId = rliJSONObject.getString("openId");
		System.out.println("1-------TerminalWixinSpTest---logininfo----------nickName="+nickName+" terminalid="+terminalid+" openId="+openId);
		
		
		OutParamLoginInfo opli=new OutParamLoginInfo();
		opli.setTerminalid(terminalid);
		opli.setOpenId(openId);
		Date dates=new Date();
		opli.setCreatetime(dates.toLocaleString());
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\",\"datas\":{\"terminalid\":\""+terminalid+"\",\"openId\":\""+openId+"\",\"createtime\":\""+dates.toLocaleString()+"\"   } }";
		System.out.println("2-------TerminalWixinSpTest---logininfo----------outJson="+outJson);
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
