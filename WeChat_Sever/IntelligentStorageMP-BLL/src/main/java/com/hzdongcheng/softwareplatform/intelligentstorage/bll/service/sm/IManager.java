package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.ManagerEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;

public interface IManager {

    void insert(ManagerEntity managerEntity);

	ManagerEntity select(String areacode,String id);

	void update(ManagerEntity managerEntity);

	void delete(String managercardid, String areacode);

	long count(ManagerEntityExample example);

	List<ManagerEx> findByExampleEx(ManagerEntityExample example);

}
