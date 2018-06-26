package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardAndBoxBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.CardAndBoxBoundEntityDao;

public interface CardAndBoxBoundExDao extends CardAndBoxBoundEntityDao {

	List<CardAndBoxBoundEx> selectByExampleEx(CardAndBoxBoundEntityExample example);
    
	List<CardAndBoxBoundEx> selectBySync();
	
}
