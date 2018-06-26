package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICallThePoliceLog;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalAlarmDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalAlarmDiaryEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalAlarmDiaryEntityDao;
/**
 * 
 * @author 文贺举
 * 报警日志操作   实现类
 */
@Service
public class CallThePoliceLog implements ICallThePoliceLog{
	
	@Autowired
	TerminalAlarmDiaryEntityDao dao;
	
	@Override
	public void saveOrUpdate(TerminalAlarmDiaryEntity terminalAlarmDiaryEntity) {
		if(null == get(terminalAlarmDiaryEntity.getAlarmcode())){
			dao.insert(terminalAlarmDiaryEntity);
		}else {
			dao.updateByPrimaryKey(terminalAlarmDiaryEntity);
		}
		
	}

	@Override
	public TerminalAlarmDiaryEntity get(String alarmCode) {
		return dao.selectByPrimaryKey(alarmCode);
	}

	@Override
	public void delete(String alarmCode) {
		dao.deleteByPrimaryKey(alarmCode);
		
	}

	@Override
	public List<TerminalAlarmDiaryEntity> findAll() {
		TerminalAlarmDiaryEntityExample example = new TerminalAlarmDiaryEntityExample();
		/*example.createCriteria().andAlarmcodeEqualTo("1");*/
		return dao.selectByExample(example);
	}

	@Override
	public int count(TerminalAlarmDiaryEntityExample example) {
		// TODO Auto-generated method stub
		return (int) dao.countByExample(example);
	}

	@Override
	public List<TerminalAlarmDiaryEntity> findByExampleEntity(TerminalAlarmDiaryEntityExample example) {
		// TODO Auto-generated method stub
		return dao.selectByExample(example);
	}


}
