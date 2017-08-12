package com.pemng.serviceSystem.base.util;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

public class DateConvert implements Converter{
	public static final String[] DATE_FORMAT = {
		"yyyy-MM-dd HH:mm:ss",
		"yyyy-MM-dd",
		"yyyy/MM/dd HH:mm:ss",
		"yyyy/MM/dd",
		"yyyyMMdd"
	};

	public Object convert(Class arg0, Object arg1) {
    	
    	if(arg0.isInstance(arg1)){
    		return arg1;
    	}
    	try{

            String p = (String)arg1;
            if(p== null || p.trim().length()==0){   
                return null;   
            }
            for(int i=0; i<DATE_FORMAT.length;i++){

                try{   
                    SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT[i]);   
                    return df.parse(p.trim());   
                }   
                catch(Exception e){   
                    
                }  
            } 
    	}catch(Exception e){
    		
    	}
        return null;
    }
}
