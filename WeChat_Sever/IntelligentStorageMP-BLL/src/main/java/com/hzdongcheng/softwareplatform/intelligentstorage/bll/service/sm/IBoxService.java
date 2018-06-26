package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;

public interface IBoxService {

	List<BoxEx> findAll(BoxEntityExample example);

	void insert(BoxEntity boxEntity);

	void update(BoxEntity boxEntity);

	BoxEx select(String terminalid, Integer boxid);

	void delete(String terminalid, Integer boxid);

	void deleteById(String id);

	long count(BoxEntityExample example);

	List<BoxEntity> selectByTerminalid(String terminalid);

	BoxEx selectByAreacode(String areacode);

	Object findArticle();

}
