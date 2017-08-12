/**
 * 
 */
package com.pemng.serviceSystem.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author luohanbin
 * 
 */
public class ApplicationContextHolder implements ApplicationContextAware {
	public static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ApplicationContextHolder.applicationContext = applicationContext;
		
	}
}
