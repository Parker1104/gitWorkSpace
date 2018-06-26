package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.MsgUtils;
import com.hzdongcheng.front.server.model.protocol.jcg.JCGErrorCode;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBackCardAndBox;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBoxIDCardLossRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBoxIDChangeCardIDConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamCardIDCheckOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamCardIDLogInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUpdatePassword;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUserCheckOutbyDay;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUserLoginCheck;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamBackCardAndBox;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamUserCheckOutbyDay;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamUserLoginCheck;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.ICashierService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BackCardAndBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OutParamBoxIDCardLossRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordByCard;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AccountExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AreaExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardAndBoxBoundExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.InBackCardAndBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.ManagerExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;

@Service("CashierService")
public class ICashierServiceImpl implements ICashierService{
	
	@Autowired
	InBackCardAndBox inCard;
	@Autowired
	StoreInRecordExDao storeInDao;
	@Autowired
	CardAndBoxBoundExDao cardDao;
	@Autowired
	ManagerExDao managerDao;
	@Autowired
	BoxExDao boxDao;
	@Autowired
	TakeOutRecordEntityDao takeDao;
	@Autowired
	AccountExDao accountDao;
	@Autowired
	TerminalExDao terminalExDao;
	@Autowired
	AreaExDao areaExDao;
	
	//根据卡号获取卡箱对应信息
	@Override
	public BackCardAndBox CardIDLogInRequest(InParamBackCardAndBox inCardAndBox) {
		String cardid = inCardAndBox.getCardID();
		//根据卡号获取存物信息				
		BackCardAndBox back = new BackCardAndBox();
		CardAndBoxBoundEntity cardAndBoxBoundEntity = cardDao.selectByPrimaryKey(cardid);
		if (cardAndBoxBoundEntity==null) {
			back.setErrorCode(JCGErrorCode.ERR_BOX_USER_NULL);
			return back;
		}else {
			StoreInRecordEntityExample example = new StoreInRecordEntityExample();
			StoreInRecordEntityExample.Criteria criteria = example.createCriteria();
			criteria.andTerminalidEqualTo(cardAndBoxBoundEntity.getTerminalid());
	        criteria.andUsercardidEqualTo(cardAndBoxBoundEntity.getCardid());
	        criteria.andBoxidEqualTo(cardAndBoxBoundEntity.getBoxid());
	        criteria.andStateEqualTo((byte)0);	        
			List<StoreInRecordEntity> list = storeInDao.selectByExample(example);
			TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(cardAndBoxBoundEntity.getTerminalid());
			BoxEntity box = boxDao.selectByPrimaryKey(cardAndBoxBoundEntity.getTerminalid(), cardAndBoxBoundEntity.getBoxid());
			AreaEntity areaEntity = areaExDao.selectByPrimaryKey(terminalEntity.getAreacode());
			if (list.size()==0) {
				back.setAreaname(areaEntity.getAreaname());
				back.setDispalyname(box.getDispalyname());
				back.setDisplayname(terminalEntity.getDisplayname());
				back.setFeevalue(0);
				back.setRealMoney(0);
				back.setErrorCode(JCGErrorCode.ERR_OK);
				return back;
			}else {
				back.setErrorCode(JCGErrorCode.ERR_PUT_CARD_USED);
				return back;
			}
		}
	}
	//用户卡登记
	@Override
	@Transactional
	public OutParamStoreInConfirm CardIDLogInConfirm(InParamCardIDLogInConfirm inConfirm) {
		OutParamStoreInConfirm outConfirm = new OutParamStoreInConfirm();
		CardAndBoxBoundEntity card = cardDao.selectByPrimaryKey(inConfirm.getCardid());
		if (card==null) {//查看卡箱绑定表是否存在该卡
			outConfirm.setErrorCode(JCGErrorCode.ERR_BOX_USER_NULL);
			return outConfirm;
		}else {
			if (card.getSync()!=1) {
				outConfirm.setErrorCode(JCGErrorCode.ERR_GET_ATH_FAIL);
				return outConfirm;
			}else {
				StoreInRecordEntity se = new StoreInRecordEntity();
				se.setTerminalid(card.getTerminalid());
				se.setBoxid(card.getBoxid());
				se.setUsercardid(inConfirm.getCardid());
				se.setStoreintime(inConfirm.getStoreintime());
				se.setMoney(inConfirm.getMoney());
				se.setRealmoney((float)0);
				se.setUsertype("0");
				se.setState((byte)0);
				se.setEffectivedays(DateUtils.addDay(new Date(), 30));
				se.setMakeopcode(inConfirm.getAccountCode());
				se.setCashierno(inConfirm.getCashierNo());
				StoreInRecordEntityExample example = new StoreInRecordEntityExample();
				StoreInRecordEntityExample.Criteria criteria = example.createCriteria();
				//criteria.andTerminalidEqualTo(card.getTerminalid());
		        criteria.andUsercardidEqualTo(card.getCardid());
		        //criteria.andBoxidEqualTo(card.getBoxid());
		        criteria.andStateEqualTo((byte)0);
		        //criteria.andStoreintimeEqualTo(inConfirm.getStoreintime());
				List<StoreInRecordEntity> list = storeInDao.selectByExample(example);
				try {
					if (list==null || list.size()==0) {
						storeInDao.insertSelective(se);
						outConfirm.setErrorCode(JCGErrorCode.ERR_OK);
						return outConfirm;
					}else {						
						outConfirm.setErrorCode(JCGErrorCode.ERR_PUT_CARD_USED);
						return outConfirm;
					}			
				} catch (Exception e) {
					e.printStackTrace();
					outConfirm.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
					return outConfirm;
				}
			}			
		}				
	}
/*	//管理卡开箱
	@Override
	public OpenBoxByManager openBoxByManager(InParamBackCardAndBox ps) {
		OpenBoxByManager open = new OpenBoxByManager();
		
		ManagerEntityExample example = new ManagerEntityExample();
		example.createCriteria().andManagercardidEqualTo(ps.getCardid());
		List<ManagerEx> manager = managerDao.selectByExampleEx(example);
		if (manager==null || manager.size()==0) {
			open.setErrorCode(JCGErrorCode.ERR_BOX_ADMIN_NULL);
			return open;
		}else{
			if (manager.iterator().next().getManagertype()==7) {
				List<BoxEntity> list = terminalExDao.selectFreeOpenBoxTerminalID(ps.getTerminalID());
				int [] boxid = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					boxid[i]=list.get(i).getBoxid();
				}
			    open.setBoxid(boxid);
			    open.setType(3);
			    open.setErrorCode(JCGErrorCode.ERR_OK);
			    return open;
			}
			if (manager.iterator().next().getManagertype()==6) {
				List<PunishRecordEntity> list = terminalExDao.selectTimeoutOpenBox(ps.getTerminalID());
				int [] boxid = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					boxid[i]=list.get(i).getBoxid();
				}
			    open.setBoxid(boxid);
			    open.setType(6);
			    open.setErrorCode(JCGErrorCode.ERR_OK);
			    return open;
			}
			if (manager.iterator().next().getManagertype()==5) {
				int [] boxid = {0};
			    open.setBoxid(boxid);
			    open.setType(5);
			    open.setErrorCode(JCGErrorCode.ERR_OK);
			    return open;
			}
			if (manager.iterator().next().getManagertype()==3) {
			    //CardAndBoxBoundEntity entity = cardDao.selectByPrimaryKey(ps.getCardid());
				List<TakeOutRecordEntity> list = terminalExDao.selectAllBoxterminalID(ps.getTerminalID());
			    int [] boxid = new int[]{};
			    for (int i = 0; i < list.size(); i++) {
					boxid[i]=list.get(i).getBoxid();
				}
			    open.setBoxid(boxid);
			    open.setType(3);
			    open.setErrorCode(JCGErrorCode.ERR_OK);
			    return open;
			}
			if (manager.iterator().next().getManagertype()==2) {
			    //CardAndBoxBoundEntity entity = cardDao.selectByPrimaryKey(ps.getCardid());
				List<TakeOutRecordEntity> list = terminalExDao.selectAllBoxterminalID(ps.getTerminalID());
			    int [] boxid = new int[]{};
			    for (int i = 0; i < list.size(); i++) {
					boxid[i]=list.get(i).getBoxid();
				}
			    open.setBoxid(boxid);
			    open.setType(3);
			    open.setErrorCode(JCGErrorCode.ERR_OK);
			    return open;
			}else if(manager.iterator().next().getManagertype()==1){
				open.setType(2);
				open.setErrorCode(JCGErrorCode.ERR_OK);
				return open;
			}else if (manager.iterator().next().getManagertype()==0) {
				open.setType(1);
				open.setErrorCode(JCGErrorCode.ERR_OK);
				return open;
			}else {
				open.setErrorCode(JCGErrorCode.ERR_GET_ATH_FAIL);
				return open;
			}
		}		
	}*/
	//根据卡号获取存物信息
	@Override
	public StoreInRecordByCard cardIDCheckOutRequest(InParamBackCardAndBox ps) {
		StoreInRecordByCard sr = new StoreInRecordByCard();		
		CardAndBoxBoundEntity card = cardDao.selectByPrimaryKey(ps.getCardID());
		if (card==null) {
			sr.setErrorCode(JCGErrorCode.ERR_GET_ATH_FAIL);
			return sr;
		}else {
			StoreInRecordEntityExample example = new StoreInRecordEntityExample();
			StoreInRecordEntityExample.Criteria criteria = example.createCriteria();
			criteria.andTerminalidEqualTo(card.getTerminalid());
	        criteria.andUsercardidEqualTo(card.getCardid());
	        criteria.andBoxidEqualTo(card.getBoxid());
	        criteria.andStateEqualTo((byte)0);	        
			List<StoreInRecordEntity> list = storeInDao.selectByExample(example);
			TerminalEntity terminalEntity = terminalExDao.selectByPrimaryKey(card.getTerminalid());
			BoxEntity box = boxDao.selectByPrimaryKey(card.getTerminalid(), card.getBoxid());
			AreaEntity areaEntity = areaExDao.selectByPrimaryKey(terminalEntity.getAreacode());
			
			if (list.size()==0) {
				sr.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
				return sr;
			}else {
				Date store = list.get(0).getStoreintime();
				String time = ps.getSendTime();
				Date taketime = DateUtils.stringToDate(time);
				try {
					long time1 = taketime.getTime();  
			        long time2 = store.getTime();  
			        long diff ;  
			        if(time1<time2) {  
			            diff = time2 - time1;  
			        } else {  
			            diff = time1 - time2;  
			        }  
			        long day = diff / (24 * 60 * 60 * 1000);  
			        long hour = (diff / (60 * 60 * 1000) - day * 24);  
			        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
			        
				    if (min>5) {
				    	sr.setUsetime((int)(day*24+hour+1));
					}else {
						sr.setUsetime((int)(day*24+hour));
					}			   
				} catch (Exception e) {
					e.printStackTrace();
				}
				sr.setAreaname(areaEntity.getAreaname());
				sr.setBoxtypecode(box.getBoxtypecode().toString());
				sr.setDispalyname(box.getDispalyname());
				sr.setDisplayname(terminalEntity.getDisplayname());
				sr.setMoney(0);
				sr.setRealmoney(0);
				sr.setStoreintime(list.get(0).getStoreintime().toString());
				return sr;
			}
		}
	}
	//用户卡结账
	@Override
	@Transactional
	public OutParamTakeOutConfirm cardIDCheckOutConfirm(InParamCardIDCheckOutConfirm ps) {
		CardAndBoxBoundEntity card = cardDao.selectByPrimaryKey(ps.getCardid());
		StoreInRecordEntityExample example2 = new StoreInRecordEntityExample();
		StoreInRecordEntityExample.Criteria criteria2 = example2.createCriteria();
		criteria2.andTerminalidEqualTo(card.getTerminalid());
		criteria2.andUsercardidEqualTo(ps.getCardid());
        criteria2.andBoxidEqualTo(card.getBoxid());
        criteria2.andStateEqualTo((byte)0);
		List<StoreInRecordEntity> list2 = storeInDao.selectByExample(example2);
		OutParamTakeOutConfirm outConfirm = new OutParamTakeOutConfirm();
		if (list2.size()==0) {
			outConfirm.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
			return outConfirm;
		}else {
			Date date = DateUtils.nowDate();
			Date time = list2.get(0).getStoreintime();	
			TakeOutRecordEntity te = new TakeOutRecordEntity();
			StoreInRecordEntity se = new StoreInRecordEntity();
			te.setTerminalid(card.getTerminalid());
			te.setBoxid(card.getBoxid());
			te.setUsercardid(ps.getCardid());
			te.setMoney(ps.getMoney());
			te.setStoreintime(time);
			te.setTaketime(date);
			te.setRealmoney((float)0);
			te.setType(2);
			te.setCashierno(ps.getCashierNo());
			te.setMakeopcode(ps.getAccountCode());
			TakeOutRecordEntityExample example = new TakeOutRecordEntityExample();
			TakeOutRecordEntityExample.Criteria criteria = example.createCriteria();
			criteria.andTerminalidEqualTo(card.getTerminalid());
	        criteria.andUsercardidEqualTo(ps.getCardid());
	        criteria.andBoxidEqualTo(card.getBoxid());
	        criteria.andStoreintimeEqualTo(time);
			List<TakeOutRecordEntity> list = takeDao.selectByExample(example);
			try {
				if (list.size()==0 || list==null) {
					takeDao.insertSelective(te);//插入取物记录
					se.setTerminalid(card.getTerminalid());
					se.setBoxid(card.getBoxid());
					se.setUsercardid(ps.getCardid());
					se.setStoreintime(time);
					se.setState((byte)1);
					storeInDao.updateByPrimaryKeySelective(se);//更新存物状态
					outConfirm.setErrorCode(JCGErrorCode.ERR_OK);
					return outConfirm;
				}else {
					outConfirm.setErrorCode(JCGErrorCode.ERR_PUT_CARD_CHECKOUT);
					return outConfirm;
				}			
			} catch (Exception e) {
				e.printStackTrace();
			}
			outConfirm.setErrorCode(JCGErrorCode.ERR_BOX_USER_NULL);
			return outConfirm;
		}		
	}
	//柜端用户登录
	@Override
	public OutParamUserLoginCheck userLogin(InParamUserLoginCheck ps) {
		OutParamUserLoginCheck out = new OutParamUserLoginCheck();
		AccountEntityExample example = new AccountEntityExample();
		Criteria criteria = example.createCriteria();
		criteria.andAccountcodeEqualTo(ps.getAccountcode());
		try {
			criteria.andPasswordEqualTo(MsgUtils.getMD5(ps.getPassword()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		criteria.andCashierflagEqualTo(1);
		List<AccountEx> list = accountDao.selectByExampleEx(example);
		if (list.size()==0 || list==null) {
			out.setErrorCode(JCGErrorCode.ERR_BOX_USER_NULL);
			return out;
		}else {
			out.setUserID(list.iterator().next().getAccountcode());
			out.setAreacode(list.iterator().next().getAreacode());
			out.setUsername(list.iterator().next().getAccountname());
			//out.setRolecode(list.iterator().next().getRolecode());
			out.setTelphone(list.iterator().next().getTelephone());
			out.setErrorCode(JCGErrorCode.ERR_OK);
			System.out.println("---柜端用户登录成功---");
			return out;
		}
	}
	//根据箱门号获取存物信息（挂失请求）
	@Override
	public OutParamBoxIDCardLossRequest boxIDCardLossRequest(InParamBoxIDCardLossRequest ps) {
		OutParamBoxIDCardLossRequest out = inCard.boxIDCardLossRequest(ps.getDispalyname());
		if (out==null) {
			OutParamBoxIDCardLossRequest out2 = new OutParamBoxIDCardLossRequest();
			out2.setErrorCode(JCGErrorCode.ERR_GET_EXIST_NULL);
			return out2;
		}else {
			StoreInRecordByCard storeRBC = inCard.getAcountInfo(out.getCardID());
			String boxtypecode = storeRBC.getBoxtypecode();
			String configvalue = storeRBC.getConfigvalue();
			Date datetime = DateUtils.nowDate();
			String date = DateUtils.dateToString(datetime);
			String dtime = out.getStoreInTime();
			StoreInRecordByCard storemoney = inCard.getmoney(dtime,date,boxtypecode,configvalue);
			float money = storemoney.getMoney();
			String time = ps.getSendTime();
			String storetime = out.getStoreInTime();
			Date taketime = DateUtils.stringToDate(time);
			Date store = DateUtils.stringToDate(storetime);
				long time1 = taketime.getTime();  
		        long time2 = store.getTime();  
		        long diff ;  
		        if(time1<time2) {  
		            diff = time2 - time1;  
		        } else {  
		            diff = time1 - time2;  
		        }  
		        long day = diff / (24 * 60 * 60 * 1000);  
		        long hour = (diff / (60 * 60 * 1000) - day * 24);  
		        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60); 
				if (min>5) {
					out.setUsedTime((int)(day*24+hour+1));
				}else {
					out.setUsedTime((int)(day*24+hour));
				}
			    out.setNeedMoney(money);
				out.setErrorCode(JCGErrorCode.ERR_OK);
				return out;		  
	   }
	}
	//挂失授权
	@Override
	public OutParamBackCardAndBox boxIDCardLossAuthorize(InParamBackCardAndBox ps) {
		OutParamBackCardAndBox out = new OutParamBackCardAndBox();
		ManagerEntityExample example = new ManagerEntityExample();
		ManagerEntityExample.Criteria criteria = example.createCriteria();
		criteria.andManagercardidEqualTo(ps.getCardID());
		criteria.andManagertypeEqualTo(4);
		List<ManagerEntity> list = managerDao.selectByExample(example);
		if (list.size()==0 || list==null) {
			out.setErrorCode(JCGErrorCode.ERR_GET_ATH_FAIL);
			return out;
		}else {
			out.setErrorCode(JCGErrorCode.ERR_OK);
			return out;
		}
	}
	//用户卡挂失
	@Override
	@Transactional
	public OutParamBackCardAndBox boxIDCardLossConfirm(InParamBackCardAndBox ps) {
		OutParamBackCardAndBox out = new OutParamBackCardAndBox();
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		StoreInRecordEntityExample.Criteria criteria = example.createCriteria();
		criteria.andUsercardidEqualTo(ps.getCardID());
		criteria.andStateEqualTo((byte)0);
		List<StoreInRecordEntity> list = storeInDao.selectByExample(example);
		if (list.size()==0) {
			out.setErrorCode(JCGErrorCode.ERR_GET_EXIST_NULL);
			return out;
		}else {
			StoreInRecordEntity se = new StoreInRecordEntity();
			se.setUsercardid(list.iterator().next().getUsercardid());
			se.setTerminalid(list.iterator().next().getTerminalid());
			se.setBoxid(list.iterator().next().getBoxid());
			se.setStoreintime(list.iterator().next().getStoreintime());
			se.setState((byte)2);
			storeInDao.updateByPrimaryKeySelective(se);
			CardAndBoxBoundEntity ce = new CardAndBoxBoundEntity();
			ce.setCardid(ps.getCardID());
			ce.setSync((byte)2);
			cardDao.updateByPrimaryKeySelective(ce);
			out.setErrorCode(JCGErrorCode.ERR_OK);
			return out;
		}		
	}
	//用户补卡
	@Override
	@Transactional
	public OutParamBackCardAndBox boxIDChangeCardIDConfirm(InParamBoxIDChangeCardIDConfirm ps) {
		OutParamBackCardAndBox out = new OutParamBackCardAndBox();
		CardAndBoxBoundEntity entity = cardDao.selectByPrimaryKey(ps.getLossCardID());
		
		if (entity==null) {
			out.setErrorCode(JCGErrorCode.ERR_BOX_USER_NULL);
			return out;
		}else {
			StoreInRecordEntityExample example = new StoreInRecordEntityExample();
			StoreInRecordEntityExample.Criteria criteria = example.createCriteria();
			criteria.andUsercardidEqualTo(ps.getLossCardID());
			criteria.andStateEqualTo((byte)2);
			List<StoreInRecordEntity> list = storeInDao.selectByExample(example);
			StoreInRecordEntity se = new StoreInRecordEntity();
			se.setUsercardid(ps.getCardID());
			se.setTerminalid(entity.getTerminalid());
			se.setBoxid(entity.getBoxid());
			se.setStoreintime(ps.getStoreInTime());
			se.setState((byte)0);
			se.setUsertype("0");
			se.setMoney(list.iterator().next().getMoney());
			se.setRealmoney(list.iterator().next().getRealmoney());
			se.setMakeopcode(ps.getAccountCode());
			se.setCashierno(ps.getCashierNo());
			CardAndBoxBoundEntity ce = new CardAndBoxBoundEntity();
			ce.setCardid(ps.getCardID());
			ce.setTerminalid(entity.getTerminalid());
			ce.setBoxid(entity.getBoxid());
			ce.setMakeopcode(ps.getAccountCode());
			ce.setLastmodifytime(DateUtils.nowDate());
			ce.setMakedate(DateUtils.nowDate());
			ce.setSync((byte)0);
			CardAndBoxBoundEntity cabb = cardDao.selectByPrimaryKey(ps.getCardID());
			if (cabb==null) {
				storeInDao.insert(se);
				cardDao.insert(ce);				
				out.setErrorCode(JCGErrorCode.ERR_OK);
				return out;
			}else {
				out.setErrorCode(JCGErrorCode.ERR_PUT_CARD_USED);
				return out;
			}			
		}
	}
	//收银员结账
	@Override
	public OutParamUserCheckOutbyDay userCheckOutbyDay(InParamUserCheckOutbyDay ps) {
		OutParamUserCheckOutbyDay out = new OutParamUserCheckOutbyDay();
		StoreInRecordByCard entity = inCard.userCheckOutbyDay(ps.getAccountCode(),ps.getCashierNo());
		StoreInRecordByCard entity2 = inCard.selectSendCardSum(ps.getAccountCode(),ps.getCashierNo());		
		out.setCheckOutmoney(entity.getMoney());
		out.setCheckOutAmount(entity.getUsetime());
		out.setSendCardmoney(entity2.getMoney());
		out.setSendCardAmount(entity2.getUsetime());
		out.setErrorCode(JCGErrorCode.ERR_OK);
		return out;
	}
	//柜端修改密码
	@Override
	public OutParamStoreInConfirm updatePassword(InParamUpdatePassword ps) {
		OutParamStoreInConfirm out = new OutParamStoreInConfirm();
			AccountEntity ae = new AccountEntity();
			ae.setAccountcode(ps.getAccountCode());
			try {
				ae.setPassword(MsgUtils.getMD5(ps.getNewPassword()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			int a = accountDao.updateByPrimaryKeySelective(ae);
			if (a==1) {
				out.setErrorCode(JCGErrorCode.ERR_OK);
				return out;
			}else {
				out.setErrorCode(JCGErrorCode.ERR_ILLIGAL_INSTRUCTION);
				return out;
			}						
	}
	
    //直接补卡
	@Override
	public OutParamBoxIDCardLossRequest boxIDCardChangeRequest(InParamBoxIDCardLossRequest ps) {
		OutParamBoxIDCardLossRequest out = new OutParamBoxIDCardLossRequest();
		OutParamBoxIDCardLossRequest store = inCard.boxIDCardChangeRequest(ps.getDispalyname());
		if (store==null) {
			out.setErrorCode(JCGErrorCode.ERR_GET_CARD_NULL);
			return out;
		}else {
			out.setAreaName(store.getAreaName());
			out.setCardID(store.getCardID());
			out.setDispalyName(store.getDispalyName());
			out.setDisplayName(store.getDisplayName());
			out.setStoreInTime(store.getStoreInTime());
			out.setPaymentedMoney(store.getPaymentedMoney());
			out.setErrorCode(JCGErrorCode.ERR_OK);
			return out;
		}
	}
}
