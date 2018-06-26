package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalserviceweixin.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appPublicDto.PublicInWXCheckTermianlNo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalserviceweixin.IAppPublicService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample;

/**
 * @author kyx
 * @Description: public微信H5页面impl
 * @ClassName:   
 * @date 2018年03月14日 下午13:09:00
 */
@Transactional
@Service("iAppPublicService")
public class AppPublicServiceImpl implements IAppPublicService{

	@Autowired
	protected UserExDao userExDao;
	@Autowired
	private TerminalExDao terminalExDao;
	
	/*更新用户信息，检查设备ID*/
	@Override
	public String doBusiness(PublicInWXCheckTermianlNo in) throws EduExceptions {
		String result = "";
		String openid = in.OpenId;
		String terminalId = in.TerminalNo;
		/*插入user表*/
		UserEntityExample userQryexample = new UserEntityExample();
		userQryexample.createCriteria().andUsercardidEqualTo(openid).andSourceEqualTo((byte)1);
		List<UserEntity> userEntityList = userExDao.selectByExample(userQryexample);
		if(CollectionUtils.isEmpty(userEntityList)){
			UserEntity userEntity = new UserEntity();
			userEntity.setTerminalno(terminalId);
			userEntity.setSex(in.Sex);////性别:1-男，2-女，0-未知  (系统状态 '0女1男')
			userEntity.setCity(in.City);
			userEntity.setCountry(in.Country);
			userEntity.setProvince(in.Province);
			userEntity.setNickname(in.Nickname);
			userEntity.setLanguage(in.Language);
			userEntity.setHeadimgurl(in.HeadImgUrl);
			userEntity.setBalance(0);
			userEntity.setUsercardid(openid);
			userEntity.setUsername(openid);
			userEntity.setCustomerid(openid);
			userEntity.setState((byte)0);
			userEntity.setCreatetime(new Date());
			userEntity.setLastmodifytime(new Date());
			userEntity.setSource((byte)1);//0:默认 1:微信app
			userExDao.insert(userEntity);
		}else{
			UserEntity userEntity = new UserEntity();
			userEntity.setTerminalno(terminalId);
			userEntity.setSex(in.Sex);
			userEntity.setCity(in.City);
			userEntity.setCountry(in.Country);
			userEntity.setProvince(in.Province);
			userEntity.setNickname(in.Nickname);
			userEntity.setLanguage(in.Language);
			userEntity.setHeadimgurl(in.HeadImgUrl);
			userEntity.setUsercardid(openid);
			userEntity.setUsername(openid);
			userEntity.setCustomerid(openid);
			userEntity.setBalance(0);
			userEntity.setState((byte)0);
			userEntity.setLastmodifytime(new Date());
			userEntity.setSource(Byte.parseByte("1"));//0:默认 1:微信app
			userExDao.updateByExampleSelective(userEntity, userQryexample);
		}
		
		
		TerminalEntityExample checkTerminalExample = new TerminalEntityExample();
		checkTerminalExample.createCriteria().andTerminalidEqualTo(terminalId);
		List<TerminalEntity> terminalEntityList = terminalExDao.selectByExample(checkTerminalExample);
		if(CollectionUtils.isEmpty(terminalEntityList)){
			result = "1";
		}else{
			result = "0";
		}
		
		return result;
	}
	
}




















