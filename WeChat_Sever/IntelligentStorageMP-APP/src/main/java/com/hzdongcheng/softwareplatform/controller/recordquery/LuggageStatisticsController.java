package com.hzdongcheng.softwareplatform.controller.recordquery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Administrator
 * 行李寄存柜统计
 */
@Controller
@RequestMapping("/luggageStatisticsController")
public class LuggageStatisticsController {
	@RequestMapping("/luggageStatistics")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/luggageStatistics");
		return mv;
	}
}
