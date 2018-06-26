package com.hzdongcheng.softwareplatform.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
@Controller
@RequestMapping("/switchController")
public class SwitchController {
	
	@Autowired CookieLocaleResolver resolver;
    
    //@Autowired SessionLocaleResolver resolver;
     
    /**
     * 语言切换
     */
	@RequestMapping(value = "/language.do" , produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
    public ModelAndView language(HttpServletRequest request,HttpServletResponse response,String switch_1){
         
		switch_1=switch_1.toLowerCase();
        if(switch_1==null||switch_1.equals("")){
            return new ModelAndView("redirect:/");
        }else{
            if(switch_1.equals("1")){
                resolver.setLocale(request, response, Locale.CHINA );
            }else if(switch_1.equals("2")){
                resolver.setLocale(request, response, Locale.ENGLISH );
            }else{
                resolver.setLocale(request, response, Locale.CHINA );
            }
        }   
        return new ModelAndView("redirect:/");
    }
}