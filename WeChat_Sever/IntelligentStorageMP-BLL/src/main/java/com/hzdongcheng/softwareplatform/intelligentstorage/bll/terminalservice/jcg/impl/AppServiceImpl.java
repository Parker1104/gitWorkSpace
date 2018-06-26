package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant.*;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerUnBindOpenID;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerUpdate;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQrcode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQrcodeQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerUpdate;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQrcode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQrcodeQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.Utils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalWeixinEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalWeixinEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalWeixinEntityDao;

/**
 * @author ysk
 * @Description: 投币机 
 * @ClassName:  CoinJCGServiceImpl
 * @date 2017年8月16日 下午13:09:00
 */
@Transactional
@Service("iAPPService")
public class AppServiceImpl implements IAPPService{
	 //日志
	// private final static Logger logger = Logger.getLogger(AppServiceImpl.class);
	 public static Log4jUtils logger = Log4jUtils.createInstanse(AppServiceImpl.class);
  
	 @Autowired
	 protected UserExDao userExDao;
	 @Autowired
	 private TerminalExDao terminalExDao;

	 @Autowired
	 private IOperationLogInpquire iOperation;
	 
	 @Autowired
	 private TerminalWeixinEntityDao terminalWeixinEntityDao;
	 
	 
	 
	 /**
     * 用户信息更新
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	@Override
	public OutParamAPCustomerUpdate doBusiness(InParamAPCustomerUpdate in) throws EduExceptions {
		OutParamAPCustomerUpdate out=new OutParamAPCustomerUpdate();
	     try{
		 	if (StringUtils.isEmpty(in.CustomerID))
				throw new EduExceptions(" in.CustomerID is 用户CustomerID不存在    ! ");
			
			in.CustomerName =  normalizeName(in.CustomerName);
			if(StringUtils.isNotEmpty(in.NickName)){
			    try {
		            in.NickName     = java.net.URLEncoder.encode(in.NickName, "utf-8");
		        } catch (UnsupportedEncodingException e) {
		            // TODO Auto-generated catch block
		            in.NickName     = "";
		        }
			}
			//System.out.println("URLEncoder.encode(in.NickName):"+in.NickName);
			//System.out.println("URLDecoder.decode(in.NickName):"+java.net.URLDecoder.decode(in.NickName, "UTF-8"));	//中文解码
		 	TerminalEntity terminalEntitys=	new TerminalEntity();
		 	if (StringUtils.isNotEmpty(in.TerminalNo))	{
		 		terminalEntitys= terminalExDao.selectByPrimaryKey(in.TerminalNo);
			 	if (null ==terminalEntitys)
					throw new EduExceptions(" terminalEntitys   in.TerminalNo="+in.TerminalNo+" not find  不存在  !");
	        }
			
		 	String   areacodes=	terminalEntitys.getAreacode();    //04  总部
		 	String	 displayname=terminalEntitys.getDisplayname();//1001
		 	String	 address=terminalEntitys.getAddress();        //安装地址
			
			UserEntityExample example = new UserEntityExample();
			example.createCriteria().andCustomeridEqualTo(in.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
			//example.createCriteria().andUsercardidEqualTo("0014418702");
			List<UserEntity> userEntityList = userExDao.selectByExample(example);
			UserEntity  userEntity=null;
			if(CollectionUtils.isEmpty(userEntityList)){
	 		    userEntity=new UserEntity();
	 		    userEntity.setUsercardid(in.CustomerID);//原来是32位 现在修改为64位
				userEntity.setCustomerid(in.CustomerID);
				userEntity.setUsername(in.CustomerName);
				userEntity.setState(SysDict.CUSTOMER_STATUS_UNACTIVE);  // app状态  0 正常 1 注销 2 注册未激活    ( 系统状态  '0:正常 1:停用')
				userEntity.setUsertype("0");      // '会员类型：0-普通用户'   (系统状态  '0普通用户  1贵宾用户  9黑名单用户    '),
				userEntity.setTelephone(in.CustomerMobile);//'移动电话'
				//userEntity.setIdtype(0);           //0身份证   (系统状态   '0身份证；1驾驶证；2学生证；等等'),
				userEntity.setIdcode(in.IDCard);
				userEntity.setUnionid(in.UnionID);
				userEntity.setTerminalno(in.TerminalNo);
				userEntity.setSex(Integer.parseInt(StringUtils.isEmpty(in.Sex)?"0":in.Sex));////性别:1-男，2-女，0-未知  (系统状态 '0女1男')
				
				userEntity.setCity(in.City);
				userEntity.setProvince(in.Province);
				userEntity.setCountry(in.Country);
				userEntity.setLanguage(in.Language);
				userEntity.setHeadimgurl(in.HeadImgUrl);
				userEntity.setGroupid("0");
				userEntity.setBalance(0);
				userEntity.setNickname(in.NickName); //TODO 后续添加微信名称 因为4个字节问题？？？？
				userEntity.setMemo(in.Remark);
				userEntity.setCreatetime(new Date());
				userEntity.setLastmodifytime(new Date());
				
				userEntity.setSource(Byte.parseByte("1"));//0:默认 1:微信app
				userExDao.insert(userEntity);
 
			}else{
				userEntity=userEntityList.get(0);
				System.out.println("  "+userEntity.getUsercardid()+"  "+userEntity.getUsername());
				userEntity.setUsername(in.CustomerName);
				userEntity.setTelephone(in.CustomerMobile);
				userEntity.setUnionid(in.UnionID);
				userEntity.setSex(Integer.parseInt(StringUtils.isEmpty(in.Sex)?"0":in.Sex));////性别:1-男，2-女，0-未知  (系统状态 '0女1男')
				userEntity.setCity(in.City);
				userEntity.setProvince(in.Province);
				userEntity.setCountry(in.Country);
				userEntity.setLanguage(in.Language);
				userEntity.setHeadimgurl(in.HeadImgUrl);
				userEntity.setState(SysDict.CUSTOMER_STATUS_NROMAL);
				userEntity.setNickname(in.NickName);  // TODO 后续添加微信名称 因为4个字节问题？？？？
				userEntity.setMemo(in.Remark);
				userEntity.setTerminalno(in.TerminalNo);
				userEntity.setLastmodifytime(new Date());  // TODO 应该调用系统的时间   3.调用UtilDAO.getCurrentDateTime()获得系统日期时间。
				
				UserEntityExample exampleUpdate = new UserEntityExample();
				exampleUpdate.createCriteria().andCustomeridEqualTo(in.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
				userExDao.updateByExampleSelective(userEntity, exampleUpdate);
		   }
    	   out.CustomerID     = userEntity.getCustomerid();
    	   // out.CustomerName   = java.net.URLDecoder.decode(userEntity.getUsername(), "utf-8");
    	   out.CustomerName   = userEntity.getUsername();
           out.CustomerStatus = userEntity.getState().toString();
           out.CustomerMobile = userEntity.getTelephone();
           out.BindMobile     = userEntity.getBindmobile();
           out.UnionID        = userEntity.getUnionid();
           out.Balance        = userEntity.getBalance();
 
           out.Location=Utils.locations(areacodes, displayname, address);
           out.TerminalName   = displayname;
           out.TerminalNo     = userEntity.getTerminalno();
           out.LastModifyTime = Constant.datetimeFromat.format(userEntity.getLastmodifytime());
           out.CreateTime     = Constant.datetimeFromat.format(userEntity.getCreatetime());
 
	       return out;
	    }catch(Exception ex){
	    	  logger.error(" AppServiceImpl OutParamAPCustomerUpdate doBusiness(InParamAPCustomerUpdate in) fail the reason is:", ex);
	    	  throw new EduExceptions(ex.getMessage());
	    } 
	}

	@Override
    public int doBusiness(InParamAPCustomerUnBindOpenID in) throws EduExceptions  {
        int result = 0;
        try{
			//1.验证输入参数是否有效，如果无效返回-1。
			if (StringUtils.isEmpty(in.CustomerID))
				throw new EduExceptions(" in.CustomerID  is null  用户CustomerID不存在!   ");
			
			UserEntityExample example = new UserEntityExample();
			example.createCriteria().andCustomeridEqualTo(in.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
			//example.createCriteria().andUsercardidEqualTo("0014418702");
			List<UserEntity> userEntityList = userExDao.selectByExample(example);
			if(!CollectionUtils.isEmpty(userEntityList)){
				UserEntity	userEntity=userEntityList.get(0);
				userEntity.setUnionid("");
				userEntity.setActive("0");
				userEntity.setState(SysDict.CUSTOMER_STATUS_CANCEL);
		        userExDao.updateByExampleSelective(userEntity, example);
			}
			//调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
 
        }catch(Exception ex){
	    	 logger.error(" AppServiceImplint int doBusiness(InParamAPCustomerUnBindOpenID in)  fail the reason is:", ex);
	    	 throw new EduExceptions(ex.getMessage());
	    }
        
        return result;
    }
    @Override
	public OutParamAPCustomerQuery doBusiness(InParamAPCustomerQuery in) throws EduExceptions {
         OutParamAPCustomerQuery out = new OutParamAPCustomerQuery();
         try{
		 		//1.验证输入参数是否有效，如果无效返回-1。
		         if(StringUtils.isEmpty(in.CustomerID)){
		             throw new EduExceptions(" in.CustomerID is null   用户CustomerID不存在!  ");
		         }
		        //TODO  ???? 扫码超时问题  ??????
		 		//int timeout = NumberUtils.parseInt(ControlParam.getInstance().scanTimeout);
		 		//int minutes = 0;
		 		
				UserEntityExample example = new UserEntityExample();
				example.createCriteria().andCustomeridEqualTo(in.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
				List<UserEntity> userEntityList = userExDao.selectByExample(example);
				UserEntity  userEntity=null;
				if(!CollectionUtils.isEmpty(userEntityList)){
					userEntity=userEntityList.get(0);
					TerminalEntity terminalNameGet=	new TerminalEntity();
					terminalNameGet= terminalExDao.selectByPrimaryKey(userEntity.getTerminalno());
					 out.CustomerName = userEntity.getUsername();
					 out.CustomerID = userEntity.getCustomerid();
		             out.CustomerMobile = userEntity.getTelephone();
		             out.CustomerStatus = userEntity.getState().toString();
		             out.CustomerStatusName = "";
		             out.BindMobile =userEntity.getBindmobile();
		             out.BindCardID = userEntity.getBindcardid();
		             out.UnionID = userEntity.getUnionid();
		             out.TerminalNo = userEntity.getTerminalno();
		             out.TerminalName = terminalNameGet.getDisplayname();
		             out.MBDeviceNo= "";
		             out.Location = "";
		             out.Sex = userEntity.getSex().toString();
		             out.City = userEntity.getCity();
		             out.Province = userEntity.getProvince();
		             out.Country = userEntity.getCountry();
		             out.Language = userEntity.getLanguage();
		             out.HeadImgUrl = userEntity.getHeadimgurl();
		             out.Balance =userEntity.getBalance();
		             out.Remark = userEntity.getMemo();
		             out.LastModifyTime = Constant.datetimeFromat.format(userEntity.getLastmodifytime());
		             out.CreateTime = Constant.datetimeFromat.format(userEntity.getCreatetime());
				}
		        //设备地址过期，重新扫码
		       /** 
			      if(timeout>=1 && minutes > timeout){
		             out.TerminalNo = "";
		             out.MBDeviceNo = "";
		             out.Location = "";
			       }
		        */
		 	    out.BindMobile = "999999999";//2016.11.11 暂不需要注册手机号
         }catch(Exception ex){
	    	 logger.error(" AppServiceImplint OutParamAPCustomerQuery doBusiness(InParamAPCustomerQuery in) fail the reason is:", ex);
	    	 throw new EduExceptions(ex.getMessage());
	    }
        return out;
 
	}
 
    @Override
    public OutTerminalQuery doBusiness(InTerminalQuery in) throws EduExceptions {
    	OutTerminalQuery out = new OutTerminalQuery();
    	TerminalEntity terminalEntitys=	new TerminalEntity();
    	if (StringUtils.isNotEmpty(in.TerminalNo))	{
	 		terminalEntitys= terminalExDao.selectByPrimaryKey(in.TerminalNo);
		 	if (null ==terminalEntitys){
				throw new EduExceptions(" terminalEntitys   in.TerminalNo="+in.TerminalNo+" not find  不存在  !");
		 	}
		 	else{
		 		out.setTerminalNo(in.TerminalNo);
		 	}
        }
    	System.out.print(in.TerminalNo);
    	return out;
    }
    

    /**
     * 设备二维码绑定
     * @param ins
     * @return
     * @throws   
     */
    @Override
    public OutTerminalQrcode doBusiness(InTerminalQrcode in) throws EduExceptions {
    	String terminalNo = in.TerminalNo;
    	String terminaUrl = in.TerminaUrl;
    	OutTerminalQrcode out = new OutTerminalQrcode();
    	TerminalEntityExample terminalEntityExample  = new TerminalEntityExample();
    	terminalEntityExample.createCriteria().andTerminalidEqualTo(terminalNo);
    	List<TerminalEntity> terminalEntityList = terminalExDao.selectByExample(terminalEntityExample);		//查询设备表是否存在该柜号
    	if(!CollectionUtils.isEmpty(terminalEntityList)){
    		TerminalWeixinEntityExample terminalWeixinEntityExample  = new TerminalWeixinEntityExample();	//查询是否是已绑定柜号
    		terminalWeixinEntityExample.createCriteria().andTerminalidEqualTo(terminalNo);
    		List<TerminalWeixinEntity> terminalWeixinEntityList = terminalWeixinEntityDao.selectByExample(terminalWeixinEntityExample);
    		if(!CollectionUtils.isEmpty(terminalWeixinEntityList)){
    			TerminalWeixinEntity terminalWeixinEntitys=	new TerminalWeixinEntity();
    			terminalWeixinEntitys = terminalWeixinEntityList.get(0);
    			out.setTerminalNo(terminalWeixinEntitys.getTerminalid());
    			out.setTerminaUrl(terminalWeixinEntitys.getQrcodeurls());
    		}else{
	    		TerminalWeixinEntity terminalWeixinEntitys=	new TerminalWeixinEntity();
	    		terminalWeixinEntitys.setTerminalid(terminalNo);
	    		terminalWeixinEntitys.setQrcodeurls(terminaUrl);
	    		terminalWeixinEntityDao.insert(terminalWeixinEntitys);
	    		out.setTerminalNo(terminalNo);
	        	out.setTerminaUrl(terminaUrl);
    		}
    		/*terminalEntitys.setAddress(address);*/
    	}
    	
    	
    	return out;
    }
    
    /**
     * 根据Url查询设备ID
     * @param ins
     * @return
     * @throws   
     */
    @Override
    public OutTerminalQrcodeQuery doBusiness(InTerminalQrcodeQuery in) throws EduExceptions {
    	String terminaUrl = in.TerminaUrl;
    		OutTerminalQrcodeQuery out = new OutTerminalQrcodeQuery();
    		TerminalWeixinEntityExample terminalWeixinEntityExample  = new TerminalWeixinEntityExample();
    		terminalWeixinEntityExample.createCriteria().andQrcodeurlsEqualTo(terminaUrl);
    		List<TerminalWeixinEntity> terminalWeixinEntityList = terminalWeixinEntityDao.selectByExample(terminalWeixinEntityExample);
    		if(!CollectionUtils.isEmpty(terminalWeixinEntityList)){
    			TerminalWeixinEntity terminalWeixinEntity = new TerminalWeixinEntity();
    			terminalWeixinEntity = terminalWeixinEntityList.get(0);
    			String terminaNo = terminalWeixinEntity.getTerminalid();
    	    	out.setTerminaUrl(terminaUrl);
    	    	out.setTerminalNo(terminaNo);
    		}else{
    			System.err.println("此二维码Url无效");
    		}
    	return out;
    }
    
    
	public static String normalizeName(String name) {
        return name.replaceAll("\\^|\"|'|:|;|<|>|@|\\*|%|!|#|\\$|,|\\?|&", "");
    }

}




















