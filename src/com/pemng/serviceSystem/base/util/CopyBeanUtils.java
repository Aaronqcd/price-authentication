package com.pemng.serviceSystem.base.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

/**
 * Created on Jun 6, 2012
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 * spring BeanUtils
 */
public class CopyBeanUtils extends BeanUtils{
	/**
	 * Function Name               copyPropsWithoutNull                                   
	 * @param                      dest	 目标
	 * @param                      orig   源
	 * @description                copyProperties 如果是null  则不copy  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 6, 2012             louzhixiong         Initial
	 **********************************************************************
	 */
	public static void copyPropsWithoutNull(Object target, Object source){
	    Class<?> actualEditable = target.getClass();  
	    PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);  
	    for (PropertyDescriptor targetPd : targetPds) {  
	      if (targetPd.getWriteMethod() != null) {  
	        PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());  
	        if (sourcePd != null && sourcePd.getReadMethod() != null) {  
	          try {  
	            Method readMethod = sourcePd.getReadMethod();  
	            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {  
	              readMethod.setAccessible(true);  
	            }  
	            Object value = readMethod.invoke(source);  
	            // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等  
	            if (value != null) {  
	              Method writeMethod = targetPd.getWriteMethod();  
	              if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {  
	                writeMethod.setAccessible(true);  
	              }  
	              writeMethod.invoke(target, value);  
	            }  
	          } catch (Throwable ex) {  
	            throw new FatalBeanException("Could not copy properties from source to target", ex);  
	          }  
	        }  
	      }  
	    }  
	}
}
