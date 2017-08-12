package com.pemng.serviceSystem.base.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;

import com.pemng.serviceSystem.base.exception.BusinessException;



public class ExceptionLoggingInterceptor implements AfterReturningAdvice,ThrowsAdvice {

	protected Log logger=LogFactory.getLog(ExceptionLoggingInterceptor.class);
	
	
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if (!logger.isDebugEnabled())  return;
		StringBuffer infoMessage = new StringBuffer();
		infoMessage.append("\nReturn the result when after executing ").append(target.getClass()).append(".").append(method.getName()).append("(") ;
		if(args!=null && args.length>1){
		    for(int i =0 ,len=args.length;i<len;i++){
		    	if(i <len-1){
		    		if(args[i]!=null){
		    			infoMessage.append("[").append(args[i].getClass()).append(" : ").append(args[i]).append("],");
		    		}
		    		else{
		    			infoMessage.append("[").append(args[i]).append(" : ").append(args[i]).append("],");
		    		}
		    	}else{
		    		if(args[i]!=null){
		    			infoMessage.append("[").append(args[i].getClass()).append(" : ").append(args[i]).append("]");
		    		}
		    		else{
		    			infoMessage.append("[").append(args[i]).append(" : ").append(args[i]).append("]");
		    		}
		    	}
		    	
		    		
		    }
		}
		else if(args!=null && args.length == 1){
			if(args[0]!=null){
    			infoMessage.append("[").append(args[0].getClass()).append(" : ").append(args[0]).append("]");
    		}
    		else{
    			infoMessage.append("[").append(args[0]).append(" : ").append(args[0]).append("]");
    		}
		}
		infoMessage.append(")");
		logger.debug(infoMessage);
		
	}
     
	public void afterThrowing(Method method,Object[] args,Object target,Exception e){
		StringBuffer errorMessage = new StringBuffer("\nERROR IN :").append(target.getClass()).append(".").append(method.getName()).append("()");
		errorMessage.append("\nCAUSE BY:").append(e.getCause());
		StackTraceElement[] trace = e.getStackTrace();
		if(trace!=null){
			for(int i=0; i<trace.length; i++){
				errorMessage.append("\n\t").append(trace[i].toString());
			}
		}
		logger.error(errorMessage);
	}
	
	public void afterThrowing(Method method,Object[] args,Object target,BusinessException e){
		
		StringBuffer errorMessage = new StringBuffer();
		errorMessage.append("\nBUSINESS_ERROR IN :").append(target.getClass()).append(".").append(method.getName()).append("()");
		errorMessage.append("\nERROR MESSAGE :").append(e.getMessage());
		errorMessage.append("\nCAUSE BY :").append(e.getCause());
		StackTraceElement[] trace = e.getStackTrace();
		if(trace!=null){
			for(int i=0; i<trace.length; i++){
				errorMessage.append("\n\t").append(trace[i].toString());
			}
		}
		logger.error(errorMessage);
		
	}
}
