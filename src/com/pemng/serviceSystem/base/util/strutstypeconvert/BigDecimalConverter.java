package com.pemng.serviceSystem.base.util.strutstypeconvert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class BigDecimalConverter extends StrutsTypeConverter {

	public String convertToString(Map context, Object o) {
		if(o==null)
			return "";
		if("".equals(o.toString().trim())){
			return "";
		}
		BigDecimal number = (BigDecimal) o;
		String str = new DecimalFormat("###.####").format(number);
		return str;
	}

	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {    
            return null;    
        }  
		
		if (BigDecimal.class.equals(toClass)) {
			if("".equals(values[0])){
				return null;
			}
			BigDecimal num = BigDecimal.valueOf(Double.valueOf(values[0]));
			return num;
		}
		return BigDecimal.valueOf(0);
	}

	public static String convertBigDecimal(BigDecimal dou){
		return new DecimalFormat("###.####").format(dou);
	}
}
