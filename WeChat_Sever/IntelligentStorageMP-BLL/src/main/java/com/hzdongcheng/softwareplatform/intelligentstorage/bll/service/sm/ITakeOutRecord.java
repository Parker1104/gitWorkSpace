package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;

public interface ITakeOutRecord {

	Object sumMoney();//昨日成交金额

	void insert(TakeOutRecordEntity te);
	
}
