package com.hzdongcheng.softwareplatform.controller.reserved;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.alibaba.fastjson.JSONObject;
import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCCheckInUser;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCClearBox;
import com.hzdongcheng.front.server.model.service.jcg.down.OutParamRCCommon;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.PushClientFactory;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl.StoreTakeRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.ReservedRegistration;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BusinessModelEntityExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardTransRuleExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.UserEntityDao;
/**
 * @author WenHeJu
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @ClassName:  ReservedRegistrationController
 * @date 2017年7月24日 下午4:18:00
 */

@Controller
@RequestMapping("/reservedRegistration")
public class ReservedRegistrationController extends BaseController{
	//日志
	public static Log4jUtils logger = Log4jUtils.createInstanse(ReservedRegistrationController.class);

	private static String url = "tcp://127.0.0.1:55666";

	@Autowired
	private TerminalExDao Terminal;
	@Autowired
	private StoreInRecordExDao StoreInRecord;
	@Autowired
	private StoreTakeRecord storeTake;
	@Autowired
	private UserEntityDao userEntityDao; 
	@Autowired
	protected CardTransRuleExDao cardTransRuleExDao;
	@Autowired
	protected BusinessModelEntityExDao business;

	@RequestMapping("view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/reserved/reservedRegistration");
		TerminalEntityExample example = new TerminalEntityExample();
		mv.addObject("TerminalEntity", Terminal.selectByExample(example));
		return mv;
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/relieve",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String relieve(ReservedRegistration reserved,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonResult jresult=new JsonResult();
		//查询终端信息
		TerminalEntity terminalEntity = Terminal.selectByDisplayname(reserved.getDisplayname());

		IPushClient pushClient = PushClientFactory.getInstance();
		IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
		InParamRCClearBox inParams = new InParamRCClearBox();
		inParams.setTerminalID(terminalEntity.getTerminalid());
		inParams.setBoxID(reserved.getBoxid());

		try {
			jcgCtrl.clearBox(url, inParams);
			jresult.msg="清除成功";
		} catch (MessageSendException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (MessageRecvTimeOutException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (NotFoundNetClientException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		try {
			Date date = new Date(Long.parseLong(reserved.getMakedate()));
			String dateTime = DateUtils.datetimeToString(date);
			StoreInRecord.deleteByPrimaryKey(terminalEntity.getTerminalid(),reserved.getBoxid(), reserved.getUsercardid(), DateUtils.stringToDateTime(dateTime));
			
			jresult.msg="删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}

		return JsonUtils.toJSONNoFeatures(jresult);
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/selectTiedBox",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectTiedBox(StoreInRecordEx storeInRecordEx,HttpServletRequest request,HttpServletResponse response) throws IOException {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<StoreInRecordEx> paymentPage = new Page<StoreInRecordEx>();
		paymentPage.setQueryParam(storeInRecordEx);
		paymentPage.setPageSize(rows);
		paymentPage.setPageNo(page);
		{
			List<StoreInRecordEx> bindingBoxEx = storeTake.getList(paymentPage);
			if(bindingBoxEx.size()>0){
				for(int i = 0;i<bindingBoxEx.size(); i++ ){
					List<TerminalEntity> terminalEntity = Terminal.queryTankno(bindingBoxEx.get(i).getDisplayname());
					if(terminalEntity.size()>0){
						String d = terminalEntity.get(0).getDisplayname();
						bindingBoxEx.get(i).setDisplayname(d);
					}else {
						bindingBoxEx.get(i).setDisplayname("");
					}
				  }	           
			}		
			paymentPage.setResults(bindingBoxEx);
			paymentPage.setTotalRecord(bindingBoxEx.size());

		}

		GridPage<StoreInRecordEx> gridPage = new GridPage<StoreInRecordEx>(paymentPage);
		return JsonUtils.toJSONString(gridPage);

	}

	/**
	 * @param reservedRegistration
	 * @param request
	 * @param response
	 * @throws IOException
	 * 用户登记
	 */
	@RequestMapping(value="/addUser",produces = {"text/html;charset=UTF-8;"})
	public void addUser(ReservedRegistration reserved, HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		//查询终端信息
		TerminalEntity terminalEntity = Terminal.selectByDisplayname(reserved.getDisplayname());
		//查询用户信息
		UserEntity userEntity = userEntityDao.selectByPrimaryKey(reserved.getUsercardid());
		Date dateTime = DateUtils.nowDate();	
	    BusinessModelEntity businessModelEntity = business.selectByPrimaryKey(terminalEntity.getBusinesscode(), Constant.BT_CFG_NAME_CARDTRANSRULE);
		
		//卡号转换
		String usercardid = transCardIDByRuleCode(Integer.parseInt(businessModelEntity.getConfigvalue()), reserved.getUsercardid());
		try {
			IPushClient pushClient = PushClientFactory.getInstance();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
			InParamRCCheckInUser InParam = new InParamRCCheckInUser();
			InParam.setTerminalID(terminalEntity.getTerminalid());
			InParam.setCardID(usercardid);  
			InParam.setBoxID(reserved.getBoxid());
			InParam.setTime(dateTime);
			OutParamRCCommon out = null;
			try {
				out = jcgCtrl.checkInUser(url, InParam);
				jo.put("msg", "注册成功");
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				logger.info("注册卡失败. Terminal ID: " + terminalEntity.getTerminalid() + ", Box ID: " + reserved.getBoxid() + 
						", Card ID: " + usercardid + ". error code:" + e.getErrorCode() + "," + e.getMessage());
			}
			if(out.getErrorCode() == 0){		
				logger.info("注册卡成功. Terminal ID: " + terminalEntity.getTerminalid() + ", Box ID: " + reserved.getBoxid() + ", Card ID: " + usercardid);
			}
			System.out.println(out.getErrorCode());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}

		//获取到后插入存物记录
		StoreInRecordEntity storeIn = new StoreInRecordEntity();
		storeIn.setTerminalid(terminalEntity.getTerminalid());
		storeIn.setUsercardid(reserved.getUsercardid());//转换
		storeIn.setBoxid(reserved.getBoxid());
		storeIn.setStoreintime(dateTime);
		//用户信息插入
		if(userEntity != null){
			storeIn.setUsertype(userEntity.getUsertype());
			storeIn.setUsername(userEntity.getUsername());
			storeIn.setIdtype(userEntity.getIdtype());
			storeIn.setIdcode(userEntity.getIdcode());
			storeIn.setTelephone(userEntity.getTelephone());
			storeIn.setAddress(userEntity.getAddress());
			storeIn.setCompany(userEntity.getCompany());
			storeIn.setDepartment(userEntity.getDepartment());
			storeIn.setSubdepartment(userEntity.getSubdepartment());
		}else {
			storeIn.setUsername(reserved.getUsername());
			storeIn.setIdcode(reserved.getIdcode());
		}
		//userEntity.getEnddate()
		if(reserved.getEffectivedays() != 0){
			storeIn.setEffectivedays(DateUtils.stringToDateTime(reserved.getEnddate()));
		}else {
			storeIn.setEffectivedays(DateUtils.getMinDate());
		}
		storeIn.setMoney((float)0);
		storeIn.setRealmoney((float)0);
		storeIn.setState((byte)0);
		StoreInRecord.insertSelective(storeIn); 
		jo.put("msg", "预留成功");
		response.getWriter().print(jo);
	}	

	//卡号转换
	public String transCardIDByRuleCode(Integer cardTransRuleCode, String openboxcode)
	{
		//查看卡号的转换	
		CardTransRuleEntity cardTransRuleEntity = cardTransRuleExDao.selectByPrimaryKey(cardTransRuleCode);
		if(cardTransRuleEntity != null) {		
			//截取字符串
			//openboxcode = openboxcode.substring(cardTransRuleEntity.getStartsubstr());


			//1   十进制  2十六进制
			if(cardTransRuleEntity.getDecimalism() == 1){
				try {
					long op = Long.parseLong(openboxcode);
					openboxcode = Long.toHexString(op);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

			//倒序 13763BE8
			if (cardTransRuleEntity.getCardrule() != 1) {
				if (StringUtils.isNotEmpty(openboxcode)){
					if ( openboxcode.length() % 2 != 0 ){
						openboxcode = "0" + openboxcode;
					}
				}

				StringBuffer out = new StringBuffer();
				String tmpOpenCode = "";
				for (int i = 0; i < openboxcode.length() / 2; i++) {
					tmpOpenCode = out.insert(0, openboxcode.substring(i * 2, i * 2 + 2)).toString();
				}

				openboxcode = tmpOpenCode;
			}

			/*while (openboxcode.length() < cardTransRuleEntity.getCardlen()) {
				openboxcode = "0" + openboxcode;
			}*/

			return openboxcode;
		}

		return openboxcode;
	}

}
