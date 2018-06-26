package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamAlarmNoticesRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInCheckRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamAlarmNoticesRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInCheckRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.HttpClient4Guotong;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant.SysDict;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.DateUtiles;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.StoreInRequestsTemp;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentesEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AreaExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentesDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackagefailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AppPartnerEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackageEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackagefailEntityDao;

import net.sf.json.JSONObject;

/**
 * @author ysk
 * @Description: 投币机 
 * @ClassName:  CoinJCGServiceImpl
 * @date 2017年8月16日 下午13:09:00
 * 
 */
@Transactional
@Service("CoinJCGService")
public class CoinJCGServiceImpl extends JCGServiceImpl{
	//private final static Logger logger = Logger.getLogger(CoinJCGServiceImpl.class);
	public static Log4jUtils logger = Log4jUtils.createInstanse(CoinJCGServiceImpl.class);


	public static ConcurrentMap<String, Object>  TerminalIdMap = new ConcurrentHashMap<String, Object>();  	//app也要调用该该类
	public static ConcurrentMap<String, StoreInRequestsTemp>  TerminalIdBoxTempMap = new ConcurrentHashMap<String, StoreInRequestsTemp>();  
	@Autowired
	protected PaymentesDao paymentesDao;
	@Autowired
	private PtreadypackageEntityDao ptreadypackageEntityDao;
	@Autowired
	private PtreadypackagefailEntityDao ptreadypackagefailEntityDao;
	@Autowired
	private AppPartnerEntityDao appPartnerEntityDao;
	@Autowired
	private AreaExDao areaExDao;
	@Autowired
	private StoreInRecordExDao storeInRecord;
	/**
	 *  存入校验请求
	 */ 
	@Override
	public OutParamStoreInCheckRequest storeCheckRequset(InParamStoreInCheckRequest inRequest) {
		//初始返回值
		OutParamStoreInCheckRequest outParam = new OutParamStoreInCheckRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			
		    /*inRequest=new InParamStoreInCheckRequest();
			inRequest.setTerminalID("674523011032547665870900");
			inRequest.setOpenBoxCode("1");
			inRequest.setBoxTypeCode(1); */
			
			String   terminalID  = inRequest.getTerminalID(); 
			String   openBoxCode = inRequest.getOpenBoxCode();//设备号  openBoxCode 卡号 
			Integer  boxTypeCode = inRequest.getBoxTypeCode();
			logger.info("1 存入校验请求参数   设备号："+terminalID+" 卡号："+openBoxCode+" 箱类型:"+boxTypeCode);
 
			/** 新 Step.1   加载规则   */
			//业务规则
			TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);//根据设备号查询业务编码
			Integer businessCode = terminalEntity.getBusinesscode();
			//根据业务编码获取业务模型
			Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
			if (businessModelMap == null){
				return outParam;
			}
 
			/** 新 Step.2   先计算金额 */
			PaymentesEx   paymentesEx=null;
			String sCheckCharge = businessModelMap.get(Constant.BT_CFG_NAME_CHARGESWITCH);
			if (StringUtils.isNotEmpty(sCheckCharge) ){
				String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
				//收费标准chargeCode  0：vip更衣柜收费标准   1 更衣柜收费标准 2 贵重物品收费标准   
				//收费模式 usedState  0:按小时收费 1：按时段收费 2：按天收费
				//boxTypeCode           1 小   2中  3大
				//折扣=实际售价/原售价×100%
				//int currentTime=DateUtils.nowDate().getHours();
				paymentesEx=paymentesDao.getMoneyByBoxTypePrepay(Integer.parseInt(chargeMode), boxTypeCode);
				logger.info("2      存入校验请求返回参数   计算预支付金额    getMoney="+paymentesEx.getMoney());
			}
			/** 新 Step.3   获取箱号 同时对柜号枷锁   */
            Byte  boxID= getBoxID(terminalID,boxTypeCode,  openBoxCode) ;
            if(null !=boxID){
            	  logger.info("3     存入校验请求返回参数    获取随机箱号        boxID="+boxID);
            	  outParam.setBoxID(boxID);//箱号
            	  outParam.setPreMoney(paymentesEx.getMoney().intValue()); //钱
            	  //outParam.setPreMoney(65500); //钱
            }else{
            	outParam.setErrorCode(JCGErrorCode.ERR_BOX_NOT_EMPTY);
            	/*TerminalEntity tmpTerminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
				outParam.setDisplayName(tmpTerminalEntity.getDisplayname());//设置显示柜号
				String areaShortname = "";
				{
					String areaCode = tmpTerminalEntity.getAreacode();
					areaShortname = areaEntityDao.selectByPrimaryKey(areaCode).getAreashortname();
					areaShortname = areaShortname.substring(areaShortname.length()-1);
				}
				outParam.setAreaCode(areaShortname);  //设置区域简称
				outParam.setBoxID((byte)0);           //设置箱号
				outParam.setDisplayBoxName("");       //设置显示箱号
				*/
            }
     
		} catch (Exception e) {
			outParam.setErrorCode(JCGErrorCode.ERR_BOX_NOT_EMPTY);
			logger.debug("异常："+e.getMessage());
			logger.error("4  error  CoinJCGServiceImpl  storeCheckRequset  fail the reason is:  "+e );
		}
		return outParam;
	}
	 
   public Byte  getBoxID(String terminalId,int boxTypeCode,String  openBoxCode) {
	        //打印处理
	        if(!TerminalIdMap.isEmpty()){
	    	   for (Map.Entry<String, Object> entry : TerminalIdMap.entrySet()) {
	    		   logger.info("TerminalIdMap    key= " + entry.getKey() + " and value= " + entry.getValue());
		       }  
	        }
	        if(!TerminalIdBoxTempMap.isEmpty()){
	    	   for (Map.Entry<String, StoreInRequestsTemp> entry : TerminalIdBoxTempMap.entrySet()) {
	    		   logger.info("TerminalIdBoxTempMap   key= " + entry.getKey() + " and value= " + entry.getValue());
		       }  
	        }
	  
	        if(!TerminalIdMap.containsKey(terminalId)){
	        	TerminalIdMap.put(terminalId, new Object());
	        }
	        //TODO 问题1   如果投币机锁定完terminalId之后   箱门随机分配 完成   但是没有确认请求   这个时候app也请求  有可能得到相同boxID的 
	        //TODO 问题2   如果投币机锁定完terminalId之后   箱门随机分配 完成   但是有确认 但是超时   
			synchronized (TerminalIdMap.get(terminalId)) {
				//查询空闲箱（根据终端号）
				List<BoxEntity> list = terminalExDao.selectFreeOpenBoxTerminalIDAndBoxTypeCode(terminalId,boxTypeCode);
	            if(!CollectionUtils.isEmpty(list)){
	            	 Integer boxId = null;
	            	 Boolean flags=false;
	            	 
	            	 //第一优先级  查找没有锁箱子
	                 for(int i=0;i<list.size();i++){
	                	 BoxEntity  boxEntity=list.get(i);
	                	 boxId = list.get(i).getBoxid() ; 
	                	 
	                	 Byte articles= boxEntity.getArticle();//'0 无物   1 有物   ' 
	                	 if(null!=articles && articles == Byte.parseByte("1")){
	                		// logger.info(" 物品检测 terminalId=" + terminalId+"  boxId=" + boxId+"  articles=" + articles);
	                		 continue;
	                	 } 
	                	 
	                	 String  terminalId_boxId=terminalId+"_"+boxId;
	                	 if( check_Ptreadypackage(terminalId,boxId)  //判断预订单表 
	                	 	// &&  check_StoreInRecord(terminalId,boxId)     //判断存记录表
	                		 && !TerminalIdBoxTempMap.containsKey(terminalId_boxId)){//找到没有枷锁的boxId
	                		 
	                		 StoreInRequestsTemp storeInRequestsTemps=new  StoreInRequestsTemp(terminalId,boxEntity.getBoxid(),openBoxCode);
	                		 TerminalIdBoxTempMap.put(terminalId_boxId, storeInRequestsTemps);//TODO 请求确认后 要删除该记录
	                		 flags=true;
	                		 
	                		 logger.info("  第一优先级  查找没有锁箱子     terminalId_boxId= " + terminalId_boxId);
	                		 
	                		 
	                     	 break;
	                     } 
	                 }
	                 
	                 //第二优先级   查找已锁箱子并且是超时的
	                 if(!flags){
	                	 for(int i=0;i<list.size();i++){
	                    	 BoxEntity  boxEntity=list.get(i);
	                    	 boxId = boxEntity.getBoxid(); 
	                    	 
	                    	 Byte articles= boxEntity.getArticle();//'0 无物   1 有物   ' 
		                	 if(null!=articles && articles == Byte.parseByte("1")){
		                		// logger.info(" 物品检测 terminalId=" + terminalId+"  boxId=" + boxId+"  articles=" + articles);
		                		 continue;
		                	 }
		                	 
	                    	 String  terminalId_boxId=terminalId+"_"+boxId;
	                    	 if( check_Ptreadypackage(terminalId,boxId) //判断存记录表
	                    			// && check_StoreInRecord(terminalId,boxId) //判断存记录表
	                    			 && TerminalIdBoxTempMap.containsKey(terminalId_boxId)){//找到没有枷锁的boxId
	                    		 StoreInRequestsTemp storeInRequestsTemps=TerminalIdBoxTempMap.get(terminalId_boxId);
	                    		 if(storeInRequestsTemps.isIsovertime()){//存在超时的
	                        		 storeInRequestsTemps=new  StoreInRequestsTemp(terminalId,boxEntity.getBoxid(),openBoxCode);
	                        		 //TODO ???  覆盖删除?????   没有付款就不用删除了 
	                        		 TerminalIdBoxTempMap.put(terminalId_boxId, storeInRequestsTemps);//TODO 请求确认后 要删除该记录
	                        		 
	                        		 logger.info("  第二优先级   查找已锁箱子并且是超时的    terminalId_boxId= " + terminalId_boxId);
	                        		 
	                    			 break;
	                    		 }
	                         } 
	                     }
	                 }
	                 return   boxId.byteValue();
	            }
				return null;
			}
    }
    //check_Ptreadypackage 请求是否占用了该箱子
	public  Boolean   check_Ptreadypackage(String terminalId,Integer boxId) {
		//删除超时的 
	 	PtreadypackageEntityExample pd_example=new PtreadypackageEntityExample();
	 	pd_example.createCriteria().andPaystatusEqualTo(Byte.parseByte("0")).andExpiredtimeLessThanOrEqualTo(new Date());
	 	ptreadypackageEntityDao.deleteByExample(pd_example);
	 	
	 	
	 	//第一种情况  terminalId / boxId  /  ExpiredTime   '逾期时间' / PayStatus  '0:未支付，1：已支付 2:已取支付'  
		Date dates=new Date();
	 	PtreadypackageEntityExample ps_example=new PtreadypackageEntityExample();
	 	List<Byte> paystatus=new ArrayList<Byte>();
	 	paystatus.add(Byte.parseByte("0"));
	 	paystatus.add(Byte.parseByte("1"));
	 	ps_example.createCriteria().andBoxnoEqualTo(boxId.toString())
	 	                           .andTerminalnoEqualTo(terminalId)
	 	                           //.andPaystatusEqualTo(Byte.parseByte("0"))
	 	                           .andPaystatusIn(paystatus) 
	 	                           .andExpiredtimeGreaterThan(dates);
	    List<PtreadypackageEntity>	ptreadypackageEntitysList=ptreadypackageEntityDao.selectByExample(ps_example);
	    
	    
	   //第二种情况   `OpenStatus`  '支付后    0:存开箱成功 1:存开箱失败 2:取开箱成功 3：取开箱失败',
	 	PtreadypackageEntityExample pd_example_qu=new PtreadypackageEntityExample();
	 	List<Byte> openstatus=new ArrayList<Byte>();
	 	openstatus.add(Byte.parseByte("1"));
	 	openstatus.add(Byte.parseByte("3"));
	 	pd_example_qu.createCriteria().andBoxnoEqualTo(boxId.toString())
	 	                           .andTerminalnoEqualTo(terminalId)
                                   .andOpenstatusIn(openstatus);
	    List<PtreadypackageEntity>	ptreadypackageEntitysList_qu=ptreadypackageEntityDao.selectByExample(ps_example);
 
	 	if(CollectionUtils.isEmpty(ptreadypackageEntitysList)   || CollectionUtils.isEmpty(ptreadypackageEntitysList_qu)    ){
	 		 logger.info("  check_Ptreadypackage    true   terminalId="+terminalId+"  boxId="+boxId+"  dates="+dates.toLocaleString()    );
	 		return true;
	 	}
	 	 logger.info("  check_Ptreadypackage    false   terminalId="+terminalId+"  boxId="+boxId+"  dates="+dates.toLocaleString()    );
		return false;
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
			 logger.info("  check_StoreInRecord    true   terminalId="+terminalId+"  boxId="+boxId    );
			return true;
		}
		 logger.info("  check_StoreInRecord    false   terminalId="+terminalId+"  boxId="+boxId    );
		return false;
	}
 
	/**
	 * 存入请求
	 */ 
	@Override
	public OutParamStoreInRequest storeRequset(InParamStoreInRequest inParam) {
		//初始返回值
		OutParamStoreInRequest outParam = new OutParamStoreInRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			String    terminalID  = inParam.getTerminalID(); //设备号   String openBoxCode = inParam.getOpenBoxCode() 卡号;
			String    openBoxCode = inParam.getOpenBoxCode();
			int       boxID = inParam.getBoxID();
			logger.info("1   存入请求      CoinJCGServiceImpl   storeRequset   terminalID="+terminalID+"   openBoxCode="+openBoxCode+"   boxID="+boxID  );
		} catch (Exception e) {
			logger.debug("异常："+e.getMessage());
			logger.error("2  error  CoinJCGServiceImpl  storeRequset  fail the reason is:  "+e );
		}
		return outParam;
	}
 
	/**
	 * 存入确认
	 */
	@Override
	public OutParamStoreInConfirm storeIn(InParamStoreInConfirm inParam) {
		OutParamStoreInConfirm outParam = new OutParamStoreInConfirm();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			//获取请求数据
			String terminalID  = inParam.getTerminalID();
			int boxID          = inParam.getBoxID();
			String openBoxCode = inParam.getOpenBoxCode();
			Date storeInTime   = inParam.getStoreInDate();
			storeInTime=DateUtiles.dateQu(storeInTime);
			
			int moneyPrepaid   = inParam.getMoney();	 
			logger.info("1   存入确认请求参数         terminalID="+terminalID+" boxID="+boxID+" openBoxCode="+openBoxCode+" storeInTime="+storeInTime.toLocaleString()+" moneyPrepaid="+moneyPrepaid);
			//业务规则
			/**
				TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);//根据设备号查询业务编码
				Integer businessCode = terminalEntity.getBusinesscode();
				Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);//根据业务编码获取业务模型
				if (businessModelMap == null){
					return outParam;
				}
				String sCheckIdentity = businessModelMap.get(Constant.BT_CFG_NAME_CHECKIDENTITY);
			*/
			//0 TODO ??????  硬件1分钟之外  发送是失败的        不存在就是超时   否则要进行垃圾回收问题
		    String  terminalId_boxId=terminalID+"_"+boxID;
		    StoreInRequestsTemp StoreInRequestsTemps=  TerminalIdBoxTempMap.get(terminalId_boxId);
		    
		    //TODO 收到存确认时，如果缓存中为null或者超时，则将此记录写进Ptreadypackagefail失败表中，openstatus为2
		    if(null == StoreInRequestsTemps || StoreInRequestsTemps.isIsovertime() ){
		    	if(null == StoreInRequestsTemps){
		    		  logger.error("2  CoinJCGServiceImpl storeIn  StoreInRequestsTemps ==  null    ");
		    	}else{
		    		  logger.error("2  CoinJCGServiceImpl storeIn  StoreInRequestsTemps  超时    ");
		    	}
	       	     TerminalIdBoxTempMap.remove(terminalId_boxId);//清理临时对象
	       	     deleteTerminalIdBoxTempMapToPtreadypackagefail(terminalID,boxID,openBoxCode,storeInTime,moneyPrepaid);//插入失败记录表
	       	     outParam.setErrorCode(JCGErrorCode.ERR_GET_NOT_RUNTIME);//没有在运行时段  也就是超时
	   		     return outParam;
	        } 
	        if(!openBoxCode.equals(StoreInRequestsTemps.getOpenBoxCode()) ){
	        	 logger.error("3  CoinJCGServiceImpl storeIn  (terminalId_boxId and openBoxCode  not matching)    openBoxCode="+openBoxCode );
	        	 outParam.setErrorCode(JCGErrorCode.ERR_GET_NOT_RUNTIME);//没有在运行时段  也就是超时
	    		 return outParam;
	        } 
	        TerminalIdBoxTempMap.remove(terminalId_boxId);//清理临时对象
    		logger.info(" 存入确认 就删除     TerminalIdBoxTempMap  terminalId_boxId="+terminalId_boxId+"  StoreInRequestsTemps="+StoreInRequestsTemps);

    		
			//Step.1  存入确认
			BoxEntity boxEntity = boxExDao.selectByPrimaryKey(terminalID, boxID);
			if(boxEntity == null){
				outParam.setErrorCode(JCGErrorCode.ERR_BOX_NOT_EXIST);
				return outParam;		
			}
			
			//Step.2   判断箱子是否被占用
		    List<StoreInRecordEntity> storeInRecordEntityList = terminalExDao.selectInByBoxId(0, boxID, terminalID);//state 0:在箱 1：已取
		    if (!CollectionUtils.isEmpty(storeInRecordEntityList)){
		    	outParam.setErrorCode(JCGErrorCode.ERR_PUT_EXIST_OTHER_BOX);
				return outParam;
			}
			//Step.3   写入在箱记录
			StoreInRecordEntity storeInRecordEntity = new StoreInRecordEntity();		
			storeInRecordEntity.setTerminalid(terminalID);
			storeInRecordEntity.setBoxid(boxID);
			storeInRecordEntity.setUsercardid(openBoxCode);
			storeInRecordEntity.setStoreintime(storeInTime);
			storeInRecordEntity.setState((byte)0);      //0:在箱 1：已取
			storeInRecordEntity.setUsertype(SysDict.CUSTOMER_TYPE_NROMAL);       //0普通用户1贵宾用户9黑名单用户11微信用户
			storeInRecordEntity.setRealmoney((float)0); //找零金额
			storeInRecordEntity.setMoney((float)moneyPrepaid); //实收金额
			
    		logger.info(" 1 写入在箱记录  terminalId_boxId="+terminalId_boxId+"  boxID="+boxID+"  openBoxCode="+openBoxCode );
    		logger.info(" 2 写入在箱记录  storeInTime="+storeInTime.toLocaleString()+"对比  storeInRecordEntity.getStoreintime()="+storeInRecordEntity.getStoreintime().toLocaleString() );
			storeInRecordExDao.insertSelective(storeInRecordEntity);
    		logger.info(" 3 写入在箱记录  storeInTime="+storeInTime.toLocaleString()+"对比  storeInRecordEntity.getStoreintime()="+storeInRecordEntity.getStoreintime().toLocaleString() );

 
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("4  CoinJCGServiceImpl storeIn  fail the reason is :"+e);
		}
		return outParam;
	}
    // 删除缓存  转移到Ptreadypackagefail
	public void deleteTerminalIdBoxTempMapToPtreadypackagefail
	(String terminalID,int boxID,String openBoxCode,Date storeInTime,int moneyPrepaid) 
	{
		String boxId = String.valueOf(boxID);
		BigDecimal payamt = new BigDecimal(moneyPrepaid);
		String remark = "存确认时间超过时限";
		PtreadypackagefailEntity ptreadypackagefail =new PtreadypackagefailEntity();
		ptreadypackagefail.setTerminalno(terminalID);
		ptreadypackagefail.setBoxno(boxId);
		ptreadypackagefail.setCustomerid(openBoxCode);
		ptreadypackagefail.setPackageid(openBoxCode);
		ptreadypackagefail.setOrdertime(storeInTime);	//下单时间
		ptreadypackagefail.setExpiredtime(new Date());	//清除时间
		ptreadypackagefail.setPayamt(payamt);
		ptreadypackagefail.setOpenstatus((byte) 2); 	//存请求缓存清除后，收到存确认异常状态记录Openstatus=2
		ptreadypackagefail.setRemark(remark);
		ptreadypackagefailEntityDao.insert(ptreadypackagefail);
	}
	
  /**
   public static void main(String[] args) {
			Integer boxtypecode=2; // 1 小   2中  3大
			System.out.println("-----inttobyte--"+boxtypecode.byteValue());
		    int max=12;
	        int min=10;
	        Random random = new Random();
	        int s = random.nextInt(max)%(max-min+1) + min;
	        System.out.println(s);
		        
			List<BoxEntity> BoxEntityList = new ArrayList<BoxEntity>(); 
			BoxEntityList.add(new BoxEntity());
			BoxEntityList.add(new BoxEntity());
			BoxEntityList.add(new BoxEntity());
			System.out.println("    BoxEntityList="+BoxEntityList.size());
			for(int i=0;i<BoxEntityList.size();i++){
				BoxEntity BoxEntitys=BoxEntityList.get(i);
				System.out.println(i+"    BoxEntityList="+BoxEntitys);
			}
	 
	}
  */
 
	/**  取请求  */
	@Override
	public OutParamTakeOutRequest takeRequest(InParamTakeOutRequest inParam) {
		//初始返回值
		OutParamTakeOutRequest outParam = new OutParamTakeOutRequest();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try{
			String terminalID  = inParam.getTerminalID();  //设备号
			int      boxid     = inParam.getBoxID();       //箱号
			String openBoxCode = inParam.getOpenBoxCode(); //卡号
		    Date storeInTime   = inParam.getStoreInDate(); //存入时间
		    storeInTime=DateUtiles.dateQu(storeInTime);
			logger.info("1   取请求 请求参数        terminalID="+terminalID+" boxID="+boxid+" openBoxCode="+openBoxCode+"  storeInTime="+storeInTime.toLocaleString() );
			//根据设备号查询业务编码
			TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
			Integer businessCode = terminalEntity.getBusinesscode();
			//根据业务编码获取业务模型
			Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
			if (businessModelMap == null){
				outParam.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);//TODO  ???? 没有业务规则的就是非法指令
				return outParam;
			}
			//Step.1 根据卡号规则，转换卡片
			//Step.2  运行时段
			//Step.3 查询在箱记录         openBoxCode每次都是唯一的     TODO  ???????应该发送存入时间的不然又问题???????
			/*StoreInRecordEntityExample example = new StoreInRecordEntityExample();
			example.createCriteria().andTerminalidEqualTo(terminalID)
			                        .andBoxidEqualTo(boxid)
			                        .andStoreintimeEqualTo(storeInTime)
						            .andUsercardidEqualTo(openBoxCode)
						            .andStateEqualTo((byte)0);//0:在箱 1：已取
			example.setOrderByClause("storeInTime desc");
			List<StoreInRecordEntity> records = storeInRecordExDao.selectByExample(example);
			logger.info("2   查询在箱记录："+records.size() );
			if (records == null || records.size() == 0){
				outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
				return outParam;
			}*/
			
			List<StoreInRecordEx> records = storeInRecord.queryBoxRecord(terminalID, boxid, storeInTime.toLocaleString());
			logger.info("2.1   新的查询在箱记录："+records.size() );
			if (records == null || records.size() == 0){
				outParam.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
				return outParam;
			}
			
			
			//Step.5 收费功能
		    String sCheckCharge = businessModelMap.get(Constant.BT_CFG_NAME_CHARGESWITCH);
			if (StringUtils.isNotEmpty(sCheckCharge) ){
				String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
				BoxEntity boxEntity = boxExDao.selectByPrimaryKey(terminalID, boxid);//查询收费箱门的类型
				//state  '0:在箱 1：已取'    查询的是state=0的情况
				//StoreInRecordEx storeInRecordEx = storeInRecordExDao.selectByRequest(terminalID, boxid, openBoxCode);//查询在箱记录表 （根据卡号）
				//StoreInRecordEntity storeInRecordEx=records.get(0);
				//if(storeInRecordEx != null){
				StoreInRecordEx storeInRecordEx=records.get(0);
				if(storeInRecordEx != null){
					//收费标准chargeCode   0:vip更衣柜收费标准   1 更衣柜收费标准 2 贵重物品收费标准   
					//收费模式 usedState    0:按小时收费 1：按时段收费 2：按天收费
					//boxTypeCode        1 小   2中  3大
					//折扣=实际售价/原售价×100%
					//int currentTime=DateUtils.nowDate().getHours();
 
					Date storeintime= storeInRecordEx.getStoreintime();
					Date nowDate= DateUtils.nowDate();
					Integer boxtypecode= boxEntity.getBoxtypecode();
					Integer chargeModeInt= Integer.parseInt(chargeMode);
					logger.info("3.1   调用存储过程准备参数    storeintime="+storeintime.toLocaleString()+" nowDate="+nowDate.toLocaleString()+" boxtypecode="+boxtypecode+" chargeModeInt="+chargeModeInt);
					Float  moneyTotal = punishDao.cost(storeintime,nowDate, boxtypecode, chargeModeInt);//应收金额
					Float  moneyPrepaid = storeInRecordEx.getMoney();//预支付
					Float  moneyremain = moneyTotal-moneyPrepaid;//超时还要支付金额=应收金额-预支付
					logger.info("3.2    调用存储过程获取金额    moneyTotal="+moneyTotal+"  moneyPrepaid="+moneyPrepaid +"  moneyremain="+moneyremain);
					if(moneyremain>=0){ 
						//outParam   //返回剩余金额
						outParam.setMoneyRemain(moneyremain.intValue());
						return outParam;
					}
					logger.info("3.3  返回参数  剩余金额  moneyremain="+moneyremain);
				}
			}
		   /**
				boolean isOk = false;
				for (StoreInRecordEntity storeInRecord : records) {
					String anotherTerminalID = storeInRecord.getTerminalid();
					if (terminalID.equalsIgnoreCase(anotherTerminalID)) {
						isOk = true;
						break;
					}
				}
				if (!isOk){
					logger.info("4  返回参数    请求参数terminalID和 StoreInRecordEntity表的terminalID 不一致  "  );
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
				}  
			*/
			} catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("4  CoinJCGServiceImpl takeRequest  fail the reason is :"+e);
			}
		return outParam;
	}

	/**
	 * 取确认
	 */
	@Override
	public OutParamTakeOutConfirm takeOut(InParamTakeOutConfirm inParam) {
		OutParamTakeOutConfirm outParam = new OutParamTakeOutConfirm();
		outParam.setErrorCode(JCGErrorCode.ERR_OK);
		try {
			//获取请求数据
			String terminalID  = inParam.getTerminalID();
			int boxID          = inParam.getBoxID();
			String openBoxCode = inParam.getOpenCode();
			Date storeInTime   = inParam.getStorInDate();
			storeInTime=DateUtiles.dateQu(storeInTime);
			
			Date takeOutTime   = inParam.getTakeOutDate();
			takeOutTime=DateUtiles.dateQu(takeOutTime);
			int moneyremain    = inParam.getMoney();  //TODO  这个要传递的  否则服务器重新计算会不准确的 
 
			logger.info("1  取确认 请求参数   terminalID="+terminalID+"  boxID="+boxID +"  openBoxCode="+openBoxCode);
			logger.info("  storeInTime="+storeInTime.toLocaleString()+"  takeOutTime="+takeOutTime.toLocaleString() +"  moneyremain="+moneyremain);
			//Step.1   根据设备号查询业务编码
			TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(terminalID);
			Integer businessCode = terminalEntity.getBusinesscode();
			Map<String, String> businessModelMap = getBussineeModelByCode(business, businessCode);
			if (businessModelMap == null){
				return outParam;
			}
			//Step.2 根据设备号，查询设备是否支持固定箱门
			BoxEntity boxEntity = boxExDao.selectByPrimaryKey(terminalID, boxID);
			if(boxEntity == null){
				outParam.setErrorCode(JCGErrorCode.ERR_BOX_NOT_EXIST);
				return outParam;		
			}
			//Step.3 查询是否已经存在记录
			TakeOutRecordEntity 	takeOutRecordEntity = takeOutRecordEntityDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
			if (takeOutRecordEntity != null){//表示已经取过了
				return outParam;				
			}
			//收费功能是否开启
			Float  moneyTotal = 0f;
			String sCheckCharge = businessModelMap.get(Constant.BT_CFG_NAME_CHARGESWITCH);
			if (StringUtils.isNotEmpty(sCheckCharge) ){
				String chargeMode = businessModelMap.get(Constant.BT_CFG_NAME_CHARGEMODE);
				//预支付金额
				PaymentesEx  paymentesEx=paymentesDao.getMoneyByBoxTypePrepay(Integer.parseInt(chargeMode), boxEntity.getBoxtypecode());
				//TODO ?????不能重新计算的   因为有 时间差的?????        总支付金额
				//moneyTotal = punishDao.cost(storeInTime,takeOutTime, boxEntity.getBoxtypecode(), Integer.parseInt(chargeMode));//应收金额
				moneyTotal=paymentesEx.getMoney()+moneyremain;
				logger.info("2    取确认  服务器更新总金额   moneyTotal="+moneyTotal+"  moneyPrepaid="+paymentesEx.getMoney()  );
			}
			
			logger.info("3  取确认 请求参数   通过主键查询  terminalID="+terminalID+"  boxID="+boxID +"  openBoxCode="+openBoxCode+"  storeInTime="+storeInTime);
		/*	StoreInRecordEntity storeInRecordEntity = storeInRecordExDao.selectByPrimaryKey(terminalID, boxID, openBoxCode, storeInTime);
			//更新存物记录
			if (null !=storeInRecordEntity){
				storeInRecordEntity = new StoreInRecordEntity();
				storeInRecordEntity.setUsercardid(openBoxCode);
				storeInRecordEntity.setTerminalid(terminalID);
				storeInRecordEntity.setBoxid(boxID);
				storeInRecordEntity.setStoreintime(storeInTime);
				storeInRecordEntity.setState((byte)1);//0:在箱 1：已取
				storeInRecordEntity.setMoney(moneyTotal);
				storeInRecordExDao.updateByPrimaryKeySelective(storeInRecordEntity);
			}else{
			    logger.error("4  取确认   CoinJCGServiceImpl   takeOut  fail the reason is null ==storeInRecordEntity    " );
			    return outParam;				 
			}*/
			
			List<StoreInRecordEntity> storeInRecordEntityList=storeInRecordExDao.queryBoxRecordList( terminalID,   boxID,   openBoxCode,  storeInTime.toLocaleString());
			StoreInRecordEntity storeInRecordEntity=null;
			if(!CollectionUtils.isEmpty(storeInRecordEntityList)){
				storeInRecordEntity=storeInRecordEntityList.get(0);
				storeInRecordEntity.setState((byte)1);//0:在箱 1：已取
				storeInRecordEntity.setMoney(moneyTotal);
				storeInRecordExDao.updateByPrimaryKeySelective(storeInRecordEntity);
			} else{
			    logger.error("4 1  取确认   CoinJCGServiceImpl   takeOut  fail the reason is null ==storeInRecordEntityList    " );
			    return outParam;				 
			} 
			
			logger.info("5  写取物记录   通过主键查询  terminalID="+terminalID+"  boxID="+boxID +"  openBoxCode="+openBoxCode+"  storeInTime="+storeInTime);

			//Step.5 写取物记录
			takeOutRecordEntity = new TakeOutRecordEntity();
			takeOutRecordEntity.setTerminalid(terminalID);
			takeOutRecordEntity.setBoxid(boxID);
			takeOutRecordEntity.setUsercardid(openBoxCode);
			takeOutRecordEntity.setStoreintime(storeInTime); 
			takeOutRecordEntity.setTaketime(takeOutTime);
			takeOutRecordEntity.setMoney(moneyTotal);
			takeOutRecordEntity.setRealmoney(moneyTotal);
			takeOutRecordEntity.setType(2);     //1中途取；2正常取；3管理取；4超时取；5远程开箱取物  7卡取消取物
			takeOutRecordEntity.setCashierno("0");
			takeOutRecordEntity.setMakeopcode(null);
			takeOutRecordEntityDao.insert(takeOutRecordEntity);
			logger.info("6 完成 写取物记录   通过主键查询  terminalID="+terminalID+"  boxID="+boxID +"  openBoxCode="+openBoxCode+"  storeInTime="+storeInTime);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("7  取确认   保存取物记录    CoinJCGServiceImpl   takeOut  fail the reason is:"+e );
		}
		return outParam;
	}
		
	
	
	
	@Override
	public OutParamAlarmNoticesRequest alarmNotices(InParamAlarmNoticesRequest alarmNotices) {
	     System.out.println(" CoinJCGServiceImpl    alarmNotices ");
		 //初始返回值
	     OutParamAlarmNoticesRequest outParam = new OutParamAlarmNoticesRequest();
		 outParam.setErrorCode(JCGErrorCode.ERR_OK);
		 try {
			 String terminalID= alarmNotices.getTerminalID(); //设备号
			 int  alarmType =alarmNotices.getAlarmType();     //报警类型
			 int  alarmLevel =alarmNotices.getAlarmType();    //报警等级
			 
			 Map<String, String> params = new HashMap<String, String>();
			 params.put("terminalID", terminalID);
			 params.put("alarmType", alarmType+"");
			 params.put("alarmLevel", alarmLevel+"");
 
			/* ApppartnerTerminalEntityExample aExample=new ApppartnerTerminalEntityExample();
			 aExample.or().andTerminalidEqualTo(terminalID);
			 List<ApppartnerTerminalEntity> apppartnerTerminalList=  apppartnerTerminalEntityDao.selectByExample(aExample);*/
			 TerminalEntityExample example=new TerminalEntityExample();
			 example.or().andTerminalidEqualTo(terminalID);
			 List<TerminalEntity> terminalLists= 	 terminalExDao.selectByExample(example);
			 
			 String  alarmnoticeurls="";
			 String  app_key="";
			 String  app_secret="";
			 
			 if(!CollectionUtils.isEmpty(terminalLists)){
				   TerminalEntity terminalEntitys =terminalLists.get(0);
				   String appkeys="";
				   String   areacode = terminalEntitys.getAreacode();//0100  010000   01000000
				   int lengths=areacode.length();
				   if(lengths>=4){
						 areacode=areacode.substring(0, 4);
						 //System.out.println("------areacode----"+areacode);
						 AreaEntity areaEntitys= areaExDao.selectByPrimaryKey(areacode);
						 appkeys= areaEntitys.getAppkey();
				   } 
				   AppPartnerEntity appPartnerEntitys=appPartnerEntityDao.selectByPrimaryKey(appkeys);
				   alarmnoticeurls= appPartnerEntitys.getAlarmnoticeurls();//通知地址
				   app_key=appPartnerEntitys.getUserId();
				   app_secret=appPartnerEntitys.getUserKey();
			 }
			 params.put("alarmnoticeurls", alarmnoticeurls+"");
			 params.put("app_key", app_key+"");
			 params.put("app_secret", app_secret+"");
			 
			 if(!StringUtils.isEmpty(terminalID) 
					 && !StringUtils.isEmpty(alarmType) 
					 && !StringUtils.isEmpty(alarmLevel) 
					 && !StringUtils.isEmpty(alarmnoticeurls)
					 && !StringUtils.isEmpty(app_key) 
					 && !StringUtils.isEmpty(app_secret) ){
				 
				 String results= HttpClient4Guotong.to_Remote_Alarm_Notices(params) ;
				 if(null !=results){
					 JSONObject resJson = JSONObject.fromObject(results); 
					 System.out.println("   resJson="+resJson);
				  	 String err_code= resJson.getString("err_code");////0:表示成功 非0:不成功
				  	 if(!"0".equals(err_code)){
				  		 outParam.setErrorCode(JCGErrorCode.ERR_App_Alarm_Notices_Remote_Call);
				  	 }
				 }else{
					 outParam.setErrorCode(JCGErrorCode.ERR_App_Alarm_Notices_Remote_Call);
				 }
			 }else{	
				 logger.error("error  CoinJCGServiceImpl  alarmNotices terminalID="+terminalID );
				 logger.error(" alarmType="+alarmType+" alarmLevel="+alarmLevel+" alarmnoticeurls="+alarmnoticeurls );
				 logger.error(" app_key="+app_key );
				 outParam.setErrorCode(JCGErrorCode.ERR_App_Alarm_Notices_Remote_Call);
			 }
			 outParam.setTerminalID(terminalID);
		 } catch (Exception e) {
			 logger.debug("异常："+e.getMessage());
			 logger.error("error  CoinJCGServiceImpl  alarmNotices  fail the reason is:  "+e );
		 }
		 return outParam;
	}
	
	public static void main(String[] args) {
		/*String terminalID="";
	    if(!StringUtils.isEmpty(terminalID)) {
	    	System.out.println("1 terminalID="+terminalID);
	    }
	    
	    terminalID=null;
	    if(!StringUtils.isEmpty(terminalID)) {
	    	System.out.println("2 terminalID="+terminalID); 	
	    }*/
		
		 String   areacode = "010000";//0100  010000   01000000
		 int lengths=areacode.length();
		 if(lengths>=4){
			 areacode=areacode.substring(0, 4);
			 System.out.println("------areacode----"+areacode);
		 }
	}
}