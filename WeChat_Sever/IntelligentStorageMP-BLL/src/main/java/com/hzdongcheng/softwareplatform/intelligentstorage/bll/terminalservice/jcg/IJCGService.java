package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import java.util.List;

import com.hzdongcheng.front.server.model.service.InParam;
import com.hzdongcheng.front.server.model.service.OutParam;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamAlarmNoticesRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInCheckRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirmByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirmOfMidway;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamAlarmNoticesRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamOpenBoxByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInCheckRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirmByManager;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirmOfMidway;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;

public interface IJCGService {
	
	//设备签到
	OutParamSignIn sign(InParamSignIn inParam);
	
	//心跳
	OutParamKeepAlive keepAlive(InParamKeepAlive inAlive);
	
	//报警
	OutParamAlarmNoticesRequest alarmNotices(InParamAlarmNoticesRequest alarmNotices);
	
	
	
	
	//存入校验请求
	OutParamStoreInCheckRequest storeCheckRequset(InParamStoreInCheckRequest inRequest);
	
	//存入请求
	OutParamStoreInRequest storeRequset(InParamStoreInRequest inRequest);
	
	//存入确认
	OutParamStoreInConfirm storeIn(InParamStoreInConfirm inConfirm);
	
	//取物请求
	OutParamTakeOutRequest takeRequest(InParamTakeOutRequest outRequest);
	
	//取物确认
	OutParamTakeOutConfirm takeOut(InParamTakeOutConfirm takeOutConfirm);
	
	//报警
	void warningNotice();
	
	//查询在用箱列表
	List<BoxEntity> queryInBoxList(InParam inParam);
	
	//查询违规箱列表
	List<BoxEntity> queryViolationBoxList(InParam inParam);
    
	//中途取物确认
	OutParamTakeOutConfirmOfMidway takeOutOfMidway(InParamTakeOutConfirmOfMidway ptoc);
    
	//管理取确认
	OutParamTakeOutConfirmByManager takeOutConfirmByManager(InParamTakeOutConfirmByManager inParam);
	
	//管理开箱
    OutParamOpenBoxByManager openBoxByManager(InParamTakeOutConfirmByManager ps);

	OutParam disconnect(InParam ps);
}
