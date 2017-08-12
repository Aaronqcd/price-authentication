package com.pemng.serviceSystem.base.util;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class AppContainerUtils {
	
	/**
	 * 静态方式获取Spring BeanFactory
	 * @param servletContext
	 * @return
	 */
	public static BeanFactory getBeanFactory(ServletContext servletContext) {
		return getWebApplicationContext(servletContext);
	}
	
	/**
	 *  静态方式获取Spring WebApplicationContext
	 * @param servletContext
	 * @return
	 */
	public static WebApplicationContext getWebApplicationContext(ServletContext servletContext) {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}
	
	/**
	 * 静态方式获取Spring 托管 Bean
	 * @param servletContext
	 * @param name
	 * @return
	 */
	public static Object getBean(ServletContext servletContext,String name){
		return getBeanFactory(servletContext).getBean(name);
	}
}
