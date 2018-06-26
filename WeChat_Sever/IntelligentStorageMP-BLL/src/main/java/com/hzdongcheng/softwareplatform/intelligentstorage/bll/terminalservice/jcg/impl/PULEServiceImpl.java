package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.front.server.model.service.InParam;
import com.hzdongcheng.front.server.model.service.OutParam;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IPULEService;

import net.sf.json.JSONObject;
 

@Service("PULEService")
public class PULEServiceImpl  implements IPULEService {
	 public static Log4jUtils logger = Log4jUtils.createInstanse(PULEServiceImpl.class);
	
	 public final static String err_code="err_code" ; //0:表示成功 非0:不成功
	 public final static String err_msg="err_msg";    //描述
	 public final static String datas="datas" ;       //数据
 
 
	 //1===========基础协议==========================================
	 public static void main(String[] args) {
 
	 }
	 
	
	 
    //设备断开
	@Override
	public OutParam disconnect(InParam inParam) {
 
		OutParam outParam = new OutParam();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			String terminalID   = inParam.getTerminalID();
			logger.info("1 断网请求  PULEServiceImpl   disconnect   terminalID="+terminalID   );
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
 
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesPULE.disconnect_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code);    //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);      //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1  PULEServiceImpl  disconnect  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 PULEServiceImpl  disconnect  error    resultMsg = null ");
            }
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  PULEServiceImpl  disconnect  fail the reason is:  "+e );
		}
		return outParam;
 
	}
 
 
	@Override
	public OutParamKeepAlive keepAlive(InParamKeepAlive inParam) {
		// return super.keepAlive(inParam);
		OutParamKeepAlive outParam = new OutParamKeepAlive();
		outParam.setLength(7);
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		
		try {
			//获取请求数据
			String terminalid  = inParam.getTerminalID();
			int totalBoxNums   = inParam.getTotalBoxNums();
			
			byte [] goodsStatus = inParam.getGoodsStatusArray();
			StringBuffer goodsStatusSbs=new StringBuffer();
			for(int i=0;i<goodsStatus.length;i++){
				goodsStatusSbs.append(goodsStatus[i]);
			}
			
			byte [] openStatus  = inParam.getOpenStatusArray();
			StringBuffer openStatusSbs=new StringBuffer();
			for(int i=0;i<openStatus.length;i++){
				openStatusSbs.append(openStatus[i]);
			}
			
		   /**
             String dumpEnergy = inParam.getDumpEnergy()+""; //电量
		     String longitude  = inParam.getLongitude();  //经度
		     String latitude   = inParam.getLatitude();   //纬度
		     String rfid       = inParam.getRfid();       //RFID
		     int  guistatus    = inParam.getGuistatus();  //柜子状态  0:立 1:倒
           */	
			
			logger.info("1 心跳请求   PULEServiceImpl   keepAlive   terminalID="+terminalid+",totalBoxNums="+totalBoxNums+ 
				    ", openStatus="+openStatus+" goodsStatusSbs="+goodsStatusSbs  );
			
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalid);
			params.put("totalBoxNums", totalBoxNums+"");
			params.put("goodsStatus", goodsStatusSbs.toString() );
			params.put("openStatus",  openStatusSbs.toString() );
 
	
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesPULE.keepAlive_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code);    //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);      //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1  PULEServiceImpl  keepAlive  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
	        }else{
	        	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
	        	logger.error("2 PULEServiceImpl  keepAlive  error    resultMsg = null ");
	        }
 
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  PULEServiceImpl  keepAlive  fail the reason is:  "+e );
		}
		return outParam;
	}
		
 
	@Override
	public OutParamSignIn sign(InParamSignIn inParam) {
		//return super.sign(inParam);
		OutParamSignIn outParam = new OutParamSignIn();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
 
		try {
			String terminalID   = inParam.getTerminalID();
			String dispalyName  = inParam.getDisplayName();
			int totalBoxNums    = inParam.getTotalBoxNums();
			String ipAddress    = inParam.getIpAddr();
			Date curDate        = DateUtils.nowDate();
			
			logger.info("1 签到请求   PULEServiceImpl   sign   terminalID="+terminalID+",dispalyName="+dispalyName+",totalBoxNums="+totalBoxNums+
					    ",ipAddress="+ipAddress+" curDate="+curDate.toLocaleString()  );
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
			params.put("dispalyName", dispalyName);
			params.put("totalBoxNums", totalBoxNums+"");
			params.put("ipAddress", ipAddress);
			params.put("curDate", curDate.toLocaleString());
 
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesPULE.sign_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code);    //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);      //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1  PULEServiceImpl  sign  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 PULEServiceImpl  sign  error    resultMsg = null ");
            }
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  PULEServiceImpl  sign  fail the reason is:  "+e );
		}
		outParam.setLength(0);
		return outParam;
 
	}




 
	 
}














