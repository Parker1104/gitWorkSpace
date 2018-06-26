package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentDetailEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PaymentDetailEntityDao;

public interface PaymentDetailExDao extends PaymentDetailEntityDao{
	
	PaymentDetailEx selectByPrimaryKeyBoxType(Integer boxTypeCode);

	List<PaymentDetailEx> selectByExampleEx(PaymentDetailEntityExample example);
	
    //查询收费标准
	List<PaymentDetailEntity> getList(Page<PaymentDetailEntity> paymentDetailPage);

	int getCount(Page<PaymentDetailEntity> paymentDetailPage);

}
