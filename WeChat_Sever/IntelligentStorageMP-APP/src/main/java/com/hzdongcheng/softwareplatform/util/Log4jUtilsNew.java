package com.hzdongcheng.softwareplatform.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
 
public class Log4jUtilsNew {
	private Logger logger = null;

	protected Log4jUtilsNew(){
	}
	static {
		try{
			//读取jar包内配置文件
			
			//InputStream is = Log4jUtils.class.getClass().getResource("/log/log4j.properties").openStream();
			//File file = File.createTempFile("log4j", ".properties");
			//FileOutputStream out = new FileOutputStream(file);
			//
			//int i;
			//byte[] buf = new byte[1024];
			//while ((i = is.read(buf)) != -1) {
			//	out.write(buf, 0, i);
			//}
			//
			//is.close();
			//out.close();
			//file.deleteOnExit();
			//读取工作目录下配置文件
			//PropertyConfigurator.configure(System.getProperty("user.dir") + "/conf/log4j.properties");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Log4jUtilsNew createInstanse(Class<?> name){

		Log4jUtilsNew log4j = new Log4jUtilsNew();
		log4j.logger = LogManager.getLogger(name);
		return log4j;
	}

	public void debug(String errorString) {
		if (logger != null && logger.isDebugEnabled())
			logger.debug(errorString);
	}

	public void info(String errorString) {
		if (logger != null && logger.isInfoEnabled())
			logger.info(errorString);
	}

	public void error(String errorString) {
		if (logger != null)
			logger.error(errorString);
	}

	public void error(String errorString, Throwable e) {
		if (logger != null)
			logger.error(errorString, e);
	}

	public void warn(String errorString) {
		if (logger != null)
			logger.warn(errorString);
	}
}
