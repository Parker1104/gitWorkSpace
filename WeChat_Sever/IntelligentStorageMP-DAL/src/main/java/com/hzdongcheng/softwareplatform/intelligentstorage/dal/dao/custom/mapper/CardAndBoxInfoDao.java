package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardAndBoxInfo;

public interface CardAndBoxInfoDao {

	List<CardAndBoxInfo> getList(Page<CardAndBoxInfo> page);

	int getCount(Page<CardAndBoxInfo> page);
	
}
