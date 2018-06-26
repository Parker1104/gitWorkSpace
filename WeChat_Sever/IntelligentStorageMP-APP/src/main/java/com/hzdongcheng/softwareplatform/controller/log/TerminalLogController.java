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
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ITerminalLog;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;

/**
 * 
 * @author wenheju
 * 终端操作日志
 */
@Controller
@RequestMapping("/terminalLogController")
public class TerminalLogController  extends BaseController {
	@Autowired
	IBoxService iBoxService;
	
	@Autowired
	ITerminalLog  iTerminalLog;
	
	/**
	 * @return
	 * 跳转页面
	 */
	@RequestMapping("/TerminalLog")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/log/TerminalLog");
		return mv;
	}
	
	@RequestMapping(value = "/selectBox.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectBox(BoxEntity box,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<BoxEntity> OperatorPage = new Page<BoxEntity>();
		OperatorPage.setPageSize(rows);
		OperatorPage.setPageNo(page);
		
		int totalRecords = 0;
		{
			
			//条件
			BoxEntityExample example = new BoxEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(box.getOpen())){
				example.createCriteria().andOpenEqualTo(box.getOpen());
			}		
			//查询总数
			totalRecords = (int) iTerminalLog.count(example);
			
			List<BoxEntity> boxList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				boxList = iTerminalLog.findAll(example);
			}
			
			OperatorPage.setResults(boxList);
			OperatorPage.setTotalRecord(totalRecords);
		}
		
		GridPage<BoxEntity> gridPage = new GridPage<BoxEntity>(OperatorPage);
		return JsonUtils.toJSONString(gridPage);
	}
}
