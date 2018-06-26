package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IFeescale;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PaymentMasterExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntityExample;
/**
 * 
 * @author 文贺举
 *  收费标准  实现类
 *
 */
@Service
public class Feescale implements IFeescale{
	@Autowired
	PaymentMasterExDao dao;
	@Override
	public void saveOrUpdate(PaymentMasterEntity paymentMasterEntity) {
		if(null == get(paymentMasterEntity.getMasterid())){
			dao.insert(paymentMasterEntity);
		}else {
			dao.updateByPrimaryKey(paymentMasterEntity);
		}
		
	}

	@Override
	public PaymentMasterEntity get(Integer masterid) {
		return dao.selectByPrimaryKey(masterid);
	}

	@Override
	public void delete(Integer masterid) {
		dao.deleteByPrimaryKey(masterid);
		
	}

	@Override
	public List<PaymentMasterEntity> findAll() {
		PaymentMasterEntityExample example = new PaymentMasterEntityExample();
		example.createCriteria().andMasteridEqualTo(1);
		return dao.selectByExample(example);	
	}

	@Override
	public List<PaymentMasterEntity> findAll(Page page) {
		PaymentMasterEntityExample example = new PaymentMasterEntityExample();
		example.createCriteria().andMasteridEqualTo(1);
		PageHelper.startPage(page.getStartPage(), page.getPageSize());
		return dao.selectByExample(example);
	}

	@Override
	public List<PaymentMasterEntity> findAll(PaymentMasterEntityExample cdt, Page page) {
		cdt.createCriteria().andMasteridEqualTo(1);
		PageHelper.startPage(page.getStartPage(), page.getPageSize());
		return dao.selectByExample(cdt);
	}

}
