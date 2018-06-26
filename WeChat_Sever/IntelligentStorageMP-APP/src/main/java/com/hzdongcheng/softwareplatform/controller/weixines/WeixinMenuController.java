package com.hzdongcheng.softwareplatform.controller.weixines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.alibaba.fastjson.JSONObject;
import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCClearBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCLockBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCOpenBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCUnlockBox;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.HttpClient4Guotong;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.PushClientFactory;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IEquipmentRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IRemoteDevice;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IJCGAppService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.vo.TreeModelVo;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.TerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PunishRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.WxmenuEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.WxmenuEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.StoreInRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.WxmenuEntityDao;

@Controller
@RequestMapping("/weixinMenu")
public class WeixinMenuController {
	//private static String url = "tcp://127.0.0.1:55666";
	private static String url = HttpClient4Guotong.tcpUrl;
	
	@Autowired
	private IRemoteDevice device;
	@Autowired
	private IEquipmentRecord iEquipmentRecord;
	@Autowired
	private TerminalExDao terminalExDao;
	@Autowired
	private StoreInRecordEntityDao storeInRecordEntityDao;
	@Autowired
	private PunishRecordExDao PunishRecordExDao;
	@Autowired
	@Qualifier("IJCGAppService") 
	IJCGAppService impl;
	boolean bconnectFlag = false;
 
	@Autowired
	private WxmenuEntityDao wxmenuEntityDao;
	
 
	
	@RequestMapping("/weixinMenu")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("web/weixines/weixinMenu");
		return mv;
	}
	
	@RequestMapping(value = "/findAll.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		
		List<TreeModelVo> lst=new ArrayList<TreeModelVo>();
		//判断用户所在区域
		//取当前用户区域
		AccountEntity accountEntity = (AccountEntity) request.getSession().getAttribute("account");
		String areacode = accountEntity.getAreacode();
		//List<AreaEntity> areaList = terminalExDao.findAccountAreacold(areacode+"%");
		
		
		WxmenuEntityExample weExample=new WxmenuEntityExample();
		weExample.or().andCodesLike(areacode+"%");
	    List<WxmenuEntity>  wxmenuEntityList=wxmenuEntityDao.selectByExample(weExample);
		
		
		for(WxmenuEntity wxe:wxmenuEntityList){
			TreeModelVo vo=new TreeModelVo();
			vo.setId(wxe.getCodes());
			vo.setName( wxe.getName() );
			vo.setOpen(true);
			vo.setpId(wxe.getCodes().substring(0, wxe.getCodes().length()-2));
			lst.add(vo);
		}
		
		return JsonUtils.toJSONNoFeatures(lst);
	}
	
	
 
	@RequestMapping(value = "/updateWxmenuEntity.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String updateWxmenuEntity( WxmenuEntity wxmenuEntity ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	 
		wxmenuEntityDao.updateByPrimaryKeySelective(wxmenuEntity);
 
		//当前柜子的所有信息
		//List<TerminalEx>  terminalEx = terminalExDao.findAllTermainal(areacode+"%");
 
		return JsonUtils.toJSONString("{msg:修改成功!}");
	}
	
	
 
	@RequestMapping(value = "/selectWxmenuEntity.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectWxmenuEntity(WxmenuEntity wxmenuEntity ,HttpServletRequest request,HttpServletResponse response) throws IOException {
 
	     WxmenuEntity  wxmenuEntitys= wxmenuEntityDao.selectByPrimaryKey(wxmenuEntity.getCodes());
		 String wxmenuEntitysse= JsonUtils.toJSONString(wxmenuEntitys);
         System.out.println("   wxmenuEntitysse="+wxmenuEntitysse);
		 return  wxmenuEntitysse;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 根据区域查询  终端    柜子信息
	 * @param areaEntity
	 * @param terminalEntity
	 * @param areacode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/selsctTerminalEntity.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selsctTerminalEntity(AreaEntity areaEntity, TerminalEntity terminalEntity, String areacode ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//当前柜子的所有信息
		List<TerminalEx>  terminalEx = terminalExDao.findAllTermainal(areacode+"%");
		return JsonUtils.toJSONString(terminalEx);
	}
	
 
	/**
	 * 根据区域查询所有箱子
	 * @param areaEntity
	 * @param terminalEntity
	 * @param areacode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/selsctTerminalEntityOrBox.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selsctTerminalEntityOrBox(AreaEntity areaEntity, TerminalEntity terminalEntity, String areacode ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<BoxEx> boxEx = terminalExDao.selectBoxExampleEx(areacode+"%");
		if(boxEx.size() > 0){
			return JsonUtils.toJSONString(boxEx);
		}
		return null;	
	}


	
	/**
	 * 在箱记录查询
	 * @param areaEntity
	 * @param terminalEntity
	 * @param areacode
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/selsctBeBoxCount.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void selsctBeBoxCount(AreaEntity areaEntity, TerminalEntity terminalEntity, String areacode ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		//当前柜子的所有信息
		long count = terminalExDao.findAllBeBoxCount(areacode+"%");
		jo.put("msg", count);
		response.getWriter().print(jo);
	}
	
	/**
	 * 根据柜号查询箱子信息
	 * @param areaEntity
	 * @param terminalEntity
	 * @param request
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	@RequestMapping(value = "/selsctallBox.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selsctallBox(AreaEntity areaEntity, TerminalEntity terminalEntity, String tankNo ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//当前柜子的所有信息
		List<BoxEntity> boxEntity = terminalExDao.findAllBox(tankNo);
		if(boxEntity != null){
			return JsonUtils.toJSONString(boxEntity);
		}
		return null;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	//=======================================处理微信的东西了========================================================

	/**
	 * 查询脏箱
	 * @param areaEntity
	 * @param terminalEntity
	 * @param areacode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/selsctTerminalsize.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selsctTerminalsize(AreaEntity areaEntity, TerminalEntity terminalEntity, String areacode ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//当前柜子的所有已存柜信息
		List<TakeOutRecordEntity> takeOutRecordEntity = terminalExDao.selectAllBoxAreacode(areacode+"%");
		if(takeOutRecordEntity.size()>0){
			return JsonUtils.toJSONString(takeOutRecordEntity);
		}
		return null;
	}
	
	/**
	 *  过期箱门查询
	 * @param areacode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectoverdue.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectoverdue(AreaEntity areaEntity, TerminalEntity terminalEntity, String areacode ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//当前柜子的所有已存柜信息
		List<PunishRecordEntity> punishRecordEntity = terminalExDao.selectAllOverdueBoxAreacode(areacode+"%");
		if(punishRecordEntity.size()>0){
			return JsonUtils.toJSONString(punishRecordEntity);
		}
		return null;
	}
	/**
	 * 当前区域下的终端查询
	 * @param areacode
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/selectAllFreeBox.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectAllFreeBox(String areacode ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//当前柜子的所有已存柜信息
		List<TerminalEntity> terminalEntity = terminalExDao.selectAllFreeBoxAreacode(areacode+"%");
		if(terminalEntity.size()>0){
			return JsonUtils.toJSONString(terminalEntity);
		}
		return null;
	}
	/**
	 * 根据终端号查询当前柜子的所有信息
	 * @param terminalid
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryTankno.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String queryTankno(String terminalid ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<TerminalEntity> terminalEntity = terminalExDao.queryTankno(terminalid);
		if(terminalEntity.size()>0){
			return JsonUtils.toJSONString(terminalEntity);
		}
		return null;
	}


	
	/**
	 * 超时箱
	 * @param areaEntity
	 * @param terminalEntity
	 * @param request
	 * @param response
	 * @return 
	 * @throws IOException
	 */
	@RequestMapping(value = "/selsctTimeoutBox.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selsctTimeoutBox(String terminalid, TerminalEntity terminalEntity, String tankNo ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//当前柜子的所有信息
		List<BoxEntity> boxEntity = terminalExDao.selectTimeoutBox(terminalid);
		if(boxEntity != null){
			return JsonUtils.toJSONString(boxEntity);
		}
		return null;
	
	}
	/**
	 * 根据柜号查询箱子在箱信息
	 * @param terminalid
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/selsctBoxState.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selsctBoxState(String terminalid ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//当前柜子的所有信息
		List<StoreInRecordEntity> storeInRecordEntity = terminalExDao.findBoxState(terminalid);
		if(storeInRecordEntity != null){
			return JsonUtils.toJSONString(storeInRecordEntity);
		}
		return null;
	
	}
   /**
    * 开箱
    * @param terId
    * @param startBoxNo
    * @param EndBoxNo
    * @param request
    * @param response
    * @throws IOException
    */
	@RequestMapping(value = "/openBox.do",produces = {"text/html;charset=UTF-8;"})
	public void openBox(String terId, int startBoxNo, int EndBoxNo, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();

			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
		 
			inParams.setTerminalID(terId);
			int[] iBoxNos = new int[EndBoxNo-startBoxNo+1];
			
			for (int i=startBoxNo;i<=EndBoxNo;i++) {
				iBoxNos[i-startBoxNo] = i;	
			}
			
			inParams.setBoxArray(iBoxNos);
			inParams.setFlag(0);
			
			try {
				jcgCtrl.openBox(url, inParams);
				device.openBox(terId,startBoxNo);
				//写日志
				RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
				rem.setAccountcode(acc1.getAccountcode());
				rem.setBoxid(startBoxNo);
				rem.setContent("开箱");
				rem.setDate(d);
				rem.setTerminalid(terId);
				rem.setType(1);
				rem.setMemo("远程开箱");
				iEquipmentRecord.saveOrUpdate(rem);

				System.out.println("开箱成功");
				jo.put("msg", "开箱成功");

			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "开箱失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}	
	/**
	 * 锁定
	 * @param terId
	 * @param lockBox
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/lockBox.do",produces = {"text/html;charset=UTF-8;"})
	public void lockBox(String terId, int lockBox, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			bconnectFlag =true;
			
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
			InParamRCLockBox inParams = new InParamRCLockBox();
			inParams.setTerminalID(terId);
			inParams.setBoxID(lockBox);
			
			try {
				jcgCtrl.lockBox(url, inParams);
				device.lockBox(terId,lockBox);
				System.out.println("锁定成功");
				jo.put("msg", "已锁定");
				RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
				rem.setAccountcode(acc1.getAccountcode());
				rem.setBoxid(lockBox);
				rem.setContent("锁定");
				rem.setDate(d);
				rem.setTerminalid(terId);
				rem.setType(3);
				rem.setMemo("远程锁定");
				iEquipmentRecord.saveOrUpdate(rem);
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "锁定失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "锁定失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 清箱
	 * @param terId
	 * @param clearBox
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/clearBox.do",produces = {"text/html;charset=UTF-8;"})
	public void clearBox(String terId, int clearBox, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
			InParamRCClearBox inParams = new InParamRCClearBox();
			inParams.setTerminalID(terId);
			inParams.setBoxID(clearBox);
			
			PunishRecordEntityExample Example = new PunishRecordEntityExample();
			Example.createCriteria().andTerminalidEqualTo(terId).andBoxidEqualTo(clearBox).andPunishstateEqualTo((byte)1);
			List<PunishRecordEntity> punishRecord = PunishRecordExDao.selectByExample(Example);
			if(punishRecord.size()>0){
				for(int j = 0; j<punishRecord.size();j++){
					PunishRecordExDao.deleteByPrimaryKey(punishRecord.get(j).getTerminalid(), punishRecord.get(j).getBoxid(), punishRecord.get(j).getUsercardid(), punishRecord.get(j).getStoreintime(), punishRecord.get(j).getEnddate());
				}	
			}
			
			StoreInRecordEntityExample  example = new StoreInRecordEntityExample();
			example.createCriteria().andTerminalidEqualTo(terId).andBoxidEqualTo(clearBox).andStateEqualTo((byte)0);
			List<StoreInRecordEntity> storeIn = storeInRecordEntityDao.selectByExample(example);
			if(storeIn.size()>0){
				for(int i = 0; i<storeIn.size();i++){
					storeInRecordEntityDao.deleteByPrimaryKey(storeIn.get(i).getTerminalid(), storeIn.get(i).getBoxid(), storeIn.get(i).getUsercardid(), storeIn.get(i).getStoreintime());
				}	
			}
			try {
				jcgCtrl.clearBox(url, inParams);
				jo.put("msg", "清箱成功");
				System.out.println("清箱成功");
				RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
				rem.setAccountcode(acc1.getAccountcode());
				rem.setBoxid(clearBox);
				rem.setContent("清箱");
				rem.setDate(d);
				rem.setTerminalid(terId);
				rem.setType(2);
				rem.setMemo("远程清箱");
				iEquipmentRecord.saveOrUpdate(rem);
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "清箱失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "清箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 解锁
	 * @param terId
	 * @param unlockBox
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/unlockBox.do",produces = {"text/html;charset=UTF-8;"})
	public void unlockBox(String terId, int unlockBox, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
				IPushClient pushClient = PushClientFactory.getInstance();
				IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
				 
				InParamRCUnlockBox inParams = new InParamRCUnlockBox();
				inParams.setTerminalID(terId);
				inParams.setBoxID(unlockBox);
				
				try {
					jcgCtrl.unlockBox(url, inParams);
					device.unlockBox(terId,unlockBox);
					System.out.println("解锁成功");
					jo.put("msg", "已解锁");
					RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
					rem.setAccountcode(acc1.getAccountcode());
					rem.setBoxid(unlockBox);
					rem.setContent("解锁");
					rem.setDate(d);
					rem.setTerminalid(terId);
					rem.setType(4);
					rem.setMemo("远程解锁");
					iEquipmentRecord.saveOrUpdate(rem);
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "解锁失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "解锁失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 一键开箱
	 * @param terminalid
	 * @param boxid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/opendirtyBox.do",produces = {"text/html;charset=UTF-8;"})
	public void opendirtyBox(String terminalid ,int boxid, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
			//查询所有脏箱信息
            //List<TakeOutRecordEntity> takeOutRecordEntity = terminalExDao.selectAllBoxAreacode(areaCode+"%");
	   		   inParams.setTerminalID(terminalid);
	   		   int[] boxarray = {boxid};
               inParams.setBoxArray(boxarray);
       		   inParams.setFlag(0);
           try {			            	
    			jcgCtrl.openBox(url, inParams);
	           			
				System.out.println("脏箱开启成功");
				jo.put("msg", "开箱成功");
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "开箱失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 一键空闲箱
	 * @param terminalid
	 * @param boxid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/openLeisureBox.do",produces = {"text/html;charset=UTF-8;"})
	public void openLeisureBox(String terminalid ,int boxid, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
			//查询所有空闲箱信息
            List<BoxEntity> boxEntity =  terminalExDao.selectAllFreeBoxAreacodes(terminalid);
            int[] boxarray = new int[boxEntity.size()];

            for (int i=0;i<boxEntity.size();i++) {
            	boxarray[i] = boxEntity.get(i).getBoxid();	
            }

            inParams.setTerminalID(terminalid);
            inParams.setBoxArray(boxarray);
            inParams.setFlag(0);
           try {			            	
    			jcgCtrl.openBox(url, inParams);
	           			
				System.out.println("开启成功");
				jo.put("msg", "开箱成功");
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "开箱失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 一键开过期箱
	 * @param terminalid
	 * @param boxid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/openOverdueBox.do",produces = {"text/html;charset=UTF-8;"})
	public void openOverdueBox(String terminalid ,int boxid, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
			//查询所有空闲箱信息
            List<PunishRecordEntity> punishRecordEntity =  terminalExDao.selectAllOverdueBoxAreacode(terminalid);
            int[] boxarray = new int[punishRecordEntity.size()];

            for (int i=0;i<punishRecordEntity.size();i++) {
            	boxarray[i] = punishRecordEntity.get(i).getBoxid();	
            }

            inParams.setTerminalID(terminalid);
            inParams.setBoxArray(boxarray);
            inParams.setFlag(0);
           try {			            	
    			jcgCtrl.openBox(url, inParams);
	           			
				System.out.println("开启成功");
				jo.put("msg", "已开");
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "开箱失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 一键打开已用箱门
	 * @param terminalid
	 * @param boxid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/openUsedBox.do",produces = {"text/html;charset=UTF-8;"})
	public void openUsedBox(String terminalid ,int boxid, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
			//查询所有空闲箱信息
            List<StoreInRecordEntity> storeInRecordEntity =  terminalExDao.inBox(terminalid);
            int[] boxarray = new int[storeInRecordEntity.size()];

            for (int i=0;i<storeInRecordEntity.size();i++) {
            	boxarray[i] = storeInRecordEntity.get(i).getBoxid();	
            }

            inParams.setTerminalID(terminalid);
            inParams.setBoxArray(boxarray);
            inParams.setFlag(0);
           try {			            	
    			jcgCtrl.openBox(url, inParams);
	           			
				System.out.println("开启成功");
				jo.put("msg", "已开");
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "开箱失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 一键开脏箱
	 * @param terminalid
	 * @param boxid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/akeyOpenTheBox.do",produces = {"text/html;charset=UTF-8;"})
	public void AkeyOpenTheBox(String terminalid ,int boxid, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
			//查询所有脏箱信息
            //List<TakeOutRecordEntity> takeOutRecordEntity = terminalExDao.selectAllBoxAreacode(areaCode+"%");
	   		   inParams.setTerminalID(terminalid);	   		   
	   		   int[] boxarray = {boxid};
               inParams.setBoxArray(boxarray);
       		   inParams.setFlag(0);
           try {			            	
    			jcgCtrl.openBox(url, inParams);
	           			
				System.out.println("开启成功");
				jo.put("msg", "已开");
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "开箱失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 根据柜子开所有箱子
	 * @param terminalid
	 * @param boxid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/OpenAllBox.do",produces = {"text/html;charset=UTF-8;"})
	public void OpenAllBox(String terminalid ,int boxid, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
	   		   inParams.setTerminalID(terminalid);	   		   
	   		   int[] boxarray = {0};
               inParams.setBoxArray(boxarray);
       		   inParams.setFlag(0);
           try {			            	
    			jcgCtrl.openBox(url, inParams);		
				System.out.println("开启成功");
				jo.put("msg", "开箱成功");
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
				jo.put("msg", "开箱失败");
			}
			//jo.put("msg", "已开箱");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}
	

    //开箱
	@RequestMapping(value = "/openBoxbydisplayNameOrdispalyName.do",produces = {"text/html;charset=UTF-8;"})
	public void openBoxbydisplayNameOrdispalyName(String openBoxCode,String areaCode,String displayName,String dispalyName, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
	    //String message = impl.storeRequset("C38E9963", "", "", displayName);
		String message = impl.takeRequest(openBoxCode, "", "", displayName);
		/*List<StoreInRecordEntity> storeInRecordEntity = impl.selectStoreInRecord("3882868050");
		if(storeInRecordEntity.size()>0){
			jo.put("msg", storeInRecordEntity);
		}*/
		/*List<BoxEntity> boxEntity = impl.selectEmptyBoxByAreaCode("04");
		if(boxEntity.size()>0){
			jo.put("msg", boxEntity);
		}*/
		/*List<BoxEntity> boxEntity =impl.selectFreeOpenBoxTerminalID(displayName);
		if(boxEntity.size()>0){
			jo.put("msg", boxEntity);
		}*/
	    jo.put("msg", message);
		response.getWriter().print(jo);
		/*JSONObject jo = new JSONObject();
		try {		
			iOpenBox.openBoxByName(displayName, dispalyName);
			jo.put("msg", "开箱成功");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);*/
	}
	
    //APP存物
	@RequestMapping(value = "/appStoreStuff.do",produces = {"text/html;charset=UTF-8;"})
	public void appStoreStuff(String userCardID,String areaCode,String displayName,String dispalyName, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		String message = impl.storeRequset(userCardID, "", "", displayName);
		if(message != null){
			jo.put("msg", message);
		}else {
			jo.put("msg", "开箱成功");
		}
	    
		response.getWriter().print(jo);
	}
    //APP取物
	@RequestMapping(value = "/appTakeStuff.do",produces = {"text/html;charset=UTF-8;"})
	public void appTakeStuff(String userCardID,String areaCode,String displayName,String dispalyName, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		String message = impl.takeRequest(userCardID, "", "", displayName);
		if(message != null){
			jo.put("msg", message);
		}else {
			jo.put("msg", "开箱成功");
		}
		response.getWriter().print(jo);
	}

    //根据卡号查询在箱记录
	@RequestMapping(value = "/personalRecord.do",produces = {"text/html;charset=UTF-8;"})
	public void personalRecord(String userCardID,String areaCode,String displayName,String dispalyName, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		List<StoreInRecordEntity> storeInRecordEntity = impl.selectStoreInRecord(userCardID);
		if(storeInRecordEntity.size()>0){
			jo.put("msg", storeInRecordEntity);
		}else {
			jo.put("msg", "无");
		}
		response.getWriter().print(jo);
	}
    //根据设备名称查询空箱信息
	@RequestMapping(value = "/personalRecordByDisplayName.do",produces = {"text/html;charset=UTF-8;"})
	public void personalRecordByDisplayName(String openBoxCode,String areaCode,String displayName,String dispalyName, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		List<BoxEntity> boxEntity = impl.selectFreeOpenBoxTerminalID(displayName);
		if(boxEntity.size()>0){
			jo.put("msg", boxEntity.size());
		}else {
			jo.put("msg", "0");
		}
		response.getWriter().print(jo);
	}
	//根据区域名称查询
	@RequestMapping(value = "/personalRecordByAreaCode.do",produces = {"text/html;charset=UTF-8;"})
	public void personalRecordByAreaCode(String openBoxCode,String areaCode,String displayName,String dispalyName, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8"); 
		JSONObject jo = new JSONObject();
		List<BoxEntity> boxEntity = impl.selectEmptyBoxByAreaCode(areaCode);
		if(boxEntity.size()>0){
			jo.put("msg", boxEntity.size());
		}else {
			jo.put("msg", "0");
		}
		response.getWriter().print(jo);
	}
}
