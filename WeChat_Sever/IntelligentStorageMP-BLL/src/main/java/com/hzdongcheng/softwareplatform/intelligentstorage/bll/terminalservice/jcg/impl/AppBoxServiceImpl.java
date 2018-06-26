package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant.*;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerBoxStatusQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGInBoxQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGInBoxQryTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamCustomerOpenFailQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerBoxStatusQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGInBoxQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGInBoxQryTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamCustomerOpenFailQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.StoreInRequestsTemp;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.Utils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxAppExEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.UserExDao;
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
 * @Description: 
 * @ClassName:  AppBoxServiceImpl
 * @date 2017年8月16日 下午13:09:00
 */
@Transactional
@Service("iAPPBoxService")
public class AppBoxServiceImpl implements IAPPBoxService{
	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(AppBoxServiceImpl.class);
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
	//查询可用箱子       存
	@Override
	public List<OutParamAPCustomerBoxStatusQry> doBusiness(InParamAPCustomerBoxStatusQry in) throws EduExceptions {
	        List<OutParamAPCustomerBoxStatusQry> outList = new LinkedList<OutParamAPCustomerBoxStatusQry>();
	        try{
	        	//1.验证输入参数是否有效，如果无效返回-1。
				if (StringUtils.isEmpty(in.CustomerID))
					throw new EduExceptions("  in.CustomerID is null  用户CustomerID不存在! ");
				UserEntityExample example = new UserEntityExample();
				example.createCriteria().andCustomeridEqualTo(in.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
				List<UserEntity> userEntityList = userExDao.selectByExample(example);
				if(CollectionUtils.isEmpty(userEntityList)){
					 return outList;
				}
				UserEntity  userEntity=userEntityList.get(0);
		        //TODO  ??????   判断设备是否在线    ??????
		        /**
			         if(!MemCacheManager.getInstance().isOnline(customer.TerminalNo)){
						//设备离线
			        	throw new EduExceptions(ErrorCode.ERR_DEVICENETWORKABNORMAL);
					 }
		        */
		        if(StringUtils.isEmpty(userEntity.getTerminalno())){
		        	return outList;
			    }
			 	TerminalEntity  terminalEntitys= terminalExDao.selectByPrimaryKey(userEntity.getTerminalno());
				if (null ==terminalEntitys)
					throw new EduExceptions(" terminalEntitys   TerminalNo="+userEntity.getTerminalno()+" not find  不存在  !");
				String Networkstate = terminalEntitys.getNetworkstate().toString();
				if(Networkstate.equals("1")){
					throw new EduExceptions("AppBoxServiceImpl doBusiness(InParamAPCustomerBoxStatusQry in) terminalEntitys   Networkstate=1    设备不在线  !");
				}
			 	String	 displayname=terminalEntitys.getDisplayname();//1001
			 	String   areacodes=	terminalEntitys.getAreacode();    //04  总部
			 	String	 address=terminalEntitys.getAddress();        //安装地址
			 	String	 Terminalid=terminalEntitys.getTerminalid();   //安装地址

			 	// step 1  应该在显示箱子之前
			 	// 清理未付款  并超时的箱子 (2min)
			 	PtreadypackageEntityExample PaystatusClear_example=new PtreadypackageEntityExample();
			 	PaystatusClear_example.createCriteria().andPaystatusEqualTo(Byte.parseByte("0")).andExpiredtimeLessThanOrEqualTo(new Date());
			 	ptreadypackageEntityDao.deleteByExample(PaystatusClear_example);
			 	
			 	// step 2 选项时自动清理该CustomerID之前Paystatus为0的记录（无论有无超时）
			 	/*PtreadypackageEntityExample customerIdClear_Example=new PtreadypackageEntityExample();
			 	customerIdClear_Example.createCriteria().andCustomeridEqualTo(in.CustomerID).andPaystatusEqualTo(Byte.parseByte("0"));
			 	ptreadypackageEntityDao.deleteByExample(customerIdClear_Example);*/
			 	
			 	//step 2  TODO 只获取空闲的箱子
				List<BoxAppExEntity> list = terminalExDao.selectFreeOpenBoxTerminalIDAndBoxTypeCodeApp(userEntity.getTerminalno());
	            if(!CollectionUtils.isEmpty(list)){
	            	for(int i=0;i<list.size();i++){
	            		int  ishave=-1;
	            		BoxAppExEntity  boxEntitys=list.get(i);
	            		//1  判断箱门是否为取失败箱门,如果是箱门状态为2
	            		PtreadypackageEntityExample openStatus3_example=new PtreadypackageEntityExample();
	            		List<Byte> openStatusvalues=new ArrayList<Byte>();//如果OpenStatus为1或3
	            		openStatusvalues.add(Byte.parseByte(SysDict.OPEN_Status_1));
	            		openStatusvalues.add(Byte.parseByte(SysDict.OPEN_Status_3));
	            		openStatus3_example.createCriteria()
	            		.andBoxnoEqualTo(boxEntitys.getBoxid().toString())
	            		.andTerminalnoEqualTo(userEntity.getTerminalno())
	            		.andOpenstatusIn(openStatusvalues);
	    			 	List<PtreadypackageEntity>	openStatus3Lists=ptreadypackageEntityDao.selectByExample(openStatus3_example);
	    			 	if(!CollectionUtils.isEmpty(openStatus3Lists)){
		    				   ishave=2;
		    			}else{
		    				
	            	    //2  判断硬件投币机是否已经锁定了该箱子， 锁定说明已经被别人占用了
		    				String  terminalId_boxId=terminalEntitys.getTerminalid()+"_"+boxEntitys.getBoxid();
		            		StoreInRequestsTemp storeInRequestsTemps= CoinJCGServiceImpl.TerminalIdBoxTempMap.get(terminalId_boxId);
		            		if(null!=storeInRequestsTemps && storeInRequestsTemps.isIsovertime()){//存在超时的
			            		logger.info("  硬件清理缓存     storeInRequestsTemps  terminalId_boxId="+terminalId_boxId+"  storeInRequestsTemps="+storeInRequestsTemps);
		            			 CoinJCGServiceImpl.TerminalIdBoxTempMap.remove(terminalId_boxId);//清理临时对象
		            		}
		            		//箱子已经被占用
		            		if( null!=storeInRequestsTemps && ! storeInRequestsTemps.isIsovertime() 
		            				|| !check_StoreInRecord(terminalEntitys.getTerminalid(),boxEntitys.getBoxid()) ) {
		            			   ishave=1;
		            		}else{
			                        //2 付款和锁定的
				            		PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
				            		List<Byte> values=new ArrayList<Byte>();
				            		values.add((byte)0);
				            		values.add((byte)1);
				    			 	ps_example.createCriteria().andBoxnoEqualTo(boxEntitys.getBoxid().toString()).andTerminalnoEqualTo(userEntity.getTerminalno())
			 	    			 	                           .andPaystatusIn(values);
				    			 	List<PtreadypackageEntity>	ptreadypackageEntitysList1=ptreadypackageEntityDao.selectByExample(ps_example);
					    			if(!CollectionUtils.isEmpty(ptreadypackageEntitysList1)){
					    				PtreadypackageEntity msg = new PtreadypackageEntity();
					    				msg = ptreadypackageEntitysList1.get(0);
					    				//if(msg.getPaystatus().equals((byte)0)){
					    				if(msg.getPaystatus() == Byte.parseByte("0")){
					    					if(msg.getCustomerid().equals(in.CustomerID)){
					    						ishave=0;
					    					}else{
					    						ishave=1;
					    					}
					    				}else{
					    					ishave=1;
					    				}
					    			}
		            		}
		    			}
	    			 	
	    			 	

	                	   
	                	   
	                	   
	            		OutParamAPCustomerBoxStatusQry out = new OutParamAPCustomerBoxStatusQry();
	            		out.TerminalName =  displayname;
	    	            out.TerminalNo   =  Terminalid;
	    	            out.BoxName      =  boxEntitys.getDispalyname();
	    	            out.BoxNo        =  boxEntitys.getBoxid().toString();
	    	            out.BoxStatus    =  boxEntitys.getStatus().toString();//
	    	            out.BoxStatusName = BoxType.getName(boxEntitys.getStatus());//0:正常 1：锁定 2:故障     
	    	            out.Boxtapystatus=(ishave);
	    	            
	    	            Byte articles= boxEntitys.getArticle();//'0 无物   1 有物   ' 
	    	            
	    	            //判断Boxtapystatus为1时 箱门锁定
	    	            if(out.Boxtapystatus==1  || (null!=articles && articles == Byte.parseByte("1"))){
	    	            	out.BoxStatus    =  BoxType.BoxType_status_1.getValue().toString();//
            	            out.BoxStatusName = BoxType.getName(BoxType.BoxType_status_1.getValue());//页面  0绿色可用，1淡黄占用，2灰色故障      
	    	            }
 
	    	            if(out.Boxtapystatus==2){
	    	            	out.BoxStatus    =  BoxType.BoxType_status_2.getValue().toString();//
            	            out.BoxStatusName = BoxType.getName(BoxType.BoxType_status_2.getValue());//页面  0绿色可用，1淡黄占用，2灰色故障      
	    	            }
 
	    	            //out.Paystatus =Integer.parseInt("1");
	    	            if(BoxType.BoxType_status_0.getValue()==boxEntitys.getStatus()){
	    	            	// `state` tinyint(4) NOT NULL COMMENT '0:在箱 1：已取',
	        	            List<StoreInRecordEntity> selectInByBoxId=terminalExDao.selectInByBoxId(0,boxEntitys.getBoxid(), Terminalid);
	        	            if(!CollectionUtils.isEmpty(selectInByBoxId)){
	        	            	out.BoxStatus    =  BoxType.BoxType_status_1.getValue().toString();//
	            	            out.BoxStatusName = BoxType.getName(BoxType.BoxType_status_1.getValue());//页面  0绿色可用，1淡黄占用，2灰色故障                                                      
	        	            }
		    	        }
	    	            out.BoxType       = boxEntitys.getBoxtypecode().toString();
	    	            out.BoxTypeName   = boxSizeEntityDao.selectByPrimaryKey(boxEntitys.getBoxtypecode()).getBoxtypename();
	    	            out.DeskBoxNo     = 0;
	    	            out.DeskNo        = 0;
	    	            outList.add(out);
	            	}
	            }
	        }catch(Exception ex ){
	        	logger.error("   AppBoxServiceImpl   List<OutParamAPCustomerBoxStatusQry>    "+ex);
	        	throw new EduExceptions(ex.getMessage());
	        }
	        return outList;
	 
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
	
	
	//用户查看租箱记录  存
	@Override
	public List<OutParamAPCustomerJBGInBoxQry> doBusiness(InParamAPCustomerJBGInBoxQry ins) throws EduExceptions {
		   List<OutParamAPCustomerJBGInBoxQry> outList = new  LinkedList<OutParamAPCustomerJBGInBoxQry>();
		   try{
			//1.验证输入参数是否有效，如果无效返回-1。
			/*if (StringUtils.isEmpty(ins.CustomerID))
				throw new EduExceptions("  in.CustomerID is null 用户CustomerID不存在!  ");*/
		    if (StringUtils.isEmpty(ins.CustomerID))
					throw new EduExceptions("in.CustomerID is null 用户CustomerID不存在!");
			   
			UserEntityExample example = new UserEntityExample();
			example.createCriteria().andCustomeridEqualTo(ins.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
			//example.createCriteria().andUsercardidEqualTo("0014418702");
			List<UserEntity> userEntityList = userExDao.selectByExample(example);
			if(CollectionUtils.isEmpty(userEntityList)){
				 return outList;
			}
			UserEntity  userEntity=userEntityList.get(0);
			
			if(StringUtils.isEmpty(ins.TerminalNo)){
				ins.TerminalNo = userEntity.getTerminalno();
	        }
			
			TerminalEntity terminalEntitys=	new TerminalEntity();
		 	if (StringUtils.isNotEmpty(ins.TerminalNo))	{
		 		terminalEntitys= terminalExDao.selectByPrimaryKey(ins.TerminalNo);
			 	if (null ==terminalEntitys)
					throw new EduExceptions(" terminalEntitys   in.TerminalNo="+ins.TerminalNo+" not find 不存在 !");
	        }
 
		 	String	 displayname=terminalEntitys.getDisplayname();//1001
		 	String   areacodes=	terminalEntitys.getAreacode();    //04  总部
		 	String	 address=terminalEntitys.getAddress();        //安装地址
		 	String	 Terminalid=terminalEntitys.getTerminalid();   //安装地址
		 	
		 	//step 1   查找订单状态  
 
		 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
		 	ps_example.createCriteria().andCustomeridEqualTo(ins.CustomerID);
		    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
		 	if( CollectionUtils.isEmpty(ptreadypackageEntitysList)){
		 		throw new EduExceptions(" PtreadypackageEntity null  没有开箱记录   ");
		 	}
		 	
		   for(int i=0;i<ptreadypackageEntitysList.size();i++){
			    OutParamAPCustomerJBGInBoxQry out = new OutParamAPCustomerJBGInBoxQry();
			    
			    PtreadypackageEntity PtreadypackageEntitys=ptreadypackageEntitysList.get(i);
			    
	            out.TerminalNo = PtreadypackageEntitys.getTerminalno();
	            out.TerminalName = displayname;
	            out.Location = Utils.locations(areacodes,displayname,address) ;
	            out.BoxNo = PtreadypackageEntitys.getBoxno();
	            out.PackageID = PtreadypackageEntitys.getPackageid();
	            out.PackageStatus =PtreadypackageEntitys.getPackagestatus();
	            out.TradeWaterNo = PtreadypackageEntitys.getTradewaterno();
	            out.CustomerID = PtreadypackageEntitys.getCustomerid();
	            out.CustomerMobile = PtreadypackageEntitys.getCustomermobile();
	            out.CustomerName = PtreadypackageEntitys.getCustomername();
	            int a= 1;
	            out.HireAmt = a;
	            out.PayedAmt = PtreadypackageEntitys.getPayamt().intValue(); //预付费金额
	            out.Desc = PtreadypackageEntitys.getRemark();
	            outList.add(out);
	        }
		   }catch(Exception ex ){
	        	logger.error("   AppBoxServiceImpl List<OutParamAPCustomerJBGInBoxQry>    "+ex);
	        	throw new EduExceptions(ex.getMessage());
	       }
	       return outList;
	}
 
	//用户查看租箱记录  取
	@Override
	public List<OutParamAPCustomerJBGInBoxQryTake> doBusiness(InParamAPCustomerJBGInBoxQryTake ins) throws EduExceptions {
		   List<OutParamAPCustomerJBGInBoxQryTake> outList = new  LinkedList<OutParamAPCustomerJBGInBoxQryTake>();
		   try{
					//1.验证输入参数是否有效，如果无效返回-1。
					/*if (StringUtils.isEmpty(ins.CustomerID))
						throw new EduExceptions("  in.CustomerID is null 用户CustomerID不存在!  ");*/

			   if (StringUtils.isEmpty(ins.CustomerID))
							throw new EduExceptions("in.CustomerID is null 用户CustomerID不存在!");
					   
					UserEntityExample example = new UserEntityExample();
					example.createCriteria().andCustomeridEqualTo(ins.CustomerID).andSourceEqualTo(Byte.parseByte("1"));
					//example.createCriteria().andUsercardidEqualTo("0014418702");
					List<UserEntity> userEntityList = userExDao.selectByExample(example);
					if(CollectionUtils.isEmpty(userEntityList)){
						 return outList;	
					}
					UserEntity  userEntity=userEntityList.get(0);
					
					if(StringUtils.isEmpty(ins.TerminalNo)){
						ins.TerminalNo = userEntity.getTerminalno();
			        }
					
					TerminalEntity terminalEntitys=	new TerminalEntity();
				 	if (StringUtils.isNotEmpty(ins.TerminalNo))	{
				 		terminalEntitys= terminalExDao.selectByPrimaryKey(ins.TerminalNo);
					 	if (null ==terminalEntitys)
							throw new EduExceptions(" terminalEntitys   in.TerminalNo="+ins.TerminalNo+" not find 不存在 !");
			        }
		 
				 	String	 displayname=terminalEntitys.getDisplayname();//1001
				 	String   areacodes=	terminalEntitys.getAreacode();    //04  总部
				 	String	 address=terminalEntitys.getAddress();        //安装地址
				 	String	 Terminalid=terminalEntitys.getTerminalid();   //安装地址
				 	//step 1查询是否存在	取开箱失败（OpenStatus=3）故障箱
				 	
				 	PtreadypackageEntityExample openFail_example=new PtreadypackageEntityExample();
				 	OutParamAPCustomerJBGInBoxQryTake outOpenFail = new OutParamAPCustomerJBGInBoxQryTake();
				 	openFail_example.createCriteria()
				 		.andTerminalnoEqualTo(ins.TerminalNo) 
				 		.andCustomeridEqualTo(ins.CustomerID)
				 		.andOpenstatusEqualTo(Byte.parseByte(SysDict.OPEN_Status_3));
				 	List<PtreadypackageEntity>	openFailEntitysLists=ptreadypackageEntityDao.selectByExample(openFail_example);
				 	if( !CollectionUtils.isEmpty(openFailEntitysLists)){
				 		for(int i=0;i<openFailEntitysLists.size();i++){
				 			PtreadypackageEntity openFailEntitysList=openFailEntitysLists.get(i);
				 			outOpenFail.FlagNo = 1;   //FlagNo	 1为故障箱;0为正常箱子;
				 			outOpenFail.TerminalNo = openFailEntitysList.getTerminalno();
				 			outOpenFail.TerminalName = displayname;
				 			outOpenFail.Location = Utils.locations(areacodes,displayname,address) ;
				 			outOpenFail.BoxNo =openFailEntitysList.getBoxno().toString();
				 			outOpenFail.CustomerID = openFailEntitysList.getCustomerid();
				 			outOpenFail.StoredTime =  Constant.datetimeFromat.format(openFailEntitysList.getStoreintime());
				 			outOpenFail.PackageID = openFailEntitysList.getPackageid();
				 			outOpenFail.TradeWaterNo = openFailEntitysList.getTradewaterno();
							outList.add(outOpenFail);
				 		}
				 	}
					//step 2 查询订单表
				 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
				 	ps_example.createCriteria().andTerminalnoEqualTo(ins.TerminalNo) 
				 	                           .andCustomeridEqualTo(ins.CustomerID)
				 	                           .andPaystatusEqualTo((byte)1);
				    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
				 	if(CollectionUtils.isEmpty(openFailEntitysLists)&&CollectionUtils.isEmpty(ptreadypackageEntitysList)){
				 		throw new EduExceptions(" PtreadypackageEntity null  没有开箱记录   ");
				 	}
						for(int i=0;i<ptreadypackageEntitysList.size();i++){
						 	OutParamAPCustomerJBGInBoxQryTake out = new OutParamAPCustomerJBGInBoxQryTake();
						    PtreadypackageEntity PtreadypackageEntitys=ptreadypackageEntitysList.get(i);
						    //用户可能对同一个boxid 都存过的记录    我们只查状态为0的
							if(!CollectionUtils.isEmpty(ptreadypackageEntitysList)){
							    out.FlagNo = 1;
							    StoreInRecordEntityExample examples = new StoreInRecordEntityExample();
								examples.createCriteria().andTerminalidEqualTo(ins.TerminalNo)
														.andBoxidEqualTo(Integer.parseInt(PtreadypackageEntitys.getBoxno()))
											            .andUsercardidEqualTo(ins.CustomerID)
											            .andStateEqualTo((byte)0);//0:在箱 1：已取
								example.setOrderByClause("storeInTime desc");
								List<StoreInRecordEntity> records = storeInRecordExDao.selectByExample(examples);
								if(!CollectionUtils.isEmpty(records)){
									StoreInRecordEntity storeInRecordEntity = records.get(0);
									out.FlagNo = 0;
						            out.TerminalNo = storeInRecordEntity.getTerminalid();
						            out.TerminalName = displayname;
						            out.Location = Utils.locations(areacodes,displayname,address) ;
						            out.BoxNo =storeInRecordEntity.getBoxid().toString();
						            out.CustomerID = storeInRecordEntity.getUsercardid();
						            out.StoredTime =  Constant.datetimeFromat.format(storeInRecordEntity.getStoreintime());
						            out.PackageID = PtreadypackageEntitys.getPackageid();
							 		out.TradeWaterNo = PtreadypackageEntitys.getTradewaterno();
								}
								out.TerminalNo = PtreadypackageEntitys.getTerminalno();
					            out.TerminalName = displayname;
					            out.Location = Utils.locations(areacodes,displayname,address) ;
					            out.BoxNo = PtreadypackageEntitys.getBoxno();
					            out.CustomerID = PtreadypackageEntitys.getCustomerid();
					            out.PackageID = PtreadypackageEntitys.getPackageid();
					            out.TradeWaterNo = PtreadypackageEntitys.getTradewaterno();
					            if(PtreadypackageEntitys.getStoreintime()!=null){
					            	out.StoredTime =  Constant.datetimeFromat.format(PtreadypackageEntitys.getStoreintime());
					            }
					            outList.add(out);
							}
				        }
				 	
				    for(int i=0;i<outList.size();i++){
				    	OutParamAPCustomerJBGInBoxQryTake  OutParamAPCustomerJBGInBoxQryTakes=	outList.get(i);
				    	System.out.println("----"+OutParamAPCustomerJBGInBoxQryTakes.getFlagNo());
				    }
				    
		   }catch(Exception ex ){
	        	logger.error("   AppBoxServiceImpl List<OutParamAPCustomerJBGInBoxQryTake>    "+ex);
	        	throw new EduExceptions(ex.getMessage());
	       }

	       return outList;
		 
	}

	 /**
     * 开箱失败信息查询
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	@Override
	public OutParamCustomerOpenFailQry doBusiness(InParamCustomerOpenFailQry in) throws EduExceptions {
		OutParamCustomerOpenFailQry out = new OutParamCustomerOpenFailQry();
		PtreadypackageEntityExample qry_example=new PtreadypackageEntityExample();
	 	qry_example.createCriteria().andTerminalnoEqualTo(in.TerminalNo)
	 	                           .andCustomeridEqualTo(in.CustomerID)
	 	                           .andBoxnoEqualTo(in.BoxNo)
	 	                           .andPackageidEqualTo(in.PackageID);
		List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(qry_example);
		if(!CollectionUtils.isEmpty(ptreadypackageEntitysList)){
			PtreadypackageEntity relust=ptreadypackageEntitysList.get(0);
			out.TerminalNo=relust.getTerminalno();
			out.BoxNo = relust.getBoxno();
			out.Paystatus = relust.getPaystatus().toString();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(relust.getOpenstatus()==1){
				String storedTime=sdf.format(relust.getOrdertime());  
				out.StoredTime =  storedTime;
			}else{
				String storedTime=sdf.format(relust.getStoreintime());  
				String takeOutTime=sdf.format(relust.getLastmodifytime()); 
				out.StoredTime = storedTime;
				out.TakeOutTime = takeOutTime;
			}
			
		}
		return out;
	}
}



















