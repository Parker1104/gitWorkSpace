package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.network.exception.MessageRecvTimeOutException;
import com.hzdongcheng.components.network.exception.MessageSendException;
import com.hzdongcheng.components.network.exception.NotFoundNetClientException;
import com.hzdongcheng.front.server.model.service.jcg.down.InParamRCOpenBox;
import com.hzdongcheng.front.server.push.IPushClient;
import com.hzdongcheng.front.server.push.product.jcg.IJCGRemoteCtrl;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.PushClientFactory;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOpenBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BoxEntityDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.TerminalEntityDao;
@Service
public class OpenBox implements IOpenBox{
	private static String url = "tcp://127.0.0.1:55666";
	@Autowired
	private TerminalEntityDao terminalEntityDao;
	@Autowired
	private BoxEntityDao boxEntityDao;

	@Override
	public void openBoxByName(String displayName, String dispalyName) {
		//根据显示柜名称  和 箱名称查询终端编号   和     箱门号
		TerminalEntityExample  example = new TerminalEntityExample();
		example.createCriteria().andDisplaynameEqualTo(displayName);
		List<TerminalEntity> terminalEntity = terminalEntityDao.selectByExample(example);
		if(terminalEntity.size()>0){
			BoxEntityExample Example = new BoxEntityExample();
			Example.createCriteria().andTerminalidEqualTo(terminalEntity.get(0).getTerminalid()).andDispalynameEqualTo(dispalyName);
			List<BoxEntity> boxEntity = boxEntityDao.selectByExample(Example);
			if(boxEntity.size()>0){
				try {
					IPushClient pushClient = PushClientFactory.getInstance();
					IJCGRemoteCtrl jcgCtrl = pushClient.queryInterface(IJCGRemoteCtrl.class);
					InParamRCOpenBox inParams = new InParamRCOpenBox();
					inParams.setTerminalID(terminalEntity.get(0).getTerminalid());
					int[] boxarray = {boxEntity.get(0).getBoxid()};
					inParams.setBoxArray(boxarray);
					inParams.setFlag(0);
					try {			            	
						jcgCtrl.openBox(url, inParams);
					} catch (MessageSendException | MessageRecvTimeOutException | NotFoundNetClientException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
