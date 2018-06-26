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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IEquipmentRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteCtrlDiaryEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntityExample.Criteria;

/**
 * 
 * @author wenheju
 * 远程控制日志
 */
@Controller
@RequestMapping("/equipmentRecordController")
public class EquipmentRecordController  extends BaseController{
	@Autowired
	private IEquipmentRecord iEquipmentRecord;
	@Autowired
	private IAccountManagement account;
	
	@RequestMapping("/EquipmentRecord")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
	/*	mv.addObject("account", account.findAll());*/
		mv.setViewName("/web/log/EquipmentRecord");
		return mv;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询操作日志
	 */
	@RequestMapping(value = "/selectEquipmentRecord.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectEquipmentRecord(RemoteCtrlDiaryEntity rem,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<RemoteCtrlDiaryEx> RemotePage = new Page<RemoteCtrlDiaryEx>();
		RemotePage.setPageSize(rows);
		RemotePage.setPageNo(page);
		
		int totalRecords = 0;
		{
			
			//条件
			/*AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");*/
			/*AccountEx operatorEntity = account.get(operator.getAccountcode());	*/
			
			//条件
			RemoteCtrlDiaryEntityExample example = new RemoteCtrlDiaryEntityExample();
			example.setDistinct(true);
			Criteria criteria = example.createCriteria();
			/*criteria.andAccountcodeEqualTo(operatorEntity.getAccountcode());*/
			if(!StringUtils.isEmpty(rem.getType())){
				criteria.andTypeEqualTo(rem.getType());	
			}	
			//查询总数
			totalRecords = (int) iEquipmentRecord.count(example);
			
			List<RemoteCtrlDiaryEx> remoteList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				remoteList = iEquipmentRecord.findByExampleEntity(example);
				for(RemoteCtrlDiaryEx  rule: remoteList){
					rule.setAccountEntity(account.get(rule.getAccountcode()));
					
				}
			}
			
			RemotePage.setResults(remoteList);
			RemotePage.setTotalRecord(totalRecords);
		}
		
		GridPage<RemoteCtrlDiaryEx> gridPage = new GridPage<RemoteCtrlDiaryEx>(RemotePage);
		return JsonUtils.toJSONString(gridPage);	
	}				
}
