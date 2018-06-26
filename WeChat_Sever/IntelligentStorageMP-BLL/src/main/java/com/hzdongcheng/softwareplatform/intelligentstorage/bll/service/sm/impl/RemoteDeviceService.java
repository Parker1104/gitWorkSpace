package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.Page;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IRemoteDevice;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.RemoteDeviceEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.RemoteDeviceDao;

@Service
public class RemoteDeviceService implements IRemoteDevice {
    @Autowired
    RemoteDeviceDao dao;
	@Override
	public Page<RemoteDeviceEx> select(Page<RemoteDeviceEx> terminalPage) {
		List<RemoteDeviceEx> list = dao.getList(terminalPage);
		int count = dao.getCount(terminalPage);
		terminalPage.setResults(list);
		terminalPage.setTotalRecord(count);
		return terminalPage;
	}
	@Override
	public void openBox(String terId, int boxId) {
		dao.openBox(terId,boxId);
	}
	@Override
	public void lockBox(String terId, int boxId) {
	    dao.lockBox(terId,boxId);
	}
	@Override
	public void unlockBox(String terId, int boxId) {
	    dao.unlockBox(terId,boxId);
	}
}
