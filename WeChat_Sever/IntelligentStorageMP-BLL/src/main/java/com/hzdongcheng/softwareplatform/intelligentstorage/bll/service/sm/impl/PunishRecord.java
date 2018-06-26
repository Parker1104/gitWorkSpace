package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPunishRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PunishRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PunishRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;
@Service
public class PunishRecord implements IPunishRecord{

	@Autowired
	PunishRecordExDao punishRecordExDao;
	
	@Override
	public void saveOrUpdate(PunishRecordEntity punishRecordEntity) {
		// TODO Auto-generated method stub
		if(null == get(punishRecordEntity.getTerminalid(), punishRecordEntity.getBoxid(),punishRecordEntity.getUsercardid(),punishRecordEntity.getStoreintime(),punishRecordEntity.getEnddate())){
			punishRecordExDao.insert(punishRecordEntity);
		}else {
			punishRecordExDao.updateByPrimaryKeySelective(punishRecordEntity);
		}
	}

	@Override
	public PunishRecordEntity get(String terminalid, Integer boxid, String usercardid, Date storeintime, Date enddate) {
		// TODO Auto-generated method stub
		return punishRecordExDao.selectByPrimaryKey(terminalid, boxid, usercardid, storeintime, enddate);
	}

	@Override
	public void delete(String terminalid, Integer boxid, String usercardid, Date storeintime, Date enddate) {
		punishRecordExDao.deleteByPrimaryKey(terminalid, boxid, usercardid, storeintime, enddate);		
	}

	@Override
	public List<PunishRecordEntity> findAll() {
		// TODO Auto-generated method stub
		PunishRecordEntityExample example = new PunishRecordEntityExample();
		return punishRecordExDao.selectByExample(example);
	}

	@Override
	public int count(PunishRecordEntityExample example) {
		// TODO Auto-generated method stub
		return (int) punishRecordExDao.countByExample(example);
	}

	@Override
	public List<PunishRecordEntity>  findByExampleEntity(PunishRecordEntityExample example) {
		// TODO Auto-generated method stub
		return punishRecordExDao.selectByExample(example);
	}

	@Override
	public List<PunishRecordEx> findByExampleEx(PunishRecordEntityExample example) {
		// TODO Auto-generated method stub
		return punishRecordExDao.selectByExampleEx(example);
	}
	
	@Override
	public void dischargePunishStatus(String terminalid, Integer boxid, String usercardid, Date storeintime){
		
		PunishRecordEntityExample example = new PunishRecordEntityExample();
		example.createCriteria().andTerminalidEqualTo(terminalid)
								.andUsercardidEqualTo(usercardid)
								.andBoxidEqualTo(boxid)
								.andStoreintimeEqualTo(storeintime);
		
		PunishRecordEntity entity = new PunishRecordEntity();
		entity.setTerminalid(terminalid);
		entity.setUsercardid(usercardid);
		entity.setBoxid(boxid);
		entity.setStoreintime(storeintime);
		entity.setEnddate(DateUtils.nowDate());
		entity.setPunishstate((byte)0);
		
		punishRecordExDao.updateByExampleSelective(entity, example);
	}

	@Override
	public Page<PunishRecordEx> selectList(Page<PunishRecordEx> page) {
		List<PunishRecordEx> list = punishRecordExDao.getList(page);
		int count = punishRecordExDao.getCount(page);
		page.setResults(list);
		page.setTotalRecord(count);
		return page;
	}
}
