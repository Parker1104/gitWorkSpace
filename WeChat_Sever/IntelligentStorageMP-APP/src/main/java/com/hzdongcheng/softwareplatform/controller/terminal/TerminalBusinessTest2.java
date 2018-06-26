package com.hzdongcheng.softwareplatform.controller.terminal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

@Controller
@RequestMapping("/terminaltestpule")
public class TerminalBusinessTest2 {
	public static Log4jUtils logger = Log4jUtils.createInstanse(TerminalBusinessTest2.class);

 
	/**
	 *  垃圾箱    1:二维码 2称重  3:箱满提示
	 */
	@RequestMapping("/typeInTest")
	@ResponseBody
	public void typeInZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");
		String boxId = request.getParameter("boxId");
		String types = request.getParameter("types");
		//String typesLength = request.getParameter("typesLength");
		String typesDataArray = request.getParameter("typesDataArray");
 

		// 对象转json数据
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\",\"datas\":{\"terminalID\":\"" + terminalID + "\",\"boxId\":\"" + boxId + "\" ,\"types\":\"" + types + "\" ,\"typesDataArray\":\"" + typesDataArray + "\"     } }";
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
	
	
	
	
	
	
	/**
	 * 测试断网
	 */
	@RequestMapping("/disconnectTest")
	@ResponseBody
	public void disconnectZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

	/**
	 * 测试心跳
	 */
	@RequestMapping("/keepsAliveTest")
	@ResponseBody
	public void keepsAliveZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");
		String totalBoxNums = request.getParameter("totalBoxNums");
		String goodsStatus = request.getParameter("goodsStatus");
		String openStatus = request.getParameter("openStatus");

		// 对象转json数据
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\",\"datas\":{\"terminalID\":\"" + terminalID
				+ "\",\"totalBoxNums\":\"" + totalBoxNums + "\"" + ",\"goodsStatus\":\"" + goodsStatus
				+ "\"  ,\"openStatus\":\"" + openStatus + "\"      } }";
		outJson = JSON.parse(outJson).toString();
		// String bToObject=(String) JSON.parse(resultes);
		// String outJson = JsonUtils.toJSONString(outParam);
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
	 * 测试 签到
	 */
	@RequestMapping("/signInTest")
	@ResponseBody
	public void signInZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");
		String dispalyName = request.getParameter("dispalyName");
		String totalBoxNums = request.getParameter("totalBoxNums");
		String ipAddress = request.getParameter("ipAddress");
		String curDate = request.getParameter("curDate");

		// 对象转json数据
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\",\"datas\":{\"terminalID\":\"" + terminalID
				+ "\",\"dispalyName\":\"" + dispalyName + "\"" + ",\"totalBoxNums\":\"" + totalBoxNums
				+ "\"  ,\"ipAddress\":\"" + ipAddress + "\" ,\"curDate\":\"" + curDate + "\"       } }";
		outJson = JSON.parse(outJson).toString();
		// String bToObject=(String) JSON.parse(resultes);
		// String outJson = JsonUtils.toJSONString(outParam);
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

	public static AtomicInteger count = new AtomicInteger(0);

	/**
	 * 测试存入请求
	 */
	@RequestMapping("/storeRequsetTest")
	@ResponseBody
	public void storeRequsetZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");
		String openBoxCode = request.getParameter("openBoxCode");
		/**
		 * 刷卡请求： errorCode： public static int ERR_OK = 00000000; //成功 public
		 * static int ERR_zgh_borrow = 00000301; //借用 public static int
		 * ERR_zgh_return = 00000302; //归还 public static int ERR_zgh_return_up =
		 * 00000303; //请先归还上一个主机的物品
		 */
		count.incrementAndGet();
		String err_code = "00000000";
		if (count.get() == 1) {
			err_code = "00000000"; // 成功
		} else if (count.get() == 2) {
			err_code = "00000301"; // 借用
		} else if (count.get() == 3) {
			err_code = "00000302"; // 归还
		} else if (count.get() == 4) {
			err_code = "00000303"; // 请先归还上一个主机的物品
		}
		if (count.get() == 5) {
			count.set(0);
		}

		// 对象转json数据
		String outJson = "{\"err_code\":\"" + err_code + "\",\"err_msg\":\"ok\",\"datas\":{\"terminalID\":\""
				+ terminalID + "\",\"openBoxCode\":\"" + openBoxCode + "\",\"boxID\":\"25\"} }";
		outJson = JSON.parse(outJson).toString();
		// String bToObject=(String) JSON.parse(resultes);
		// String outJson = JsonUtils.toJSONString(outParam);
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
	 * 测试存入请求 确认
	 */
	@RequestMapping("/storeInTest")
	@ResponseBody
	public void storeInZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");
		String boxID = request.getParameter("boxID");
		String openBoxCode = request.getParameter("openBoxCode");
		String storeInTime = request.getParameter("storeInTime");
		String money = request.getParameter("money");

		// 对象转json数据
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\"," + "\"datas\":{\"terminalID\":\"" + terminalID
				+ "\",\"boxID\":\"" + boxID + "\",\"openBoxCode\":\"" + openBoxCode + "\"," + "\"storeInTime\":\""
				+ storeInTime + "\",\"money\":\"" + money + "\"} }";
		outJson = JSON.parse(outJson).toString();
		// String bToObject=(String) JSON.parse(resultes);
		// String outJson = JsonUtils.toJSONString(outParam);
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
	 * 测试取请求
	 */
	@RequestMapping("/takeRequestTest")
	@ResponseBody
	public void takeRequestZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");
		// String boxID = request.getParameter("boxID");
		String openBoxCode = request.getParameter("openBoxCode");
		// String storeInTime = request.getParameter("storeInTime");

		// 对象转json数据
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\"," + "\"datas\":{\"terminalID\":\"" + terminalID
				+ "\" ," + "\"openBoxCode\":\"" + openBoxCode + "\" } }";
		outJson = JSON.parse(outJson).toString();
		// String bToObject=(String) JSON.parse(resultes);
		// String outJson = JsonUtils.toJSONString(outParam);
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
	 * 测试存入请求 确认
	 */
	@RequestMapping("/takeOutTest")
	@ResponseBody
	public void takeOutZGH(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("========alarmNoticesTest=======");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		String terminalID = request.getParameter("terminalID");
		String boxID = request.getParameter("boxID");
		String openBoxCode = request.getParameter("openBoxCode");
		String storeInTime = request.getParameter("storeInTime");
		String takeOutTime = request.getParameter("takeOutTime");
		String money = request.getParameter("money");

		// 对象转json数据
		String outJson = "{\"err_code\":\"00000000\",\"err_msg\":\"ok\"," + "\"datas\":{\"terminalID\":\"" + terminalID
				+ "\",\"boxID\":\"" + boxID + "\"" + ",\"openBoxCode\":\"" + openBoxCode + "\",\"storeInTime\":\""
				+ storeInTime + "\"" + ",\"takeOutTime\":\"" + takeOutTime + "\",\"money\":\"" + money + "\"} }";
		outJson = JSON.parse(outJson).toString();
		// String bToObject=(String) JSON.parse(resultes);
		// String outJson = JsonUtils.toJSONString(outParam);
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
