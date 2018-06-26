package com.hzdongcheng.softwareplatform.controller.log;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OperatorDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntityExample;
/**
 * 
 * @author wenheju
 *   操作日志
 *
 */
@Controller
@RequestMapping("/operateLogInquireController")
public class OperateLogInquireController extends BaseController{

	@Autowired
	private IOperationLogInpquire  ioperationLogInpquire;

	@RequestMapping("/operateLogInquire")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/log/operateLogInquire");	
		return mv;
	}
	/** 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询所以操作日志
	 */
	@RequestMapping(value = "/selectOperationLogInpquire", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectOperationLogInpquire(OperatorDiaryEntity oper,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		String date_1 = request.getParameter("date_1"); 
		String date_2 = request.getParameter("date_2");

		//获取当前用户
		/*AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");//创建人*/
		Page<OperatorDiaryEx> OperatorPage = new Page<OperatorDiaryEx>();
		OperatorPage.setPageSize(rows);
		OperatorPage.setPageNo(page);

		int totalRecords = 0;
		{
			//条件
			OperatorDiaryEntityExample example = new OperatorDiaryEntityExample();
			example.setDistinct(true);
			example.setOrderByClause("date desc");
			/*Criteria criteria = example.createCriteria();*/
			/*criteria.andAccountcodeEqualTo(acc.getAccountcode());*/	
			if(!StringUtils.isEmpty(date_1) || !StringUtils.isEmpty(date_2)){
				example.createCriteria().andDateBetween(DateUtils.stringToDateTime(date_1),  DateUtils.stringToDateTime(date_2));
			}		
			//查询总数
			totalRecords = (int) ioperationLogInpquire.count(example);

			List<OperatorDiaryEx> operatorList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				operatorList = ioperationLogInpquire.selectByExampleOperatorEx(example);
			}

			OperatorPage.setResults(operatorList);
			OperatorPage.setTotalRecord(totalRecords);
		}

		GridPage<OperatorDiaryEx> gridPage = new GridPage<OperatorDiaryEx>(OperatorPage);
		return JsonUtils.toJSONString(gridPage);	
	}	

}
