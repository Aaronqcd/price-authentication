package com.pemng.serviceSystem.base.test;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring Context Util
 * @author YangMinsheng 2008-08-19
 */
public class SpringContextUtil implements ApplicationContextAware {
    
    private static ApplicationContext context = null;

    /**
     * 对于junit,不在WEB容器中运行，此时ApplicationContext必须直接重从applicationContext*.xml获得
     * @return
     */
    public synchronized static ApplicationContext initApplicationContextFromFile() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("resources\\spring\\applicationContext.xml");
        }
        return context;
    }

    /**
     * 获取对象。使用于两种情况：
     * 1. 从WEB容器中获得；
     * 2. 如果没有启动容器，则直接从applicationContext*.xml,此时适用于JUNIT和JAVA应用程序。
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     */
    public static Object getBean(String name) throws BeansException {
        initApplicationContextFromFile();
        return context.getBean(name);
    }   
    
    
	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * @param applicationContext
	 * @throws BeansException
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
	    context = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}

}
