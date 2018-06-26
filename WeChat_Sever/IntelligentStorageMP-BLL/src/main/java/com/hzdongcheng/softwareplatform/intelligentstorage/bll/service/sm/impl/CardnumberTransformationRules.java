package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICardnumberTransformationRules;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardTransRuleEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardTransRuleExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntityExample;
/**
 * 
 * @author wenheju
 * 卡号转换规则 实现类
 */
@Service
public class CardnumberTransformationRules implements ICardnumberTransformationRules{
	
	@Autowired
	CardTransRuleExDao dao;
	
	@Override
	public void saveOrUpdate(CardTransRuleEntity cardTransRuleEntity) {
		if(null == get(cardTransRuleEntity.getTransrulecode())){
			dao.insert(cardTransRuleEntity);
		}else {
			dao.updateByPrimaryKeySelective(cardTransRuleEntity);
		}		
	}

	@Override
	public CardTransRuleEntity get(Integer transRuleCode) {
		return dao.selectByPrimaryKey(transRuleCode);
	}

	@Override
	public void delete(Integer transRuleCode) {
		dao.deleteByPrimaryKey(transRuleCode);
		
	}

	@Override
	public List<CardTransRuleEntity> findAll() {
		CardTransRuleEntityExample example = new CardTransRuleEntityExample();
		/*example.createCriteria().andTransrulecodeEqualTo(1);*/
		return dao.selectByExample(example);	
	}

	@Override
	public List<CardTransRuleEx> findByExampleEx(CardTransRuleEntityExample example) {
		// TODO Auto-generated method stub
		return dao.findByExampleEx(example);
	}

	@Override
	public int count(CardTransRuleEntityExample example) {
		// TODO Auto-generated method stub
		return (int) dao.countByExample(example);
	}


}
