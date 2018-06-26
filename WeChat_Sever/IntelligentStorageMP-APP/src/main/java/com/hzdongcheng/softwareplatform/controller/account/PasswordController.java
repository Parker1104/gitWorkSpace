package com.hzdongcheng.softwareplatform.controller.account;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.MsgUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;

/**
 * 
 * @author wenheju
 *    修改密码
 */
@Controller
@RequestMapping("/passwordController")
public class PasswordController extends BaseController{
	@Autowired
	IAccountManagement iAccountManagement;
	@Autowired
	IOperationLogInpquire iOperation;
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("/pass")
	public ModelAndView view(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/account/password");
		return mv;
	}
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/index");
		return mv;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 修改密码
	 */
	@RequestMapping(value = "/saveOrUpdateAccount.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void saveOrUpdateAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {		    
		    
		    String confirmpassword = request.getParameter("confirmpassword");		    
		    String confirmpasswordMD5 = MsgUtils.getMD5(confirmpassword);
		    System.out.println(confirmpasswordMD5);		  
		    AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");		
			Date d = new Date();
			d.getTime();
		    if(acc.getAccountcode() != null){
		    	iAccountManagement.changePassword(acc.getAccountcode(), confirmpasswordMD5);
		    	OperatorDiaryEntity operator = new OperatorDiaryEntity();
		    	operator.setAccountcode(acc.getAccountcode());
		    	operator.setDate(d);
		    	operator.setDescription("修改密码");
		    	operator.setModlename("账号信息");
		    	operator.setMemo("修改密码");
		    	iOperation.saveOrUpdate(operator);
	        	request.getSession().removeAttribute("account");  		    
		    }
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 初始化密码
	 */
	@RequestMapping(value = "/initializeAccountPassword.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String initializeAccountPassword(String accountcode, String areacode, HttpServletRequest request, HttpServletResponse response) throws Exception {		    
		    JsonResult jresult = new JsonResult();
		    AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");
			Date d = new Date();
			d.getTime();
/*		    System.out.println(acc.getAccountcode());
		    System.out.println(areacode);*/
		    
			try {
				 if(acc.getAccountcode().equals(accountcode)){ 
  			    	jresult.msg="无法初始化自己的账号密码";
  			    	return JsonUtils.toJSONString(jresult);
  		         }if(acc.getAreacode().length()>=areacode.length()){
  		        	jresult.msg="权限不足  无法完成初始化";
  			    	return JsonUtils.toJSONString(jresult);
  		         }
  			    iAccountManagement.clearPassword(accountcode);
  		    	OperatorDiaryEntity operator = new OperatorDiaryEntity();
		    	operator.setAccountcode(acc.getAccountcode());
		    	operator.setDate(d);
		    	operator.setDescription("初始化密码");
		    	operator.setModlename("账号管理");
		    	operator.setMemo("初始化密码");
		    	iOperation.saveOrUpdate(operator);
				jresult.success = true;
				jresult.msg="密码初始化成功";
			} catch (Exception e) {
				jresult.msg = e.getMessage();
			}
		   return JsonUtils.toJSONString(jresult);
	}
}
