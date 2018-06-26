package com.hzdongcheng.softwareplatform.controller.appmanager.proxys;
 
import java.util.List;
import com.dcdzsoft.EduException;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerBoxHireAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerBoxStatusQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerConsumeAmt;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerInTimeOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGInBoxQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGInBoxQryTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGOpenBoxTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGPayOpenBoxTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGSchedule;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGStore;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerJBGStoreTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerPayBack;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerPrepayAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerUnBindOpenID;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamAPCustomerUpdate;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InParamCustomerOpenFailQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQrcode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InTerminalQrcodeQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerBoxHireAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerBoxStatusQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerConsumeAmt;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGInBoxQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGInBoxQryTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGSchedule;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGStore;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerJBGStoreTake;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerPrepayAmtQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamAPCustomerUpdate;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutParamCustomerOpenFailQry;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQrcode;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.OutTerminalQrcodeQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPOpenService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPPayBackService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPPayService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.IAPPWinPayService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;
import com.hzdongcheng.softwareplatform.util.SpringContextUtil;

public class MobileProxy {

	 /**
     * 用户信息更新
     * @param ins
     * @return
     * @throws   
     */
    public    OutParamAPCustomerUpdate doBusiness( InParamAPCustomerUpdate ins) throws  EduExceptions {
    	IAPPService iAPPService = (IAPPService) SpringContextUtil.getBean("iAPPService");  
    	OutParamAPCustomerUpdate outParamAPCustomerUpdates=iAPPService.doBusiness(ins);
        return outParamAPCustomerUpdates;
    }
    

    /**
     * 查询设备ID
     * @param ins
     * @return
     * @throws   
     */
    public    OutTerminalQuery doBusiness( InTerminalQuery ins) throws  EduExceptions {
    	IAPPService iAPPService = (IAPPService) SpringContextUtil.getBean("iAPPService");  
    	OutTerminalQuery outTerminalQuery=iAPPService.doBusiness(ins);
        return outTerminalQuery;
    }
    
    
    
    
    /**
     * 设备二维码绑定
     * @param ins
     * @return
     * @throws   
     */
    public    OutTerminalQrcode doBusiness( InTerminalQrcode ins) throws  EduExceptions {
    	IAPPService iAPPService = (IAPPService) SpringContextUtil.getBean("iAPPService");  
    	OutTerminalQrcode OutTerminalQrcode=iAPPService.doBusiness(ins);
        return OutTerminalQrcode;
    }
    
    
    /**
     * 根据Url查询设备ID
     * @param ins
     * @return
     * @throws   
     */
    public    OutTerminalQrcodeQuery doBusiness( InTerminalQrcodeQuery ins) throws  EduExceptions {
    	IAPPService iAPPService = (IAPPService) SpringContextUtil.getBean("iAPPService");  
    	OutTerminalQrcodeQuery outTerminalQuery=iAPPService.doBusiness(ins);
        return outTerminalQuery;
    }
    
    
    
    
    /**
     * 解绑定合作方用户编号
     * @param ins
     * @return
     * @throws  
     */
    public   int doBusiness( InParamAPCustomerUnBindOpenID ins) throws EduExceptions {
    	IAPPService iAPPService = (IAPPService) SpringContextUtil.getBean("iAPPService");  
    	int  results=iAPPService.doBusiness(ins);
        return results;
    }
    /**
     * 用户信息查询
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public    OutParamAPCustomerQuery doBusiness( InParamAPCustomerQuery ins) throws EduExceptions {
    	IAPPService iAPPService = (IAPPService) SpringContextUtil.getBean("iAPPService");  
    	OutParamAPCustomerQuery  results=iAPPService.doBusiness(ins);
        return results;
    }
    
    
    
    /**
     * 箱状态查询  存
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public    List<OutParamAPCustomerBoxStatusQry> doBusiness(InParamAPCustomerBoxStatusQry ins) throws  EduExceptions {
    	 IAPPBoxService iAPPBoxService = (IAPPBoxService) SpringContextUtil.getBean("iAPPBoxService");  
    	 List<OutParamAPCustomerBoxStatusQry>  results=iAPPBoxService.doBusiness(ins);
         return results;
    }
    
    /**
     * 箱状态查询  取
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public    List<OutParamAPCustomerJBGInBoxQryTake> doBusiness(InParamAPCustomerJBGInBoxQryTake ins) throws  EduExceptions {
    	 IAPPBoxService iAPPBoxService = (IAPPBoxService) SpringContextUtil.getBean("iAPPBoxService");  
    	 List<OutParamAPCustomerJBGInBoxQryTake>  results=iAPPBoxService.doBusiness(ins);
         return results;
    }
    
    
    
    
    /**
     * 开箱失败信息查询
     * @param ins
     * @return
     * @throws   
     */
    public    OutParamCustomerOpenFailQry doBusiness( InParamCustomerOpenFailQry ins) throws  EduExceptions {
    	IAPPBoxService iAPPBoxService = (IAPPBoxService) SpringContextUtil.getBean("iAPPBoxService");  
    	OutParamCustomerOpenFailQry outOpenFailQry=iAPPBoxService.doBusiness(ins);
        return outOpenFailQry;
    }
    
    /**
     * 用户租箱记录查询 
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   List< OutParamAPCustomerJBGInBoxQry> doBusiness( InParamAPCustomerJBGInBoxQry ins) throws  EduExceptions {
    	IAPPBoxService iAPPBoxService = (IAPPBoxService) SpringContextUtil.getBean("iAPPBoxService");  
    	List<OutParamAPCustomerJBGInBoxQry>  results=iAPPBoxService.doBusiness(ins);
        return results;
    }
    
    
    /**
     * 用户预定箱格
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public OutParamAPCustomerJBGSchedule doBusiness(InParamAPCustomerJBGSchedule ins)  throws  EduExceptions {
    	IAPPPayService iAPPPayService = (IAPPPayService) SpringContextUtil.getBean("iAPPPayService");  
    	OutParamAPCustomerJBGSchedule  results=iAPPPayService.doBusiness(ins);
        return results;
    }
    /**
     * 格口收费标准查询
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   List<OutParamAPCustomerBoxHireAmtQry> doBusiness(InParamAPCustomerBoxHireAmtQry ins) throws EduExceptions{
    	IAPPPayService iAPPPayService = (IAPPPayService) SpringContextUtil.getBean("iAPPPayService");  
    	List<OutParamAPCustomerBoxHireAmtQry>  results=iAPPPayService.doBusiness(ins);
        return results;
    }
    
 
    /**
     * 预付金额查询
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public    OutParamAPCustomerPrepayAmtQry doBusiness( InParamAPCustomerPrepayAmtQry ins) throws EduExceptions {
    	IAPPWinPayService iAPPWinPayService = (IAPPWinPayService) SpringContextUtil.getBean("iAPPWinPayService");  
    	OutParamAPCustomerPrepayAmtQry  results=iAPPWinPayService.doBusiness(ins);
        return results;
    }
    
    
    /**
     * 消费金额计算
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   OutParamAPCustomerConsumeAmt doBusiness(InParamAPCustomerConsumeAmt in) throws EduExceptions{
    	IAPPOpenService iAPPOpenService = (IAPPOpenService) SpringContextUtil.getBean("iAPPOpenService");  
    	OutParamAPCustomerConsumeAmt  results=iAPPOpenService.doBusiness(in);
        return results;
    } 
    
    /**
     * 中途取开箱不结算   
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   String doBusiness(InParamAPCustomerInTimeOpenBox in) throws EduExceptions{
    	IAPPOpenService iAPPOpenService = (IAPPOpenService) SpringContextUtil.getBean("iAPPOpenService");  
    	String  results=iAPPOpenService.doBusiness(in);
        return results;
    }
    
    /**
     * 用户开箱   存
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   String doBusiness(InParamAPCustomerJBGOpenBox in) throws EduExceptions{
    	IAPPOpenService iAPPOpenService = (IAPPOpenService) SpringContextUtil.getBean("iAPPOpenService");  
    	String  results=iAPPOpenService.doBusiness(in);
        return results;
    }
    
    /**
     * 用户开箱  取
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   String doBusiness(InParamAPCustomerJBGOpenBoxTake in) throws EduExceptions{
    	IAPPOpenService iAPPOpenService = (IAPPOpenService) SpringContextUtil.getBean("iAPPOpenService");  
    	String  results=iAPPOpenService.doBusiness(in);
        return results;
    } 
    
    /**
	 * 微信二次支付流水号
	 * @param in
	 * @return
	 */
    
    public   String doBusiness(InParamAPCustomerJBGPayOpenBoxTake in) throws EduExceptions{
    	IAPPOpenService iAPPOpenService = (IAPPOpenService) SpringContextUtil.getBean("iAPPOpenService");  
    	String  results=iAPPOpenService.doBusiness(in);
        return results;
    }
    
    
    
    /**
     * 微信支付回调地址  
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   String doBusiness( InParamAPCustomerPayBack ins) throws  EduExceptions {
    	IAPPPayBackService iAPPPayBackService = (IAPPPayBackService) SpringContextUtil.getBean("iAPPPayBackService");  
    	String  results=iAPPPayBackService.doBusiness(ins);
        return results;
    }
    
    /**
     * 支付回调地址  存
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    /*public   OutParamAPCustomerJBGStore doBusiness( InParamAPCustomerJBGStore ins) throws  EduExceptions {
    	IAPPPayBackService iAPPPayBackService = (IAPPPayBackService) SpringContextUtil.getBean("iAPPPayBackService");  
    	OutParamAPCustomerJBGStore  results=iAPPPayBackService.doBusiness(ins);
        return results;
    }*/
    
    
    /**
     * 支付回调地址  取
     * @param p1
     * @return
     * @throws com.dcdzsoft.EduException
     */
    public   OutParamAPCustomerJBGStoreTake doBusiness( InParamAPCustomerJBGStoreTake ins) throws  EduExceptions {
    	IAPPPayBackService iAPPPayBackService = (IAPPPayBackService) SpringContextUtil.getBean("iAPPPayBackService");  
    	OutParamAPCustomerJBGStoreTake  results=iAPPPayBackService.doBusiness(ins);
        return results;
    }
    
}


























