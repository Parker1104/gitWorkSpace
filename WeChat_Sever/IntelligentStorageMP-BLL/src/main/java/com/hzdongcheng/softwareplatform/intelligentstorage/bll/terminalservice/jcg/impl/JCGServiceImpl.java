package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IEquipmentRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IJCGService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardTransRuleExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.ManagerExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PunishRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TakeOutRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AreaEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.MidwayTakeRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.RunTimeEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;

@Service("JCGService")
public class JCGServiceImpl implements IJCGService {
	@Autowired
	protected TerminalExDao terminalExDao;
	@Autowired
	protected BoxExDao boxExDao;
	@Autowired
	protected StoreInRecordExDao storeInRecordExDao;
	@Autowired
	protected TakeOutRecordEntityDao takeOutRecordEntityDao;
	@Autowired
	protected TakeOutRecordExDao takeOutRecordExDao;
	@Autowired
	protected RunTimeEntityDao rtDao;
	@Autowired
	protected BusinessModelEntityExDao business;
	@Autowired
	protected IUserService userService;
	@Autowired
	protected MidwayTakeRecordEntityDao midwayTakeRecordEntityDao;
	@Autowired
	protected PunishRecordExDao punishDao;
	@Autowired
	protected CardTransRuleExDao cardTransRuleExDao;
	@Autowired
	protected ManagerExDao managerDao;	
	@Autowired
	protected AreaEntityDao areaEntityDao;
	@Autowired
	protected IEquipmentRecord iEquipmentRecord;
	
	public static Log4jUtils logger = Log4jUtils.createInstanse(JCGServiceImpl.class);

	//卡号转换
	public String transCardIDByRuleCode(Integer cardTransRuleCode, String openboxcode)
	{
		//查看卡号的转换	
		CardTransRuleEntity cardTransRuleEntity = cardTransRuleExDao.selectByPrimaryKey(cardTransRuleCode);
		if(cardTransRuleEntity != null) {		
			//截取字符串
			openboxcode = openboxcode.substring(cardTransRuleEntity.getStartsubstr());

			//倒序
			if (cardTransRuleEntity.getCardrule() != 1) {

				if (StringUtils.isNotEmpty(openboxcode)){
					if ( openboxcode.length() % 2 != 0 ){
						openboxcode = "0" + openboxcode;
					}
				}

				StringBuffer out = new StringBuffer();
				String tmpOpenCode = "";
				for (int i = 0; i < openboxcode.length() / 2; i++) {
					tmpOpenCode = out.insert(0, openboxcode.substring(i * 2, i * 2 + 2)).toString();
				}

				openboxcode = tmpOpenCode;
			}

			if(cardTransRuleEntity.getDecimalism() == 1){
				//十进制
				try {	
					openboxcode =String.valueOf(Long.parseLong(openboxcode,16));
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage());
					System.out.println("转换失败");
				}

			}

			/*while (openboxcode.length() < cardTransRuleEntity.getCardlen()) {
				openboxcode = "0" + openboxcode;
			}*/

			return openboxcode;
		}

		return openboxcode;
	}

	//运行时段判断
	public boolean checkRuntime(int runtimecode) {
		//RunTimeEntity runtime = rtDao.selectTime(sTerminalID);//查询运行时段
		RunTimeEntity runtime = rtDao.selectByPrimaryKey(runtimecode);
		Calendar cal = Calendar.getInstance();
		Date date = DateUtils.nowDate();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);//获取当天是周几
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String s = sdf.format(date);
		int a = 0;
		int b = 0;
		switch (week) {
		case 2://周一
			a = s.compareTo(runtime.getMonstartruntime());//比较存物时间是否超出运行时段
			b = s.compareTo(runtime.getMonendruntime());
			break;
		case 3://周二
			a = s.compareTo(runtime.getTuestartruntime());//比较存物时间是否超出运行时段
			b = s.compareTo(runtime.getTueendruntime());
			break;
		case 4://周三
			a = s.compareTo(runtime.getWedstartruntime());
			b = s.compareTo(runtime.getWedendruntime());
			break;
		case 5://周四
			a = s.compareTo(runtime.getThursstartruntime());
			b = s.compareTo(runtime.getThursendruntime());
			break;
		case 6://周五
			a = s.compareTo(runtime.getFristartruntime());
			b = s.compareTo(runtime.getFriendruntime());
			break;
		case 7://周六
			a = s.compareTo(runtime.getSatstartruntime());
			b = s.compareTo(runtime.getSatendruntime());
			break;
		case 1://周日
			a = s.compareTo(runtime.getSunstartruntime());
			b = s.compareTo(runtime.getSunendruntime());
			break;
		}
		if (a>0 && b<0) {
			return true;
		}
		return false;
	}

	//查询在用箱列表
	@Override
	public List<BoxEntity> queryInBoxList(InParam inParam) {
		/*String terminalID = inParam.getTerminalID();
		List<BoxEntity> list = new ArrayList<BoxEntity>();
		try {
			list = bDao.queryInBoxList(terminalID);			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
	}

	//查询违规箱列表
	@Override
	public List<BoxEntity> queryViolationBoxList(InParam inParam) {
		//String terminalID = inParam.getTerminalID();
		/*List<BoxEntity> list = new ArrayList<BoxEntity>();
		try {
			list = bDao.queryViolationBoxList(terminalID);			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
	}

	//管理卡刷卡日志
	private void WriteDiary(String terminalID ,String openBoxCode,String sOpType,String sBoxList) {
		//日志
		Date d = new Date();
		d.getTime();
		//写入日志
		RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
		rem.setAccountcode(openBoxCode);
		rem.setContent(sOpType);
		rem.setTerminalid(terminalID);
		rem.setType(1);
		rem.setDate(d);
		rem.setBoxid(0);
		rem.setMemo(sOpType + "箱门列表：" +sBoxList);
		try {
			iEquipmentRecord.saveOrUpdate(rem);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}

	}
	
	public Map<String , String> getBussineeModelByCode(BusinessModelEntityExDao businessModelDao, Integer businessCode){
		List<BusinessModelEntity> businessModelEntitys = businessModelDao.selectByKey(businessCode);
		if (businessModelEntitys == null || businessModelEntitys.size() == 0){
			return null;
		}

		Map<String , String> map = new HashMap<String , String>(); 
		for (BusinessModelEntity entity : businessModelEntitys) {
			String configName    = entity.getConfigname();
			String configValue   = entity.getConfigvalue();
			map.put(configName, configValue);
		}

		return map;
	}

	
	
	//设备签到
	@Override
	@Transactional
	public OutParamSignIn sign(InParamSignIn inParam) { 
		OutParamSignIn outParam = new OutParamSignIn();

		//
		String terminalID   = inParam.getTerminalID();
		String dispalyName  = inParam.getDisplayName();
		int totalBoxNums    = inParam.getTotalBoxNums();
		String ipAddress    = inParam.getIpAddr();
		Date curDate        = DateUtils.nowDate();

		TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
		if (terminalEntity != null){
			if (!dispalyName.equalsIgnoreCase(terminalEntity.getDisplayname()) ||
					!ipAddress.equalsIgnoreCase(terminalEntity.getIpaddr())){
				terminalEntity.setDisplayname(dispalyName);
				terminalEntity.setIpaddr(ipAddress);
				terminalEntity.setLastmodifytime(curDate);
				terminalExDao.updateByPrimaryKeySelective(terminalEntity);
			}
			terminalEntity.setNetworkstate((byte)0);
			terminalExDao.updateByPrimaryKeySelective(terminalEntity);
		} else {
			terminalEntity = new TerminalEntity();
			terminalEntity.setTerminalid(terminalID);
			terminalEntity.setDisplayname(dispalyName);
			terminalEntity.setTotalboxnumber(totalBoxNums);
			terminalEntity.setIpaddr(ipAddress);
			terminalEntity.setMakedate(curDate);
			terminalEntity.setLastmodifytime(curDate);
			terminalEntity.setAreacode("01");
			terminalEntity.setBusinesscode(1);
			terminalEntity.setMacaddress("00-00-00-00-00-00");
			terminalEntity.setRunstatus((byte)1);
			terminalEntity.setNetworkstate((byte)0);
			terminalEntity.setRegisterflag("1");
			terminalExDao.insertSelective(terminalEntity);

			BoxEntity box = new BoxEntity();
			for (int i = 1; i <= totalBoxNums; i++) {
				NumberFormat formatter = NumberFormat.getNumberInstance();   //数值格式化
				formatter.setMinimumIntegerDigits(2);   
				formatter.setGroupingUsed(false);   
				String s = formatter.format(i);
				box.setTerminalid(terminalID);
				box.setBoxid(i);
				box.setArticle((byte)0);
				box.setOpen((byte)0);
				box.setStatus((byte)0);
				if (inParam.isFixed()) {
					box.setFixedbox((byte)1);
				}else {
					box.setFixedbox((byte)0);
				}
				box.setOneboxmorecard((byte)0);
				box.setBoxtypecode(1);
				box.setDispalyname(dispalyName+"-"+s);
				boxExDao.insertSelective(box);
			}
		}

		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		outParam.setLength(0);
		return outParam;
	}

	
	//心跳
	@Override
	public OutParamKeepAlive keepAlive(InParamKeepAlive inParam) {
		OutParamKeepAlive outParam = new OutParamKeepAlive();
		outParam.setLength(7);
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		//获取请求数据
		String terminalid  = inParam.getTerminalID();
		int totalBoxNums   = inParam.getTotalBoxNums();
		byte []goodsStatus = inParam.getGoodsStatusArray();
		byte []openStatus  = inParam.getOpenStatusArray();
	    String dumpEnergy = inParam.getDumpEnergy()+""; //电量
	    String longitude  = inParam.getLongitude();  //经度
	    String latitude   = inParam.getLatitude();   //纬度
	    String rfid       = inParam.getRfid();       //RFID
	    int  guistatus    = inParam.getGuistatus();  //柜子状态  0:立 1:倒
		
		//比较物品状态
		BoxEntity boxEntity = new BoxEntity();
		BoxEntityExample example = new BoxEntityExample();
		example.createCriteria().andTerminalidEqualTo(terminalid);
		List<BoxEntity> list = boxExDao.selectByExample(example);
		if(totalBoxNums != list.size()){
			logger.error("JCGServiceImpl keepAlive  totalBoxNums != list.size()   terminalid="+terminalid+"  totalBoxNums="+totalBoxNums+" list.size()="+list.size() );
		}else {
			//logger.info("JCGServiceImpl keepAlive  totalBoxNumstotalBoxNums="+totalBoxNums+" list.size()="+list.size());
			for (int i = 0; i < list.size(); i++) {
				//logger.info(" getBoxid[i]="+list.get(i).getBoxid()+" goodsStatus[i]="+goodsStatus[i]+"   openStatus[i]="+openStatus[i] );
				if (list.get(i).getArticle() != goodsStatus[i] || list.get(i).getOpen() != openStatus[i]) {
					int article = goodsStatus[i];
					int open    = openStatus[i];
					int boxid   = list.get(i).getBoxid();
					boxEntity.setTerminalid(terminalid);
					boxEntity.setBoxid(boxid);
					boxEntity.setArticle((byte)article);//物品状态    article 0 无物     1 有物   
					boxEntity.setOpen((byte)open);      //开关状态    open    0 开启     1 关闭   2故障
					boxExDao.updateByPrimaryKeySelective(boxEntity);
				}
			}
			//更新设备
			TerminalEntity tEntity = terminalExDao.selectByPrimaryKey(terminalid);
			if (tEntity != null){
			    String dumpEnergySql = tEntity.getDumpenergy();
			    String longitudeSql  = tEntity.getLongitude();
			    String latitudeSql   = tEntity.getLatitude();
			    String rfidSql       = tEntity.getRfid();
			    Integer guistatusSql     = (null==tEntity.getGuistatus()?0:tEntity.getGuistatus());
				if ( (  !dumpEnergy.equals(dumpEnergySql+"") )   ||  
					 (  !longitude.equals(longitudeSql) )   || 
					 (  !latitude.equals(latitudeSql) )   || 
					 (  !rfid.equals(rfidSql) )   ||
					 ( guistatus  !=guistatusSql )    ) {
					/* logger.info(" 数据库更新 dumpEnergy="+dumpEnergy+" longitude="+longitude
							   +"  latitude="+latitude+"  rfid="+rfid+"  guistatus="+guistatus);*/
					  tEntity.setDumpenergy(dumpEnergy+""); //电量
					  tEntity.setLongitude(longitude);      //经度
					  tEntity.setLatitude(latitude);        //纬度
					  tEntity.setRfid(rfid);                //RFID
					  tEntity.setGuistatus(guistatus);      //柜子状态  0:立 1:倒
				      terminalExDao.updateByPrimaryKeySelective(tEntity);
				}
		    }
 
			/*for (int i = 0; i < list.size(); i++) { 				
				int state = openStatus[i];
				int boxid = list.get(i).getBoxid();					
				//取物不关箱门  暂停存物权限七天
				if (state==0) {
					TakeOutRecordEntity takeOutRecordEntity =  takeOutRecordExDao.selectMaxByExample(terminalid, boxid);
					if(takeOutRecordEntity != null){
						long time = takeOutRecordEntity.getTaketime().getTime();
						long nowtime = DateUtils.nowDate().getTime();
						long diff = nowtime-time;
						long min = diff / (60 * 1000);
						if (min>2) {
							//查询看他是否有违规记录如果有就不操作
							PunishRecordEntityExample Example = new PunishRecordEntityExample();
							Example.setDistinct(true);
							Example.createCriteria().andTerminalidEqualTo(takeOutRecordEntity.getTerminalid())
							.andBoxidEqualTo(takeOutRecordEntity.getBoxid())
							.andUsercardidEqualTo(takeOutRecordEntity.getUsercardid()).andPunishstateBetween((byte)1, (byte)9);
							List<PunishRecordEntity> punishRecordEntity = punishDao.selectByExample(Example);
							if(punishRecordEntity.size() == 0){
								logger.info("不关箱门惩罚记录生成成功");
								PunishRecordEntity punish = new PunishRecordEntity();
								punish.setTerminalid(terminalid);
								punish.setBoxid(boxid);
								punish.setUsercardid(takeOutRecordEntity.getUsercardid());
								punish.setStoreintime(takeOutRecordEntity.getStoreintime());						
								TerminalEntity terminal = terminalExDao.selectByPrimaryKey(terminalid);//获取惩罚天数
								BusinessModelEntity model = business.selectByPrimaryKey(terminal.getBusinesscode(), Constant.BT_CFG_NAME_NOTCLOSEDOORPUNISHDAY);
								if (model==null) {

								}else {
									punish.setEnddate(DateUtils.addDay(new Date(), Integer.parseInt(model.getConfigvalue())));
								}
								logger.info("获取不关箱门的卡号："+takeOutRecordEntity.getUsercardid() +"惩罚的时间"+Integer.parseInt(model.getConfigvalue()));
								punish.setMoney(takeOutRecordEntity.getMoney());
								punish.setRealmoney(takeOutRecordEntity.getRealmoney());
								punish.setPunishstate((byte)9);
								punish.setMakedate(DateUtils.nowDate());
								punishDao.insertSelective(punish);//插入惩罚记录表
							}
						}
					}
				}
			}*/	
		}
		return outParam;
	}

	@Override
	public OutParamStoreInCheckRequest storeCheckRequset(InParamStoreInCheckRequest inRequest) {
		 System.out.println("            JCGServiceImpl    storeCheckRequset   不处理的  ");
		return null;
	}

	//存入请求
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
				logger.info("设备号："+terminalID+"卡号："+inParam.getOpenBoxCode()+"业务类型编码："+businessCode+"业务模型："+businessModelMap+"获取的转换规则："+sCardTransRuleCode+"转换后卡号："+openBoxCode+"获取运营时段的ID："+sRunTimeCode);
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
					if (userEntity == null || userEntity.getState() ==1){
						outParam.setErrorCode(JCGErrorCode.ERR_PUT_ATH_FAIL);
						return outParam;
					}
				}
			}

			//Step.4 查询违规记录
			String sCheckVinolation = businessModelMap.get(Constant.BT_CFG_NAME_VINOLATIONSWITCH);
			if ( StringUtils.isNotEmpty(sCheckVinolation) ){
				boolean isCheckVinolation = sCheckVinolation.equalsIgnoreCase(Constant.BT_CFG_NAME_VINOLATIONSWITCH_OFF);
				if (isCheckVinolation){
					//Step.4.1 验证惩罚记录表
					PunishRecordEntityExample example = new PunishRecordEntityExample();
					example.createCriteria().andUsercardidEqualTo(openBoxCode).andPunishstateNotEqualTo((byte)0);
					long count = punishDao.countByExample(example);
					logger.info("获取是否开启身份验证："+sCheckIdentity+"  1开 0关"+"获取是否开启惩罚："+sCheckVinolation+"  0开 1关"+"是否有惩罚记录："+count+"  0无");
					if (count > 0){
						outParam.setErrorCode(JCGErrorCode.ERR_PUT_CARD_INVALID);
						return outParam;					
					}
				}
				//存物间隔时间
				String intervalBetween = businessModelMap.get(Constant.BT_CFG_NAME_STOREININTERVALTIME);	
				if(intervalBetween != null){
					//间隔时间
					if(Integer.parseInt(intervalBetween)  > 0 ){
						//查询上次存物记录
						TakeOutRecordEntity takeOutRecordEntity = takeOutRecordExDao.selectMaxTakeTime(terminalID, openBoxCode);
						if(takeOutRecordEntity != null){
							Date takeTiem = takeOutRecordEntity.getTaketime(); //最大取物时间
							//存物间隔时间验证
							Calendar calendar = Calendar.getInstance();	
							calendar.setTime(takeTiem);
							calendar.add(Calendar.MINUTE,Integer.valueOf(intervalBetween));
							if (!DateUtils.nowDate().after(calendar.getTime())) {
								outParam.setErrorCode(JCGErrorCode.ERR_PUT_CARD_INTERVAL);
								return outParam;
							}
						}
					}
				}
			}

			//Step.5 一卡一箱验证
			String sOneCardOneBox = businessModelMap.get(Constant.BT_CFG_NAME_ONECARDONEBOX);
			if (StringUtils.isNotEmpty(sOneCardOneBox)){
				boolean isOneCardOneBox = sOneCardOneBox.equalsIgnoreCase(Constant.BT_CFG_NAME_ONECARDONEBOX_ON);
				if (!isOneCardOneBox){
					outParam.setErrorCode(JCGErrorCode.ERR_OK);
					return outParam;
				} else {
					//Step.6 查询在箱记录
					List<StoreInRecordEx> records = storeInRecordExDao.queryInBoxRecord(openBoxCode, businessCode);
					if (records == null || records.size() == 0){	
						outParam.setErrorCode(JCGErrorCode.ERR_OK);
						return outParam;	
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
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("异常："+e.getMessage());
		} 
		return outParam;
	}

	//存入确认
	@Override
	@Transactional
	public OutParamStoreInConfirm storeIn(InParamStoreInConfirm inParam) { 
		OutParamStoreInConfirm outParam = new OutParamStoreInConfirm();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);

		//获取请求数据
		String terminalID  = inParam.getTerminalID();
		int boxID          = inParam.getBoxID();
		String openBoxCode = inParam.getOpenBoxCode();
		Date storeInTime   = inParam.getStoreInDate();
		int money          = inParam.getMoney();
	 
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
		
		//查询是否存在
		StoreInRecordEntity storeInRecordEntity = storeInRecordExDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
		if (storeInRecordEntity != null){
			return outParam;
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
			}
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
	
		} catch (Exception e) {
			outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
			logger.error(e.getMessage());
		} 
		return outParam;

	}

	//取物请求
	@Override
	public OutParamTakeOutRequest takeRequest(InParamTakeOutRequest inParam) {

		//初始返回值
		OutParamTakeOutRequest outParam = new OutParamTakeOutRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);

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
			logger.info("取请求   设备号："+terminalID+"卡号："+inParam.getOpenBoxCode()+"业务信息"+terminalEntity+"业务编码："+businessCode+"业务模型："+businessModelMap+"转换后卡号："+openBoxCode);
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

		//Step.4 查询违规记录
		String sCheckVinolation = businessModelMap.get(Constant.BT_CFG_NAME_VINOLATIONSWITCH);
		if ( StringUtils.isNotEmpty(sCheckVinolation) ){
			boolean isCheckVinolation = sCheckVinolation.equalsIgnoreCase(Constant.BT_CFG_NAME_VINOLATIONSWITCH_ON);
			if (isCheckVinolation){
				//Step.4.1 验证惩罚记录表
				PunishRecordEntityExample example = new PunishRecordEntityExample();
				example.createCriteria().andUsercardidEqualTo(openBoxCode).andPunishstateNotEqualTo((byte)0);
				long count = punishDao.countByExample(example);
				if (count > 0){
					outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_INVALID);
					return outParam;					
				}
			}
		}

		//Step.5 查询在箱记录
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		example.createCriteria().andUsercardidEqualTo(openBoxCode)
								.andStateEqualTo((byte)0);
		example.setOrderByClause("storeInTime desc");
		List<StoreInRecordEntity> records = storeInRecordExDao.selectByExample(example);
		if (records == null || records.size() == 0){
			outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
			return outParam;
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

	//取物确认
	@Override
	@Transactional
	public OutParamTakeOutConfirm takeOut(InParamTakeOutConfirm inParam) {

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
			midwayTakeRecordEntity = midwayTakeRecordEntityDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime, takeOutTime);
			if(midwayTakeRecordEntity != null){
				return outParam;
			}
		} else {
			takeOutRecordEntity = takeOutRecordEntityDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
			if (takeOutRecordEntity != null){
				return outParam;				
			}
		}

		//Step.4 查询在箱记录表是否存在记录，没有则写入
		boolean isExistInBox = true;
		storeInRecordEntity = storeInRecordExDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
		try{
			if (storeInRecordEntity == null){
				isExistInBox = false;
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
					storeInRecordEntity.setState(isFixbox ? (byte)0 : (byte)1);
					storeInRecordEntity.setMoney((float)money);
					storeInRecordEntity.setRealmoney((float)0);
					storeInRecordExDao.insertSelective(storeInRecordEntity);	
				} catch (Exception e) {
					outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
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
				takeOutRecordEntity.setMakeopcode("");
				takeOutRecordEntityDao.insert(takeOutRecordEntity);

				if (isExistInBox){
					storeInRecordEntity.setState((byte)1);
					storeInRecordExDao.updateByPrimaryKeySelective(storeInRecordEntity);
				}
			}
		}
		catch (Exception e){
			outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
			logger.error(e.getMessage());
		} 
		return outParam;
	}

	//中途取物确认
	@Override
	@Transactional
	public OutParamTakeOutConfirmOfMidway takeOutOfMidway(InParamTakeOutConfirmOfMidway inParam) {
		OutParamTakeOutConfirmOfMidway outParam = new OutParamTakeOutConfirmOfMidway();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);

		//获取请求数据
		String terminalID  = inParam.getTerminalID();
		int boxID          = inParam.getBoxID();
		String openBoxCode = inParam.getOpenCode();
		Date storeInTime   = inParam.getStorInDate();
		Date takeOutTime   = inParam.getTakeOutDate();
		int money          = inParam.getMoney();
 
		//Step.1 卡号转换
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

		//Step.2
		MidwayTakeRecordEntity midwayTakeRecordEntity = midwayTakeRecordEntityDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime, takeOutTime);
		if(midwayTakeRecordEntity != null){
			return outParam;
		}

		//Step.3 查询在箱记录表是否存在记录，没有则写入
		StoreInRecordEntity storeInRecordEntity = storeInRecordExDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
		try{
			if (storeInRecordEntity == null){
				//Step.3.1 查询用户信息
				storeInRecordEntity = new StoreInRecordEntity();
				UserEntity userEntity = userService.select(openBoxCode);
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
				}
				//Step.3.2 写入在箱记录表
				storeInRecordEntity = new StoreInRecordEntity();
				storeInRecordEntity.setTerminalid(terminalID);
				storeInRecordEntity.setBoxid(boxID);
				storeInRecordEntity.setUsercardid(openBoxCode);
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)0);
				storeInRecordEntity.setMoney(0f);	
				storeInRecordEntity.setUsertype(userEntity.getUsertype());
				storeInRecordExDao.insert(storeInRecordEntity);
			}

			//Step.4 写中途取物记录
			midwayTakeRecordEntity = new MidwayTakeRecordEntity();
			midwayTakeRecordEntity.setTerminalid(terminalID);
			midwayTakeRecordEntity.setBoxid(boxID);
			midwayTakeRecordEntity.setUsercardid(openBoxCode);
			midwayTakeRecordEntity.setStoreintime(storeInTime); 
			midwayTakeRecordEntity.setTaketime(takeOutTime);
			midwayTakeRecordEntity.setMoney((float)money);
			midwayTakeRecordEntity.setRealmoney((float)money);
			midwayTakeRecordEntity.setType(1);
			midwayTakeRecordEntityDao.insert(midwayTakeRecordEntity);
		} catch (Exception e){
			outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
			logger.error(e.getMessage());
		} 
		return outParam;
	}

	//管理取物确认
	@Override
	@Transactional
	public OutParamTakeOutConfirmByManager takeOutConfirmByManager(InParamTakeOutConfirmByManager inParam) {
		OutParamTakeOutConfirmByManager outParam = new OutParamTakeOutConfirmByManager();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);

		//获取请求数据
		String terminalID  = inParam.getTerminalID();
		int boxID          = inParam.getBoxID();
		String openBoxCode = inParam.getOpenCode();
		Date storeInTime   = inParam.getStorInDate();
		Date takeOutTime   = inParam.getTakeOutDate();
		int money          = inParam.getMoney();

 		//Step.1 卡号转换
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

		TakeOutRecordEntity takeOutRecordEntity = null;
		StoreInRecordEntity storeInRecordEntity = null;

		//Step.2 查询在箱记录表是否存在记录，没有则写入
		storeInRecordEntity = storeInRecordExDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);

		try{
			if (storeInRecordEntity == null){
				//写存物记录
				storeInRecordEntity = new StoreInRecordEntity();
				UserEntity userEntity = userService.select(openBoxCode);
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
				storeInRecordEntity.setUsercardid(storeInRecordEntity.getUsercardid());
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)1);
				storeInRecordEntity.setMoney((float)money);
				storeInRecordEntity.setRealmoney((float)0);
				storeInRecordExDao.insertSelective(storeInRecordEntity);	
			}

			//Step.3 写取物记录
			takeOutRecordEntity = new TakeOutRecordEntity();
			takeOutRecordEntity.setTerminalid(terminalID);
			takeOutRecordEntity.setBoxid(boxID);
			takeOutRecordEntity.setUsercardid(storeInRecordEntity.getUsercardid());
			takeOutRecordEntity.setStoreintime(storeInTime); 
			takeOutRecordEntity.setTaketime(takeOutTime);
			takeOutRecordEntity.setMoney((float)money);
			takeOutRecordEntity.setRealmoney((float)money);
			takeOutRecordEntity.setType(3);
			takeOutRecordEntity.setCashierno("0");
			takeOutRecordEntity.setMakeopcode(null);
			takeOutRecordEntityDao.insert(takeOutRecordEntity);
		} catch (Exception e){
			outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
			logger.error(e.getMessage());
		} 
		return outParam;
	}
		
	//报警
	@Override
	public void warningNotice() {
	}

	//管理卡开箱
	@Override
	public OutParamOpenBoxByManager openBoxByManager(InParamTakeOutConfirmByManager inParam) {

		OutParamOpenBoxByManager outParam = new OutParamOpenBoxByManager();

		//请求参数
		String openBoxCode = inParam.getOpenCode();
		String terminalID  = inParam.getTerminalID();

 		//终端信息
		TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
		if (terminalEntity == null){
			outParam.setErrorCode(JCGErrorCode.ERR_GET_ATH_FAIL);
			return outParam;
		}

		//查询管理卡信息
		int type = 0;
		ManagerEntityExample managerEntityExample = new ManagerEntityExample();
		managerEntityExample.createCriteria().andManagercardidEqualTo(openBoxCode);
		List<ManagerEntity> managerEntitylist = managerDao.selectByExample(managerEntityExample);
		if (managerEntitylist == null || managerEntitylist.size() == 0) {
			outParam.setType(4); //用户卡
			
			//Step.1 卡号转换
			//根据设备号查询业务编码
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

			//查询是否用户卡
			StoreInRecordEntityExample example = new StoreInRecordEntityExample();
			example.createCriteria().andUsercardidEqualTo(openBoxCode)
									.andStateEqualTo((byte)0);
			example.setOrderByClause("storeInTime desc");
			List<StoreInRecordEntity> records = storeInRecordExDao.selectByExample(example);
			if (records == null || records.size() == 0){
				outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
				return outParam;
			}

			outParam.setErrorCode(JCGErrorCode.ERR_GET_EXIST_OTHER_GUI);	

			String id  = records.get(0).getTerminalid();
			int boxID = records.get(0).getBoxid();

			//设置显示柜号
			BoxEx boxEntityEx = boxExDao.selectByPrimaryKeys(id, boxID);
			TerminalEntity terminal = terminalExDao.selectByPrimaryKey(id);
			outParam.setDisplayName(terminal.getDisplayname());

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
			outParam.setBoxID((byte)boxID);
			//设置显示箱号
			outParam.setDisplayBoxName(boxEntityEx.getDispalyname());

			return outParam;
		}

		ManagerEntity managerEntity = managerEntitylist.get(0);
		
		//判断收费是黑名单管理员  1是
		if(managerEntity.getState() == 1){
			outParam.setErrorCode(JCGErrorCode.ERR_BOX_ADMIN_NULL);
			return outParam;
		}
		
		type = managerEntity.getManagertype();
		String sBoxList = "";

		switch (type) {
			case Constant.CARD_TYPE_USER:  //用户
				//返回箱号 柜号
				break;
			case Constant.CARD_TYPE_CLEAR: //清除 故障 经理
				{
					//添加维修卡日志
					WriteDiary(terminalID,openBoxCode,"维修卡开箱",sBoxList);

					outParam.setType(2);
					outParam.setErrorCode(JCGErrorCode.ERR_OK);
				}
				break;
		case Constant.CARD_TYPE_MANAGER_FREE: //空闲箱门
			{
				//查询空闲箱（根据终端号）
				List<BoxEntity> list = terminalExDao.selectFreeOpenBoxTerminalID(terminalID);
				int [] boxid = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					boxid[i] = list.get(i).getBoxid();
					
					sBoxList += boxid[i];
					if (i < list.size() - 1)
						sBoxList += ",";
				}
				//添加空闲卡箱门日志
				WriteDiary(terminalID, openBoxCode, "空闲卡开箱", sBoxList);

				outParam.setBoxid(boxid);
				outParam.setType(7);
				outParam.setErrorCode(JCGErrorCode.ERR_OK);		
			}
			break;
		case Constant.CARD_TYPE_MANAGER_CLEAN: //清洁卡
			{
				//查询已用箱（根据终端号）
				List<TakeOutRecordEntity> list = terminalExDao.selectAllBoxterminalID(terminalID);
				int [] boxid = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					boxid[i]=list.get(i).getBoxid();
					if (sBoxList == "") sBoxList = "" + boxid[i]; else sBoxList += ","+boxid[i];
				} 
				//添加清洁卡箱门日志
				WriteDiary(terminalID,openBoxCode,"清洁卡开箱",sBoxList);

				outParam.setBoxid(boxid);
				outParam.setType(3);
				outParam.setErrorCode(JCGErrorCode.ERR_OK);
			}
			break;
		case Constant.CARD_TYPE_MANAGER_TIMEOUT: //超时开箱
			{
				//查询超时箱并且是在箱记录（根据终端号）
				List<PunishRecordEntity> list = terminalExDao.selectTimeoutOpenBox(terminalID);
	
				int [] boxid = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					boxid[i]=list.get(i).getBoxid();
					if (sBoxList == "") sBoxList = "" + boxid[i]; else sBoxList += ","+boxid[i];
				}
	
				//添加超时卡箱门日志
				WriteDiary(terminalID,openBoxCode,"超时卡开箱",sBoxList);
	
				outParam.setBoxid(boxid);
				outParam.setType(6);
				outParam.setErrorCode(JCGErrorCode.ERR_OK);
			}
			break;
		
		case Constant.CARD_TYPE_MANAGER_ALL: //管理者
			{
				//添加空闲卡箱门日志
				WriteDiary(terminalID,openBoxCode,"管理卡开箱",sBoxList);
				outParam.setType(1);
				outParam.setErrorCode(JCGErrorCode.ERR_OK);
			}
		break;	
		case Constant.CARD_TYPE_MANAGER_FULLYOPEN: //全开卡
			{
				//添加全开箱门日志
				WriteDiary(terminalID,openBoxCode,"全开卡开箱","All Box");
	
				int[] boxid ={0};
				outParam.setBoxid(boxid);
				outParam.setType(5);
				outParam.setErrorCode(JCGErrorCode.ERR_OK);
			}
			break;
		default:
			break;
		} 
		return outParam;	
	}
    //设备断开
	@Override
	public OutParam disconnect(InParam ps) {
		
		OutParam outParam = new OutParam();
		TerminalEntity terminalEntity = new TerminalEntity();
		terminalEntity.setTerminalid(ps.getTerminalID());
		terminalEntity.setNetworkstate((byte)1);
		int a = terminalExDao.updateByPrimaryKeySelective(terminalEntity);
		logger.info("JCGServiceImpl TerminalID:"+ps.getTerminalID()+"disconnect!");
		if (a==1) {
			outParam.setErrorCode(JCGErrorCode.ERR_OK);
			return outParam;
		}else {
			outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
			return outParam;
		}		
	}

	@Override
	public OutParamAlarmNoticesRequest alarmNotices(InParamAlarmNoticesRequest alarmNotices) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
