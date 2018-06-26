package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import com.hzdongcheng.front.server.model.service.InParam;
import com.hzdongcheng.front.server.model.service.OutParam;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamDKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamTakeOutRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInDRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutRequest;

public interface IZGHService {
	//取物请求
	OutParamStoreInDRequest storeRequsetNew(InParamStoreInRequest outRequest);
	
	
	
	//设备签到
	OutParamSignIn sign(InParamSignIn inParam);
	
	//心跳
	OutParamKeepAlive keepAlive(InParamKeepAlive inAlive);
 
	//心跳
	OutParamKeepAlive keepDAlive(InParamDKeepAlive inAlive);
 
	
	//存入请求
	OutParamStoreInRequest storeRequset(InParamStoreInRequest inRequest);
	
	//存入确认
	OutParamStoreInConfirm storeIn(InParamStoreInConfirm inConfirm);
	
	 
	
	//取物请求
	OutParamTakeOutRequest takeRequest(InParamTakeOutRequest outRequest);
	
	//取物确认
	OutParamTakeOutConfirm takeOut(InParamTakeOutConfirm takeOutConfirm);
	
	OutParam disconnect(InParam ps);
}
