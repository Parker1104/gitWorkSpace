package com.hzdongcheng.softwareplatform.controller.userinfo;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.eclipse.jdt.internal.compiler.env.IDependent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IDepartment;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.UserEntityExample.Criteria;

@Controller
@RequestMapping(value="/userController")
public class UserController extends BaseController{

	@Autowired
	private IUserService impl;
	@Autowired
	IOperationLogInpquire iOperation;
	/*@Autowired
	IDepartment idepartment;*/
	
    /**
     * 用户管理页面
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(value="/userList")
	public ModelAndView userList(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/web/userinfo/userManagement");
		/*mv.addObject("dept", idepartment.findAll());*/
        mv.addObject("LevelList", impl.findAll()); 
	    return mv;
	}
	@SuppressWarnings("null")
	@RequestMapping(value="/user",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String Tuser(HttpServletRequest request,UserEntity userEntity){
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<UserEntity> ManagerExPage = new Page<UserEntity>();
		ManagerExPage.setPageSize(rows);
		ManagerExPage.setPageNo(page);
		
		int totalRecords = 0;
		    
			//条件
			UserEntityExample example = new UserEntityExample();
			Criteria criteria = example.createCriteria();
			example.setDistinct(true);
			//criteria.andAreacodeLike(operatorEntity.getAreaEntity().getAreacode()+"%");			
			if (!StringUtils.isEmpty(userEntity.getUsercardid())) {
				criteria.andUsercardidEqualTo(userEntity.getUsercardid());
			}		
			if (!StringUtils.isEmpty(userEntity.getUsername())) {
				criteria.andUsernameEqualTo(userEntity.getUsername());
			}
			if (!StringUtils.isEmpty(userEntity.getUsertype())) {
				criteria.andUsertypeEqualTo(userEntity.getUsertype());
			}
			if (!StringUtils.isEmpty(userEntity.getState())) {
				criteria.andStateEqualTo(userEntity.getState());
			}
			//查询总数
			totalRecords = (int) impl.count(example);
			
			List<UserEntity> userEntityList = null;
			List<UserEntity> reUserEntityList = new ArrayList<UserEntity>();
			if (totalRecords > 0)
			{
				
				//分页查询
				PageHelper.startPage(page, rows);
				userEntityList = impl.findAll(example);
				for(int i=0;i<userEntityList.size();i++){
					UserEntity reUserEntity= userEntityList.get(i);
					String nickname="";
					Date lastmodifytime = reUserEntity.getLastmodifytime();
					try {
						nickname = java.net.URLDecoder.decode(reUserEntity.getNickname(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					reUserEntity.setNickname(nickname);
					reUserEntityList.add(reUserEntity);
				}
			}
			
			ManagerExPage.setResults(reUserEntityList);
			ManagerExPage.setTotalRecord(totalRecords);
		
		GridPage<UserEntity> gridPage = new GridPage<UserEntity>(ManagerExPage);
		return JsonUtils.toJSONString(gridPage);
	}
	/**
	 * 用户添加
	 * @param userEntity
	 * @param request
	 * @param response
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/userSave")
	@ResponseBody
	public void save(UserEntity userEntity, HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		Date date  = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR,1);
		String id = userEntity.getUsercardid();
		UserEntity u = impl.select(id);
		try {
			if(u == null || u.equals("null")){
				userEntity.setEnddate(cal.getTime());
				userEntity.setMakedate(date);
				userEntity.setLastmodifytime(date);
				impl.save(userEntity);
				jo.put("msg", "添加成功！");
				OperatorDiaryEntity operator = new OperatorDiaryEntity();
				operator.setAccountcode(acc1.getAccountcode());
				operator.setDate(d);
				operator.setDescription("新增用户信息");
				operator.setModlename("用户管理");
				operator.setMemo("新增用户信息");
				iOperation.saveOrUpdate(operator);
			}else {
				jo.put("msg", "用户卡号已经存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败！");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 用户修改
	 * @param userEntity
	 * @param request
	 * @param response
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping(value="/userUpdate")
	@ResponseBody
	public void update(UserEntity userEntity, HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		JSONObject jo = new JSONObject();
		Date date  = new Date();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			userEntity.setLastmodifytime(date);
			impl.update(userEntity);
			jo.put("msg", "修改成功！");
			OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("修改用户信息");
			operator.setModlename("用户管理");
			operator.setMemo("修改用户信息");
			iOperation.saveOrUpdate(operator);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "操作失败！");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 用户删除
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/userDelete")
	public void delete( String id ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			impl.delete(id);
			jo.put("msg", "删除成功！");
			OperatorDiaryEntity operator = new OperatorDiaryEntity();
			operator.setAccountcode(acc1.getAccountcode());
			operator.setDate(d);
			operator.setDescription("删除用户信息");
			operator.setModlename("用户管理");
			operator.setMemo("删除用户信息");
			iOperation.saveOrUpdate(operator);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败！");
		}
	    response.getWriter().print(jo); 	
	} 
	
}




















