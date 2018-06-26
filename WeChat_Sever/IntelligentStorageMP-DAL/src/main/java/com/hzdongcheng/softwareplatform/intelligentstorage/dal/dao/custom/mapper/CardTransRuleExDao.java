package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardTransRuleEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.CardTransRuleEntityDao;

public interface CardTransRuleExDao extends CardTransRuleEntityDao{

	List<CardTransRuleEx> findByExampleEx(CardTransRuleEntityExample example);
	
}
