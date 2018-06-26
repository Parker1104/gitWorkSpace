package com.hzdongcheng.softwareplatform.intelligentstorage.bll.config;

/*import com.dcdzsoft.sda.db.DataSourceUtils;

*/
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company:  杭州东城电子有限公司</p>
 
 */
public class ApplicationConfig {
    /**
     * database config
     */
    //private DataSourceUtils dbCfg = DataSourceUtils.getInstance();

    //错误消息提示语言
    private String locale = "";


    //系统运行模式
    private int sysRunModel = 1; //1:转发 2:运营 3:转发运营

    //接口包名
    private String interfacePackage = ""; //

    //业务处理线程的数量
    private int workerCount = 100; //业务处理线程的数量

    //记录自己平台的消息日志
    private boolean logRawMsg = false;

    //记录运营商消息的日志
    private boolean logMbMsg = false;
    
    //测试需要发送短信否
    private String sendShortMsg = ""; //MsgProxyDcdzsoft,MsgProxyFangzhengkuandai,MsgProxyShanghaiyz
    private String gatewayUser = "";
    private String gatewayPwd = "";
    private String smsServerIp = "";
    private String smsServerPort = "";
    private String smsCharset = "";//短信编码
    private String smsMobilePrefix = "";//手机号前缀，用户发送国家短信
    
    //运营商服务器IP
    private String mbHost = "";
    //运营商服务器Port
    private String mbPort = "";
    //运营商服务器URI
    private String mbUri = "";

    private String ftpHost = "";
    private String ftpPort = "";
    private String ftpUser = "";
    private String ftpPasswd = "";
    
    //合作方服务器(国通、菜鸟)
    private boolean sentToGuotong = false;
    private boolean newGuotongApi = false;
    private boolean uploadToPartner = false;//订单状态是否上传到合作方系统
    private boolean registerToPartner = false;//柜体是否需要注册到合作方系统（国通、菜鸟等）
    private String dbsAipsIp = "";
    private String dbsAipsPort = "";
    
    /**
     * memcache config
     */
    private boolean memCache = false;
    private String servers = "";
    private String weights = "";
    private int initConn = 0;
    private int minConn  = 0;
    private int maxConn  = 0;
    private int maxIdle  = 0;
    
    private int lockerSessionIdle = 0;
    
    //合作方接口
    private String outProxy = "";
    private String apiStringWs = "";
    private String apiSysIdWs = "";
    private String apiKeyWs = "";
    private String apiToken = "";

    /**
     * webapps的真实路径
     */
    private String physicalPath = "";

    private String terminalLogPath = "terminalLog";
    private String fullTerminalLogPath;
    private String fullTerminalLogTmpPath;

    private String picFilePath = "pic";
    private String fullPicFilePath = "";
    /**
     * 私有默认构造函数
     */
    private ApplicationConfig() {

    }

    private static class SingletonHolder {
        private static final ApplicationConfig instance = new ApplicationConfig();
    }


    /**
     * 静态工厂方法，返还此类的惟一实例
     * @return a ApplicationConfig instance
     */
    public static ApplicationConfig getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 读取配置文件
     * @param fileName String
     * @throws ConfigurationException
     * @throws IOException
     */
    public void load(String fileName)  {
        try{
        	  String mcPrefix = "mcconfig.";

              XMLConfiguration config = new XMLConfiguration(fileName);
              config.setEncoding("utf-8"); //设置编码
              config.setDefaultListDelimiter('~');
       
              //mcconfig
              this.setMemCache(config.getBoolean(mcPrefix+"memCache"));
              this.setServers(config.getString(mcPrefix+"servers"));
              this.setWeights(config.getString(mcPrefix+"weights"));
              this.setInitConn(config.getInt(mcPrefix+"initConn"));
              this.setMinConn(config.getInt(mcPrefix+"minConn"));
              this.setMaxConn(config.getInt(mcPrefix+"maxConn"));
              this.setMaxIdle(config.getInt(mcPrefix+"maxIdle"));
              this.setLockerSessionIdle(config.getInt(mcPrefix+"lockerSessionIdle"));
        }catch(Exception ex ){
        	ex.printStackTrace();
        }
 
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public String getLocale() {
        return locale;
    }

    public int getSysRunModel(){
        return sysRunModel;
    }

    public void setSysRunModel(int sysRunModel)
    {
        this.sysRunModel = sysRunModel;
    }

    public int getWorkerCount(){
    	return workerCount;
    }

    public void setWorkerCount(int workerCount)
    {
    	this.workerCount = workerCount;
    }

    public String getFullTerminalLogPath() {
        return this.getPhysicalPath() + this.terminalLogPath;
    }

    public String getFullTerminalLogTmpPath() {
        return getFullTerminalLogPath() + "/temp";
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }

    public void setTerminalLogPath(String terminalLogPath) {
        this.terminalLogPath = terminalLogPath;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setInterfacePackage(String value)
    {
    	this.interfacePackage = value;
    }

    public String getInterfacePackage()
    {
    	return interfacePackage;
    }

    public void setMbHost(String value)
    {
        this.mbHost = value;
    }

    public String getMbHost()
    {
        return mbHost;
    }

    public void setMbPort(String value)
    {
        this.mbPort = value;
    }

    public String getMbPort()
    {
        return mbPort;
    }

    public void setMbUri(String value)
    {
        this.mbUri = value;
    }

    public void setLogRawMsg(boolean logRawMsg) {
        this.logRawMsg = logRawMsg;
    }

    public void setLogMbMsg(boolean logMbMsg) {
        this.logMbMsg = logMbMsg;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public void setFtpPasswd(String ftpPasswd) {
        this.ftpPasswd = ftpPasswd;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public void setFullTerminalLogPath(String fullTerminalLogPath) {
        this.fullTerminalLogPath = fullTerminalLogPath;
    }

    public void setFullTerminalLogTmpPath(String fullTerminalLogTmpPath) {
        this.fullTerminalLogTmpPath = fullTerminalLogTmpPath;
    }

    public String getMbUri()
    {
        return mbUri;
    }

    public boolean isLogRawMsg() {
        return logRawMsg;
    }

    public boolean isLogMbMsg() {
        return logMbMsg;
    }
    
    public boolean isSentToGutong() {
        return sentToGuotong;
    }

    public void setSentToGuotong(boolean sentToGuotong) {
        this.sentToGuotong = sentToGuotong;
    }
    
    public void setDbsAipsIp(String dbsAipsIp) {
        this.dbsAipsIp = dbsAipsIp;
    }
    
    public String getDbsAipsIp() {
        return dbsAipsIp;
    }
    
    public void setDbsAipsPort(String dbsAipsPort) {
        this.dbsAipsPort = dbsAipsPort;
    }
    
    public String getDbsAipsPort() {
        return dbsAipsPort;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public String getFtpPasswd() {
        return ftpPasswd;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public String getTerminalLogPath() {
        return terminalLogPath;
    }
    
    public String getSendShortMsg() {
        return sendShortMsg;
    }
    
    public void setSendShortMsg(String sendShortMsg ) {
        this.sendShortMsg = sendShortMsg;
    }
    
    public String getSmsServerIp() {
        return smsServerIp;
    }
    
    public void setSmsServerIp(String smsServerIp ) {
        this.smsServerIp = smsServerIp;
    }
    
    public String getSmsServerPort() {
        return smsServerPort;
    }
    
    public void setSmsServerPort(String smsServerPort ) {
        this.smsServerPort = smsServerPort;
    }
    
    public String getGatewayUser() {
        return gatewayUser;
    }
    
    public void setGatewayUser(String gatewayUser ) {
        this.gatewayUser = gatewayUser;
    }
    
    public String getGatewayPwd() {
        return gatewayPwd;
    }
    
    public void setGatewayPwd(String gatewayPwd ) {
        this.gatewayPwd = gatewayPwd;
    }

    public String getSmsCharset() {
        return smsCharset;
    }

    public void setSmsCharset(String smsCharset) {
        this.smsCharset = smsCharset;
    }

    public boolean isNewGuotongApi() {
        return newGuotongApi;
    }

    public void setNewGuotongApi(boolean newGuotongApi) {
        this.newGuotongApi = newGuotongApi;
    }

    public String getFullPicFilePath() {
        this.fullPicFilePath = this.getPhysicalPath()+this.picFilePath;
        return fullPicFilePath;
    }

    public void setFullPicFilePath(String fullPicFilePath) {
        this.fullPicFilePath = fullPicFilePath;
    }

    public String getPicFilePath() {
        return picFilePath;
    }

    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }

    public String getOutProxy() {
        return outProxy;
    }

    public void setOutProxy(String outProxy) {
        this.outProxy = outProxy;
    }

    public String getApiStringWs() {
        return apiStringWs;
    }

    public void setApiStringWs(String apiStringWs) {
        this.apiStringWs = apiStringWs;
    }

    public String getApiSysIdWs() {
        return apiSysIdWs;
    }

    public void setApiSysIdWs(String apiSysIdWs) {
        this.apiSysIdWs = apiSysIdWs;
    }

    public String getApiKeyWs() {
        return apiKeyWs;
    }

    public void setApiKeyWs(String apiKeyWs) {
        this.apiKeyWs = apiKeyWs;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
    public boolean isRegisterToPartner() {
        return registerToPartner;
    }

    public void setRegisterToPartner(boolean registerToPartner) {
        this.registerToPartner = registerToPartner;
    }

    public boolean isUploadToPartner() {
        return uploadToPartner;
    }

    public void setUploadToPartner(boolean uploadToPartner) {
        this.uploadToPartner = uploadToPartner;
    }

    public String getSmsMobilePrefix() {
        return smsMobilePrefix;
    }

    public void setSmsMobilePrefix(String smsMobilePrefix) {
        this.smsMobilePrefix = smsMobilePrefix;
    }

	public boolean isMemCache() {
		return memCache;
	}

	public void setMemCache(boolean memCache) {
		this.memCache = memCache;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

	public int getInitConn() {
		return initConn;
	}

	public void setInitConn(int initConn) {
		this.initConn = initConn;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getLockerSessionIdle() {
		return lockerSessionIdle;
	}

	public void setLockerSessionIdle(int lockerSessionIdle) {
		this.lockerSessionIdle = lockerSessionIdle;
	}

}
