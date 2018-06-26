package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant.*;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerPrepayAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerPrepayAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPWinPayService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.Utils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentesEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentesDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxSizeEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackageEntityDao;
 
/**
 * @author ysk
 * @Description: 投币机 
 * @ClassName:  AppPayServiceImpl
 * @date 2017年8月16日 下午13:09:00
 */
@Transactional
@Service("iAPPWinPayService")
public class AppWinPayServiceImpl implements IAPPWinPayService{
	//private final static Logger logger = Logger.getLogger(AppWinPayServiceImpl.class);
	
	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(AppWinPayServiceImpl.class);
	
	@Autowired
	protected UserExDao userExDao;
	@Autowired
	private TerminalExDao terminalExDao;
	@Autowired
	private BoxSizeEntityDao boxSizeEntityDao;
	@Autowired
	private IOperationLogInpquire iOperation;
	@Autowired
	private PtreadypackageEntityDao ptreadypackageEntityDao;
	@Autowired
	protected BusinessModelEntityExDao business;
	@Autowired
	protected PaymentesDao paymentesDao;
	@Autowired
	protected BoxExDao boxExDao;
	
	//正真发起支付请求
	@Override
	public OutParamAPCustomerPrepayAmtQry doBusiness(InParamAPCustomerPrepayAmtQry in) throws EduExceptions {
	        OutParamAPCustomerPrepayAmtQry out = new OutParamAPCustomerPrepayAmtQry();
	        try{
				//1.验证输入参数是否有效，如果无效返回-1。
		         if(StringUtils.isEmpty(in.PackageID)
		        	||StringUtils.isEmpty(in.TerminalNo)
		        	||StringUtils.isEmpty(in.CustomerID)
		        	||StringUtils.isEmpty(in.TradeWaterNo)
		        	|| in.Num < 0){ 
		        	throw new EduExceptions(" in.Num < 0 | in.PackageID | in.TerminalNo | in.CustomerID |in.TradeWaterNo is null  ");
		        } 
				UserEntityExample example = new UserEntityExample();
				example.createCriteria().andCustomeridEqualTo(in.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
				//example.createCriteria().andUsercardidEqualTo("0014418702");
				List<UserEntity> userEntityList = userExDao.selectByExample(example);
				if(CollectionUtils.isEmpty(userEntityList)){
					 return out;
				}
				UserEntity  userEntity=userEntityList.get(0);
		        if(StringUtils.isEmpty(userEntity.getTerminalno())){
			        return out;
			    }
		        if(StringUtils.isEmpty(in.TerminalNo)){
		            in.TerminalNo = userEntity.getTerminalno();
		        }
			 	TerminalEntity  terminalEntitys= terminalExDao.selectByPrimaryKey(in.TerminalNo);
				if (null ==terminalEntitys){
					throw new EduExceptions(" terminalEntitys   TerminalNo="+userEntity.getTerminalno()+" not find   不能找到  !");
				}
				//'0: 正常 1:锁定 2:故障',
				if(!SysDict.TERMINAL_STATUS_NORMAL.equals(terminalEntitys.getRunstatus().toString())){
		            throw new EduExceptions("  terminalEntitys.getRunstatus() ="+terminalEntitys.getRunstatus()+"  柜体故障 !");
		        }
				Integer businessCode = terminalEntitys.getBusinesscode();
				//根据业务编码获取业务模型
				Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
				if (businessModelMap == null){
					throw new EduExceptions("  businessModelMap is null   收费模式没有定义 !  ");
				}
			 	List<PaymentesEx>   paymentesExList=null;
				String sCheckCharge = businessModelMap.get(Constant.BT_CFG_NAME_CHARGESWITCH);
				if (StringUtils.isNotEmpty(sCheckCharge) ){
					String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
					//收费标准chargeCode      0：vip更衣柜收费标准   1 更衣柜收费标准 2 贵重物品收费标准   
					//收费模式 usedState       0：按小时收费 1：按时段收费 2：按天收费
					//boxTypeCode           1 小   2中  3大      折扣=实际售价/原售价×100%
					BoxEx boxEntityEx = boxExDao.selectByPrimaryKeys(terminalEntitys.getTerminalid(), Integer.parseInt(in.BoxNo));
					//计算预付费用
					paymentesExList=paymentesDao.getMoneyByBoxTypePrepayDetails(Integer.parseInt(chargeMode), boxEntityEx.getBoxtypecode());
				} 
				if(CollectionUtils.isEmpty(paymentesExList)){
					throw new EduExceptions("  paymentesExList  is null  ");
				}
				PaymentesEx PaymentesExs=	paymentesExList.get(0);

			 	String	 displayname=terminalEntitys.getDisplayname();//1001
			 	String   areacodes=	terminalEntitys.getAreacode();    //04  总部
			 	String	 address=terminalEntitys.getAddress();        //安装地址
			   //String   Terminalid=terminalEntitys.getTerminalid(); //安装地址
 
			 	//step 1   查找订单状态  
			 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
			 	ps_example.createCriteria().andTerminalnoEqualTo(in.TerminalNo)
			 	                           .andPackageidEqualTo(in.PackageID).andPaystatusEqualTo(Byte.parseByte("0"));
			    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
			 	if( CollectionUtils.isEmpty(ptreadypackageEntitysList)){
			 		throw new EduExceptions(" PtreadypackageEntity  have payed  or 不存在或者已支付过了   !    ");
			 	}
			 	
			 	PtreadypackageEntity PtreadypackageEntitys=ptreadypackageEntitysList.get(0);
			 	/**
			 	   if(isExpired(PtreadypackageEntitys.getRemark()){//交易流水号已过期    对微信来说就是商户订单号
			        	System.out.println("交易流水号已过期:"+in.TradeWaterNo);
			    		throw new EduExceptions("交易流水号已过期:"+in.TradeWaterNo +"   !");
		    	   }
		    	*/
 
			 	Date sysDateTime = new Date(System.currentTimeMillis());
			 	Date expiredTime = addTimeStampBySecond(sysDateTime, 120);//2分钟
			 	
		        //int amountCent = PaymentesExs.getMoney().intValue();
		        //double amountYuan = (double)amountCent/100;//元
		        //PtreadypackageEntitys.setHireamt(new BigDecimal(amountYuan));           //总金额   格口租用金额  
			 	
			 	int amountYuan = PaymentesExs.getMoney().intValue();//元
		        PtreadypackageEntitys.setPayamt(new BigDecimal(amountYuan));          //预支付金额    
		        PtreadypackageEntitys.setHirewhopay(SysDict.CUSTOMER_TYPE_NROMAL);    //格口租用支付方：用户 
		        PtreadypackageEntitys.setLastmodifytime(new Date());
		        PtreadypackageEntitys.setExpiredtime(expiredTime);
		        ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
		        
		        //生成交易流水
		        out.CustomerID   = in.CustomerID;
		        out.PackageID    = in.PackageID;
		        out.TradeWaterNo = PtreadypackageEntitys.getTradewaterno();
		        out.Amount       = amountYuan*100;   //这里要给微信支付   所以要转化为分  
		        out.Detail       = "";
		        out.BoxNo        = PtreadypackageEntitys.getBoxno();
		        out.TerminalNo   = PtreadypackageEntitys.getTerminalno();
		        out.TerminalName = displayname;
		        out.Location     = Utils.locations(areacodes, displayname, address);
		        out.Remark       = "";
	        }catch(Exception ex ){
	        	logger.error("   AppWinPayServiceImpl   OutParamAPCustomerPrepayAmtQry doBusiness(InParamAPCustomerPrepayAmtQry p1)    "+ex);
	        	throw new EduExceptions(ex.getMessage());
	        }
	        return out;
	}
 
    public static Date addTimeStampBySecond(Date date, int second)  {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)second * 1000L);
        return new Date(cal.getTime().getTime());
    }
    public static long getMillis(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
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
	private final static long EXPIRED_TIME_IN_MILLISENCOD = 600*1000;//10分钟
	public    static boolean isExpired(Date tradeTime){
		if(tradeTime != null){
			java.util.Date nowData = new java.util.Date();
			if(nowData.getTime()-tradeTime.getTime() > EXPIRED_TIME_IN_MILLISENCOD){
				return true;
			}
		}else {
			return true;
		}
		return false;
	}
}




















