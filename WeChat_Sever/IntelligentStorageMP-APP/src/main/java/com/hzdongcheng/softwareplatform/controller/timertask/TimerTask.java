package com.hzdongcheng.softwareplatform.controller.timertask;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBusinessModel;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationtimeframe;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPunishRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IStoreTakeRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ITakeOutRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.timertask.ITimerTaskService;

@Component
public class TimerTask {
	@Autowired
	IStoreTakeRecord istoreRecord;
	@Autowired
	IBusinessModel iBusinessModel;
	@Autowired
	TerminalService terminalService;
	@Autowired
	IPunishRecord iPunishRecord;
	@Autowired
	IOperationtimeframe iOperationtimeframe;
	@Autowired
    ITakeOutRecord iTakeOutRecord;
	@Autowired
	ITimerTaskService timerTaskService;
	//每月15日上午10:15触发
	//@Scheduled(cron = "0 15 10 15 * ?")
	public void cardAndBoxBoundTask() {
		try{
			//timerTaskService.cardAndBoxBound();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	//超时开箱任务
	//每5分钟刷新一次
	//@Scheduled(cron = "0/30 * * * * ?")
	public void openBoxWhenBeyondTimeTask(){
		try{
			//timerTaskService.openBoxWhenTimeoutAtSpecifiedTime();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	//存物超过？？小时限制存取 天 或 多少倍于超时时间
	//@Scheduled(cron = "0/50 * * * * ?")
	public void violationTask(){	
		try{
			//生成违规记录
			//timerTaskService.generateViolationRecord(); 
			//重置自动解禁下违规有效时间
			//timerTaskService.resetViolationLiftTime();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
