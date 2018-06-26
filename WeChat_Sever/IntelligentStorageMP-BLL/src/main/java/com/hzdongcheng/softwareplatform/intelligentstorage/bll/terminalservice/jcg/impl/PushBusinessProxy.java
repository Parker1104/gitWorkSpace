package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPDepositOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.HttpClient4Guotong;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant.ErrorCode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

/**
 * Title: 智能储物柜系统
 * Description: 向设备端推送的所有业务接口
 * Copyright: Copyright (c) 2017
 * Company: 杭州东城电子有限公司
 * @version 1.0
 */
public class PushBusinessProxy {
	public static Log4jUtils log = Log4jUtils.createInstanse(PushBusinessProxy.class);
    public static ConcurrentMap<String, String>  TerminalIdOpenBoxLock = new ConcurrentHashMap<String, String>();  
 
	protected PushBusinessProxy() {}
	/***
     * 远程开箱
     * @param p
     * @return
     * @throws Exception
     */
    public static String doBusiness( InParamAPDepositOpenBox p) throws EduExceptions {
    	  if(!TerminalIdOpenBoxLock.containsKey(p.TerminalNo)){
            	TerminalIdOpenBoxLock.put(p.TerminalNo, p.TerminalNo);
          }
    	  synchronized (TerminalIdOpenBoxLock.get(p.TerminalNo)) {
	            log.info("1   APDepositOpenBox:TerminalNo="+p.TerminalNo+",BoxNo="+p.BoxNo+" ");
	            String result = HttpClient4Guotong.doSentJCGOpenBox(p.TerminalNo, p.BoxNo, p.OpenID, p.TradeWaterNo);
	            log.info("2  APDepositOpenBox:TerminalNo="+p.TerminalNo+",BoxNo="+p.BoxNo+",result="+result);
	            if(!"0".equals(result)){
	            	switch(result){
	            	case "104":
	            		throw new EduExceptions(ErrorCode.ERR_DEVICEBUSINESSBUSY);
	            	case "103":
	            		throw new EduExceptions(ErrorCode.ERR_DEVICENETWORKABNORMAL);
	            	case "201":
	            		throw new EduExceptions(ErrorCode.ERR_APP_BOXNOTEXISTS);
	            	case "101":
	            		throw new EduExceptions(ErrorCode.ERR_TERMINALNOTEXISTS);
	            	case "102":
	            		throw new EduExceptions(ErrorCode.ERR_APP_NORIGHTOPENBOX);
	            	case "105":
	            		throw new EduExceptions(ErrorCode.ERR_DEVICEConcurrent);
	            		
	            	default:
	            		throw new EduExceptions(ErrorCode.ERR_APP_OPENBOXFAIL);
	            	}
	            }
		   return "";
    	 }
    }
}







