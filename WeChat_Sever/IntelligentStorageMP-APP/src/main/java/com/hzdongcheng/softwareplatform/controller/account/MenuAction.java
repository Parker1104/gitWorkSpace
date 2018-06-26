package com.hzdongcheng.softwareplatform.controller.account;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMenuService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.vo.TreeModelVo;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MenuEntity;


@Controller
@RequestMapping("/menuAction")
public class MenuAction extends BaseController{
	@Autowired
	IMenuService menuService;

	@RequestMapping(value="/getmenulist.do",produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
	public String getMenuList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//JsonResult jresult=new JsonResult();
		List<MenuEntity> lst=menuService.findAll();
		List<TreeModelVo> lstvo=new ArrayList<TreeModelVo>();
		
		for(MenuEntity menu : lst){
			TreeModelVo vo=new TreeModelVo();
			vo.setId(menu.getMenucode());
			vo.setName(getMessage(request, Constant.MenuCodePre+menu.getMenucode()));
			//vo.setOpen(true);
			vo.setpId(menu.getMenucode().substring(0, menu.getMenucode().length()-3));
			lstvo.add(vo);
			
			String buttons=menu.getMenubutton();//功能点作为子节点
			if(StringUtils.isNotEmpty(buttons)){
				for(char c : buttons.toCharArray()){
					//授权权限 A:增加 M:修改 D:删除 Q:查询;E;导出;O开箱;C清箱;L锁;U解锁;S:统计;I:用户登记;C用户注销;P:管理员推送;N:管理员取消
					vo=new TreeModelVo();
					vo.setId(menu.getMenucode()+"_"+c);
					vo.setName(getMessage(request, Constant.MenuCodePre+c));
					vo.setpId(menu.getMenucode());
					lstvo.add(vo);
				}
			}
		}
		//jresult.success=true;
		//jresult.data=JsonUtils.bean2json(lstvo);
		
		return JsonUtils.toJSONNoFeatures(lstvo);
	}
}