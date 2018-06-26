package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CashierStatistic;

public interface CashierStatisticDao {

	List<CashierStatistic> getList(Page<CashierStatistic> page);

	int getCount(Page<CashierStatistic> page);
    
	@Select("select SUM(money) from storeinrecord")
	Object sumMoeny();//总收费额
    
	@Select("select SUM(Money) from takeoutrecord")
	Object sumRealMoeny();//总退费额
    
	@Select("select (select SUM(money) from storeinrecord)+SUM(Money) from takeoutrecord")
	Object subtract();//总结余金额
	
}
