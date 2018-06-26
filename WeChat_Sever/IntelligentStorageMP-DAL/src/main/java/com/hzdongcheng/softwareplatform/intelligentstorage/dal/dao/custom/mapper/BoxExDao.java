package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxEntityDao;

public interface BoxExDao extends BoxEntityDao {

	List<BoxEx> selectByExampleEx(BoxEntityExample example);

	List<BoxEntity> selectByTerminalid(String terminalid);

	BoxEx selectByPrimaryKeys(@Param("terminalid") String terminalid, @Param("boxid") Integer boxid);

	BoxEx selectByAreacode(String areacode);

	Object findArticle();

	List<BoxEntity> queryViolationBoxList(String terminalID);

	@Select("SELECT terminalID,boxid from box where boxid not in (SELECT boxid from storeinrecord where state =0)")
	List<BoxEntity> selectBox();
	
	@Select("select distinct address from `user`  order by address asc")
	List<UserEntity> selectUserBydistinct();
	
	void updateAll(@Param("id")String id, @Param("boxtypecode")Integer boxtypecode,@Param("oneboxmorecard")byte oneboxmorecard,@Param("fixedbox")byte fixedbox);
}
