package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntityExample;
/**
 * 
 * @author wenheju
 *  收费标准  接口
 *
 */
public interface IFeescale{
    //添加或者更新收费标准信息
	void saveOrUpdate(PaymentMasterEntity paymentMasterEntity);

	//根据code获取收费标准信息
	PaymentMasterEntity get(Integer masterid);

	//根据code删除收费标准信息
	void delete(Integer masterid);

	//查询所有收费标准信息
	List<PaymentMasterEntity> findAll();

	//查询所有收费标准信息(支持分页)
	List<PaymentMasterEntity> findAll(Page page);

	//根据条件查询收费标准信息(支持分页)
	List<PaymentMasterEntity> findAll(PaymentMasterEntityExample cdt, Page page);
}
