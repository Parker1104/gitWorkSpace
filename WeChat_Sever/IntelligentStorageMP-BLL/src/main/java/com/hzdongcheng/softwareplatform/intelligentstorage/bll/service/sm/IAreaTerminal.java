package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AreaTerminalEx;

public interface IAreaTerminal {

	List<AreaTerminalEx> select();

	List<AreaTerminalEx> selectZtree(String areacode);

}
