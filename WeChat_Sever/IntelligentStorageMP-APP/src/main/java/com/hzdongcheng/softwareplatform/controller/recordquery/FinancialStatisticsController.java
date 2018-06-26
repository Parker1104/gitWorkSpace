package com.hzdongcheng.softwareplatform.controller.recordquery;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.controller.temobj.StaticAppCros;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant.SysDict;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackagefailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackagefailEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackageEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackagefailEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.StoreInRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalEntityDao;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/financialStatistics")
public class FinancialStatisticsController extends BaseController {
	@Autowired
	TerminalEntityDao terminalEntityDao;
	
	@Autowired
	PtreadypackageEntityDao ptreadyPackageEntityDao;
	
	@Autowired
	PtreadypackagefailEntityDao ptreadypackagefailEntityDao;
	
	@Autowired
	StoreInRecordEntityDao storeInRecordEntityDao;
	
	@RequestMapping("/view")
	public ModelAndView payStatus() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/FinancialStatistics");
		return mv;
	}
	public static Log4jUtils logger = Log4jUtils.createInstanse(PayStatusQueryController.class);
	/*费用查询*/
	@RequestMapping(value = "/financialRecord",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String FinancialRecord(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		JSONObject reslut = new JSONObject();
		String terminalId = request.getParameter("terminalId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = request.getParameter("startTime");
		String overTime = request.getParameter("overTime");
		List<Byte> values = new ArrayList<Byte>();
		values.add((byte) 1);
		values.add((byte) 2);
		List<StaticAppCros> staticAppCrosList=new ArrayList<StaticAppCros>();
		Page<StaticAppCros> staticAppCrosPage = new Page<StaticAppCros>();
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		staticAppCrosPage.setPageSize(rows);
		staticAppCrosPage.setPageNo(page);
		if(terminalId!=null&&terminalId.length()<24){	//查询所有柜子
				TerminalEntityExample terminalExample = new TerminalEntityExample();
				TerminalEntityExample.Criteria criteria = terminalExample.createCriteria();
				criteria.andAreacodeEqualTo("01");
				List<TerminalEntity> TerminalList = null;
				TerminalList=terminalEntityDao.selectByExample(terminalExample);
				 staticAppCrosList=new ArrayList<StaticAppCros>();
				if(!CollectionUtils.isEmpty(TerminalList)){
					int size = TerminalList.size();
					for(int i=0;i<size;i++){
						float coinMoney = 0;		//投币机总金额
						float wxMoney = 0;			//微信总金额
						float wxMoneyP = 0;			//微信P表金额
						float wxMoneyPF = 0;		//微信PF表金额
						float countMoney = 0;		//微信与投币机金额总和
						terminalId = TerminalList.get(i).getTerminalid();
						String displayname = TerminalList.get(i).getDisplayname()+"号柜";
						/*投币机实收*/
						StoreInRecordEntityExample coinExample = new StoreInRecordEntityExample();
						StoreInRecordEntityExample.Criteria coinCriteria = coinExample.createCriteria();
						coinCriteria.andTerminalidEqualTo(terminalId).andUsertypeEqualTo(SysDict.CUSTOMER_TYPE_NROMAL)
						.andStoreintimeBetween(sdf.parse(startTime), sdf.parse(overTime));
						List<StoreInRecordEntity> coinStoreInMoneyRecords=storeInRecordEntityDao.selectByExample(coinExample);
							StoreInRecordEntity coinStoreInMoneyRecord = new StoreInRecordEntity();
							int srecordSize = coinStoreInMoneyRecords.size();
							for(int j=0;j<srecordSize;j++){
								coinStoreInMoneyRecord = coinStoreInMoneyRecords.get(j);
								coinMoney = coinMoney+coinStoreInMoneyRecord.getMoney();  //投币机总金额累加
							}
						
						/*微信实收*/
						//step1  Ptreadypackage表
						PtreadypackageEntityExample wxPExample = new PtreadypackageEntityExample();
						PtreadypackageEntityExample.Criteria wxCriteria = wxPExample.createCriteria();
						wxCriteria.andTerminalnoEqualTo(terminalId)
						.andOrdertimeBetween(sdf.parse(startTime), sdf.parse(overTime)).andPaystatusIn(values);
						List<PtreadypackageEntity> ptreadypackageEntitys = ptreadyPackageEntityDao.selectByExample(wxPExample);
							PtreadypackageEntity ptreadypackageEntity = new PtreadypackageEntity();
							int precordSize = ptreadypackageEntitys.size();
							for(int h=0;h<precordSize;h++){
								ptreadypackageEntity = ptreadypackageEntitys.get(h);
								if(ptreadypackageEntity.getPaystatus()==1){
									wxMoneyP = ptreadypackageEntity.getPayamt().floatValue()+wxMoneyP ;
								}else if(ptreadypackageEntity.getPaystatus()==2){
									if(null ==ptreadypackageEntity.getHireamt()){
										wxMoneyP = ptreadypackageEntity.getPayamt().floatValue()+wxMoneyP ;
									}else{
										wxMoneyP = ptreadypackageEntity.getHireamt().floatValue()+wxMoneyP ;
									}
									
								}
							}
						
						//step2  PtreadypackageFail表
						PtreadypackagefailEntityExample wxPFExample = new PtreadypackagefailEntityExample();
						PtreadypackagefailEntityExample.Criteria wxPFCriteria = wxPFExample.createCriteria();
						wxPFCriteria.andTerminalnoEqualTo(terminalId).
						andOrdertimeBetween(sdf.parse(startTime), sdf.parse(overTime)).andPaystatusIn(values);
						List<PtreadypackagefailEntity> ptreadypackagefailEntitys = ptreadypackagefailEntityDao.selectByExample(wxPFExample);
							PtreadypackagefailEntity ptreadypackagefailEntity = new PtreadypackagefailEntity();
							int precordFSize = ptreadypackagefailEntitys.size();
							for(int g=0;g<precordFSize;g++){
								ptreadypackagefailEntity = ptreadypackagefailEntitys.get(g);
								if(ptreadypackagefailEntity.getPaystatus()==1){
									wxMoneyPF = ptreadypackagefailEntity.getPayamt().floatValue() + wxMoneyPF;
								}else if(ptreadypackagefailEntity.getPaystatus()==2){
									if(null == ptreadypackagefailEntity.getHireamt()){
										wxMoneyPF = ptreadypackagefailEntity.getPayamt().floatValue() + wxMoneyPF;
									}else{
										wxMoneyPF = ptreadypackagefailEntity.getHireamt().floatValue() + wxMoneyPF;
									}
									
								}
							}
						wxMoney = wxMoneyP+wxMoneyPF;
						countMoney = coinMoney + wxMoney;
						
						/*总计金额发送到后台页面*/
						StaticAppCros staticAppCros=new StaticAppCros(
							displayname,startTime,overTime,String.valueOf(wxMoney),String.valueOf(coinMoney),String.valueOf(countMoney));
						staticAppCrosList.add(staticAppCros);
					}
				}else{
					reslut.put("msg", "未查询到设备");
			    	response.getWriter().print(reslut);
					System.err.println("未查询到设备");
				}
		}else if(terminalId!=null&&terminalId.length()==24){
			/*投币机实收*/
			float coinMoney = 0;
			float wxMoney = 0;
			float wxMoneyP = 0;
			float wxMoneyPF = 0;
			float countMoney = 0;
			TerminalEntityExample terminalExample = new TerminalEntityExample();
			TerminalEntityExample.Criteria criteria = terminalExample.createCriteria();
			criteria.andTerminalidEqualTo(terminalId);
			List<TerminalEntity> TerminalList = null;
			TerminalList=terminalEntityDao.selectByExample(terminalExample);
			String displayname = TerminalList.get(0).getDisplayname()+"号柜";
			
			StoreInRecordEntityExample coinExample = new StoreInRecordEntityExample();
			StoreInRecordEntityExample.Criteria coinCriteria = coinExample.createCriteria();
			coinCriteria.andTerminalidEqualTo(terminalId).andUsertypeEqualTo(SysDict.CUSTOMER_TYPE_NROMAL)
			.andStoreintimeBetween(sdf.parse(startTime), sdf.parse(overTime));
			List<StoreInRecordEntity> coinStoreInMoneyRecords=storeInRecordEntityDao.selectByExample(coinExample);
				StoreInRecordEntity coinStoreInMoneyRecord = new StoreInRecordEntity();
				int srecordSize = coinStoreInMoneyRecords.size();
				for(int j=0;j<srecordSize;j++){
					coinStoreInMoneyRecord = coinStoreInMoneyRecords.get(j);
					coinMoney = coinMoney+coinStoreInMoneyRecord.getMoney();  //投币机总金额累加
				}
			
			/*微信实收*/
			//step1  Ptreadypackage表
			PtreadypackageEntityExample wxPExample = new PtreadypackageEntityExample();
			PtreadypackageEntityExample.Criteria wxCriteria = wxPExample.createCriteria();
			wxCriteria.andTerminalnoEqualTo(terminalId)
			.andOrdertimeBetween(sdf.parse(startTime), sdf.parse(overTime)).andPaystatusIn(values);
			List<PtreadypackageEntity> ptreadypackageEntitys = ptreadyPackageEntityDao.selectByExample(wxPExample);
				PtreadypackageEntity ptreadypackageEntity = new PtreadypackageEntity();
				int precordSize = ptreadypackageEntitys.size();
				for(int h=0;h<precordSize;h++){
					ptreadypackageEntity = ptreadypackageEntitys.get(h);
					if(ptreadypackageEntity.getPaystatus()==1){
						wxMoneyP = ptreadypackageEntity.getPayamt().floatValue() + wxMoneyP;
					}else if(ptreadypackageEntity.getPaystatus()==2){
						if(null ==ptreadypackageEntity.getHireamt()){
							wxMoneyP = ptreadypackageEntity.getPayamt().floatValue()+wxMoneyP ;
						}else{
							wxMoneyP = ptreadypackageEntity.getHireamt().floatValue()+wxMoneyP ;
						}
					}
				}
			
				
			//step2  PtreadypackageFail表
			PtreadypackagefailEntityExample wxPFExample = new PtreadypackagefailEntityExample();
			PtreadypackagefailEntityExample.Criteria wxPFCriteria = wxPFExample.createCriteria();
			wxPFCriteria.andTerminalnoEqualTo(terminalId).
			andOrdertimeBetween(sdf.parse(startTime), sdf.parse(overTime)).andPaystatusIn(values);
			List<PtreadypackagefailEntity> ptreadypackagefailEntitys = ptreadypackagefailEntityDao.selectByExample(wxPFExample);
				PtreadypackagefailEntity ptreadypackagefailEntity = new PtreadypackagefailEntity();
				int precordFSize = ptreadypackagefailEntitys.size();
				for(int g=0;g<precordFSize;g++){
					ptreadypackagefailEntity = ptreadypackagefailEntitys.get(g);
					if(ptreadypackagefailEntity.getPaystatus()==1){
						wxMoneyPF = ptreadypackagefailEntity.getPayamt().floatValue() + wxMoneyPF;
					}else if(ptreadypackagefailEntity.getPaystatus()==2){
						if(null == ptreadypackagefailEntity.getHireamt()){
							wxMoneyPF = ptreadypackagefailEntity.getPayamt().floatValue() + wxMoneyPF;
						}else{
							wxMoneyPF = ptreadypackagefailEntity.getHireamt().floatValue() + wxMoneyPF;
						}
						
					}
				}
				
			wxMoney = wxMoneyP+wxMoneyPF;  			//微信总金额
			countMoney = coinMoney + wxMoney;		//微信与投币机总金额
			
			StaticAppCros staticAppCros=new StaticAppCros(
				displayname,startTime,overTime,String.valueOf(wxMoney),String.valueOf(coinMoney),String.valueOf(countMoney));
			staticAppCrosList.add(staticAppCros);
		}else{
			return null;
		}
			System.out.println(terminalId+startTime+overTime);
			
			//查询总数
			int totalRecords = staticAppCrosList.size();
			if (totalRecords > 0) {
				PageHelper.startPage(page, rows);//分页查询
			}
			staticAppCrosPage.setResults(staticAppCrosList);
			staticAppCrosPage.setTotalRecord(totalRecords);
	
			float wxMoneyCounts=0;
			float coinMoneyCounts=0;
			float moneyCounts=0;
			
			if(!CollectionUtils.isEmpty(staticAppCrosList)){
				for(int i=0;i<staticAppCrosList.size();i++){
					StaticAppCros StaticAppCross=staticAppCrosList.get(i);
					if(StringUtils.isNotEmpty(StaticAppCross.getWxMoney())  ){
						wxMoneyCounts +=Float.parseFloat(StaticAppCross.getWxMoney());
					}
					if(StringUtils.isNotEmpty(StaticAppCross.getCoinMoney())  ){
						coinMoneyCounts +=Float.parseFloat(StaticAppCross.getCoinMoney());
					}
					if(StringUtils.isNotEmpty(StaticAppCross.getCountMoney())  ){
						moneyCounts +=Float.parseFloat(StaticAppCross.getCountMoney());
					}
				}
			}
			
			/*总计金额发送到后台页面*/
			StaticAppCros staticAppCros=new StaticAppCros(
					"总   计",startTime,overTime,String.valueOf(wxMoneyCounts),String.valueOf(coinMoneyCounts),String.valueOf(moneyCounts));
				staticAppCrosList.add(staticAppCros);
				
			GridPage<StaticAppCros> gridPage = new GridPage<StaticAppCros>(staticAppCrosPage);
			return JsonUtils.toJSONString(gridPage);
	}
	
	/*柜号查询*/
	@RequestMapping("/terminalQuery")
	public void terminalQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TerminalEntityExample example = new TerminalEntityExample();
		TerminalEntityExample.Criteria criteria = example.createCriteria();
		criteria.andAreacodeEqualTo("01");
		List<TerminalEntity> TerminalList = null;
		TerminalList=terminalEntityDao.selectByExample(example);
    	JSONArray family = new JSONArray();
		if(!CollectionUtils.isEmpty(TerminalList)){
			int size = TerminalList.size();
			for(int i=0;i<size;i++){
				JSONObject terminalIdJson = new JSONObject();
				String terminalName = TerminalList.get(i).getDisplayname();
				String terminalNo = TerminalList.get(i).getTerminalid();
				terminalIdJson.put("terminalName", Integer.parseInt(terminalName)) ;
				terminalIdJson.put("terminalNo", terminalNo) ;
				family.add(terminalIdJson);
			}
		}
		PrintWriter out = response.getWriter();
		out.println(family.toString());
        out.flush();
        out.close();
	}
}
