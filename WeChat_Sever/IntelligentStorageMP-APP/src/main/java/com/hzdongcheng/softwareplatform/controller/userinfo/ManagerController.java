package com.hzdongcheng.softwareplatform.controller.userinfo;

import java.io.IOException;
import java.util.Calendar;
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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDatadic;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IManager;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.ManagerEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PunishRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.ManagerEntityExample.Criteria;

@Controller
@RequestMapping(value="/managerController")
public class ManagerController extends BaseController {
	 @Autowired
	 IManager iManager;
	 @Autowired
	 private IAreaService area;
	 @Autowired
	 IAccountManagement account;
	 @Autowired
	 IDatadic dic;
	 @Autowired
	 IOperationLogInpquire iOperation;
	 
	 /**
	  * 管理员管理页面
	  * @return
	  */
	 @RequestMapping("/adminManage")
	 public ModelAndView view() {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("/web/userinfo/adminManage");
			mv.addObject("area", area.findAll());
			mv.addObject("manager", dic.findAll(Constant.DICT_MAMAGER));
			return mv;
		}
	 @RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	 @ResponseBody
	 public String list(ManagerEntity acc,HttpServletRequest request,HttpServletResponse response) {
		 int page = getParamInt(request, "page", 1);
			int rows = getParamInt(request, "rows", 10);
			
			Page<ManagerEx> ManagerExPage = new Page<ManagerEx>();
			ManagerExPage.setPageSize(rows);
			ManagerExPage.setPageNo(page);
			
			int totalRecords = 0;
			    AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
				AccountEx operatorEntity = account.get(operator.getAccountcode());	
				
				//条件
				ManagerEntityExample example = new ManagerEntityExample();
				Criteria criteria = example.createCriteria();
				example.setDistinct(true);
				criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");			
				if (!StringUtils.isEmpty(acc.getManagercardid())) {
					criteria.andManagercardidEqualTo(acc.getManagercardid());
				}		
				if (!StringUtils.isEmpty(acc.getManagername())) {
					criteria.andManagernameEqualTo(acc.getManagername());
				}
				if (!StringUtils.isEmpty(acc.getManagertype())) {
					criteria.andManagertypeEqualTo(acc.getManagertype());
				}
				if (!StringUtils.isEmpty(acc.getState())) {
					criteria.andStateEqualTo(acc.getState());
				}
				
				System.out.println(acc.getManagername());
				
				//查询总数
				totalRecords = (int) iManager.count(example);
				
				List<ManagerEx> ManagerList = null;
				if (totalRecords > 0)
				{
					//分页查询
					PageHelper.startPage(page, rows);
					ManagerList = iManager.findByExampleEx(example);
					if(ManagerList != null){
						for(ManagerEx rule: ManagerList){
							rule.setDictEntity(dic.get(Constant.DICT_MAMAGER, rule.getManagertype()));
						}
					}
				}
				
				ManagerExPage.setResults(ManagerList);
				ManagerExPage.setTotalRecord(totalRecords);
			
			GridPage<ManagerEx> gridPage = new GridPage<ManagerEx>(ManagerExPage);
			return JsonUtils.toJSONString(gridPage);
	}
	 /**
	  * 管理员新增和修改
	  * @param managerEntity
	  * @param request
	  * @param response
	  * @throws IOException
	  */
	@RequestMapping("/insert")
	public void addorUpdate(ManagerEntity managerEntity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		Date date  = new Date();
		date.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR,1);
		AccountEntity account = (AccountEntity)request.getSession().getAttribute("account");
		String name = account.getAccountname();
		String id = managerEntity.getManagercardid();
		String areacode = managerEntity.getAreacode();
		ManagerEntity me = iManager.select(areacode,id);
		try {
			if (me == null) {
				managerEntity.setMakeopcode(name);
				managerEntity.setEnddate(cal.getTime());
				managerEntity.setMakedate(new Date());
				managerEntity.setLastmodifytime(new Date());
				iManager.insert(managerEntity);
				jo.put("msg", "添加成功");
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(account.getAccountcode());
				operator.setDate(date);
				operator.setDescription("新增终端管理员");
				operator.setModlename("终端管理员管理");
				operator.setMemo("新增终端管理员");
				iOperation.saveOrUpdate(operator);
			}else {
				managerEntity.setLastmodifytime(new Date());
				iManager.update(managerEntity);
				jo.put("msg", "修改成功");
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(account.getAccountcode());
				operator.setDate(date);
				operator.setDescription("修改终端管理员");
				operator.setModlename("终端管理员管理");
				operator.setMemo("修改终端管理员");
				iOperation.saveOrUpdate(operator);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}		
		response.getWriter().print(jo);
	}
	/**
	 * 区域选择列表
	 * @return
	 */
	@RequestMapping(value="/areaList",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String areaList() {
		List<AreaEntity> areaEntity = area.findAll();
		String json = JsonUtils.toJSONNoFeatures(areaEntity);
		return json;
	}
	/**
	 * 管理员删除
	 * @param managercardid
	 * @param areacode
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/delete")
	public void delete(String managercardid,String areacode,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			iManager.delete(managercardid,areacode);
			jo.put("msg", "删除成功");
			OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("删除终端管理员");
			operator.setModlename("终端管理员管理");
			operator.setMemo("删除终端管理员");
			iOperation.saveOrUpdate(operator);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 去重查询
	 * @param areacode
	 * @param managercardid
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/select")
	public void select(String areacode,String managercardid,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			ManagerEntity entity = iManager.select(areacode, managercardid);
			if (entity==null) {
				jo.put("msg","卡号可以使用");
			}else {
				jo.put("msg","卡号已经存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg","查询失败");
		}
		response.getWriter().print(jo);
	}
}
