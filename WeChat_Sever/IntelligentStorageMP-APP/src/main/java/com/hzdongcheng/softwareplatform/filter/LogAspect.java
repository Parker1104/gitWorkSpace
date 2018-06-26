package com.hzdongcheng.softwareplatform.filter;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.softwareplatform.intelligentstorage.dal.dao.table.entity.AccountEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component("logUtilService")
public class LogAspect {
    private final static Log4jUtils logger = Log4jUtils.createInstanse(LogAspect.class);
    static {
        System.out.println("LogAspect 被加载了----------");
    }

    //@Before("execution(public * com.apay.utils.MsgUtils.sendMsg(..))")
    public void doAfterSendMsg(JoinPoint jp) {
        try {
            String phoneNo = (String) jp.getArgs()[0];
            String methodName = jp.getSignature().getName();
            String className = jp.getTarget().getClass().getSimpleName();
            String target = jp.getTarget().toString();
            System.out.println("phoneNo----------"+phoneNo);
            System.out.println("methodName----------"+methodName);
            System.out.println("className----------"+className);
            System.out.println("target----------"+target);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("异常："+e.getMessage());
        }
    }

    @Before("execution(public * com.hzdongcheng.softwareplatform.intelligentstorage.bll.service.impl.*Impl.add*(..))")
    public void doBeforeAdd(JoinPoint jp) {
        try {
            AccountEntity ae = (AccountEntity)CurrentSession.getSession();
            String operator = null==ae?"null":ae.getAccountname();
            Object obj = jp.getArgs()[0];
            String methodName = jp.getSignature().getName();
            String className = jp.getTarget().getClass().getSimpleName();
            String target = jp.getTarget().toString();
            logger.info("operator"+operator);
            logger.info("methodName----------" + methodName);
            logger.info("className----------" + className);
            logger.info("params----------"+obj.toString());
            logger.info("target----------" + target);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.debug("异常：" + e.getMessage());
        }
    }


}
