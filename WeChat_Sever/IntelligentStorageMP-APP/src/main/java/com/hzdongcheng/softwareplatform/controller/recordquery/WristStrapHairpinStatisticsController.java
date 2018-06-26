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
 * 腕带发卡统计
 */
@Controller
@RequestMapping("/wristStrapHairpinStatisticsController")
public class WristStrapHairpinStatisticsController extends BaseController{
	@Autowired
	IWristStrapHairpin Iwrist;
	
	@RequestMapping("/wristStrapHairpinStatistics")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/wristStrapHairpinStatistics");
		mv.addObject("sendcount", Iwrist.sendCount());// 总发卡量
		mv.addObject("collectcount", Iwrist.collectCount());//总退卡量
		mv.addObject("notcollect", Iwrist.notCollect());//总未退卡量
		mv.addObject("todaysendcount", Iwrist.todaySendCount());//今日总发卡量
		mv.addObject("todaycollectcount", Iwrist.todayCollectCount());//今日总退卡量
		mv.addObject("todaynotcollect", Iwrist.todayNotCollect());//今日总未退卡量
		mv.addObject("nan", Iwrist.countMen());//男用户量
		mv.addObject("nv", Iwrist.countWonmen());//女用户量
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
		Page=Iwrist.selectHairpin(Page);
		GridPage<WristStrapHairpin> gridPage = new GridPage<WristStrapHairpin>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
}
