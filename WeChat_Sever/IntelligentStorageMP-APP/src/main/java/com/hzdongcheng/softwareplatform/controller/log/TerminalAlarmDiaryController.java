package com.hzdongcheng.softwareplatform.controller.log;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICallThePoliceLog;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalAlarmDiaryEntity;

/**
 * 
 * @author 文贺举
 * 设备报警日志      wu
 */
@Controller
@RequestMapping("/terminalAlarmDiaryController")
public class TerminalAlarmDiaryController {
	@Autowired
	private ICallThePoliceLog iCallThePoliceLog;
	/**
	 * @return
	 * 跳转页面
	 */
	@RequestMapping("/TerminalLog")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/log/TerminalLog");
		return mv;
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询所以操作日志
	 */
	@RequestMapping(value = "/selectEquipmentRecord.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectEquipmentRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<TerminalAlarmDiaryEntity> TerminalAlarmDiaryEntityList = iCallThePoliceLog.findAll();
		String json  = JsonUtils.toJSONNoFeatures(TerminalAlarmDiaryEntityList);	
    	System.out.println(json);
		return json;
	}	
}
