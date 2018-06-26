package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.DepartmentEntityDao;

public interface DepartmentExDao extends DepartmentEntityDao {
    
	//@Select("select Max(id) from department where id like '${pId}%' AND LENGTH(id)=LENGTH('${pId}')+2")
	String findMaxChild(@Param("pId")String pId);
	
}
