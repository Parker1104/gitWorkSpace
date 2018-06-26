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
@RequestMapping(value="/businessControlleres")
public class BusinessControlleres extends BaseController {
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
		mv.setViewName("/web/equipmentinfo/businessTypes");
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
