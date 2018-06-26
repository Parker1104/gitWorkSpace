package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BackCardAndBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OutParamBoxIDCardLossRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordByCard;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;


public interface InBackCardAndBox {
	//根据卡号返回卡箱对应信息
    @Select("SELECT * FROM cardandboxbound AND c.sync=1 and c.CardID=#{cardid}")
    CardAndBoxBoundEntity selectCardAndBoxBound(@Param("cardid")String cardid);
    
    
	//根据卡号返回卡箱对应信息
    @Select("SELECT a.areaName,t.displayName,b.dispalyName,"
    		+ "(SELECT a.feeValue*a.discountRate/100*b.discountRate/100 AS feeValue "
    		+ "FROM paymentdetail a,paymentmaster b WHERE a.masterID = b.masterID AND b.orderNumber =1 AND "
    		+ "chargeCode =(SELECT configValue  FROM businessmodel bm WHERE bm.businessCode = t.businessCode "
    		+ "AND bm.configName = 'ChargeMode') AND boxtypecode = b.boxTypeCode) AS feeValue FROM "
    	+ "cardandboxbound c,terminal t,box b ,area a WHERE (b.boxID = c.boxID AND c.terminalID = b.terminalID) AND c.terminalID = t.terminalID AND a.areaCode=t.areaCode and c.sync=1 and c.CardID=#{cardid}")
	BackCardAndBox selectByCardid(@Param("cardid")String cardid);
    
  
    //根据卡号获取存物信息
    @Select("SELECT a.areaName,t.displayName,box.dispalyName,s.storeInTime,s.money,s.realMoney from cardandboxbound c LEFT JOIN box on (box.terminalID=c.terminalID and box.boxID=c.boxID) "+ 
             "LEFT JOIN terminal t on t.terminalID = c.terminalID LEFT JOIN area a on a.areaCode = t.areaCode "+
             "LEFT JOIN storeinrecord s on (s.terminalID=c.terminalID and s.boxID=c.boxID) where s.state=0 and c.sync=1 and c.CardID=#{cardid}")
	StoreInRecordByCard cardIDCheckOutRequest(@Param("cardid")String cardid);
    
    ////根据箱门号获取存物信息
    @Select("SELECT a.areaName,t.displayName,box.dispalyName,s.userCardID as cardid,s.storeInTime,s.money as paymentedMoney,s.realMoney FROM storeinrecord s "+
             "LEFT JOIN box ON (box.terminalID = s.terminalID AND box.boxID = s.boxID) LEFT JOIN terminal t ON t.terminalID = s.terminalID "+
             "LEFT JOIN area a ON a.areaCode = t.areaCode WHERE s.state = 0 AND box.dispalyName =#{boxID}")
	OutParamBoxIDCardLossRequest boxIDCardLossRequest(@Param("boxID")String boxID);
    //查询当天收费总金额  结账数量
    @Select("select sum(money) as money,COUNT(*)as usetime from takeoutrecord where makeOpCode=#{accountCode} and cashierNo=#{cashierNo} and to_days(takeTime) = to_days(now()) and type=2;")
	StoreInRecordByCard userCheckOutbyDay(@Param("accountCode")String accountCode, @Param("cashierNo")String cashierNo);
    //查询当天发卡总量
    @Select("select sum(money) as money, COUNT(*) as usetime from storeinrecord where makeOpCode=#{accountCode} and cashierNo=#{cashierNo} and to_days(storeInTime) = to_days(now());")
	StoreInRecordByCard selectSendCardSum(@Param("accountCode")String accountCode, @Param("cashierNo")String cashierNo);
    //结账要返回给存储过程的参数
    @Select("SELECT box.boxtypecode,s.storeInTime,b.configValue,s.realmoney,s.money from cardandboxbound c LEFT JOIN box on (box.terminalID=c.terminalID and box.boxID=c.boxID)"
             +" LEFT JOIN terminal t on t.terminalID = c.terminalID LEFT JOIN area a on a.areaCode = t.areaCode"
             +" LEFT JOIN businessmodel b on t.businessCode=b.businessCode"
             +" LEFT JOIN storeinrecord s on (s.terminalID=c.terminalID and s.boxID=c.boxID) where s.state=0 and b.configName='ChargeMode' and c.CardID=#{cardid}")
	StoreInRecordByCard getAcountInfo(@Param("cardid")String cardid);
    //获取应收费金额
    @Select("CALL sp_CalculatePaymentMoney(#{time},#{date},#{boxtypecode},#{configvalue})")
	StoreInRecordByCard getmoney(@Param("time")String time,@Param("date") String date,@Param("boxtypecode") String boxtypecode, @Param("configvalue")String configvalue);
    
    //直接补卡
    /*@Select("SELECT a.areaName,t.displayName,box.dispalyName,s.userCardID as cardid,s.storeInTime,s.money as paymentedMoney,s.realMoney FROM storeinrecord s"
          +" LEFT JOIN box ON (box.terminalID = s.terminalID AND box.boxID = s.boxID) LEFT JOIN terminal t ON t.terminalID = s.terminalID"
          +" LEFT JOIN area a ON a.areaCode = t.areaCode WHERE s.state = 2 AND box.dispalyName =#{dispalyname} Order by storeInTime desc LIMIT 0,1")*/
    @Select("select a.areaName,c.makedate as storeintime,t.displayName,box.dispalyName,c.CardID as cardid FROM cardandboxbound c,box,terminal t,area a where c.terminalid=t.terminalid and (box.terminalID = c.terminalID AND box.boxID = c.boxID) and a.areacode= t.areacode and c.sync=2 and box.dispalyName=#{dispalyname}")
    OutParamBoxIDCardLossRequest boxIDCardChangeRequest(@Param("dispalyname")String dispalyname);

    //查询当天丢失卡数量
    @Select("select COUNT(*) as usetime ,SUM(realmoney)as money from takeoutrecord t,cardandboxbound c "
    		+ "where (t.terminalid=c.terminalid and t.boxid=c.boxid and t.usercardid=c.cardid) and "
    		+ "t.makeOpCode=#{accountCode} and t.cashierNo=#{cashierNo} and t.type=3 and c.sync=2 and to_days(t.storeintime) = to_days(now())")
	StoreInRecordByCard selectByLosscard(@Param("accountCode")String accountCode, @Param("cashierNo")String cashierNo);

    //查询取消卡金额   数量
    @Select("SELECT SUM(money)as money,COUNT(*) as idtype from takeoutrecord where type=7 and makeOpCode=#{accountCode} and cashierNo=#{cashierNo} and to_days(storeintime) = to_days(now())")
	StoreInRecordEntity selectCancelCard(@Param("accountCode")String accountCode, @Param("cashierNo")String cashierNo);
	
}
