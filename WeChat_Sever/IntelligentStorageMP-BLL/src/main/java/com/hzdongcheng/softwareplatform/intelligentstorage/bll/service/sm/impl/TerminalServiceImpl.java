package com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IAreaService;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.IOperationLogInpquire;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.sm.TerminalService;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.TerminalEx;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.BoxExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.TerminalExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AreaEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BoxEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.OperatorDiaryEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.TerminalEntityExample;

@Service
@Repository
public class TerminalServiceImpl implements TerminalService {

	@Autowired
	private TerminalExDao terminalExDao;
	@Autowired
	private TerminalService impl;
	@Autowired
	private IAreaService iArea;
	@Autowired
	private IOperationLogInpquire iOperation;
	@Autowired
	private BoxExDao boxExDao;

	@Override
	public void save(TerminalEntity te) {
		terminalExDao.insertSelective(te);
	}

	public List<TerminalEx> findAll(TerminalEntityExample example) {
		return terminalExDao.selectByExampleEx(example);
	}

	public void delete(String id) {
		terminalExDao.deleteByPrimaryKey(id);
	}

	@Override
	public TerminalEntity select(String terminalid) {
		return terminalExDao.selectByPrimaryKey(terminalid);
	}

	@Override
	public void update(TerminalEntity te) {
		terminalExDao.updateByPrimaryKeySelective(te);
	}

	@Override
	public long count(TerminalEntityExample example) {
		return terminalExDao.countByExample(example);
	}

	@Override
	public List<TerminalEx> selectAllBycode(Integer code) {
		// TODO Auto-generated method stub
		return terminalExDao.selectAllBycode(code);
	}

	@Override
	public TerminalEntity selectByDisplayname(String displayname) {
		// TODO Auto-generated method stub
		return terminalExDao.selectByDisplayname(displayname);
	}

	
	@Override
	@Transactional
	public String InsertTerminal(TerminalEx te, BoxEntity box, AccountEntity account) {
		// TODO Auto-generated method stub
		Date date =new Date();
		date.getTime();
		te.setNetworkstate((byte)0);
		te.setMakedate(date);
		te.setLastmodifytime(date);
		String terminalid = te.getTerminalid();
		TerminalEntity tEntity = impl.select(terminalid);


		String id = te.getTerminalid();
		String areacode = te.getAreacode();
		AreaEntity area = iArea.get(areacode);
		String shortname = area.getAreashortname();
		int sum = te.getTotalboxnumber();
		int dispaly = 0;

		if(te.getBoxnumberswitch() == 0){
			dispaly = Integer.parseInt(te.getBoxcode());
			//获取最小和最大的显示箱号并到数据库里判断是否已存在
			String sShowStartBoxNo = "";
			String sShowEndBoxNo = "";
			sShowStartBoxNo = te.getBoxcode();	
			sShowEndBoxNo = String.valueOf((Integer.parseInt(te.getBoxcode())+sum-1));
			while (sShowStartBoxNo.length()<4){
				sShowStartBoxNo = "0" + sShowStartBoxNo;
			}
			while (sShowEndBoxNo.length()<4){
				sShowEndBoxNo = "0" + sShowEndBoxNo;
			}
			//如果按区域显示的话在前面加上区域简称
			if (te.getNumbering() != 0)
			{
				sShowStartBoxNo = shortname + sShowStartBoxNo;
				sShowEndBoxNo = shortname + sShowEndBoxNo;
			}
			List<BoxEntity> boxEntity = null;
			List<BoxEntity> boxEx = null;
			//0 是默认   1是区域
			if (te.getNumbering() != 0)
			{
				//数据库判断是否存在
				BoxEntityExample example = new BoxEntityExample();
				example.createCriteria().andDispalynameBetween(sShowStartBoxNo, sShowEndBoxNo);
				boxEntity = boxExDao.selectByExample(example);
				if(boxEntity.size() < 1){
					boxEntity = null;
				}
			}else {
				//数据库判断是否存在
				BoxEntityExample Example = new BoxEntityExample();
				Example.createCriteria().andDispalynameBetween(te.getDisplayname()+"-"+sShowStartBoxNo, te.getDisplayname()+"-"+sShowEndBoxNo);
				boxEx = boxExDao.selectByExample(Example);
				if(boxEx.size() < 1){
					boxEx = null;
				}
			}
			if(boxEntity != null || boxEx != null){
				return "已有箱编号  请重新输入";      
			}else {
				try {
					if (StringUtils.isEmpty(tEntity)) {
						te.setNetworkstate((byte)0);
						te.setRegisterflag("0");
						TerminalEntity terminalEntity = new TerminalEntity();
						
						
						ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
						BeanUtils.copyProperties(terminalEntity,te); //TODO
						
						
						//BeanUtils.copyProperties(te,terminalEntity);
						terminalExDao.insertSelective(terminalEntity);

						//判断通过修改当前柜中箱门的显示箱号
						for (int i = 0; i < sum; i++) {
							box.setTerminalid(id);
							box.setBoxid(i+1);
							box.setArticle((byte)0);
							box.setOpen((byte)0);
							box.setStatus((byte)0);
							box.setFixedbox((byte)0);
							box.setOneboxmorecard((byte)0);
							box.setBoxtypecode(1);

							String sShowBoxNo = String.valueOf((dispaly +i));
							while (sShowBoxNo.length()<4){
								sShowBoxNo = "0" + sShowBoxNo;
							}
							//根据区域时添加区域简称
							if(te.getNumbering() != 0){
								sShowBoxNo = shortname + sShowBoxNo;
								box.setDispalyname(sShowBoxNo);
							}else {
								box.setDispalyname(te.getDisplayname()+"-"+sShowBoxNo);
							}						
							boxExDao.insert(box);	
						}	

					/*	OperatorDiaryEntity operator = new OperatorDiaryEntity();
						operator.setAccountcode(account.getAccountcode());
						operator.setDate(date);
						operator.setDescription("新增设备信息");
						operator.setModlename("设备管理");
						operator.setMemo("新增设备信息");
						iOperation.saveOrUpdate(operator);*/
					}else {

						te.setNetworkstate((byte)0);
						te.setRegisterflag("0");
						te.setRunstatus((byte)0);
						TerminalEntity terminalEntity = new TerminalEntity();
						
						//te.setDate(new Date().toLocaleString());
						
						ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
						BeanUtils.copyProperties(terminalEntity,te); //TODO
						terminalExDao.updateByPrimaryKeySelective(terminalEntity);

						for (int i = 0; i < sum; i++) {
							box.setTerminalid(id);
							box.setBoxid(i+1);

							String sShowBoxNo = String.valueOf((dispaly + i));
							while (sShowBoxNo.length()<4){
								sShowBoxNo = "0" + sShowBoxNo;
							}
							//根据区域时添加区域简称
							if(te.getNumbering() != 0){
								sShowBoxNo = shortname + sShowBoxNo;
								box.setDispalyname(sShowBoxNo);
							}else {
								box.setDispalyname(te.getDisplayname()+"-"+sShowBoxNo);
							}	

							boxExDao.updateByPrimaryKeySelective(box);
						}
						/*OperatorDiaryEntity operator = new OperatorDiaryEntity();
						operator.setAccountcode(account.getAccountcode());
						operator.setDate(date);
						operator.setDescription("修改设备信息");
						operator.setModlename("设备管理");
						operator.setMemo("修改设备信息");
						iOperation.saveOrUpdate(operator);*/
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "操作成功";
			}	
		}else {
			try {
				if (StringUtils.isEmpty(tEntity)) {
					if(te.getRunstatus() != null){
					  te.setRunstatus(te.getRunstatus());
					}else {
					  te.setRunstatus((byte)0);
					}
					if(te.getNetworkstate() != null){
						te.setNetworkstate(te.getNetworkstate());
					}else {
						te.setNetworkstate((byte)0);
					}
					if(te.getRegisterflag() != null){
						te.setRegisterflag(te.getRegisterflag());
					}else {
						te.setRegisterflag("0");
					}
					TerminalEntity terminalEntity = new TerminalEntity();
					
					
					ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
					BeanUtils.copyProperties(terminalEntity,te); //TODO
					//BeanUtils.copyProperties(te,terminalEntity);
	 
					terminalExDao.insertSelective(terminalEntity);
					return "新增成功";
				}else {
					if(te.getRunstatus() != null){
					    te.setRunstatus(te.getRunstatus());
					}else {
					    te.setRunstatus((byte)0);
					}
					if(te.getNetworkstate() != null){
						te.setNetworkstate(te.getNetworkstate());
					}else {
						te.setNetworkstate((byte)0);
					}
					if(te.getRegisterflag() != null){
						te.setRegisterflag(te.getRegisterflag());
					}else {
						te.setRegisterflag("0");
					}
					TerminalEntity terminalEntity = new TerminalEntity();
					
					ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
					BeanUtils.copyProperties(terminalEntity,te); //TODO
					
					//BeanUtils.copyProperties(te,terminalEntity);
					terminalExDao.updateByPrimaryKeySelective(terminalEntity);
					return "修改成功";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return "操作成功";
		}
	}
	
	
	
	
	
	@Override
	@Transactional
	public String InsertTerminal(TerminalEntity te,BoxEntity box, AccountEntity account) {
		// TODO Auto-generated method stub
		Date date =new Date();
		date.getTime();
		te.setNetworkstate((byte)0);
		te.setMakedate(date);
		te.setLastmodifytime(date);
		String terminalid = te.getTerminalid();
		TerminalEntity tEntity = impl.select(terminalid);


		String id = te.getTerminalid();
		String areacode = te.getAreacode();
		AreaEntity area = iArea.get(areacode);
		String shortname = area.getAreashortname();
		int sum = te.getTotalboxnumber();
		
		String Boxcode=StringUtils.isEmpty(te.getBoxcode())?"0":te.getBoxcode();
		int dispaly = Integer.parseInt(Boxcode);	
		//int dispaly = Integer.parseInt(StringUtils.isEmpty(te.getBoxcode())?"0":te.getBoxcode());	
		

		//获取最小和最大的显示箱号并到数据库里判断是否已存在
		String sShowStartBoxNo = "";
		String sShowEndBoxNo = "";
		sShowStartBoxNo = te.getBoxcode();	
		sShowEndBoxNo = String.valueOf((Integer.parseInt(Boxcode)+sum-1));
		while (sShowStartBoxNo.length()<4){
			sShowStartBoxNo = "0" + sShowStartBoxNo;
		}
		while (sShowEndBoxNo.length()<4){
			sShowEndBoxNo = "0" + sShowEndBoxNo;
		}
		//System.out.println("----te.getNumbering()------"+te.getNumbering());
		//如果按区域显示的话在前面加上区域简称
		if (!StringUtils.isEmpty(te.getNumbering())  && te.getNumbering() != 0)
		{
			sShowStartBoxNo = shortname + sShowStartBoxNo;
			sShowEndBoxNo = shortname + sShowEndBoxNo;
		}
		List<BoxEntity> boxEntity = null;
		List<BoxEntity> boxEx = null;
		if (!StringUtils.isEmpty(te.getNumbering())  && te.getNumbering() != 0)
		{
			//数据库判断是否存在
			BoxEntityExample example = new BoxEntityExample();
			example.createCriteria().andDispalynameBetween(sShowStartBoxNo, sShowEndBoxNo);
			boxEntity = boxExDao.selectByExample(example);
			if(boxEntity.size() < 1){
				boxEntity = null;
			}
		}else {
			//数据库判断是否存在
			BoxEntityExample Example = new BoxEntityExample();
			Example.createCriteria().andDispalynameBetween(te.getDisplayname()+"-"+sShowStartBoxNo, te.getDisplayname()+"-"+sShowEndBoxNo);
			boxEx = boxExDao.selectByExample(Example);
			if(boxEx.size() < 1){
				boxEx = null;
			}
		}
		if(boxEntity != null || boxEx != null){
            return "已有编号  请重新输入";
		}else {
			try {
				if (StringUtils.isEmpty(tEntity)) {
					te.setNetworkstate((byte)0);
					te.setRegisterflag("0");
					impl.save(te);

					//判断通过修改当前柜中箱门的显示箱号
					for (int i = 0; i < sum; i++) {
						box.setTerminalid(id);
						box.setBoxid(i+1);
						box.setArticle((byte)0);
						box.setOpen((byte)0);
						box.setStatus((byte)0);
						box.setFixedbox((byte)0);
						box.setOneboxmorecard((byte)0);
						box.setBoxtypecode(1);

						String sShowBoxNo = String.valueOf((dispaly +i));
						while (sShowBoxNo.length()<4){
							sShowBoxNo = "0" + sShowBoxNo;
						}
						//根据区域时添加区域简称
						if(te.getNumbering() != 0){
							sShowBoxNo = shortname + sShowBoxNo;
							box.setDispalyname(sShowBoxNo);
						}else {
							box.setDispalyname(te.getDisplayname()+"-"+sShowBoxNo);
						}						
						boxExDao.insert(box);	
					}	

					/*OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(account.getAccountcode());
					operator.setDate(date);
					operator.setDescription("新增设备信息");
					operator.setModlename("设备管理");
					operator.setMemo("新增设备信息");
					iOperation.saveOrUpdate(operator);*/
				}else {
					
					te.setNetworkstate((byte)0);
					te.setRegisterflag("0");
					te.setRunstatus((byte)0);
					impl.update(te);

					for (int i = 0; i < sum; i++) {
						box.setTerminalid(id);
						box.setBoxid(i+1);

						String sShowBoxNo = String.valueOf((dispaly + i));
						while (sShowBoxNo.length()<4){
							sShowBoxNo = "0" + sShowBoxNo;
						}
						//根据区域时添加区域简称
						if(!StringUtils.isEmpty(te.getNumbering())  &&  te.getNumbering() != 0){
							sShowBoxNo = shortname + sShowBoxNo;
							box.setDispalyname(sShowBoxNo);
						}else {
							box.setDispalyname(te.getDisplayname()+"-"+sShowBoxNo);
						}	
						
						boxExDao.updateByPrimaryKeySelective(box);
					}
				/*	OperatorDiaryEntity operator = new OperatorDiaryEntity();
					operator.setAccountcode(account.getAccountcode());
					operator.setDate(date);
					operator.setDescription("修改设备信息");
					operator.setModlename("设备管理");
					operator.setMemo("修改设备信息");
					iOperation.saveOrUpdate(operator);*/
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "操作成功";
		}
	}
}
