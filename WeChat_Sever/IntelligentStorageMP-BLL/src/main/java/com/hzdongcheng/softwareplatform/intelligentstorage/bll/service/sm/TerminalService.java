package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm;

import java.util.List;

import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.TerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;

public interface TerminalService {

	void save(TerminalEntity te);

	List<TerminalEx> findAll(TerminalEntityExample example);

	void delete(String id);

	TerminalEntity select(String terminalid);

	void update(TerminalEntity te);

	long count(TerminalEntityExample example);

	List<TerminalEx> selectAllBycode(Integer code);

	TerminalEntity selectByDisplayname(String displayname);
	
	String InsertTerminal(TerminalEntity te,BoxEntity box, AccountEntity acc1);
	
	
    String InsertTerminal(TerminalEx te, BoxEntity box, AccountEntity account) ;
	
	
	
}
