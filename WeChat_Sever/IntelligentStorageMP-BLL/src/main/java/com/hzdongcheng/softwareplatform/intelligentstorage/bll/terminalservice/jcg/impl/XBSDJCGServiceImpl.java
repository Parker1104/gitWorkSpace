package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.MsgUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;

/**
 * @author WenHeJu
 * @Description: TODO(西北师大正元一卡通对接) 
 * @ClassName:  XBSDServerImpl
 * @date 2017年7月7日 下午12:27:05
 * 
 */
@Service("XBSDService")
public class XBSDJCGServiceImpl extends JCGServiceImpl{

	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(XBSDJCGServiceImpl.class);

	//获取用户信息
	public static String sPeoxyName = "";
	public static String APPID = "5246D8A01B466E59358232E9094F9666";
	public static String APPSECERT = "FA01C4EA68FA20D9EFE6C1976D24E957";

	String tokenMessage = "";
	public static String url = "http://60.191.37.211:8086/easytong_app/api/token?appid="+APPID+"&appsecret="+APPSECERT;

	// 获取授权信息
	public class InParamPayMoney
	{
		public String accNum = "";
		public String amount = "";
		public String cardAccNum = "";
		public String eWalletId = "";
		public String serialNum = "";
	}

	// 返回信息
	public class ReturnPayMoney
	{
		public String code;
		public String msg;
		public PayMoneyInfo data;
	}

	// 支付信息类
	public class PayMoneyInfo
	{ 
		public String accNum;
		public String accName;
		public String perCode;
		public String liquidationDate;
		public String platformSerialNum;
		public String serialNum;
		public String amount;
		public String sign;
	}

	/**
	 * 获取token的值
	 */
	private String getAccess_token() {
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			//设置链接
			URL urlGet = new URL(url);
			//启动链接
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			//设置链接参数与要求
			http.setRequestMethod("GET");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setUseCaches(false);// 使用POST不能使用缓存
			// 连接会话  
			http.connect();
			// 获取输入流  
			if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = http.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
					resultBuffer.append("\n");
				}
			}
			JSONObject jsStr = JSONObject.parseObject(resultBuffer.toString());
			tokenMessage = jsStr.get("access_token").toString();
			//jsStr.get("expires_in");
			logger.info("Access_token 信息："+tokenMessage);
			//System.out.println(reader.toString()); 
			reader.close();// 关闭流  
			http.disconnect();// 断开连接     
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenMessage;
	}


	/**
	 * @param body
	 * @return
	 * 加密要发送的信息
	 */
	private String createSendMessage(String body) {
		// TODO Auto-generated method stub
		//签名加密
		String sign = MD5(body + "&key=" + APPID);
		body += "&sign=" + sign;
		return body;
	}

	/**
	 * @param convertString
	 * @return
	 * md5加密
	 */
	private String MD5(String convertString) {
		String t2 = null;
		try {
			t2 = MsgUtils.getMD5(convertString);
			t2 = t2.replaceAll("-", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return t2;
	}

	/**
	 * @param sContent
	 * @return
	 * 发送扣费信息
	 */
	public static boolean httpURLConectionPOST(String sUrl,String sContent) {  
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try { 
			// 把字符串转换为URL请求地址  
			URL url = new URL("http://60.191.37.211:8086/easytong_app/"+sUrl);
			URLConnection conn = url.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(sContent);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			JSONObject jsStr = JSONObject.parseObject(result);
			String sResultCode = jsStr.get("code").toString();
			if (sResultCode=="0")  return true;
			else{
				String sResultMessage = jsStr.get("msg").toString();
				logger.info("返回结果："+sResultMessage);
				return false;
			}
		} catch (Exception e) {  
			e.printStackTrace(); 
			System.out.println("失败!");  
			return false;
		}  
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}

	}  

	/**
	 * 存入请求
	 */
	@Override
	public OutParamStoreInRequest storeRequset(InParamStoreInRequest inParam) {
		//初始返回值
		OutParamStoreInRequest outParam = new OutParamStoreInRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
		try {
			//设备号
			String terminalID  = inParam.getTerminalID();
			//卡号
			String openBoxCode = inParam.getOpenBoxCode();

			//根据设备号查询业务编码
			TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
			Integer businessCode = terminalEntity.getBusinesscode();

			//根据业务编码获取业务模型
			Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);

			if (businessModelMap == null){
				return outParam;
			}

			//Step.1 根据卡号规则，转换卡片
			String sCardTransRuleCode = businessModelMap.get(Constant.BT_CFG_NAME_CARDTRANSRULE);

			if (StringUtils.isNotEmpty(sCardTransRuleCode)){
				Integer ruleCode = Integer.valueOf(sCardTransRuleCode);
				openBoxCode = transCardIDByRuleCode(ruleCode, openBoxCode);
			}

			//Step.2  运行时段
			int runtimeCode = 0;
			{
				String sRunTimeCode = businessModelMap.get(Constant.BT_CFG_NAME_RUNTIME);
				logger.info("设备号："+terminalID+"卡号："+openBoxCode+"业务类型编码："+businessCode+"业务模型："+businessModelMap+"获取的转换规则："+sCardTransRuleCode+"转换后卡号："+openBoxCode+"获取运营时段的ID："+sRunTimeCode);
				if (StringUtils.isEmpty(sRunTimeCode)){
					outParam.setErrorCode(JCGErrorCode.ERR_PUT_NOT_RUNTIME);
					return outParam;
				}

				runtimeCode = Integer.valueOf(sRunTimeCode);
				if (!checkRuntime(runtimeCode))
				{
					outParam.setErrorCode(JCGErrorCode.ERR_PUT_NOT_RUNTIME);
					return outParam;		
				}
			}

			//Step.3 身份认证
			String sCheckIdentity = businessModelMap.get(Constant.BT_CFG_NAME_CHECKIDENTITY);
			if ( StringUtils.isNotEmpty(sCheckIdentity) ){
				boolean isCheckIdentity = sCheckIdentity.equalsIgnoreCase(Constant.BT_CFG_NAME_CHECKIDENTITY_ON);
				if (isCheckIdentity){
					//Step.3.1 查询用户信息表，验证身份
					UserEntity userEntity = userService.select(openBoxCode);
					if (userEntity == null){
						outParam.setErrorCode(JCGErrorCode.ERR_PUT_ATH_FAIL);
						return outParam;
					}
				}
			}

			//Step.4 查询在箱记录
			StoreInRecordEntityExample example = new StoreInRecordEntityExample();
			example.createCriteria().andUsercardidEqualTo(openBoxCode)
			.andStateEqualTo((byte)0);
			example.setOrderByClause("storeInTime desc");
			List<StoreInRecordEx> records = storeInRecordExDao.selectByExampleEx(example);
			if (records == null || records.size() == 0){		
				outParam.setErrorCode(JCGErrorCode.ERR_OK);
				return outParam;	
			}

			//Step.5一卡一箱验证
			String sOneCardOneBox = businessModelMap.get(Constant.BT_CFG_NAME_ONECARDONEBOX);
			logger.info("获取是否有在箱记录："+records.size()+"  0没有"+"获取是否开启一卡一箱验证："+sOneCardOneBox);
			if (StringUtils.isNotEmpty(sOneCardOneBox)){
				boolean isOneCardOneBox = sOneCardOneBox.equalsIgnoreCase(Constant.BT_CFG_NAME_ONECARDONEBOX_ON);
				if (!isOneCardOneBox){
					outParam.setErrorCode(JCGErrorCode.ERR_OK);
					return outParam;
				}
			}

			outParam.setErrorCode(JCGErrorCode.ERR_PUT_EXIST_OTHER_GUI);
			//设置显示柜号
			outParam.setDisplayName(records.get(0).getDisplayname());

			String areaShortname = "";
			{
				TerminalEntity tmpTerminalEntity = terminalExDao.selectByPrimaryKey(records.get(0).getTerminalid());
				String areaCode = tmpTerminalEntity.getAreacode();
				areaShortname = areaEntityDao.selectByPrimaryKey(areaCode).getAreashortname();
				areaShortname = areaShortname.substring(areaShortname.length()-1);
			}
			//设置区域简称
			outParam.setAreaCode(areaShortname);
			//设置箱号
			outParam.setBoxID(records.get(0).getBoxid().byteValue());
			//设置显示箱号
			outParam.setDisplayBoxName(records.get(0).getDispalyname());		

		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("异常："+e.getMessage());
		}
		return outParam;
	}

	/**
	 * 存入确认
	 */
	@Override
	public OutParamStoreInConfirm storeIn(InParamStoreInConfirm inParam) {
		OutParamStoreInConfirm outParam = new OutParamStoreInConfirm();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);

		//获取请求数据
		String terminalID  = inParam.getTerminalID();
		int boxID          = inParam.getBoxID();
		String openBoxCode = inParam.getOpenBoxCode();
		Date storeInTime   = inParam.getStoreInDate();
		int money          = inParam.getMoney();
		//查询是否存在
		StoreInRecordEntity storeInRecordEntity = storeInRecordExDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
		if (storeInRecordEntity != null){
			return outParam;
		}
		//Step.2 卡号转换
		//根据设备号查询业务编码
		TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
		Integer businessCode = terminalEntity.getBusinesscode();
		//根据业务编码获取业务模型
		Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
		if (businessModelMap != null){
			String sCardTransRuleCode = businessModelMap.get(Constant.BT_CFG_NAME_CARDTRANSRULE);		
			if (StringUtils.isNotEmpty(sCardTransRuleCode)){
				Integer ruleCode = Integer.valueOf(sCardTransRuleCode);
				openBoxCode = transCardIDByRuleCode(ruleCode, openBoxCode);
			}
		}

		//写入在箱记录
		storeInRecordEntity = new StoreInRecordEntity();		
		UserEntity userEntity = userService.select(openBoxCode);
		try {
			if (userEntity != null){
				storeInRecordEntity.setUsertype(userEntity.getUsertype());
				storeInRecordEntity.setUsername(userEntity.getUsername());
				storeInRecordEntity.setIdtype(userEntity.getIdtype());
				storeInRecordEntity.setIdcode(userEntity.getIdcode());
				storeInRecordEntity.setCompany(userEntity.getCompany());
				storeInRecordEntity.setDepartment(userEntity.getDepartment());
				storeInRecordEntity.setSubdepartment(userEntity.getSubdepartment());
				storeInRecordEntity.setTelephone(userEntity.getTelephone());
				storeInRecordEntity.setAddress(userEntity.getAddress());
				storeInRecordEntity.setEffectivedays(userEntity.getEnddate());
				storeInRecordEntity.setRealmoney((float)money);
				storeInRecordEntity.setTerminalid(terminalID);
				storeInRecordEntity.setBoxid(boxID);
				storeInRecordEntity.setUsercardid(openBoxCode);
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)0);
				storeInRecordEntity.setMoney((float)money);	
				storeInRecordExDao.insertSelective(storeInRecordEntity);	
			}else {
				storeInRecordEntity.setTerminalid(terminalID);
				storeInRecordEntity.setBoxid(boxID);
				storeInRecordEntity.setUsercardid(openBoxCode);
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)0);
				storeInRecordEntity.setMoney((float)money);	
				storeInRecordEntity.setUsertype("0");
				storeInRecordEntity.setRealmoney((float)0);
				storeInRecordEntity.setMoney((float)0);	
				storeInRecordExDao.insertSelective(storeInRecordEntity);	
			}		
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return outParam;
	}

	/**
	 * 取请求
	 */
	@Override
	public OutParamTakeOutRequest takeRequest(InParamTakeOutRequest inParam) {
		// TODO Auto-generated method stub
		//初始返回值
		OutParamTakeOutRequest outParam = new OutParamTakeOutRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);

		//设备号
		String terminalID  = inParam.getTerminalID();
		//箱号
		int boxid          = inParam.getBoxID();
		//卡号
		String openBoxCode = inParam.getOpenBoxCode();

		//根据设备号查询业务编码
		TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);

		Integer businessCode = terminalEntity.getBusinesscode();


		//根据业务编码获取业务模型
		Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
		if (businessModelMap == null){
			return outParam;
		}

		//Step.1 根据卡号规则，转换卡片
		String sCardTransRuleCode = businessModelMap.get(Constant.BT_CFG_NAME_CARDTRANSRULE);
		if (StringUtils.isNotEmpty(sCardTransRuleCode)){
			Integer ruleCode = Integer.valueOf(sCardTransRuleCode);
			openBoxCode = transCardIDByRuleCode(ruleCode, openBoxCode);
			logger.info("取请求   设备号："+terminalID+"卡号："+openBoxCode+"业务信息"+terminalEntity+"业务编码："+businessCode+"业务模型："+businessModelMap+"转换后卡号："+openBoxCode);
		}

		//Step.2  运行时段
		int runtimeCode = 0;
		{
			String sRunTimeCode = businessModelMap.get(Constant.BT_CFG_NAME_RUNTIME);
			if (StringUtils.isEmpty(sRunTimeCode)){
				outParam.setErrorCode(JCGErrorCode.ERR_GET_NOT_RUNTIME);
				return outParam;
			}

			runtimeCode = Integer.valueOf(sRunTimeCode);
			if (!checkRuntime(runtimeCode))
			{
				outParam.setErrorCode(JCGErrorCode.ERR_GET_NOT_RUNTIME);
				return outParam;		
			}
		}

		//Step.3 身份认证
		String sCheckIdentity = businessModelMap.get(Constant.BT_CFG_NAME_CHECKIDENTITY);
		if ( StringUtils.isNotEmpty(sCheckIdentity) ){
			boolean isCheckIdentity = sCheckIdentity.equalsIgnoreCase(Constant.BT_CFG_NAME_CHECKIDENTITY_ON);
			if (isCheckIdentity){
				//Step.3.1 查询用户信息表，验证身份
				UserEntity userEntity = userService.select(openBoxCode);
				if (userEntity == null || userEntity.getState() ==1){
					outParam.setErrorCode(JCGErrorCode.ERR_GET_ATH_FAIL);
					return outParam;
				}
			}
		}

		//Step.4 查询在箱记录
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		example.createCriteria().andUsercardidEqualTo(openBoxCode)
		.andStateEqualTo((byte)0);
		example.setOrderByClause("storeInTime desc");
		List<StoreInRecordEntity> records = storeInRecordExDao.selectByExample(example);
		logger.info("获取是否开启身份验证："+sCheckIdentity+"  1开 0关"+"查询在箱记录："+records.size()+"  0无");
		if (records == null || records.size() == 0){
			outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
			return outParam;
		}

		//Step.5 收费功能
		String sCheckCharge = businessModelMap.get(Constant.BT_CFG_NAME_CHARGESWITCH);
		if (StringUtils.isNotEmpty(sCheckCharge) ){
			boolean isCheckIdentity = sCheckIdentity.equalsIgnoreCase(Constant.BT_CFG_NAME_CHARGESWITCH_ON);
			if (isCheckIdentity){
				String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
				//查询收费箱门的类型
				BoxEntity boxEntity = boxExDao.selectByPrimaryKey(terminalID, boxid);
				//查询在箱记录表 （根据卡号）
				StoreInRecordEx storeInRecordEx = storeInRecordExDao.selectByRequest(terminalID, boxid, openBoxCode);
				if(storeInRecordEx != null){
					float money = punishDao.cost(storeInRecordEx.getStoreintime(), DateUtils.nowDate(), boxEntity.getBoxtypecode(), Integer.parseInt(chargeMode));
					if(money>0){
						getAccess_token();
						sPeoxyName = "api/payservice/pay?access_token=" + tokenMessage;
						//accNum 账号  amount  消费金额,格式是XX.XX元   cardAccNum 卡号      eWalletId 钱包   serialNum 开发者方流水号
						String body = "accNum=1&amount="+money+"&cardAccNum=1&eWalletId=1&serialNum="+DateUtils.nowDate().getTime();
						if (!httpURLConectionPOST(sPeoxyName,createSendMessage(body))){
							//返回扣费失败
							outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_INVALID);
							return outParam;
						}
					} 
				}
			}
		}

		boolean isOk = false;
		for (StoreInRecordEntity storeInRecord : records) {
			String anotherTerminalID = storeInRecord.getTerminalid();
			if (terminalID.equalsIgnoreCase(anotherTerminalID)) {
				isOk = true;
				break;
			}
		}

		if (!isOk){
			outParam.setErrorCode(JCGErrorCode.ERR_GET_EXIST_OTHER_GUI);
			BoxEx boxEntityEx = boxExDao.selectByPrimaryKeys(records.get(0).getTerminalid(), records.get(0).getBoxid());
			//设置显示柜号
			outParam.setDisplayName(boxEntityEx.getTerminalEntity().getDisplayname());

			String areaShortname = "";
			{
				TerminalEntity tmpTerminalEntity = terminalExDao.selectByPrimaryKey(records.get(0).getTerminalid());
				String areaCode = tmpTerminalEntity.getAreacode();
				areaShortname = areaEntityDao.selectByPrimaryKey(areaCode).getAreashortname();
				areaShortname = areaShortname.substring(areaShortname.length()-1);
			}
			//设置区域简称
			outParam.setAreaCode(areaShortname);
			//设置箱号
			outParam.setBoxID(records.get(0).getBoxid().byteValue());
			//设置显示箱号
			outParam.setDisplayBoxName(boxEntityEx.getDispalyname());	
		} else {
			outParam.setErrorCode(JCGErrorCode.ERR_OK);
		}

		return outParam;
	}

	/**
	 * 取确认
	 */
	@Override
	public OutParamTakeOutConfirm takeOut(InParamTakeOutConfirm inParam) {
		// TODO Auto-generated method stub
		OutParamTakeOutConfirm outParam = new OutParamTakeOutConfirm();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);

		//获取请求数据
		String terminalID  = inParam.getTerminalID();
		int boxID          = inParam.getBoxID();
		String openBoxCode = inParam.getOpenCode();
		Date storeInTime   = inParam.getStorInDate();
		Date takeOutTime   = inParam.getTakeOutDate();
		int money          = inParam.getMoney();

		//Step.1 根据设备号，查询设备是否支持固定箱门
		BoxEntity boxEntity = boxExDao.selectByPrimaryKey(terminalID, boxID);
		if(boxEntity == null){
			outParam.setErrorCode(JCGErrorCode.ERR_BOX_NOT_EXIST);
			return outParam;		
		}
		boolean isFixbox = boxEntity.getFixedbox() == 1 ? true : false;

		//Step.2 卡号转换
		//根据设备号查询业务编码
		TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
		Integer businessCode = terminalEntity.getBusinesscode();
		//根据业务编码获取业务模型
		Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
		if (businessModelMap != null){
			String sCardTransRuleCode = businessModelMap.get(Constant.BT_CFG_NAME_CARDTRANSRULE);	
			if (StringUtils.isNotEmpty(sCardTransRuleCode)){
				Integer ruleCode = Integer.valueOf(sCardTransRuleCode);
				openBoxCode = transCardIDByRuleCode(ruleCode, openBoxCode);
			}
		}

		//Step.3 查询是否已经存在记录
		MidwayTakeRecordEntity midwayTakeRecordEntity = null;
		TakeOutRecordEntity takeOutRecordEntity = null;
		StoreInRecordEntity storeInRecordEntity = null;
		if (isFixbox){
			MidwayTakeRecordEntityExample example = new MidwayTakeRecordEntityExample();
			example.createCriteria().andTerminalidEqualTo(terminalID)
			.andBoxidEqualTo(boxID)
			.andTaketimeEqualTo(takeOutTime);

			List<MidwayTakeRecordEntity> midwayTakeRecordEntityList = midwayTakeRecordEntityDao.selectByExample(example);
			if(midwayTakeRecordEntityList != null && midwayTakeRecordEntityList.size() != 0){
				return outParam;
			}
		} else {
			takeOutRecordEntity = takeOutRecordEntityDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
			if (takeOutRecordEntity != null){
				return outParam;				
			}
		}

		//Step.4 查询在箱记录表是否存在记录，没有则写入
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		example.createCriteria().andTerminalidEqualTo(terminalID)
		.andBoxidEqualTo(boxID)
		.andUsercardidEqualTo(openBoxCode)
		.andStateEqualTo((byte)0);
		example.setOrderByClause("storeInTime desc");
		List<StoreInRecordEntity> storeInRecordEntityList = storeInRecordExDao.selectByExample(example);
		if (storeInRecordEntityList == null || storeInRecordEntityList.size() == 0){
			//写存物记录
			storeInRecordEntity = new StoreInRecordEntity();
			UserEntity userEntity = userService.select(openBoxCode);
			try {
				if (userEntity != null){
					storeInRecordEntity.setUsertype(userEntity.getUsertype());
					storeInRecordEntity.setUsername(userEntity.getUsername());
					storeInRecordEntity.setIdtype(userEntity.getIdtype());
					storeInRecordEntity.setIdcode(userEntity.getIdcode());
					storeInRecordEntity.setCompany(userEntity.getCompany());
					storeInRecordEntity.setDepartment(userEntity.getDepartment());
					storeInRecordEntity.setSubdepartment(userEntity.getSubdepartment());
					storeInRecordEntity.setTelephone(userEntity.getTelephone());
					storeInRecordEntity.setAddress(userEntity.getAddress());
					storeInRecordEntity.setEffectivedays(userEntity.getEnddate());
					storeInRecordEntity.setRealmoney((float)0);
				}
				storeInRecordEntity.setTerminalid(terminalID);
				storeInRecordEntity.setBoxid(boxID);
				storeInRecordEntity.setUsercardid(openBoxCode);
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)0);
				storeInRecordEntity.setMoney((float)money);
				storeInRecordEntity.setRealmoney((float)0);
				storeInRecordExDao.insertSelective(storeInRecordEntity);	
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
			}
		}

		//Step.5 写取物记录
		if (isFixbox){
			midwayTakeRecordEntity = new MidwayTakeRecordEntity();
			midwayTakeRecordEntity.setTerminalid(terminalID);
			midwayTakeRecordEntity.setBoxid(boxID);
			midwayTakeRecordEntity.setUsercardid(openBoxCode);
			midwayTakeRecordEntity.setStoreintime(storeInTime); 
			midwayTakeRecordEntity.setTaketime(takeOutTime);
			midwayTakeRecordEntity.setMoney((float)money);
			midwayTakeRecordEntity.setRealmoney((float)money);
			midwayTakeRecordEntity.setType(1);
			midwayTakeRecordEntity.setMemo(null);
			midwayTakeRecordEntity.setCashierno(null);
			midwayTakeRecordEntityDao.insert(midwayTakeRecordEntity);
		} else {
			try {
				takeOutRecordEntity = new TakeOutRecordEntity();
				takeOutRecordEntity.setTerminalid(terminalID);
				takeOutRecordEntity.setBoxid(boxID);
				takeOutRecordEntity.setUsercardid(openBoxCode);
				takeOutRecordEntity.setStoreintime(storeInTime); 
				takeOutRecordEntity.setTaketime(takeOutTime);
				takeOutRecordEntity.setMoney((float)money);
				takeOutRecordEntity.setRealmoney((float)money);
				takeOutRecordEntity.setType(2);
				takeOutRecordEntity.setCashierno("0");
				takeOutRecordEntity.setMakeopcode(null);
				takeOutRecordEntityDao.insert(takeOutRecordEntity);

				storeInRecordEntity = new StoreInRecordEntity();
				storeInRecordEntity.setTerminalid(terminalID);
				storeInRecordEntity.setBoxid(boxID);
				storeInRecordEntity.setUsercardid(storeInRecordEntityList.get(0).getUsercardid());
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)1);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
			}

			storeInRecordExDao.updateByPrimaryKeySelective(storeInRecordEntity);
		}

		return outParam;
	}

}
