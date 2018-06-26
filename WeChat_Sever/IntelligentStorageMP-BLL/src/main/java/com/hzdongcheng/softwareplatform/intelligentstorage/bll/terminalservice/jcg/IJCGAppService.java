package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.StoreInRecordEntity;

/**
 * @author WenHeJu
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @ClassName:  IJCGQueryService
 * @date 2017年7月13日 上午8:38:07
 * 
 */
public interface IJCGAppService {
	
	//存入请求
	String storeRequset(String userCardID,String userName,String className, String displayName);
	
	//取物请求
	String takeRequest(String userCardID,String userName,String className, String displayName);
	
	//根据卡号查询在箱记录
	List<StoreInRecordEntity> selectStoreInRecord(String openBoxCode);
	
	//根据设备名称查询空箱信息
    List<BoxEntity>	selectFreeOpenBoxTerminalID(String displayName);
    
    //根据名称查询
    List<BoxEntity> selectEmptyBoxByAreaCode(String areaCode);
}
