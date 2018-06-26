package com.hzdongcheng.softwareplatform.controller.account;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMenuRightsService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.vo.MenuVo;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.MenuRightsEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuRightsEntity;

@Controller
@RequestMapping("/menuRightsController")
public class MenuRightsController extends BaseController {

	@Autowired
	IMenuRightsService menuRightsManagementDao;

	@RequestMapping(value = "/insertRightsController.do", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	@Transactional(rollbackFor=Exception.class)
	// 查询角色权限 选择菜单的(添加、修改菜单)
	public String insertRightsController(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 取出数据添加
		String rolecode = request.getParameter("rolecode");
		String ids = request.getParameter("ids");

		JsonResult jresult=new JsonResult();
		if(StringUtils.isEmpty(rolecode)||StringUtils.isEmpty(ids)){
			jresult.msg=getMessage(request, "param.iserror");
		}else{
			menuRightsManagementDao.deleteByPrimaryKeyRoleCode(rolecode);
			for (String id: ids.split(",")) {
				if(StringUtils.isEmpty(id)) continue;
				//是否功能节点
				String[] arr=id.split("_");
				 String aa = null;
				if(arr[0].length() != 3){
					List<MenuRightsEx>  menuRightsEx = menuRightsManagementDao.get(Integer.parseInt(rolecode));
				   for(MenuRightsEx men : menuRightsEx){
					   aa =  men.getMenuoperator();
				   }
				}
				if(arr.length<2){
					MenuRightsEntity menuRightsEntity=new MenuRightsEntity();
					menuRightsEntity.setRolecode(Integer.parseInt(rolecode));
					menuRightsEntity.setMenucode(id);
					menuRightsEntity.setMenuaccess("1");
					menuRightsEntity.setMenuoperator("");
					menuRightsEntity.setMenuauthorize("0");
					menuRightsManagementDao.saveOrUpdate(menuRightsEntity);
				}
				else{	
					MenuRightsEntity menuRightsEntity=new MenuRightsEntity();
					menuRightsEntity.setRolecode(Integer.parseInt(rolecode));
					menuRightsEntity.setMenucode(arr[0]);
					menuRightsEntity.setMenuaccess("1");
					if(arr[0].length() != 3){
						menuRightsEntity.setMenuoperator(aa + arr[1]);
					}else {
						menuRightsEntity.setMenuoperator(arr[1]);
					}
					
					menuRightsEntity.setMenuauthorize("0");
					menuRightsManagementDao.saveOrUpdate(menuRightsEntity);
				}
			}
			jresult.success=true;
		}

		return JsonUtils.toJSONNoFeatures(jresult);
	}

	@RequestMapping(value = "/selectRightsController.do",produces = "application/json;charset=UTF-8")
	@ResponseBody
	// 查询 角色 权限
	public String selectRightsController(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer roleCode = Integer.parseInt(request.getParameter("rolecode"));
		
		JsonResult jresult=new JsonResult();
		List<String> lst=new ArrayList<String>();
		try{
			List<MenuRightsEx> menuRightsList = menuRightsManagementDao.get(roleCode);
			for (MenuRightsEx mr : menuRightsList) {
				String menucode=mr.getMenucode();
				lst.add(menucode);
				String buttons=mr.getMenuoperator();
				if(StringUtils.isNotEmpty(buttons)){
					for(char c : buttons.toCharArray()){
						lst.add(menucode+"_"+c);
					}
				}
			}
			jresult.success=true;
			jresult.data=JsonUtils.toJSONNoFeatures(lst);
		}catch(Exception e){
			jresult.success=false;
			jresult.msg=getMessage(request, "menuright.get.faul");
		}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	
	/**
	 * 首页菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getMenuByCode.do",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getMenuByCode(HttpServletRequest request, HttpServletResponse response){
		Integer roleCode = getParamInt(request, "rolecode",0);
		if(roleCode==0){
			AccountEntity account=(AccountEntity)request.getSession().getAttribute(Constant.SessionID);
			if(account!=null){
				roleCode=account.getRolecode();
			}
		}
		List<MenuVo> lst=new ArrayList<MenuVo>();
		try{
			List<MenuRightsEx> menuRightsList = menuRightsManagementDao.get(roleCode);
			for (MenuRightsEx mr : menuRightsList) {
				MenuVo vo=new MenuVo();
				vo.setModuleId(mr.getMenucode());
				vo.setParentId(mr.getMenucode().substring(0, mr.getMenucode().length()-3));
				vo.setFullName(getMessage(request, Constant.MenuCodePre + mr.getMenucode()));
				vo.setTarget("expand");
				
				MenuEntity menu=mr.getMenu();
				vo.setIcon(menu.getMenuicon());
				vo.setUrlAddress(menu.getMenuurl());
				vo.setIsMenu(menu.getMenudub());
				
				//System.out.println(" 菜单   "+vo.getModuleId()+" "+vo.getParentId()+"  "+vo.getFullName());
 
				
				lst.add(vo);
			}
		}catch(Exception e){
		}
		return JsonUtils.toJSONNoFeatures(lst);
	}
}