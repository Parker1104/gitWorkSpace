package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IWristStrapHairpin;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.WristStrapHairpin;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.WristStrapHairpinDao;
@Service
public class WristStrapHairpinImpl implements IWristStrapHairpin {
	@Autowired
	WristStrapHairpinDao hairpinDao;

	@Override
	public Page<WristStrapHairpin> selectHairpin(Page<WristStrapHairpin> page) {
		List<WristStrapHairpin> list = hairpinDao.getList(page);
		int count = hairpinDao.getCount(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}
    //腕带丢失统计
	@Override
	public Page<WristStrapHairpin> selectMiss(Page<WristStrapHairpin> page) {
		List<WristStrapHairpin> list = hairpinDao.getMissList(page);
		int count = hairpinDao.getMissCount(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}
	@Override
	public long sendCount() {
		// TODO Auto-generated method stub
		return hairpinDao.sendCount();
	}
	@Override
	public long collectCount() {
		// TODO Auto-generated method stub
		return hairpinDao.collectCount();
	}
	@Override
	public long notCollect() {
		// TODO Auto-generated method stub
		return hairpinDao.notCollect();
	}
	@Override
	public long todaySendCount() {
		// TODO Auto-generated method stub
		return hairpinDao.todaySendCount();
	}
	@Override
	public long todayCollectCount() {
		// TODO Auto-generated method stub
		return hairpinDao.todayCollectCount();
	}
	@Override
	public long todayNotCollect() {
		// TODO Auto-generated method stub
		return hairpinDao.todayNotCollect();
	}
	@Override
	public long countMen() {
		// TODO Auto-generated method stub
		return hairpinDao.countMen();
	}
	@Override
	public long countWonmen() {
		// TODO Auto-generated method stub
		return hairpinDao.countWonmen();
	}
	
}
