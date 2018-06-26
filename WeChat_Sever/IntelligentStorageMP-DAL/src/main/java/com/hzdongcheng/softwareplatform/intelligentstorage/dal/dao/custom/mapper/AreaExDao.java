package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AreaEntityDao;

public interface AreaExDao extends AreaEntityDao{

    /*******************以下为扩展******************/
    /**
     * 取所有子节点及下级节点
     * @param areaCode
     * @return
     */
    List<AreaEntity> findAllChild(String areaCode);
    /**
     * 取最大子节点编码
     * @param areaCode
     * @return
     */
    String findMaxChild(@Param("areaCode") String areaCode);

}
