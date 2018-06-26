package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OperatorDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.OperatorDiaryEntityDao;

public interface OperatorDiaryExDao extends OperatorDiaryEntityDao{

	List<OperatorDiaryEx> selectByExampleEx(OperatorDiaryEntityExample example);

	List<OperatorDiaryEx> selectByExampleOperatorEx(OperatorDiaryEntityExample example);

	void deleteByPrimaryKeyEx(String accountCode);

}
