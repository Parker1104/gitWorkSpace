package com.hzdongcheng.softwareplatform.controller.dic;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.vo.TreeModelVo;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.AreaExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AppPartnerEntityDao;

@Controller
@RequestMapping("/areaAction")
public class AreaAction extends BaseController{
	
	@Autowired
	private IAreaService areadao;
	@Autowired
	private AreaExDao areaExDao;
	@Autowired
	AppPartnerEntityDao appPartnerEntityDao;

	
	/**
			@RequestMapping(value = "/")
			public String index(){
				return "./web/dic/area";
			}
	*/
	
	
	@RequestMapping(value = "/")
	public ModelAndView index(){
 
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/dic/area");
		
		AppPartnerEntityExample example=new AppPartnerEntityExample();
		List<AppPartnerEntity>  appPartnerEntitylists=appPartnerEntityDao.selectByExample(example);
		
		mv.addObject("appkeylist",appPartnerEntitylists);
		
		//return "./web/dic/area";
		return mv;
				
	}
 
	@RequestMapping(value = "/saveAreaName.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String saveAreaName(TreeModelVo vo,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jresult=new JsonResult();
		String areashortname=getParamString(request, "areashortname", "");
		String appkey=getParamString(request, "appkey", "");
		
		if(vo==null || StringUtils.isEmpty(vo.getName())){
			jresult.msg=getMessage(request, "param.iserror");
		}else{
			try{
				AreaEntity rol = new AreaEntity();
				rol.setAreacode(vo.getId());
				rol.setAreaname(vo.getName());
				rol.setAreashortname(areashortname);
				if(StringUtils.isEmpty(rol.getAreacode())){
					//生成当前编码
					int currmax=0;
					String pId=vo.getpId();
					String currmaxpId=areaExDao.findMaxChild(pId);
					if(StringUtils.isNotEmpty(currmaxpId)){
						currmax=Integer.parseInt(currmaxpId.substring(pId.length()));
						currmax++;
					}
					rol.setAreacode(pId+StringUtils.leftPad(currmax+"", 2,'0'));
				}
				if(!StringUtils.isEmpty(appkey)){
					rol.setAppkey(appkey);
				}
 
				areadao.saveOrUpdate(rol);
				
				AreaEntity ae=areadao.get(rol.getAreacode());
				vo=new TreeModelVo();
				vo.setId(ae.getAreacode());
				if(StringUtils.isEmpty(ae.getAreashortname())){
					vo.setName(ae.getAreaname());
				}else{
					vo.setName(String.format("%s(%s)",ae.getAreaname(),ae.getAreashortname()));
				}
				vo.setOpen(true);
				vo.setpId(ae.getAreacode().substring(0, ae.getAreacode().length()-2));
				
				jresult.data=JsonUtils.toJSONNoFeatures(vo);
				jresult.success=true;
				jresult.msg=getMessage(request, "tip.success");
			}catch(Exception e){
				jresult.msg=e.getMessage();
			}
		}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	
	@RequestMapping(value = "/removeNode.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String removeNode(String id,HttpServletRequest request) throws Exception {
		JsonResult jresult=new JsonResult();	
			try{
				AreaEntityExample example = new AreaEntityExample();
				example.setDistinct(true);
				example.createCriteria().andAreacodeLike(id +"%");
				List<AreaEntity> areaEntity = areadao.findAll(example);
				for(int i = 0; i < areaEntity.size(); i++){
					/* System.out.println(areaEntity.get(i).getAreacode());*/
					 areadao.delete(areaEntity.get(i).getAreacode());
				}
							
				jresult.success=true;
				jresult.msg= getMessage(request, "tip.success");
			}catch(Exception e){
				jresult.msg= e.getMessage(); 
			}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	
	@RequestMapping(value = "/findAll.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		
		List<TreeModelVo> lst=new ArrayList<TreeModelVo>();
		//判断用户所在区域
		List<AreaEntity> areaList = areadao.findAll();
		for(AreaEntity ae:areaList){
			TreeModelVo vo=new TreeModelVo();
			vo.setId(ae.getAreacode());
			if(StringUtils.isEmpty(ae.getAreashortname())){
				vo.setName(requestContext.getMessage(ae.getAreaname()));
			}else{
				  //vo.setName(String.format("%s(%s)",ae.getAreaname(),ae.getAreashortname()));
				   String gsName="";
				   if(StringUtils.isNotEmpty(ae.getAppkey())){
		        	   AppPartnerEntity  appPartnerEntitys =appPartnerEntityDao.selectByPrimaryKey(ae.getAppkey());
		        	   gsName=appPartnerEntitys.getUserName() ;
		           }
				  vo.setName(String.format("%s(%s){%s}",ae.getAreaname(),ae.getAreashortname() ,  ae.getAppkey() ));
			}
			vo.setOpen(true);
			vo.setpId(ae.getAreacode().substring(0, ae.getAreacode().length()-2));
			
			lst.add(vo);
		}
		
		return JsonUtils.toJSONNoFeatures(lst);
	}
	
	public static void main(String[] args) {
		String treeNode="测试(01){03}";
		int indexpos = treeNode.indexOf("(");
		System.out.println("-----indexpos="+indexpos);
		if (indexpos == -1) {
			//$("#name").val(treeNode.name);
		} else {
			//$("#name").val(treeNode.name.substring(0, indexpos));
			//$("#areashortname").val( treeNode.name.substring(indexpos + 1, treeNode.name.indexOf(")")));
			String name=treeNode.substring(0, indexpos);
			String areashortname=treeNode.substring(indexpos + 1, treeNode.indexOf(")"));
			System.out.println("-----name="+name+"   areashortname="+areashortname);
			String appkey=treeNode.substring(treeNode.indexOf("{") + 1, treeNode.indexOf("}"));
			System.out.println("-----appkey="+appkey );
		}
		
	}
	
	
	
	/**
	 * @param are
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectArea.do", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectArea(AreaEntity are, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		   /*System.out.println(are.getAreacode());*/
		   AreaEntity area = areadao.get(are.getAreacode());
		   
           if(StringUtils.isNotEmpty(area.getAppkey())){
        	   AppPartnerEntity  appPartnerEntitys =appPartnerEntityDao.selectByPrimaryKey(area.getAppkey());
        	   area.setAppkey(appPartnerEntitys.getUserName());
           }
 
		   String json = JsonUtils.toJSONString(area);
		   return json;
	}
	/**
	 * @param are
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectAreaFu.do", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectAreaFu(AreaEntity are, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		   /*System.out.println(are.getAreacode());*/
		   if(are.getAreacode().length() == 6){
			   AreaEntity area = areadao.get(are.getAreacode().substring(0,4));
			   String json = JsonUtils.toJSONString(area);
			   return json;
		   }else if (are.getAreacode().length() == 4) {
			   AreaEntity area = areadao.get(are.getAreacode().substring(0,2));
			   String json = JsonUtils.toJSONString(area);
			   return json;
		   }
		   AreaEntity area = areadao.get(are.getAreacode());
		   String json = JsonUtils.toJSONString(area);
		   return json;
		  
					 	

	
	}

}
