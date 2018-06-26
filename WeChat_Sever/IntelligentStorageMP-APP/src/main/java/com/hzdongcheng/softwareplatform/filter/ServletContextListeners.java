package com.hzdongcheng.softwareplatform.filter;
 
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

import com.dcdzsoft.config.ErrorMsgConfig;
//import com.hzdongcheng.components.memcached.memcached.MemCachedImpl;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.softwareplatform.controller.terminal.AppApiExternalBusiness;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.config.ApplicationConfig;
import com.hzdongcheng.softwareplatform.intelligentstorage.bll.memcached.MemCacheManager;

public class ServletContextListeners implements ServletContextListener {
	//private final static Logger logger = Logger.getLogger(ServletContextListeners.class);
	public static Log4jUtils logger = Log4jUtils.createInstanse(ServletContextListeners.class);
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	   /**
			ServletContext context = arg0.getServletContext();
	        String realPath = context.getRealPath("/") + "/";
	        String logPath = realPath + "log"; //设置log属性值
	        System.setProperty("log.home", logPath);
	        org.apache.log4j.PropertyConfigurator.configure(logPath + "/log4j.properties");
			logger.info("  ServletContextListeners contextInitialized ");
		*/
		//inites(arg0 ) ;
	}
 
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void inites(ServletContextEvent arg0 )  {
		
	    ServletContext context = arg0.getServletContext();
        String realPath = context.getRealPath("/");
        if(!realPath.endsWith("/"))
        	realPath = realPath + "/";
        
        String fileName = realPath + "WEB-INF/appconfig.xml";
        logger.info("   ServletContextListeners  fileName   "+fileName);
        //加载错误信息提示文件
        String errorfileName = realPath + "WEB-INF/locale/errorMsg_zh";
        //System.out.println("      errorfileName================"+errorfileName);
        try {
			ErrorMsgConfig.load(errorfileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        
        
        /*
        ApplicationConfig apConfig = ApplicationConfig.getInstance();
        //设置真实的物理路径
        apConfig.setPhysicalPath(realPath);     
        apConfig.load(fileName);
 
        MemCachedImpl  memcached = new MemCachedImpl();
        memcached.setInitConn(apConfig.getInitConn());
        memcached.setMaxConn(apConfig.getMaxConn());
        memcached.setMaxIdle(apConfig.getMaxIdle());
        memcached.setMinConn(apConfig.getMinConn());
        memcached.setServers(apConfig.getServers().split(","));
        memcached.initialize();
		MemCacheManager.getInstance().setMemcachedService(memcached ); 	*/
 
    
		
		
	}
	
	
	
	
}
