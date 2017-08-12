package com.pemng.serviceSystem.base.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public final class RequestWrapper extends HttpServletRequestWrapper {
	public RequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return cleanXSS(value);
	}

	public Map getParameterMap() {
		Map map = super.getParameterMap();
		Iterator iter = (map.keySet() != null) ? map.keySet().iterator() : null;
		String key = null;
		String[] values = null;
		if (iter != null) {
			while (iter.hasNext()) {
				key = (String) iter.next();
				if (key != null) {
					values = (String[]) map.get(key);
					for (int i = 0; i < values.length; i++)
						values[i] = cleanXSS(values[i]);
				}
			}
		}
		return map;
	}

	private String cleanXSS(String value) {
		// You'll need to remove the spaces from the html entities below
		//value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");  //json传值会被拦截
//		value = value.replaceAll("'", "&#39;");  //json传值会被拦截
		value = value.replaceAll("eval\\((.*)\\)", "");
//		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value.replaceAll("(?<![a-zA-z])script(?![a-zA-z])|javascript", "");
		value = value.replaceAll("(?i)script", "");
		return value;
	}
}