package com.hzdongcheng.softwareplatform.controller.log;

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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ISystemMessage;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.SystemInfoEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.SystemInfoEntityExample;
/**
 * 
 * @author wenheju
 * 系统信息
 */
@Controller
@RequestMapping("/systemMessageController")
public class SystemMessageController  extends BaseController{
	@Autowired
	private ISystemMessage iSystemMessage;
	/**
	 * @return
	 * 跳转页面
	 */
	@RequestMapping("/systemMessage")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/log/systemMessage");
		return mv;
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询所以操作日志
	 */
	@RequestMapping(value = "/selectSystemMessage.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectSystemMessage(SystemInfoEntity sys,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<SystemInfoEntity> SystemInfoPage = new Page<SystemInfoEntity>();
		SystemInfoPage.setPageSize(rows);
		SystemInfoPage.setPageNo(page);
		int totalRecords = 0;
		{
			
			//条件
			SystemInfoEntityExample example = new SystemInfoEntityExample();
			example.setDistinct(true);
			
			//查询总数
			totalRecords = (int) iSystemMessage.count(example);
			
			List<SystemInfoEntity> systemInfoList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				systemInfoList = iSystemMessage.findByExampleEntity(example);
			}
			
			SystemInfoPage.setResults(systemInfoList);
			SystemInfoPage.setTotalRecord(totalRecords);
		}
		
		GridPage<SystemInfoEntity> gridPage = new GridPage<SystemInfoEntity>(SystemInfoPage);
		return JsonUtils.toJSONString(gridPage);	
	}
	
	/**
	 * @param ids
	 * @throws Exception
	 * 删除信息
	 */
	@RequestMapping(value = "/delectSystemMessage.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void delectSystemMessage(String ids) throws Exception {
		System.out.println(ids);
		if(!StringUtils.isEmpty(ids)){
			for(String id :ids.split(",")){
				if(StringUtils.isEmpty(id)) continue;
				System.out.println(id);
				iSystemMessage.delete(id);
			
			}
		}
	}
}
