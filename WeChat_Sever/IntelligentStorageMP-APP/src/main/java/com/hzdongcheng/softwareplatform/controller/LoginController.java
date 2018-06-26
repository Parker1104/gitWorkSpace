package com.hzdongcheng.softwareplatform.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMenuRightsService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.MenuRightsEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;

@Controller
@RequestMapping(value="/loginController")
public class LoginController {
	@Autowired
	IAccountManagement iAccountManagement;
	@Autowired
	IMenuRightsService menuRightsService;
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.HEAD})
    public ModelAndView webIndex(){
        ModelAndView modelAndView = new ModelAndView("/index");
        return  modelAndView;
    }
	/**
	 * 登录验证
	 * @param accountname
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="login.do")
	public ModelAndView login(String accountcode,String password,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		AccountEntity aEntity = iAccountManagement.login(accountcode,password);
		if (aEntity == null || aEntity.equals("")) {
			mv.setViewName("/index");
		}else {
			AccountEntity account = new AccountEntity();
			account.setAccountname(aEntity.getAccountname());
            account.setAccountcode(accountcode);
            account.setPassword(password);
            account.setAreacode(aEntity.getAreacode());
            account.setRolecode(aEntity.getRolecode());
            request.getSession().setAttribute(Constant.SessionAccoutID, account);
            System.out.println(request.getSession().getAttribute("account"));
			
           // mv.setViewName("redirect:/myadmin");

			//取当前角色权限
			List<MenuRightsEx> menuRightsList = menuRightsService.get(aEntity.getRolecode());
			List<String> lst=new ArrayList<String>();
			for (MenuRightsEx mr : menuRightsList) {
				String menucode=mr.getMenucode();
				lst.add(menucode);
				String buttons=mr.getMenuoperator();
				if(StringUtils.isNotEmpty(buttons)){
					for(char c : buttons.toCharArray()){
						lst.add(menucode+"_"+c);
					}
				}
			}
            request.getSession().setAttribute(Constant.SessionID, aEntity);
            request.getSession().setAttribute(Constant.SessionRoleID, lst);

            mv.setViewName("redirect:/myadmin.do");
		}
		return mv;
	}
}