package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntityExample;
 
 
/**
 */
public interface IAppartners {
 
	List<AppPartnerEntity> findByExampleEntity(AppPartnerEntityExample example);
	int count(AppPartnerEntityExample example);
	void saveOrUpdate(AppPartnerEntity appPartnerEntity);
    void delete(String userId ) ;
}
