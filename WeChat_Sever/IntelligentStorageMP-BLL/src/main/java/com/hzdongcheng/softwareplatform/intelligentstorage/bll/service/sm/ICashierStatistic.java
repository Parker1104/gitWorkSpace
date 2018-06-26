package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CashierStatistic;

public interface ICashierStatistic {

	Page<CashierStatistic> selectCashier(Page<CashierStatistic> page);

	Object sumMoeny();

	Object sumRealMoeny();

	Object subtract();
	
}
