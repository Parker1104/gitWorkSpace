package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationtimeframe;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.RunTimeEntityDao;
/**
 * 
 * @author wenheju
 *  运营时段信息实现类
 */
@Service
public class Operationtimeframe implements IOperationtimeframe{
	
	@Autowired
	RunTimeEntityDao dao;
	
	@Override
	public void saveOrUpdate(RunTimeEntity runTimeEntity) {
		if(null == get(runTimeEntity.getRuntimecode())){
			dao.insert(runTimeEntity);
		}else {
			dao.updateByPrimaryKey(runTimeEntity);
		}
		
	}

	@Override
	public RunTimeEntity get(Integer runTimeCode) {
		return dao.selectByPrimaryKey(runTimeCode);
	}

	@Override
	public void delete(Integer runTimeCode) {
		dao.deleteByPrimaryKey(runTimeCode);
		
	}

	@Override
	public List<RunTimeEntity> findAll() {
		RunTimeEntityExample example = new RunTimeEntityExample();
		/*example.createCriteria().andRuntimecodeEqualTo(1);*/
		return dao.selectByExample(example);	
	}

	@Override
	public int count(RunTimeEntityExample example) {
		// TODO Auto-generated method stub
		return (int) dao.countByExample(example);
	}

	@Override
	public List<RunTimeEntity> findByExampleEntity(RunTimeEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}
}
