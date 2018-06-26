package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.front.server.model.service.InParam;
import com.hzdongcheng.front.server.model.service.OutParam;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamDKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInDRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IZGHService;
import net.sf.json.JSONObject;

@Service("ZGHService")
public class ZGHServiceImpl  implements IZGHService {
	 public static Log4jUtils logger = Log4jUtils.createInstanse(ZGHServiceImpl.class);
	
	 public final static String err_code="err_code" ; //0:表示成功 非0:不成功
	 public final static String err_msg="err_msg";    //描述
	 public final static String datas="datas" ;       //数据
 
 
	 //1===========基础协议==========================================
	 public static void main(String[] args) {
 
	 }
	 
	 
	 
    //设备断开
	@Override
	public OutParam disconnect(InParam inParam) {
		/*OutParam outParam = new OutParam();
		TerminalEntity terminalEntity = new TerminalEntity();
		terminalEntity.setTerminalid(ps.getTerminalID());
		terminalEntity.setNetworkstate((byte)1);
		int a = terminalExDao.updateByPrimaryKeySelective(terminalEntity);
		if (a==1) {
			outParam.setErrorCode(JCGErrorCode.ERR_OK);
			return outParam;
		}else {
			outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
			return outParam;
		}*/		
 
		OutParam outParam = new OutParam();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			String terminalID   = inParam.getTerminalID();
			logger.info("1 断网请求   ZGHServiceImpl   disconnect   terminalID="+terminalID   );
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
 
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.sign_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code);    //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);      //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1  HttpClientesZGH  disconnect  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 ZGHServiceImpl  disconnect  error    resultMsg = null ");
            }
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  ZGHServiceImpl  disconnect  fail the reason is:  "+e );
		}
		return outParam;
 
	}
		
		
		
 
	@Override
	public OutParamKeepAlive keepDAlive(InParamDKeepAlive inParam) {
		// return super.keepAlive(inParam);
		OutParamKeepAlive outParam = new OutParamKeepAlive();
		outParam.setLength(7);
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		
		try {
			//获取请求数据
			String terminalid  = inParam.getTerminalID();
			int totalBoxNums   = inParam.getTotalBoxNums();
			
			/*byte [] goodsStatus = inParam.getGoodsStatusArray();
			StringBuffer goodsStatusSbs=new StringBuffer();
			for(int i=0;i<goodsStatus.length;i++){
				goodsStatusSbs.append(goodsStatus[i]);
			}*/
			
			//byte [] openStatus  = inParam.getOpenStatusArray();
			int [] openStatus  = inParam.getOpenStatusIntArray();
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
			
			logger.info("1心跳请求   ZGHServiceImpl   keepAlive   terminalID="+terminalid+",totalBoxNums="+totalBoxNums+ 
				    ", openStatus="+openStatus  );
			
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalid);
			params.put("totalBoxNums", totalBoxNums+"");
			//params.put("goodsStatus", goodsStatusSbs.toString() );
			params.put("openStatus",  openStatusSbs.toString() );
 
	
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.keepAlive_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code);    //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);      //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1  HttpClientesZGH  keepAlive  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
	        }else{
	        	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
	        	logger.error("2 ZGHServiceImpl  keepAlive  error    resultMsg = null ");
	        }
 
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  ZGHServiceImpl  keepAlive  fail the reason is:  "+e );
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
			
			/*byte [] goodsStatus = inParam.getGoodsStatusArray();
			StringBuffer goodsStatusSbs=new StringBuffer();
			for(int i=0;i<goodsStatus.length;i++){
				goodsStatusSbs.append(goodsStatus[i]);
			}*/
			
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
			
			logger.info("1 心跳请求   ZGHServiceImpl   keepAlive   terminalID="+terminalid+",totalBoxNums="+totalBoxNums+ 
				    ", openStatus="+openStatus  );
			
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalid);
			params.put("totalBoxNums", totalBoxNums+"");
			//params.put("goodsStatus", goodsStatusSbs.toString() );
			params.put("openStatus",  openStatusSbs.toString() );
 
	
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.keepAlive_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code);    //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);      //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1  HttpClientesZGH  keepAlive  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
	        }else{
	        	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
	        	logger.error("2 ZGHServiceImpl  keepAlive  error    resultMsg = null ");
	        }
 
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  ZGHServiceImpl  keepAlive  fail the reason is:  "+e );
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
			
			logger.info("1 签到请求   ZGHServiceImpl   sign   terminalID="+terminalID+",dispalyName="+dispalyName+",totalBoxNums="+totalBoxNums+
					    ",ipAddress="+ipAddress+" curDate="+curDate.toLocaleString()  );
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
			params.put("dispalyName", dispalyName);
			params.put("totalBoxNums", totalBoxNums+"");
			params.put("ipAddress", ipAddress);
			params.put("curDate", curDate.toLocaleString());
 
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.sign_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code);    //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);      //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1  HttpClientesZGH  sign  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 ZGHServiceImpl  sign  error    resultMsg = null ");
            }
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  ZGHServiceImpl  sign  fail the reason is:  "+e );
		}
		outParam.setLength(0);
		return outParam;
 
	}
 
	//2===========修改如下==========================================
	//存入请求
	@Override
	@Transactional
	public OutParamStoreInDRequest storeRequsetNew(InParamStoreInRequest inParam) {
		//初始返回值
		OutParamStoreInDRequest outParam = new OutParamStoreInDRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			String    terminalID  = inParam.getTerminalID();  
			String    openBoxCode = inParam.getOpenBoxCode();
			//int       boxID = inParam.getBoxID();
			logger.info("1   存入请求      ZGHServiceImpl   storeRequsetNew   terminalID="+terminalID+"   openBoxCode="+openBoxCode  );
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
			params.put("openBoxCode", openBoxCode);
 
		    JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.storeRequset_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code); //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);  //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
		        int boxIDs=datases.getInt("boxID");
			    logger.info("1  HttpClientesZGH  storeRequsetNew  jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setBoxID(boxIDs);
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 ZGHServiceImpl  storeRequsetNew  error    resultMsg = null ");
            }
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("3  error  ZGHServiceImpl  storeRequsetNew  fail the reason is:  "+e );
		}
		return outParam;
	}
	
	
	
	//存入请求
	@Override
	@Transactional
	public OutParamStoreInRequest storeRequset(InParamStoreInRequest inParam) {
 
		return null;
	}
	
	
	//存入确认
	@Override
	@Transactional
	public OutParamStoreInConfirm storeIn(InParamStoreInConfirm inParam) {
		OutParamStoreInConfirm outParam = new OutParamStoreInConfirm();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			//获取请求数据
			String terminalID  = inParam.getTerminalID();
			int boxID          = inParam.getBoxID();
			String openBoxCode = inParam.getOpenBoxCode();
			Date storeInTime   = inParam.getStoreInDate();
			int money   = inParam.getMoney();	 
			logger.info("1   存入确认请求参数         terminalID="+terminalID+" boxID="+boxID+" openBoxCode="+openBoxCode+" storeInTime="+storeInTime.toLocaleString()+" moneyPrepaid="+money);
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
			params.put("boxID", boxID+"");
			params.put("openBoxCode", openBoxCode);
			params.put("storeInTime", storeInTime.toLocaleString());
			params.put("money", money+"");
			
			/**
                                      经常出现的问题
	           JCGErrorCode.ERR_BOX_NOT_EXIST
	           JCGErrorCode.ERR_PUT_EXIST_OTHER_BOX
            */
			JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.storeIn_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code); //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);  //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1   jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 ZGHServiceImpl storeIn   error   resultMsg = null ");
            }
 
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("3  ZGHServiceImpl storeIn  fail the reason is :"+e);
		}
		return outParam;
	}

	
	
	
	///=======================现在暂时作废===================================
	//取物请求
	@Override
	@Transactional
	public OutParamTakeOutRequest takeRequest(InParamTakeOutRequest inParam) {
		//初始返回值
		OutParamTakeOutRequest outParam = new OutParamTakeOutRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try{
			String terminalID  = inParam.getTerminalID();  //设备号
			//int      boxID     = inParam.getBoxID();       //箱号
			String openBoxCode = inParam.getOpenBoxCode(); //卡号
		    //Date storeInTime   = inParam.getStoreInDate(); //存入时间
			logger.info("1   取请求 请求参数        terminalID="+terminalID +" openBoxCode="+openBoxCode  );
			/**
				    经常出现的问题
				  JCGErrorCode.ERR_ILLIGAL_INSTRUCTION
				  JCGErrorCode.ERR_GET_CARD_NULL
			*/
			
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
			//params.put("boxID", boxID+"");
			params.put("openBoxCode", openBoxCode);
			//params.put("storeInTime", storeInTime.toLocaleString());
 
			JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.takeRequest_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code); //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);  //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1   jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 ZGHServiceImpl takeRequest   error   resultMsg = null ");
            }
		    
		    
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("3  ZGHServiceImpl takeRequest  fail the reason is :"+e);
		   }
		return outParam;
	}
	
	//取物确认
	@Override
	@Transactional
	public OutParamTakeOutConfirm takeOut(InParamTakeOutConfirm inParam) {
		OutParamTakeOutConfirm outParam = new OutParamTakeOutConfirm();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			//获取请求数据
			String terminalID  = inParam.getTerminalID();
			int boxID          = inParam.getBoxID();
			String openBoxCode = inParam.getOpenCode();
			Date storeInTime   = inParam.getStorInDate();
			Date takeOutTime   = inParam.getTakeOutDate();
			int money          = inParam.getMoney();  //TODO  这个要传递的  否则服务器重新计算会不准确的 
			logger.info("1  取确认 请求参数   terminalID="+terminalID+"  boxID="+boxID +"  openBoxCode="+openBoxCode);
			logger.info("  storeInTime="+storeInTime.toLocaleString()+"  takeOutTime="+takeOutTime.toLocaleString() +"  money="+money);
			/**
				    经常出现的问题
				  JCGErrorCode.ERR_BOX_NOT_EXIST
				  JCGErrorCode.ERR_GET_CARD_NULL
			*/
			Map<String, String> params=new HashMap<String, String>();
			params.put("terminalID", terminalID);
			params.put("boxID", boxID+"");
			params.put("openBoxCode", openBoxCode);
			params.put("storeInTime", storeInTime.toLocaleString());
			params.put("takeOutTime", takeOutTime.toLocaleString());
			params.put("money", money+"");
			
 
			JSONObject jsonResults = JSONObject.fromObject(HttpClientesZGH.takeOut_url(params));
		    if(jsonResults!=null){
		        String err_codees= jsonResults.getString(err_code); //ox00000000:表示成功 非0:不成功
		        String err_msges= jsonResults.getString(err_msg);  //描述
		        JSONObject datases=  jsonResults.getJSONObject(datas); //数据
			    logger.info("1   jsonResults="+jsonResults+" err_codees="+err_codees+" err_msges="+err_msges+" datases="+datases );
			    outParam.setErrorCode(Integer.parseInt(err_codees, 16) );
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//为空就是非法指令
            	logger.error("2 ZGHServiceImpl takeOut   error   resultMsg = null ");
            }
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("2  ZGHServiceImpl    takeOut  fail the reason is:"+e );
		}
		return outParam;
	}










	
  
}














