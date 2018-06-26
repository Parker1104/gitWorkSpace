package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentDetailEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntityExample;
/**
 * 
 * @author wenheju
 *    收费从表
 */
public interface IPaymentDetail {
	//添加或者更新收费标准信息
	void saveOrUpdate(PaymentDetailEntity paymentDetailEntity);
	
	//根据code获取信息
	PaymentDetailEntity get(Integer masterID, String detailid);
	
	void insert(PaymentDetailEntity paymentDetailEntity);
	
	//根据箱子查询
	PaymentDetailEx SelectBoxType(Integer boxTypeCode);
	
	//根据主从code删除收费标准信息
	void delete(Integer masterid, String detailid);

	//查询所有收费标准信息
	List<PaymentDetailEntity> findAll();
	
	//查询所有收费标准信息（分页）
	Page<PaymentDetailEntity> listPage(Page<PaymentDetailEntity> paymentDetailPage);
	
	//查询收费主表的总数
	int count(PaymentDetailEntityExample example);

	List<PaymentDetailEx> findByExampleEntity(PaymentDetailEntityExample example);	
	
}
