package com.hzdongcheng.softwareplatform.controller.appmanager;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcdzsoft.config.ErrorMsgConfig;
//import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.packet.JsonPacket;
import com.dcdzsoft.packet.PacketUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.controller.appmanager.proxys.MobileProxy;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.ApplicationConfig;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.memcached.MemCacheManager;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.util.WebUtils;
import net.sf.json.JSONObject;


 //===
@SuppressWarnings("all")
@Controller
@RequestMapping("/appAction")
public class AppesController extends BaseController{
	private final static Logger log = Logger.getLogger(AppesController.class);
	private static Class proxyClass = null;
	private static MobileProxy mobileProxy = null;
	static {
		try {
 
			proxyClass = Class.forName("com.hzdongcheng.softwareplatform.controller.appmanager.proxys.MobileProxy");
			mobileProxy = (MobileProxy) proxyClass.newInstance();
		} catch (Exception e) {}
	}
	@RequestMapping(value="/mobileservice.do",  method = {RequestMethod.GET, RequestMethod.POST } ,produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
	public String mobileservice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/Json;charset=utf8");
		String message = WebUtils.readRequestData(request);
		String resultMsg = "";
	    log.info("AppController mobileservice  request="  + message);
		try{
		    JSONObject json = JSONObject.fromObject(message);
			if(json != null){
				JsonPacket packet = (JsonPacket)JSONObject.toBean(json,JsonPacket.class);
				if(packet != null){
                    if(packet._CmdType == JsonPacket.MSG_SENT_BY_SERVER){
                        resultMsg = handleRequest(packet, message);
                    }
                }
			}else{
				log.error("[AppController mobileservice   unpack error:],msg=" + message);
			}
		}catch(Exception e){
			log.error("[ AppController mobileservice  handle request:],error= " + e.getMessage() + ",msg=" + message );
		}
		//返回处理结果
	    /**	PrintWriter out = response.getWriter();  out.println(resultMsg); out.flush();  out.close();*/
		return resultMsg;
	}
	/**
	 * 处理客户端主动发起的请求交易
	 * @param packet
	 */
	protected String handleRequest(JsonPacket packet,String message){
		String responseMsg = "";
		String serviceName = packet._ServiceName;
		if(StringUtils.isNotEmpty(serviceName) && serviceName.length() > 10){
			String dtoName = "com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto." + packet._ServiceName;
		    //System.out.println("handleRequest="  + dtoName);
			try{
				Class inClass = Class.forName(dtoName);
				Object inParam = PacketUtils.buildBussinessDTOParam(packet,inClass);
				Method method = proxyClass.getMethod("doBusiness", new Class[]{inClass});
                Object result = method.invoke(mobileProxy, new Object[]{inParam});
                //构造响应包
                responseMsg =  PacketUtils.buildSuccessResult(packet,result);
                if(responseMsg.length()>100 && packet._ServiceName.endsWith("Qry")){
                    log.info("AppController handleRequest    _dcdz_response=>" + responseMsg.substring(0,100));
                }else{
                    log.info("AppController handleRequest    _dcdz_response=>" + responseMsg);
                }
			} catch(java.lang.ClassNotFoundException e0){
				log.error("[AppController handleRequest   ClassNotFoundException:],errmsg=" + e0.getMessage() + ",msg=" + message);
			} catch (NoSuchMethodException e1){
				log.error("[AppController handleRequest   NoSuchMethodException:],errmsg=" + e1.getMessage() + ",msg=" + message);
			} catch (java.lang.reflect.InvocationTargetException e2) {
				
			   /** 
				    String errorMsg = ErrorMsgConfig.getLocalizedMessage(e2.getTargetException().getMessage());
	                if("zh".equalsIgnoreCase(ApplicationConfig.getInstance().getLocale())){
	                    responseMsg = PacketUtils.buildFailResult(packet, errorMsg);
	                }else{
	                    responseMsg = PacketUtils.buildFailResult(packet, e2.getTargetException().getMessage());
	                } 
	                System.out.println(" 1  errorMsg="+errorMsg);
	                System.out.println(" 2  responseMsg="+responseMsg);
                */
			    String errorMsg = e2.getTargetException().getMessage();
			    //System.out.println("2  errorMsg="+errorMsg);
				responseMsg = PacketUtils.buildFailResult(packet, errorMsg);
                log.error("[AppController handleRequest   Buiness Error:],errmsg=" + errorMsg + ",msg=" + message);
				//if(apCfg.isLogRawMsg())
          		log.info("AppController handleRequest   response=" + responseMsg);
            } catch(Exception e3){
				log.error("[AppController handleRequest   Service Error:],errmsg=" + e3.getMessage() + ",msg=" + message);
			}
		}
		return responseMsg;
	}  
}