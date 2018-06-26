package com.hzdongcheng.softwareplatform.intelligentstorage.bll.terminalservice.jcg;

import com.hzdongcheng.front.server.model.service.jcg.up.InParamBackCardAndBox;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBoxIDCardLossRequest;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamBoxIDChangeCardIDConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamCardIDCheckOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamCardIDLogInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUpdatePassword;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUserCheckOutbyDay;
import com.hzdongcheng.front.server.model.service.jcg.up.InParamUserLoginCheck;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamBackCardAndBox;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamStoreInConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamTakeOutConfirm;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamUserCheckOutbyDay;
import com.hzdongcheng.front.server.model.service.jcg.up.OutParamUserLoginCheck;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.BackCardAndBox;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.OutParamBoxIDCardLossRequest;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.custom.entity.StoreInRecordByCard;

public interface ICashierService {
	
	//根据卡号返回卡箱对应信息
	BackCardAndBox CardIDLogInRequest(InParamBackCardAndBox inCardAndBox);
    //用户卡登记
    OutParamStoreInConfirm CardIDLogInConfirm(InParamCardIDLogInConfirm inConfirm);
    //管理开箱
	//OpenBoxByManager openBoxByManager(InParamBackCardAndBox ps);
	//根据卡号获取存物信息
	StoreInRecordByCard cardIDCheckOutRequest(InParamBackCardAndBox ps);
	//用户卡结账
	OutParamTakeOutConfirm cardIDCheckOutConfirm(InParamCardIDCheckOutConfirm ps);
	//柜端用户登录
	OutParamUserLoginCheck userLogin(InParamUserLoginCheck ps);
	//根据箱门号获取存物信息
	OutParamBoxIDCardLossRequest boxIDCardLossRequest(InParamBoxIDCardLossRequest ps);
	//挂失授权
	OutParamBackCardAndBox boxIDCardLossAuthorize(InParamBackCardAndBox ps);
	//用户卡挂失
	OutParamBackCardAndBox boxIDCardLossConfirm(InParamBackCardAndBox ps);
	//用户补卡
	OutParamBackCardAndBox boxIDChangeCardIDConfirm(InParamBoxIDChangeCardIDConfirm ps);
	//收银员结账
	OutParamUserCheckOutbyDay userCheckOutbyDay(InParamUserCheckOutbyDay ps);
	//柜端修改密码
	OutParamStoreInConfirm updatePassword(InParamUpdatePassword ps);
	//直接补卡
	OutParamBoxIDCardLossRequest boxIDCardChangeRequest(InParamBoxIDCardLossRequest ps);
}
