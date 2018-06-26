package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant.*;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGStore;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGStoreTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerPayBack;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPDepositOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGStore;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGStoreTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPPayBackService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentesDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxSizeEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackageEntityDao;
 
/**
 * @author ysk
 * @Description: 投币机 
 * @ClassName:  AppPayServiceImpl
 * @date 2017年8月16日 下午13:09:00
 */
@Transactional
@Service("iAPPPayBackService")
public class AppPayBackServiceImpl implements IAPPPayBackService{
	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(AppPayBackServiceImpl.class);
	//private final static Logger logger = Logger.getLogger(AppPayBackServiceImpl.class);
	
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
	protected StoreInRecordExDao storeInRecordExDao;
	@Autowired
	protected BusinessModelEntityExDao business;
	@Autowired
	protected PaymentesDao paymentesDao;
	@Autowired
	protected BoxExDao boxExDao;
	
	
	//微信支付回调
	@Override
	public String doBusiness(InParamAPCustomerPayBack ins)throws EduExceptions {
		/*
		      `TradeWaterNo` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '流水号',
		      `TradeWaterNotwo` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '二次支付流水号',
		 */
		BigDecimal amt = new BigDecimal(ins.Amt);
		BigDecimal num =new BigDecimal("100");
		BigDecimal money = amt.divide(num);
		PtreadypackageEntity PtreadypackageEntitys=null;
		PtreadypackageEntityExample tradeWaterNo_example=new PtreadypackageEntityExample();
		tradeWaterNo_example.createCriteria().andTradewaternoEqualTo(ins.TradeWaterNo);//可能1一次 2次 3次 4次
		List<PtreadypackageEntity>	ptreadypackage_1_EntitysList=ptreadypackageEntityDao.selectByExample(tradeWaterNo_example);
		if(CollectionUtils.isEmpty(ptreadypackage_1_EntitysList)){
			PtreadypackageEntityExample tradeWaterNoTwo_example=new PtreadypackageEntityExample();
			tradeWaterNoTwo_example.createCriteria().andTradewaternotwoEqualTo(ins.TradeWaterNo);// 2次  
			List<PtreadypackageEntity>	ptreadypackage_2_EntitysList=ptreadypackageEntityDao.selectByExample(tradeWaterNoTwo_example);
			if(CollectionUtils.isEmpty(ptreadypackage_2_EntitysList)){
		    	 throw new EduExceptions(" AppPayBackServiceImpl  doBusiness(InParamAPCustomerPayBack ins)    支付成功了  但是订单不存在   !!!!  ");
			}else{
				//取_支付回调业务
				PtreadypackageEntitys= ptreadypackage_2_EntitysList.get(0);
				String paystatus = PtreadypackageEntitys.getPaystatus().toString();
				String openstatus = PtreadypackageEntitys.getOpenstatus().toString();
				if(paystatus.equals("1")&&openstatus.equals("0")){
					PtreadypackageEntitys.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_3));
					PtreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_2));
					PtreadypackageEntitys.setHireamt(PtreadypackageEntitys.getPayamt().add(money));	//费用总计
					ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
				}
			}
		}else{
			//存_支付回调业务
			PtreadypackageEntitys= ptreadypackage_1_EntitysList.get(0);
			String paystatus = PtreadypackageEntitys.getPaystatus().toString();
			String openstatus = PtreadypackageEntitys.getOpenstatus().toString();
			if(paystatus.equals("0")&&openstatus.equals("-1")){
				PtreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_1));
				PtreadypackageEntitys.setPayamt(money);	//预付金额转化	"分为元"TODO
				PtreadypackageEntitys.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_1));
				PtreadypackageEntitys.setLimitcounts(1);
				PtreadypackageEntitys.setLimitcountstake(0);
				ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
			}
			  
		}
		return null;
	}
	//1   开箱支付回调地址
	/*@Override
	public OutParamAPCustomerJBGStore doBusiness(InParamAPCustomerJBGStore in) throws EduExceptions {
        OutParamAPCustomerJBGStore out = new OutParamAPCustomerJBGStore();
        try{
		 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
		 	ps_example.createCriteria().andBoxnoEqualTo(in.BoxNo).andTerminalnoEqualTo(in.TerminalNo)
		 	                           .andCustomeridEqualTo(in.CustomerID).andPackageidEqualTo(in.PackageID)
		 	                           .andTradewaternoEqualTo(in.TransactionNo);
		    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
		    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
		    	 throw new EduExceptions(" AppPayBackServiceImpl kai doBusiness   ptreadypackageEntitysList  is null 订单不存在      ");
		    }
		    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList.get(0);
		    String   paystatus= PtreadypackageEntitys.getPaystatus().toString();//'0:未支付，1：已支付',
		    if(paystatus.equals(SysDict.PAY_Status_1)){
		    	 throw new EduExceptions(" AppPayBackServiceImpl kai doBusiness   paystatus  have payed   订单已支付 ");
		    }
		    
	 	    //更新订单支付状态
		    PtreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_1));
		    PtreadypackageEntitys.setLimitcounts(0);
		    PtreadypackageEntitys.setLimitcountstake(0);
	 	 	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
		    
	        //返回值 
	        out.PackageID = in.PackageID;
	        out.BoxNo = in.BoxNo;
	        out.TerminalNo = in.TerminalNo;
	        out.CustomerID = in.CustomerID;
	        //推送消息
	        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        }catch(Exception ex ){
        	logger.error("   AppPayBackServiceImpl kai OutParamAPCustomerJBGStore doBusiness(InParamAPCustomerJBGStore in)   "+ex);
        	throw new EduExceptions(ex.getMessage());
        } 
        return out;
	}*/

	
	//2  取件支付回调地址
	@Override
	public OutParamAPCustomerJBGStoreTake doBusiness(InParamAPCustomerJBGStoreTake in) throws EduExceptions {
		   OutParamAPCustomerJBGStoreTake out = new OutParamAPCustomerJBGStoreTake();
	        try{
	        	
			 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
			 	ps_example.createCriteria().andBoxnoEqualTo(in.BoxNo).andTerminalnoEqualTo(in.TerminalNo)
			 	                           .andCustomeridEqualTo(in.CustomerID).andPackageidEqualTo(in.PackageID);
			 	                           //.andTradewaternoremainEqualTo(in.TransactionNo);
			    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
			    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
			    	 throw new EduExceptions(" AppPayBackServiceImpl take doBusiness   ptreadypackageEntitysList  is null 订单不存在      ");
			    }
			    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList.get(0);
			    //step 1 更新订单支付状态 
			    /*
				    PtreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_2));
				    PtreadypackageEntitys.setTradewaternoremain(in.TransactionNo);    //更新二次支付订单流水号
				    BigDecimal BigDecimals=new BigDecimal(in.TransactionAmt);
				    PtreadypackageEntitys.setHireamt(PtreadypackageEntitys.getPayamt().add(BigDecimals));
			 	 	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
			    */
			    
		 	 	//step 2 更新在箱信息
		 	 	StoreInRecordEntityExample R_Example = new StoreInRecordEntityExample();
			    R_Example.createCriteria()
			    .andBoxidEqualTo(Integer.parseInt(in.BoxNo))
			    .andTerminalidEqualTo(in.TerminalNo)
			    .andUsercardidEqualTo(in.CustomerID)
			    .andStateEqualTo((byte)0);
			    List<StoreInRecordEntity> StoreInRecordEntityList= storeInRecordExDao.selectByExample(R_Example);
			    if(!CollectionUtils.isEmpty(StoreInRecordEntityList)){
			    	StoreInRecordEntity StoreInRecordEntitys = StoreInRecordEntityList.get(0);
			    	StoreInRecordEntitys.setState((byte)1);
			    	storeInRecordExDao.updateByPrimaryKeySelective(StoreInRecordEntitys);
			    }
		 	 	
			    //返回值 
		        out.PackageID = in.PackageID;
		        out.BoxNo = in.BoxNo;
		        out.TerminalNo = in.TerminalNo;
		        out.CustomerID = in.CustomerID;
			    
			    //step 3 下行开箱业务
			    InParamAPDepositOpenBox p1 = new InParamAPDepositOpenBox();
		        p1.OpenType     = "9";//开箱类型 9 未知  0 存 1 取
		        p1.OpenID       = in.CustomerID;
		        p1.BoxNo        = in.BoxNo;
		        p1.TerminalNo   = in.TerminalNo;
		        p1.OccurTime    = new  Timestamp(new Date().getTime());
		        p1.TradeWaterNo = PtreadypackageEntitys.getTradewaterno();
		        p1.CheckCode    = "";
		        try{
			    	PushBusinessProxy.doBusiness(p1);  
			    	PtreadypackageEntitys.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_2));
			    	PtreadypackageEntitys.setLimitcountstake(1);
			    	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
			    	return out;
				    }catch(Exception ex){
				    	PtreadypackageEntitys.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_3));
				    	PtreadypackageEntitys.setLimitcountstake(1);
				    	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
				    	return null;
				    }
	        }catch(Exception ex ){
	        	logger.error("   AppPayBackServiceImpl take OutParamAPCustomerJBGStoreTake doBusiness(InParamAPCustomerJBGStoreTake in)   "+ex);
	        	throw new EduExceptions(ex.getMessage());
	        }
	}
	
	
	
	
	//--------------------------老的1.0------------------
	/*
	//1   开箱支付回调地址
	@Override
	public OutParamAPCustomerJBGStore doBusiness(InParamAPCustomerJBGStore in) throws EduExceptions {
        OutParamAPCustomerJBGStore out = new OutParamAPCustomerJBGStore();
        try{
		 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
		 	ps_example.createCriteria().andBoxnoEqualTo(in.BoxNo).andTerminalnoEqualTo(in.TerminalNo)
		 	                           .andCustomeridEqualTo(in.CustomerID).andPackageidEqualTo(in.PackageID)
		 	                           .andTradewaternoEqualTo(in.TransactionNo);
		    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
		    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
		    	 throw new EduExceptions(" AppPayBackServiceImpl kai doBusiness   ptreadypackageEntitysList  is null 订单不存在      ");
		    }
		    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList.get(0);
		    String   paystatus= PtreadypackageEntitys.getPaystatus().toString();//'0:未支付，1：已支付',
		    if(paystatus.equals(SysDict.PAY_Status_1)){
		    	 throw new EduExceptions(" AppPayBackServiceImpl kai doBusiness   paystatus  have payed   订单已支付 ");
		    }
		    
	 	    //更新订单支付状态
		    PtreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_1));
		    PtreadypackageEntitys.setLimitcounts(0);
		    PtreadypackageEntitys.setLimitcountstake(0);
	 	 	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
		    
	        //返回值 
	        out.PackageID = in.PackageID;
	        out.BoxNo = in.BoxNo;
	        out.TerminalNo = in.TerminalNo;
	        out.CustomerID = in.CustomerID;
	        //推送消息
	        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        }catch(Exception ex ){
        	logger.error("   AppPayBackServiceImpl kai OutParamAPCustomerJBGStore doBusiness(InParamAPCustomerJBGStore in)   "+ex);
        	throw new EduExceptions(ex.getMessage());
        } 
        return out;
	}

	
	//2  取件支付回调地址
	@Override
	public OutParamAPCustomerJBGStoreTake doBusiness(InParamAPCustomerJBGStoreTake in) throws EduExceptions {
		   OutParamAPCustomerJBGStoreTake out = new OutParamAPCustomerJBGStoreTake();
	        try{
	        	
			 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
			 	ps_example.createCriteria().andBoxnoEqualTo(in.BoxNo).andTerminalnoEqualTo(in.TerminalNo)
			 	                           .andCustomeridEqualTo(in.CustomerID).andPackageidEqualTo(in.PackageID);
			 	                           //.andTradewaternoremainEqualTo(in.TransactionNo);
			    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
			    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
			    	 throw new EduExceptions(" AppPayBackServiceImpl take doBusiness   ptreadypackageEntitysList  is null 订单不存在      ");
			    }
			    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList.get(0);
			    String   paystatus= PtreadypackageEntitys.getPaystatus().toString();//'0:未支付 , 1：已支付  , 2: 取支付成功 ',
			    if(!paystatus.equals(SysDict.PAY_Status_1)){
			    	 throw new EduExceptions(" AppPayBackServiceImpl take doBusiness   no prepaied    ");
			    }
			    
			    
			    //step 1 更新订单支付状态 
			    PtreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_2));
			    PtreadypackageEntitys.setTradewaternoremain(in.TransactionNo);    //更新二次支付订单流水号
			    BigDecimal BigDecimals=new BigDecimal(in.TransactionAmt);
			    PtreadypackageEntitys.setHireamt(PtreadypackageEntitys.getPayamt().add(BigDecimals));
		 	 	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
			    
		 	 	//step 2 更新在箱信息
		 	 	StoreInRecordEntityExample R_Example = new StoreInRecordEntityExample();
			    R_Example.createCriteria()
			    .andBoxidEqualTo(Integer.parseInt(in.BoxNo))
			    .andTerminalidEqualTo(in.TerminalNo)
			    .andUsercardidEqualTo(in.CustomerID)
			    .andStateEqualTo((byte)0);
			    List<StoreInRecordEntity> StoreInRecordEntityList= storeInRecordExDao.selectByExample(R_Example);
			    if(!CollectionUtils.isEmpty(StoreInRecordEntityList)){
			    	StoreInRecordEntity StoreInRecordEntitys = StoreInRecordEntityList.get(0);
			    	StoreInRecordEntitys.setState((byte)1);
			    	storeInRecordExDao.updateByPrimaryKeySelective(StoreInRecordEntitys);
			    }
		 	 	
			    //返回值 
		        out.PackageID = in.PackageID;
		        out.BoxNo = in.BoxNo;
		        out.TerminalNo = in.TerminalNo;
		        out.CustomerID = in.CustomerID;
			    
			    //step 3 下行开箱业务
			    InParamAPDepositOpenBox p1 = new InParamAPDepositOpenBox();
		        p1.OpenType     = "9";//开箱类型 9 未知  0 存 1 取
		        p1.OpenID       = in.CustomerID;
		        p1.BoxNo        = in.BoxNo;
		        p1.TerminalNo   = in.TerminalNo;
		        p1.OccurTime    = new  Timestamp(new Date().getTime());
		        p1.TradeWaterNo = PtreadypackageEntitys.getTradewaterno();
		        p1.CheckCode    = "";
		        try{
			    	PushBusinessProxy.doBusiness(p1);  //不做业务处理，直接开箱
			    	PtreadypackageEntitys.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_2));
			    	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
			    	return out;
				    }catch(Exception ex){
				    	PtreadypackageEntitys.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_3));
				    	PtreadypackageEntitys.setLimitcountstake(1);
				    	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
				    	return null;
				    }
	        }catch(Exception ex ){
	        	logger.error("   AppPayBackServiceImpl take OutParamAPCustomerJBGStoreTake doBusiness(InParamAPCustomerJBGStoreTake in)   "+ex);
	        	throw new EduExceptions(ex.getMessage());
	        }
	}
	*/
 
	
 
}




















