package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CashierStatisticDao;

@Service
public class CashierStatisticImpl implements ICashierStatistic {
	
	@Autowired
	CashierStatisticDao dao;

	@Override
	public Page<CashierStatistic> selectCashier(Page<CashierStatistic> page) {
		List<CashierStatistic> list = dao.getList(page);
		int count = dao.getCount(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}

	@Override
	public Object sumMoeny() {
		// TODO Auto-generated method stub
		return dao.sumMoeny();
	}

	@Override
	public Object sumRealMoeny() {
		// TODO Auto-generated method stub
		return dao.sumRealMoeny();
	}

	@Override
	public Object subtract() {
		// TODO Auto-generated method stub
		return dao.subtract();
	}
}
