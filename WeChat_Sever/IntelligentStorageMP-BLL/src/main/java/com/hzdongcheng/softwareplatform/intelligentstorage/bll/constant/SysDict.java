package com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public final class SysDict {
	
 
    public final static String CHARGE_TYPE_HOURS   = "0"; //按小时
    public final static String CHARGE_TYPE_DAYS    = "1"; //按天
    public final static String CHARGE_TYPE_TIME_INTERVAL   = "2"; // 按时段
	
    //支付标志
    public final static String PAY_Status_0 = "0"; //0 未支付
    public final static String PAY_Status_1 = "1"; //1 预支付成功
    public final static String PAY_Status_2 = "2"; //2 取支付成功
    
    //开箱标志
    public final static String OPEN_Status_0 = "0"; //0 存开箱成功
    public final static String OPEN_Status_1 = "1"; //1 存开箱失败
    public final static String OPEN_Status_2 = "2"; //2 取开箱成功
    public final static String OPEN_Status_3 = "3"; //3 取开箱失败
/*    
    public final static String PAY_FLAG_NO = "0"; //不需要支付
    public final static String PAY_FLAG_YES = "1"; //需要支付
    public final static String PAY_FLAG_SUCCESS = "2"; //支付成功
*/	
    
    //15055-会员类型
    public final static String CUSTOMER_TYPE_NROMAL  = "0"; //普通客户
    public final static String CUSTOMER_TYPE_APP     = "10"; //APP用户
    public final static String CUSTOMER_TYPE_WEIXIN  = "11"; //微信用户
    public final static String CUSTOMER_TYPE_POSTMAN = "99"; //投递人员
    
    
    
/*    //41003 格口收费类型
    public final static String CHARGE_TYPE_USAGE   = "1"; //按次收费
    public final static String CHARGE_TYPE_HOURS   = "4"; //按小时
    public final static String CHARGE_TYPE_DAYS    = "5"; //按天
    public final static String CHARGE_TYPE_TIME_INTERVAL   = "6"; // 按时段
    public final static String CHARGE_TYPE_MONTHS  = "7"; //按月
    public final static String CHARGE_TYPE_QUQRTER = "8"; //按季
    public final static String CHARGE_TYPE_YEARS   = "9"; //按年
*/

        //柜体状态
    public final static String TERMINAL_STATUS_NORMAL = "0"; //正常
    public final static String TERMINAL_STATUS_LOCKED = "1"; //锁定
    public final static String TERMINAL_STATUS_FAULT = "2"; //故障
    public final static String TERMINAL_STATUS_FAULTLOCKED = "3"; //故障锁定
    public final static String TERMINAL_STATUS_FAULT_POWERFAIL = "4"; //掉电故障Power-fail
    public final static String TERMINAL_STATUS_CANCEL          = "5"; //5-注销cancel
    public final static String TERMINAL_STATUS_FAULT_OFFLINE   = "6"; //离线故障Off-line
    public final static String TERMINAL_STATUS_FAULT_BOX       = "7"; //存在箱门故障
    public final static String TERMINAL_STATUS_UNKNOWN = "9"; //未知

    //15051-个人客户状态
    public final static Byte CUSTOMER_STATUS_NROMAL   = 0; //正常
    public final static Byte CUSTOMER_STATUS_CANCEL   = 1; //注销
    public final static Byte CUSTOMER_STATUS_UNACTIVE = 2; //注册未激活
}
