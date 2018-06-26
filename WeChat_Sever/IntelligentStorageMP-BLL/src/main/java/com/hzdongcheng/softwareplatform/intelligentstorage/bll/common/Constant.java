package com.hzdongcheng.softwareplatform.intelligentstorage.bll.common;

public class Constant {
	/**
	 * Session User key
	 */
	public static final String SessionID="_Dcdz_Session_User_Key";
	
	/**
	 * Session UserInRole key
	 */
	public static final String SessionRoleID="_Dcdz_Session_Role_Key";
	
	/**
	 * 国际化时，menu前缀
	 */
	public static final String MenuCodePre="menu_";
	
	/**
	 * 用户和字典定义
	 */
	public static final String SessionAccoutID="account";
	
	public static final String DICT_MAMAGER = "13000";
	
	public static final String DICT_CARDTYPE = "13100";
	
	public static final String DICT_PAYMENTMASTERID = "13200";
	
	public static final String DICT_USEDSTATE = "13300";
	
	
	/**
	 * 卡片类型
	 */
	//用户
	public static final int CARD_TYPE_USER  = 4;
	//清除 故障
	public static final int CARD_TYPE_CLEAR = 1;
	//清洁卡
	public static final int CARD_TYPE_MANAGER_CLEAN = 2;
	//空闲箱门 开箱
	public static final int CARD_TYPE_MANAGER_FREE = 3;
	//管理者
	public static final int CARD_TYPE_MANAGER_ALL = 0;
	//超时 开箱
	public static final int CARD_TYPE_MANAGER_TIMEOUT = 6;
	//全开卡
	public static final int CARD_TYPE_MANAGER_FULLYOPEN = 5;
	/**
	 * business model config name
	 */
	//身份验证标识
	public static final String BT_CFG_NAME_CHECKIDENTITY="ValidateIdentities";
	public static final String BT_CFG_NAME_CHECKIDENTITY_ON = "1";
	public static final String BT_CFG_NAME_CHECKIDENTITY_OFF = "0";
	//一卡一箱标识
	public static final String BT_CFG_NAME_ONECARDONEBOX="OneCardOneBox";
	public static final String BT_CFG_NAME_ONECARDONEBOX_ON="1";
	public static final String BT_CFG_NAME_ONECARDONEBOX_OFF="0";
	//运营时段
	public static final String BT_CFG_NAME_RUNTIME="RunTime";
	//收费功能
	public static final String BT_CFG_NAME_CHARGESWITCH="ChargeFuncSwitch";
	public static final String BT_CFG_NAME_CHARGESWITCH_ON="1";
	public static final String BT_CFG_NAME_CHARGESWITCH_OFF="0";	
	//收费模式
	public static final String BT_CFG_NAME_CHARGEMODE = "ChargeMode";
	//违规存包限制标识
	public static final String BT_CFG_NAME_VINOLATIONSWITCH="LimitSwitch";
	public static final String BT_CFG_NAME_VINOLATIONSWITCH_ON="1";
	public static final String BT_CFG_NAME_VINOLATIONSWITCH_OFF="0";
	//违规解除类型
	public static final String BT_CFG_NAME_VINOLATIONRELIEVETYPE="Limitcondition";
	public static final String BT_CFG_NAME_VINOLATIONRELIEVETYPE_AUTO="1";
	public static final String BT_CFG_NAME_VINOLATIONRELIEVETYPE_MANAGE="2";
	//限制存取包  天  小时
	public static final String BT_CFG_NAME_PUNISHTYPE="Timeout";
	public static final String BT_CFG_NAME_PUNISHTYPE_DAY="Timeout_text_2";
	public static final String BT_CFG_NAME_PUNISHTYPE_MULTIPLE="Timeout_text_3";
	//超时自动开箱
	public static final String BT_CFG_NAME_OPENBOXWHENTIMEOUT="ViolationsLimitCheckbox";
	public static final String BT_CFG_NAME_OPENBOXWHENTIMEOUT_ON="1";
	public static final String BT_CFG_NAME_OPENBOXWHENTIMEOUT_OFF="0";
	public static final String BT_CFG_NAME_OPENBOXWHENTIMEOUT_HOURS="ViolationsLimit_1";
	public static final String BT_CFG_NAME_OPENBOXWHENTIMEOUT_TIME="ViolationsLimit_2";
	//卡片转换规则
	public static final String BT_CFG_NAME_CARDTRANSRULE="Rule";
	//免费使用时间
	public static final String BT_CFG_NAME_FREETIME="FreeTime";
	//两次存物间隔时间
	public static final String BT_CFG_NAME_STOREININTERVALTIME="TimeBetween";
	//取物未关箱门惩罚时间（天）
	public static final String BT_CFG_NAME_NOTCLOSEDOORPUNISHDAY="NoneDoorLimit";
	//提醒功能
	public static final String BT_CFG_NAME_RENINDED="PromptTime";
	/**
	 * 
	 * 提醒方式
	 * 
	 */
	//微信
	public static final String BT_CFG_NAME_WECHAT ="WeChatCheckbox";
	public static final String BT_CFG_NAME_WECHAT_OFF = "0";
	public static final String BT_CFG_NAME_WECHAT_ON = "1";
	//邮箱
	public static final String BT_CFG_NAME_EMAIL = "EmailCheckbox";
	public static final String BT_CFG_NAME_EMAIL_OFF = "0";
	public static final String BT_CFG_NAME_EMAIL_ON = "1";
	//短信
	public static final String BT_CFG_NAME_SMS = "SMSCheckbox";
	public static final String BT_CFG_NAME_SMS_OFF = "0";
	public static final String BT_CFG_NAME_SMS_ON = "1";
	
	/**
	 * 业务
	 * 信息的事件类型
	 * 
	 */
	//1、存包提醒
	public static final String BT_CFG_NAME_INFORMATIONREMIND="InformationRemind";
	public static final String BT_CFG_NAME_INFORMATIONREMIND_ON="0";
	public static final String BT_CFG_NAME_INFORMATIONREMIND_OFF="1";
	//2、到期前1小时提醒
	public static final String BT_CFG_NAME_DUETOBEFORE="DueToBefore";
	public static final String BT_CFG_NAME_DUETOBEFORE_ON="0";
	public static final String BT_CFG_NAME_DUETOBEFORE_OFF="1";
	//3、到期后1小时提醒
	public static final String BT_CFG_NAME_DUETOAFTER="DueToAfter";
	public static final String BT_CFG_NAME_DUETOAFTER_ON="0";
	public static final String BT_CFG_NAME_DUETOAFTER_OFF="1";
	//4、清箱提醒
	public static final String BT_CFG_NAME_CLEARBOXREMIND="ClearBoxRemind";
	public static final String BT_CFG_NAME_CLEARBOXREMIND_ON="0";
	public static final String BT_CFG_NAME_CLEARBOXREMIND_OFF="1";
	//5、超期存包处罚提醒
	public static final String BT_CFG_NAME_TIMEOUTSAVEPACKAGE="TimeoutSavePackage";
	public static final String BT_CFG_NAME_TIMEOUTSAVEPACKAGE_ON="0";
	public static final String BT_CFG_NAME_TIMEOUTSAVEPACKAGE_OFF="1";
	//6、未关箱门处罚提醒
	public static final String BT_CFG_NAME_NOTCLOSEBOXDOOR="NotCloseBoxDoor";
	public static final String BT_CFG_NAME_NOTCLOSEBOXDOOR_ON="0";
	public static final String BT_CFG_NAME_NOTCLOSEBOXDOOR_OFF="1";
	/**
	 * 
	 * 信息提醒功能
	 * 
	 */
	//1.存包提醒
	public static final String INFORMATION_REMIND_SAVE ="IdName同学：您已于SavePacKageTime在CaseInformation存物，存物时间最长longestTime小时，请于endOfTime前结束存物。";
	//2.到期前1小时提醒
	public static final String INFORMATION_REMIND_DUETOBEFORE ="IdName同学：您在CaseInformation号箱的存物将于DueToTheTime到期，请尽快结束存物，超期后系统将按照图书馆存包柜使用规则进行处罚：存物超时，以N倍 超时时长禁用您的存物权限；超时达H小时后系统将自动开箱清理，其它读者也有权清空并使用该箱。";
	//3.到期后1小时提醒
	public static final String INFORMATION_REMIND_DUETOAFTER ="IdName同学：您在CaseInformation号箱的存物已超期，请尽快取回物品。系统将按照图书馆存包柜使用规则进行处罚：存物超时将以N倍  超时时长禁用您的存物权限；超时达H小时后系统将自动开箱清理，其它读者有权清空并使用该箱。";
	//4.清箱提醒
	public static final String INFORMATION_REMIND_CLEARBOXREMIND ="证件名称同学：您在箱包信息号箱的存物已超过？？？小时（根据清箱时长规则确定），当日闭馆前系统平台将自动开箱并清理，请您尽快取回物品，避免丢失。";
	//5.超期处罚
	public static final String INFORMATION_REMIND_TIMEOUTSAVEPACKAGE ="IdName同学：您在CaseInformation号箱的存物超期时长为AreLongOverdue小时，系统平台将按照图书馆存包柜使用规则以N 超时时长禁用您的存物权限，从 Timeout 至 endOfTime 时间内禁止您的存包权限。";
	//6.不关闭箱门
	public static final String INFORMATION_REMIND_NOTCLOSEBOXDOOR ="IdName同学，您在CaseInformation号箱进行取物操作后，没有关闭箱门，系统将按照图书馆存包柜使用规则进行处罚，Day天内取消您的存包权限。从 Timeout 至  endOfTime 时间内禁止您存包。";
	//超时提醒
	public static final String INFORMATION_REMIND_TIMEOUTPROMPT = "IdName同学：您在CaseInformation号箱的存物已超期请尽快取出.否则系统将在Time点自动打开箱门。如有丢失后果自负";
	//超时提醒前一个小时  Pre-timeout prompt
	public static final String INFORMATION_REMIND_PRETIMEOUTPROMPT = "IdName同学：您在CaseInformation号箱的存物还剩Time小时箱门自动打开。清尽快取出";
	
}