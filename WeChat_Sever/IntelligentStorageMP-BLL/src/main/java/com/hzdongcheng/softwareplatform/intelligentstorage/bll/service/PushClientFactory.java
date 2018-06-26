/**
 * All rights Reserved, Designed By Android_Robot   
 * @Title:  PushClientFactory.java   
 * @Package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: Jxing     
 * @date:   2017年5月17日 下午5:33:23   
 * @version V1.0     
 */
package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service;

import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.factory.PushServiceFactory;

/** 
* @ClassName: PushClientFactory 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Jxing 
* @date 2017年5月17日 下午5:33:23 
* @version 1.0 
*/
public class PushClientFactory {
	
	private static IPushClient pushClient = null;
	
	public static synchronized IPushClient getInstance(){
		if (pushClient == null){
			pushClient = PushServiceFactory.createInstanse();		
			pushClient.setRecvTimeoutMills(5000);
			pushClient.connect();
		}
		return pushClient;
	}
}
