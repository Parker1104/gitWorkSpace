package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.WristStrapHairpin;

public interface WristStrapHairpinDao {
    
	/*@Select("SELECT	s.makeOpCode,a.accountName,s.cashierNo,COUNT(*) AS sendcardcount,COUNT(t.taketime) as collectcardcount "+
      "FROM storeinrecord s LEFT JOIN account a ON s.makeOpCode = a.accountCode LEFT JOIN takeoutrecord t "
      + "ON (t.terminalid = s.terminalID AND t.boxid = s.boxID and t.storeInTime=s.storeInTime and t.usercardid=s.userCardID) "+
        "GROUP BY s.makeOpCode,s.cashierNo")*/
	List<WristStrapHairpin> getList(Page<WristStrapHairpin> page);
    
	/*@Select("SELECT COUNT(*) FROM (select a.accountName,s.cashierNo,COUNT(*) "
			+ "as sendcardcount from storeinrecord s LEFT JOIN account a on s.makeOpCode=a.accountCode "
			+ "GROUP BY s.makeOpCode,s.cashierNo) as a")*/
	int getCount(Page<WristStrapHairpin> page);
    
	//腕带丢失统计
	List<WristStrapHairpin> getMissList(Page<WristStrapHairpin> page);
    /*@Select("SELECT COUNT(*) FROM (select COUNT(*) as lossCardCount,a.accountName,(SELECT COUNT(*) from storeinrecord) as sendCardCount from "
    		+ "storeinrecord s LEFT JOIN account a ON a.accountCode=s.makeOpCode where state=2 GROUP BY s.makeOpCode,to_days(s.storeInTime) = to_days(now())) as a")*/
	int getMissCount(Page<WristStrapHairpin> page);
    
	@Select("select COUNT(*) from storeinrecord")
	long sendCount();//发卡总量
	
    @Select("select COUNT(*) from takeoutrecord")
	long collectCount();//退卡总量
    
    @Select("select COUNT(*)-(select COUNT(*) from takeoutrecord) from storeinrecord")
	long notCollect();//未退卡总量
    
    @Select("select COUNT(*) from storeinrecord where TO_DAYS(storeInTime)=TO_DAYS(NOW())")
	long todaySendCount();//今日发卡量
    
    @Select("select COUNT(*) from takeoutrecord where TO_DAYS(taketime)=TO_DAYS(NOW())")
	long todayCollectCount();//今日退卡量
    
    @Select("select COUNT(*)-(select COUNT(*) from takeoutrecord where TO_DAYS(taketime)=TO_DAYS(NOW())) from storeinrecord where TO_DAYS(storeInTime)=TO_DAYS(NOW())")
	long todayNotCollect();//今日未退卡量
    
    @Select("select count(*) from storeinrecord s LEFT JOIN terminal t on s.terminalID = t.terminalID where t.displayName<2102 and t.displayName>2000")
	long countMen();//男用户量
    
    @Select("select count(*) from storeinrecord s LEFT JOIN terminal t on s.terminalID = t.terminalID where t.displayName<1172")
	long countWonmen();//女用户量
	
}
