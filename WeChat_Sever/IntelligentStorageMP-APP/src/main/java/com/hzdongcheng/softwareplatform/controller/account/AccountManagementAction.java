package com.hzdongcheng.softwareplatform.controller.account;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.MsgUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IRoleManagementService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
@Controller
@RequestMapping("/accountManagementAction")
public class AccountManagementAction extends BaseController {

	@Autowired
	private IAccountManagement account;
	@Autowired
	IRoleManagementService irole;
	@Autowired
	IAreaService iArea;
	@Autowired
	IOperationLogInpquire iOperationLogInpquire;

	@Autowired
	IOperationLogInpquire iOperation;

	/*private Log logger = LogFactory.getLog(this.getClass());  */

	@RequestMapping("/account") // 返回界面
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/account/account"); // 返回路径
		mv.addObject("area", iArea.findAll());
		mv.addObject("role", irole.findAll());
		return mv;
	}

	/**
	 * @param acc
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 根据编码查询所有用户信息
	 */
	@RequestMapping(value = "/selectAccountManagement.do" , produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectAccountManagement(AccountEntity acc, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*SimpleDateFormat s = new SimpleDateFormat();*/
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<AccountEx> AccountExPage = new Page<AccountEx>();
		AccountExPage.setPageSize(rows);
		AccountExPage.setPageNo(page);

		int totalRecords = 0;
		{
			AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
			AccountEx operatorEntity = account.get(operator.getAccountcode());	

			//条件
			AccountEntityExample example = new AccountEntityExample();
			example.setDistinct(true);
			Criteria criteria = example.createCriteria();
			criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");
			criteria.andAccountcodeNotEqualTo("dongcheng");
			if(!StringUtils.isEmpty(acc.getAccountname()) ){
				criteria.andAccountnameLike("%"+acc.getAccountname()+"%");
			}
			//查询总数
			totalRecords = (int) account.count(example);

			List<AccountEx> accountList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				accountList = account.findByExampleEx(example);
			}

			AccountExPage.setResults(accountList);
			AccountExPage.setTotalRecord(totalRecords);
		}

		GridPage<AccountEx> gridPage = new GridPage<AccountEx>(AccountExPage);
		return JsonUtils.toJSONString(gridPage);		
	}

	/**
	 * @param id
	 * @param request
	 * @param response
	 * @throws Exception
	 * 删除用户信息
	 */
	@RequestMapping(value = "/delectAccountManagement.do")
	@ResponseBody
	public void delectAccountManagement(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		AccountEntity accountEntity = (AccountEntity) request.getSession().getAttribute("account");
		//获取系统时间
		Date d = new Date();
		d.getTime();
		/*AccountEx aa = account.get(id);*/
		iOperationLogInpquire.deleteOperatorDiary(id);
		try {
			/*if(aa.getAreaEntity().getAreacode().length() == accountEntity.getAreacode().length()){
				jo.put("msg", "权限不足  删除失败！");
			}else {}	*/
				account.delete(id);
				/*logger.info("成功");*/
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(accountEntity.getAccountcode());
				operator.setDate(d);
				operator.setDescription("删除账号以及操作日志信息");
				operator.setModlename("账号管理");
				operator.setMemo("删除账号以及操作日志信息");
				iOperation.saveOrUpdate(operator);
				jo.put("msg", getMessage(request, "tip.success"));
				
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", getMessage(request, "tip.faul"));
		}
		response.getWriter().print(jo); 
	}
	/**
	 * @param acc
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询当前用户信息
	 */
	@RequestMapping(value = "/selectAccount.do")
	@ResponseBody
	public String selectAccount(AccountEntity acc, HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(acc.getAccountcode());
		AccountEx accountEx = account.get(acc.getAccountcode());
		String json = JsonUtils.toJSONString(accountEx);
		return json;

	}
	/**
	 * @param acc
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 	 添加 修改用户信息
	 */
	@RequestMapping(value = "/saveOrUpdateAccountManagement.do", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String saveOrUpdateAccountManagement(AccountEntity acc, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult jresult = new JsonResult();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();		
		AccountEx aa = account.get(acc.getAccountcode());
		try {
			if(aa == null){	
				acc.setPassword(MsgUtils.getMD5(acc.getPassword()));
				acc.setMakedate(d);
				acc.setLastmodifytime(d);
				acc.setMakeopcode(acc1.getAccountname());
				account.saveOrUpdate(acc);
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("新增账号");
				operator.setModlename("账号管理");
				operator.setMemo("新增账号");
				iOperation.saveOrUpdate(operator);            
			}else{
				acc.setLastmodifytime(d);	    		
				acc.setMakeopcode(acc1.getAccountname());
				account.saveOrUpdate(acc);  
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("修改账号");
				operator.setModlename("账号管理");
				operator.setMemo("修改账号");
				iOperation.saveOrUpdate(operator);
			}
			// List<RoleEntity> roleList = roledao.findAll();
			jresult.success = true;
			jresult.msg =getMessage(request, "tip.success");
			// jresult.data=JsonUtils.list2json(roleList);
		} catch (Exception e) {
			jresult.msg = e.getMessage();
		}
		return JsonUtils.toJSONString(jresult);
	}
}