package com.hzdongcheng.softwareplatform.controller.appmanager.proxyspublic;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appPublicDto.PublicInWXCheckTermianlNo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalserviceweixin.IAppPublicService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.util.SpringContextUtil;

public class MobileWPublicProxy {
	/**
     * 查询设备ID
     * @param ins
     * @return
     * @throws   
     */
    public    String  doBusiness( PublicInWXCheckTermianlNo ins) throws  EduExceptions {
    	IAppPublicService iAppPublicService = (IAppPublicService) SpringContextUtil.getBean("iAppPublicService");  
    	String result = iAppPublicService.doBusiness(ins);
        return result;
    }
}


























