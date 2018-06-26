package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BusinessModelEntityDao;

public interface BusinessModelEntityExDao extends BusinessModelEntityDao {
	
	@Select("select * from businessmodel GROUP BY businessCode, businessName")
	List<BusinessModelEntity> selectAllByGroup();
}
