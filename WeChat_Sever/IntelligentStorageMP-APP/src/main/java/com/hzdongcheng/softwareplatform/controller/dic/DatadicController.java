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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDatadic;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DictEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;


/**
 * 
 * @author wenheju
 *    数据字典 
 */
@Controller
@RequestMapping("/datadicController")
public class DatadicController extends BaseController{
	@Autowired
	IDatadic iDatadic;
	@Autowired
	IOperationLogInpquire iOperation;
	
	@RequestMapping("/datadic")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/dic/datadic");
		return mv;
	}
	
	@RequestMapping(value = "/selectDatadic.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody	
	//查询信息
	public String selectDatadic(DictEntity dic, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<DictEntity> dictPage = new Page<DictEntity>();
		dictPage.setPageSize(rows);
		dictPage.setPageNo(page);
		
		int totalRecords = 0;
		{
			
			//条件
			DictEntityExample example = new DictEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(dic.getDictname())){
				example.createCriteria().andDictnameLike("%"+dic.getDictname()+"%");
			}			
			//查询总数
			totalRecords = (int) iDatadic.count(example);
			
			List<DictEntity> dictList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				dictList = iDatadic.findByExampleEntity(example);
			}
			
			dictPage.setResults(dictList);
			dictPage.setTotalRecord(totalRecords);
		}
		
		GridPage<DictEntity> gridPage = new GridPage<DictEntity>(dictPage);
		return JsonUtils.toJSONString(gridPage);					
	}
	/**
	 * @param acc
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询信息
	 */
	@RequestMapping(value = "/selectDatadicCode.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody	
	public String selectDatadicCode(DictEntity dic, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(dic.getDicttypecode());
		DictEntity dictEntity = iDatadic.get(dic.getDicttypecode(), dic.getDictcode());
		String json = JsonUtils.toJSONString(dictEntity);
		return json;
		
	}
	
	// 删除信息
	@RequestMapping(value = "/delectDatadic.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void delectDatadic(DictEntity dic, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();  
		iDatadic.delete(dic.getDicttypecode(), dic.getDictcode());
		OperatorDiaryEntity operator = new OperatorDiaryEntity();
		operator.setAccountcode(acc1.getAccountcode());
		operator.setDate(d);
		operator.setDescription("删除数据字典");
		operator.setModlename("基础资料");
		operator.setMemo("删除数据字典");
		iOperation.saveOrUpdate(operator);
	}
	// 添加 修改箱体信息
	@RequestMapping(value = "/saveOrUpdateDatadic.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String saveOrUpdateDatadic(HttpServletRequest request, HttpServletResponse response) throws Exception {		    
			JsonResult jresult=new JsonResult();
		    String dictTypeName = request.getParameter("dictTypeName");
		    System.out.println(dictTypeName);
		    
		    String dictCode = request.getParameter("dictCode");
		    System.out.println(dictCode);
		    
		    String dictName = request.getParameter("dictName");
		    System.out.println(dictName);
		    String memo = request.getParameter("memo");
		    System.out.println(memo);
		    
			String dictTypeCode = getParamString(request, "dictTypeCode", "");
			System.out.println(dictTypeCode);
			AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
			Date d = new Date();
			d.getTime();
			DictEntity dictEntity = iDatadic.get(dictTypeCode,Integer.parseInt(dictCode));
			//校验
			if(StringUtils.isEmpty(dictTypeCode)){
				jresult.msg="类型编号不能为空！";
			}else{
				try{
					DictEntity dic = new DictEntity();
					if(!StringUtils.isEmpty(dictTypeCode) || !StringUtils.isEmpty(dictCode))
						dic.setDicttypecode(dictTypeCode);
					    dic.setDicttypename(dictTypeName);
						dic.setDictcode(Integer.parseInt(dictCode));
						dic.setDictname(dictName);
						dic.setMemo(memo);
						iDatadic.saveOrUpdate(dic);	
						if(dictEntity == null){
							OperatorDiaryEntity operator = new OperatorDiaryEntity();
							operator.setAccountcode(acc1.getAccountcode());
							operator.setDate(d);
							operator.setDescription("增加数据字典");
							operator.setModlename("基础资料");
							operator.setMemo("增加数据字典");
							iOperation.saveOrUpdate(operator);
						}else {
							OperatorDiaryEntity operator = new OperatorDiaryEntity();
							operator.setAccountcode(acc1.getAccountcode());
							operator.setDate(d);
							operator.setDescription("修改数据字典");
							operator.setModlename("基础资料");
							operator.setMemo("修改数据字典");
							iOperation.saveOrUpdate(operator);
						}
					//List<RoleEntity> roleList = roledao.findAll();
					jresult.success=true;
					jresult.msg="操作成功";
					//jresult.data=JsonUtils.list2json(roleList);
				}catch(Exception e){
					jresult.msg=e.getMessage();
				}
			}
			return JsonUtils.toJSONNoFeatures(jresult);	   		         
	}
	
}
