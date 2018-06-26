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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxDoorUsage;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxDoorUsage;

/**
 * 
 * @author Administrator
 * 箱门使用率
 */
@Controller
@RequestMapping("/boxDoorUsageController")
public class BoxDoorUsageController extends BaseController{
	
	@Autowired
	IBoxDoorUsage iboxDoor;
	
	@RequestMapping("/boxDoorUsage")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/boxDoorUsage");
		return mv;
	}
	
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(BoxDoorUsage entity,HttpServletRequest request ,HttpServletResponse response){
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<BoxDoorUsage> Page = new Page<BoxDoorUsage>();
		Page.setQueryParam(entity);
		Page.setPageNo(page);
		Page.setPageSize(rows);
		Page=iboxDoor.selectBoxUse(Page);
		GridPage<BoxDoorUsage> gridPage = new GridPage<BoxDoorUsage>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
}
