package com.hzdongcheng.softwareplatform.controller.userinfo;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.components.toolkits.utils.GridPage;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCClearBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCLockBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCOpenBox;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCUnlockBox;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.factory.PushServiceFactory;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.controller.BaseController;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IEquipmentRecord;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IRemoteDevice;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteDeviceEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.RemoteCtrlDiaryEntity;

@Controller
@RequestMapping(value="/remoteDevice")
public class RemoteDeviceController extends BaseController{
	
	private static String url = "tcp://127.0.0.1:55666";
	@Autowired
	IRemoteDevice device;
	@Autowired
	IEquipmentRecord iEquipmentRecord;
	
	/**
	 * 远程设备控制页面
	 * @return
	 */
	@RequestMapping("/view")
	public ModelAndView view() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/web/equipmentinfo/remoteDeviceMonitor");
		return mv;
	}
	@RequestMapping(value="/list",produces = {"text/html;charset=UTF-8;"})
	@ResponseBody
	public String list(RemoteDeviceEx tEntity,HttpServletRequest request) {
		int page = getParamInt(request, "page", 1);
		int rows = getParamInt(request, "rows", 10);
		Page<RemoteDeviceEx> terminalPage = new Page<RemoteDeviceEx>();
		terminalPage.setQueryParam(tEntity);
		terminalPage.setPageNo(page);
		terminalPage.setPageSize(rows);
		terminalPage=device.select(terminalPage);
		GridPage<RemoteDeviceEx> gridPage = new GridPage<RemoteDeviceEx>(terminalPage);
		String json = JsonUtils.toJSONNoFeatures(gridPage);
		System.out.println(json);
		return json;
	}
	/**
	 * 远程开箱
	 * @param terId
	 * @param boxId
	 * @throws IOException 
	 */
	@RequestMapping("/openBox")
	public void openBox(String terId,int boxId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
			IPushClient pushClient = PushServiceFactory.createInstanse();
			
			pushClient.setRecvTimeoutMills(5000);
			pushClient.connect();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
            InParamRCOpenBox inParams = new InParamRCOpenBox();
			
			inParams.setTerminalID(terId);
			inParams.setBoxArray(new int[]{boxId});
			inParams.setFlag(0);
			
			try {
				jcgCtrl.openBox(url, inParams);
				device.openBox(terId,boxId);
				System.out.println("开箱成功");
				jo.put("msg", "已开箱");
				RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
				rem.setAccountcode(acc1.getAccountcode());
				rem.setBoxid(boxId);
				rem.setContent("开箱");
				rem.setDate(d);
				rem.setTerminalid(terId);
				rem.setType(1);
				rem.setMemo("远程开箱");
				iEquipmentRecord.saveOrUpdate(rem);
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "开箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 远程锁定
	 * @param terId
	 * @param boxId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/lockBox")
	public void lockBox(String terId,int boxId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
            IPushClient pushClient = PushServiceFactory.createInstanse();
			
			pushClient.setRecvTimeoutMills(5000);
			pushClient.connect();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
			 
			InParamRCLockBox inParams = new InParamRCLockBox();
			inParams.setTerminalID(terId);
			inParams.setBoxID(boxId);
			
			try {
				jcgCtrl.lockBox(url, inParams);
				device.lockBox(terId,boxId);
				System.out.println("锁定成功");
				jo.put("msg", "已锁定");
				RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
				rem.setAccountcode(acc1.getAccountcode());
				rem.setBoxid(boxId);
				rem.setContent("锁定");
				rem.setDate(d);
				rem.setTerminalid(terId);
				rem.setType(3);
				rem.setMemo("远程锁定");
				iEquipmentRecord.saveOrUpdate(rem);
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "锁定失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 远程清箱
	 * @param terId
	 * @param boxId
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/clearBox")
	public void clearBox(String terId,int boxId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
            IPushClient pushClient = PushServiceFactory.createInstanse();			
			pushClient.setRecvTimeoutMills(5000);
			pushClient.connect();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
			InParamRCClearBox inParams = new InParamRCClearBox();
			inParams.setTerminalID(terId);
			inParams.setBoxID(boxId);
			jcgCtrl.clearBox(url, inParams);
			jo.put("msg", "清箱成功");
			System.out.println("注销成功");
			RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
			rem.setAccountcode(acc1.getAccountcode());
			rem.setBoxid(boxId);
			rem.setContent("清箱");
			rem.setDate(d);
			rem.setTerminalid(terId);
			rem.setType(2);
			rem.setMemo("远程清箱");
			iEquipmentRecord.saveOrUpdate(rem);
		} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
			e.printStackTrace();
			jo.put("msg", "清箱失败");
		}
		response.getWriter().print(jo);
	}
	/**
	 * 远程解锁
	 * @param terId
	 * @param boxId
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/unlockBox")
	public void unlockBox(String terId,int boxId,HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject jo = new JSONObject();
		AccountEntity acc1 = (AccountEntity) request.getSession().getAttribute("account");
		Date d = new Date();
		d.getTime();
		try {
            IPushClient pushClient = PushServiceFactory.createInstanse();
			
			pushClient.setRecvTimeoutMills(5000);
			pushClient.connect();
			IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
			 
			InParamRCUnlockBox inParams = new InParamRCUnlockBox();
			inParams.setTerminalID(terId);
			inParams.setBoxID(boxId);
			
			try {
				jcgCtrl.unlockBox(url, inParams);
				device.lockBox(terId,boxId);
				System.out.println("解锁成功");
				jo.put("msg", "已解锁");
				RemoteCtrlDiaryEntity rem =  new RemoteCtrlDiaryEntity();
				rem.setAccountcode(acc1.getAccountcode());
				rem.setBoxid(boxId);
				rem.setContent("解锁");
				rem.setDate(d);
				rem.setTerminalid(terId);
				rem.setType(4);
				rem.setMemo("远程解锁");
				iEquipmentRecord.saveOrUpdate(rem);
			} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "解锁失败");
		}
		response.getWriter().print(jo);
	}
}
