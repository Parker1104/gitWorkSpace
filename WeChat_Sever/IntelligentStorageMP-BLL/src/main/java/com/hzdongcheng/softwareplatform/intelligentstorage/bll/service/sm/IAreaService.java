package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntityExample;
/**
 * 
 * @author wenheju
 *  区域管理  接口
 */
public interface IAreaService {
	//添加或者更新区域管理信息
	void saveOrUpdate(AreaEntity areaEntity);

	//根据code获取区域管理信息
	AreaEntity get(String areaCode);

	//根据code删除区域管理信息
	void delete(String areaCode);

	//查询所有区域管理信息
	List<AreaEntity> findAll();

	List<AreaEntity> findAllChild(String areaCode);

	//查询所有区域管理信息(支持分页)
	List<AreaEntity> findAll(AreaEntityExample example);

	//根据条件查询区域管理信息(支持分页)
	List<AreaEntity> findAll(AreaEntityExample cdt, Page page);

	String findMaxChild(String areaCode);
}
