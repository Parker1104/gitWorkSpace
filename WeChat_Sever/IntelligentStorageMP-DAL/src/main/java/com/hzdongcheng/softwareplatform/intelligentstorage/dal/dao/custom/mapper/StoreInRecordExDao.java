package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.StoreInRecordEntityDao;

public interface StoreInRecordExDao extends StoreInRecordEntityDao {
	
	List<StoreInRecordEx> selectByExampleEx(StoreInRecordEntityExample example);

	StoreInRecordEx selectByRequest(@Param("terminalid")String terminalid, @Param("boxid")int boxid,@Param("usercardid") String usercardid);

	List<StoreInRecordEx> selectByNullBox(String terminalid);

	Object yesterdayCount();//昨天箱门使用次数

	Object todayCount();//今天箱门使用次数
	
	@Select("select * from storeinrecord a "
			+ "where state = 0 "
			+ "and not EXISTS (select usercardid from punishrecord "
			+ "where punishState != 0 and usercardid = a.usercardid)")
	List<StoreInRecordEntity> selectNotViolation();
	
	@Select("select * from storeinrecord " + 
			" where userCardID = #{0} and state = 0" +
			" and terminalID in (SELECT terminalID from terminal where businessCode = #{1})" +
			" order by storeInTime desc")
	List<StoreInRecordEx> queryInBoxRecord(String openCode, Integer businessCode);
	
    //存取记录
	List<StoreInRecordEx> getList(Page<StoreInRecordEx> page);
	int getCount(Page<StoreInRecordEx> page);
	
    //预留查询
	List<StoreInRecordEx> selectReserved(Page<StoreInRecordEx> page);

	
 
	@Select("select * from storeinrecord "
			+ "  where terminalID=#{0} and boxID=#{1}  and storeInTime=#{2} and state=0 ")
	List<StoreInRecordEx> queryBoxRecord(String terminalID, Integer boxID,String storeInTime);
	
	
	@Select("select * from storeinrecord "
			+ "  where terminalID=#{0} and boxID=#{1} and  userCardID=#{2}  and storeInTime=#{3}  ")
	List<StoreInRecordEntity> queryBoxRecordList(String terminalID,Integer  boxID,String  openBoxCode,String storeInTime);
	
    //@Update("update box set open=0 where terminalid=#{terId} and boxid=#{boxId}")
    @Insert("insert  into storeinrecord (terminalID,boxID,userCardID, storeInTime,state,userType ,realMoney,money ) "
    		+ "  value(#{0},#{1},#{2},#{3},   #{4},#{5},#{6},#{7} )   ")
	void StoreInRecordInsert(String terminalID,Integer  boxID,String  openBoxCode,String storeInTime,
			            Byte states,String usertype,Float realmoney,Float money);
	
	
	
}
