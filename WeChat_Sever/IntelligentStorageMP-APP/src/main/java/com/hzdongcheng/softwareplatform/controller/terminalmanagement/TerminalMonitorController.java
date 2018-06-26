package com.hzdongcheng.softwareplatform.controller.terminalmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaTerminal;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AreaTerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;

@Controller
@RequestMapping("/terminalMonitor")
public class TerminalMonitorController {
	@Autowired
	IAreaService area;
	@Autowired
	IAreaTerminal iTerminal;
	@Autowired
	TerminalService tService;
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("web/equipmentinfo/equipmentMonitor");
		return mv;
	}
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list() {
		List<AreaEntity> list = area.findAll();
		String json = JsonUtils.toJSONNoFeatures(list);
		return json;
	}
	@RequestMapping(value="/ztree",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String ztree() {
		List<AreaTerminalEx> list = iTerminal.select();
		String jsonString = JsonUtils.toJSONNoFeatures(list);
		System.out.println(jsonString);
		return jsonString;
	}
	@RequestMapping(value="/selectZtree",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectZtree(String areacode) {
		List<AreaTerminalEx> list = iTerminal.selectZtree(areacode);
		String jsonString = JsonUtils.toJSONNoFeatures(list);
		System.out.println(jsonString);
		return jsonString;
	}
}
