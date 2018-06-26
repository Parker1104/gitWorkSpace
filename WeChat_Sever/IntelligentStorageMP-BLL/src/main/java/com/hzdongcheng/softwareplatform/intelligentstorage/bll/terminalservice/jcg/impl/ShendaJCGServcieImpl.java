package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.thirdparty.I3thValidation;
import com.hzdongcheng.thirdparty.factory.ThirdPartyDockingFactory;

@Service("ShendaService")
public class ShendaJCGServcieImpl extends JCGServiceImpl {
	
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
				I3thValidation shenda = ThirdPartyDockingFactory.getShenDaDockingImpl();
				if (!shenda.verifyCardID(openBoxCode.toUpperCase())){
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
}
