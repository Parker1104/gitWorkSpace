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
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CashierStatistic;

/**
 * 
 * @author wenheju
 * 收银统计
 */
@Controller
@RequestMapping("/cashierStatisticalController")
public class CashierStatisticalController extends BaseController{
	@Autowired
	ICashierStatistic  iCashierStatistic;
	
	@RequestMapping("/cashierStatistical")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/cashierStatistical");
		mv.addObject("money", iCashierStatistic.sumMoeny());
		mv.addObject("realmoney", iCashierStatistic.sumRealMoeny());
		mv.addObject("balance", iCashierStatistic.subtract());
		return mv;
	}
	
	@RequestMapping(value = "/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(CashierStatistic entity,HttpServletRequest request,HttpServletResponse response) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<CashierStatistic> Page = new Page<CashierStatistic>();
		Page.setQueryParam(entity);
		Page.setPageNo(page);
		Page.setPageSize(rows);
		Page=iCashierStatistic.selectCashier(Page);
		GridPage<CashierStatistic> gridPage = new GridPage<CashierStatistic>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
}
