package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

 
 
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerConsumeAmt;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerInTimeOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGOpenBoxTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGPayOpenBoxTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerConsumeAmt;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

public interface IAPPOpenService {
    /**
     * 消费金额计算
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   OutParamAPCustomerConsumeAmt doBusiness(InParamAPCustomerConsumeAmt in) throws EduExceptions ;
    /**
     * 用户开箱 存
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   String doBusiness(InParamAPCustomerJBGOpenBox in) throws EduExceptions ;
    
    /**
     * 用户开箱  取
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	public String doBusiness(InParamAPCustomerJBGOpenBoxTake in) throws EduExceptions ;
	
	
	/**
     * 中途取开箱不结算   
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
	public   String doBusiness(InParamAPCustomerInTimeOpenBox in) throws EduExceptions ;
	
	
	/**
	 * 微信二次支付流水号
	 * @param in
	 * @return
	 */
	public   String doBusiness(InParamAPCustomerJBGPayOpenBoxTake in)throws EduExceptions ;
}
