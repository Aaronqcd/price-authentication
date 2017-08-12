package com.pemng.serviceSystem.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateFormatterZh {

	 public static String dateToCN(Date date) {      
	        if (null == date || "".equals(date)) {      
	            return "";      
	        }      
	        String[] CN = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };    
	        String str = "十";   
	           
//	        String[] CN = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};    
//	        String str = "拾";   
	           
	        Calendar calendar = Calendar.getInstance();      
	        calendar.setTime(date);      
	           
	        StringBuffer cn = new StringBuffer();      
	           
	        String year = String.valueOf(calendar.get(Calendar.YEAR));      
	          
	        for (int i = 0; i < year.length(); i++) {      
	            cn.append(CN[year.charAt(i) - 48]);      
	        }      
	          
	        cn.append("年");      
	          
	        int t1,t2;   
	           
	        int mon = calendar.get(Calendar.MONTH) + 1;      
	           
	        t1 = mon/10;   
	        t2 = mon%10;   
	           
	        if(t1 < 10){   
	            if(t1 != 0){   
	            	if(t1 != 1) 
	            		cn.append(CN[t1]);   
	                cn.append(str);   
	            }  
	        }   
	           
	        if(t2 < 10 && t2 != 0){   
	            cn.append(CN[t2]);   
	        }   
	           
	        cn.append("月");      
	          
	        int day = calendar.get(Calendar.DAY_OF_MONTH);      
	           
	        t1 = day/10;   
	        t2 = day%10;   
	           
	        if(t1 < 10){   
	            if(t1 != 0){   
	            	if(t1 != 1)
	            		cn.append(CN[t1]);   
	                cn.append(str);   
	            }  
	        }   
	           
	        if(t2 < 10 && t2 != 0){   
	            cn.append(CN[t2]);   
	        }   
	           
	        cn.append("日");      
	          
	        return cn.toString();      
	    }  
	 
	 public static String getDate(String fmt , Date d) {
		 if(d == null || StringUtils.isBlank(fmt))
			 return "";
		 DateFormat df = new SimpleDateFormat(fmt);
		 return df.format(d);
	 }
}
