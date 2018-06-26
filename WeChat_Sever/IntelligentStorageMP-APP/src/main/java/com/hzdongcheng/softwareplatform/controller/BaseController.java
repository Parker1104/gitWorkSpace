package com.hzdongcheng.softwareplatform.controller;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.filter.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

public class BaseController {


    protected String message = "";

    protected final static String MODE_UPDATE = "update";
    protected final static String MODE_ADD = "add";

    /**
     * 返回dataGrid的分页数据
     * @param request
     * @param param
     * @param value
     * @return
     */
    protected int getParamInt(HttpServletRequest request, String param,int value) {
        String s = request.getParameter(param);
        if (s == null || s.equals("") || s.equals("0")) {
            return value;
        } else {
            return Integer.parseInt(s);
        }
    }
    
    protected String getParamString(HttpServletRequest request, String param,String value) {
		String s = request.getParameter(param);
		if (s == null || s.equals("")) {
			return value;
		} else {
		return s;
		}
	}

    /**
     * 返回转发路径
     * @param commonPath
     * @param pathName
     * @return
     */
    public  String getPath(String commonPath,String pathName){
        if(StringUtils.isEmpty(commonPath)|| StringUtils.isEmpty(pathName)){
            return null;
        }
        return commonPath+"/"+pathName;
    }

    /**
     * 返回重定向路径
     * @param commonPath
     * @param pathName
     * @return
     */
    public String getRedirectPath(String commonPath,String pathName) {
        if(StringUtils.isEmpty(commonPath)||StringUtils.isEmpty(pathName)){
            return null;
        }
        commonPath="redirect:"+commonPath;
        return commonPath+"/"+pathName;
    }

    /**
     * 打印map信息
     * @param map
     */
    public void printMap(Map<String,String> map){
        if(map==null||map.size()==0){
            return;
        }
        for(String key:map.keySet()){
        }
    }

    /** 基于@ExceptionHandler异常处理 */
    //@ExceptionHandler
    public String exceptionHandler(HttpServletRequest request, Exception exception) {
        request.setAttribute("exception", exception);
        // 根据不同错误转向不同页面
        if(exception instanceof RuntimeException) {
            return "error-business";
        }else {
            return "error";
        }
    }

    public ModelAndView redirectIndex(HttpServletRequest request){
        ModelAndView model=new ModelAndView();
        String sourceUrl=request.getRequestURL().toString();
        request.getSession().setAttribute("sourceUrl",sourceUrl);
        // model.setViewName("redirect:"+ Constant.REDIRECT_LOGIN_ADDRESS);
        return model;
    }

    public String getSourceUrl(HttpServletRequest request){
        String sourceUrl=(String)request.getSession().getAttribute("sourceUrl");
        return sourceUrl;
    }

    public AccountEntity getCurrentUser(HttpServletRequest request){
    	AccountEntity accountEntity = (AccountEntity)request.getSession().getAttribute(Constant.STAFFINFO);
        return accountEntity;
    }

    /**
     * 根据I18n取资源文件
     * @param request
     * @param key
     * @return
     */
    public String getMessage(HttpServletRequest request,String key){
    	RequestContext requestContext = new RequestContext(request);
		String i18n=key;
		try{
			i18n=requestContext.getMessage(key);
		}catch(Exception e){}
		if(StringUtils.isEmpty(i18n)){
			i18n=key;
		}
		return i18n;
    }
}
