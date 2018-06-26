package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import com.hzdongcheng.front.server.model.service.InParam;
import com.hzdongcheng.front.server.model.service.OutParam;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamSignIn;
//import com.hzdongcheng.front.server.model.service.jcg.up.InParamType;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamKeepAlive;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamSignIn;
//import com.hzdongcheng.front.server.model.service.jcg.up.OutParamType;

public interface IPULEService {
 
	
	//设备签到
	OutParamSignIn sign(InParamSignIn inParam);
	
	// 垃圾箱    1:二维码 2称重  3:箱满提示
	//OutParamType typeIn(InParamType inParam);
	
	//心跳
	OutParamKeepAlive keepAlive(InParamKeepAlive inAlive);
 
	OutParam disconnect(InParam ps);
}
