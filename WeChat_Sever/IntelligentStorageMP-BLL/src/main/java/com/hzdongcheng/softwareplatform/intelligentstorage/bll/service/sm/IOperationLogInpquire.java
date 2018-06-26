package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;


import java.util.Date;
import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OperatorDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntityExample;
/**
 * 
 * @author wenheju
 *   操作日志   接口
 */

public interface IOperationLogInpquire {
    //添加或者更新操作日志
	void saveOrUpdate(OperatorDiaryEntity operatorDiaryEntity);

	//根据角色code获取操作日志信息
	OperatorDiaryEntity get(String accountCode);

	//根据角色code删除操作日志信息
	void delete(String accountCode, Date date);

	//查询所有操作日志信息
	List<OperatorDiaryEntity> findAll();

	//查询所有操作日志信息(支持分页)
	int count(OperatorDiaryEntityExample example);

	List<OperatorDiaryEx> findByExampleEx(OperatorDiaryEntityExample example);
	
	List<OperatorDiaryEx>  selectByExampleOperatorEx(OperatorDiaryEntityExample example);
	
	void deleteOperatorDiary(String accountCode);
}
