package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AreaTerminalEx;

public interface AreaTerminalDao {
    @Select("select box.boxID,box.terminalID from box"+" "+
      "LEFT JOIN terminal t on box.terminalid = t.terminalid"
      )
	List<AreaTerminalEx> select();
    @Select("select box.boxID,box.terminalID from box LEFT JOIN terminal t on t.terminalID = box.terminalID"+" "+
        "LEFT JOIN area on t.areaCode = area.areaCode"+" "+
         "where area.areaCode=#{areacode}")
	List<AreaTerminalEx> selectZtree(String areacode);

}
