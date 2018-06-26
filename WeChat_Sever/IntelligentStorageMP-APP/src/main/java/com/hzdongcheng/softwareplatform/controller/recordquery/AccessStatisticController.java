package com.hzdongcheng.softwareplatform.controller.recordquery;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IStoreTakeRecord;

@Controller
@RequestMapping("/accessStatistic")
public class AccessStatisticController {
	@Autowired
	IStoreTakeRecord str;
	
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/accessRecordStatistic");
		return mv;
	}
	/**
	 * 按月统计存取记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/selectForMonth")
	public void selectForMonth(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		//List<Statistic> list = str.selectForMonth();
		//jo.put("list", list);
		response.getWriter().print(jo);
	}
}
