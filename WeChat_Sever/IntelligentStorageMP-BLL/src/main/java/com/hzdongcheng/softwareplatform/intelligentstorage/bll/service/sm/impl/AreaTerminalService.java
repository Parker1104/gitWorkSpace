package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaTerminal;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AreaTerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AreaTerminalDao;

@Service
public class AreaTerminalService implements IAreaTerminal {
    @Autowired
    AreaTerminalDao dao;
	@Override
	public List<AreaTerminalEx> select() {
		return dao.select();
	}
	@Override
	public List<AreaTerminalEx> selectZtree(String areacode) {
		return dao.selectZtree(areacode);
	}

}
