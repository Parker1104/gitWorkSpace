package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerBoxHireAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGSchedule;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerBoxHireAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGSchedule;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPPayService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.PropertiesUtil;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.StoreInRequestsTemp;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.Utils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxAppExEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentesEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentesDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxSizeEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackageEntityDao;
 
/**
 * @author ysk
 * @Description: 投币机 
 * @ClassName:  AppPayServiceImpl
 * @date 2017年8月16日 下午13:09:00
 */
@Transactional
@Service("iAPPPayService")
public class AppPayServiceImpl implements IAPPPayService{
	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(AppPayServiceImpl.class);
	//private final static Logger logger = Logger.getLogger(AppPayServiceImpl.class);
	
	
	
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
	@Autowired
	protected StoreInRecordExDao storeInRecordExDao;
	@Autowired
	protected BoxEntityDao boxEntityDao;
	//去支付之前 创建预订单 
	@Override
	public OutParamAPCustomerJBGSchedule doBusiness(InParamAPCustomerJBGSchedule in) throws EduExceptions {
	    OutParamAPCustomerJBGSchedule out = new OutParamAPCustomerJBGSchedule();
	    
	    try{
				//step 1  验证输入参数是否有效，如果无效返回-1。
				if (StringUtils.isEmpty(in.CustomerID))
					throw new EduExceptions("  in.CustomerID is null 用户CustomerID不存在!  ");
		
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
				if (null ==terminalEntitys)
						throw new EduExceptions(" terminalEntitys   TerminalNo="+userEntity.getTerminalno()+" not find  不存在 !");
				 			
				if(!SysDict.TERMINAL_STATUS_NORMAL.equals(terminalEntitys.getRunstatus().toString())){
		            throw new EduExceptions("  terminalEntitys.getRunstatus() ="+terminalEntitys.getRunstatus()+"  柜体故障  ! ");
		        }//'0:正常  1:锁定  2:故障',
				
			 	String	 displayname=terminalEntitys.getDisplayname();							
			 	String   areacodes=	terminalEntitys.getAreacode();    								//04  总部
			 	String	 address=terminalEntitys.getAddress();        								//安装地址
			 	Date sysDateTime = new Date(System.currentTimeMillis());
				Integer weixin_effective_time=PropertiesUtil.weixin_effective_time();
			 	Date expiredTime = addTimeStampBySecond(sysDateTime, weixin_effective_time);		//锁定时间获取，propertieses.properties中配置
			 	
			 	//step 2  清楚预定格口
			 	PtreadypackageEntityExample customerIdClear_Example=new PtreadypackageEntityExample();
			 	customerIdClear_Example.createCriteria().andCustomeridEqualTo(in.CustomerID).andPaystatusEqualTo(Byte.parseByte("0"));
			 	ptreadypackageEntityDao.deleteByExample(customerIdClear_Example);
			 	
			 	//step 3  判断是否存在已支付预付款记录(微信回退bug)
			 	PtreadypackageEntityExample pay_example=new PtreadypackageEntityExample();
			 	pay_example.createCriteria().andBoxnoEqualTo(in.BoxNo).andTerminalnoEqualTo(in.TerminalNo)
			 	.andPaystatusEqualTo((byte)1);
			 	List<PtreadypackageEntity>	payReady_list=ptreadypackageEntityDao.selectByExample(pay_example);
			 	if(!CollectionUtils.isEmpty(payReady_list)){
			 		out.Remark="1";
			 		return out;
			 	}
			 	
			 	//step 4  判断硬件投币机是否已经锁定了该箱子， 锁定说明已经被别人占用了
				String  terminalId_boxId=in.TerminalNo+"_"+in.BoxNo;
        		StoreInRequestsTemp storeInRequestsTemps= CoinJCGServiceImpl.TerminalIdBoxTempMap.get(terminalId_boxId);
        		if(null!=storeInRequestsTemps && storeInRequestsTemps.isIsovertime()){//存在超时的
	        		logger.info("  硬件清理缓存     storeInRequestsTemps  terminalId_boxId="+terminalId_boxId+"  storeInRequestsTemps="+storeInRequestsTemps);
	    			CoinJCGServiceImpl.TerminalIdBoxTempMap.remove(terminalId_boxId);//清理临时对象
        		}
        		//step5 箱子已经被占用
        		if( null!=storeInRequestsTemps && ! storeInRequestsTemps.isIsovertime()  
        				|| !check_StoreInRecord(terminalEntitys.getTerminalid(),Integer.parseInt(in.BoxNo )) ) {
        			out.Remark="2";
			 		return out;
        		}
			 	
        		
        		
        		//物品检测   是否有物
        		BoxEntity BoxEntitys = boxEntityDao.selectByPrimaryKey(in.TerminalNo, Integer.parseInt(in.BoxNo));
				if(null !=BoxEntitys){
					Byte articles =BoxEntitys.getArticle();
					if(  null!=articles && articles == Byte.parseByte("1")   ){
		            	out.Remark="2";
				 		return out;
		            }
				}
 
        		
			 	//step 5  同时防止用户不断的点击   点击同一个箱子要 进行更新处理的
			 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
			 	ps_example.createCriteria().andBoxnoEqualTo(in.BoxNo).andTerminalnoEqualTo(in.TerminalNo)
			 	.andPaystatusEqualTo((byte)0);
			    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
			    PtreadypackageEntity ptreadypackageEntitys=new PtreadypackageEntity();
			 	if(!CollectionUtils.isEmpty(ptreadypackageEntitysList)){
			 	    ptreadypackageEntitys=ptreadypackageEntitysList.get(0);
			 		String   customerids= ptreadypackageEntitys.getCustomerid();
			 		if(!customerids.equals(in.CustomerID)){
		        		//箱已被其他用户预定
		                throw new EduExceptions(" TerminalNo   BoxNo  in used   箱已被其他用户预定!  ");
		        	}
			 	    //更新订单时间
			 		ptreadypackageEntitys.setExpiredtime(expiredTime);
			 	 	ptreadypackageEntityDao.updateByPrimaryKeySelective(ptreadypackageEntitys);
			 	 	out.BoxNo        = ptreadypackageEntitys.getBoxno();
		            out.PackageID    = ptreadypackageEntitys.getPackageid();
		            out.CustomerID   = ptreadypackageEntitys.getCompanyid();
		            out.TerminalNo   = ptreadypackageEntitys.getTerminalno();
		            out.TerminalName = displayname;
		            out.Location     = Utils.locations(areacodes, displayname, address);
		            out.TradeWaterNo = ptreadypackageEntitys.getTradewaterno();//交易流水号
		    		return out;
			 	}else{
			 	    //更新或创建预定订单
			 	    ptreadypackageEntitys=new PtreadypackageEntity();
			        //生成订单号
			        ptreadypackageEntitys.setPackageid(generateOrderId(sysDateTime, "N"));
			        ptreadypackageEntitys.setTerminalno(in.TerminalNo);
			        ptreadypackageEntitys.setCustomerid(in.CustomerID);;
			        ptreadypackageEntitys.setCustomermobile(userEntity.getTelephone());
			        ptreadypackageEntitys.setCustomername(userEntity.getUsername());
			        ptreadypackageEntitys.setCustomeraddress( Utils.locations(areacodes, displayname, address));
			        ptreadypackageEntitys.setTerminalno(in.TerminalNo);
			        ptreadypackageEntitys.setBoxno(in.BoxNo);
			        ptreadypackageEntitys.setOrdertime(new Date());
			        ptreadypackageEntitys.setLastmodifytime(new Date());
			        ptreadypackageEntitys.setExpiredtime(expiredTime);
			        ptreadypackageEntitys.setTradewaterno(generateTradeWaterNo(sysDateTime));//交易流水号
			        ptreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_0));
			        ptreadypackageEntitys.setOpenstatus((byte) -1);
			        ptreadypackageEntityDao.insert(ptreadypackageEntitys);
			 	}
			 	
			 	// step 6 查询费用
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
					BoxEx boxEntityEx = boxExDao.selectByPrimaryKeys(terminalEntitys.getTerminalid(), Integer.parseInt(in.BoxNo));
					paymentesExList=paymentesDao.getMoneyByBoxTypePrepayDetails(Integer.parseInt(chargeMode), boxEntityEx.getBoxtypecode());
				} 
				if(CollectionUtils.isEmpty(paymentesExList)){
					throw new EduExceptions("  paymentesExList  is null  ");
				}
				PaymentesEx PaymentesExs=	paymentesExList.get(0);
			 	
		        out.BoxNo        = ptreadypackageEntitys.getBoxno();
		        out.PackageID    = ptreadypackageEntitys.getPackageid();
		        out.CustomerID   = ptreadypackageEntitys.getCustomerid();
		        out.TerminalNo   = ptreadypackageEntitys.getTerminalno();
		        out.TerminalName = displayname;
		        out.Location=Utils.locations(areacodes, displayname, address);
		        out.TradeWaterNo = ptreadypackageEntitys.getTradewaterno();
		        out.Amount = PaymentesExs.getMoney().intValue();
			    // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
	    }catch(Exception ex ){
        	logger.error("   AppPayServiceImpl   OutParamAPCustomerJBGSchedule doBusiness(InParamAPCustomerJBGSchedule in)  "+ex);
        	throw new EduExceptions(ex.getMessage());
        }
      return out;
	}
	
	
    //check_StoreInRecord 请求是否占用了该箱子
	public Boolean   check_StoreInRecord(String terminalId,Integer boxId) {
		//Step.4 查询在箱记录
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		example.createCriteria().andTerminalidEqualTo(terminalId)
		                        .andBoxidEqualTo(boxId)
		                        .andStateEqualTo((byte)0);//0:在箱 1：已取
		List<StoreInRecordEntity> records = storeInRecordExDao.selectByExample(example);
		if (records == null || records.size() == 0){
			return true;
		}
		return false;
	}
	
	
	
	//显示支付价格列表
	@Override
	public List<OutParamAPCustomerBoxHireAmtQry> doBusiness(InParamAPCustomerBoxHireAmtQry in) throws EduExceptions {
	        List<OutParamAPCustomerBoxHireAmtQry> outList = new  LinkedList<OutParamAPCustomerBoxHireAmtQry>();
	        try{
	        	//1.验证输入参数是否有效，如果无效返回-1。
				if (StringUtils.isEmpty(in.CustomerID))
					throw new EduExceptions("  in.CustomerID is null   用户CustomerID不存在！ ");
		
				UserEntityExample example = new UserEntityExample();
				example.createCriteria().andCustomeridEqualTo(in.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
				//example.createCriteria().andUsercardidEqualTo("0014418702");
				List<UserEntity> userEntityList = userExDao.selectByExample(example);
				if(CollectionUtils.isEmpty(userEntityList)){
					 return outList;
				}
				UserEntity  userEntity=userEntityList.get(0);
		        if(StringUtils.isEmpty(userEntity.getTerminalno())){
			        return outList;
			    }
		        
		        if(StringUtils.isEmpty(in.TerminalNo)){
		            in.TerminalNo = userEntity.getTerminalno();
		        }
		 
			 	TerminalEntity  terminalEntitys= terminalExDao.selectByPrimaryKey(in.TerminalNo);
				if (null ==terminalEntitys)
						throw new EduExceptions(" terminalEntitys   TerminalNo="+userEntity.getTerminalno()+" not find  不能找到  !");
				 
				//'0: 正常 1:锁定 2:故障',
				if(!SysDict.TERMINAL_STATUS_NORMAL.equals(terminalEntitys.getRunstatus().toString())){
		            throw new EduExceptions("  terminalEntitys.getRunstatus() ="+terminalEntitys.getRunstatus()+"   柜体故障  !");
		        }
			 	String	 displayname=terminalEntitys.getDisplayname();//1001
			 	String   areacodes=	terminalEntitys.getAreacode();    //04  总部
			 	String	 address=terminalEntitys.getAddress();        //安装地址
			 	//String	 Terminalid=terminalEntitys.getTerminalid();   //安装地址
			 	
			 	Date sysDateTime = new Date(System.currentTimeMillis());
			 	Date expiredTime = addTimeStampBySecond(sysDateTime, 600);//10分钟
			 	
			 	
				/** 新 Step.1   查找设备的业务规则  */
				//业务规则
				TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalEntitys.getTerminalid());//根据设备号查询业务编码
				Integer businessCode = terminalEntity.getBusinesscode();
				//根据业务编码获取业务模型
				Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
				if (businessModelMap == null){
					throw new EduExceptions("  businessModelMap is null    收费模式没有定义 ！  ");
				}
			 	
				/** 新 Step.2     */
			    List<PaymentesEx>   paymentesExList=null;
				String sCheckCharge = businessModelMap.get(Constant.BT_CFG_NAME_CHARGESWITCH);
				if (StringUtils.isNotEmpty(sCheckCharge) ){
					String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
					//收费标准chargeCode      0：vip更衣柜收费标准   1 更衣柜收费标准 2 贵重物品收费标准   
					//收费模式 usedState       0：按小时收费 1：按时段收费 2：按天收费
					//boxTypeCode           1 小   2中  3大      折扣=实际售价/原售价×100%
					BoxEx boxEntityEx = boxExDao.selectByPrimaryKeys(terminalEntity.getTerminalid(), Integer.parseInt(in.BoxNo));
					//计算预付费用
					paymentesExList=paymentesDao.getMoneyByBoxTypePrepayDetails(Integer.parseInt(chargeMode), boxEntityEx.getBoxtypecode());
				} 
				//收费模式 usedState       0：按小时收费 1：按时段收费 2：按天收费
				//2：按天收费     就是区间收费
				//1：按时段收费  就是区间收费
				//0：按小时收费  有间隔的
				
                if(!CollectionUtils.isEmpty(paymentesExList)){
                	 for(int i=0;i<paymentesExList.size();i++){
                		 PaymentesEx PaymentesExs=paymentesExList.get(i);
                		 int startTime= PaymentesExs.getStartTime();
                		 int endTime= PaymentesExs.getEndTime();
                		 int timeValue= PaymentesExs.getTimeValue();
                		 OutParamAPCustomerBoxHireAmtQry out = new OutParamAPCustomerBoxHireAmtQry();
     		             out.ChargeType =  PaymentesExs.getUsedState().toString();//收费模式 usedState       0：按小时收费 1：按时段收费 2：按天收费
     		             out.Amount     = PaymentesExs.getMoney().intValue() ;//分
     		             double amountYuan = (double)out.Amount;
     		             switch(out.ChargeType){
     				            case SysDict.CHARGE_TYPE_HOURS:
     				                out.Num = 0;
     				                out.NumUnit    = "收费标准:";
     				                out.ModifyFlag = "0";//租用时长是否允许修改标志，0-不允许，1-允许用户设置租用时长
     				                out.Remark     = "每"+timeValue+"小时 收费"+amountYuan+"元";
     				                break;
     				            case SysDict.CHARGE_TYPE_DAYS:
     				                out.Num = 0;
     				                out.NumUnit    = "按天";
     				                out.ModifyFlag = "0";//租用时长是否允许修改标志，0-不允许，1-允许用户设置租用时长
     				                out.Remark     = "（"+startTime+"天到"+endTime+"天）区间收费 "+amountYuan+"元";
     				                break;
     				            case SysDict.CHARGE_TYPE_TIME_INTERVAL:
    				                out.Num = 0;
    				                out.NumUnit    = "按时段";
    				                out.ModifyFlag = "0";//租用时长是否允许修改标志，0-不允许，1-允许用户设置租用时长
    				                out.Remark     = "（"+startTime+"到"+endTime+"）区间收费 "+amountYuan+"元";
    				                break; 
     		              }
     		             outList.add(out);
                	 }
  
                }
	        }catch(Exception ex ){
	        	logger.error("   AppPayServiceImpl List<OutParamAPCustomerBoxHireAmtQry> doBusiness(InParamAPCustomerBoxHireAmtQry in)   "+ex);
	        	throw new EduExceptions(ex.getMessage());
	        }
	        return outList; 
		 
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
	

    /**
     * 生成订单号
     * @param sysDateTime
     * @param tail N-正常收费（预收费）订单，E-逾期收费订单，B-退款收费订单
     * @return
     */
    private static String generateOrderId(Date sysDateTime, String tail){
        SimpleDateFormat datetimeFromat = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer orderid = new StringBuffer(datetimeFromat.format(sysDateTime));
        orderid.append(RandUtils.generateNumber(5)).append(tail);
        return orderid.toString();
    }
    /**
     * 生成交易流水:yyyyMMddHHmmss+5位随机数
     * @param sysDateTime
     * @return
     */
    public  static String generateTradeWaterNo(Date sysDateTime) {
        SimpleDateFormat datetimeFromat = new SimpleDateFormat("yyyyMMddHHmmss");
        String headStr = datetimeFromat.format(sysDateTime);
        StringBuffer buff = new StringBuffer();
        buff.append(headStr).append(RandUtils.generateNumber(3)).append(RandUtils.generateNumber(3));
        return buff.toString();
    }
    
    
    
    
    public static Date addTimeStampBySecond(Date date, int second)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)second * 1000L);
        return new Date(cal.getTime().getTime());
    }
    public static long getMillis(java.util.Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }
    
	
	public static void main(String[] args) {
	   /*Date sysDateTime=new Date();
	   String generateOrderId=	generateOrderId(sysDateTime, "N");
       System.out.println("----1------generateOrderId-----"+generateOrderId);
	   String generateTradeWaterNo=generateTradeWaterNo(sysDateTime);
	   System.out.println("----2------generateTradeWaterNo-----"+generateTradeWaterNo);*/
	   
	   Date sysDateTime = new Date(System.currentTimeMillis());
	   System.out.println("1 sysDateTime= "+sysDateTime.toLocaleString());
	   Date expiredTime = addTimeStampBySecond(sysDateTime, 600);//10分钟
	   System.out.println("2 expiredTime= "+expiredTime.toLocaleString());
	}
    
    
    
}




















