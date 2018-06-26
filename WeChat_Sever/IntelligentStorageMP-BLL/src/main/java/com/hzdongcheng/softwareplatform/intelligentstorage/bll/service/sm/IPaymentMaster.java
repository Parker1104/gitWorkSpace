package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentMasterEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntityExample;
/**
 * 
 * @author wenheju
 *   收费主表
 *
 */
public interface IPaymentMaster {
	   //添加或者更新收费标准信息
		void saveOrUpdate(PaymentMasterEntity paymentMasterEntity);
		
		PaymentMasterEntity get(Integer masterID);

		PaymentMasterEntity getChargeCode(Integer chargeCode);

		//查询所有收费标准信息
		List<PaymentMasterEntity> findAll();

        //查询所有（分页）
		
		List<PaymentMasterEx> selectAllPaymentMaster(PaymentMasterEntityExample example);

		int count(PaymentMasterEntityExample example);

		void delete(Integer masterID);
		

		
}
