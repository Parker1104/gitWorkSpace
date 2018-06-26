package com.hzdongcheng.softwareplatform.filter;

import org.springframework.beans.BeansException;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {  
  
    private static ApplicationContext applicationContext;  
      
    public static Object getBean(String beanName){  
        return applicationContext.getBean(beanName);          
    }  
      
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class cla){
        return (T)applicationContext.getBean(cla);
    }  
  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        SpringUtil.applicationContext=applicationContext;
    } 
}
