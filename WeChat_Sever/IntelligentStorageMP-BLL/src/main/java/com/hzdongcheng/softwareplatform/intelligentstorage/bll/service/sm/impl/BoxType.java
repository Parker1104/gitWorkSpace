package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxType;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxSizeEntityDao;
/**
 * 
 * @author wenheju
 *
 */
@Service
public class BoxType implements IBoxType{

	@Autowired
	BoxSizeEntityDao dao;

	@Override
	public void saveOrUpdate(BoxSizeEntity boxSizeEntity) {
		if(null == get(boxSizeEntity.getBoxtypecode())){
			dao.insert(boxSizeEntity);
		}else {
			dao.updateByPrimaryKey(boxSizeEntity);
		}
	}

	@Override
	public BoxSizeEntity get(Integer boxTypeCode) {
		return dao.selectByPrimaryKey(boxTypeCode);
	}

	@Override
	public void delete(Integer boxTypeCode) {
		dao.deleteByPrimaryKey(boxTypeCode);

	}

	@Override
	public List<BoxSizeEntity> findAll() {
		BoxSizeEntityExample example = new BoxSizeEntityExample();
		/*example.createCriteria().andBoxtypecodeEqualTo(1);*/
		return dao.selectByExample(example);
	}

	@Override
	public int count(BoxSizeEntityExample example) {
		/*example.createCriteria().andAccountcodeNotEqualTo("dcdzManager");*/
		return (int) dao.countByExample(example);
	}

	@Override
	public List<BoxSizeEntity> findByExampleEntity(BoxSizeEntityExample example) {
		/*example.createCriteria().andAccountcodeNotEqualTo("dcdzManager");*/
		return dao.selectByExample(example);
	}



}
