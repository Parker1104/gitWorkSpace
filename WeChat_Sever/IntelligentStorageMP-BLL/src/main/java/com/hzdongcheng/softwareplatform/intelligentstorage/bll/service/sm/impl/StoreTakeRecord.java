package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IStoreTakeRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.StoreInRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
@Service
public class StoreTakeRecord implements IStoreTakeRecord {
    
	@Autowired
	StoreInRecordExDao dao;
	@Override
	public long count(StoreInRecordEntityExample example) {
		// TODO Auto-generated method stub
		return dao.countByExample(example);
	}

	@Override
	public List<StoreInRecordEx> findAll(StoreInRecordEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExampleEx(example);
	}
    //查询昨天箱门使用次数
	@Override
	public Object yesterdayCount() {
		// TODO Auto-generated method stub
		return dao.yesterdayCount();
	}
	//今天箱门使用次数
	@Override
	public Object todayCount() {
		// TODO Auto-generated method stub
		return dao.todayCount();
	}

	@Override
	public Page<StoreInRecordEx> selectList(Page<StoreInRecordEx> page) {
		List<StoreInRecordEx> list = dao.getList(page);
		int count = dao.getCount(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}
	@Override
	public void insert(StoreInRecordEntity storeInRecordEntity) {
		dao.insertSelective(storeInRecordEntity);
	}

	public List<StoreInRecordEx> getList(Page<StoreInRecordEx> paymentPage) {
		// TODO Auto-generated method stub
		return dao.selectReserved(paymentPage);
	}

}
