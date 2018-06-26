package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ITakeOutRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;

@Controller
public class TakeOutRecord implements ITakeOutRecord {
	
	@Autowired
	TakeOutRecordEntityDao dao;
    
	//昨日成交金额
	@Override
	public Object sumMoney() {
		// TODO Auto-generated method stub
		return dao.sumMoney();
	}

	@Override
	public void insert(TakeOutRecordEntity te) {
		dao.insertSelective(te);
	}
}
