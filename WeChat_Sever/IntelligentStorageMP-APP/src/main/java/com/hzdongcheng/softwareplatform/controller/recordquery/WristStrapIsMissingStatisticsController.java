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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IWristStrapHairpin;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.WristStrapHairpin;

/**
 * 
 * @author Administrator
 * 腕带丢失统计
 */
@Controller
@RequestMapping("/wristStrapIsMissingStatisticsController")
public class WristStrapIsMissingStatisticsController extends BaseController{
	
	@Autowired
	IWristStrapHairpin Iwrist;
	
	@RequestMapping("/wristStrapIsMissingStatistics")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/wristStrapIsMissingStatistics");
		return mv;
	}
	
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(WristStrapHairpin entity,HttpServletRequest request ,HttpServletResponse response){
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<WristStrapHairpin> Page = new Page<WristStrapHairpin>();
		Page.setQueryParam(entity);
		Page.setPageNo(page);
		Page.setPageSize(rows);
		Page=Iwrist.selectMiss(Page);
		GridPage<WristStrapHairpin> gridPage = new GridPage<WristStrapHairpin>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
}
