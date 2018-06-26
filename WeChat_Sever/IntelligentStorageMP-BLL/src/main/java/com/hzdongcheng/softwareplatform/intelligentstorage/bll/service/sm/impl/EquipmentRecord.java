package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IEquipmentRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteCtrlDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.RemoteCtrlDiaryExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntityExample;

@Service
public class EquipmentRecord implements IEquipmentRecord{
	
    @Autowired
    RemoteCtrlDiaryExDao remoteCtrlDiaryExDao;
	
	@Override
	public void saveOrUpdate(RemoteCtrlDiaryEntity remoteCtrlDiaryEntity) {
		if(null == get(remoteCtrlDiaryEntity.getAccountcode(), null)){
			remoteCtrlDiaryExDao.insert(remoteCtrlDiaryEntity);
		}else {
			remoteCtrlDiaryExDao.updateByPrimaryKey(remoteCtrlDiaryEntity);
		}
		
		
	}

	@Override
	public RemoteCtrlDiaryEntity get(String accountCode,Date date) {
		return remoteCtrlDiaryExDao.selectByPrimaryKey(accountCode, date);	
	}

	@Override
	public void delete(String accountCode,Date date) {
		remoteCtrlDiaryExDao.deleteByPrimaryKey(accountCode, date);
		
	}

	@Override
	public List<RemoteCtrlDiaryEntity> findAll() {
		RemoteCtrlDiaryEntityExample example = new RemoteCtrlDiaryEntityExample();
		/*example.createCriteria().andAccountcodeEqualTo("");*/
		return remoteCtrlDiaryExDao.selectByExample(example);
	}

	@Override
	public int count(RemoteCtrlDiaryEntityExample example) {
		// TODO Auto-generated method stub
		return (int) remoteCtrlDiaryExDao.countByExample(example);
	}

	@Override
	public List<RemoteCtrlDiaryEx> findByExampleEntity(RemoteCtrlDiaryEntityExample example) {
		// TODO Auto-generated method stub
		return remoteCtrlDiaryExDao.selectByExampleEx(example);
	}

}
