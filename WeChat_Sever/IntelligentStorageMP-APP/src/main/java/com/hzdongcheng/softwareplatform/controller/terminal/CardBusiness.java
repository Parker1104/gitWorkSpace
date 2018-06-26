package com.hzdongcheng.softwareplatform.controller.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCCheckOutUser;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBackCardAndBox;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBoxIDCardLossRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBoxIDChangeCardIDConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamCardIDCheckOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamCardIDLogInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUpdatePassword;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUserCheckOutbyDay;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUserLoginCheck;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamBackCardAndBox;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamUserCheckOutbyDay;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamUserLoginCheck;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.factory.PushServiceFactory;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICardAndBoxBound;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.ICashierService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BackCardAndBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OutParamBoxIDCardLossRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordByCard;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;

@Controller
@RequestMapping("/terminal")
public class CardBusiness {
	
	@Autowired
	ICashierService impl;
	@Autowired
	ICardAndBoxBound icard;
	/**
	 * 根据卡号获取卡箱对应信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/cardIDLogInRequest")
	@ResponseBody
	public void cardIDLogInRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamBackCardAndBox ps = JsonUtils.toBean(reqBody, InParamBackCardAndBox.class);
		
		//具体查询实现
		BackCardAndBox entity = impl.CardIDLogInRequest(ps);

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
	 * 用户卡登记
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/cardIDLogInConfirm")
	@ResponseBody
	public void cardIDLogInConfirm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamCardIDLogInConfirm ps = JsonUtils.toBean(reqBody, InParamCardIDLogInConfirm.class);
		
		//具体卡登记实现
		OutParamStoreInConfirm list = impl.CardIDLogInConfirm(ps);

		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		//对象转json数据
		String outJson = JsonUtils.toJSONString(list);
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
	 * 根据卡号获取存取记录信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/cardIDLogOutRequest")
	@ResponseBody
	public void cardIDCheckOutRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamBackCardAndBox ps = JsonUtils.toBean(reqBody, InParamBackCardAndBox.class);
		
		//具体查询实现
		StoreInRecordByCard entity = impl.cardIDCheckOutRequest(ps);

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
	 * 卡结账
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/cardIDLogOutConfirm")
	@ResponseBody
	public void cardIDCheckOutConfirm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamCardIDCheckOutConfirm ps = JsonUtils.toBean(reqBody, InParamCardIDCheckOutConfirm.class);
		
		//具体卡登记实现
		OutParamTakeOutConfirm list = impl.cardIDCheckOutConfirm(ps);

		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		//对象转json数据
		String outJson = JsonUtils.toJSONString(list);
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
//	@RequestMapping("/openBoxByManager")
//	@ResponseBody
//	public void openBoxByManager(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		
//		//解析JSON字符串
//		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
//		String line = null;  
//		StringBuilder sb = new StringBuilder();  
//		while((line = br.readLine())!=null){  
//			sb.append(line);  
//		}  
//		String reqBody = sb.toString(); 
//		reqBody = URLDecoder.decode(reqBody, "UTF-8");
//		
//		System.out.println("request : " + reqBody);	
//		
//		//转换成对象
//		InParamBackCardAndBox ps = JsonUtils.toBean(reqBody, InParamBackCardAndBox.class);
//		
//		//具体实现
//		OpenBoxByManager entity = impl.openBoxByManager(ps);
//
//		response.setCharacterEncoding("UTF-8"); 
//		response.setContentType("application/json; charset=utf-8");
//		//对象转json数据
//		String outJson = JsonUtils.toJSONString(entity);
//		PrintWriter out = null; 
//		try {
//			out = response.getWriter(); 
//			out.write(outJson);
//		} catch (IOException e) { 
//			e.printStackTrace(); 
//		} finally {
//			if (out != null) {
//				out.close(); 
//			} 
//		}
//	}
	/**
	 * 柜端用户登录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/userLogin")
	@ResponseBody
	public void userLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamUserLoginCheck ps = JsonUtils.toBean(reqBody, InParamUserLoginCheck.class);
		
		//具体查询实现
		OutParamUserLoginCheck entity = impl.userLogin(ps);

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
	 * 根据箱门号获取存物信息(挂失请求)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/boxIDCardLossRequest")
	@ResponseBody
	public void boxIDCardLossRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamBoxIDCardLossRequest ps = JsonUtils.toBean(reqBody, InParamBoxIDCardLossRequest.class);
		
		//具体查询实现
		OutParamBoxIDCardLossRequest entity = impl.boxIDCardLossRequest(ps);

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
	 * 挂失授权
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/boxIDCardLossAuthorize")
	@ResponseBody
	public void boxIDCardLossAuthorize(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamBackCardAndBox ps = JsonUtils.toBean(reqBody, InParamBackCardAndBox.class);
		
		//具体授权实现
		OutParamBackCardAndBox entity = impl.boxIDCardLossAuthorize(ps);

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
	 * 用户卡挂失 注销卡
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NotFoundNetClientException 
	 * @throws MessageRecvTimeOutException 
	 * @throws MessageSendException 
	 */
	@RequestMapping("/boxIDCardLossConfirm")
	@ResponseBody
	public void boxIDCardLossConfirm(HttpServletRequest request,HttpServletResponse response) throws IOException, MessageSendException, MessageRecvTimeOutException, NotFoundNetClientException {
		
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
		InParamBackCardAndBox ps = JsonUtils.toBean(reqBody, InParamBackCardAndBox.class);
		
		//具体授权实现
		OutParamBackCardAndBox entity = impl.boxIDCardLossConfirm(ps);
		CardAndBoxBoundEntity card = icard.selectByCardid(ps.getCardID());
		IPushClient pushClient = PushServiceFactory.createInstanse();				
		pushClient.setRecvTimeoutMills(5000);
		pushClient.connect();
		
		IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
		InParamRCCheckOutUser inParams = new InParamRCCheckOutUser();				
		inParams.setTerminalID(card.getTerminalid());
		inParams.setCardID(ps.getCardID());
		inParams.setBoxID(card.getBoxid());				
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		//对象转json数据
		String outJson = JsonUtils.toJSONString(entity);
		PrintWriter out = null; 
		try {
			jcgCtrl.checkOutUser("tcp://127.0.0.1:55666", inParams);
			System.out.println("注销成功");
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
	 * 用户补卡
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/boxIDCardChangeConfirm")
	@ResponseBody
	public void boxIDCardChangeConfirm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamBoxIDChangeCardIDConfirm ps = JsonUtils.toBean(reqBody, InParamBoxIDChangeCardIDConfirm.class);
		
		//具体授权实现
		OutParamBackCardAndBox entity = impl.boxIDChangeCardIDConfirm(ps);

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
	 * 直接补卡
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/boxIDCardChangeRequest")
	@ResponseBody
	public void boxIDCardChangeRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamBoxIDCardLossRequest ps = JsonUtils.toBean(reqBody, InParamBoxIDCardLossRequest.class);
		
		//具体实现
		OutParamBoxIDCardLossRequest entity = impl.boxIDCardChangeRequest(ps);

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
	 * 收银员结账
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/userCheckOutbyDay")
	@ResponseBody
	public void userCheckOutbyDay(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamUserCheckOutbyDay ps = JsonUtils.toBean(reqBody, InParamUserCheckOutbyDay.class);
		
		//具体授权实现
		OutParamUserCheckOutbyDay entity = impl.userCheckOutbyDay(ps);

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
	 * 柜端修改密码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/userModifyPWD")
	@ResponseBody
	public void userModifyPWD(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
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
		InParamUpdatePassword ps = JsonUtils.toBean(reqBody, InParamUpdatePassword.class);
		
		//具体实现
		OutParamStoreInConfirm list = impl.updatePassword(ps);

		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/json; charset=utf-8");
		//对象转json数据
		String outJson = JsonUtils.toJSONString(list);
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
