package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAppartners;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AppPartnerEntityDao;
 
@Service
public class ApppartnerServiceImpl implements IAppartners {
	@Autowired
	AppPartnerEntityDao dao;
	
	@Override
	public List<AppPartnerEntity> findByExampleEntity(AppPartnerEntityExample example) {
		return dao.selectByExample(example);
	}
	@Override
	public int count(AppPartnerEntityExample example) {
		return (int) dao.countByExample(example);
	}
	@Override
	public void saveOrUpdate(AppPartnerEntity appPartnerEntity) {
		if(null ==  get(appPartnerEntity.getUserId()) ){
			dao.insert(appPartnerEntity);
		}else {
			dao.updateByPrimaryKeySelective(appPartnerEntity);
		}
	}
	public AppPartnerEntity get(String userId ) {
		return dao.selectByPrimaryKey(userId);
	}
	@Override
	public void delete(String userId ) {
		dao.deleteByPrimaryKey(userId);
	}   

}







