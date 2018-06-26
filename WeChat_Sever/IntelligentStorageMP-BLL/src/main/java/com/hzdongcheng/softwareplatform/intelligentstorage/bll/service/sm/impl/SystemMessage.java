package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ISystemMessage;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.SystemInfoEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.SystemInfoEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.SystemInfoEntityDao;
/**
 * 
 * @author wenheju
 * 系统信息  实现类
 */
@Service
public class SystemMessage implements ISystemMessage{
	
	@Autowired
	SystemInfoEntityDao dao;
	
	@Override
	public void saveOrUpdate(SystemInfoEntity systemInfoEntity) {
		if(null == get(systemInfoEntity.getSystemid())){
			dao.insert(systemInfoEntity);
		}else {
			dao.updateByPrimaryKey(systemInfoEntity);
		}
		
	}

	@Override
	public SystemInfoEntity get(String systemID) {
		return dao.selectByPrimaryKey(systemID);
	}

	@Override
	public void delete(String systemID) {
		dao.deleteByPrimaryKey(systemID);
		
	}

	@Override
	public List<SystemInfoEntity> findAll() {
		SystemInfoEntityExample example = new SystemInfoEntityExample();
		/*example.createCriteria().andSystemidEqualTo("");*/
		return dao.selectByExample(example);	
	}

	@Override
	public int count(SystemInfoEntityExample example) {
		// TODO Auto-generated method stub
		return (int) dao.countByExample(example);
	}

	@Override
	public List<SystemInfoEntity> findByExampleEntity(SystemInfoEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}


}
