package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntityExample;

public interface IGradeService {
	
	void delete(String terminalid, String subdepartment);

	long count(GradeEntityExample example);

	List<GradeEntity> select(GradeEntityExample example);

	void insert(GradeEntity gradeEntity);

	void update(GradeEntity gradeEntity);
	
	GradeEntity selectGrade(String terminalid,String subdepartment);

}
