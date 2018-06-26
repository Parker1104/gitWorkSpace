package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICardAndBoxBound;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardAndBoxBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardAndBoxBoundExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntityExample;
@Service
public class CardAndBoxBound implements ICardAndBoxBound {
	@Autowired
	CardAndBoxBoundExDao dao;

	@Override
	public long count(CardAndBoxBoundEntityExample example) {
		// TODO Auto-generated method stub
		return dao.countByExample(example);
	}

	@Override
	public List<CardAndBoxBoundEx> findAll(CardAndBoxBoundEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExampleEx(example);
	}

	@Override
	public void add(CardAndBoxBoundEntity card) {
		dao.insertSelective(card);
	}

	@Override
	public void update(CardAndBoxBoundEntity card) {
		dao.updateByPrimaryKeySelective(card);
	}

	@Override
	public void delete(String id) {
		dao.deleteByPrimaryKey(id);
	}

	@Override
	public CardAndBoxBoundEntity selectByCardid(String cardid) {
		return dao.selectByPrimaryKey(cardid);		
	}

	@Override
	public List<CardAndBoxBoundEx> selectBySync() {
		// TODO Auto-generated method stub
		return dao.selectBySync();
	}

	@Override
	public List<CardAndBoxBoundEntity> selectByBoxId(String terminalid, Integer boxid) {
		CardAndBoxBoundEntityExample example = new CardAndBoxBoundEntityExample();
		CardAndBoxBoundEntityExample.Criteria criteria = example.createCriteria();
		criteria.andTerminalidEqualTo(terminalid);
		criteria.andBoxidEqualTo(boxid);
		return dao.selectByExample(example);
	}
}
