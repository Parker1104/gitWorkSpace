package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AccountEntityDao;

public interface AccountExDao extends AccountEntityDao {

	List<AccountEx> selectByExampleEx(AccountEntityExample example);
	
	AccountEx selectByPrimaryKeyEx(String accountcode);

	List<AccountEx> selectAccount();

	List<AccountEx> selectAccountRoleCode(Integer roldcode);
}
