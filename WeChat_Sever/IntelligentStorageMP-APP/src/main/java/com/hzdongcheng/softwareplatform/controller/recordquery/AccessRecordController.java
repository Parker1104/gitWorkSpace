package com.hzdongcheng.softwareplatform.controller.recordquery;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IMidwayTakeRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IStoreTakeRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ITakeOutRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.MidwayTakeRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntityExample;

@Controller
@RequestMapping("/accessRecord")
public class AccessRecordController extends BaseController {
	@Autowired
	IStoreTakeRecord iRecord;
	@Autowired
	IMidwayTakeRecord mRecord;
 
 
    @Autowired
    TakeOutRecordEntityDao takeOutRecordEntityDao;
    
    
    
	/**
	 * 存取记录页面
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/accessRecordQuery");
		return mv;
	}
	@RequestMapping(value = "/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(StoreInRecordEntity store,HttpServletRequest request,HttpServletResponse response) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<StoreInRecordEx> ManagerExPage = new Page<StoreInRecordEx>();
		ManagerExPage.setPageSize(rows);
		ManagerExPage.setPageNo(page);

		int totalRecords = 0;
		/*AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
			AccountEx operatorEntity = account.get(operator.getAccountcode());*/	

		//条件
		StoreInRecordEntityExample example = new StoreInRecordEntityExample();
		Criteria criteria = example.createCriteria();
		example.setDistinct(true);
		example.setOrderByClause("state asc, storeintime desc");
		//criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");
		if(!StringUtils.isEmpty(store.getUsercardid())) {
			criteria.andUsercardidEqualTo(store.getUsercardid());
		}	
		if (!StringUtils.isEmpty(store.getTerminalid())) {
			criteria.andTerminalidEqualTo(store.getTerminalid());
		}
		if (!StringUtils.isEmpty(store.getBoxid())) {
			criteria.andBoxidEqualTo(store.getBoxid());
		}
		if (!StringUtils.isEmpty(store.getState())) {
			criteria.andStateEqualTo(store.getState());
		}
		if (!StringUtils.isEmpty(store.getUsername())) {
			criteria.andUsernameEqualTo(store.getUsername());
		}
		//查询总数
		totalRecords = (int) iRecord.count(example);

		List<StoreInRecordEx> TerminalList = null;
		if (totalRecords > 0)
		{
			//分页查询
			PageHelper.startPage(page, rows);
			TerminalList = iRecord.findAll(example);
		}

		ManagerExPage.setResults(TerminalList);
		ManagerExPage.setTotalRecord(totalRecords);

		GridPage<StoreInRecordEx> gridPage = new GridPage<StoreInRecordEx>(ManagerExPage);
		return JsonUtils.toJSONString(gridPage);
	}
	@RequestMapping(value = "/list2" ,produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list2(StoreInRecordEx store,HttpServletRequest request) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<StoreInRecordEx> Page = new Page<StoreInRecordEx>();
		Page.setQueryParam(store);
		Page.setPageNo(page);
		Page.setPageSize(rows);
		Page=iRecord.selectList(Page);
		GridPage<StoreInRecordEx> gridPage = new GridPage<StoreInRecordEx>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
	/**
	 * 取物记录
	 * @param record
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/takeoutList")
	@ResponseBody
	public String takeoutList(TakeOutRecordEntity record,HttpServletRequest request,HttpServletResponse response) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<TakeOutRecordEntity> MidwayPage = new Page<TakeOutRecordEntity>();
		MidwayPage.setPageSize(rows);
		MidwayPage.setPageNo(page);

		int totalRecords = 0;
		//条件
		TakeOutRecordEntityExample example = new TakeOutRecordEntityExample();
		//com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntityExample.Criteria criteria = example.createCriteria();
		TakeOutRecordEntityExample.Criteria criteria = example.createCriteria();
 
		if (!StringUtils.isEmpty(record.getTerminalid())) {
			criteria.andTerminalidEqualTo(record.getTerminalid());
			//System.out.println(" 1 getTerminalid= "+record.getTerminalid());
		}
		if (!StringUtils.isEmpty(record.getBoxid())) {
			criteria.andBoxidEqualTo(record.getBoxid());
			//System.out.println(" 2 getBoxid= "+record.getBoxid());
		}
		if (!StringUtils.isEmpty(record.getUsercardid())) {
			criteria.andUsercardidEqualTo(record.getUsercardid());
			//System.out.println(" 3 getUsercardid= "+record.getUsercardid());
		}
		if (!StringUtils.isEmpty(record.getMemo())) {
			String date = record.getMemo();	
			//System.out.println(" 4 date= "+date);
			Date time = DateUtils.stringToDateTime(date);
			criteria.andStoreintimeGreaterThanOrEqualTo(time);
 
		}
		/*if (!StringUtils.isEmpty(record.getCashierno())) {
			String date = record.getCashierno();
			Date time = DateUtils.stringToDateTime(date);
			criteria.andTaketimeLessThan(time);
			System.out.println(" 5 Storeintime= "+time);
		}*/
		//查询总数
		totalRecords = (int) takeOutRecordEntityDao.countByExample(example);
		List<TakeOutRecordEntity> TerminalList = null;
		if (totalRecords > 0)
		{
			//分页查询
			PageHelper.startPage(page, rows);
			TerminalList = takeOutRecordEntityDao.selectByExample(example);
		}
		MidwayPage.setResults(TerminalList);
		MidwayPage.setTotalRecord(totalRecords);

		GridPage<TakeOutRecordEntity> gridPage = new GridPage<TakeOutRecordEntity>(MidwayPage);
		return JsonUtils.toJSONString(gridPage);
	}
	
	/**
	 * 中途取物记录
	 * @param record
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/midwayList")
	@ResponseBody
	public String midwayList(MidwayTakeRecordEntity record,HttpServletRequest request,HttpServletResponse response) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<MidwayTakeRecordEntity> MidwayPage = new Page<MidwayTakeRecordEntity>();
		MidwayPage.setPageSize(rows);
		MidwayPage.setPageNo(page);

		int totalRecords = 0;
		/*AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
			AccountEx operatorEntity = account.get(operator.getAccountcode());*/	

		//条件
		MidwayTakeRecordEntityExample example = new MidwayTakeRecordEntityExample();
		com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.MidwayTakeRecordEntityExample.Criteria criteria = example.createCriteria();
		//example.setDistinct(true);
		//criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");					
		if (!StringUtils.isEmpty(record.getTerminalid())) {
			criteria.andTerminalidEqualTo(record.getTerminalid());
		}
		if (!StringUtils.isEmpty(record.getBoxid())) {
			criteria.andBoxidEqualTo(record.getBoxid());
		}
		if (!StringUtils.isEmpty(record.getUsercardid())) {
			criteria.andUsercardidEqualTo(record.getUsercardid());
		}
		if (!StringUtils.isEmpty(record.getMemo())) {
			String date = record.getMemo();				
			Date time = DateUtils.stringToDateTime(date);
			criteria.andStoreintimeGreaterThanOrEqualTo(time);
		}
		if (!StringUtils.isEmpty(record.getCashierno())) {
			String date = record.getCashierno();
			Date time = DateUtils.stringToDateTime(date);
			criteria.andTaketimeLessThan(time);
		}
		//查询总数
		totalRecords = (int) mRecord.count(example);

		List<MidwayTakeRecordEntity> TerminalList = null;
		if (totalRecords > 0)
		{
			//分页查询
			PageHelper.startPage(page, rows);
			TerminalList = mRecord.findAll(example);
		}

		MidwayPage.setResults(TerminalList);
		MidwayPage.setTotalRecord(totalRecords);

		GridPage<MidwayTakeRecordEntity> gridPage = new GridPage<MidwayTakeRecordEntity>(MidwayPage);
		return JsonUtils.toJSONString(gridPage);
	}
}
