package com.hzdongcheng.softwareplatform.controller.terminalmanagement;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAccountManagement;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBusinessModel;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.AccountEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.TerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AppPartnerEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AppPartnerEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.AreaEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalEntityDao;
import com.hzdongcheng.softwareplatform.util.Objectes;
import com.hzdongcheng.softwareplatform.util.NumberUtils;

@Component
@Controller
@RequestMapping(value="/terminalControlleres")
public class TerminalControlleres extends BaseController{
	
	@Autowired
	private TerminalService impl;
	@Autowired
	private IAreaService iArea;
	@Autowired
	private IBusinessModel iModel;
	@Autowired
	private IBoxService iBox;
	@Autowired
	private IAccountManagement account;
	@Autowired
	private IOperationLogInpquire iOperation;
	
	@Autowired
	private AppPartnerEntityDao dao;
	
	@Autowired
	private BoxEntityDao boxEntityDao;
 
	@Autowired
	private  AreaEntityDao areaEntityDao;
	
	
	/**
	 * 设备（柜子）管理页面
	 * @return
	 */
	@RequestMapping("/arkAttributeSetes")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/equipmentinfo/arkAttributeSetes");
		
		AppPartnerEntityExample example=new AppPartnerEntityExample();
		List<AppPartnerEntity>  appPartnerEntitylists=dao.selectByExample(example);
		
		
/*		List<Objectes> runstatusList=new ArrayList<Objectes>();
		runstatusList.add(new Objectes("0","正常") );
		runstatusList.add(new Objectes("1","锁定") );
		runstatusList.add(new Objectes("2","故障") );
		mv.addObject("runstatusList",runstatusList);*/
		
		mv.addObject("appkeylist",appPartnerEntitylists);
		mv.addObject("area",iArea.findAll());
		mv.addObject("business",iModel.findAllByGroup());
		return mv;
	}		
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(HttpServletRequest request,TerminalEx terminal) throws ParseException{
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<TerminalEx> ManagerExPage = new Page<TerminalEx>();
		ManagerExPage.setPageSize(rows);
		ManagerExPage.setPageNo(page);
		
		int totalRecords = 0;
		    AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
			AccountEx operatorEntity = account.get(operator.getAccountcode());	
			//条件
			TerminalEntityExample example = new TerminalEntityExample();
			Criteria criteria = example.createCriteria();
			example.setDistinct(true);
			example.setOrderByClause("displayname ASC");
 
			//System.out.println(" terminal.getAreacode()="+terminal.getAreacode());
			
			if (!StringUtils.isEmpty(terminal.getAreacode())) {
				  criteria.andAreacodeLike(terminal.getAreacode()+"%");	
			}else{
				//criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");	
			}	
			
			if (!StringUtils.isEmpty(terminal.getDisplayname())) {
				criteria.andDisplaynameEqualTo(terminal.getDisplayname());
			}		
			if (!StringUtils.isEmpty(terminal.getNetworkstate())) {
				criteria.andNetworkstateEqualTo(terminal.getNetworkstate());
			}
			/*if (!StringUtils.isEmpty(terminal.getRegisterflag())) {
				criteria.andRegisterflagEqualTo(terminal.getRegisterflag());
			}*/
			if (!StringUtils.isEmpty(terminal.getRunstatus())) {
				criteria.andRunstatusEqualTo(terminal.getRunstatus());
			}
			if (!StringUtils.isEmpty(terminal.getDate())) {
				String date = terminal.getDate();
				Date d = DateUtils.stringToDateTime(date);
				criteria.andMakedateEqualTo(d);
			}
			//查询总数
			totalRecords = (int) impl.count(example);
			
			List<TerminalEx> TerminalList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				TerminalList = impl.findAll(example);
				for(int i=0;i<TerminalList.size();i++){
					TerminalEx TerminalExs=  	TerminalList.get(i);
					/*if( !StringUtils.isEmpty(TerminalExs.getAppkey()) ) {
						AppPartnerEntity AppPartnerEntitys=dao.selectByPrimaryKey(TerminalExs.getAppkey());
						TerminalList.get(i).setAppkeyname(AppPartnerEntitys.getUserName());
					}else{
						TerminalList.get(i).setAppkeyname("");
					}*/
					//System.out.println("  Areacode= "+TerminalExs.getAreacode());
					
					String boxcodemax="";
					
					String areacode=TerminalExs.getAreacode();
					AreaEntity areaEntitys=areaEntityDao.selectByPrimaryKey(areacode);
					String areashortnames=areaEntitys.getAreashortname();
					
					BoxEntityExample boxEntityExample =new BoxEntityExample();
					boxEntityExample.or().andDispalynameLike(areashortnames+"%");
				    List<BoxEntity> boxEntityList=boxEntityDao.selectByExample(boxEntityExample);
					if(!CollectionUtils.isEmpty(boxEntityList)){
						BoxEntity BoxEntityMaxId=boxEntityList.get(boxEntityList.size()-1);
						String dispalynames=BoxEntityMaxId.getDispalyname();
						//TerminalExs.setBoxcodemax(dispalynames);
						boxcodemax=dispalynames;
					}
					
					
					BoxEntityExample boxEntityExampleCode =new BoxEntityExample();
					boxEntityExampleCode.or().andTerminalidEqualTo(TerminalExs.getTerminalid());
				    List<BoxEntity> boxEntityListCode=boxEntityDao.selectByExample(boxEntityExampleCode);
					if(!CollectionUtils.isEmpty(boxEntityListCode)){
						BoxEntity BoxEntityStart=boxEntityListCode.get(0);
						BoxEntity BoxEntityEnd=boxEntityListCode.get(boxEntityListCode.size()-1);
						
						Integer Numberings=TerminalExs.getNumbering();
						if(null==Numberings || Numberings==0){//0 默认编号
							boxcodemax="boxNum=("+BoxEntityStart.getDispalyname()+"->"+BoxEntityEnd.getDispalyname() +")   maximum=("+boxcodemax+") " ;
						}else if(null!=Numberings && Numberings==1){//1 区域编号
							boxcodemax= "boxNum=("+BoxEntityStart.getDispalyname()+"->"+BoxEntityEnd.getDispalyname()+")";
						}
						
					}
					TerminalExs.setBoxcodemax(boxcodemax);
					
					/*（誉爱） 设备号16进制转ascii码*/
					String str ="";
					String a = NumberUtils.cutString(TerminalExs.getTerminalid());
					String b = a.substring(1, a.length());
					String c = b.substring(0,b.length()-1);
					String[]ss = c.split(",");
					for(int j=0;j<ss.length;j++){
						String val = ss[j].replace(" ", "");
						str = str+NumberUtils.toStringHex(val);
					}
					TerminalExs.setDisplayname(str);
				}
			}
			
			ManagerExPage.setResults(TerminalList);
			ManagerExPage.setTotalRecord(totalRecords);
		
		GridPage<TerminalEx> gridPage = new GridPage<TerminalEx>(ManagerExPage);
		return JsonUtils.toJSONString(gridPage);
	}
	/**
	 * 新增柜信息
	 * @param te
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public void add(TerminalEx te,BoxEntity box,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		try {
			String success = impl.InsertTerminal(te, box, acc1);
			jo.put("msg", success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		
		response.getWriter().print(jo);
	}
	
	/**
	 * 新增柜信息
	 * @param te
	 * @param request
	 * @param response
	 * @throws IOException 
	 *//*
	@RequestMapping(value="/add")
	@ResponseBody
	public void add(TerminalEntity te,BoxEntity box,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		try {
			String success = impl.InsertTerminal(te, box, acc1);
			jo.put("msg", success);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jo.put("msg", "操作失败");
		}
		
		response.getWriter().print(jo);
	}*/
	
	
	
	/**
	 * 删除柜属性
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/delete")
	public void delete(String id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		StringBuffer json = new StringBuffer();
		json.append("{\"msg\":");
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			iBox.deleteById(id);
			impl.delete(id);
			json.append("\"删除成功！\"");
		/*	OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("删除设备信息");
			operator.setModlename("设备管理");
			operator.setMemo("删除设备信息");
			iOperation.saveOrUpdate(operator);*/
		} catch (Exception e) {
			e.printStackTrace();
			json.append("\"删除失败！\"");
		}finally {
			json.append("}");
		}
		response.getWriter().print(json);
	}
	/**
	 * 去重查询
	 * @param terminalid
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/select")
	public void select(String terminalid,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			TerminalEntity entity = impl.select(terminalid);
			if (entity == null) {
				jo.put("msg", "设备号可以使用");
			}else {
				jo.put("msg", "设备号已存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "查询失败");
		}
		response.getWriter().print(jo);
	}
}
