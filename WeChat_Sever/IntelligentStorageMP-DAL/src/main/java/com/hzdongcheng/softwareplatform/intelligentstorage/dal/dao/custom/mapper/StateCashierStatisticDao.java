package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StateCashierStatistic;

public interface StateCashierStatisticDao {
   
	List<StateCashierStatistic> getList(Page<StateCashierStatistic> page);
      
	int getCount(Page<StateCashierStatistic> page);
    
	List<StateCashierStatistic> getLists(Page<StateCashierStatistic> page);

	int getCounts(Page<StateCashierStatistic> page);
	
}
