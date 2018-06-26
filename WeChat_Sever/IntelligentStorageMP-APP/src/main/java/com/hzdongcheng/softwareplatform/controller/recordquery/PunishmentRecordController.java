package com.hzdongcheng.softwareplatform.controller.recordquery;

import java.util.Date;
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
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPunishRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PunishRecordEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.PunishRecordExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PunishRecordEntityExample.Criteria;

@Controller
@RequestMapping("/punishmentRecord")
public class PunishmentRecordController extends BaseController{

	@Autowired
	IPunishRecord iPunishRecord;

	@Autowired
	PunishRecordExDao punishRecordExDao;

	@RequestMapping("/punishmentRecord")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/punishmentRecord");
		return mv;
	}

	@RequestMapping(value = "/selectpunishmentRecord.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectpunishmentRecord(PunishRecordEntity pun, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<PunishRecordEx> PunishRecord = new Page<PunishRecordEx>();
		PunishRecord.setPageSize(rows);
		PunishRecord.setPageNo(page);
		int totalRecords = 0;
		{
			//条件
			PunishRecordEntityExample example = new PunishRecordEntityExample();
			Criteria criteria = example.createCriteria();
			example.setDistinct(true);
			example.setOrderByClause("punishstate desc,storeInTime desc");
			if(!StringUtils.isEmpty(pun.getUsercardid())) {
				criteria.andUsercardidEqualTo(pun.getUsercardid());
			}
			if(!StringUtils.isEmpty(pun.getBoxid())) {
				criteria.andBoxidEqualTo(pun.getBoxid());
			}
			if(!StringUtils.isEmpty(pun.getPunishstate())){	
				if(pun.getPunishstate() == 1){
					criteria.andPunishstateNotEqualTo((byte)0);	
				}else {
					criteria.andPunishstateEqualTo(pun.getPunishstate());
				}

			}		
			//查询总数
			totalRecords = (int) iPunishRecord.count(example);

			List<PunishRecordEx>  punishRecord = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				punishRecord = iPunishRecord.findByExampleEx(example);
			}

			PunishRecord.setResults(punishRecord);
			PunishRecord.setTotalRecord(totalRecords);
		}

		GridPage<PunishRecordEx> gridPage = new GridPage<PunishRecordEx>(PunishRecord);
		return JsonUtils.toJSONString(gridPage);	


	}
	@RequestMapping(value = "/selectpunishmentRecordpage.do" ,produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectpunishmentRecordpage(PunishRecordEx pun,HttpServletRequest request) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<PunishRecordEx> Page = new Page<PunishRecordEx>();
		Page.setQueryParam(pun);
		Page.setPageNo(page);
		Page.setPageSize(rows);
		Page=iPunishRecord.selectList(Page);
		GridPage<PunishRecordEx> gridPage = new GridPage<PunishRecordEx>(Page);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
	/**
	 * @param pun
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 管理员解禁
	 */
	@RequestMapping(value = "/saveOrUpdatePunishmentRecord.do", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String saveOrUpdatePunishmentRecord(String terminalid,String boxid,String usercardid,Long storeintime,Long enddate, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult jresult = new JsonResult();
		try { 
			/*PunishRecordEntity punishRecord = iPunishRecord.get(terminalid, Integer.parseInt(boxid), usercardid, new Date(storeintime), new Date(enddate));
			if(punishRecord.getPunishstate() == 1){
				iPunishRecord.dischargePunishStatus(terminalid, Integer.parseInt(boxid), usercardid, new Date(storeintime));
			}*/
			PunishRecordEntity punishRecordEntity = new PunishRecordEntity();
			punishRecordEntity.setTerminalid(terminalid);
			punishRecordEntity.setBoxid(Integer.parseInt(boxid));
			punishRecordEntity.setUsercardid(usercardid);
			punishRecordEntity.setStoreintime(new Date(storeintime));
			punishRecordEntity.setEnddate(new Date(enddate));
			punishRecordEntity.setPunishstate((byte)0);
			punishRecordExDao.updateByPrimaryKeySelective(punishRecordEntity);

			jresult.success = true;
			jresult.msg =getMessage(request, "tip.success");
		} catch (Exception e) {
			jresult.msg = "解禁失败";
			e.getMessage();
		}
		return JsonUtils.toJSONString(jresult);
	}
}
