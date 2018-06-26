package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import java.util.List;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerBoxStatusQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGInBoxQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGInBoxQryTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamCustomerOpenFailQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerBoxStatusQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGInBoxQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGInBoxQryTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamCustomerOpenFailQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

public interface IAPPBoxService {
	 public   List<OutParamAPCustomerBoxStatusQry> doBusiness(InParamAPCustomerBoxStatusQry ins) throws  EduExceptions;
	 public   List< OutParamAPCustomerJBGInBoxQry> doBusiness( InParamAPCustomerJBGInBoxQry ins) throws  EduExceptions ;
	 public List<OutParamAPCustomerJBGInBoxQryTake> doBusiness(InParamAPCustomerJBGInBoxQryTake ins) throws EduExceptions;
	 public OutParamCustomerOpenFailQry doBusiness(InParamCustomerOpenFailQry in) throws EduExceptions;
}
