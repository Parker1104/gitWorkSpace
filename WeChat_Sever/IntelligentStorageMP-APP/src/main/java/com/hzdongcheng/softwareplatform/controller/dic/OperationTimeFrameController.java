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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBusinessModel;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationtimeframe;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RunTimeEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;

/**
 * 
 * @author wenheju
 *   运行时段
 */
@Controller
@RequestMapping("/operationTimeFrameController")
public class OperationTimeFrameController extends BaseController{
	@Autowired
	IOperationtimeframe iOperationtimeframe;
	@Autowired
	IBusinessModel iBusinessModel;
	@Autowired
	IOperationLogInpquire iOperation;

	@RequestMapping("/operationtimeframe")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/dic/operationtimeframe");
		return mv;
	}

	@RequestMapping(value="/selectRunTime.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	//查询信息
	public String selectRunTime(RunTimeEntity run, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<RunTimeEntity> RunTimePage = new Page<RunTimeEntity>();
		RunTimePage.setPageSize(rows);
		RunTimePage.setPageNo(page);

		int totalRecords = 0;
		{

			//条件
			RunTimeEntityExample example = new RunTimeEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(run.getRuntimename())){
				example.createCriteria().andRuntimenameLike("%"+run.getRuntimename()+"%");
			}			
			//查询总数
			totalRecords = (int) iOperationtimeframe.count(example);

			List<RunTimeEntity> runTimePageList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				runTimePageList = iOperationtimeframe.findByExampleEntity(example);
			}

			RunTimePage.setResults(runTimePageList);
			RunTimePage.setTotalRecord(totalRecords);
		}

		GridPage<RunTimeEntity> gridPage = new GridPage<RunTimeEntity>(RunTimePage);
		return JsonUtils.toJSONString(gridPage);	

	}	

	// 查询（条件）信息
	@RequestMapping(value = "/selectRunTimeName.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectRunTimeName(String runtimename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");		
		System.out.println(runtimename);
		RunTimeEntityExample example = new RunTimeEntityExample();
		example.setDistinct(true);
		example.createCriteria().andRuntimenameEqualTo(runtimename);
		List<RunTimeEntity> runTimeEntity = iOperationtimeframe.findByExampleEntity(example);
		String json = JsonUtils.toJSONNoFeatures(runTimeEntity);
		return json;

	}

	// 查询（条件）信息
	@RequestMapping(value = "/selectRunTimeCold.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectRunTimeCold(String runtimecode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");		
		System.out.println(runtimecode);
		BusinessModelEntityExample example = new BusinessModelEntityExample();
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andConfignameEqualTo("RunTime");
		criteria.andConfigvalueEqualTo(runtimecode);
		List<BusinessModelEntity> businessModelEntity = iBusinessModel.findByExample(example);
		return JsonUtils.toJSONNoFeatures(businessModelEntity);

	}

	// 删除信息多选
	@RequestMapping(value = "/delectRunTime.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void delectRunTime(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			iOperationtimeframe.delete(Integer.parseInt(id));
			OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("删除运营时段");
			operator.setModlename("业务场景设置");
			operator.setMemo("删除运营时段");
			iOperation.saveOrUpdate(operator);
			jo.put("msg", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败！");
		}
		response.getWriter().print(jo); 
	}
	@RequestMapping(value="/saveOrUpdateRunTime.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	//添加修改信息
	public String saveOrUpdateRunTime(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonResult jresult=new JsonResult();
		String runTimeCode = getParamString(request, "runtimeCode", "");
		String  runTimeName = request.getParameter("runTimeName"); 
		System.out.println(runTimeName);
		String monStartRunTime = request.getParameter("monStartRunTime");    
		System.out.println(monStartRunTime);
		String monEndRunTime  = request.getParameter("monEndRunTime");   
		System.out.println(monEndRunTime);
		String tueStartRunTime = request.getParameter("tueStartRunTime"); 
		System.out.println(tueStartRunTime);
		String tueEndRunTime = request.getParameter("tueEndRunTime");  
		System.out.println(tueEndRunTime);
		String wedStartRunTime = request.getParameter("wedStartRunTime"); 
		System.out.println(wedStartRunTime);
		String wedEndRunTime  = request.getParameter("wedEndRunTime");  
		System.out.println(wedEndRunTime);
		String thursStartRunTime  = request.getParameter("thursStartRunTime");   
		System.out.println(thursStartRunTime);
		String thursEndRunTime  = request.getParameter("thursEndRunTime");  
		System.out.println(thursEndRunTime);
		String friStartRunTime = request.getParameter("friStartRunTime");
		System.out.println(friStartRunTime);
		String friEndRunTime  = request.getParameter("friEndRunTime");  
		System.out.println(friEndRunTime);
		String satStartRunTime  = request.getParameter("satStartRunTime");
		System.out.println(satStartRunTime);
		String satEndRunTime  = request.getParameter("satEndRunTime");  
		System.out.println(satEndRunTime);
		String sunStartRunTime = request.getParameter("sunStartRunTime"); 
		System.out.println(sunStartRunTime);
		String sunEndRunTime  = request.getParameter("sunEndRunTime");    
		System.out.println(sunEndRunTime);
		AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");//创建人
		String makeOpCode = acc.getAccountname();
		System.out.println(makeOpCode);   
		Date d = new Date();
		d.getTime();       
		//校验
		if(StringUtils.isEmpty(runTimeName)){
			jresult.msg="角色名不能为空！";
		}else{
			try{
				RunTimeEntity run = new RunTimeEntity();
				if(!StringUtils.isEmpty(runTimeCode)) 
					run.setRuntimecode(Integer.parseInt(runTimeCode));				  
				run.setRuntimename(runTimeName);
				run.setMonstartruntime(monStartRunTime);
				run.setMonendruntime(monEndRunTime);
				run.setTuestartruntime(tueStartRunTime);
				run.setTueendruntime(tueEndRunTime);
				run.setWedstartruntime(wedStartRunTime);
				run.setWedendruntime(wedEndRunTime);
				run.setThursstartruntime(thursStartRunTime);
				run.setThursendruntime(thursEndRunTime);
				run.setFristartruntime(friStartRunTime);
				run.setFriendruntime(friEndRunTime);
				run.setSatstartruntime(satStartRunTime);
				run.setSatendruntime(satEndRunTime);
				run.setSunstartruntime(sunStartRunTime);
				run.setSunendruntime(sunEndRunTime);
				run.setMakeopcode(makeOpCode);
				run.setMakedate(d);			   
				iOperationtimeframe.saveOrUpdate(run);
				if(runTimeCode == null || runTimeCode==""){
					OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(acc.getAccountcode());
					operator.setDate(d);
					operator.setDescription("新增运营时段");
					operator.setModlename("业务场景设置");
					operator.setMemo("新增运营时段");
					iOperation.saveOrUpdate(operator);
				}else {
					OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(acc.getAccountcode());
					operator.setDate(d);
					operator.setDescription("修改运营时段");
					operator.setModlename("业务场景设置");
					operator.setMemo("修改运营时段");
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
