package com.pemng.serviceSystem.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PeUtility {

	public static Map<String , String> methods = null;
	
	public static String getMethod(String key) {
		if(methods == null) {
			initMethods();
		}
		
		return methods.get(key.trim());
		//'001':'请选择','002':'市场法','003':'成本法','004':'专家咨询法','005':'收益法','006':'清算法'
	}

	private static void initMethods() {
		methods = new HashMap<String , String>();
		methods.put("001", "");
		methods.put("002", "市场法");
		methods.put("003", "成本法");
		methods.put("004", "专家咨询法");
		methods.put("005", "收益法");
		methods.put("006", "清算法");
		
		
	}
	
	
	public static List splitTrim(String value) {
		return splitTrim(value , "\n");
	}
	public static List splitTrim(String value , String regex) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(value)) {
			String[] values =  value.split(regex);
			for(String v : values) {
				if(StringUtils.isNotBlank(v))
					list.add(v.trim());
			}
		} else {
			list.add("");
		}
		
		return list;
	}
}
