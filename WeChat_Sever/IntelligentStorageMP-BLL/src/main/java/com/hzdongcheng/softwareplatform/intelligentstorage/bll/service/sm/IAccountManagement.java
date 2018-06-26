package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntityExample;

public interface IAccountManagement {
	
	//添加或者更新账号
	void saveOrUpdate(AccountEntity accountEntity);
	
	//根据账号code获取账号信息
	AccountEx get(String accountcode);
	
	//根据账号code删除账号信息
	void delete(String accountcode);
	
	//查询所有账号信息
	List<AccountEntity> findAll();
	
	//清空账户密码
	void clearPassword(String accountcode);
	
	//修改密码
	void changePassword(String accountcode, String newPassword);
	
    //登录
	AccountEntity login(String accountcode,String password);
	
	//查询所有账号信息(支持分页) 

	long count(AccountEntityExample example);

	List<AccountEx> findByExampleEx(AccountEntityExample example);
}
