package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCCheckInUser;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCClearBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCOpenBox;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.PushClientFactory;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IJCGAppService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardTransRuleExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;

/**
 * @author WenHeJu
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @ClassName:  JCGAppServiceImpl
 * @date 2017年7月11日 上午9:18:52
 * 湖南文理学院     APP存取开箱   个人存取记录   剩余箱数量
 */
@Service("IJCGAppService")
public class JCGAppServiceImpl implements IJCGAppService {
	@Autowired
	protected BoxExDao boxExDao;
	@Autowired
	protected TerminalExDao terminalExDao;
	@Autowired
	protected StoreInRecordExDao storeInRecordExDao;
	@Autowired
	private TakeOutRecordEntityDao takeOutRecordEntityDao;
	@Autowired
	protected IUserService userService;
	@Autowired
	protected CardTransRuleExDao cardTransRuleExDao;
	
	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(JCGAppServiceImpl.class);

	private static String url = "tcp://127.0.0.1:55666";
	
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
	
	//反转卡号
	public String reverseTransCardIDByRuleCode(Integer cardTransRuleCode, String openboxcode)
	{
		//查看卡号的转换	
		CardTransRuleEntity cardTransRuleEntity = cardTransRuleExDao.selectByPrimaryKey(cardTransRuleCode);
		if(cardTransRuleEntity != null) {		
			//截取字符串
			openboxcode = openboxcode.substring(cardTransRuleEntity.getStartsubstr());
			
			if(cardTransRuleEntity.getDecimalism() == 1){
				//十进制转十六进制
				try {	
					openboxcode =Long.toHexString(Long.parseLong(openboxcode)).toUpperCase();
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage());
					System.out.println("转换失败");
				}

			}

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



			/*while (openboxcode.length() < cardTransRuleEntity.getCardlen()) {
				openboxcode = "0" + openboxcode;
			}*/

			return openboxcode;
		}

		return openboxcode;
	}
	/**
	 *APP存物
	 */
	@Override
	public String storeRequset(String userCardID, String userName, String className, String displayName) {
		// TODO Auto-generated method stub
		StoreInRecordEntity storeInRecordEntity = null;
		//根据卡号查询存物记录
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		example.createCriteria().andUsercardidEqualTo(userCardID).andStateEqualTo((byte)0);
		List<StoreInRecordEntity> storeInRecord = storeInRecordExDao.selectByExample(example);
		if(storeInRecord.size()>0){
			//返回存物  箱号  柜号  
			String message = storeInRecord.get(0).getUsername()+"同学你好：你在本柜"+storeInRecord.get(0).getBoxid()+"箱，已存物";
            return message;
		}else {	
			//根据柜名字查询空箱
			List<BoxEntity> boxEntity = terminalExDao.selectEmptyBoxByDisplayName(displayName);
			if(boxEntity.size()>0){
				//开箱
				try {
					IPushClient pushClient = PushClientFactory.getInstance();
					IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
					InParamRCCheckInUser InParam = new InParamRCCheckInUser();
					InParam.setTerminalID(boxEntity.get(0).getTerminalid());
					//String userardid = reverseTransCardIDByRuleCode(1, userCardID);
					InParam.setCardID(userCardID);  //卡号要变成可变十进制
					InParam.setBoxID(boxEntity.get(0).getBoxid());
					InParam.setTime(DateUtils.nowDate());
					try {
						jcgCtrl.checkInUser(url, InParam);
					} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
						logger.info("注册卡失败. Terminal ID: " + boxEntity.get(0).getTerminalid() + ", Box ID: " + boxEntity.get(0).getBoxid() + 
								", Card ID: " + userCardID + ". error code:" + e.getErrorCode() + "," + e.getMessage());
					}
					InParamRCOpenBox inParams = new InParamRCOpenBox();
					inParams.setTerminalID(boxEntity.get(0).getTerminalid());
					int[] boxarray = {boxEntity.get(0).getBoxid()};
					inParams.setBoxArray(boxarray);
					inParams.setFlag(0);
					try {
						jcgCtrl.openBox(url, inParams);
						//写存物记录
						storeInRecordEntity = new StoreInRecordEntity();
						UserEntity userEntity = userService.select(userCardID);
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
							storeInRecordEntity.setTerminalid(boxEntity.get(0).getTerminalid());
							storeInRecordEntity.setBoxid(boxEntity.get(0).getBoxid());
							storeInRecordEntity.setUsercardid(userCardID);
							storeInRecordEntity.setStoreintime(DateUtils.nowDate());
							storeInRecordEntity.setState((byte)0);
							storeInRecordEntity.setMoney((float)0);
							storeInRecordEntity.setRealmoney((float)0);
							storeInRecordExDao.insertSelective(storeInRecordEntity);	
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
						logger.error(e.getMessage());
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}else {
				//提示本柜全满
				String message = "本柜全满";
	            return message;
			}
		}
		return null;	
	}
	/**
	 *APP取物
	 */
	@Override
	public String takeRequest(String userCardID, String userName, String className, String displayName) {
		// TODO Auto-generated method stub
		//根据卡号查询存物记录
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		example.createCriteria().andUsercardidEqualTo(userCardID).andStateEqualTo((byte)0);
		List<StoreInRecordEntity> storeInRecord = storeInRecordExDao.selectByExample(example);
		if(storeInRecord.size()>0){
			//开箱
			try {
				TakeOutRecordEntity takeOutEntity = new TakeOutRecordEntity();
				StoreInRecordEntity storeInRecordEntity = new StoreInRecordEntity();
				
				takeOutEntity.setUsercardid(storeInRecord.get(0).getUsercardid());
				takeOutEntity.setTerminalid(storeInRecord.get(0).getTerminalid());
				takeOutEntity.setBoxid(storeInRecord.get(0).getBoxid());
				takeOutEntity.setStoreintime(storeInRecord.get(0).getStoreintime());
				takeOutEntity.setTaketime(DateUtils.nowDate());
				takeOutEntity.setMoney(0F);
				takeOutEntity.setRealmoney(0F);
				takeOutEntity.setType(2);

				storeInRecordEntity.setUsercardid(storeInRecord.get(0).getUsercardid());
				storeInRecordEntity.setTerminalid(storeInRecord.get(0).getTerminalid());
				storeInRecordEntity.setBoxid(storeInRecord.get(0).getBoxid());
				storeInRecordEntity.setStoreintime(storeInRecord.get(0).getStoreintime());
				storeInRecordEntity.setState((byte)1);
				
				IPushClient pushClient = PushClientFactory.getInstance();
				IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
				InParamRCOpenBox inParams = new InParamRCOpenBox();
				inParams.setTerminalID(storeInRecord.get(0).getTerminalid());
				int[] boxarray = {storeInRecord.get(0).getBoxid()};
				inParams.setBoxArray(boxarray);
				inParams.setFlag(0);
				InParamRCClearBox inParamClearBox = new InParamRCClearBox();
				inParamClearBox.setTerminalID(storeInRecord.get(0).getTerminalid());
				inParamClearBox.setBoxID(storeInRecord.get(0).getBoxid());
				try {
					jcgCtrl.clearBox(url, inParamClearBox);
					try {			            	
						jcgCtrl.openBox(url, inParams);
						takeOutRecordEntityDao.insertSelective(takeOutEntity);
						storeInRecordExDao.updateByPrimaryKeySelective(storeInRecordEntity);
					} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage());
				}
				
			} catch (Exception e) {
				 logger.error(e.getMessage());
			}
		}else {	
			//提示没有该学生的存物记录
			String message = "无";
            return message;
		}
		return null;
	}	

	/**
	 * 根据卡号查询记录
	 */
	@Override
	public List<StoreInRecordEntity> selectStoreInRecord(String userCardID) {
		// TODO Auto-generated method stub
		List<StoreInRecordEntity> storeInRecordEntity = terminalExDao.selectInBoxByOpenBoxCode(userCardID);
		if(storeInRecordEntity.size()>0){
			return storeInRecordEntity;
		}
		return null;
	}

	/**
	 * 根据设备名称查询空箱信息
	 */
	@Override
	public List<BoxEntity> selectFreeOpenBoxTerminalID(String displayName) {
		// TODO Auto-generated method stub
		List<BoxEntity> boxEntity = terminalExDao.selectEmptyBoxByDisplayName(displayName);
		if(boxEntity.size()>0){
			return boxEntity; 
		}
		return null;
	}

	/**
	 * 根据区域查询空箱
	 */
	@Override
	public List<BoxEntity> selectEmptyBoxByAreaCode(String areaCode) {
		// TODO Auto-generated method stub
		List<BoxEntity> boxEntity = terminalExDao.selectEmptyBoxByAreaCode(areaCode+"%");
		if(boxEntity.size()>0){
			return boxEntity; 
		}
		return null;
	}

}
