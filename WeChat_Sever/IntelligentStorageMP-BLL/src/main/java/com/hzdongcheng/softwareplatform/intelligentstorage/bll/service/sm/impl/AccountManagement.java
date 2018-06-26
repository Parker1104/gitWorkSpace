package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.MsgUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AccountExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AccountEntityDao;

@Service
public class AccountManagement implements IAccountManagement {
	
	@Autowired
	AccountExDao dao;
	@Autowired
	AccountEntityDao accountEntityDao;
	
	/* (non Javadoc) 
	* <p>Title: count</p> 
	* <p>Description: </p> 
	* @param example
	* @return 
	* @see com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement#count(com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample) 
	*/
	@Override
	public long count(AccountEntityExample example) {
		example.createCriteria().andAccountcodeNotEqualTo("dcdzManager");
		return dao.countByExample(example);
	}
	
	@Override
	public void saveOrUpdate(AccountEntity accountEntity) {
		
		if ( null == get(accountEntity.getAccountcode())) {
			dao.insert(accountEntity);
		} else {
			dao.updateByPrimaryKeySelective(accountEntity);
		}
	}

	@Override
	public AccountEx get(String accountcode) {
		return dao.selectByPrimaryKeyEx(accountcode);
	}

	@Override
	public void delete(String accountcode) {
		dao.deleteByPrimaryKey(accountcode);
	}

	@Override
	public List<AccountEntity> findAll() {
		AccountEntityExample example = new AccountEntityExample();
		example.createCriteria().andAccountcodeNotEqualTo("dcdzManager");
		return dao.selectByExample(example);
	}

	/* (non Javadoc) 
	* <p>Title: findByExample</p> 
	* <p>Description: </p> 
	* @param example
	* @return 
	* @see com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement#findByExample(com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample) 
	*/
	@Override
	public List<AccountEx> findByExampleEx(AccountEntityExample example) {
		example.createCriteria().andAccountcodeNotEqualTo("dcdzManager");
		return dao.selectByExampleEx(example);
	}

	@Override
	public void clearPassword(String accountcode) {
		AccountEntity record = new AccountEntity();
		record.setAccountcode(accountcode);
		record.setPassword("4A7D1ED414474E4033AC29CCB8653D9B");
		dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public void changePassword(String accountcode, String newPassword) {
		AccountEntity record = new AccountEntity();
		record.setAccountcode(accountcode);
		record.setPassword(newPassword);
		dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public AccountEntity login(String accountcode, String password) {
		AccountEntity entity = accountEntityDao.selectByPrimaryKey(accountcode);
		if (entity == null){
			return entity;
		}
		
		boolean sucess = false;
		try {
			sucess = entity.getPassword().equalsIgnoreCase(MsgUtils.getMD5(password));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (sucess)
			return entity;
		
		return null;
	}
}
