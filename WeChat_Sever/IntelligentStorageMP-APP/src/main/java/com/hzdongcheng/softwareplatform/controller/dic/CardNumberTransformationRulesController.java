package com.hzdongcheng.softwareplatform.controller.dic;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBusinessModel;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICardnumberTransformationRules;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDatadic;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardTransRuleEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardTransRuleExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;

/**
 * 
 * @author wenheju
 *   卡号转换规则
 */
@Controller
@RequestMapping("/cardNumberTransformationRulesController")
public class CardNumberTransformationRulesController extends BaseController {
	@Autowired
	ICardnumberTransformationRules iCardnumberTransformationRules;
	@Autowired
	IDatadic iDatadic;
	@Autowired
	CardTransRuleExDao cardTransRuleExDao;
	@Autowired
	IBusinessModel iBusinessModel;
	@Autowired
	IOperationLogInpquire iOperation;
	
	@RequestMapping("/cardNumberTransformationRules")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();		
		mv.setViewName("/web/dic/cardNumberTransformationRules");
		mv.addObject("datadic", iDatadic.getCardType());
		return mv;
	}
	
	@RequestMapping(value = "/selectCardTransRule.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody	
	//查询信息
	public String selectCardTransRule(CardTransRuleEntity car, DictEntity dic, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<CardTransRuleEx> CardPage = new Page<CardTransRuleEx>();
		CardPage.setPageSize(rows);
		CardPage.setPageNo(page);
		
		int totalRecords = 0;
		{
			
			//条件
			CardTransRuleEntityExample example = new CardTransRuleEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(car.getTransrulename())){
				example.createCriteria().andTransrulenameLike("%"+car.getTransrulename()+"%");
			}			
			//查询总数
			totalRecords = (int) iCardnumberTransformationRules.count(example);
			List<CardTransRuleEx> cardTransRuleList = null;
			
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);	
				cardTransRuleList = iCardnumberTransformationRules.findByExampleEx(example);
				if(cardTransRuleList != null){
					for(CardTransRuleEx rule: cardTransRuleList){
						rule.setDictEntity(iDatadic.get(Constant.DICT_CARDTYPE, rule.getCardtype()));
					}
				}
				
			}
			
			CardPage.setResults(cardTransRuleList);
			CardPage.setTotalRecord(totalRecords);
		}
		
		GridPage<CardTransRuleEx> gridPage = new GridPage<CardTransRuleEx>(CardPage);
		return JsonUtils.toJSONString(gridPage);
	}	
	// 查询（条件）信息
	@RequestMapping(value = "/selectCardTransRuleName.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectCardTransRuleName(String transrulename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");		
		System.out.println(transrulename);
		CardTransRuleEntityExample example = new CardTransRuleEntityExample();
		example.setDistinct(true);
		example.createCriteria().andTransrulenameEqualTo(transrulename);
		List<CardTransRuleEx> cardTransRuleEx = iCardnumberTransformationRules.findByExampleEx(example);
		String json = JsonUtils.toJSONNoFeatures(cardTransRuleEx);
		return json;
		
	}
	// 查询（条件）信息   业务类型
	@RequestMapping(value = "/delect.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String delect(String transrulecode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");		
		System.out.println(transrulecode);
		BusinessModelEntityExample example = new BusinessModelEntityExample();
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andConfignameEqualTo("Rule");
		criteria.andConfigvalueEqualTo(transrulecode);
		List<BusinessModelEntity> businessModelEntity = iBusinessModel.findByExample(example);
		return JsonUtils.toJSONNoFeatures(businessModelEntity);
		 
		
	}
	// 删除信息
		@RequestMapping(value = "/delectCardTransRule.do",produces = {"text/html;charset=UTF-8;"})
		@ResponseBody
		public void delectCardTransRule(HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setCharacterEncoding("utf-8");		
			/*String transRuleCode = request.getParameter("transrulecode");*/
			Integer transRuleCode = Integer.parseInt(request.getParameter("transrulecode"));
			System.out.println(transRuleCode);
			iCardnumberTransformationRules.delete(transRuleCode);
		}
		// 删除信息多选
		@RequestMapping(value = "/delectCardTransRules.do",produces = {"text/html;charset=UTF-8;"})
		@ResponseBody
		public void delectCardTransRules(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
			JSONObject jo = new JSONObject();
			AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
			Date d = new Date();
			d.getTime();
			try {
				iCardnumberTransformationRules.delete(Integer.parseInt(id));
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("删除卡号转换规则");
				operator.setModlename("业务场景设置");
				operator.setMemo("删除卡号转换规则");
				iOperation.saveOrUpdate(operator);
				jo.put("msg", "删除成功！");
			} catch (Exception e) {
				e.printStackTrace();
				jo.put("msg", "删除失败！");
			}
		    response.getWriter().print(jo); 
		}
	@RequestMapping(value = "/saveOrUpdateCardTransRule.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody	
	//增加 修改  信息
	public String saveOrUpdateCardTransRule(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		
		JsonResult jresult=new JsonResult();		
		String transRuleName = request.getParameter("transRuleName");  
	
		Integer cardType = Integer.parseInt(request.getParameter("cardType"));	
	
		String memo = request.getParameter("memo");		
		
		Integer cardLen = Integer.parseInt(request.getParameter("cardLen"));	
	
		Integer cardRule = Integer.parseInt(request.getParameter("cardRule"));		
		
		Integer decimalism = Integer.parseInt(request.getParameter("decimalism"));
			
		Integer startSubStr = Integer.parseInt(request.getParameter("startSubStr"));
		
		String transRuleCode= getParamString(request, "transRuleCode", "");
		
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		
		//校验
		if(StringUtils.isEmpty(transRuleName)){
			jresult.msg="卡类型不能为空！";
		}else{
			try{
				CardTransRuleEntity  car = new CardTransRuleEntity();
				if(!StringUtils.isEmpty(transRuleCode)) 
			    car.setTransrulecode(Integer.parseInt(transRuleCode));
				car.setTransrulename(transRuleName);
				car.setCardtype(cardType);
				car.setDecimalism(decimalism);
				car.setCardlen(cardLen);
				car.setCardrule(cardRule);
				car.setStartsubstr(startSubStr);
				car.setMemo(memo);
				iCardnumberTransformationRules.saveOrUpdate(car);
				if(transRuleCode == null || transRuleCode == ""){
					OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(acc1.getAccountcode());
					operator.setDate(d);
					operator.setDescription("新增卡号转换规则");
					operator.setModlename("业务场景设置");
					operator.setMemo("新增卡号转换规则");
					iOperation.saveOrUpdate(operator);
				}else {
					OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(acc1.getAccountcode());
					operator.setDate(d);
					operator.setDescription("修改卡号转换规则");
					operator.setModlename("业务场景设置");
					operator.setMemo("修改卡号转换规则");
					iOperation.saveOrUpdate(operator);
				}
					
				jresult.success=true;
				jresult.msg="操作成功";	
			}catch(Exception e){
				jresult.msg=e.getMessage();
			}
		}
		return JsonUtils.toJSONNoFeatures(jresult);	   	
	}
}
