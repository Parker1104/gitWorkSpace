package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPaymentDetail;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentDetailEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentDetailExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PaymentDetailEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PaymentMasterEntityDao;
/**
 * 
 * @author wenheju
 *  收费从表实现
 *
 */
@Service
public class PaymentDetail implements IPaymentDetail{
	@Autowired
	PaymentDetailEntityDao paymentDetailEntityDao;
	@Autowired
	PaymentDetailExDao paymentDetailExDao;
	@Autowired
	PaymentMasterEntityDao paymentMasterEntityDao;

	@Override
	public void saveOrUpdate(PaymentDetailEntity paymentDetailEntity) {
		if(null == get(paymentDetailEntity.getMasterid(),paymentDetailEntity.getDetailid())){
			paymentDetailEntityDao.insert(paymentDetailEntity);
		}else {
			paymentDetailEntityDao.updateByPrimaryKeySelective(paymentDetailEntity);
		}

	}

	@Override
	public PaymentDetailEntity get(Integer masterID,String detailid) {
		return paymentDetailEntityDao.selectByPrimaryKey(masterID, detailid);
	}

	@Override
	public void delete(Integer masterid,String detailid) {
		paymentDetailEntityDao.deleteByPrimaryKey(masterid, detailid);

	}

	@Override
	public List<PaymentDetailEntity> findAll() {
		PaymentDetailEntityExample example = new PaymentDetailEntityExample();
		return paymentDetailEntityDao.selectByExample(example);
	}

	@Override
	public Page<PaymentDetailEntity> listPage(Page<PaymentDetailEntity> paymentDetailPage) {
		List<PaymentDetailEntity> list = paymentDetailExDao.getList(paymentDetailPage);
		int count=paymentDetailExDao.getCount(paymentDetailPage);
		paymentDetailPage.setResults(list);
		paymentDetailPage.setTotalRecord(count);
		return paymentDetailPage;
	}

	@Override
	public int count(PaymentDetailEntityExample example) {
		// TODO Auto-generated method stub
		return (int) paymentDetailExDao.countByExample(example);
	}

	@Override
	public List<PaymentDetailEx> findByExampleEntity(PaymentDetailEntityExample example) {
		// TODO Auto-generated method stub
		return paymentDetailExDao.selectByExampleEx(example);
	}

	@Override
	public PaymentDetailEx SelectBoxType(Integer boxTypeCode) {
		// TODO Auto-generated method stub
		return paymentDetailExDao.selectByPrimaryKeyBoxType(boxTypeCode);
	}

	@Override
	public void insert(PaymentDetailEntity paymentDetailEntity) {
		paymentDetailEntityDao.insert(paymentDetailEntity);
	}
}
