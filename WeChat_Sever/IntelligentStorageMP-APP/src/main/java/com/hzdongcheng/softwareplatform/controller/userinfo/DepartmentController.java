package com.hzdongcheng.softwareplatform.controller.userinfo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDepartment;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.vo.TreeModelVo;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.DepartmentExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.DepartmentEntityExample;

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {
	@Autowired
	IDepartment iDepartment;	
	
	@RequestMapping("/view")
	public ModelAndView name() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/userinfo/department");
		return mv;
	}
	
	@RequestMapping(value = "/saveDepartmentName",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String saveAreaName(TreeModelVo vo,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jresult=new JsonResult();
		String memo=getParamString(request, "memo", "");
		
		if(vo==null || StringUtils.isEmpty(vo.getName())){
			jresult.msg=getMessage(request, "param.iserror");
		}else{
			try{
				DepartmentEntity rol = new DepartmentEntity();
				rol.setId(vo.getId());
				rol.setDepartment(vo.getName());
				rol.setMemo(memo);
				if(StringUtils.isEmpty(rol.getId())){
					//生成当前编码
					int currmax=0;
					String pId=vo.getpId();
					String currmaxpId=iDepartment.findMaxChild(pId);
					if(StringUtils.isNotEmpty(currmaxpId)){
						currmax=Integer.parseInt(currmaxpId.substring(pId.length()));
						currmax++;
					}
					rol.setId(pId+StringUtils.leftPad(currmax+"", 2,'0'));
				}
				iDepartment.saveOrUpdate(rol);
				
				DepartmentEntity ae=iDepartment.get(rol.getId());
				vo=new TreeModelVo();
				vo.setId(ae.getId());
				if(StringUtils.isEmpty(ae.getMemo())){
					vo.setName(ae.getDepartment());
				}else{
					vo.setName(String.format("%s(%s)",ae.getDepartment(),ae.getMemo()));
				}
				vo.setOpen(true);
				vo.setpId(ae.getId().substring(0, ae.getId().length()-2));
				
				jresult.data=JsonUtils.toJSONNoFeatures(vo);
				jresult.success=true;
				jresult.msg=getMessage(request, "tip.success");
			}catch(Exception e){
				jresult.msg=e.getMessage();
			}
		}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	
	@RequestMapping(value = "/removeNode",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String removeNode(String id,HttpServletRequest request) throws Exception {
		JsonResult jresult=new JsonResult();	
			try{
				DepartmentEntityExample example = new DepartmentEntityExample();
				example.setDistinct(true);
				example.createCriteria().andIdLike(id +"%");
				List<DepartmentEntity> departmentEntity = iDepartment.findAll(example);
				for(int i = 0; i < departmentEntity.size(); i++){
					/* System.out.println(areaEntity.get(i).getAreacode());*/
					 iDepartment.delete(departmentEntity.get(i).getId());
				}
							
				jresult.success=true;
				jresult.msg= getMessage(request, "tip.success");
			}catch(Exception e){
				jresult.msg= e.getMessage(); 
			}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	
	@RequestMapping(value = "/findAll",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		
		List<TreeModelVo> lst=new ArrayList<TreeModelVo>();
		List<DepartmentEntity> departmentList = iDepartment.findAll();
		for(DepartmentEntity ae:departmentList){
			TreeModelVo vo=new TreeModelVo();
			vo.setId(ae.getId());
			if(StringUtils.isEmpty(ae.getMemo())){
				vo.setName(requestContext.getMessage(ae.getDepartment()));
			}else{
				vo.setName(String.format("%s(%s)",ae.getDepartment(),ae.getMemo()));
			}
			vo.setOpen(true);
			vo.setpId(ae.getId().substring(0, ae.getId().length()-2));
			
			lst.add(vo);
		}
		
		return JsonUtils.toJSONNoFeatures(lst);
	}
	/**
	 * @param are
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectbyid", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectArea(DepartmentEntity are, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		   /*System.out.println(are.getAreacode());*/
		   DepartmentEntity department = iDepartment.get(are.getId());;
		   String json = JsonUtils.toJSONString(department);
		   return json;
	}
	/**
	 * @param are
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDepartmentFu", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectAreaFu(DepartmentEntity are, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		   /*System.out.println(are.getAreacode());*/
		   if(are.getId().length() == 6){
			   DepartmentEntity department = iDepartment.get(are.getId().substring(0,4));
			   String json = JsonUtils.toJSONString(department);
			   return json;
		   }else if (are.getId().length() == 4) {
			   DepartmentEntity department = iDepartment.get(are.getId().substring(0,2));
			   String json = JsonUtils.toJSONString(department);
			   return json;
		   }
		   DepartmentEntity department = iDepartment.get(are.getId());
		   String json = JsonUtils.toJSONString(department);
		   return json;
		  
					 	

	
	}
}
