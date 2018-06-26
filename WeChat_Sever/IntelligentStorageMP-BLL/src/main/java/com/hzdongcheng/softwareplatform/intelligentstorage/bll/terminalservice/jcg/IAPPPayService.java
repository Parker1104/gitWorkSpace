package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import java.util.List;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerBoxHireAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGSchedule;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerBoxHireAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGSchedule;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

public interface IAPPPayService {
	  public    OutParamAPCustomerJBGSchedule doBusiness( InParamAPCustomerJBGSchedule p1) throws EduExceptions ;
	  public   List<OutParamAPCustomerBoxHireAmtQry> doBusiness(InParamAPCustomerBoxHireAmtQry in) throws EduExceptions;
}
