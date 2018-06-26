package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirmByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamOpenBoxByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirmByManager;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IGradeService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;

/**
 * @author WenHeJu
 * @Description: TODO(北京协创   根据班级分配箱门开箱) 
 * @ClassName:  BJXCJCGServiceImpl
 * @date 2017年8月2日 下午7:00:58
 */

@Service("BJXCJCGService")
public class BJXCJCGServiceImpl extends JCGServiceImpl{
	
	@Autowired
	private IGradeService iGradeService;	
	//private static String url = "tcp://127.0.0.1:55666";
	
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
	
	/**
	 * (管理)存物请求
	 */
/*	@Override
	public OutParamStoreInRequest storeRequset(InParamStoreInRequest inParam) {
		// TODO Auto-generated method stub
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
				logger.info("设备号："+terminalID+"卡号："+inParam.getOpenBoxCode()+"转换后卡号："+openBoxCode+"业务类型编码："+businessCode+"业务模型："+businessModelMap+"获取的转换规则："+sCardTransRuleCode+"获取运营时段的ID："+sRunTimeCode);
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
            
			//Step.4 查询用户是哪个班级的学生
			GradeEntity gradeEntity =  null;
			BoxEntity boxEntity = null;
			UserEntity userEntity = userService.select(openBoxCode);
			gradeEntity = iGradeService.selectGrade(terminalID, userEntity.getSubdepartment());
			boxEntity = boxExDao.selectByPrimaryKey(terminalID, gradeEntity.getBoxid());
			if(userEntity != null){
			    //查询班级绑箱
				if(gradeEntity != null){
					 //获取箱信息		
					if(gradeEntity.getTerminalid().equals(terminalID)){
						outParam.setErrorCode(JCGErrorCode.ERR_PUT_EXIST_OTHER_BOX);
					}else {
						outParam.setErrorCode(JCGErrorCode.ERR_PUT_ATH_FAIL);
					}
					//设置显示柜号
					outParam.setDisplayName(gradeEntity.getDisplayname());

					String areaShortname = "";
					{
						TerminalEntity tmpTerminalEntity = terminalExDao.selectByPrimaryKey(gradeEntity.getTerminalid());
						String areaCode = tmpTerminalEntity.getAreacode();
						areaShortname = areaEntityDao.selectByPrimaryKey(areaCode).getAreashortname();
						areaShortname = areaShortname.substring(areaShortname.length()-1);
					}
					
					//设置区域简称
					outParam.setAreaCode(areaShortname);
					//设置箱号
					outParam.setBoxID(gradeEntity.getBoxid().byteValue());
					//设置显示箱名称
					outParam.setDisplayBoxName(boxEntity.getDispalyname().substring(boxEntity.getDispalyname().length()-4, boxEntity.getDispalyname().length()));		
					return outParam;
				}		
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("异常："+e.getMessage());
		}
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		return outParam;
	}*/

	/**
	 * (管理)取请求
	 */
	@Override
	public OutParamOpenBoxByManager openBoxByManager(InParamTakeOutConfirmByManager inParam) {
		
		OutParamOpenBoxByManager outParam = new OutParamOpenBoxByManager();
		//请求参数
		String openBoxCode = inParam.getOpenCode();
		String terminalID  = inParam.getTerminalID();
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
			logger.info("取请求   设备号："+terminalID+"卡号："+inParam.getOpenCode()+"业务信息"+terminalEntity+"业务编码："+businessCode+"业务模型："+businessModelMap+"转换后卡号："+openBoxCode);
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
		
		//Step.5 查询在箱箱号
		UserEntity userEntity = userService.select(openBoxCode);
		GradeEntity gradeEntity = iGradeService.selectGrade(terminalID, userEntity.getSubdepartment());
	
		if(gradeEntity != null){ 
			//判断班级箱子是否禁用
			if(gradeEntity.getState() == 0){
				//判断时间是否到期
				if(gradeEntity.getEnddate().after(DateUtils.nowDate())){
		    		//查询管理卡信息
		    		int type = 0;		
		    		//判断用户是否禁用 1是
		    		if(userEntity.getState() == 1){
		    			outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
		    			return outParam;
		    		}

		    		type = Integer.parseInt(userEntity.getCompany()) ;
		    		String sBoxList = "";

		    		switch (type) {
		    		
		    		case Constant.CARD_TYPE_MANAGER_TIMEOUT: //超时开箱
		    			{
			
		    	            int[] boxarray = {gradeEntity.getBoxid()};
		    	            for(int i = 0; i < boxarray.length ; i++){
		    	            	sBoxList += boxarray[i];
		    	            }
		    				//添加空闲卡箱门日志
		    				WriteDiary(terminalID, openBoxCode, "用户开箱(班级)", sBoxList);

		    				outParam.setBoxid(boxarray);
		    				outParam.setType(6);
		    				outParam.setErrorCode(JCGErrorCode.ERR_OK);		
		    			}
		    			break;	
		    		default:
		    			break;
		    		}  
				}else {
					outParam.setErrorCode(JCGErrorCode.ERR_GET_NOT_VALIDTIME);
					return outParam;
				}
			}else {
				outParam.setErrorCode(JCGErrorCode.ERR_GET_ATH_FAIL);
				//outParam.setErrorCode(JCGErrorCode.ERR_OK);		
				return outParam;
			}                 
		}else {
			   outParam.setErrorCode(JCGErrorCode.ERR_GET_EXIST_OTHER_GUI);
    			return outParam;
			
		}	
		
		return outParam;	
	}

	
	/**
	 * (管理)中途取
	 */
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


		StoreInRecordEntity storeInRecordEntity = null;
		MidwayTakeRecordEntity midwayTakeRecordEntity = null;
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
				storeInRecordEntity.setUsercardid(openBoxCode);
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)0);
				storeInRecordEntity.setMoney((float)money);
				storeInRecordEntity.setRealmoney((float)0);
				storeInRecordExDao.insertSelective(storeInRecordEntity);	
			}
			//Step.3 写取物记录
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
		} catch (Exception e){
			outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
			logger.error(e.getMessage());
		}
		return outParam;
	}	

}
