package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IGradeService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.GradeEntityDao;

@Service
public class GradeServiceImpl implements IGradeService{
	
    @Autowired
	private GradeEntityDao gradeEntityDao;
	
	@Override
	public void insert(GradeEntity gradeEntity) {
		// TODO Auto-generated method stub
		gradeEntityDao.insertSelective(gradeEntity);
		
	}

	@Override
	public void update(GradeEntity gradeEntity) {
		// TODO Auto-generated method stub
		gradeEntityDao.updateByPrimaryKeySelective(gradeEntity);
		
	}

	@Override
	public void delete(String terminalid, String subdepartment) {
		// TODO Auto-generated method stub
		gradeEntityDao.deleteByPrimaryKey(terminalid, subdepartment);
		
	}

	@Override
	public long count(GradeEntityExample example) {
		// TODO Auto-generated method stub
		return gradeEntityDao.countByExample(example);
	}

	@Override
	public List<GradeEntity> select(GradeEntityExample example) {
		// TODO Auto-generated method stub
		return gradeEntityDao.selectByExample(example);
	}

	@Override
	public GradeEntity selectGrade(String terminalid, String subdepartment) {
		// TODO Auto-generated method stub
		return gradeEntityDao.selectByPrimaryKey(terminalid, subdepartment);
	}

}
