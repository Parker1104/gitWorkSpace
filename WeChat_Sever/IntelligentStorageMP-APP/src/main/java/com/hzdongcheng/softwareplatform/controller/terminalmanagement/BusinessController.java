package com.hzdongcheng.softwareplatform.controller.terminalmanagement;

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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBusinessModel;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICardnumberTransformationRules;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDatadic;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationtimeframe;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BusinessEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.TerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;

@Controller
@RequestMapping(value="/businessController")
public class BusinessController extends BaseController {
	@Autowired
	IBusinessModel iModel;
	@Autowired
	TerminalService tService;
	@Autowired
	IOperationtimeframe time;
	@Autowired
	ICardnumberTransformationRules card;
	@Autowired
	IDatadic master;
	@Autowired
	ICardnumberTransformationRules rule;
	@Autowired
	IOperationLogInpquire iOperation;
	
	/**
	 * 业务类型设置页面
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/equipmentinfo/businessType2");
		mv.addObject("runtime",time.findAll());
		mv.addObject("card", card.findAll());
		mv.addObject("master", master.findAll("13200"));
		mv.addObject("rule", rule.findAll());
		return mv;
	}
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(BusinessModelEntity business,HttpServletRequest request) {
		
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<BusinessModelEntity> ManagerExPage = new Page<BusinessModelEntity>();
		ManagerExPage.setPageSize(rows);
		ManagerExPage.setPageNo(page);
		
		int totalRecords = 0;

		//criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");			
		if (business.getBusinessname()==null) {

		}else{

		}			

		List<BusinessModelEntity> TerminalList = null;
		//分页查询
		PageHelper.startPage(page, rows);

		TerminalList = iModel.findAllByGroup();

		totalRecords = TerminalList.size();

		ManagerExPage.setResults(TerminalList);
		ManagerExPage.setTotalRecord(totalRecords);
		
		GridPage<BusinessModelEntity> gridPage = new GridPage<BusinessModelEntity>(ManagerExPage);
		return JsonUtils.toJSONString(gridPage);
	}
	/**
	 * 业务类型新增
	 * @param business
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/addNew")
	@ResponseBody
	public void addNew(BusinessEx bEx,BusinessModelEntity business,HttpServletRequest request,HttpServletResponse response) throws IOException {
		/*int code = business.getBusinesscode();
		String name = business.getConfigname();
		BusinessModelEntity b = iModel.get(code,name);*/
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
	    try{
			String n = bEx.getBusinessname();
			business.setBusinessname(n);
			business.setConfigname("ChargeFuncSwitch");
			business.setConfigvalue(bEx.getChargefuncswitch());
			business.setMemo(bEx.getMemo());
			iModel.add(business);//收费功能
			String sf = bEx.getChargefuncswitch();
			if (sf=="0" || sf.equals("0")) {
				business.setBusinessname(n);
				business.setConfigname("ChargeMode");
				business.setConfigvalue(bEx.getChargemode());
				business.setMemo(bEx.getMemo());
				iModel.add(business);//收费模式
			}
			 
			jo.put("msg", "添加成功");
			/*OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("新增业务类型设置");
			operator.setModlename("业务场景设定");
			operator.setMemo("新增业务类型设置");
			iOperation.saveOrUpdate(operator);*/
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		response.getWriter().print(jo);
	}
	
	/**
	 * 业务修改
	 * @param be
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/updateNew")
	@ResponseBody
	public void updateNew(BusinessEx bEx,BusinessModelEntity business,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try{
			String n = bEx.getBusinessname();
			business.setBusinessname(n);
			business.setConfigname("ChargeFuncSwitch");
			business.setConfigvalue(bEx.getChargefuncswitchs());
			business.setMemo(bEx.getMemo());
			iModel.update(business);//收费功能
			String sf = bEx.getChargefuncswitchs();
			if(sf=="0" || sf.equals("0")){
				BusinessModelEntity chargeModeBean = iModel.get(bEx.getBusinesscode(), "ChargeMode");
				if (chargeModeBean == null) {
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("ChargeMode");
					business.setConfigvalue(bEx.getChargemode());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//新增收费模式
				}
				business.setBusinesscode(bEx.getBusinesscode());
				business.setBusinessname(n);
				business.setConfigname("ChargeMode");
				business.setConfigvalue(bEx.getChargemode());
				business.setMemo(bEx.getMemo());
				/*business.setMemo(iModel.get(bEx.getBusinesscode(), "ChargeMode").getMemo());*/
				iModel.update(business);//修改收费模式
			}
 
			jo.put("msg", "修改成功");
			/*OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("修改业务类型设置");
			operator.setModlename("业务场景设定");
			operator.setMemo("修改业务类型设置");
			iOperation.saveOrUpdate(operator);*/
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		response.getWriter().print(jo);
		
	}
	
	
	
	
	
	//=========================================OLD============================================
	
	
	/**
	 * 业务类型新增
	 * @param business
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/add")
	@ResponseBody
	public void add(BusinessEx bEx,BusinessModelEntity business,HttpServletRequest request,HttpServletResponse response) throws IOException {
		/*int code = business.getBusinesscode();
		String name = business.getConfigname();
		BusinessModelEntity b = iModel.get(code,name);*/
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
	    try{
			String n = bEx.getBusinessname();
			business.setBusinessname(n);
			business.setConfigname("ValidateIdentities");
			business.setConfigvalue(bEx.getValidateidentities());
			business.setMemo(bEx.getMemo());
			iModel.add(business);
			BusinessModelEntity be = iModel.findLastInsertOne();
			int id = be.getBusinesscode();
			business.setBusinesscode(id);
			business.setBusinessname(n);
			business.setConfigname("OneCardOneBox");
			business.setConfigvalue(bEx.getOnecardonebox());
			business.setMemo(bEx.getMemo());
			iModel.add(business);//一卡一箱
			business.setBusinesscode(id);
			business.setBusinessname(n);
			business.setConfigname("RunTime");
			business.setConfigvalue(bEx.getRuntime());
			business.setMemo(bEx.getMemo());
			iModel.add(business);//运行时段
			business.setBusinesscode(id);
			business.setBusinessname(n);
			business.setConfigname("Rule");
			business.setConfigvalue(bEx.getRule());
			business.setMemo(bEx.getMemo());
			iModel.add(business);//卡片转换规则
			business.setBusinesscode(id);
			business.setBusinessname(n);
			business.setConfigname("ChargeFuncSwitch");
			business.setConfigvalue(bEx.getChargefuncswitch());
			business.setMemo(bEx.getMemo());
			iModel.add(business);//收费功能
			String sf = bEx.getChargefuncswitch();
			if (sf=="0" || sf.equals("0")) {
				business.setBusinesscode(id);
				business.setBusinessname(n);
				business.setConfigname("ChargeMode");
				business.setConfigvalue(bEx.getChargemode());
				business.setMemo(bEx.getMemo());
				iModel.add(business);//收费模式
			}
			business.setBusinesscode(id);
			business.setBusinessname(n);
			business.setConfigname("LimitSwitch");
			business.setConfigvalue(bEx.getLimitswitch());
			business.setMemo(bEx.getMemo());
			iModel.add(business);//违规存包限制
			String xz = bEx.getLimitswitch();
			if (xz=="0" || xz.equals("0")) {
				business.setBusinesscode(id);
				business.setBusinessname(n);
				business.setConfigname("Limitcondition");
				business.setConfigvalue(bEx.getLimitcondition());
				business.setMemo(bEx.getMemo());
				iModel.add(business);//限制条件
				String lc = bEx.getLimitcondition();
				if (lc=="1" || lc.equals("1")) {
					business.setBusinesscode(id);
					business.setBusinessname(n);
					business.setConfigname("FreeTime");
					business.setConfigvalue(bEx.getFreetime());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//免费使用时间
					business.setBusinesscode(id);
					business.setBusinessname(n);
					business.setConfigname("TimeBetween");
					business.setConfigvalue(bEx.getTimebetween());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//存物间隔时间
					//违规限制和违规限制存取包的    开   关
					if(bEx.getViolationsLimitCheckbox() == null){
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimitCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo());
						iModel.add(business); //违规限制   功能开关
					}else {
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimitCheckbox");
						business.setConfigvalue(bEx.getViolationsLimitCheckbox());
						business.setMemo(bEx.getMemo()); 
						iModel.add(business);//违规限制   功能开关
						
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimit_1");
						business.setConfigvalue(bEx.getViolationsLimit_1());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//违规限制    超时时间
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimit_2");
						business.setConfigvalue(bEx.getViolationsLimit_2());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//违规限制    开箱时间
					}
					//违规限制和违规限制存取包的    开   关
					if(bEx.getTimeoutCheckbox() == null){
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("TimeoutCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.add(business);//违规限制存取包   功能开关
					}else {
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("TimeoutCheckbox");
						business.setConfigvalue(bEx.getTimeoutCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.add(business);  //违规限制存取包   功能开关
						/*business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("Timeout_text_1");
						business.setConfigvalue(bEx.getTimeout_text_1());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//违规限制存取包     超时*/						
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("Timeout");
						business.setConfigvalue(bEx.getTimeout());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//违规限制存取包     1天  2 小时
						String timeout_1 =  bEx.getTimeout();
						if(timeout_1=="1" || timeout_1.equals("1")){
							business.setBusinesscode(id);
							business.setBusinessname(n);
							business.setConfigname("Timeout_text_2");
							business.setConfigvalue(bEx.getTimeout_text_2());
							business.setMemo(bEx.getMemo());
							iModel.add(business);//违规限制存取包  1
						}else {
							business.setBusinesscode(id);
							business.setBusinessname(n);
							business.setConfigname("Timeout_text_3");
							business.setConfigvalue(bEx.getTimeout_text_3());
							business.setMemo(bEx.getMemo());
							iModel.add(business);//违规限制存取包  2
						}
					}
					if(bEx.getWeChatCheckbox() == null){
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("WeChatCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.add(business);//微信
					}else {
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("WeChatCheckbox");
						business.setConfigvalue(bEx.getWeChatCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//微信
					}
					
					if(bEx.getEmailCheckbox() == null){
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("EmailCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.add(business);//邮箱
					}else {
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("EmailCheckbox");
						business.setConfigvalue(bEx.getEmailCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//邮箱
					}
					
					if(bEx.getSMSCheckbox() == null){
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("SMSCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.add(business);//短信
					}else {
						business.setBusinesscode(id);
						business.setBusinessname(n);
						business.setConfigname("SMSCheckbox");
						business.setConfigvalue(bEx.getSMSCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//短信
					}
					business.setBusinesscode(id);
					business.setBusinessname(n);
					business.setConfigname("PromptTime");
					business.setConfigvalue(bEx.getPromptTime());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//过期前提示	
					business.setBusinesscode(id);
					business.setBusinessname(n);
					business.setConfigname("NoneDoorLimit");
					business.setConfigvalue(bEx.getNoneDoorLimit());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//取物后不关箱门
				}
			}
			jo.put("msg", "添加成功");
			OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("新增业务类型设置");
			operator.setModlename("业务场景设定");
			operator.setMemo("新增业务类型设置");
			iOperation.saveOrUpdate(operator);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		response.getWriter().print(jo);
	}

	/**
	 * 业务修改
	 * @param be
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/update")
	@ResponseBody
	public void name(BusinessEx bEx,BusinessModelEntity business,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try{
			String n = bEx.getBusinessname();
			business.setBusinesscode(bEx.getBusinesscode());
			business.setBusinessname(n);
			business.setConfigname("ValidateIdentities");
			business.setConfigvalue(bEx.getValidateidentities());
			business.setMemo(bEx.getMemo());
			iModel.update(business);//修改身份
			business.setBusinesscode(bEx.getBusinesscode());
			business.setBusinessname(n);
			business.setConfigname("OneCardOneBox");
			business.setConfigvalue(bEx.getOnecardonebox());
			business.setMemo(bEx.getMemo());
			iModel.update(business);//一卡一箱
			business.setBusinesscode(bEx.getBusinesscode());
			business.setBusinessname(n);
			business.setConfigname("RunTime");
			business.setConfigvalue(bEx.getRuntime());
			business.setMemo(bEx.getMemo());
			iModel.update(business);//运行时段
			business.setBusinesscode(bEx.getBusinesscode());
			business.setBusinessname(n);
			business.setConfigname("Rule");
			business.setConfigvalue(bEx.getRule());
			business.setMemo(bEx.getMemo());
			iModel.update(business);//卡片转换规则
			business.setBusinesscode(bEx.getBusinesscode());
			business.setBusinessname(n);
			business.setConfigname("ChargeFuncSwitch");
			business.setConfigvalue(bEx.getChargefuncswitchs());
			business.setMemo(bEx.getMemo());
			iModel.update(business);//收费功能
			String sf = bEx.getChargefuncswitchs();
			if(sf=="0" || sf.equals("0")){
				BusinessModelEntity chargeModeBean = iModel.get(bEx.getBusinesscode(), "ChargeMode");
				if (chargeModeBean == null) {
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("ChargeMode");
					business.setConfigvalue(bEx.getChargemode());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//新增收费模式
				}
				business.setBusinesscode(bEx.getBusinesscode());
				business.setBusinessname(n);
				business.setConfigname("ChargeMode");
				business.setConfigvalue(bEx.getChargemode());
				business.setMemo(bEx.getMemo());
				iModel.update(business);//修改收费模式
			}
			business.setBusinesscode(bEx.getBusinesscode());
			business.setBusinessname(n);
			business.setConfigname("LimitSwitch");
			business.setConfigvalue(bEx.getLimitswitchs());
			business.setMemo(bEx.getMemo());
			iModel.update(business);//违规存包限制
			String xz = bEx.getLimitswitchs();
			if (xz=="0" || xz.equals("0")) {
				BusinessModelEntity limitconditionBean = iModel.get(bEx.getBusinesscode(), "Limitcondition");
				if (limitconditionBean==null) {
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("Limitcondition");
					business.setConfigvalue(bEx.getLimitcondition());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//新增限制条件
				}
				business.setBusinesscode(bEx.getBusinesscode());
				business.setBusinessname(n);
				business.setConfigname("Limitcondition");
				business.setConfigvalue(bEx.getLimitcondition());
				business.setMemo(bEx.getMemo());
				iModel.update(business);//修改限制条件
				
				String lc = bEx.getLimitcondition();
				if (lc=="1" || lc.equals("1")) {
					BusinessModelEntity FreeTimeBean = iModel.get(bEx.getBusinesscode(), "FreeTime");
					if (FreeTimeBean==null) {
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("FreeTime");
						business.setConfigvalue(bEx.getFreetime());
						business.setMemo(bEx.getMemo());
						iModel.add(business);//新增免费使用时间
					}
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("FreeTime");
					business.setConfigvalue(bEx.getFreetime());
					business.setMemo(bEx.getMemo());
					iModel.update(business);//修改免费使用时间
				}
				
				BusinessModelEntity TimeBetweenBean = iModel.get(bEx.getBusinesscode(),"TimeBetween");
				if(TimeBetweenBean == null){
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("TimeBetween");
					business.setConfigvalue(bEx.getTimebetween());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//新增存物间隔时间
				}
				business.setBusinesscode(bEx.getBusinesscode());
				business.setBusinessname(n);
				business.setConfigname("TimeBetween");
				business.setConfigvalue(bEx.getTimebetween());
				business.setMemo(bEx.getMemo());
				iModel.update(business);//修改存物间隔时间	
				
				BusinessModelEntity violationsLimitCheckboxBean = iModel.get(bEx.getBusinesscode(),"ViolationsLimitCheckbox");
				if(violationsLimitCheckboxBean == null){
					if(bEx.getViolationsLimitCheckbox() == null){
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimitCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo());
						iModel.add(business); //违规限制   功能开关
					}else {
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimitCheckbox");
						business.setConfigvalue(bEx.getViolationsLimitCheckbox());
						business.setMemo(bEx.getMemo()); 
						iModel.update(business);//违规限制   功能开关
						BusinessModelEntity violationsLimitBeanOne = iModel.get(bEx.getBusinesscode(),"ViolationsLimit_1");
						if(violationsLimitBeanOne == null){
							business.setBusinesscode(bEx.getBusinesscode());
							business.setBusinessname(n);
							business.setConfigname("ViolationsLimit_1");
							business.setConfigvalue(bEx.getViolationsLimit_1());
							business.setMemo(bEx.getMemo());
							iModel.add(business);//新增违规限制    超时时间
						}
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimit_1");
						business.setConfigvalue(bEx.getViolationsLimit_1());
						business.setMemo(bEx.getMemo());
						iModel.update(business);//修改违规限制    超时时间
						
						BusinessModelEntity violationsLimitBeanTwo = iModel.get(bEx.getBusinesscode(),"ViolationsLimit_2");
						if(violationsLimitBeanTwo == null){
							business.setBusinesscode(bEx.getBusinesscode());
							business.setBusinessname(n);
							business.setConfigname("ViolationsLimit_2");
							business.setConfigvalue(bEx.getViolationsLimit_2());
							business.setMemo(bEx.getMemo());
							iModel.add(business);//新增违规限制    开箱时间
						}
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimit_2");
						business.setConfigvalue(bEx.getViolationsLimit_2());
						business.setMemo(bEx.getMemo());
						iModel.update(business);//修改违规限制    开箱时间
					}
				}else{								
					if(violationsLimitCheckboxBean.getConfigvalue() == null || violationsLimitCheckboxBean.getConfigvalue() == ""){
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("ViolationsLimitCheckbox");
						business.setConfigvalue(violationsLimitCheckboxBean.getConfigvalue());
						business.setMemo(bEx.getMemo());
						iModel.add(business); //违规限制   功能开关	
					}else {
						if(bEx.getViolationsLimitCheckbox() == null){	
							iModel.deleteModel(violationsLimitCheckboxBean.getBusinesscode(), violationsLimitCheckboxBean.getConfigname());
							iModel.deleteModel(violationsLimitCheckboxBean.getBusinesscode(), "ViolationsLimit_1");
							iModel.deleteModel(violationsLimitCheckboxBean.getBusinesscode(), "ViolationsLimit_2");
						}else {		
							business.setBusinesscode(bEx.getBusinesscode());
							business.setBusinessname(n);
							business.setConfigname("ViolationsLimitCheckbox");
							business.setConfigvalue(bEx.getViolationsLimitCheckbox());
							business.setMemo(bEx.getMemo()); 
							iModel.update(business);//违规限制   功能开关	
							BusinessModelEntity violationsLimitBeanOne = iModel.get(bEx.getBusinesscode(),"ViolationsLimit_1");
							if(violationsLimitBeanOne == null){
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("ViolationsLimit_1");
								business.setConfigvalue(bEx.getViolationsLimit_1());
								business.setMemo(bEx.getMemo());
								iModel.add(business);//新增违规限制    超时时间
							}
							business.setBusinesscode(bEx.getBusinesscode());
							business.setBusinessname(n);
							business.setConfigname("ViolationsLimit_1");
							business.setConfigvalue(bEx.getViolationsLimit_1());
							business.setMemo(bEx.getMemo());
							iModel.update(business);//修改违规限制    超时时间
							
							BusinessModelEntity violationsLimitBeanTwo = iModel.get(bEx.getBusinesscode(),"ViolationsLimit_2");
							if(violationsLimitBeanTwo == null){
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("ViolationsLimit_2");
								business.setConfigvalue(bEx.getViolationsLimit_2());
								business.setMemo(bEx.getMemo());
								iModel.add(business);//新增违规限制    开箱时间
							}
							business.setBusinesscode(bEx.getBusinesscode());
							business.setBusinessname(n);
							business.setConfigname("ViolationsLimit_2");
							business.setConfigvalue(bEx.getViolationsLimit_2());
							business.setMemo(bEx.getMemo());
							iModel.update(business);//修改违规限制    开箱时间
						}	
					}
			   }
				BusinessModelEntity timeoutCheckboxBean = iModel.get(bEx.getBusinesscode(),"TimeoutCheckbox");
				if(timeoutCheckboxBean == null){
					if(bEx.getTimeoutCheckbox() == null){
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("TimeoutCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.add(business);//违规限制存取包   功能开关
					}else {
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("TimeoutCheckbox");
						business.setConfigvalue(bEx.getTimeoutCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.update(business);  //违规限制存取包   功能开关
						
						BusinessModelEntity timeoutBean = iModel.get(bEx.getBusinesscode(),"Timeout");
						if(timeoutBean == null){
							business.setBusinesscode(bEx.getBusinesscode());
							business.setBusinessname(n);
							business.setConfigname("Timeout");
							business.setConfigvalue(bEx.getTimeout());
							business.setMemo(bEx.getMemo());
							iModel.add(business);//违规限制存取包     1天  2 小时
						}
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("Timeout");
						business.setConfigvalue(bEx.getTimeout());
						business.setMemo(bEx.getMemo());
						iModel.update(business);//违规限制存取包     1天  2 小时
						String timeout_1 =  bEx.getTimeout();
						if(timeout_1=="1" || timeout_1.equals("1")){
							BusinessModelEntity timeoutBeanOne = iModel.get(bEx.getBusinesscode(),"Timeout_text_2");
							if(timeoutBeanOne == null){
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_2");
								business.setConfigvalue(bEx.getTimeout_text_2());
								business.setMemo(bEx.getMemo());
								iModel.add(business);//新增 违规限制存取包  1
							}else {
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_2");
								business.setConfigvalue(bEx.getTimeout_text_2());
								business.setMemo(bEx.getMemo());
								iModel.update(business);//修改 违规限制存取包  1
							}			
							//查询判断Timeout_text_3是否存在    如果存在删除
						    BusinessModelEntityExample example = new BusinessModelEntityExample();
							example.setDistinct(true);
							example.createCriteria().andConfignameEqualTo("Timeout_text_3");
							List<BusinessModelEntity> businessModelEntity = iModel.findAll(example);
				         	if(businessModelEntity != null){
								//存在删除
								iModel.deleteModel(bEx.getBusinesscode(), "Timeout_text_3");
							}
						}else {
							BusinessModelEntity timeoutBeanTwo = iModel.get(bEx.getBusinesscode(),"Timeout_text_3");
							if(timeoutBeanTwo == null){
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_3");
								business.setConfigvalue(bEx.getTimeout_text_3());
								business.setMemo(bEx.getMemo());
								iModel.add(business);//新增  违规限制存取包  2
							}else {
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_3");
								business.setConfigvalue(bEx.getTimeout_text_3());
								business.setMemo(bEx.getMemo());
								iModel.update(business);//修改  违规限制存取包  2
							}
							//查询判断Timeout_text_2是否存在    如果存在删除
						    BusinessModelEntityExample example = new BusinessModelEntityExample();
							example.setDistinct(true);
							example.createCriteria().andConfignameEqualTo("Timeout_text_2");
							List<BusinessModelEntity> businessModelEntity = iModel.findAll(example);
							if(businessModelEntity != null){
								iModel.deleteModel(bEx.getBusinesscode(), "Timeout_text_2");
							}
						}
						
					}
				}else {
					if(bEx.getTimeoutCheckbox() == null){
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("TimeoutCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.add(business);//违规限制存取包   功能开关
					}else {
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("TimeoutCheckbox");
						business.setConfigvalue(bEx.getTimeoutCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.update(business); //违规限制存取包   功能开关
					
						BusinessModelEntity timeoutBean = iModel.get(bEx.getBusinesscode(),"Timeout");
						if(timeoutBean == null){
							business.setBusinesscode(bEx.getBusinesscode());
							business.setBusinessname(n);
							business.setConfigname("Timeout");
							business.setConfigvalue(bEx.getTimeout());
							business.setMemo(bEx.getMemo());
							iModel.add(business);//违规限制存取包     1天  2 小时
						}
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("Timeout");
						business.setConfigvalue(bEx.getTimeout());
						business.setMemo(bEx.getMemo());
						iModel.update(business);//违规限制存取包     1天  2 小时
						String timeout_1 =  bEx.getTimeout();
						if(timeout_1=="1" || timeout_1.equals("1")){
							BusinessModelEntity timeoutBeanOne = iModel.get(bEx.getBusinesscode(),"Timeout_text_2");
							if(timeoutBeanOne == null){
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_2");
								business.setConfigvalue(bEx.getTimeout_text_2());
								business.setMemo(bEx.getMemo());
								iModel.add(business);//新增 违规限制存取包  1
							}else {
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_2");
								business.setConfigvalue(bEx.getTimeout_text_2());
								business.setMemo(bEx.getMemo());
								iModel.update(business);//修改 违规限制存取包  1
							}			
							//查询判断Timeout_text_3是否存在    如果存在删除
						    BusinessModelEntityExample example = new BusinessModelEntityExample();
							example.setDistinct(true);
							example.createCriteria().andConfignameEqualTo("Timeout_text_3");
							List<BusinessModelEntity> businessModelEntity = iModel.findAll(example);
				         	if(businessModelEntity != null){
								iModel.deleteModel(bEx.getBusinesscode(), "Timeout_text_3");
							}
						}else {
							BusinessModelEntity timeoutBeanTwo = iModel.get(bEx.getBusinesscode(),"Timeout_text_3");
							if(timeoutBeanTwo == null){
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_3");
								business.setConfigvalue(bEx.getTimeout_text_3());
								business.setMemo(bEx.getMemo());
								iModel.add(business);//新增  违规限制存取包  2
							}else {
								business.setBusinesscode(bEx.getBusinesscode());
								business.setBusinessname(n);
								business.setConfigname("Timeout_text_3");
								business.setConfigvalue(bEx.getTimeout_text_3());
								business.setMemo(bEx.getMemo());
								iModel.update(business);//修改  违规限制存取包  2
							}
							//查询判断Timeout_text_2是否存在    如果存在删除
						    BusinessModelEntityExample example = new BusinessModelEntityExample();
							example.setDistinct(true);
							example.createCriteria().andConfignameEqualTo("Timeout_text_2");
							List<BusinessModelEntity> businessModelEntity = iModel.findAll(example);
							if(businessModelEntity != null){
								iModel.deleteModel(bEx.getBusinesscode(), "Timeout_text_2");
							}
						}
					}
				}
				BusinessModelEntity promptTime = iModel.get(bEx.getBusinesscode(),"PromptTime");
				if(promptTime == null){
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("PromptTime");
					business.setConfigvalue(bEx.getPromptTime());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//过期前提示
				}else {
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("PromptTime");
					business.setConfigvalue(bEx.getPromptTime());
					business.setMemo(bEx.getMemo());
					iModel.update(business);//过期前提示	
				}
				BusinessModelEntity noneDoorLimitBaen = iModel.get(bEx.getBusinesscode(),"NoneDoorLimit");
				if(noneDoorLimitBaen == null){
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("NoneDoorLimit");
					business.setConfigvalue(bEx.getNoneDoorLimit());
					business.setMemo(bEx.getMemo());
					iModel.add(business);//新增   取物后不关箱门
				}else {
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("NoneDoorLimit");
					business.setConfigvalue(bEx.getNoneDoorLimit());
					business.setMemo(bEx.getMemo());
					iModel.update(business);//修改   取物后不关箱门	
				}
				BusinessModelEntity weChatCheckbox = iModel.get(bEx.getBusinesscode(),"WeChatCheckbox");
				if(weChatCheckbox == null){
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("WeChatCheckbox");
					business.setConfigvalue("0");
					business.setMemo(bEx.getMemo()); 
					iModel.add(business);//微信
				}else {
					if(bEx.getEmailCheckbox() == null){
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("WeChatCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.update(business);//微信
					}else {
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("WeChatCheckbox");
						business.setConfigvalue(bEx.getWeChatCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.update(business);//微信
					}

				}
				
				BusinessModelEntity emailCheckbox = iModel.get(bEx.getBusinesscode(),"EmailCheckbox");
				if(emailCheckbox == null){
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("EmailCheckbox");
					business.setConfigvalue("0");
					business.setMemo(bEx.getMemo()); 
					iModel.add(business);//邮箱
				}else {
					if(bEx.getEmailCheckbox() == null){
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("EmailCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.update(business);//邮箱
					}else {
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("EmailCheckbox");
						business.setConfigvalue(bEx.getEmailCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.update(business);//邮箱
					}

				}
				
				BusinessModelEntity sMSCheckbox = iModel.get(bEx.getBusinesscode(),"SMSCheckbox");
				if(sMSCheckbox == null){
					business.setBusinesscode(bEx.getBusinesscode());
					business.setBusinessname(n);
					business.setConfigname("SMSCheckbox");
					business.setConfigvalue("0");
					business.setMemo(bEx.getMemo()); 
					iModel.add(business);//短信
				}else {
					if(bEx.getSMSCheckbox() == null){
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("SMSCheckbox");
						business.setConfigvalue("0");
						business.setMemo(bEx.getMemo()); 
						iModel.update(business);//短信
					}else {
						business.setBusinesscode(bEx.getBusinesscode());
						business.setBusinessname(n);
						business.setConfigname("SMSCheckbox");
						business.setConfigvalue(bEx.getSMSCheckbox());
						business.setMemo(bEx.getMemo());
						iModel.update(business);//短信
					}
				}
				
			}
	
			jo.put("msg", "修改成功");
			OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("修改业务类型设置");
			operator.setModlename("业务场景设定");
			operator.setMemo("修改业务类型设置");
			iOperation.saveOrUpdate(operator);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		response.getWriter().print(jo);
	}
	@RequestMapping("/updateAll")
	public void update(Integer code,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("msg", iModel.find(code));
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 业务类型删除
	 * @param code
	 * @param name
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(Integer code,String name,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		List<TerminalEx> list = tService.selectAllBycode(code);
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			if (list == null ||list.size()==0) {
				iModel.delete(code);
				jo.put("msg", "删除成功");
				/*OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("删除业务类型设置");
				operator.setModlename("业务场景设定");
				operator.setMemo("删除业务类型设置");
				iOperation.saveOrUpdate(operator);*/
			}else {
				jo.put("msg", "该业务模式正在使用");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败");
		}
		response.getWriter().print(jo);
	}
}
