package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;


import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.WristStrapHairpin;

public interface IWristStrapHairpin {

	Page<WristStrapHairpin> selectHairpin(Page<WristStrapHairpin> page);//腕带发卡统计

	Page<WristStrapHairpin> selectMiss(Page<WristStrapHairpin> page);//腕带丢失统计	

	long sendCount();

	long collectCount();

	long notCollect();

	long todaySendCount();

	long todayCollectCount();

	long todayNotCollect();

	long countMen();

	long countWonmen();
	
}
