package com.hzdongcheng.softwareplatform.intelligentstorage.bll.constant;

import java.text.SimpleDateFormat;

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
public final class Constant {
    public static final SimpleDateFormat datetimeFromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 长字符串最大长度
     */
    public static final int LONGSTR_LEN = 40960; //40K
    public static final int CONTENT_MAX_LEN = 3900;
    /**
     * Operator
     */
    public static final String DEFAULT_SUPEROPERID = "181818"; //超级管理员编号
    public static final String DEFAULT_QUERYOPERID = "!@#4rfv*&^()"; //内部查询用
    /**
     * Operator type
     */
   public static final int OPER_TYPE_SYS = 1; //系统管理员
    /**
     * ModuleID
     */
    public static final String MODULE_ID_OP = "13"; //操作员管理
    /**
     * 用户状态
     */
    public static final String USER_STATUS_NORMAL = "0"; //正常
    public static final String USER_STATUS_LOGOUT = "1"; //注销(审核未通过)
    public static final String USER_STATUS_NOTACTIVATION = "2"; //注册未激活
 

 

 
 
 
}
