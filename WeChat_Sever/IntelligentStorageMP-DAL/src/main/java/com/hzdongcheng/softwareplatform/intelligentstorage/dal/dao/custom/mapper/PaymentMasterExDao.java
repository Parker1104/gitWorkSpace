package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentMasterEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PaymentMasterEntityDao;

public interface PaymentMasterExDao extends PaymentMasterEntityDao{
	
	List<PaymentMasterEx> selectPaymentMasterByExample(PaymentMasterEntityExample example);
}
