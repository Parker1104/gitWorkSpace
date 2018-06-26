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

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICompanyInfo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CompanyEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;

/**
 * 
 * @author wenheju
 * 运营公司的信息
 *
 */
@Controller
@RequestMapping("/companyInfoController")
public class CompanyInfoController extends BaseController{
	@Autowired
	ICompanyInfo iCompanyInfo;
	@Autowired
	IOperationLogInpquire iOperation;
	
	@RequestMapping("/companyInfo")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/dic/companyInfo");
		return mv;
	}
	
	@RequestMapping(value="/selectCompany.do",produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
	//查询信息
	public String selectCompany(CompanyEntity com, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<CompanyEntity> CompanyPage = new Page<CompanyEntity>();
		CompanyPage.setPageSize(rows);
		CompanyPage.setPageNo(page);
		
		int totalRecords = 0;
		{
			
			//条件
			CompanyEntityExample example = new CompanyEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(com.getCompanyname())){
				example.createCriteria().andCompanynameLike("%"+com.getCompanyname()+"%");
			}			
			//查询总数
			totalRecords = (int) iCompanyInfo.count(example);
			
			List<CompanyEntity> companyList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				companyList = iCompanyInfo.findByExampleEntity(example);
			}
			
			CompanyPage.setResults(companyList);
			CompanyPage.setTotalRecord(totalRecords);
		}
		
		GridPage<CompanyEntity> gridPage = new GridPage<CompanyEntity>(CompanyPage);
		return JsonUtils.toJSONString(gridPage);	
	}	
	@RequestMapping(value = "/selectCompanyCode.do")
	@ResponseBody
	// 查询当前用户信息
	public String selectCompanyCode(CompanyEntity com , HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(com.getCompanycode());
		CompanyEntity companyEntity = iCompanyInfo.get(com.getCompanycode());
		String json = JsonUtils.toJSONString(companyEntity);
		return json;
		
	}
	// 查询（条件）信息
	@RequestMapping(value = "/selectCompanyName.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectCompanyName(String companyname, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");		
		System.out.println(companyname);
		CompanyEntityExample example = new CompanyEntityExample();
		example.setDistinct(true);
		example.createCriteria().andCompanynameEqualTo(companyname);
		List<CompanyEntity> companyEntity = iCompanyInfo.findByExampleEntity(example);
		String json = JsonUtils.toJSONNoFeatures(companyEntity);
		return json;
		
	}
	// 删除信息多
	@RequestMapping(value = "/delectCompany.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void delectCompany(String ids,HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		if(!StringUtils.isEmpty(ids)){
			for(String id :ids.split(",")){
				if(StringUtils.isEmpty(id)) continue;			
				iCompanyInfo.delete(id);
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("删除运营公司信息");
				operator.setModlename("基础资料");
				operator.setMemo("删除运营公司信息");
				iOperation.saveOrUpdate(operator);
				
			}
		}
	}
	@RequestMapping(value="/saveOrUpdateCompany.do",produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
	//添加修改信息
	public String saveOrUpdateCompany(CompanyEntity com,HttpServletRequest request, HttpServletResponse response) throws Exception {
		  JsonResult jresult=new JsonResult();
	      AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");//创建人
			Date d = new Date();
		    d.getTime();   	
				try{	
					String companycode   = com.getCompanycode();
					CompanyEntity companyList = iCompanyInfo.get(companycode);
					if(companyList == null){
							com.setMakeopcode(acc.getAccountname());
							com.setMakedate(d);
							com.setLastmodifytime(d);
							if(com.getLastmodifytime() == null){
								com.setCompanylinkname("null");
							}
							if(com.getCompanywelcome() == null){
								com.setCompanywelcome("null");
							}						
							iCompanyInfo.saveOrUpdate(com);
							OperatorDiaryEntity operator = new OperatorDiaryEntity();
							operator.setAccountcode(acc.getAccountcode());
							operator.setDate(d);
							operator.setDescription("增加运营公司信息");
							operator.setModlename("基础资料");
							operator.setMemo("增加运营公司信息");
							iOperation.saveOrUpdate(operator);
					}else {
						com.setMakeopcode(acc.getAccountname());
						com.setLastmodifytime(d);
						iCompanyInfo.saveOrUpdate(com);	
						OperatorDiaryEntity operator = new OperatorDiaryEntity();
						operator.setAccountcode(acc.getAccountcode());
						operator.setDate(d);
						operator.setDescription("修改运营公司信息");
						operator.setModlename("基础资料");
						operator.setMemo("修改运营公司信息");
						iOperation.saveOrUpdate(operator);
					}	
					jresult.success=true;
					jresult.msg="操作成功";	
				}catch(Exception e){
					jresult.msg=e.getMessage();
				}
			return JsonUtils.toJSONNoFeatures(jresult);	
	}	
}
