package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;

public class EduExceptions extends RuntimeException
{

    public EduExceptions(String msg)
    {
        super(msg);
    }

    private static final long serialVersionUID = -3674625526599986057L;
    public static final String TIP_MSG = "System Internal Error,please contact your system administrator";
    public static final String EXCP_PARMERR = "-1";
    public static final String EXCP_NORECORD = "-2";
    public static final String EXCP_SQLERR = "-3";
    public static final String EXCP_ORACLERESOURCEBUSY = "-4";
    public static final String EXCP_GETCURRENTDATEFAIL = "-5";
    public static final String EXCP_GETCURRENTTIMEFAIL = "-6";
    public static final String EXCP_GETSEQUENCEERR = "-7";
    public static final String EXCP_OPERATEDBERROR = "-8";
    public static final String EXCP_EXECUTEPROCFAIL = "-9";
    public static final String EXCP_GETCONNECTIONFAIL = "-10";
    public static final String EXCP_TRAVERSEROWSETFAIL = "-11";
    public static final String EXCP_GETTEXTDATAFAIL = "-12";
    public static final String EXCP_GETBINDATAFAIL = "-13";
    public static final String EXCP_BACKUPFAIL = "-20";
    public static final String EXCP_RECOVERFAIL = "-21";
    public static final String EXCP_CONVERTXMLDATA = "-51";
    public static final String EXCP_NOTSUPPORTDATATYPE = "-52";
    public static final String EXCP_NOTEXISTCOLUMN = "-53";
}