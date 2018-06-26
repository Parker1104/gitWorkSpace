package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentesEx;
 

public interface PaymentesDao {

	//根据箱号类型查找money
	@Select("     SELECT a.feeValue*a.discountRate/100*b.discountRate/100 AS money        "
			+ "   FROM paymentdetail a,paymentmaster b WHERE a.masterID = b.masterID     "
			+ "   AND  b.chargeCode =#{chargeCode}   AND a.boxTypeCode = #{boxTypeCode}   "
			+ "   AND  b.startTime=0    ")
			//+ "   AND  b.startTime< #{currentTime}   AND #{currentTime} <b.endTime     ")
    PaymentesEx getMoneyByBoxType(@Param("currentTime") int currentTime, @Param("chargeCode") int chargeCode, @Param("boxTypeCode") int boxTypeCode);
	 
	//根据箱号类型查找money
	@Select("     SELECT a.feeValue*a.discountRate/100*b.discountRate/100 AS money        "
			+ "   FROM paymentdetail a,paymentmaster b WHERE a.masterID = b.masterID     "
			+ "   AND  b.chargeCode =#{chargeCode}   AND a.boxTypeCode = #{boxTypeCode}   "
			+ "   AND  b.startTime=0    ")
    PaymentesEx getMoneyByBoxTypePrepay( @Param("chargeCode") int chargeCode, @Param("boxTypeCode") int boxTypeCode);
	
	
	//根据箱号类型查找money  查找描述
	@Select("     select pr.masterID, pd.detailID, pd.boxTypeCode,  pr.orderNumber,  pr.usedState,  pr.timeValue,   "
			+ "      pr.startTime,   pr.endTime,     pd.feeValue*pd.discountRate/100*pr.discountRate/100 AS money         "
			+ "   from paymentmaster pr LEFT JOIN  paymentdetail pd on pd.masterID=pr.masterID      "
			+ "   where pr.chargeCode=#{chargeCode}  and pd.boxTypeCode=#{boxTypeCode}    "
			+ "   order by pr.orderNumber asc    "
			)
    List<PaymentesEx> getMoneyByBoxTypePrepayDetails( @Param("chargeCode") int chargeCode, @Param("boxTypeCode") int boxTypeCode);
	
	
	
    //获取应收费金额
    @Select("CALL sp_CalculatePaymentMoney(#{time},#{date},#{boxtypecode},#{configvalue})")
    PaymentesEx getmoney(@Param("time")String time,@Param("date") String date,@Param("boxtypecode") String boxtypecode, @Param("configvalue")String configvalue);

}










