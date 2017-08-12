package com.pemng.serviceSystem.base.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

public class ExcecuteLoggingAdvisor  {
	private Logger logger;
    
    
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
    	
    	long t1 = System.currentTimeMillis();
    	
    	Object retVal = pjp.proceed();
    	
    	
    	long t2 = System.currentTimeMillis(); 
    	
    	logger.info("Method execute use " + (t2-t1)/1000 + "." + ((t2-t1)%1000) + "s [" 
    			+ (pjp.getSignature().toLongString())
    			+ " ] ");
    	
    	return retVal;
    	
    }


	public Logger getLogger() {
		return logger;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
//	public void before(Method   method,   Object[]   args,   Object   target)throws Throwable{   
//		if (!logger.isDebugEnabled())  return;
//		StringBuffer infoMessage = new StringBuffer();
//		infoMessage.append("\nSVW_BKM_SYSTEM_INFO : Begin execute ").append(target.getClass()).append(".").append(method.getName()).append("(") ;
//		if(args!=null && args.length>1){
//			
//		    for(int i =0 ,len=args.length;i<len;i++){
//		    	if(i <len-1){
//		    		if(args[i]!=null){
//		    			infoMessage.append("[").append(args[i].getClass()).append(" : ").append(args[i]).append("],");
//		    		}
//		    		else{
//		    			infoMessage.append("[").append(args[i]).append(" : ").append(args[i]).append("],");
//		    		}
//		    	}else{
//		    		if(args[i]!=null){
//		    			infoMessage.append("[").append(args[i].getClass()).append(" : ").append(args[i]).append("]");
//		    		}
//		    		else{
//		    			infoMessage.append("[").append(args[i]).append(" : ").append(args[i]).append("]");
//		    		}
//		    	}
//		    		
//		    }
//		}
//		else if(args!=null && args.length == 1){
//			if(args[0]!=null){
//    			infoMessage.append("[").append(args[0].getClass()).append(" : ").append(args[0]).append("]");
//    		}
//    		else{
//    			infoMessage.append("[").append(args[0]).append(" : ").append(args[0]).append("]");
//    		}
//			
//		}
//		infoMessage.append(")");
//		logger.debug(infoMessage);
//		
//    }

    
    
}
