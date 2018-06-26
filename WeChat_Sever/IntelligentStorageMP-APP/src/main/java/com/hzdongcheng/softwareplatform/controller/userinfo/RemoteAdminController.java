package com.hzdongcheng.softwareplatform.controller.userinfo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCCheckInManager;
import com.hzdongcheng.front.server.model.service.jcg.down.OutParamRCCommon;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.factory.PushServiceFactory;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IManager;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IManagerCheckInRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.ManagerEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerCheckInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;

@Controller
@RequestMapping(value="/remoteAdmin")
public class RemoteAdminController extends BaseController {
	@Autowired
	IManager iManager;
	@Autowired
    IAccountManagement account;
	@Autowired
	TerminalService terminal;
	@Autowired
	IManagerCheckInRecord managerCheckIn;
	/**
	 * 远程登记管理卡页面
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/userinfo/remoteAdminCardRegistration");
		TerminalEntityExample example = new TerminalEntityExample();
		mv.addObject("terminal", terminal.findAll(example));
		return mv;
	}
	@RequestMapping(value = "/selectAdmin" , produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectAdmin(ManagerEx acc, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<ManagerEx> ManagerExPage = new Page<ManagerEx>();
		ManagerExPage.setPageSize(rows);
		ManagerExPage.setPageNo(page);
		
		int totalRecords = 0;
		    AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
			AccountEx operatorEntity = account.get(operator.getAccountcode());	
			
			//条件
			ManagerEntityExample example = new ManagerEntityExample();
			Criteria criteria = example.createCriteria();
			example.setDistinct(true);
			criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");			
			if (!StringUtils.isEmpty(acc.getManagercardid())) {
				criteria.andManagercardidEqualTo(acc.getManagercardid());
			}
			if (!StringUtils.isEmpty(acc.getManagername())) {
				criteria.andManagernameEqualTo(acc.getManagername());
			}			
			
			//查询总数
			totalRecords = (int) iManager.count(example);
			
			List<ManagerEx> ManagerList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				ManagerList = iManager.findByExampleEx(example);
			}
			
			ManagerExPage.setResults(ManagerList);
			ManagerExPage.setTotalRecord(totalRecords);
		
		GridPage<ManagerEx> gridPage = new GridPage<ManagerEx>(ManagerExPage);
		return JsonUtils.toJSONString(gridPage);		
	}
	/**
	 * 注册管理卡
	 * @param manager
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(ManagerCheckInRecordEntity manager,HttpServletResponse response) {
		IPushClient pushClient = PushServiceFactory.createInstanse();		
		pushClient.setRecvTimeoutMills(5000);
		pushClient.connect();
		IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
		InParamRCCheckInManager inParams = new InParamRCCheckInManager();
		
		inParams.setTerminalID(manager.getTerminalid());
		inParams.setCardID(manager.getManagercardid());
		inParams.setTime(DateUtils.nowDate());
		try {
			OutParamRCCommon outParamm = jcgCtrl.checkInManager("tcp://127.0.0.1:55666", inParams);
			if (outParamm.getErrorCode() == 0){
				System.out.println("注册管理卡成功");
				managerCheckIn.add(manager);
			}else{
				System.out.println("注册管理卡失败: error code is " + outParamm.getErrorCode());
			}
		} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
			e.printStackTrace();
		}
	}
}
