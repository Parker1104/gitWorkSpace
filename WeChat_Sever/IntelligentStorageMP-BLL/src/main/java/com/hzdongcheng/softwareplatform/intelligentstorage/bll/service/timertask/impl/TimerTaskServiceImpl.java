/**
 * All rights Reserved, Designed By Android_Robot   
 * @Title:  TimerTaskService.java   
 * @Package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: Jxing     
 * @date:   2017年5月17日 上午9:30:35   
 * @version V1.0     
 */
package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask.impl;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.msgnotification.helper.HuNanShiFanEMailNotify;
import com.hzdongcheng.components.msgnotification.model.Receiver;
import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.exception.InvalidArgumentException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.utils.TypeConvertUtils;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCCheckInUser;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCClearBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCOpenBox;
import com.hzdongcheng.front.server.model.service.jcg.down.OutParamRCCommon;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.PushClientFactory;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICardAndBoxBound;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPunishRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask.ITimerTaskService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardAndBoxBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PunishRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MessageNoticeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MessageNoticeRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.WeChatMessageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.WeChatMessageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BusinessModelEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.MessageNoticeRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PunishRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.RunTimeEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.UserEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.WeChatMessageEntityDao;

/** 
 * @ClassName: TimerTaskService 
 * @Description: TODO(定时任务业务实现类) 
 * @author Jxing 
 * @date 2017年5月17日 上午9:30:35 
 * @version 1.0 
 */
@Service
public class TimerTaskServiceImpl implements ITimerTaskService{

	@Override
	public void cardAndBoxBound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateViolationRecord() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetViolationLiftTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openBoxWhenTimeoutAtSpecifiedTime() {
		// TODO Auto-generated method stub
		
	}

	/*//缓冲业务类型Map,用于比对是否发生变化。如果发生变化，需要重新启动开箱定时任务
	private Map<Integer, Map<String , String>> oldBusinessModelMap = null;
	//缓冲开箱定时任务线程对象
	private Map<Integer, ScheduledExecutorService> openBoxTaskMap = new HashMap<Integer, ScheduledExecutorService>();
	//开箱提醒定时任务
	private Map<Integer, ScheduledExecutorService> openBoxNotifyTaskMap = new HashMap<Integer, ScheduledExecutorService>();

	//系统第一次运行标识
	private boolean initizale = true;
	//日志类
	private Log4jUtils Logger = Log4jUtils.createInstanse(this.getClass());
	@Autowired
	IPunishRecord iPunishRecord;
	@Autowired
	ICardAndBoxBound cardAndBoxBoundDao;
	@Autowired
	private StoreInRecordExDao storeInRecordExDao;
	@Autowired
	private TakeOutRecordEntityDao takeOutRecordEntityDao;
	@Autowired
	private PunishRecordEntityDao punishRecordEntityDao;
	@Autowired
	private BusinessModelEntityExDao businessModelEntityDao;
	@Autowired
	private TerminalExDao terminalExDao;
	@Autowired
	private RunTimeEntityDao runtimeEntityDao;
	@Autowired
	private PunishRecordExDao punishRecordEntityExDao;
	@Autowired
	private UserEntityDao userEntityDao; 
	@Autowired
	private WeChatMessageEntityDao weChatMessageEntityDao;
	@Autowired
	private MessageNoticeRecordEntityDao messageNoticeRecordEntityDao;
	 (non Javadoc) 
	 * <p>Title: cardAndBoxBound</p> 
	 * <p>Description: </p>  
	 * @see com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask.ITimerTaskService#cardAndBoxBound() 
	 
	@Override
	public void cardAndBoxBound() {
		System.out.println("卡箱绑定任务开始执行...");
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);

			List<CardAndBoxBoundEx> list = cardAndBoxBoundDao.selectBySync();
			for (int i = 0; i < list.size(); i++) { 
				String terminalid =  list.get(i).getTerminalid();
				int boxid = list.get(i).getBoxid();
				String cardid = list.get(i).getCardid();
				InParamRCCheckInUser inParams = new InParamRCCheckInUser();			
				inParams.setTerminalID(terminalid);
				inParams.setCardID(cardid);
				inParams.setTime(list.get(i).getMakedate());
				inParams.setBoxID(boxid);

				OutParamRCCommon outParamm = null;
				try {
					outParamm = jcgCtrl.checkInUser("tcp://127.0.0.1:55666", inParams);
				} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
					Logger.info("注册卡失败. Terminal ID: " + terminalid + ", Box ID: " + boxid + 
							", Card ID: " + cardid + ". error code:" + e.getErrorCode() + "," + e.getMessage());
				}
				Robot r  =  new Robot();
				r.delay(2000);
				System.out.println("延时两秒。。。");
				if (outParamm != null){
					CardAndBoxBoundEntity card = new CardAndBoxBoundEntity();
					card.setTerminalid(terminalid);
					card.setBoxid(boxid);
					card.setCardid(cardid);
					card.setSync((byte)1);

					if(outParamm.getErrorCode() == 0){		
						cardAndBoxBoundDao.update(card);
						Logger.info("注册卡成功. Terminal ID: " + terminalid + ", Box ID: " + boxid + ", Card ID: " + cardid);
					} else if (outParamm.getErrorCode() == 4){
						Logger.warn("注册卡失败,重复录卡. Terminal ID: " + terminalid + ", Box ID: " + boxid + 
								", Card ID: " + cardid + ". error code:" + outParamm.getErrorCode());

						InParamRCClearBox inParamRCClearBox = new InParamRCClearBox();
						inParamRCClearBox.setTerminalID(terminalid);
						inParamRCClearBox.setBoxID(boxid);

						try {
							jcgCtrl.clearBox("tcp://127.0.0.1:55666", inParamRCClearBox);
						} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
							Logger.error("清除重复卡失败");
						}

					} else {
						Logger.warn("注册卡失败. Terminal ID: " + terminalid + ", Box ID: " + boxid + 
								", Card ID: " + cardid + ". error code:" + outParamm.getErrorCode());					
					}
				} else{
					System.out.println("注册卡失败: out param is null.");
				}
			}
		} catch (AWTException e1) {			
			e1.printStackTrace();
		}
	}

	 (non Javadoc) 
	 * <p>Title: generateViolationRecord</p> 
	 * <p>Description: </p>  
	 * @see com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask.ITimerTaskService#generateViolationRecord() 
	 
	@Override
	public void generateViolationRecord(){

		Map<Integer, Map<String , String>> businessModelMap = getBusinessModelToMap(businessModelEntityDao);

		boolean isLoadEmailComponent = false;
		HuNanShiFanEMailNotify notify = null;

		//查询在箱且未生成违规记录的信息
		List<StoreInRecordEntity> records = storeInRecordExDao.selectNotViolation();
		for (StoreInRecordEntity record : records) {

			//查询业务类型
			String terminalID = record.getTerminalid(); //设备号
			Integer boxID     = record.getBoxid();      //箱号
			String cardID     = record.getUsercardid(); //用户卡号
			Date storeInTime  = record.getStoreintime();//存物时间

			//根据设备号查询业务类型编码
			Integer businessCode = getBusinessCodeByTerminalID(terminalExDao, terminalID);
			if (businessCode == null)
				continue;

			//得到控制参数
			Map<String, String> controlParams = businessModelMap.get(businessCode);

			//违规功能开启标识
			boolean violationOnOff = Constant.BT_CFG_NAME_VINOLATIONSWITCH_OFF.equals(controlParams.get(Constant.BT_CFG_NAME_VINOLATIONSWITCH));
			if (!violationOnOff)
				continue;

			//得到免费时段
			String sFreeTime = controlParams.get(Constant.BT_CFG_NAME_FREETIME);
			if (StringUtils.isEmpty(sFreeTime)){
				sFreeTime = "0";
			}
			int freeTime = Integer.valueOf(sFreeTime);

			boolean isViolation = false;

			if (freeTime == 0){
				//根据运营时段分析违规记录
				//取出运营时段
				String sRuntimeCode = controlParams.get(Constant.BT_CFG_NAME_RUNTIME);
				if (StringUtils.isEmpty(sRuntimeCode)){
					continue;
				}
				int runtimeCode = Integer.valueOf(sRuntimeCode);
				int weekOfDay   = DateUtils.getWeekOfDate(storeInTime);

				//得到截至时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String sEndTime = sdf.format(storeInTime) + " " + getRuntimeModeByCode(runtimeEntityDao, runtimeCode, weekOfDay);
				try {
					Date endTime = sdf2.parse(sEndTime);
					//如果截至时间在当前之前，在箱记录违规
					if (endTime.before(DateUtils.nowDate())){
						isViolation = true;
					}
				} catch (ParseException e) {
					Logger.error(e.getMessage());
				}
			} else {
				//根据免费时长分析违规记录
				Calendar calendar = Calendar.getInstance(); 
				calendar.setTime(storeInTime);
				calendar.add(Calendar.MINUTE, freeTime);
				Calendar now = Calendar.getInstance(); 
				now.setTime(DateUtils.nowDate());
				//免费截至时间在当前时间之后，记录违规
				if (calendar.before(now)){
					isViolation = true;
				}

			}

			if (isViolation){

				PunishRecordEntity punishRecordEntity = new PunishRecordEntity();

				punishRecordEntity.setUsercardid(cardID);
				punishRecordEntity.setTerminalid(terminalID);
				punishRecordEntity.setBoxid(boxID);
				punishRecordEntity.setStoreintime(storeInTime);
				punishRecordEntity.setEnddate(DateUtils.getMinDate());
				punishRecordEntity.setMoney(0f);
				punishRecordEntity.setRealmoney(0f);
				punishRecordEntity.setPunishstate(TypeConvertUtils.intToByte(1));
				punishRecordEntity.setMakedate(DateUtils.nowDate());
				punishRecordEntityDao.insert(punishRecordEntity);

				TerminalEntity terminalEntity = null;
				UserEntity userEntity = null;

				//判断微信提示是否开启
				if(controlParams.get(Constant.BT_CFG_NAME_WECHAT).equalsIgnoreCase(Constant.BT_CFG_NAME_WECHAT_ON) ){

					terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
					userEntity = userEntityDao.selectByPrimaryKey(cardID);

					//IdName同学：您在CaseInformation号箱的存物已超期请尽快取出.否则系统将在Time点自动打开箱门。如有丢失后果自负。

					if(userEntity != null && terminalEntity != null){

						String timeoutSavePackage = Constant.INFORMATION_REMIND_TIMEOUTPROMPT.replaceAll("IdName",userEntity.getUsername())
								.replaceAll("CaseInformation", boxID.toString()).replaceAll("Time", controlParams.get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME));

						WeChatMessageEntity weChatMessageEntity= new WeChatMessageEntity();

						weChatMessageEntity.setStudentno(userEntity.getIdcode());
						weChatMessageEntity.setCardno(userEntity.getUsercardid());
						weChatMessageEntity.setName(userEntity.getUsername());
						weChatMessageEntity.setStatus("未发送");
						weChatMessageEntity.setAddtime(DateUtils.nowDate());
						weChatMessageEntity.setSendtime(DateUtils.nowDate());
						weChatMessageEntity.setUnitname(userEntity.getCompany());
						weChatMessageEntity.setPhonenumber(userEntity.getTelephone());
						weChatMessageEntity.setOpeniddy(userEntity.getSubdepartment());
						weChatMessageEntity.setOpenidfw(userEntity.getTelephone());	
						weChatMessageEntity.setEmail(userEntity.getAddress());
						weChatMessageEntity.setOperatetype("惩罚提醒");
						weChatMessageEntity.setSavetime(storeInTime);
						weChatMessageEntity.setBoxinfo(terminalEntity.getAddress() +"箱号是"+boxID.toString());
						weChatMessageEntity.setSendcontent(timeoutSavePackage);


						WeChatMessageEntityExample example = new  WeChatMessageEntityExample();
						example.createCriteria().andCardnoEqualTo(userEntity.getUsercardid())
						.andSavetimeEqualTo(storeInTime).andSendcontentEqualTo(timeoutSavePackage);
						List<WeChatMessageEntity>  weChatMessage = weChatMessageEntityDao.selectByExample(example);
						if(weChatMessage.size()<1){
							weChatMessageEntityDao.insertSelective(weChatMessageEntity);
							//写信息通知记录表
							MessageNoticeRecordEntity messageNoticeRecord = new MessageNoticeRecordEntity();
							messageNoticeRecord.setTerminalid(terminalID);
							messageNoticeRecord.setCardid(userEntity.getUsercardid());
							messageNoticeRecord.setBoxid(boxID);
							messageNoticeRecord.setStoredtime(storeInTime);
							messageNoticeRecord.setStoreddate(null);
							messageNoticeRecord.setMessagetype("2");
							messageNoticeRecord.setCustomer(userEntity.getSubdepartment());
							messageNoticeRecord.setCustomername(userEntity.getUsername());
							messageNoticeRecord.setSendcontent(timeoutSavePackage);
							messageNoticeRecord.setSendstatus("2");
							messageNoticeRecord.setLastmodifytime(DateUtils.nowDate());
							messageNoticeRecordEntityDao.insertSelective(messageNoticeRecord);
						}
						Logger.info("超期处罚微信提醒："+timeoutSavePackage);
					}
				}

				//判断邮箱提示是否开启
				if(controlParams.get(Constant.BT_CFG_NAME_EMAIL).equalsIgnoreCase( Constant.BT_CFG_NAME_EMAIL_ON)){
					if (terminalEntity == null)
						terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);

					if (userEntity == null)
						userEntity = userEntityDao.selectByPrimaryKey(cardID);

					if (!isLoadEmailComponent){
						//开启写邮箱提醒
						notify = HuNanShiFanEMailNotify.getInstance("逾期存包违规通知");				    		
						isLoadEmailComponent = true;
					}

					if (notify != null){
						if(userEntity != null && terminalEntity != null){
							Receiver receiver = new Receiver();
							receiver.setAddress(userEntity.getAddress());
							receiver.setId(cardID);
							receiver.setName(userEntity.getUsername());
							receiver.setLockerNo(terminalEntity.getDisplayname());
							receiver.setBoxNo(boxID.toString());
							String dt = storeInTime.toString();
							SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
							SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							try {
								receiver.setStoreInTime(sdf2.format(sdf1.parse(dt)));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							receiver.setTakeoutTime("");
							receiver.setMoney("0");
							receiver.setServicePhone("0571-86468639");
							try {

								MessageNoticeRecordEntityExample example = new MessageNoticeRecordEntityExample();
								example.createCriteria().andTerminalidEqualTo(terminalID).andBoxidEqualTo(boxID)
								.andCardidEqualTo(userEntity.getUsercardid()).andStoredtimeEqualTo(storeInTime)
								.andMessagetypeEqualTo("1").andSendcontentEqualTo("逾期存包违规通知");
								List<MessageNoticeRecordEntity> messageNoticeRecordList = messageNoticeRecordEntityDao.selectByExample(example);
								if(messageNoticeRecordList.size()<1)
								{
									notify.expiriedNotify(receiver);
									//写信息通知记录表
									MessageNoticeRecordEntity messageNoticeRecord = new MessageNoticeRecordEntity();
									messageNoticeRecord.setTerminalid(terminalID);
									messageNoticeRecord.setCardid(userEntity.getUsercardid());
									messageNoticeRecord.setBoxid(boxID);
									messageNoticeRecord.setStoredtime(storeInTime);
									messageNoticeRecord.setStoreddate(null);
									messageNoticeRecord.setMessagetype("1");
									messageNoticeRecord.setCustomer(userEntity.getAddress());
									messageNoticeRecord.setCustomername(userEntity.getUsername());
									messageNoticeRecord.setSendcontent("逾期存包违规通知");
									messageNoticeRecord.setSendstatus("2");
									messageNoticeRecord.setLastmodifytime(DateUtils.nowDate());
									messageNoticeRecordEntityDao.insertSelective(messageNoticeRecord);
								}
							} catch (MessageSendException | InvalidArgumentException e) {
								// TODO Auto-generated catch block
								Logger.error("邮件错误："+e.getMessage());
							}
						}
					}					

				}
			}
		}

		if (notify != null){
			notify.release();
		}
	}

	 (non Javadoc) 
	 * <p>Title: resetViolationLiftTime</p> 
	 * <p>Description: </p>  
	 * @see com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask.ITimerTaskService#resetViolationLiftTime() 
	 
	@Override
	public void resetViolationLiftTime() {

		//查询所有开启自动解禁功能的业务类型
		Map<Integer, Map<String, String>> businessModelMap = getAutoRelieveBusinessCode(businessModelEntityDao);

		if (businessModelMap == null || businessModelMap.size() == 0)
			return;

		//查询所有开启自动解禁功能的设备
		List<String> terminalList = getAutoRelieveTerminalID(businessModelMap, terminalExDao);
		if (terminalList == null || terminalList.size() == 0)
			return;

		//根据设备查询所有违规记录
		List<PunishRecordEntity> records = getAutoPunishRecord(punishRecordEntityExDao, terminalList);
		if (records == null || records.size() == 0)
			return;

		for (PunishRecordEntity record : records) {

			//查询业务类型
			String terminalID = record.getTerminalid();  //设备号
			Integer boxID     = record.getBoxid();       //箱号
			String cardID     = record.getUsercardid();  //用户卡号
			Date storeInTime  = record.getStoreintime(); //存物时间
			Date endTime      = record.getEnddate();     //得到取物时间
			Byte state        = record.getPunishstate(); //惩罚标识 0:解禁 1:违规 9:自动解禁下已生成截至时间

			//根据设备号查询业务类型编码
			Integer businessCode = getBusinessCodeByTerminalID(terminalExDao, terminalID);
			if (businessCode == null)
				continue;

			PunishRecordEntity punishRecordEntity = new PunishRecordEntity();
			punishRecordEntity.setUsercardid(cardID);
			punishRecordEntity.setTerminalid(terminalID);
			punishRecordEntity.setBoxid(boxID);
			punishRecordEntity.setStoreintime(storeInTime);

			if (state == 1){
				//得到控制参数
				Map<String, String> controlParams = businessModelMap.get(businessCode);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endTime);
				long iUseTime = 0;
				long iTimeOutTime = 0;
				Long iPunishTime = null;
				if ( "1".equals(controlParams.get(Constant.BT_CFG_NAME_PUNISHTYPE_DAY))){
					//按指定惩罚天数
					calendar.add(Calendar.DATE, 1);
				} else {
					iUseTime = (endTime.getTime()-storeInTime.getTime())/60000;   //结束时间 -开始时间 = 存物时间
					iTimeOutTime = iUseTime- Long.valueOf((controlParams.get(Constant.BT_CFG_NAME_FREETIME).toString()));  
					iPunishTime = iTimeOutTime * Long.valueOf((controlParams.get(Constant.BT_CFG_NAME_PUNISHTYPE_MULTIPLE).toString()));
					//按倍率
					calendar.add(Calendar.MINUTE,Integer.valueOf(iPunishTime.toString()));
				}	
				try {
					punishRecordEntity.setEnddate(calendar.getTime());
					punishRecordEntity.setPunishstate(TypeConvertUtils.intToByte(9));
					punishRecordEntity.setMoney((float)0);
					punishRecordEntity.setRealmoney((float)0);
					punishRecordEntityExDao.updateByPrimaryKeySelectiveEx(punishRecordEntity);
				} catch (Exception e) {
					// TODO: handle exception
					Logger.error(e.getMessage());
				}

			} else {	
				//判断当前时间是否超过终止时间，如果超过则自动解禁
				if (DateUtils.nowDate().after(endTime)){
					try {
						punishRecordEntity.setEnddate(endTime);
						punishRecordEntity.setPunishstate(TypeConvertUtils.intToByte(0));
						//iPunishRecord
						punishRecordEntityExDao.updateByPrimaryKeySelectiveEx(punishRecordEntity);
					} catch (Exception e) {
						// TODO: handle exception
						Logger.error(e.getMessage());
					}	
				}
			}
		}
	}

	 (non Javadoc) 
	 * <p>Title: openBoxWhenTimeoutAtSpecifiedTime</p> 
	 * <p>Description: </p>  
	 * @see com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask.ITimerTaskService#openBoxWhenTimeoutAtSpecifiedTime() 
	 
	@Override
	public void openBoxWhenTimeoutAtSpecifiedTime() {
		System.out.println("定时开箱任务...");
		//系统第一次加载时，获取业务类型
		if (initizale){
			initizale = false;
			oldBusinessModelMap = getBusinessModelToMap(businessModelEntityDao);
		}

		//查询所有开启自动解禁功能的业务类型
		Map<Integer, Map<String, String>> businessModelMap = getAutoRelieveBusinessCode(businessModelEntityDao);
		if (businessModelMap == null || businessModelMap.size() == 0)
			return;

		//遍历map，找到开启定时开箱功能的业务类型
		//启动定时开箱任务
		for (Map.Entry<Integer, Map<String, String>> entry : businessModelMap.entrySet()) {

			Integer businessCode = entry.getKey();

			boolean isOpen = "1".equals(entry.getValue().get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT));

			if (!isOpen){
				//检查是否存在定时开箱任务，如果有停止任务
				ScheduledExecutorService service = openBoxTaskMap.get(businessCode);
				if (service != null){
					service.shutdown();
					service = null;
					openBoxTaskMap.remove(businessCode);
				}

				continue;
			}

			String sTime = "24:00";
			if (StringUtils.isNotEmpty(entry.getValue().get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME))){
				sTime = entry.getValue().get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME);
			}

			//获取超时时间
			String sExceedTime = "0";
			int exceedTime = 0;
			if (StringUtils.isNotEmpty(entry.getValue().get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_HOURS))){
				sExceedTime = entry.getValue().get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_HOURS);
				exceedTime = Integer.valueOf(sExceedTime);
			}

			String sNotifyTime = "1";
			int notifyTime = 1;
			if (StringUtils.isNotEmpty(entry.getValue().get(Constant.BT_CFG_NAME_RENINDED))){
				sNotifyTime = entry.getValue().get(Constant.BT_CFG_NAME_RENINDED);
				notifyTime = Integer.valueOf(sNotifyTime);
			}			

			//如果在系统第一次加载时，未获取到业务类型，则重新加载
			if (oldBusinessModelMap == null || oldBusinessModelMap.size() == 0){
				oldBusinessModelMap = getBusinessModelToMap(businessModelEntityDao);
			}

			String oldTime = "24:00";
			if(StringUtils.isNotEmpty(oldBusinessModelMap.get(businessCode).get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME))){
				oldTime = oldBusinessModelMap.get(businessCode).get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME);
			}

			String sOldExceedTime = "0";
			if (StringUtils.isNotEmpty(oldBusinessModelMap.get(businessCode).get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_HOURS))){
				sOldExceedTime = oldBusinessModelMap.get(businessCode).get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_HOURS);
			}

			String sOldNotifyTime = "1";
			if (StringUtils.isNotEmpty(oldBusinessModelMap.get(businessCode).get(Constant.BT_CFG_NAME_RENINDED))){
				sOldNotifyTime = oldBusinessModelMap.get(businessCode).get(Constant.BT_CFG_NAME_RENINDED);
			}

			ScheduledExecutorService openBoxService = null;
			ScheduledExecutorService openBoxNotifyService = null;

			if (oldTime.equals(sTime) && sOldExceedTime.equals(sExceedTime)){
				//参数没有变更
				openBoxService = openBoxTaskMap.get(businessCode);
			} else {
				//参数发生变更
				oldBusinessModelMap.get(businessCode).put(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME, sTime);
				oldBusinessModelMap.get(businessCode).put(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_HOURS, sExceedTime);							
				openBoxService = openBoxTaskMap.get(businessCode);
				if (openBoxService != null){
					openBoxService.shutdown();
					openBoxService = null;
				}
			}

			if (sOldNotifyTime.equals(sNotifyTime)){
				//参数没有变更
				openBoxNotifyService = openBoxNotifyTaskMap.get(businessCode);			
			} else {
				oldBusinessModelMap.get(businessCode).put(Constant.BT_CFG_NAME_RENINDED, sNotifyTime);

				openBoxNotifyService = openBoxNotifyTaskMap.get(businessCode);
				if (openBoxNotifyService != null){
					openBoxNotifyService.shutdown();
					openBoxNotifyService = null;
				}				
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sTime = sdf.format(DateUtils.nowDate()) + " " + sTime + ":00";
			try {
				Date time = sdf2.parse(sTime);
				//开箱功能的
				if (DateUtils.nowDate().after(time)){
					//第二天的这个时刻
					time = DateUtils.addDay(time, 1);
				}

				int minutes = (int) ((time.getTime()-DateUtils.nowDate().getTime())/(1000 * 60));
				if(minutes == 0){
					minutes = minutes+1;
				}

				int notifyMinutes = (notifyTime *60) - minutes;

				//开箱定时参数变化
				if (openBoxService == null){	
					openBoxService = Executors.newSingleThreadScheduledExecutor();
					openBoxService.scheduleWithFixedDelay(new OpenBoxThread(businessCode, exceedTime), minutes, 24*60, TimeUnit.MINUTES);
					openBoxTaskMap.put(businessCode, openBoxService);    	
				}	
				//开箱提醒参数变化
				if (openBoxNotifyService == null){
					if (notifyMinutes > 0) {
						openBoxNotifyService = Executors.newSingleThreadScheduledExecutor();
						openBoxNotifyService.scheduleWithFixedDelay(new generatingIrregularityThread(businessCode, exceedTime), 0, 24*60, TimeUnit.MINUTES);
						openBoxNotifyTaskMap.put(businessCode, openBoxNotifyService);
					}

				}	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	*//**
	 * 
	 * @Method Name: getBusinessCodeByTerminalID 
	 * @Description: TODO(根据设备号查询业务类型编码) 
	 * @param  @param dao
	 * @param  @param terminalID
	 * @param  @return
	 * @return Integer
	 *//*
	private Integer getBusinessCodeByTerminalID(TerminalExDao dao, String terminalID){
		TerminalEntity entity = dao.selectByPrimaryKey(terminalID);
		if (entity == null){
			return null;
		}
		return entity.getBusinesscode();
	}

	*//**
	 * 
	 * @Method Name: getRuntimeModeByCode 
	 * @Description: TODO(获取指定星期的截至时间) 
	 * @param  @param dao
	 * @param  @param runtimeCode
	 * @param  @param weekOfDay
	 * @param  @return
	 * @return String
	 *//*
	private String getRuntimeModeByCode(RunTimeEntityDao dao, int runtimeCode, int weekOfDay){
		RunTimeEntity entity = dao.selectByPrimaryKey(runtimeCode);

		String endTime = "23:00";

		switch (weekOfDay) {
		case 1:
			endTime = entity.getMonendruntime();
			break;
		case 2:
			endTime = entity.getTueendruntime();
			break;
		case 3:
			endTime = entity.getWedendruntime();
			break;
		case 4:
			endTime = entity.getThursendruntime();
			break;
		case 5:
			endTime = entity.getFriendruntime();
			break;
		case 6:
			endTime = entity.getSatendruntime();
			break;
		case 7:
			endTime = entity.getSunendruntime();
			break;
		default:
			break;
		}

		return endTime;
	}

	*//**
	 * 
	 * @Method Name: getBusinessModel 
	 * @Description: TODO(获取业务模型) 
	 * @param  @param businessModelEntityDao
	 * @param  @return
	 * @return Map<Integer,Map<String,String>>
	 *//*
	private Map<Integer, Map<String , String>> getBusinessModelToMap(BusinessModelEntityDao businessModelEntityDao){

		List<BusinessModelEntity> businessModelEntitys = businessModelEntityDao.selectByExample(null);
		if (businessModelEntitys == null || businessModelEntitys.size() == 0){
			return null;
		}

		Map<Integer, Map<String , String>> map = new HashMap<Integer, Map<String , String>>();
		for (BusinessModelEntity entity : businessModelEntitys) {
			Integer businessCode = entity.getBusinesscode();
			String configName    = entity.getConfigname();
			String configValue   = entity.getConfigvalue();

			Map<String , String> subMap = map.get(businessCode);
			if (subMap == null){
				subMap = new HashMap<String , String>();
				map.put(businessCode, subMap);
			}
			subMap.put(configName, configValue);
		}

		return map;
	}

	*//**
	 * 
	 * @Method Name: getBusinessModel 
	 * @Description: TODO(获取开启自动解禁业务模型) 
	 * @param  @param businessModelEntityDao
	 * @param  @return
	 * @return Map<Integer,Map<String,String>>
	 *//*
	private Map<Integer, Map<String , String>> getAutoRelieveBusinessCode(BusinessModelEntityDao businessModelEntityDao){

		List<BusinessModelEntity> businessModelEntitys = businessModelEntityDao.selectByExample(null);
		if (businessModelEntitys == null || businessModelEntitys.size() == 0){
			return null;
		}

		Map<Integer, Map<String , String>> returnMap = new HashMap<Integer, Map<String , String>>();

		Map<Integer, Map<String , String>> map = new HashMap<Integer, Map<String , String>>();
		for (BusinessModelEntity entity : businessModelEntitys) {
			Integer businessCode = entity.getBusinesscode();
			String configName    = entity.getConfigname();
			String configValue   = entity.getConfigvalue();

			Map<String , String> subMap = map.get(businessCode);
			if (subMap == null){
				subMap = new HashMap<String , String>();
				map.put(businessCode, subMap);
			}
			subMap.put(configName, configValue);
		}

		for (Map.Entry<Integer, Map<String, String>> entry : map.entrySet()) {
			Integer businessCode = entry.getKey();
			Map<String, String> subMap = entry.getValue();
			if (Constant.BT_CFG_NAME_VINOLATIONRELIEVETYPE_AUTO.equals(subMap.get(Constant.BT_CFG_NAME_VINOLATIONRELIEVETYPE))){
				returnMap.put(businessCode, subMap);
			}
		}

		return returnMap;
	}

	*//**
	 * 
	 * @Method Name: getAutoRelieveTerminalID 
	 * @Description: TODO(查询所有开启自动解禁功能的设备号) 
	 * @param  @param businessModelList
	 * @param  @param dao
	 * @param  @return
	 * @return List<String>
	 *//*
	private List<String> getAutoRelieveTerminalID(final Map<Integer, Map<String, String>> businessModelMap, TerminalExDao dao) {
		List<String> list = new ArrayList<>();

		TerminalEntityExample example = new TerminalEntityExample();
		for (Map.Entry<Integer, Map<String, String>> entry : businessModelMap.entrySet()) {	
			Integer code = entry.getKey();

			example.clear();
			example.createCriteria().andBusinesscodeEqualTo(code);

			List<TerminalEntity> entitys = dao.selectByExample(example);
			for (TerminalEntity terminalEntity : entitys) {
				list.add(terminalEntity.getTerminalid());
			}
		}

		return list;
	}

	*//**
	 * 
	 * @Method Name: getAutoPunishRecord 
	 * @Description: TODO(获取所有开启自动解禁且已取物的违规记录) 
	 * @param  @param dao
	 * @param  @param terminals
	 * @param  @return
	 * @return List<PunishRecordEntity>
	 *//*
	private List<PunishRecordEntity> getAutoPunishRecord(PunishRecordExDao dao, final List<String> terminals) {

		List<PunishRecordEntity> list = new ArrayList<>();

		for (String terminalID : terminals) {
			List<PunishRecordEntity> tmpList = dao.selectWhenAlreadyTakeOut(terminalID);
			if (tmpList == null || tmpList.size() == 0)
				continue;
			list.addAll(tmpList);
		}

		return list;
	}

	*//**
	 * 
	 * @Method Name: getAutoPunishRecord 
	 * @Description: TODO(获取所有开启自动解禁且已未取的违规记录) 
	 * @param  @param dao
	 * @param  @param terminals
	 * @param  @return
	 * @return List<PunishRecordEntity>
	 *//*
	private List<PunishRecordEntity> getAutoPunishRecordInBox(PunishRecordExDao dao, final List<String> terminals) {

		List<PunishRecordEntity> list = new ArrayList<>();

		for (String terminalID : terminals) {
			List<PunishRecordEntity> tmpList = dao.selectInBox(terminalID);
			if (tmpList == null || tmpList.size() == 0)
				continue;
			list.addAll(tmpList);
		}

		return list;
	}

	*//**
	 * 
	 * @ClassName: OpenBoxThread 
	 * @Description: TODO(开箱任务线程) 
	 * @author Jxing 
	 * @date 2017年5月17日 下午4:51:12 
	 * @version 1.0
	 *//*
	private class OpenBoxThread implements Runnable {

		private Integer businessCode;
		private int exceedTime;
		public OpenBoxThread(Integer businessCode, int exceedTime){
			this.businessCode = businessCode;
			this.exceedTime = exceedTime;
		}	
		public void run() {
			//根据设备号查询
			//查询所有开启自动解禁功能的设备
			Map<Integer, Map<String , String>> map = new HashMap<Integer, Map<String , String>>();
			map.put(businessCode, null);

			List<String> terminalList = getAutoRelieveTerminalID(map, terminalExDao);
			if (terminalList == null || terminalList.size() == 0)
				return;

			//根据设备查询所有违规记录
			List<PunishRecordEntity> records = getAutoPunishRecordInBox(punishRecordEntityExDao, terminalList);
			if (records == null || records.size() == 0)
				return;

			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);

			for(int i = 0; i<records.size(); i++){

				TakeOutRecordEntity takeOutEntity = new TakeOutRecordEntity();
				StoreInRecordEntity storeInRecordEntity = new StoreInRecordEntity();

				takeOutEntity.setUsercardid(records.get(i).getUsercardid());
				takeOutEntity.setTerminalid(records.get(i).getTerminalid());
				takeOutEntity.setBoxid(records.get(i).getBoxid());
				takeOutEntity.setStoreintime(records.get(i).getStoreintime());
				takeOutEntity.setTaketime(DateUtils.nowDate());
				takeOutEntity.setMoney(0F);
				takeOutEntity.setRealmoney(0F);
				takeOutEntity.setType(4);

				storeInRecordEntity.setUsercardid(records.get(i).getUsercardid());
				storeInRecordEntity.setTerminalid(records.get(i).getTerminalid());
				storeInRecordEntity.setBoxid(records.get(i).getBoxid());
				storeInRecordEntity.setStoreintime(records.get(i).getStoreintime());
				storeInRecordEntity.setState((byte)1);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(records.get(i).getStoreintime());
				calendar.add(Calendar.HOUR, exceedTime);

				if(DateUtils.nowDate().after(calendar.getTime())){	
					//执行开箱
					InParamRCOpenBox inParam = new InParamRCOpenBox();
					InParamRCClearBox inParamClearBox = new InParamRCClearBox();
					inParam.setTerminalID(records.get(i).getTerminalid());
					inParam.setBoxArray(new int[]{records.get(i).getBoxid()});
					Logger.info("开箱 终端号："+records.get(i).getTerminalid()+"箱号："+records.get(i).getBoxid());
					inParamClearBox.setTerminalID(records.get(i).getTerminalid());
					inParamClearBox.setBoxID(records.get(i).getBoxid());
					try {
						jcgCtrl.clearBox("tcp://127.0.0.1:55666", inParamClearBox);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						jcgCtrl.openBox("tcp://127.0.0.1:55666", inParam);
						//开箱成功更新数据库
						takeOutRecordEntityDao.insertSelective(takeOutEntity);
						storeInRecordExDao.updateByPrimaryKeySelective(storeInRecordEntity);
					} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}  
			}
		}
	}
	*//**
	 * 
	 * @ClassName: generatingIrregularity 
	 * @Description: TODO(提示功能) 
	 * @author  WenHeju
	 * @date 2017年5月17日 下午4:51:12 
	 * @version 1.0
	 *//*
	private class generatingIrregularityThread implements Runnable {

		private Integer businessCode;
		private int exceedTime;
		public generatingIrregularityThread(Integer businessCode, int exceedTime){
			this.businessCode = businessCode;
			this.exceedTime = exceedTime;
		}	
		public void run() {

			boolean isLoadEmailComponent = false;
			HuNanShiFanEMailNotify notify = null;
			TerminalEntity terminalEntity = null;
			UserEntity userEntity = null;

			//根据businessCode查询业务信息
			//根据设备号查询
			//查询所有开启自动解禁功能的设备
			Map<Integer, Map<String , String>> map = new HashMap<Integer, Map<String , String>>();
			map.put(businessCode, null);

			List<String> terminalList = getAutoRelieveTerminalID(map, terminalExDao);
			if (terminalList == null || terminalList.size() == 0)
				return;

			//根据设备查询所有违规记录
			List<PunishRecordEntity> records = getAutoPunishRecordInBox(punishRecordEntityExDao, terminalList);
			if (records == null || records.size() == 0)
				return;

			//查询所有开启自动解禁功能的业务类型
			Map<Integer, Map<String, String>> businessModelMap = getAutoRelieveBusinessCode(businessModelEntityDao);
			if (businessModelMap == null || businessModelMap.size() == 0)
				return;

			//遍历map，找到开启定时开箱功能的业务类型
			for (Map.Entry<Integer, Map<String, String>> entry : businessModelMap.entrySet()) {

				if (records == null || records.size() == 0)
					return;

				for(int i = 0; i<records.size(); i++){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(records.get(i).getStoreintime());
					calendar.add(Calendar.HOUR, exceedTime);
					//取到时间开始任务
					if(DateUtils.nowDate().after(calendar.getTime())){
						//判断微信提示是否开启
						if(entry.getValue().get(Constant.BT_CFG_NAME_WECHAT).equalsIgnoreCase(Constant.BT_CFG_NAME_WECHAT_ON) ){

							terminalEntity = terminalExDao.selectByPrimaryKey(records.get(i).getTerminalid());
							userEntity = userEntityDao.selectByPrimaryKey(records.get(i).getUsercardid());

							//IdName同学：您在CaseInformation号箱的存物已超期请尽快取出.否则系统将在Time点自动打开箱门。如有丢失后果自负
							if(userEntity != null && terminalEntity != null){
								String timeoutSavePackage = Constant.INFORMATION_REMIND_TIMEOUTPROMPT.replaceAll("IdName",userEntity.getUsername())
										.replaceAll("CaseInformation", records.get(i).getBoxid().toString())
										.replaceAll("Time", entry.getValue().get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME));
								WeChatMessageEntity weChatMessageEntity= new WeChatMessageEntity();	
								weChatMessageEntity.setStudentno(userEntity.getIdcode());
								weChatMessageEntity.setCardno(userEntity.getUsercardid());
								weChatMessageEntity.setName(userEntity.getUsername());
								weChatMessageEntity.setStatus("未发送");
								weChatMessageEntity.setAddtime(DateUtils.nowDate());
								weChatMessageEntity.setSendtime(DateUtils.nowDate());
								weChatMessageEntity.setUnitname(userEntity.getCompany());
								weChatMessageEntity.setPhonenumber(userEntity.getTelephone());
								weChatMessageEntity.setOpeniddy(userEntity.getSubdepartment());
								weChatMessageEntity.setOpenidfw(userEntity.getTelephone());	
								weChatMessageEntity.setEmail(userEntity.getAddress());
								weChatMessageEntity.setOperatetype("超期清箱提醒");
								weChatMessageEntity.setSavetime(records.get(i).getStoreintime());
								weChatMessageEntity.setBoxinfo(terminalEntity.getAddress()+"箱号是"+records.get(i).getBoxid().toString());
								weChatMessageEntity.setSendcontent(timeoutSavePackage);

								WeChatMessageEntityExample example = new  WeChatMessageEntityExample();
								example.createCriteria().andCardnoEqualTo(userEntity.getUsercardid())
								.andSavetimeEqualTo(records.get(i).getStoreintime()).andSendcontentEqualTo(timeoutSavePackage);
								List<WeChatMessageEntity>  weChatMessage = weChatMessageEntityDao.selectByExample(example);
								if(weChatMessage.size()<1){
									weChatMessageEntityDao.insertSelective(weChatMessageEntity);
									//写信息通知记录表
									MessageNoticeRecordEntity messageNoticeRecord = new MessageNoticeRecordEntity();
									messageNoticeRecord.setTerminalid(records.get(i).getTerminalid());
									messageNoticeRecord.setCardid(userEntity.getUsercardid());
									messageNoticeRecord.setBoxid(records.get(i).getBoxid());
									messageNoticeRecord.setStoredtime(records.get(i).getStoreintime());
									messageNoticeRecord.setStoreddate(null);
									messageNoticeRecord.setMessagetype("2");
									messageNoticeRecord.setCustomer(userEntity.getSubdepartment());
									messageNoticeRecord.setCustomername(userEntity.getUsername());
									messageNoticeRecord.setSendcontent(timeoutSavePackage);
									messageNoticeRecord.setSendstatus("2");
									messageNoticeRecord.setLastmodifytime(DateUtils.nowDate());
									messageNoticeRecordEntityDao.insertSelective(messageNoticeRecord);
									Logger.info("超期处罚微信提醒："+timeoutSavePackage); 
								}			 
							}
						}

						//判断邮箱提示是否开启
						if(entry.getValue().get(Constant.BT_CFG_NAME_EMAIL).equalsIgnoreCase( Constant.BT_CFG_NAME_EMAIL_ON)){
							if (terminalEntity == null)
								terminalEntity = terminalExDao.selectByPrimaryKey(records.get(i).getTerminalid());

							if (userEntity == null)
								userEntity = userEntityDao.selectByPrimaryKey(records.get(i).getUsercardid());

							if (!isLoadEmailComponent){
								//开启写邮箱提醒
								notify = HuNanShiFanEMailNotify.getInstance("逾期存包开箱通知");				    		
								isLoadEmailComponent = true;
							}

							if (notify != null){
								if(userEntity != null && terminalEntity != null){
									Receiver receiver = new Receiver();
									receiver.setAddress(userEntity.getAddress());
									receiver.setId(records.get(i).getUsercardid());
									receiver.setName(userEntity.getUsername());
									receiver.setLockerNo(terminalEntity.getDisplayname());
									receiver.setBoxNo(records.get(i).getBoxid().toString());
									//String dt = storeInTime.toString();
									//SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
									//SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									receiver.setStoreInTime(entry.getValue().get(Constant.BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME)); //开箱时间
									receiver.setTakeoutTime("");
									receiver.setMoney("0");
									receiver.setServicePhone("0571-86468639");
									try {
										MessageNoticeRecordEntityExample example = new MessageNoticeRecordEntityExample();
										example.createCriteria().andTerminalidEqualTo(records.get(i).getTerminalid()).andBoxidEqualTo(records.get(i).getBoxid())
										.andCardidEqualTo(userEntity.getUsercardid()).andStoredtimeEqualTo(records.get(i).getStoreintime())
										.andMessagetypeEqualTo("1").andSendcontentEqualTo("逾期存包开箱通知");
										List<MessageNoticeRecordEntity> messageNoticeRecordList = messageNoticeRecordEntityDao.selectByExample(example);
										if(messageNoticeRecordList.size()<1)
										{
											notify.storeinExpiryNotify(receiver);
											//写信息通知记录表
											MessageNoticeRecordEntity messageNoticeRecord = new MessageNoticeRecordEntity();
											messageNoticeRecord.setTerminalid(records.get(i).getTerminalid());
											messageNoticeRecord.setCardid(userEntity.getUsercardid());
											messageNoticeRecord.setBoxid(records.get(i).getBoxid());
											messageNoticeRecord.setStoredtime(records.get(i).getStoreintime());
											messageNoticeRecord.setStoreddate(null);
											messageNoticeRecord.setMessagetype("1");
											messageNoticeRecord.setCustomer(userEntity.getAddress());
											messageNoticeRecord.setCustomername(userEntity.getUsername());
											messageNoticeRecord.setSendcontent("逾期存包开箱通知");
											messageNoticeRecord.setSendstatus("0");
											messageNoticeRecord.setLastmodifytime(DateUtils.nowDate());
											messageNoticeRecordEntityDao.insertSelective(messageNoticeRecord);
										}

									} catch (MessageSendException | InvalidArgumentException e) {
										// TODO Auto-generated catch block
										Logger.error("邮件错误："+e.getMessage());
									}
								}
							}		
						}			
					}  
				}
			}
		}
	}*/
}
