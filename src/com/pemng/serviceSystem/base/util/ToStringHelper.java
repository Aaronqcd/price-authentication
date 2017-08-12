/**    
 * description: 
 * Create on Oct 15, 2011 5:54:33 PM    
 *      
 */    
package com.pemng.serviceSystem.base.util;    

import java.lang.reflect.Field;
   
/**    
 * @author luohanbin</a>   
 * @version 1.0    
 */

public class ToStringHelper {
	
	
	public static String toString(Object o){
		if(o == null)
			return "";
		
		try{
			StringBuffer buf = new StringBuffer(o.getClass().getName() + "\n[");
			Field[] fields = o.getClass().getDeclaredFields();
			for(int i=0; i<fields.length; i++){
				Field f = fields[i];
				String fieldName = f.getName();
				Class c = f.getType();
				Object val = BeanUtils.forceGetProperty(o, fieldName);
				if(val != null){
					buf.append("	" + fieldName + "[" +val.getClass().getName() +  "]  = " + val + "\n");
				}
			}
			
			buf.append("]");
			
			return buf.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
}
   