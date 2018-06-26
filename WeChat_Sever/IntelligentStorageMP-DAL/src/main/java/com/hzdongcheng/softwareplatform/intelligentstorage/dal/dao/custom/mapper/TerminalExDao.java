package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxAppExEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.TerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalEntityDao;

public interface TerminalExDao extends TerminalEntityDao{
	
	List<TerminalEx> selectByExampleEx(TerminalEntityExample example);
    
	List<TerminalEx> selectAllBycode(Integer code);
	
	/**
	 * 页面
	 * @param areacode
	 * @return
	 */
    //根据区域ID查询终端  箱子信息
    @Select("select * FROM box WHERE terminalID in (select terminalID FROM terminal WHERE areaCode like #{areacode} ) ORDER BY dispalyName ASC")
    List<BoxEx> selectBoxExampleEx(String areacode);
    
    //根据区域code查询柜子名称信息（  请选择你要查询的柜号： ）
    @Select("SELECT * from terminal WHERE areaCode LIKE #{areacode} ORDER BY displayName ASC")
	List<TerminalEx> findAllTermainal(String areacode);
    
    //查询所有已用(脏箱)箱信息 
    @Select("select distinct terminalID,boxid FROM TakeOutRecord a WHERE Left(a.storeInTime,10) = CURRENT_DATE "
    		+ "AND a.boxid not in (select boxid from storeinrecord WHERE state =0 and terminalID= a.terminalID and boxID = a.boxID) "
    		+ "AND a.terminalID = #{terminalID}")
    List<TakeOutRecordEntity> selectAllBoxAreacode(String terminalID);
    
    //过期箱门查询
    @Select("SELECT * from punishrecord where punishstate = '1' AND terminalID = #{terminalID} ")
	List<PunishRecordEntity> selectAllOverdueBoxAreacode(String terminalID); 
    
    //空闲箱门查询
    @Select("SELECT * from box where terminalID = #{terminalID} AND boxid not in (select boxid from storeinrecord where state = 0 and terminalID = #{terminalID}) ORDER BY boxID")
    List<BoxEntity> selectAllFreeBoxAreacodes(String terminalID);
    
    //超时箱
    @Select("SELECT * from punishrecord where punishstate = '1' AND terminalID = #{terminalID}")
    List<BoxEntity> selectTimeoutBox(String terminalID);
    
    //取到当前区域终端
    @Select("SELECT * from terminal where areaCode like #{areacode} ORDER BY terminalID")
    List<TerminalEntity> selectAllFreeBoxAreacode(String areacode);
    
    //查询存物记录  看箱子状态
    @Select("select * from storeinrecord WHERE terminalID =  #{terminalid} and state = '0'")
	List<StoreInRecordEntity> findBoxState(String terminalid);
	
	//根据区域ID查询终端  柜子信息
    @Select("select * FROM terminal WHERE areaCode like #{areacode} ORDER BY displayName ")
    List<TerminalEx> selectTerminalExampleEx(String areacode);
   
    @Select("SELECT count(*) from storeinrecord where state = 0 and terminalID in(select terminalID from terminal where areaCode like #{areacode})")
    long  findAllBeBoxCount(String string);
    
    //根据名称查询终端信息
    @Select("select * from terminal where displayname=#{displayname}")
	TerminalEntity selectByDisplayname(String displayname);
    
    //根据区域code查询柜子信息
    @Select("select * from terminal where AreaCode like #{AreaCode}")
	TerminalEntity selectTermianlByAreacode(String AreaCode);
 
    //在箱
    @Select("SELECT * from storeinrecord  WHERE state = '0' and terminalID = #{terminalID}")
    List<StoreInRecordEntity> inBox(String terminalID);
    
    //根据柜子名称查询箱信息
    @Select("select * FROM  box WHERE terminalid  =  #{tankNo} ")
	List<BoxEntity>  findAllBox(String tankNo);
    
    //查询所有脏箱总数
    @Select("select count(*) FROM TakeOutRecord a WHERE Left(a.storeInTime,10) = CURRENT_DATE "
    		+ "AND a.boxid not in (select boxid from storeinrecord WHERE state =0 and terminalID= a.terminalID and boxID = a.boxID)"
    		+ " AND a.terminalID in (SELECT terminalID from terminal where areaCode like  #{areaCode}) ORDER BY terminalID,boxID")
    long findAllTermainalsize(String string);

    //根据终端号查询柜子信息
    @Select("select * FROM terminal WHERE terminalId = #{terminalid} ")
	List<TerminalEntity> queryTankno(String terminalid);
    
    @Select("SELECT * from punishrecord where punishstate = '1' ")
   	List<PunishRecordEntity> noneTakeBox();
    
    //根据区域查询空箱
    @Select("SELECT * from box b where b.boxid not in (select boxid from storeinrecord s "
    		+ "where s.terminalID in (SELECT terminalID FROM terminal t WHERE t.areacode like #{areaCode})AND s.state = 0 and s.terminalID = b.terminalID ) ")
    List<BoxEntity> selectEmptyBoxByAreaCode(String areaCode);
    
    //根据终端名字查询空箱
    @Select("SELECT * from box b where b.boxid not in (select boxid from storeinrecord s "
    		+ "where s.terminalID in (SELECT terminalID FROM terminal t WHERE t.displayName = #{displayName}) AND s.state = 0 and s.terminalID = b.terminalID ) ORDER BY b.boxID ")
    List<BoxEntity> selectEmptyBoxByDisplayName(String displayName);
    
    //根据卡号查询存物信息
    @Select("SELECT * from storeinrecord  WHERE userCardID = #{userCardID} GROUP BY storeIntime")
    List<StoreInRecordEntity> selectInBoxByOpenBoxCode(String userCardID);
    
    /**
     * 管理卡
     * @param terminalID
     * @return
     */
    
    //清洁卡开箱（卡）
    @Select("select distinct terminalID,boxid FROM TakeOutRecord a WHERE Left(a.storeInTime,10) = CURRENT_DATE "
    		+ "AND a.boxid not in (select boxid from storeinrecord WHERE state =0 and terminalID= a.terminalID and boxID = a.boxID) "
    		+ "AND a.terminalID = #{terminalID}" )
    List<TakeOutRecordEntity> selectAllBoxterminalID(String terminalID);
    
    //开箱柜子所有箱门（卡）
    @Select("select * from box WHERE terminalID =  #{terminalid} ")
	List<BoxEntity> selectOpenBoxTerminalID(String terminalID);
    
    //空闲开箱（卡）
    @Select("SELECT * from box where terminalID = #{terminalID} AND boxid not in (select boxid from storeinrecord where state = 0 and terminalID = #{terminalID}) ORDER BY boxID")
	List<BoxEntity> selectFreeOpenBoxTerminalID(String terminalID);
   
    //超时开箱
    @Select("SELECT * from punishrecord where punishstate = '1' AND terminalID = #{terminalID}")
    List<PunishRecordEntity> selectTimeoutOpenBox(String terminalID);
    
    //根据编号查询箱信息
    @Select("select * FROM  box WHERE terminalid  =  #{terminalID} ")
	List<BoxEntity>  selectTerminalIDBox(String terminalID);
    
    //用户区域控制
    @Select("select * FROM area WHERE areacode like #{areacode}")
	List<AreaEntity> findAccountAreacold(String areacode);
    
    
    //新 空闲开箱（卡）并根据箱子大小
    @Select("SELECT * from box where terminalID = #{terminalID} "
    	 + " AND  boxid not in (select boxid from storeinrecord where state = 0 and terminalID = #{terminalID}) "
    	 + " AND  boxTypeCode =#{boxTypeCode}   "
    	 + " ORDER BY boxID")
	List<BoxEntity> selectFreeOpenBoxTerminalIDAndBoxTypeCode(@Param("terminalID") String terminalID,@Param("boxTypeCode") int boxTypeCode);
    
    
    //根据柜号箱号状态 查看存储记录
    @Select(" select *  from storeinrecord where state = #{state} and  boxID=#{boxID} and terminalID = #{terminalID}  "
    	  + " GROUP BY storeIntime")
    List<StoreInRecordEntity> selectInByBoxId(
    		@Param("state") int state,@Param("boxID") int boxID,@Param("terminalID") String terminalID );
    
    
    
    //app查看空箱子  新 空闲开箱 
    @Select("SELECT b.*,'tname' as tname  from box b where terminalID = #{terminalID} "
    	 + " ORDER BY boxID")
	List<BoxAppExEntity> selectFreeOpenBoxTerminalIDAndBoxTypeCodeApp(@Param("terminalID") String terminalID);
    
    
    
    
}





