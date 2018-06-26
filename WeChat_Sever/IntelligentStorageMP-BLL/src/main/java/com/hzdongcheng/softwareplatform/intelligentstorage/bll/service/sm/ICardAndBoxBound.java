package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardAndBoxBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntityExample;

public interface ICardAndBoxBound {

	long count(CardAndBoxBoundEntityExample example);

	List<CardAndBoxBoundEx> findAll(CardAndBoxBoundEntityExample example);

	void add(CardAndBoxBoundEntity card);

	void update(CardAndBoxBoundEntity card);

	void delete(String id);

	CardAndBoxBoundEntity selectByCardid(String cardid);

	List<CardAndBoxBoundEx> selectBySync();

	List<CardAndBoxBoundEntity> selectByBoxId(String terminalid, Integer boxid);
	
}
