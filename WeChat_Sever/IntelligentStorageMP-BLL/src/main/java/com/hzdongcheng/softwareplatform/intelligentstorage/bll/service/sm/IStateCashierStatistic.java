package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StateCashierStatistic;

public interface IStateCashierStatistic {

	Page<StateCashierStatistic> selectCashier(Page<StateCashierStatistic> page);

	Page<StateCashierStatistic> selectSummary(Page<StateCashierStatistic> page);
	
}
