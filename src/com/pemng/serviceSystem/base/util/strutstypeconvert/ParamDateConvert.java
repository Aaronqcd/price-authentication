/**    
 * description: 
 * Create on Oct 15, 2011 5:05:38 PM    
 *      
 */    
package com.pemng.serviceSystem.base.util.strutstypeconvert;    

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;
   
/**    
 * 日期转换
 * @author luohanbin</a>   
 * @version 1.0    
 */

public class ParamDateConvert extends StrutsTypeConverter {
	
    private static final String FORMATDATE = "yyyy-MM-dd";   
    private static final String FORMATTIME = "yyyy-MM-dd HH:mm:ss";  

	public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values == null || values.length == 0) {    
            return null;    
        }   
        //有时分秒的要先转换   
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATTIME);    
        Date date = null;      
        String dateString = values[0];   
        if (dateString != null) {
            try {
                date = sdf.parse(dateString);      
            } catch (ParseException e) {    
                date = null;      
            }
            if(date == null)
            {
                sdf = new SimpleDateFormat(FORMATDATE);   
                try {
                    date = sdf.parse(dateString);      
                } catch (ParseException e) {      
                    date = null;      
                }   
            }   
        }   
        return date;   
	}

	public String convertToString(Map context, Object o) {
        if (o instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMATTIME);    
            return sdf.format((Date)o);   
        }    
        return "";  
	}

}
   