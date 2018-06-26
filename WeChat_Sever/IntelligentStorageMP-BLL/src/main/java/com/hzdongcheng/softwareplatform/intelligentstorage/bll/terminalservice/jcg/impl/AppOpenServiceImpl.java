package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerConsumeAmt;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerInTimeOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGOpenBoxTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGPayOpenBoxTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPDepositOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPWxmenuInfo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamOpenErrBoxInfo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerConsumeAmt;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPWxmenuInfo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPOpenService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.Utils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PunishRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.WxmenuEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.WxmenuEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxSizeEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackageEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.WxmenuEntityDao;
 
/**
 * @author ysk
 * @Description: 投币机 
 * @ClassName:  CoinJCGServiceImpl
 * @date 2017年8月16日 下午13:09:00
 */

@Service("iAPPOpenService")
public class AppOpenServiceImpl implements IAPPOpenService{
	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(AppOpenServiceImpl.class);
	//private final static Logger logger = Logger.getLogger(AppOpenServiceImpl.class);
	
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
	protected TakeOutRecordEntityDao takeOutRecordEntityDao;
	@Autowired
	protected BusinessModelEntityExDao business;
	@Autowired
	protected BoxExDao boxExDao;
	@Autowired
	protected PunishRecordExDao punishDao;
	@Autowired
	protected  WxmenuEntityDao WxmenuDao;
	
	
	
    /**
     * 消费金额计算s
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	@Override
	public OutParamAPCustomerConsumeAmt doBusiness(InParamAPCustomerConsumeAmt in) throws EduExceptions {
		   OutParamAPCustomerConsumeAmt out = new OutParamAPCustomerConsumeAmt();
		   try{
		       
				
			    PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
			 	ps_example.createCriteria().andCustomeridEqualTo(in.CustomerID)
			 	                            .andPackageidEqualTo(in.PackageID).andPaystatusEqualTo(Byte.parseByte(SysDict.PAY_Status_1)) ;
			    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
			    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
			    	 throw new EduExceptions(" AppOpenServiceImpl  doBusiness   ptreadypackageEntitysList  is null 订单不存在或者没有支付      ");
			    }
			    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList.get(0);
			    
			    //判断是否逾期，计算逾期费用
			 	TerminalEntity  terminalEntitys= terminalExDao.selectByPrimaryKey(PtreadypackageEntitys.getTerminalno());
				if (null ==terminalEntitys)
						throw new EduExceptions(" terminalEntitys   TerminalNo  not find  不存在 !");
				 
				//'0: 正常 1:锁定 2:故障',
				if(!SysDict.TERMINAL_STATUS_NORMAL.equals(terminalEntitys.getRunstatus().toString())){
		            throw new EduExceptions("  terminalEntitys.getRunstatus() ="+terminalEntitys.getRunstatus()+"  柜体故障  ! ");
		        }

			 	String	 displayname=terminalEntitys.getDisplayname();//1001
			 	String   areacodes=	terminalEntitys.getAreacode();    //04  总部
			 	String	 address=terminalEntitys.getAddress();        //安装地址
			 	//String	 Terminalid=terminalEntitys.getTerminalid();   //安装地址
			 	
			 	
		        //计算消费金额
		        out.PackageID      = in.PackageID;
		        //out.PrepayAmt      = (int)(PtreadypackageEntitys.getPayamt().intValue()/100);//单位分   //已支付金额（分)
		        out.PrepayAmt      = (int)(PtreadypackageEntitys.getPayamt().intValue());//单位分   //已支付金额（分)
		        //out.Amount       =  amountCent;        //待支付金额(分)
		        //out.StoredTime   = Constant.datetimeFromat.format(inboxPack.StoredTime);
		        //out.SysDateTime  = Constant.datetimeFromat.format(sysDateTime);
		        //out.ExpiredTime  = Constant.datetimeFromat.format(inboxPack.ExpiredTime);
		        out.TerminalNo     = terminalEntitys.getTerminalid();
		        out.TerminalName   = displayname;
		        out.Location       = Utils.locations(areacodes, displayname, address);
		   }catch(Exception ex ){
	        	logger.error("   AppOpenServiceImpl  OutParamAPCustomerConsumeAmt doBusiness(InParamAPCustomerConsumeAmt in)   "+ex);
	      	    throw new EduExceptions(ex.getMessage());
	       }
	       return out;
 
	}
    /**
     * 用户开箱  存
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	@Override
	public String doBusiness(InParamAPCustomerJBGOpenBox in) throws EduExceptions {
		   String results="0";
		   
		   int openStatus = 0;
		   PtreadypackageEntityExample openStatus_example=new PtreadypackageEntityExample();
		   openStatus_example.createCriteria().andCustomeridEqualTo(in.CustomerID).andPackageidEqualTo(in.PackageID);
		   List<PtreadypackageEntity> openStatus_results = ptreadypackageEntityDao.selectByExample(openStatus_example);
		   if(CollectionUtils.isEmpty(openStatus_results)){
		    	 throw new EduExceptions(" AppOpenServiceImpl  doBusiness   ptreadypackageEntitysList  is null 订单不存在      ");
		    }
		   if(!CollectionUtils.isEmpty(openStatus_results)){
			   PtreadypackageEntity openStatus_result= openStatus_results.get(0);
			   openStatus = openStatus_result.getOpenstatus();
			   if(openStatus==1){
				   int      boxId     =  Integer.parseInt(openStatus_result.getBoxno());
				   String termianlNo = openStatus_result.getTerminalno();
				   
				   //判断是否被占用
				   List<StoreInRecordEntity> storeInRecordEntityList = terminalExDao.selectInByBoxId(0, boxId, termianlNo);//state 0:在箱 1：已取
				    if (!CollectionUtils.isEmpty(storeInRecordEntityList)){
				    	throw new EduExceptions(" storeInRecordEntityList   ");
					}
				   InParamAPDepositOpenBox p1 = new InParamAPDepositOpenBox();
			        p1.OpenType     = "9";//开箱类型 9 未知  0 存 1 取
			        p1.OpenID       = in.CustomerID;
			        p1.BoxNo        = in.BoxNo;
			        p1.TerminalNo   = openStatus_result.getTerminalno();
			        p1.OccurTime    = new  Timestamp(new Date().getTime());
			        p1.TradeWaterNo = in.TradeWaterNo;
			        p1.CheckCode    = "";
			        try{
			        	int Limitcounts = openStatus_result.getLimitcounts();
			        	if(Limitcounts<4){
			        	Date time = new Date();
			        	int      boxID     =  Integer.parseInt(openStatus_result.getBoxno());
			        	//下行开箱业务
				    	String result = PushBusinessProxy.doBusiness(p1);
				    	System.out.println(result);
				    	openStatus_result.setOpenstatus((byte) 0);
				    	openStatus_result.setStoreintime(time);
				    	openStatus_result.setLastmodifytime(time);
				    	ptreadypackageEntityDao.updateByPrimaryKeySelective(openStatus_result); //更新P表
				    	StoreInRecordEntity storeInRecordEntity = new StoreInRecordEntity();	
						storeInRecordEntity.setTerminalid(openStatus_result.getTerminalno());
						storeInRecordEntity.setBoxid(boxID);
						storeInRecordEntity.setUsercardid(openStatus_result.getCustomerid());
						storeInRecordEntity.setStoreintime(time);
						storeInRecordEntity.setState((byte)0);      //0:在箱 1：已取
						storeInRecordEntity.setUsertype(SysDict.CUSTOMER_TYPE_WEIXIN);   //0普通用户1贵宾用户9黑名单用户11微信用户
						storeInRecordEntity.setRealmoney((float)0); //找零金额
						storeInRecordEntity.setMoney(openStatus_result.getPayamt().floatValue()); //实收金额
						storeInRecordExDao.insertSelective(storeInRecordEntity);
						return results;
						}else{
				    		results = "3";
						    return results;
				    	}
				    }catch(Exception ex){
				    	int Limitcounts = openStatus_result.getLimitcounts();
				    	Limitcounts++;
				    	openStatus_result.setLimitcounts(Limitcounts);
			    		ptreadypackageEntityDao.updateByPrimaryKeySelective(openStatus_result);
				    		results = "2";
						    return results;
				    }
			   }
		   }
		   
		   try{
			   //step 2  先开箱  
			    InParamAPDepositOpenBox p1 = new InParamAPDepositOpenBox();
		        p1.OpenType     = "9";//开箱类型 9 未知  0 存 1 取
		        p1.OpenID       = in.CustomerID;
		        p1.BoxNo        = in.BoxNo;
		        p1.TerminalNo   = in.TerminalNo;
		        p1.OccurTime    = new  Timestamp(new Date().getTime());
		        p1.TradeWaterNo = in.TradeWaterNo;
		        p1.CheckCode    = "";
		        try{
			    	PushBusinessProxy.doBusiness(p1);	//下行开箱业务
			    }catch(Exception ex){
				    PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
				 	ps_example.createCriteria().andCustomeridEqualTo(in.CustomerID)
				 	                            .andPackageidEqualTo(in.PackageID).andPaystatusEqualTo(Byte.parseByte(SysDict.PAY_Status_1)) ;
				    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
				    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
				    	 throw new EduExceptions(" AppOpenServiceImpl  doBusiness   ptreadypackageEntitysList  is null 订单不存在或者没有支付      ");
				    }
				    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList.get(0);
				    int i = PtreadypackageEntitys.getLimitcounts();
				    i++;
				    PtreadypackageEntitys.setOpenstatus((byte) 1);
				    PtreadypackageEntitys.setLimitcounts(i);
					ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
					results = "1";
				    return results;
			    }
		        
		        //step 3 卡箱记录处理
			    PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
			 	ps_example.createCriteria().andCustomeridEqualTo(in.CustomerID)
			 	                            .andPackageidEqualTo(in.PackageID).andPaystatusEqualTo(Byte.parseByte(SysDict.PAY_Status_1)) ;
			    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
			    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
			    	 throw new EduExceptions(" AppOpenServiceImpl  doBusiness   ptreadypackageEntitysList  is null 订单不存在或者没有支付      ");
			    }
			    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList.get(0);
			    
			    String   customerid =  PtreadypackageEntitys.getCustomerid();
			    int      boxID     =  Integer.parseInt(PtreadypackageEntitys.getBoxno());
			    String  terminalID =  PtreadypackageEntitys.getTerminalno();
			    float    payamt =  PtreadypackageEntitys.getPayamt().floatValue();
 
				//4 判断箱子是否被占用
			    List<StoreInRecordEntity> storeInRecordEntityList = terminalExDao.selectInByBoxId(0, boxID, terminalID);//state 0:在箱 1：已取
			    if (!CollectionUtils.isEmpty(storeInRecordEntityList)){
			    	throw new EduExceptions(" storeInRecordEntityList   ");
				}
 
			    StoreInRecordEntity 	storeInRecordEntity = new StoreInRecordEntity();		
				storeInRecordEntity.setTerminalid(terminalID);
				storeInRecordEntity.setBoxid(boxID);
				storeInRecordEntity.setUsercardid(customerid);
				storeInRecordEntity.setStoreintime(new Date());
				storeInRecordEntity.setState((byte)0);      //0:在箱 1：已取
				storeInRecordEntity.setUsertype(SysDict.CUSTOMER_TYPE_WEIXIN);   //0普通用户1贵宾用户9黑名单用户     11微信用户
				storeInRecordEntity.setRealmoney((float)0); //找零金额
				storeInRecordEntity.setMoney(payamt); //实收金额
				storeInRecordExDao.insertSelective(storeInRecordEntity);
 
				PtreadypackageEntitys.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_0));  //更新OPENStatus状态为0
				PtreadypackageEntitys.setStoreintime(storeInRecordEntity.getStoreintime());
				ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
		        try{
		            Thread.sleep(500);
		        }catch(Exception e) {
		            System.out.println(e.getMessage());
		        }
				// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		   }catch(Exception ex ){
	        	logger.error("   AppOpenServiceImpl  doBusiness(InParamAPCustomerJBGOpenBox in) "+ex);
	      	    throw new EduExceptions(ex.getMessage());
	       }
	       return results;
	}
 
 
    /**
     * 用户开箱  取
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	@Override
	public String doBusiness(InParamAPCustomerJBGOpenBoxTake in) throws EduExceptions {
		   String results="0";
		   try{
			   //step 1  开箱前 判断是否超时   超时就重新支付才能开箱的
			    String   terminalID=  in.TerminalNo;
			    Integer boxid= Integer.parseInt(in.BoxNo);
			    String  openBoxCode=in.CustomerID;
				//根据设备号查询业务编码
				TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
				Integer businessCode = terminalEntity.getBusinesscode();

				//根据业务编码获取业务模型
				Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
				if (businessModelMap == null){
					return results;
				}
 
				//step 2 判断是否是故障开箱
				PtreadypackageEntityExample failedOpen_example=new PtreadypackageEntityExample();
				failedOpen_example.createCriteria()
				.andCustomeridEqualTo(in.CustomerID)
				.andPackageidEqualTo(in.PackageID)
				.andPaystatusEqualTo(Byte.parseByte(SysDict.PAY_Status_2))
				.andOpenstatusEqualTo(Byte.parseByte(SysDict.OPEN_Status_3));
				List<PtreadypackageEntity>	failedOpenLists=ptreadypackageEntityDao.selectByExample(failedOpen_example);
				if(!CollectionUtils.isEmpty(failedOpenLists)){
					InParamAPDepositOpenBox p1 = new InParamAPDepositOpenBox();
					PtreadypackageEntity failedOpenList = failedOpenLists.get(0);
					int i =  failedOpenList.getLimitcountstake();
			        p1.OpenType     = "9";//开箱类型 9 未知  0 存 1 取
			        p1.OpenID       = in.CustomerID;
			        p1.BoxNo        = in.BoxNo;
			        p1.TerminalNo   = in.TerminalNo;
			        p1.OccurTime    = new  Timestamp(new Date().getTime());
			        p1.TradeWaterNo = in.TradeWaterNo;
			        p1.CheckCode    = ""; 
			        try{
			        	if(i<4){
			        		i++;			//用户操作次数未到达3次，开放开箱权限
					    	PushBusinessProxy.doBusiness(p1);  //不做业务处理，直接开箱
					    	failedOpenList.setOpenstatus(Byte.parseByte(SysDict.OPEN_Status_2));
					    	ptreadypackageEntityDao.updateByPrimaryKeySelective(failedOpenList);
					    	return results;
			        	}else{
							results="timesBeyond";	//用户操作次数到达3次，联系管理员
							return results;
						}
				    }catch(Exception ex){
				    	failedOpenList.setLimitcountstake(i);
				    	ptreadypackageEntityDao.updateByPrimaryKeySelective(failedOpenList);
				    	results="openFailF";	//开箱失败，重新尝试
						return results;
				    }
				}
				
				
				
			    //step 3 判断是否补交
			    PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
			 	ps_example.createCriteria().andCustomeridEqualTo(in.CustomerID)
			 	                            .andPackageidEqualTo(in.PackageID).andPaystatusEqualTo(Byte.parseByte(SysDict.PAY_Status_2));
			    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
				Float  moneyTotal=0f;
			    if(CollectionUtils.isEmpty(ptreadypackageEntitysList)){
				    String sCheckCharge = businessModelMap.get(Constant.BT_CFG_NAME_CHARGESWITCH);
					if (StringUtils.isNotEmpty(sCheckCharge) ){
							String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
							BoxEntity boxEntity = boxExDao.selectByPrimaryKey(terminalID, boxid);//查询收费箱门的类型
							StoreInRecordEx storeInRecordEx = storeInRecordExDao.selectByRequest(terminalID, boxid, openBoxCode);//查询在箱记录表 （根据卡号）
							if(storeInRecordEx != null){
								SimpleDateFormat timetype = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date storeintime= storeInRecordEx.getStoreintime();
								Date nowDate= DateUtils.nowDate();
								Integer boxtypecode= boxEntity.getBoxtypecode();
								Integer chargeModeInt= Integer.parseInt(chargeMode);
								logger.info("3.1   调用存储过程准备参数   InParamAPCustomerJBGOpenBoxTake   storeintime="+storeintime+" nowDate="+nowDate.toLocaleString()+" boxtypecode="+boxtypecode+" chargeModeInt="+chargeModeInt);
							    moneyTotal = punishDao.cost(storeintime,nowDate, boxtypecode, chargeModeInt);//应收金额
								Float  moneyPrepaid = storeInRecordEx.getMoney();//预支付
								Float  moneyremain = (moneyTotal-moneyPrepaid)*100;//超时还要支付金额=应收金额-预支付
								logger.info("3.2    调用存储过程获取金额    InParamAPCustomerJBGOpenBoxTake moneyTotal="+moneyTotal+"  moneyPrepaid="+moneyPrepaid +"  moneyremain="+moneyremain);
								if(moneyremain>0){ //返回要支付的金额
									ArrayList<String> list = new ArrayList<String>();
									list.add(boxid.toString());
									list.add(moneyremain.toString());
									list.add(timetype.format(storeintime));
									list.add(timetype.format(nowDate));
									return  list.toString();//返回支付信息
								}else{	//处理支付状态为 已取支付了 
									//============Ptreadypackage====================
								    PtreadypackageEntityExample ps_examples=new PtreadypackageEntityExample();
								 	ps_examples.createCriteria().andCustomeridEqualTo(in.CustomerID).andPackageidEqualTo(in.PackageID);
								    List<PtreadypackageEntity>	ptreadypackageEntitysLists=ptreadypackageEntityDao.selectByExample(ps_examples);
								    
								    if(CollectionUtils.isEmpty(ptreadypackageEntitysLists)){
								    	 throw new EduExceptions(" AppPayBackServiceImpl take doBusiness   ptreadypackageEntitysList  is null 订单不存在      ");
								    }
								    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysLists.get(0);
								    String   paystatus= PtreadypackageEntitys.getPaystatus().toString();//'0:未支付 , 1：已支付  , 2: 取支付成功 ',
								    if(!paystatus.equals(SysDict.PAY_Status_1)){
								    	 throw new EduExceptions(" AppPayBackServiceImpl take doBusiness   no prepaied ");
								    }
							 	    //更新订单支付状态
								    PtreadypackageEntitys.setPaystatus(Byte.parseByte(SysDict.PAY_Status_2));
								    //BigDecimal BigDecimals=new BigDecimal(in.TransactionAmt);
								    //PtreadypackageEntitys.setHireamt(PtreadypackageEntitys.getPayamt().add(BigDecimals));
							 	 	ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
							}
					}
								//logger.info("3.3  返回参数  InParamAPCustomerJBGOpenBoxTake 剩余金额  moneyremain="+moneyremain); 
			    }else{
			    	String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
					BoxEntity boxEntity = boxExDao.selectByPrimaryKey(terminalID, boxid);//查询收费箱门的类型
					StoreInRecordEx storeInRecordEx = storeInRecordExDao.selectByRequest(terminalID, boxid, openBoxCode);//查询在箱记录表 （根据卡号）
					if(storeInRecordEx != null){
					    	Date storeintime= storeInRecordEx.getStoreintime();
							Date nowDate= DateUtils.nowDate();
							Integer boxtypecode= boxEntity.getBoxtypecode();
							Integer chargeModeInt= Integer.parseInt(chargeMode);
							logger.info("3.1   调用存储过程准备参数   InParamAPCustomerJBGOpenBoxTake   storeintime="+storeintime+" nowDate="+nowDate.toLocaleString()+" boxtypecode="+boxtypecode+" chargeModeInt="+chargeModeInt);
						    moneyTotal = punishDao.cost(storeintime,nowDate, boxtypecode, chargeModeInt);//应收金额
					}
			    }
 
 
			   //step 4    更新记录
		    	Date storeInTime   = in.StoredTime;
			    StoreInRecordEntity storeInRecordEntity = storeInRecordExDao.selectByPrimaryKey(terminalID, boxid, openBoxCode, storeInTime);
				//更新存物记录
				if (null !=storeInRecordEntity){
					try {	
						storeInRecordEntity = new StoreInRecordEntity();
						storeInRecordEntity.setTerminalid(terminalID);
						storeInRecordEntity.setBoxid(boxid);
						storeInRecordEntity.setUsercardid(openBoxCode);
						//storeInRecordEntity.setStoreintime(storeInTime);
						storeInRecordEntity.setState((byte)1);//0:在箱 1：已取   TODO
						storeInRecordEntity.setStoreintime(storeInTime);
						storeInRecordEntity.setMoney(moneyTotal);
						storeInRecordExDao.updateByPrimaryKeySelective(storeInRecordEntity);
					} catch (Exception e) {
						logger.error(e.getMessage());
						logger.error("更新存物记录   AppOpenServiceImpl InParamAPCustomerJBGOpenBoxTake  fail the reason is:"+e );
					}
				}else{
					logger.error("AppOpenServiceImpl InParamAPCustomerJBGOpenBoxTake fail the reason is null ==storeInRecordEntity    " );
					return results;	
				}
				//Step.5 写取物记录
				try {
					TakeOutRecordEntity takeOutRecordEntity = new TakeOutRecordEntity();
					takeOutRecordEntity.setTerminalid(terminalID);
					takeOutRecordEntity.setBoxid(boxid);
					takeOutRecordEntity.setUsercardid(openBoxCode);
					takeOutRecordEntity.setStoreintime(storeInTime); 
					takeOutRecordEntity.setTaketime(new Date());
					takeOutRecordEntity.setMoney(moneyTotal);
					takeOutRecordEntity.setRealmoney(moneyTotal);
					takeOutRecordEntity.setType(2);     //1中途取；2正常取；3管理取；4超时取；5远程开箱取物  7卡取消取物
					takeOutRecordEntity.setCashierno("0");
					takeOutRecordEntity.setMakeopcode(null);
					takeOutRecordEntityDao.insert(takeOutRecordEntity);
				    PtreadypackageEntityExample ps_examples=new PtreadypackageEntityExample();
				 	ps_examples.createCriteria().andCustomeridEqualTo(in.CustomerID).andPackageidEqualTo(in.PackageID);
				    List<PtreadypackageEntity>	ptreadypackageEntitysLists=ptreadypackageEntityDao.selectByExample(ps_examples);
				    if(!CollectionUtils.isEmpty(ptreadypackageEntitysLists)){
				    	PtreadypackageEntity PtreadypackageEntitys=ptreadypackageEntitysLists.get(0);
				    	PtreadypackageEntitys.setStoreintime(takeOutRecordEntity.getTaketime());
				    	PtreadypackageEntitys.setOpenstatus((byte) 2);
						ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
				    }
		 
				} catch (Exception e) {
					logger.error(e.getMessage());
					logger.error("  保存取物记录   AppOpenServiceImpl InParamAPCustomerJBGOpenBoxTake   fail the reason is:"+e );
				}
				
				
		        //6. 开箱下行业务
				InParamAPDepositOpenBox p1 = new InParamAPDepositOpenBox();
		        p1.OpenType     = "9";//开箱类型 9 未知  0 存 1 取
		        p1.OpenID       = in.CustomerID;
		        p1.BoxNo        = in.BoxNo;
		        p1.TerminalNo   = in.TerminalNo;
		        p1.OccurTime    = new  Timestamp(new Date().getTime());
		        p1.TradeWaterNo = in.TradeWaterNo;
		        p1.CheckCode    = "";
		        try{
			    	PushBusinessProxy.doBusiness(p1);
			    }catch(Exception ex){
				    PtreadypackageEntityExample ps_example2=new PtreadypackageEntityExample();
				    ps_example2.createCriteria().andCustomeridEqualTo(in.CustomerID)
				 	                            .andPackageidEqualTo(in.PackageID).andPaystatusEqualTo(Byte.parseByte(SysDict.PAY_Status_2)) ;
				    List<PtreadypackageEntity>	ptreadypackageEntitysList2=ptreadypackageEntityDao.selectByExample(ps_example2);
				    if(CollectionUtils.isEmpty(ptreadypackageEntitysList2)){
				    	 throw new EduExceptions(" AppOpenServiceImpl  doBusiness   ptreadypackageEntitysList  is null 订单不存在或者没有支付      ");
				    }
				    PtreadypackageEntity PtreadypackageEntitys= ptreadypackageEntitysList2.get(0);
				    int i = PtreadypackageEntitys.getLimitcountstake();
				    i++;
				    PtreadypackageEntitys.setLimitcountstake(i);
				    PtreadypackageEntitys.setOpenstatus((byte) 3);
					ptreadypackageEntityDao.updateByPrimaryKeySelective(PtreadypackageEntitys);
					results = "openFailS";
				    return results;
			    }
			  }
		   }catch(Exception ex ){
	        	logger.error("   AppOpenServiceImpl  doBusiness(InParamAPCustomerJBGOpenBoxTake in) "+ex);
	      	    throw new EduExceptions(ex.getMessage());
	       }
	       return results; 
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
	 * 微信二次支付流水号更新(取)
	 * @param in.setTradewaternotwo
	 * @return
	 */
	@Override
	public String doBusiness(InParamAPCustomerJBGPayOpenBoxTake in) throws EduExceptions {
		PtreadypackageEntityExample tradeWaterNo_example = new PtreadypackageEntityExample();
		tradeWaterNo_example.createCriteria().andTerminalnoEqualTo(in.TerminalNo).andPackageidEqualTo(in.PackageID).andPaystatusEqualTo(Byte.parseByte(SysDict.PAY_Status_1));
		List<PtreadypackageEntity>	resultEntitysList=ptreadypackageEntityDao.selectByExample(tradeWaterNo_example);
		if(!CollectionUtils.isEmpty(resultEntitysList)){
			PtreadypackageEntity resultEntity=resultEntitysList.get(0);
			resultEntity.setTradewaternotwo(in.TradeWaterNo);
			ptreadypackageEntityDao.updateByPrimaryKeySelective(resultEntity);
			return "0";
		}else{
			logger.error("AppOpenServiceImpl doBusiness(InParamAPCustomerJBGPayOpenBoxTake in) fail: 微信二次支付流水号更新失败！ ");
			return null;
		}
	}
	
	/**
     * 中途取开箱不结算   
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	@Override
	public String doBusiness(InParamAPCustomerInTimeOpenBox in) throws EduExceptions {
		// TODO Auto-generated method stub
		return null;
	}
}



















