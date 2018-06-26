package com.hzdongcheng.softwareplatform.controller.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;

@Controller
@RequestMapping(value="/remoteUser")
public class RemoteUserController {
	@Autowired
	IBoxService box;
	@Autowired
	TerminalService terminal;
	/**
	 * 远程登记用户卡页面
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/userinfo/remoteUserCardRegistration");
		TerminalEntityExample example2 = new TerminalEntityExample();
		mv.addObject("terminal", terminal.findAll(example2));
		BoxEntityExample example = new BoxEntityExample();
		mv.addObject("box", box.findAll(example));
		return mv;
	}
}
