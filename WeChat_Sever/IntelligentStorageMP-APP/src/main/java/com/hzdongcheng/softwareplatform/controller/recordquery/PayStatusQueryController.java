package com.hzdongcheng.softwareplatform.controller.recordquery;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appDto.InpayStatusQuery;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg.impl.AppServiceImpl;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackageEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PtreadypackagefailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TakeOutRecordEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackageEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.PtreadypackagefailEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.StoreInRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TakeOutRecordEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalEntityDao;

@Controller
@RequestMapping("/payStatus")
public class PayStatusQueryController extends BaseController{
	
	@Autowired
	TerminalEntityDao terminalEntityDao;
	
	@Autowired
	PtreadypackageEntityDao ptreadyPackageEntityDao;
	
	@Autowired
	PtreadypackagefailEntityDao ptreadypackagefailEntityDao;
	
	@Autowired
	StoreInRecordEntityDao storeInRecordEntityDao;
	
	@Autowired
	TakeOutRecordEntityDao takeOutRecordEntityDao;
	
	/**
	 * 订单查询
	 * @return
	 */
	public static Log4jUtils logger = Log4jUtils.createInstanse(PayStatusQueryController.class);
	@RequestMapping("/view")
	public ModelAndView payStatus() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/record/payStatusQuery");
		return mv;
	}
	@RequestMapping(value = "/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(InpayStatusQuery ptready,HttpServletRequest request,HttpServletResponse response) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 20);
		
		Page<PtreadypackageEntity> PtreadyPage = new Page<PtreadypackageEntity>();
		PtreadyPage.setPageSize(rows);
		PtreadyPage.setPageNo(page);
		int totalRecords = 0;
		PtreadypackageEntityExample example = new PtreadypackageEntityExample();
		PtreadypackageEntityExample.Criteria criteria = example.createCriteria();
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		if(!StringUtils.isEmpty(ptready.getBoxno())) {
			criteria.andBoxnoEqualTo(ptready.getBoxno());
		}
		if (!StringUtils.isEmpty(ptready.getTerminalno())) {
			criteria.andTerminalnoEqualTo(ptready.getTerminalno());
		}
		if (!StringUtils.isEmpty(ptready.getOpenstatus())) {
			criteria.andOpenstatusEqualTo(ptready.getOpenstatus());
		}
		if (!StringUtils.isEmpty(ptready.getStarttime())&&StringUtils.isEmpty(ptready.getOvertime())) {
			Date startTime;
			try {
				startTime = sdf.parse(ptready.getStarttime().trim());
				criteria.andOrdertimeGreaterThanOrEqualTo(startTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}if(!StringUtils.isEmpty(ptready.getOvertime())&&StringUtils.isEmpty(ptready.getStarttime())){
			Date overTime;
			try {
				overTime = sdf.parse(ptready.getOvertime().trim());
				criteria.andOrdertimeLessThanOrEqualTo(overTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(ptready.getStarttime())&&!StringUtils.isEmpty(ptready.getOvertime())){
			Date startTime;
			Date overTime;
			try {
				startTime =sdf.parse(ptready.getStarttime().trim());
				overTime = sdf.parse(ptready.getOvertime().trim());
				criteria.andOrdertimeBetween(startTime, overTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//查询总数
				totalRecords = (int) ptreadyPackageEntityDao.countByExample(example);
				List<PtreadypackageEntity> terminalList = null;
				List<PtreadypackageEntity> terminalListNew = new ArrayList<>();
				if (totalRecords > 0)
				{
					//分页查询
					PageHelper.startPage(page, rows);
					terminalList = ptreadyPackageEntityDao.selectByExample(example);
					if(!CollectionUtils.isEmpty(terminalList)){
						for(int i=0;i<terminalList.size();i++){
							PtreadypackageEntity terminals=terminalList.get(i);
							String terminalNo = terminals.getTerminalno();
							TerminalEntityExample termianlNameEx = new TerminalEntityExample();
							TerminalEntityExample.Criteria criteriaTermianlName = termianlNameEx.createCriteria();
							criteriaTermianlName.andTerminalidEqualTo(terminalNo);
							List<TerminalEntity>terminalEntitys=terminalEntityDao.selectByExample(termianlNameEx);
							if(!CollectionUtils.isEmpty(terminalEntitys)){
								TerminalEntity terminalEntity = terminalEntitys.get(0);
								String terminalName = terminalEntity.getDisplayname();
								terminals.setRemark(terminalName);
								terminalListNew.add(terminals);
							}
						}
					}
					else{
						logger.error(" PayStatusQueryController:柜号有误");
					}
				}
				PtreadyPage.setResults(terminalListNew);
				PtreadyPage.setTotalRecord(totalRecords);
		
		GridPage<PtreadypackageEntity> gridPage = new GridPage<PtreadypackageEntity>(PtreadyPage);
		return JsonUtils.toJSONString(gridPage);
	}
	
	/*取失败清楚*/
	@RequestMapping(value = "/deleteTakenRecord",produces = {"text/html;charset=UTF-8;"})
	public void openBox(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject reslut = new JSONObject();
		String terminalNo = request.getParameter("terminalNo");
		String boxNo = request.getParameter("boxNo");
		PtreadypackageEntityExample example = new PtreadypackageEntityExample();
		PtreadypackageEntityExample.Criteria criteria = example.createCriteria();
		criteria.andTerminalnoEqualTo(terminalNo).andBoxnoEqualTo(boxNo).andOpenstatusEqualTo((byte) 3);
		List<PtreadypackageEntity> boxlList = ptreadyPackageEntityDao.selectByExample(example);
			if(!CollectionUtils.isEmpty(boxlList)){
				PtreadypackageEntity ptready = boxlList.get(0);
				ptready.setOpenstatus((byte) 4);		//取失败后管理员开箱成功状态
			       ptreadyPackageEntityDao.updateByPrimaryKeySelective(ptready);
			}
		reslut.put("msg", "删除成功");
    	response.getWriter().print(reslut);
	}
	
	
	/*存失败清楚*/
	@RequestMapping(value = "/deleteDepositRecord",produces = {"text/html;charset=UTF-8;"})
	public void deleteRecord(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject reslut = new JSONObject();
		Date now = new Date(); 
		String terminalNo = request.getParameter("terminalNo");
		String boxNo = request.getParameter("boxNo");
			PtreadypackageEntityExample delete_example = new PtreadypackageEntityExample();
			PtreadypackageEntityExample.Criteria criteria = delete_example.createCriteria();
			criteria.andTerminalnoEqualTo(terminalNo).andBoxnoEqualTo(boxNo).andOpenstatusEqualTo((byte) 1);
			List<PtreadypackageEntity> delete_boxlLists = null;
			delete_boxlLists = ptreadyPackageEntityDao.selectByExample(delete_example);
			if(!CollectionUtils.isEmpty(delete_boxlLists)){
				PtreadypackageEntity delete_boxlList = new PtreadypackageEntity();
				delete_boxlList = delete_boxlLists.get(0);
					PtreadypackagefailEntity insert_failMsg= new PtreadypackagefailEntity();
					insert_failMsg.setBoxno(delete_boxlList.getBoxno());
					insert_failMsg.setCustomerid(delete_boxlList.getCustomerid());
					insert_failMsg.setCustomeraddress(delete_boxlList.getCustomeraddress());;
					insert_failMsg.setTerminalno(delete_boxlList.getTerminalno());
					insert_failMsg.setOrdertime(delete_boxlList.getOrdertime());
					insert_failMsg.setExpiredtime(delete_boxlList.getExpiredtime());
					insert_failMsg.setHireamt(delete_boxlList.getHireamt());
					insert_failMsg.setPayamt(delete_boxlList.getPayamt());;
					insert_failMsg.setPackageid(delete_boxlList.getPackageid());
					insert_failMsg.setTradewaterno(delete_boxlList.getTradewaterno());
					insert_failMsg.setPaystatus(delete_boxlList.getPaystatus());
					insert_failMsg.setLastmodifytime(now);
					insert_failMsg.setOpenstatus(delete_boxlList.getOpenstatus());
					insert_failMsg.setLimitcounts(delete_boxlList.getLimitcounts());
					insert_failMsg.setLimitcountstake(delete_boxlList.getLimitcountstake());
					ptreadypackagefailEntityDao.insert(insert_failMsg);		//插入查询表
					ptreadyPackageEntityDao.deleteByExample(delete_example);//删除业务表
					reslut.put("msg", "删除成功");
					response.getWriter().print(reslut);
			}else{
				reslut.put("msg", "删除失败");
				response.getWriter().print(reslut);
			}
	}
	
	/*清箱*/
	@RequestMapping(value = "/clearBox",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void clearBox(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String returnMsg = null;
		StoreInRecordEntityExample clearBoxExample = new StoreInRecordEntityExample();
		StoreInRecordEntityExample.Criteria criteria = clearBoxExample.createCriteria();
		criteria.andStateEqualTo((byte) 0);
		List<StoreInRecordEntity> storeInRecordEntitys= storeInRecordEntityDao.selectByExample(clearBoxExample);
		if(!CollectionUtils.isEmpty(storeInRecordEntitys)){
			int size =storeInRecordEntitys.size();
			for(int i=0;size>i;i++){
				StoreInRecordEntity storeInRecordEntity = storeInRecordEntitys.get(i);
				storeInRecordEntity.setState((byte)1);
				storeInRecordEntityDao.updateByPrimaryKeySelective(storeInRecordEntity);
				if(storeInRecordEntity.getUsertype().equals("11")){
					PtreadypackageEntityExample clearPtreadyExample = new PtreadypackageEntityExample();
					PtreadypackageEntityExample.Criteria clearPtreadyCriteria = clearPtreadyExample.createCriteria();
					clearPtreadyCriteria
					.andTerminalnoEqualTo(storeInRecordEntity.getTerminalid())
					.andBoxnoEqualTo(storeInRecordEntity.getBoxid().toString())
					.andStoreintimeEqualTo(storeInRecordEntity.getStoreintime());
					List<PtreadypackageEntity>pEntitys=ptreadyPackageEntityDao.selectByExample(clearPtreadyExample);
					PtreadypackageEntity pEntity = pEntitys.get(0);
					pEntity.setPaystatus((byte)2);
					pEntity.setOpenstatus((byte)2);
					ptreadyPackageEntityDao.updateByPrimaryKeySelective(pEntity);
				}
				//Step.5 写取物记录
				TakeOutRecordEntityExample clearTakeOutExample = new TakeOutRecordEntityExample();
				TakeOutRecordEntityExample.Criteria clearTakeOutCriteria = clearTakeOutExample.createCriteria();
				clearTakeOutCriteria
				.andTerminalidEqualTo(storeInRecordEntity.getTerminalid())
				.andBoxidEqualTo(storeInRecordEntity.getBoxid())
				.andStoreintimeEqualTo(storeInRecordEntity.getStoreintime())
				.andUsercardidEqualTo(storeInRecordEntity.getUsercardid());
				List<TakeOutRecordEntity>  takeOutRecordEntityList=	takeOutRecordEntityDao.selectByExample(clearTakeOutExample);
				if(! CollectionUtils.isEmpty(takeOutRecordEntityList) ){
					logger.error(" PayStatusQueryController:TakeOutRecord已存在记录");
				}else{
					TakeOutRecordEntity takeOutRecordEntity = new TakeOutRecordEntity();
					takeOutRecordEntity.setTerminalid(storeInRecordEntity.getTerminalid());
					takeOutRecordEntity.setBoxid(storeInRecordEntity.getBoxid());
					takeOutRecordEntity.setUsercardid(storeInRecordEntity.getUsercardid());
					takeOutRecordEntity.setStoreintime(storeInRecordEntity.getStoreintime()); 
					takeOutRecordEntity.setTaketime(new Date());
					takeOutRecordEntity.setMoney(storeInRecordEntity.getMoney());
					takeOutRecordEntity.setRealmoney(storeInRecordEntity.getMoney());
					takeOutRecordEntity.setType(3);     //1中途取；2正常取；3管理取；4超时取；5远程开箱取物  7卡取消取物
					takeOutRecordEntity.setCashierno("0");
					takeOutRecordEntity.setMakeopcode(null);
					takeOutRecordEntityDao.insert(takeOutRecordEntity);
					returnMsg = "1";
					logger.info("1 PayStatusQueryController clearBox:clear sccuess!");
				}
			}
		}else{
			returnMsg = "2";
			logger.info("2 PayStatusQueryController clearBox:no boxes clear;");
		}
		PrintWriter out = response.getWriter();
		out.println(returnMsg);
        out.flush();
        out.close();
	}
}
