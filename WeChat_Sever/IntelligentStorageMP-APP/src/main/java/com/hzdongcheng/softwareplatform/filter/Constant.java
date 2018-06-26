package com.hzdongcheng.softwareplatform.filter;


/**
 * Created by xu 
 * 2017-01-06
 * 10:50
 * TO DO What
 */
public class Constant {

    public final static String STAFFINFO = "accountEntity";

    /**
     * 权限常量
     */
    public interface MANAGE_CONSTANTS {
        public final static String USER = "2";
        public final static String GROUP = "1";
        public final static String RESOURCE = "3";

    }

    /**
     * 事件常量
     */
    public interface EVENT_CONSTANTS{
        // 增加 删除 修改 查询 启用 禁用 分配权限
        public final static int ADD = 1;
        public final static int DELETE = 2;
        public final static int UPDATE = 3;
        public final static int FIND = 4;
        public final static int ENABLE = 5;
        public final static int DISABLE = 6;
        public final static int  ASSIGN= 7;
    }

    /**
     * 模块常量
     */
    public interface MODULE_CONSTANTS{
        public final static int TUSER = 1;
        public final static int ROLE = 2;
        public final static int MENU = 3;
        public final static int GROUP = 4;
        public final static int DICT = 5;
    }
}
