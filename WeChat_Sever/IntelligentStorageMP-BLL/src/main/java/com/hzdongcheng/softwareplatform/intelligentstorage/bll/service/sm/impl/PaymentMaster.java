package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPaymentMaster;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentMasterEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentDetailExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentMasterExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntityExample;
@Service
public class PaymentMaster implements IPaymentMaster{
	
	@Autowired
	PaymentMasterExDao paymentMasterExDao;
	@Autowired
	PaymentDetailExDao paymentDetailExDao;
	
	@Override
	public void saveOrUpdate(PaymentMasterEntity paymentMasterEntity) {
		if(null == get(paymentMasterEntity.getMasterid())){
			paymentMasterExDao.insert(paymentMasterEntity);
		}else {
			paymentMasterExDao.updateByPrimaryKeySelective(paymentMasterEntity);
		}
		
	}
	
	@Override
	public PaymentMasterEntity get(Integer masterID) {
		return paymentMasterExDao.selectByPrimaryKey(masterID);
	}
	
	@Override
	public PaymentMasterEntity getChargeCode(Integer chargeCode) {
		return null;
	}

	@Override
	public List<PaymentMasterEntity> findAll() {
		PaymentMasterEntityExample example = new PaymentMasterEntityExample();
		/*example.createCriteria().andMasteridEqualTo(1);*/
		return paymentMasterExDao.selectByExample(example);
	}

	@Override
	public List<PaymentMasterEx> selectAllPaymentMaster(PaymentMasterEntityExample example) {
		// TODO Auto-generated method stub
		return paymentMasterExDao.selectPaymentMasterByExample(example);
	}

	@Override
	public int count(PaymentMasterEntityExample example) {
		// TODO Auto-generated method stub
		return (int) paymentMasterExDao.countByExample(example);
	}

	@Override
	public void delete(Integer masterID) {
		// TODO Auto-generated method stub
		paymentMasterExDao.deleteByPrimaryKey(masterID);
	}

}
