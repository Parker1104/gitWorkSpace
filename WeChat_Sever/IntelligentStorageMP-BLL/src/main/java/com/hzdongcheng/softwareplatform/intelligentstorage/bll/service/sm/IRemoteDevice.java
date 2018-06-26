package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteDeviceEx;

public interface IRemoteDevice {

	Page<RemoteDeviceEx> select(Page<RemoteDeviceEx> terminalPage);

	void openBox(String terId, int boxId);

	void lockBox(String terId, int boxId);

	void unlockBox(String terId, int boxId);
	
}
