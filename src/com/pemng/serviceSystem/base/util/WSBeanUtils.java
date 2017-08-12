package com.pemng.serviceSystem.base.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;

public class WSBeanUtils extends BeanUtils {
	static {
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
		ConvertUtils.register(new DateConvert(), java.sql.Date.class);
		ConvertUtils.register(new LongConverter(null), java.lang.Long.class);
		ConvertUtils.register(new IntegerConverter(null), java.lang.Integer.class);
		ConvertUtils.register(new DoubleConverter(null), java.lang.Double.class);
		ConvertUtils.register(new FloatConverter(null), java.lang.Float.class);
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
	}

	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
	}

	public static void copySamePerfixProperties(Object source, Object target, String perfix) {

	}
}


