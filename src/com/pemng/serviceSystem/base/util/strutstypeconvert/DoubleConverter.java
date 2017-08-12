package com.pemng.serviceSystem.base.util.strutstypeconvert;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class DoubleConverter extends StrutsTypeConverter {

	public String convertToString(Map context, Object o) {
		if(o==null)
			return "";
		if("".equals(o.toString().trim())){
			return "";
		}
		Double number = (Double) o;
		String str = new DecimalFormat("#0.00").format(number);
		return str;
	}

	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {    
            return null;    
        }  
		
		if (Double.class.equals(toClass)) {
			if("".equals(values[0])){
				return null;
			}
			Double num = Double.valueOf(values[0]);
			return num;
		}
		return Double.valueOf("0");
	}

	public static String convertDouble(Double dou){
		return new DecimalFormat("#0.00").format(dou);
	}
}
