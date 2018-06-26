package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

 
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGStoreTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerPayBack;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGStoreTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

public interface IAPPPayBackService {
    //public    OutParamAPCustomerJBGStore doBusiness( InParamAPCustomerJBGStore in) throws EduExceptions ; 
    public    OutParamAPCustomerJBGStoreTake doBusiness( InParamAPCustomerJBGStoreTake in) throws EduExceptions ; 
    public String doBusiness(InParamAPCustomerPayBack ins)throws EduExceptions ; 
}
