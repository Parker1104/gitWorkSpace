package com.hzdongcheng.softwareplatform.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.PropertiesUtil;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
 
public class CheckLogin implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String servletPath = request.getServletPath();
        System.out.println("1"+servletPath);
        if(checkPath(servletPath)){
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/");
        }else{
            AccountEntity tuser = (AccountEntity)request.getSession().getAttribute("account");         
            if(tuser==null){
                String timeoutPage = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + "/";
                // request.getSession(true).removeAttribute("validateCode");
                //response.sendRedirect(timeoutPage);
                response.setContentType("text/html");
                response.getWriter().write("<html><script type=\"text/javascript\">window.parent.location.href = '" + timeoutPage + "';</script><html>");
            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }

    public boolean checkPath(String servletPath){


        if(StringUtils.isEmpty(servletPath)){
            return false;
        }
        if(servletPath.indexOf("?")>-1){
            servletPath=servletPath.substring(0,servletPath.indexOf("?")-1);
        }
        List<String> pathList=new ArrayList<String>();
        //pathList.add("/");
        pathList.add("/index");
        pathList.add("/main");
        pathList.add("/registereda");
        pathList.add("/druid");
        pathList.add("/css/");
        pathList.add("/js/");
        pathList.add("/images/");
        pathList.add("/getImage");
        if(0==PropertiesUtil.test_environment()){
    	   pathList.add("/appes");
        }
      
        boolean flag=false;
        for(String path:pathList){
            if(servletPath.indexOf(path.trim())>-1){
                flag=true;
                break;
            }
        }
        if("/".equals(servletPath)){
            flag=true;
        }
        return flag;

    }

    @Override
    public void destroy() {

    }
}
