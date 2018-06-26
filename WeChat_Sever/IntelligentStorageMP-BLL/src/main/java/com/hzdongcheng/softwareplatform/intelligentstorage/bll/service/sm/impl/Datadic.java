package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDatadic;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.DictEntityDao;
/**
 * 
 * @author wenheju
 * 数据字典的  实现类
 *
 */
@Service
public class Datadic implements IDatadic {
    
	@Autowired
	DictEntityDao dao;

	@Override
	public void saveOrUpdate(DictEntity dictEntity) {
		if(null == get(dictEntity.getDicttypecode(), dictEntity.getDictcode())){
			dao.insert(dictEntity);
		}else {
			dao.updateByPrimaryKeySelective(dictEntity);
		}

	}


	@Override
	public List<DictEntity> findAll() {
		DictEntityExample example = new DictEntityExample();
		return dao.selectByExample(example);	
	}


	@Override
	public DictEntity get(String dictTypeCode, Integer dictCode) {
		return dao.selectByPrimaryKey(dictTypeCode, dictCode);
	}


	@Override
	public void delete(String dictTypeCode , Integer dictCode) {
		dao.deleteByPrimaryKey(dictTypeCode, dictCode);
	}   

	@Override
	public int count(DictEntityExample example) {
		// TODO Auto-generated method stub
		return (int) dao.countByExample(example);
	}


	@Override
	public List<DictEntity> findByExampleEntity(DictEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}


	@Override
	public List<DictEntity> findAll(String dictTypeCode) {
		DictEntityExample example = new DictEntityExample();
		example.createCriteria().andDicttypecodeEqualTo(dictTypeCode);
		example.setOrderByClause("dictcode asc");
		return dao.selectByExample(example);
	}


	@Override
	public List<DictEntity> getCardType() {
		return findAll(Constant.DICT_CARDTYPE);
	}


	@Override
	public List<DictEntity> getChargecode() {
		return findAll(Constant.DICT_PAYMENTMASTERID);
	}


	@Override
	public List<DictEntity> getUsedState() {
		// TODO Auto-generated method stub
		return findAll(Constant.DICT_USEDSTATE);
	}

}
