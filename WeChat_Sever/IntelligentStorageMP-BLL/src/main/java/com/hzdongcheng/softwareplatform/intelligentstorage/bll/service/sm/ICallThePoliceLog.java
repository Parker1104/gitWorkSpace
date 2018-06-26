package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalAlarmDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalAlarmDiaryEntityExample;
/**
 * 
 * @author wenheju
 * 报警日志操作   接口
 */
public interface ICallThePoliceLog {
    //添加或者更新报警日志信息
	void saveOrUpdate(TerminalAlarmDiaryEntity terminalAlarmDiaryEntity);

	//根据角色code获取报警日志信息
	TerminalAlarmDiaryEntity get(String alarmCode);

	//根据角色code删除报警日志信息
	void delete(String alarmCode);

	//查询所有报警日志信息
	List<TerminalAlarmDiaryEntity> findAll();

	//查询所有报警日志信息(支持分页)
	int count(TerminalAlarmDiaryEntityExample example);

	List<TerminalAlarmDiaryEntity> findByExampleEntity(TerminalAlarmDiaryEntityExample example);


}
