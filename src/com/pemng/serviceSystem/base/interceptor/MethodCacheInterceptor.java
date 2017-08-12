/*
 * Created on 2006-4-20
 * Copyright 2006－2006 vekee . All rights reserved. 
 */
package com.pemng.serviceSystem.base.interceptor;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;




public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {
	protected final static Log logger = LogFactory.getLog(MethodCacheInterceptor.class);
	
	private Cache cache;

	/**
	 * 
	 * sets cache name to be used
	 * 
	 */

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 * 
	 */

	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		Object[] arguments = invocation.getArguments();
		Object result;
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		if(logger.isInfoEnabled()){
			logger.info("/n========ehcache测试:"+ cacheKey);	
		}
		Element element = cache.get(cacheKey);
		if (element == null) {

			// call target/sub-interceptor

			result = invocation.proceed();
			// cache method result
			element = new Element(cacheKey, (Serializable) result);
			cache.put(element);
		}
//System.out.println("/n/n======calculate========:"+ cache.calculateInMemorySize()+":disk:"+cache.getDiskStoreSize()+":memory:"+cache.getMemoryStoreSize()+"::"+cache.getSize());		
		return element.getValue();
	}

	/**
	 * 
	 * creates cache key: targetName.methodName.argument0.argument1...
	 * 
	 */

	private String getCacheKey(String targetName, String methodName, Object[] arguments) {

		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 * 
	 */

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

    public static void main(String[] args) {
//		ApplicationContext applicationContext = 
//			  new ClassPathXmlApplicationContext("classpath:/applicationcontext.xml");
//        AreaDAO hw = (AreaDAO) applicationContext.getBean("areaDAO");
//       System.out.println(hw.findById("abc"));
//       //net.sf.hibernate.
//       //org.ehcache.hibernate.Provider 原来的
//// net.sf.ehcache.hibernate.Provider  配置文件内容
//  //     net/sf/hibernate/cache/CacheProvider
////    EhCacheProvider
//       System.out.println(hw.findById("abc"));
    }
}
