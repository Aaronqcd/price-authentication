package com.pemng.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.pojo.Data;

public class Utility {

	public static String getValue (Map<String , Data> map , String key) {
		String value = "";
		if(map != null) {
			if(map.get(key) != null) {
				value = map.get(key).getValue1();
			}
		}
		
		return value;
	}
	
	
	public static String getValue(Map<String , Map<String , Data>> maps , String code , String key , int valueIndex) {
		String value = "";
		if(maps != null && 
				maps.get(code) != null &&
				maps.get(code).get(key) != null) {
			if(1 == valueIndex) {
				value = maps.get(code).get(key).getValue1();
			} else if(2 == valueIndex) {
				value = maps.get(code).get(key).getValue2();
			} else if(3 == valueIndex) {
				value = maps.get(code).get(key).getValue3();
			}
		}
		
		if(value == null)
			value = "";
		
		return value;
	}
	
	public static String getIntValue(Map<String , Map<String , Data>> maps , String code , String key , int valueIndex) {
		String value = "0";
		if(maps != null && 
				maps.get(code) != null &&
				maps.get(code).get(key) != null) {
			if(1 == valueIndex) {
				value = maps.get(code).get(key).getValue1();
			} else if(2 == valueIndex) {
				value = maps.get(code).get(key).getValue2();
			} else if(3 == valueIndex) {
				value = maps.get(code).get(key).getValue3();
			}
		}
		
		if(value == null)
			value = "0";
		
		return value;
	}
	/**
	 * 有字符串转化为数组，默认字符串以','分割
	 * @param str
	 * @return
	 */
	public static List<String> getListofStr(String str) {
		if(isEmpty(str))
			return null;
		return getListOfStr(str , ",");
	}
	
	/**
	 * 有字符串转化为数组
	 * @param str
	 * @param delim : 分隔符
	 * @return
	 */
	private static List<String> getListOfStr(String str, String delim) {
		if(isEmpty(str))
			return null;
		return Arrays.asList(str.split(delim));
	}



	public static boolean isEmpty(String str) {
		boolean flag = false;
		if(str == null || str.trim().length() < 1) {
			flag = true;
		}
		return flag;
	}
	
	public static Object convertToDb(Object o) {
		if(o != null) {
			if(o instanceof String) {
				return "'" + o + "'";
			} else {
				return o;
			}
		} else {
			return "NULL" ;
		}
	}


	public static Integer timeTitle(Integer noRespTime,	List<Integer> noRespTimes) {
		Integer resTime = new Integer(0);
		for(Integer tempTime : noRespTimes) {
			if(noRespTime >= tempTime) {
				resTime = tempTime;
			}
		}
		
		return resTime;
	}
	
	public static void main(String[] args ) {
		Integer noRespTime = new Integer(45);
		List<Integer> noRespTimes = new ArrayList<Integer>();
		noRespTimes.add(new Integer(15));
		noRespTimes.add(new Integer(30));
		noRespTimes.add(new Integer(45));
		noRespTimes.add(new Integer(60));
		noRespTimes.add(new Integer(75));
		
		System.out.println(Utility.timeTitle(noRespTime, noRespTimes));
	}
}
