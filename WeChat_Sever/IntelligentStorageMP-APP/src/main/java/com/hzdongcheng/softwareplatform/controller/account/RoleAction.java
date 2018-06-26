package com.hzdongcheng.softwareplatform.controller.account;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMenuRightsService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IRoleManagementService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.MenuRightsEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AccountExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RoleEntityExample;

@Controller
@RequestMapping("/roleAction")
public class RoleAction extends BaseController{
	@Autowired
	IRoleManagementService roledao;
	@Autowired
	IMenuRightsService menuRightsManagementDao;
	@Autowired
	AccountExDao accountExDao;
	@Autowired
	IOperationLogInpquire iOperation;

	@RequestMapping("/role") // 返回界面
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/account/role"); // 返回路径	
		return mv;
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 增加修改角色
	 */
	@RequestMapping(value="/insertRole.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String insertRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jresult=new JsonResult();
		String name = request.getParameter("rolenameadd");
		String remark = request.getParameter("memo");
		String rolecode=getParamString(request, "rolecode", "");
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		
		Date d = new Date();
		d.getTime();
		//校验
		if(StringUtils.isEmpty(name)){
			jresult.msg=String.format(getMessage(request, "param.notempty"), getMessage(request, "role.tip.rolename"));
		}else{
			try{
				RoleEntity rol = new RoleEntity();
				if(!StringUtils.isEmpty(rolecode)) 
				rol.setRolecode(Integer.parseInt(rolecode));
				rol.setRolename(name);
				rol.setMemo(remark);
				roledao.saveOrUpdate(rol);
				if(rolecode == null || rolecode == ""){
					OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(acc1.getAccountcode());		
					operator.setDate(d);
					operator.setDescription("添加角色");
					operator.setModlename("账号管理");
					operator.setMemo("添加角色");
					iOperation.saveOrUpdate(operator);
				}else{
					OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(acc1.getAccountcode());		
					operator.setDate(d);
					operator.setDescription("修改角色");
					operator.setModlename("账号管理");
					operator.setMemo("修改角色");
					iOperation.saveOrUpdate(operator);
				}
				//List<RoleEntity> roleList = roledao.findAll();
				jresult.success=true;
				jresult.msg=getMessage(request, "tip.success");
				//jresult.data=JsonUtils.list2json(roleList);
			}catch(Exception e){
				jresult.msg=e.getMessage();
			}
		}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	/**
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询角色分页列表
	 */
	@RequestMapping(value="/selectRole.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectRole(RoleEntity vo,HttpServletRequest request, HttpServletResponse response) throws Exception {

		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<RoleEntity> RolePage = new Page<RoleEntity>();
		RolePage.setPageSize(rows);
		RolePage.setPageNo(page);

		int totalRecords = 0;
		{

			//条件
			RoleEntityExample example = new RoleEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(vo.getRolename())){
				example.createCriteria().andRolenameLike("%"+vo.getRolename()+"%");	
			}

			//查询总数
			totalRecords = (int) roledao.count(example);

			List<RoleEntity> roleEntityList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				roleEntityList = roledao.findAll(example);
			}

			RolePage.setResults(roleEntityList);
			RolePage.setTotalRecord(totalRecords);
		}

		GridPage<RoleEntity> gridPage = new GridPage<RoleEntity>(RolePage);
		return JsonUtils.toJSONString(gridPage);		
	}
	/**
	 * @param rolenameadd
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询（条件）信息
	 */
	@RequestMapping(value = "/selectRoleName.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectRoleName(String rolenameadd, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");		
		System.out.println(rolenameadd);
		RoleEntityExample example = new RoleEntityExample();
		example.setDistinct(true);
		example.createCriteria().andRolenameEqualTo(rolenameadd);
		List<RoleEntity> roleEntity = roledao.findAll(example);
		String json = JsonUtils.toJSONNoFeatures(roleEntity);
		return json;

	}
	/**
	 * @param rolecode
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询角色分页列表
	 */
	@RequestMapping(value="/selectAccountRoleCode.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectAccountRoleCode(String rolecode,RoleEntity vo,HttpServletRequest request, HttpServletResponse response) throws Exception {	
		List<AccountEx> account = accountExDao.selectAccountRoleCode(Integer.parseInt(rolecode));
		return JsonUtils.toJSONString(account);		
	}
	/**
	 * @param rolecode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 删除
	 */
	@RequestMapping(value="/delectRole.do")
	@ResponseBody
	@Transactional(rollbackFor=Exception.class)
	public String delectRole(String rolecode,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jresult=new JsonResult();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		
		Date d = new Date();
		d.getTime();
		try {		
			List<MenuRightsEx> menuRightsEx= menuRightsManagementDao.get(Integer.parseInt(rolecode));
			if(menuRightsEx != null){
				menuRightsManagementDao.deleteByPrimaryKeyRoleCode(rolecode);
				roledao.delete(Integer.parseInt(rolecode));
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());		
				operator.setDate(d);
				operator.setDescription("删除角色");
				operator.setModlename("账号管理");
				operator.setMemo("删除角色");
				iOperation.saveOrUpdate(operator);
			}else {
				roledao.delete(Integer.parseInt(rolecode));
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());		
				operator.setDate(d);
				operator.setDescription("删除角色");
				operator.setModlename("账号管理");
				operator.setMemo("删除角色");
				iOperation.saveOrUpdate(operator);
									
			}	

			jresult.success=true;
			jresult.msg="操作成功";
		} catch (Exception e) {
			jresult.msg=e.getMessage();
		}
		return JsonUtils.toJSONNoFeatures(jresult);	
	}
}
