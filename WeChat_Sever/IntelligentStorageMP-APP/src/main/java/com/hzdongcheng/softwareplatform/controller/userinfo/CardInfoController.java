package com.hzdongcheng.softwareplatform.controller.userinfo;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCCheckInUser;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCCheckOutUser;
import com.hzdongcheng.front.server.model.service.jcg.down.OutParamRCCommon;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.factory.PushServiceFactory;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IBoxService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.ICardAndBoxBound;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IUserService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.CardAndBoxBoundEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardAndBoxBoundEntityExample.Criteria;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;

@Controller
@RequestMapping(value="/cardInfo")
public class CardInfoController extends BaseController {
	@Autowired
	ICardAndBoxBound icard;
	@Autowired
	IUserService user;
	@Autowired
	IBoxService box;
	@Autowired
	TerminalService terminal;
	/**
	 * 卡片信息管理页面
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/userinfo/cardInfoManage");
		mv.addObject("card",user.findAll());
		TerminalEntityExample example2 = new TerminalEntityExample();
		example2.setOrderByClause("displayname desc");
		mv.addObject("terminal", terminal.findAll(example2));
		BoxEntityExample example = new BoxEntityExample();
		mv.addObject("box", box.findAll(example));
		return mv;
	}
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(HttpServletRequest request,CardAndBoxBoundEx card) throws ParseException{
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		
		Page<CardAndBoxBoundEx> CardPage = new Page<CardAndBoxBoundEx>();
		CardPage.setPageSize(rows);
		CardPage.setPageNo(page);
		
		int totalRecords = 0;
		    /*AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
			AccountEx operatorEntity = account.get(operator.getAccountcode());	*/
			
			//条件
			CardAndBoxBoundEntityExample example = new CardAndBoxBoundEntityExample();
			Criteria criteria = example.createCriteria();
			example.setDistinct(true);
			example.setOrderByClause("terminalid,boxid");
			if (!StringUtils.isEmpty(card.getCardid())) {
				criteria.andCardidEqualTo(card.getCardid());
			}
			if (!StringUtils.isEmpty(card.getBoxid())) {
				criteria.andBoxidEqualTo(card.getBoxid());
			}
			if (card.getSync()>-1) {
				criteria.andSyncEqualTo(card.getSync());
			}
			if (!StringUtils.isEmpty(card.getDisplayname())) {
				TerminalEntity entity = terminal.selectByDisplayname(card.getDisplayname());
				if (entity != null) {
					criteria.andTerminalidEqualTo(entity.getTerminalid());
				}				
			}
			//查询总数
			totalRecords = (int) icard.count(example);
			
			List<CardAndBoxBoundEx> CardList = null;
			if (totalRecords > 0)
			{
				//分页查询
				PageHelper.startPage(page, rows);
				CardList = icard.findAll(example);
			}
			
			CardPage.setResults(CardList);
			CardPage.setTotalRecord(totalRecords);
		
		GridPage<CardAndBoxBoundEx> gridPage = new GridPage<CardAndBoxBoundEx>(CardPage);
		return JsonUtils.toJSONString(gridPage);
	}
	/**
	 * 卡箱关系绑定
	 * @param card
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/add")
	public void add(CardAndBoxBoundEntity card,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity operator = (AccountEntity) request.getSession().getAttribute("account");
		card.setMakeopcode(operator.getAccountcode());
		card.setMakedate(DateUtils.nowDate());
		card.setLastmodifytime(DateUtils.nowDate());
		card.setSync((byte)0);		
		List<CardAndBoxBoundEntity> list = icard.selectByBoxId(card.getTerminalid(),card.getBoxid());		
		try {
			if (list.size()==0 || list==null) {
				icard.add(card);		
				jo.put("msg", "绑定成功");
			}else {
				jo.put("msg", "该设备柜号已绑卡");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "绑定失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 卡箱关系修改
	 * @param card
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/update")
	public void update(CardAndBoxBoundEntity card,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		card.setLastmodifytime(DateUtils.nowDate());
		try {
			icard.update(card);
			jo.put("msg", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "修改失败");
		}
		response.getWriter().println(jo);
	}
	/**
	 * 根据设备号查询箱编号
	 * @param terminalid
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/selectByid")
	public void selectByid(String terminalid,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
		   List<BoxEntity> list = box.selectByTerminalid(terminalid);
		   jo.put("msg", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(jo);
	}
	/**
	 * 卡箱绑定删除
	 * @param id
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/delete")
	public void delete(String id,String terminalid,int boxid,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		IPushClient pushClient = PushServiceFactory.createInstanse();		
		pushClient.setRecvTimeoutMills(5000);
		pushClient.connect();
		IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
		InParamRCCheckOutUser inParams = new InParamRCCheckOutUser();
		
		inParams.setTerminalID(terminalid);
		inParams.setCardID(id);
		inParams.setBoxID(boxid);
		try {
			jcgCtrl.checkOutUser("tcp://127.0.0.1:55666", inParams);
			System.out.println("注销成功");
			icard.delete(id);
			jo.put("msg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "删除失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 验证卡号是否存在
	 * @param cardid
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/selectByCardid")
	public void selectByCardid(String cardid,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		try {
			CardAndBoxBoundEntity card = icard.selectByCardid(cardid);
			if (card == null) {
				jo.put("msg", "true");
			}else {
				jo.put("msg", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(jo);
	}
}
