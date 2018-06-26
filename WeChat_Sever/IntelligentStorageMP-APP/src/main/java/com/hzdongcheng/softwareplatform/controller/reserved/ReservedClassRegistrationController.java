package com.hzdongcheng.softwareplatform.controller.reserved;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IGradeService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.GradeEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.GradeEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;

/**
 * @author WenHeJu
 * @Description: TODO(根据班级绑定箱子) 
 * @ClassName:  ReservedClassRegistrationController
 * @date 2017年8月2日 下午3:33:39
 */
@Controller
@RequestMapping("/reservedClassRegistration")
public class ReservedClassRegistrationController extends BaseController{
	
	//日志 
	public static Log4jUtils logger = Log4jUtils.createInstanse(ReservedClassRegistrationController.class);
	@Autowired
	private IGradeService iGradeService;
	@Autowired
	private TerminalExDao Terminal;
	@Autowired
	private BoxExDao boxExDao;
	
	@RequestMapping("view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		TerminalEntityExample example = new TerminalEntityExample();
		mv.addObject("TerminalEntity", Terminal.selectByExample(example));
		mv.setViewName("/web/reserved/reservedClassRegistration");
		return mv;
	}
	/**
	 * @param cradeEntity
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/selectClassRegistration",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectClassRegistration(GradeEntity cradeEntity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<GradeEntity> paymentPage = new Page<GradeEntity>();
		paymentPage.setPageSize(rows);
		paymentPage.setPageNo(page);
		{
			GradeEntityExample example = new GradeEntityExample();
			Criteria criteria = example.createCriteria();
			example.setOrderByClause("subdepartment ASC");
			if(!StringUtils.isEmpty(cradeEntity.getSubdepartment())){
				criteria.andSubdepartmentEqualTo(cradeEntity.getSubdepartment());
			}
			if(!StringUtils.isEmpty(cradeEntity.getState())){
				criteria.andStateEqualTo(cradeEntity.getState());
			}
			List<GradeEntity> gradeEntity = iGradeService.select(example);
			
			paymentPage.setResults(gradeEntity);
			paymentPage.setTotalRecord(gradeEntity.size());
		}

		GridPage<GradeEntity> gridPage = new GridPage<GradeEntity>(paymentPage);
		return JsonUtils.toJSONString(gridPage);

	}
	/**
	 * @param cradeEntity
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * 添加修改
	 */
	@RequestMapping(value="/addClassRegistration",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String addClassRegistration(GradeEx gradeEx,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonResult jresult=new JsonResult();
		try {
			//根据柜名称查询终端信息
			TerminalEntity terminalEntity =	Terminal.selectByDisplayname(gradeEx.getDisplayname());	
			
			//日期转换 String --> Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    Date date = sdf.parse(gradeEx.getEnddate());
		    
		    //bean 的数据   copy   gradeEx --> gradeEntity
		    GradeEntity gradeEntity = new GradeEntity();
		    BeanUtils.copyProperties(gradeEx, gradeEntity);
		    gradeEntity.setTerminalid(terminalEntity.getTerminalid());
		    gradeEntity.setEnddate(date);
		    
			GradeEntityExample example = new GradeEntityExample();
			example.createCriteria().andSubdepartmentEqualTo(gradeEx.getSubdepartment());
			List<GradeEntity> gradeList =  iGradeService.select(example);
		    
		    if(gradeList.size()>0){
		    	iGradeService.update(gradeEntity);
		    }else {
		    	iGradeService.insert(gradeEntity);
			}
			
			jresult.msg="操作成功";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			jresult.msg="操作失败";
		}
		
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	@RequestMapping(value="/delectClassRegistration",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String delectClassRegistration(GradeEntity gradeEntity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonResult jresult=new JsonResult();
		try {
			iGradeService.delete(gradeEntity.getTerminalid(), gradeEntity.getSubdepartment());
			jresult.msg="删除成功";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			jresult.msg="删除失败";
		}
		
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	@RequestMapping(value="/selectBox",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectBox(GradeEntity gradeEntity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<BoxEntity> boxEntity = null;
		try {
			TerminalEntity  terminalEntity =Terminal.selectByDisplayname(gradeEntity.getDisplayname());
			GradeEntityExample example = new GradeEntityExample();
			example.createCriteria().andTerminalidEqualTo(terminalEntity.getTerminalid());
			List<GradeEntity> gradelist = iGradeService.select(example);
			BoxEntityExample Example = new BoxEntityExample();
			BoxEntityExample.Criteria criteria = Example.createCriteria();
			criteria.andTerminalidEqualTo(terminalEntity.getTerminalid());
			for(int i = 0 ;i<gradelist.size();i++){
				criteria.andBoxidNotEqualTo(gradelist.get(i).getBoxid());	
			}	
			boxEntity =  boxExDao.selectByExample(Example);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		
		return JsonUtils.toJSONNoFeatures(boxEntity);
	}

	@RequestMapping(value="/selectBoxName",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectBoxName(GradeEntity gradeEntity,HttpServletRequest request,HttpServletResponse response) throws IOException {
		BoxEntity boxEntity = null;
		try {
			boxEntity =  boxExDao.selectByPrimaryKey(gradeEntity.getTerminalid(), gradeEntity.getBoxid());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		
		return JsonUtils.toJSONNoFeatures(boxEntity);
	}

}
