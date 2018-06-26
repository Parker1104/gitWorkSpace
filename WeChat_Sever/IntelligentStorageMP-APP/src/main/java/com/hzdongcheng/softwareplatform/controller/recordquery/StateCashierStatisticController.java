package com.hzdongcheng.softwareplatform.controller.recordquery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IStateCashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StateCashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;

@Controller
@RequestMapping("/StateCashierStatistic")
public class StateCashierStatisticController extends BaseController {
	
	@Autowired
	IStateCashierStatistic iStateCashierStatistic;
	@Autowired
	ICashierStatistic  iCashierStatistic;
	
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/stateCashierStatistic");
		mv.addObject("money", iCashierStatistic.sumMoeny());
		mv.addObject("realmoney", iCashierStatistic.sumRealMoeny());
		mv.addObject("balance", iCashierStatistic.subtract());
		return mv;
	}
	
	@RequestMapping(value = "/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(StateCashierStatistic entity,HttpServletRequest request,HttpServletResponse response) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<StateCashierStatistic> Page = new Page<StateCashierStatistic>();
		/*if (entity.getAccountname()==null) {
			AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");		
			entity.setAccountname(operator.getAccountname());
		}*/		
		Page.setQueryParam(entity);
		Page.setPageNo(page);
		Page.setPageSize(rows);
		Page=iStateCashierStatistic.selectCashier(Page);
		GridPage<StateCashierStatistic> gridPage = new GridPage<StateCashierStatistic>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
	
	@RequestMapping("/summaryview")
	public ModelAndView summaryview() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/summaryStatistics");		
		return mv;
	}
	
	@RequestMapping(value = "/lists",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String lists(StateCashierStatistic entity,HttpServletRequest request,HttpServletResponse response) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 20);
		Page<StateCashierStatistic> Page = new Page<StateCashierStatistic>();
		Page.setQueryParam(entity);
		Page.setPageNo(page);
		Page.setPageSize(rows);
		Page=iStateCashierStatistic.selectSummary(Page);
		GridPage<StateCashierStatistic> gridPage = new GridPage<StateCashierStatistic>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
}
