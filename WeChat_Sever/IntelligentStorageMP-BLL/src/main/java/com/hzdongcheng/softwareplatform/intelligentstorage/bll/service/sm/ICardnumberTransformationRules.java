package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardTransRuleEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntityExample;
/**
 * 
 * @author 文贺举
 *   卡号转换规则 接口
 */
public interface ICardnumberTransformationRules {
    //添加或者更新卡号转换规则
	void saveOrUpdate(CardTransRuleEntity cardTransRuleEntity);

	//根据角色code获取卡号转换规则信息
	CardTransRuleEntity get(Integer transRuleCode);

	//根据角色code删除卡号转换规则信息
	void delete(Integer transRuleCode);

	//查询所有卡号转换规则信息
	List<CardTransRuleEntity> findAll();

	//查询所有卡号转换规则信息(支持分页)
	int count(CardTransRuleEntityExample example);

	List<CardTransRuleEx> findByExampleEx(CardTransRuleEntityExample example);
}
