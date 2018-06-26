package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IStateCashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StateCashierStatistic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StateCashierStatisticDao;

@Service
public class StateCashierStatisticImpl implements IStateCashierStatistic {
	
	@Autowired
	StateCashierStatisticDao dao;

	@Override
	public Page<StateCashierStatistic> selectCashier(Page<StateCashierStatistic> page) {		
		List<StateCashierStatistic> list = dao.getList(page);
		int count = dao.getCount(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}

	@Override
	public Page<StateCashierStatistic> selectSummary(Page<StateCashierStatistic> page) {
		List<StateCashierStatistic> list = dao.getLists(page);
		int count = dao.getCounts(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}
}
