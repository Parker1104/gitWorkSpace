package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.Date;
import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteCtrlDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntityExample;

public interface IEquipmentRecord {
    //添加或者更新远程记录信息
	void saveOrUpdate(RemoteCtrlDiaryEntity remoteCtrlDiaryEntity);

	//根据箱编号获取远程记录信息
	RemoteCtrlDiaryEntity get(String accountCode, Date date);

	//根据箱编号删除远程记录信息
	void delete(String accountCode, Date date);

	//查询所有远程记录信息
	List<RemoteCtrlDiaryEntity> findAll();

	//查询所有远程记录信息(支持分页)
	int count(RemoteCtrlDiaryEntityExample example);

	List<RemoteCtrlDiaryEx> findByExampleEntity(RemoteCtrlDiaryEntityExample example);
}
