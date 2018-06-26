/**
 * All rights Reserved, Designed By Android_Robot   
 * @Title:  ITimerTaskService.java   
 * @Package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: Jxing     
 * @date:   2017年5月17日 上午9:36:05   
 * @version V1.0     
 */
package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask;

/** 
* @ClassName: ITimerTaskService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Jxing 
* @date 2017年5月17日 上午9:36:05 
* @version 1.0 
*/
public interface ITimerTaskService {
	
	/**
	 * 
	* @Method Name: cardAndBoxBound 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param  
	* @return void
	 */
	void cardAndBoxBound();
	
	/**
	 * 
	* @Method Name: generateViolationRecord 
	* @Description: TODO(生成违规记录) 
	* @param  
	* @return void
	 */
	void generateViolationRecord();
	
	/**
	 * 
	* @Method Name: updateViolationLiftTime 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param  
	* @return void
	 */
	void resetViolationLiftTime();
	
	/**
	 * 
	* @Method Name: openBoxWhenTimeout 
	* @Description: TODO(指定时刻打开存物超时箱门) 
	* @param  
	* @return void
	 */
	void openBoxWhenTimeoutAtSpecifiedTime();

}
