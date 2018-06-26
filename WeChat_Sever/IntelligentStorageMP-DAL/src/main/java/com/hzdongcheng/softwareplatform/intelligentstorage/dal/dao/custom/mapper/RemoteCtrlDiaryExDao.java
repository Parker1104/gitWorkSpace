package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteCtrlDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.RemoteCtrlDiaryEntityDao;

public interface RemoteCtrlDiaryExDao extends RemoteCtrlDiaryEntityDao{

	List<RemoteCtrlDiaryEx> selectByExampleEx(RemoteCtrlDiaryEntityExample example);

}
