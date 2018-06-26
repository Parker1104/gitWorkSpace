package com.hzdongcheng.softwareplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IStoreTakeRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ITakeOutRecord;

@Controller
@RequestMapping(value="/")
public class MainController extends BaseController{
	@Autowired
	IBoxService iBoxService;
	@Autowired
    ITakeOutRecord takeRecord;
	@Autowired
	IStoreTakeRecord storeRecord;
	/**
	 * 主界面初始化
	 * @return
	 */
	@RequestMapping("/myadmin.do")
	public ModelAndView list(){
		ModelAndView mv = new ModelAndView("main");
		mv.addObject("status", iBoxService.findArticle());
		return mv;
	}
	
	@RequestMapping("/default")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView("default");
		mv.addObject("status", iBoxService.findArticle());//待办（物品和箱状态）
		mv.addObject("money", takeRecord.sumMoney());//昨日成交金额
		mv.addObject("ycount",storeRecord.yesterdayCount());//昨天箱门使用次数
		mv.addObject("tcount",storeRecord.todayCount());//今天箱门使用次数
		return mv;
	}
}
