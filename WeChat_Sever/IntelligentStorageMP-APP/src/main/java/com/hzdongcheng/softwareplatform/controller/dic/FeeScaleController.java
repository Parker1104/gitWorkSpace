package com.hzdongcheng.softwareplatform.controller.dic;

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
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.common.Constant;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxType;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBusinessModel;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDatadic;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPaymentDetail;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPaymentMaster;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentDetailEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentMasterEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentMasterEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;

/**
 * @author WenHeJu
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @ClassName:  FeeScaleController
 * @date 2017年7月7日 下午6:00:43
 * 收费标准
 */
@Controller
@RequestMapping("/feeScaleController") 
public class FeeScaleController extends BaseController {
	@Autowired
	private IPaymentMaster iPaymentMaster;
	@Autowired
	private IPaymentDetail iPaymentDetail;
	@Autowired
	private IBoxType iBoxType;
	@Autowired
	private IBusinessModel iBusinessModel;
	@Autowired
	private IOperationLogInpquire iOperation;
	@Autowired
	private IDatadic iDatadic;

	/**
	 * @return 取箱子的信息和收费主表信息
	 */
	@RequestMapping("/feeScale")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/dic/feeScale");
		mv.addObject("PaymentMaster", iPaymentMaster.findAll());
		mv.addObject("BoxType", iBoxType.findAll());
		mv.addObject("datadic1", iDatadic.getChargecode());
		mv.addObject("datadic2", iDatadic.getUsedState());
		return mv;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 添加 修改信息 （从表） 
	 */
	@RequestMapping(value = "/saveOrUpdateProgramElement", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String saveOrUpdateProgramElement(PaymentDetailEntity pay, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult jresult = new JsonResult();
		//获取当前用户
		AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");//创建人
		String makeOpCode = acc.getAccountname();
		Integer Masterid =  pay.getMasterid();
		try {
			// 添加 或更新收费从表
			PaymentDetailEntity paymentDetail = iPaymentDetail.get(pay.getMasterid(), String.valueOf(Masterid)+pay.getBoxtypecode());		
			if(paymentDetail == null){
				pay.setDetailid(String.valueOf(Masterid)+pay.getBoxtypecode());
				pay.setMakedate(DateUtils.nowDate());
				pay.setLastmodifytime(DateUtils.nowDate());
				pay.setMakeopcode(makeOpCode);
				iPaymentDetail.insert(pay);
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc.getAccountcode());
				operator.setDate(DateUtils.nowDate());
				operator.setDescription("新增收费标准绑箱信息");
				operator.setModlename("基础资料");
				operator.setMemo("新增收费标准绑箱信息");
				iOperation.saveOrUpdate(operator);
			}else {
				pay.setDetailid(String.valueOf(Masterid)+pay.getBoxtypecode());
				pay.setLastmodifytime(DateUtils.nowDate());
				pay.setMakeopcode(makeOpCode);
				iPaymentDetail.saveOrUpdate(pay);
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc.getAccountcode());
				operator.setDate(DateUtils.nowDate());
				operator.setDescription("修改收费标准绑箱信息");
				operator.setModlename("基础资料");
				operator.setMemo("修改收费标准绑箱信息");
				iOperation.saveOrUpdate(operator);
			}			
			jresult.success = true;
			jresult.msg = getMessage(request, "tip.success");
		} catch (Exception e) {
			jresult.msg = e.getMessage();
		}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	/**
	 * @param pay
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 收费标准（主表）
	 */
	@RequestMapping(value = "/saveOrUpdatePaymentMaster", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String saveOrUpdatePaymentMaster(PaymentMasterEntity pay, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult jresult = new JsonResult();
		//获取当前用户
		AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");//创建人
		String makeOpCode = acc.getAccountname();
		//查询标准序号
		PaymentMasterEntityExample example = new PaymentMasterEntityExample();
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andChargecodeEqualTo(pay.getChargecode());
		List<PaymentMasterEx> paymentMasterEntity = iPaymentMaster.selectAllPaymentMaster(example);

		try {
			if(pay.getMasterid() == null){
				if(StringUtils.isEmpty(pay.getMasterid())){
					pay.setOrdernumber(paymentMasterEntity.size() + 1);
					pay.setMakedate(DateUtils.nowDate());
					pay.setLastmodifytime(DateUtils.nowDate());
					pay.setMakeopcode(makeOpCode);
					iPaymentMaster.saveOrUpdate(pay);
					OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(acc.getAccountcode());
					operator.setDate(DateUtils.nowDate());
					operator.setDescription("新增收费标准");
					operator.setModlename("基础资料");
					operator.setMemo("新增收费标准");
					iOperation.saveOrUpdate(operator);
				}


			}else {
				pay.setMakedate(pay.getMakedate());
				pay.setLastmodifytime(DateUtils.nowDate());
				pay.setMakeopcode(makeOpCode);
				iPaymentMaster.saveOrUpdate(pay);
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc.getAccountcode());
				operator.setDate(DateUtils.nowDate());
				operator.setDescription("修改收费标准");
				operator.setModlename("基础资料");
				operator.setMemo("修改收费标准");
				iOperation.saveOrUpdate(operator);
			}

			jresult.success = true;
			jresult.msg = getMessage(request, "tip.success");
		} catch (Exception e) {
			jresult.msg = e.getMessage();
		}
		return JsonUtils.toJSONNoFeatures(jresult);
	}
	/**
	 * 
	 * @param pay
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询从表信息
	 */
	@RequestMapping(value = "/selectProgramElement", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectProgramElement(String masterid , String detailid, String ordernumber ,PaymentDetailEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PaymentDetailEntity paymentDetailEntity = iPaymentDetail.get(Integer.parseInt(masterid), detailid);
		return JsonUtils.toJSONString(paymentDetailEntity);
	} 
	/**
	 * 
	 * @param pay
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询从表信息  用作弹框用
	 */
	@RequestMapping(value = "/selectProgramFeescale", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectProgramFeescale(String masterid ,String ordernumber ,PaymentDetailEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PaymentDetailEntityExample example	= new 	PaymentDetailEntityExample();
		example.setDistinct(true);
		example.createCriteria().andMasteridEqualTo(Integer.parseInt(masterid));
		List<PaymentDetailEx> paymentDetailEx = iPaymentDetail.findByExampleEntity(example);
		return JsonUtils.toJSONString(paymentDetailEx);
	} 
	/**
	 * 
	 * @param pay
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询所有从表信息
	 */
	@RequestMapping(value = "/selectProgram", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectProgram(PaymentDetailEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<PaymentDetailEx> paymentPage = new Page<PaymentDetailEx>();
		paymentPage.setPageSize(rows);
		paymentPage.setPageNo(page);

		int totalRecords = 0;
		{

			//条件
			PaymentDetailEntityExample example = new PaymentDetailEntityExample();
			example.setDistinct(true);
			example.createCriteria().andMasteridEqualTo(pay.getMasterid());
			//查询总数
			totalRecords = (int) iPaymentDetail.count(example);

			List<PaymentDetailEx> paymentList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				paymentList = iPaymentDetail.findByExampleEntity(example);
			}

			paymentPage.setResults(paymentList);
			paymentPage.setTotalRecord(totalRecords);
		}

		GridPage<PaymentDetailEx> gridPage = new GridPage<PaymentDetailEx>(paymentPage);
		return JsonUtils.toJSONString(gridPage);	

	} 
	/**
	 * @param pay
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询收费标准收费存在
	 */ 
	@RequestMapping(value = "/selectChargeCode", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectChargeCode(PaymentMasterEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PaymentMasterEntityExample example = new PaymentMasterEntityExample();
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andChargecodeEqualTo(pay.getChargecode());
		List<PaymentMasterEx> paymentMasterEntity = iPaymentMaster.selectAllPaymentMaster(example);
		return JsonUtils.toJSONString(paymentMasterEntity);
	} 
	/**
	 * @param pay
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询主表
	 */
	@RequestMapping(value = "/selectFeescalProgram", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String selectFeescalProgram(PaymentMasterEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);

		Page<PaymentMasterEx> paymentPage = new Page<PaymentMasterEx>();
		paymentPage.setPageSize(rows);
		paymentPage.setPageNo(page);

		int totalRecords = 0;
		{

			//条件
			PaymentMasterEntityExample example = new PaymentMasterEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(pay.getChargecode())){
				example.createCriteria().andChargecodeEqualTo(pay.getChargecode());
			}
			//查询总数
			totalRecords = (int) iPaymentMaster.count(example);

			List<PaymentMasterEx> paymentList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				paymentList = (List<PaymentMasterEx>) iPaymentMaster.selectAllPaymentMaster(example);
				if(paymentList != null){
					for(PaymentMasterEx  rule: paymentList){
						//收费模式
						rule.setDictEntity(iDatadic.get(Constant.DICT_PAYMENTMASTERID, rule.getChargecode()));
						//收费标准
						rule.setDictEntity1(iDatadic.get(Constant.DICT_USEDSTATE, rule.getUsedstate()));
					}

				}
			}

			paymentPage.setResults(paymentList);
			paymentPage.setTotalRecord(totalRecords);
		}

		GridPage<PaymentMasterEx> gridPage = new GridPage<PaymentMasterEx>(paymentPage);
		return JsonUtils.toJSONString(gridPage);		
	}
	/**
	 * 
	 * @param ids
	 * @throws Exception
	 * 删除 从主表  收费信息
	 */
	@RequestMapping(value = "/delectProgramElement", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public void delectProgramElement(PaymentMasterEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AccountEntity acc = (AccountEntity) request.getSession().getAttribute("account");
		OperatorDiaryEntity operator = new OperatorDiaryEntity();		    
		iPaymentMaster.delete(pay.getMasterid());	
		operator.setAccountcode(acc.getAccountcode());
		operator.setDate(DateUtils.nowDate());
		operator.setDescription("删除收费标准");
		operator.setModlename("基础资料");
		operator.setMemo("删除收费标准");
		iOperation.saveOrUpdate(operator);
	}
	/**
	 * @param pay
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 * 删除 看是否其他地方有引用
	 */
	@RequestMapping(value = "/delect", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public String delect(PaymentMasterEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BusinessModelEntityExample example = new BusinessModelEntityExample();
		example.setDistinct(true);
		BusinessModelEntityExample.Criteria criteria = example.createCriteria();
		criteria.andConfignameEqualTo("ChargeMode");
		criteria.andConfigvalueEqualTo(String.valueOf(pay.getMasterid()));
		List<BusinessModelEntity> businessModelEntity = iBusinessModel.findByExample(example);
		return JsonUtils.toJSONNoFeatures(businessModelEntity);
	}
	@RequestMapping(value = "/delectDetail", produces = { "text/html;charset=UTF-8;" })
	@ResponseBody
	public void delectDetail(PaymentDetailEntity pay, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		iPaymentDetail.delete(pay.getMasterid(),pay.getDetailid());
	}

}