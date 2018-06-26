package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalserviceweixin;

import com.hzdongcheng.softwareplatform.intelligentstorage.bll.appPublicDto.PublicInWXCheckTermianlNo;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.util.EduExceptions;

public interface IAppPublicService {
	
	public String doBusiness(PublicInWXCheckTermianlNo in)  throws EduExceptions ;

	
}
