package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OperatorDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.OperatorDiaryExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntityExample;
/**
 * 
 * @author 文贺举
 *  操作日志   实现类
 */
@Service
public class OperationLogInpquire implements IOperationLogInpquire{
	
	@Autowired
	OperatorDiaryExDao operatorDiaryExDao;
	
	@Override
	public void saveOrUpdate(OperatorDiaryEntity operatorDiaryEntity) {
		if(null == get(operatorDiaryEntity.getAccountcode())){
			operatorDiaryExDao.insert(operatorDiaryEntity);
		}else {
			operatorDiaryExDao.updateByPrimaryKeySelective(operatorDiaryEntity);
		}
		
	}

	@Override
	public OperatorDiaryEntity get(String accountCode) {
		return null;	
	}

	@Override
	public void delete(String accountCode,Date date) {
		operatorDiaryExDao.deleteByPrimaryKey(accountCode, date);	
	}

	@Override
	public List<OperatorDiaryEntity> findAll() {
		OperatorDiaryEntityExample example = new OperatorDiaryEntityExample();
		/*example.createCriteria().andAccountcodeEqualTo("");*/
		return operatorDiaryExDao.selectByExample(example);
	}

	@Override
	public int count(OperatorDiaryEntityExample example) {
		// TODO Auto-generated method stub
		return (int) operatorDiaryExDao.countByExample(example);
	}

	@Override
	public List<OperatorDiaryEx> findByExampleEx(OperatorDiaryEntityExample example) {
		// TODO Auto-generated method stub
		return operatorDiaryExDao.selectByExampleEx(example);
	}

	@Override
	public List<OperatorDiaryEx> selectByExampleOperatorEx(OperatorDiaryEntityExample example) {
		// TODO Auto-generated method stub
		return operatorDiaryExDao.selectByExampleOperatorEx(example);
	}

	@Override
	public void deleteOperatorDiary(String accountCode) {
		// TODO Auto-generated method stub
		operatorDiaryExDao.deleteByPrimaryKeyEx(accountCode);
		
	}

}
