package com.hzdongcheng.softwareplatform.controller.industrialcontrol;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;




@Controller
@RequestMapping("/industrialTest")
public class IndustrialTest {
	/**
	 * 测试断网
	 */
	@RequestMapping("/disconnectTest")
	@ResponseBody
	public void disconnectTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");

		// 对象转json数据
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\",\"datas\":{\"terminalID\":\"" + terminalID
				+ "\"     } }";
		outJson = JSON.parse(outJson).toString();
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
