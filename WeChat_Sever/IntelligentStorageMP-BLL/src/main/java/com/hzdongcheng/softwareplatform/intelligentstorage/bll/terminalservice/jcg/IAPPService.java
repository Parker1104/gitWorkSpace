package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

 
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerUnBindOpenID;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerUpdate;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQrcode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQrcodeQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerUpdate;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQrcode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQrcodeQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

public interface IAPPService {
	 public OutParamAPCustomerUpdate doBusiness(InParamAPCustomerUpdate in)  throws EduExceptions ;
	 public int doBusiness(InParamAPCustomerUnBindOpenID in)  throws EduExceptions ;
	 public OutParamAPCustomerQuery doBusiness(InParamAPCustomerQuery in) throws EduExceptions;
	 public OutTerminalQuery doBusiness(InTerminalQuery in) throws EduExceptions;
	 public OutTerminalQrcode doBusiness(InTerminalQrcode in) throws EduExceptions;
	 public OutTerminalQrcodeQuery doBusiness(InTerminalQrcodeQuery in) throws EduExceptions;
}
