package com.hzdongcheng.softwareplatform.controller.userinfo;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationtimeframe;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserAndRuntime;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.UserAndRuntimeBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserAndRuntimeBoundEntityExample.Criteria;

@Controller
@RequestMapping("/userAndRuntime")
public class UserAndRuntimeController extends BaseController{
	
	@Autowired
	IUserAndRuntime iUserAndRuntime;
	@Autowired
	IOperationtimeframe iOperationtimeframe;
	@Autowired
	IUserService iUserService;
	
	@RequestMapping("/view")
	public ModelAndView name() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/userinfo/userAndRuntime");
		mv.addObject("runtime", iOperationtimeframe.findAll());
		mv.addObject("user", iUserService.findAll());
		return mv;
	}
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(HttpServletRequest request,UserAndRuntimeBoundEx user) throws ParseException{
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<UserAndRuntimeBoundEx> CardPage = new Page<UserAndRuntimeBoundEx>();
		CardPage.setPageSize(rows);
		CardPage.setPageNo(page);
		
		int totalRecords = 0;
		    /*AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
			AccountEx operatorEntity = account.get(operator.getAccountcode());	*/
			
			//条件
		    UserAndRuntimeBoundEntityExample example = new UserAndRuntimeBoundEntityExample();
			Criteria criteria = example.createCriteria();
			example.setDistinct(true);
			if (!StringUtils.isEmpty(user.getUsercardid())) {
				criteria.andUsercardidEqualTo(user.getUsercardid());
			}			
			//查询总数
			totalRecords = (int) iUserAndRuntime.count(example);
			
			List<UserAndRuntimeBoundEx> CardList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				CardList = iUserAndRuntime.findAll(example);
			}
			
			CardPage.setResults(CardList);
			CardPage.setTotalRecord(totalRecords);
		
		GridPage<UserAndRuntimeBoundEx> gridPage = new GridPage<UserAndRuntimeBoundEx>(CardPage);
		return JsonUtils.toJSONString(gridPage);
	}
	/**
	 * 员工绑定工作时段
	 * @param entity
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/insert")
	public void insert(UserAndRuntimeBoundEntity entity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			iUserAndRuntime.insert(entity);
			jo.put("msg", "绑定成功");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "绑定失败");
		}
		response.getWriter().print(jo);
	}
	
	/**
	 * 员工修改绑定工作时段
	 * @param entity
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/update")
	public void update(UserAndRuntimeBoundEntity entity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			iUserAndRuntime.update(entity);
			jo.put("msg", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "修改失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 删除员工绑定时间
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/delete")
	public void delete(int id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			iUserAndRuntime.delete(id);
			jo.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败");
		}
		response.getWriter().print(jo);
	}
}
