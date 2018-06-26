package com.hzdongcheng.softwareplatform.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.mapper.CardTransRuleExDao;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.BusinessModelEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntity;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.CardTransRuleEntityExample;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.mapper.BusinessModelEntityDao;
@Service
public class CardNumberConversion {
	
	@Autowired
	BusinessModelEntityDao business;
	@Autowired
	CardTransRuleExDao cardTransRuleExDao;
	public  CardNumberConversion() {
		
	}
	public String getNewCardID(String terminalid,String openboxcode)
	{
		BusinessModelEntity businessmode0 = business.selectByTerminaidz(terminalid);//转换规则	
		//查看卡号 	的转换	
		CardTransRuleEntityExample example1 = new CardTransRuleEntityExample();
		example1.setDistinct(true);
		example1.createCriteria().andTransrulecodeEqualTo(Integer.parseInt(businessmode0.getConfigvalue()));
		List<CardTransRuleEntity> cardTransRuleEntity = cardTransRuleExDao.selectByExample(example1);
		
		if(cardTransRuleEntity != null) {		
			//截取字符串
			openboxcode = openboxcode.substring(cardTransRuleEntity.get(0).getStartsubstr()); 
			//倒序
			if (cardTransRuleEntity.get(0).getCardrule() != 1) {
				
				if (StringUtils.isNotEmpty(openboxcode)){
					if ( openboxcode.length() % 2 != 0 ){
						openboxcode = "0" + openboxcode;
					}
				}
				
				StringBuffer out = new StringBuffer();
				String tmpOpenCode = "";
				for (int i = 0; i < openboxcode.length() / 2; i++) {
					tmpOpenCode = out.insert(0, openboxcode.substring(i * 2, i * 2 + 2)).toString();
				}
				
				openboxcode = tmpOpenCode;
			}
			
			if(cardTransRuleEntity.get(0).getDecimalism() == 1){
				//十进制
				openboxcode = Integer.valueOf(openboxcode,16).toString();
			}
			
			while (openboxcode.length() < cardTransRuleEntity.get(0).getCardlen()) {
				openboxcode = "0" + openboxcode;
			}
			
			return openboxcode;
		}
		return "";
	}

}
