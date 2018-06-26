package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;



@Service
public class BoxService implements IBoxService {
	
	@Autowired
	BoxExDao boxExDao;

	@Override
	public void insert(BoxEntity boxEntity) {
		boxExDao.insert(boxEntity);
	}

	@Override
	public void update(BoxEntity boxEntity) {
		boxExDao.updateByPrimaryKeySelective(boxEntity);
	}

	@Override
	public BoxEx select(String terminalid, Integer boxid) {
		return boxExDao.selectByPrimaryKeys(terminalid, boxid);
	}

	@Override
	public void delete(String terminalid, Integer boxid) {
		boxExDao.deleteByPrimaryKey(terminalid, boxid);
	}

	@Override
	public void deleteById(String id) {
		BoxEntityExample example = new BoxEntityExample();
		example.createCriteria().andTerminalidEqualTo(id);
		boxExDao.deleteByExample(example);
	}

	@Override
	public List<BoxEx> findAll(BoxEntityExample example) {
		// TODO Auto-generated method stub
		return boxExDao.selectByExampleEx(example);
	}

	@Override
	public long count(BoxEntityExample example) {
		return boxExDao.countByExample(example);
	}

	@Override
	public List<BoxEntity> selectByTerminalid(String terminalid) {
		return boxExDao.selectByTerminalid(terminalid);
	}

	@Override
	public BoxEx selectByAreacode(String areacode) {
		// TODO Auto-generated method stub
		return boxExDao.selectByAreacode(areacode);
	}

	@Override
	public Object findArticle() {
		// TODO Auto-generated method stub
		return boxExDao.findArticle();
	}
}
