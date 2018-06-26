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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICallThePoliceLog;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalAlarmDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalAlarmDiaryEntityExample;

/**
 * 
 * @author wenheju
 *   系统报警日志
 */
@Controller
@RequestMapping("/callThePoliceLogController")
public class CallThePoliceLogController extends BaseController {
	
	@Autowired
	ICallThePoliceLog iCallThePoliceLog;
	
	@RequestMapping("/callThePoliceLog")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/log/callThePoliceLog");
		return mv;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询所以操作日志
	 */
	@RequestMapping(value = "/selectTerminalAlarmDiary.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectTerminalAlarmDiary(TerminalAlarmDiaryEntity ter,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<TerminalAlarmDiaryEntity> TerminalPage = new Page<TerminalAlarmDiaryEntity>();
		TerminalPage.setPageSize(rows);
		TerminalPage.setPageNo(page);
		
		int totalRecords = 0;
		{	
			//条件
			TerminalAlarmDiaryEntityExample example = new TerminalAlarmDiaryEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(ter.getAlarmcode())){
				example.createCriteria().andAlarmcodeLike("%"+ter.getAlarmcode()+"%");
			}		
			//查询总数
			totalRecords = (int) iCallThePoliceLog.count(example);
			
			List<TerminalAlarmDiaryEntity> terminalList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				terminalList = iCallThePoliceLog.findByExampleEntity(example);
			}
			
			TerminalPage.setResults(terminalList);
			TerminalPage.setTotalRecord(totalRecords);
		}
		
		GridPage<TerminalAlarmDiaryEntity> gridPage = new GridPage<TerminalAlarmDiaryEntity>(TerminalPage);
		return JsonUtils.toJSONString(gridPage);	
	}	

	/**
	 * @param ids
	 * @throws Exception
	 * 删除信息
	 */
	@RequestMapping(value = "/delectTerminalAlarmDiary.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void delectTerminalAlarmDiary(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		System.out.println(id);	
		try {
			iCallThePoliceLog.delete(id);
			jo.put("msg", "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败！");
		}
	    response.getWriter().print(jo); 	
	}
}
