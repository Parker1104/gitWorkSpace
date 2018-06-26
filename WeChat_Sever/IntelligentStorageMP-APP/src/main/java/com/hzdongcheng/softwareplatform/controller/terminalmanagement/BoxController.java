package com.hzdongcheng.softwareplatform.controller.terminalmanagement;



import java.io.IOException;
import java.util.Date;
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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxType;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BoxEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;

@Controller
@RequestMapping(value="/boxController")
public class BoxController extends BaseController{
	
	@Autowired
	IBoxService boxService;
	@Autowired
	TerminalService tService;
	@Autowired
	IBoxType iBoxType;
	@Autowired
	IAccountManagement account;
	@Autowired
	IOperationLogInpquire iOperation;
	/**
	 * 箱属性页面
	 * @return
	 */
	@RequestMapping("/boxAttributeSet")
	public ModelAndView view(TerminalEntity tEntity) {
		ModelAndView mv = new ModelAndView();
		TerminalEntityExample example = new TerminalEntityExample();
		mv.setViewName("/web/equipmentinfo/boxAttributeSet");
		mv.addObject("terminal",tService.findAll(example));
		mv.addObject("box",iBoxType.findAll());
		return mv;
	}
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(BoxEntity box,HttpServletRequest request,HttpServletResponse response){
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<BoxEx> ManagerExPage = new Page<BoxEx>();
		ManagerExPage.setPageSize(rows);
		ManagerExPage.setPageNo(page);
		
		int totalRecords = 0;		
			//条件
			BoxEntityExample example = new BoxEntityExample();			
			Criteria criteria = example.createCriteria();
			example.setDistinct(true);
			example.setOrderByClause("dispalyname asc");
			//criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");			
			/*if (!StringUtils.isEmpty(box.getDispalyname())) {
				criteria2.andDisplaynameEqualTo(box.getDispalyname());
			}	*/		
			if (!StringUtils.isEmpty(box.getBoxid())) {
				criteria.andBoxidEqualTo(box.getBoxid());
			}
			if (!StringUtils.isEmpty(box.getBoxtypecode())) {
				criteria.andBoxtypecodeEqualTo(box.getBoxtypecode());
			}
			if (!StringUtils.isEmpty(box.getStatus())) {
				criteria.andStatusEqualTo(box.getStatus());
			}
			if (!StringUtils.isEmpty(box.getArticle())) {
				criteria.andArticleEqualTo(box.getArticle());
			}
			if (!StringUtils.isEmpty(box.getDisplayname())) {
				TerminalEntity entity = tService.selectByDisplayname(box.getDisplayname());
				if (entity != null) {
					criteria.andTerminalidEqualTo(entity.getTerminalid());
				}				
			}
			//查询总数
			totalRecords = (int) boxService.count(example);
			
			List<BoxEx> TerminalList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				TerminalList = boxService.findAll(example);
			}
			
			ManagerExPage.setResults(TerminalList);
			ManagerExPage.setTotalRecord(totalRecords);
		
		GridPage<BoxEx> gridPage = new GridPage<BoxEx>(ManagerExPage);
		return JsonUtils.toJSONString(gridPage);
	}
	/**
	 * 箱属性新增修改
	 * @param boxEntity
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public void addOrUpdate(BoxEntity boxEntity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		int boxid = boxEntity.getBoxid();
		String terminalid = boxEntity.getTerminalid();
		BoxEx box = boxService.select(terminalid,boxid);
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			if (box == null) {
				boxEntity.setArticle((byte)0);
				boxEntity.setOpen((byte)0);
				boxEntity.setStatus((byte)0);
				boxService.insert(boxEntity);
				jo.put("msg", "添加成功");
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("新增箱信息");
				operator.setModlename("设备管理");
				operator.setMemo("新增箱信息");
				iOperation.saveOrUpdate(operator);
			}else{
				boxService.update(boxEntity);
				jo.put("msg", "修改成功");
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("修改箱信息");
				operator.setModlename("设备管理");
				operator.setMemo("修改箱信息");
				iOperation.saveOrUpdate(operator);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 箱属性删除
	 * @param terminalid
	 * @param boxid
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/delete")
	public void delete(String terminalid,Integer boxid,HttpServletRequest request,HttpServletResponse response ) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			boxService.delete(terminalid,boxid);
			jo.put("msg", "删除成功");
			OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("删除箱信息");
			operator.setModlename("设备管理");
			operator.setMemo("删除箱信息");
			iOperation.saveOrUpdate(operator);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg","删除失败");
		}
		response.getWriter().print(jo);
	}
}
