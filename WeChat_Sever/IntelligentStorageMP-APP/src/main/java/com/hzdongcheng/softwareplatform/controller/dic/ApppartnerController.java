package com.hzdongcheng.softwareplatform.controller.dic;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAppartners;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AreaExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AreaEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;

/**
 *   第三方移动    接入接口 
 */

@Controller
@RequestMapping("/apppartnerController")
public class ApppartnerController extends BaseController{
	public static Log4jUtils logger = Log4jUtils.createInstanse(ApppartnerController.class);
	
	@Autowired
	IAppartners iAppartners;
	@Autowired
	private AreaEntityDao areaEntityDao;
	
	@RequestMapping("/viewes")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/dic/appartners");
		return mv;
	}
	
	@RequestMapping(value = "/selects.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody	
	//查询信息
	public String selects(AppPartnerEntity entityes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<AppPartnerEntity> dictPage = new Page<AppPartnerEntity>();
		dictPage.setPageSize(rows);
		dictPage.setPageNo(page);
		
		//条件
		AppPartnerEntityExample example = new AppPartnerEntityExample();
		example.setDistinct(true);
		if(!StringUtils.isEmpty(entityes.getUserName())){
			example.createCriteria().andUserNameLike("%"+entityes.getUserName()+"%");
		}		
		int	totalRecords = (int) iAppartners.count(example);//查询总数
		
		List<AppPartnerEntity> dictList = null;
		if (totalRecords > 0) {
			PageHelper.startPage(page, rows);//分页查询
			dictList = iAppartners.findByExampleEntity(example);
		}
		dictPage.setResults(dictList);
		dictPage.setTotalRecord(totalRecords);
 
		return JsonUtils.toJSONString( new GridPage<AppPartnerEntity>(dictPage));					
	}
 
	// 添加 修改箱体信息
	@RequestMapping(value = "/saveOrUpdates.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String saveOrUpdates(HttpServletRequest request, HttpServletResponse response) throws Exception {		    
			  JsonResult jresult=new JsonResult();
			  try{
				    String userId = request.getParameter("userId");
				    String userName = request.getParameter("userName");
				    String userKey = request.getParameter("userKey");
				    String alarmNoticeUrls = request.getParameter("alarmNoticeUrls");
	
			    	AppPartnerEntity entityes = new AppPartnerEntity();
					if(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(userName) && !StringUtils.isEmpty(userKey)){
						entityes.setUserId(userId);
						entityes.setUserName(userName);
						entityes.setUserKey(userKey);
						entityes.setAlarmnoticeurls(alarmNoticeUrls);
						entityes.setLastModifyTime(new Date());
						iAppartners.saveOrUpdate(entityes);	
		            }
					jresult.success=true;
					jresult.msg="操作成功";
		      }catch(Exception e){
		    	   jresult.success=false;
			       jresult.msg=e.getMessage();
				   logger.error("error  ApppartnerController  saveOrUpdates  fail the reason is:  "+e );
		      }
		      return JsonUtils.toJSONNoFeatures(jresult);	   		         
	}
	
	// 删除信息
	@RequestMapping(value = "/delectes.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String delectes(AppPartnerEntity entityes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 JsonResult jresult=new JsonResult();
		 try{
			   AreaEntityExample example =new AreaEntityExample();
			   example.or().andAppkeyEqualTo(entityes.getUserId());
			   List<AreaEntity> areaEntitysList=areaEntityDao.selectByExample(example);
               if(CollectionUtils.isEmpty(areaEntitysList)){
            	   iAppartners.delete(entityes.getUserId());
    			   jresult.success=true;
    		       jresult.msg="ok";
               }else{
            	   jresult.success=false;
    		       jresult.msg="area useing (区域使用中....)";
               }
			   
		 }catch(Exception e){
	    	   jresult.success=false;
		       jresult.msg=e.getMessage();
			   logger.error("error  ApppartnerController  delectes  fail the reason is:  "+e );
	      }
		  String  results=JsonUtils.toJSONNoFeatures(jresult);
		  System.out.println("=====results===="+results);
		  return results;	   		         
	}
	
/*	*//**
	 * @param acc
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询信息
	 *//*
	@RequestMapping(value = "/selectDatadicCode.do", produces = {"text/html;charset=UTF-8;"})
	@ResponseBody	
	public String selectDatadicCode(DictEntity dic, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(dic.getDicttypecode());
		DictEntity dictEntity = iDatadic.get(dic.getDicttypecode(), dic.getDictcode());
		String json = JsonUtils.toJSONString(dictEntity);
		return json;
		
	}
	


	*/
}
