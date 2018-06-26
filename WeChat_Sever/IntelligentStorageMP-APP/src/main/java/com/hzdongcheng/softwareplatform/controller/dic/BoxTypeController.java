package com.hzdongcheng.softwareplatform.controller.dic;

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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxType;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IPaymentDetail;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.JsonResult;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.PaymentDetailEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxSizeEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.PaymentDetailEntityExample;
/**
 * 
 * @author wenheju
 *    箱子大小
 */
@Controller
@RequestMapping("/boxTypeController")
public class BoxTypeController extends BaseController{
	
    @Autowired
	IBoxType boxType; 
	@Autowired
	IOperationLogInpquire iOperation;
	@Autowired
	IPaymentDetail iPaymentDetail;
    
	@RequestMapping("/boxType")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/dic/boxType");
		return mv;
	}
	
	/**
	 * @param box
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 查询所有箱体信息
	 */
	@RequestMapping(value = "/selectBoxType.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectBoxType(BoxSizeEntity box, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<BoxSizeEntity> BoxSizePage = new Page<BoxSizeEntity>();
		BoxSizePage.setPageSize(rows);
		BoxSizePage.setPageNo(page);
		
		int totalRecords = 0;
		{
			
			//条件
			BoxSizeEntityExample example = new BoxSizeEntityExample();
			example.setDistinct(true);
			if(!StringUtils.isEmpty(box.getBoxtypename())){
				example.createCriteria().andBoxtypenameLike("%"+box.getBoxtypename()+"%");
			}			
			//查询总数
			totalRecords = (int) boxType.count(example);
			
			List<BoxSizeEntity> accountList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				accountList = boxType.findByExampleEntity(example);
			}
			
			BoxSizePage.setResults(accountList);
			BoxSizePage.setTotalRecord(totalRecords);
		}
		
		GridPage<BoxSizeEntity> gridPage = new GridPage<BoxSizeEntity>(BoxSizePage);
		return JsonUtils.toJSONString(gridPage);	
			
		
	}
	@RequestMapping(value = "/selectBoxTypeName.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String selectBoxTypeName(String  boxTypeName,BoxSizeEntity box, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//条件
		BoxSizeEntityExample example = new BoxSizeEntityExample();
		example.setDistinct(true);
		example.createCriteria().andBoxtypenameEqualTo(boxTypeName);
		List<BoxSizeEntity> boxSizeEntity = boxType.findByExampleEntity(example);
		String json = JsonUtils.toJSONNoFeatures(boxSizeEntity);
		return json;
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 添加 修改箱体信息
	 */
	@RequestMapping(value = "/saveOrUpdateBoxType.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String saveOrUpdateBoxType(HttpServletRequest request, HttpServletResponse response) throws Exception {		    
			JsonResult jresult=new JsonResult();
			AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
			Date d = new Date();
			d.getTime();
		    String boxtypename = request.getParameter("boxtypename");
		    System.out.println(boxtypename);
		    Integer shen = Integer.parseInt(request.getParameter("shen")) ;
			System.out.println(shen);
		    Integer kuan =  Integer.parseInt(request.getParameter("kuan"));
	        System.out.println(kuan);
	    	Integer gao =  Integer.parseInt(request.getParameter("gao"));
		    System.out.println(gao);
		    String beizhu = request.getParameter("beizhu");
		    System.out.println(beizhu);
			String boxTypeCode=getParamString(request, "boxtypecode", "");
			/*System.out.println(boxTypeCode);*/
			//校验
			if(StringUtils.isEmpty(boxtypename)){
				jresult.msg="箱子大小不能为空！";
			}else{
				try{
					 BoxSizeEntity box = new BoxSizeEntity();
					if(!StringUtils.isEmpty(boxTypeCode))
						box.setBoxtypecode(Integer.parseInt(boxTypeCode));
					    box.setBoxtypename(boxtypename);
					    box.setHeight(gao);
					    box.setWidth(kuan);
					    box.setDepth(shen);
					    box.setMemo(beizhu);
					    boxType.saveOrUpdate(box);
						OperatorDiaryEntity operator = new OperatorDiaryEntity();
						if(boxTypeCode == null || boxTypeCode == ""){
							operator.setAccountcode(acc1.getAccountcode());
							operator.setDate(d);
							operator.setDescription("新增箱体尺寸");
							operator.setModlename("基础资料");
							operator.setMemo("新增箱体尺寸");
							iOperation.saveOrUpdate(operator);
						}else {
							operator.setAccountcode(acc1.getAccountcode());
							operator.setDate(d);
							operator.setDescription("修改箱体尺寸");
							operator.setModlename("基础资料");
							operator.setMemo("修改箱体尺寸");
							iOperation.saveOrUpdate(operator);
						}

					
					//List<RoleEntity> roleList = roledao.findAll();
					jresult.success=true;
					jresult.msg="操作成功";
					//jresult.data=JsonUtils.list2json(roleList);
				}catch(Exception e){
					jresult.msg=e.getMessage();
				}
			}
			return JsonUtils.toJSONNoFeatures(jresult);	   		         
	}
	/**
	 * 
	 * @param ids
	 * @throws Exception
	 * 删除箱体信息
	 */
	@RequestMapping(value = "/delectBoxType.do",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public void delectBoxType(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			//判断收费从表收费有数据
			PaymentDetailEntityExample example = new PaymentDetailEntityExample();
			example.createCriteria().andBoxtypecodeEqualTo(Integer.parseInt(id));
			List<PaymentDetailEx>  paymentDetailEx = iPaymentDetail.findByExampleEntity(example);
			if(paymentDetailEx.size()>0){
				jo.put("msg", "删除失败收费表有引用！");
			}else {
				boxType.delete(Integer.parseInt(id));
				jo.put("msg", "删除成功！");
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("删除箱体尺寸");
				operator.setModlename("基础资料");
				operator.setMemo("删除箱体尺寸");
				iOperation.saveOrUpdate(operator);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败！");
		}
	    response.getWriter().print(jo); 
	}
}
