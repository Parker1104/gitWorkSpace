package com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteDeviceEx;

public interface RemoteDeviceDao {

	List<RemoteDeviceEx> getList(Page<RemoteDeviceEx> terminalPage);

	int getCount(Page<RemoteDeviceEx> terminalPage);
    @Update("update box set open=0 where terminalid=#{terId} and boxid=#{boxId}")
	void openBox(@Param("terId")String terId,@Param("boxId")int boxId);
    
    @Update("update box set status=1 where terminalid=#{terId} and boxid=#{boxId}")
	void lockBox(@Param("terId")String terId, @Param("boxId")int boxId);
	
    @Update("update box set status=0 where terminalid=#{terId} and boxid=#{boxId}")
	void unlockBox(@Param("terId")String terId, @Param("boxId")int boxId);
}
