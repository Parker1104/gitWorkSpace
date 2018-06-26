package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerPrepayAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerPrepayAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

public interface IAPPWinPayService {
	 public    OutParamAPCustomerPrepayAmtQry doBusiness( InParamAPCustomerPrepayAmtQry p1) throws EduExceptions ;
}
