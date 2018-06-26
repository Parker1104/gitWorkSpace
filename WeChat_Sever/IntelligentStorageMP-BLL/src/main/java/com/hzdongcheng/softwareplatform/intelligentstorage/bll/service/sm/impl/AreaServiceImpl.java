package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.dto.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AreaExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntityExample;
/**
 * 
 * @author wenheju
 *  区域管理  实现类
 */
@Service
public class AreaServiceImpl implements IAreaService{
	
	@Autowired
	AreaExDao areaExDao;
		
	@Override
	public void saveOrUpdate(AreaEntity areaEntity) {
		if(null == get(areaEntity.getAreacode())){
			areaExDao.insert(areaEntity);
		}else {
			areaExDao.updateByPrimaryKey(areaEntity);
		}
		
	}

	@Override
	public AreaEntity get(String areaCode) {
		return areaExDao.selectByPrimaryKey(areaCode);
	}

	@Override
	public void delete(String areaCode) {
		areaExDao.deleteByPrimaryKey(areaCode);
		
	}

	@Override
	public List<AreaEntity> findAll() {
		AreaEntityExample example = new AreaEntityExample();
		/*example.createCriteria().andAreacodeEqualTo("1");*/
		return areaExDao.selectByExample(example);
	}

	@Override
	public List<AreaEntity> findAll(AreaEntityExample example) {;
		return areaExDao.selectByExample(example);
	}

	@Override
	public List<AreaEntity> findAll(AreaEntityExample cdt, Page page) {
		cdt.createCriteria().andAreacodeEqualTo("1");
		PageHelper.startPage(page.getStartPage(), page.getPageSize());
		return areaExDao.selectByExample(cdt);
	}

	@Override
	public List<AreaEntity> findAllChild(String areaCode) {
		AreaEntityExample example = new AreaEntityExample();
		example.createCriteria().andAreacodeLike(areaCode + "%");
		return areaExDao.selectByExample(example);
	}
	
	@Override
	public String findMaxChild(String areaCode){
		return areaExDao.findMaxChild(areaCode);
	}
}
