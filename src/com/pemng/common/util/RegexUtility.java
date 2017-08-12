package com.pemng.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegexUtility {

	/**
	 * 查询是否匹配正则表达式
	 * @param input
	 * @param regex
	 * @return
	 */
	public static boolean matcher(String input , String regex) {
		boolean flag = false;
		//flag = Pattern.matches(regex, input);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		flag = matcher.find();
		return flag;
	}
	
	
	/**
	 * 取出匹配正则表达式的内容
	 * @param input
	 * @param regex
	 * @return
	 */
	public static String group(String input , String regex) {
		String group = null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if(matcher.find())
			group = matcher.group();
		return group;
	}
	
	
	/**
	 * 根据正则表达式替换
	 * @param input
	 * @param regex
	 * @param targetStr
	 * @return
	 */
	public static String replace(String input , String regex , String targetStr) {
		
		String result = null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		result = matcher.replaceFirst(targetStr);
		
		return result;
	}
	
	
	/**
	 * 根据正则表达式替换
	 * @param input
	 * @param regex
	 * @param targetStr
	 * @return
	 */
	public static String replaceAll(String input , String regex , String targetStr) {
		
		String result = null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		result = matcher.replaceAll(targetStr);
		
		return result;
	}
	
	public static String findIP(String input) {
		String ip = null;
		Pattern pattern = Pattern.compile(REGEX_IP);
		Matcher matcher = pattern.matcher(input);
		if(matcher.find())
			ip = matcher.group();
		return ip;
	}
	
	/**
	 * 客户端发送的新建故障短信内容
	 */
	public static String REGEX_NEW_CASE="^\\[.*\\].*'\\d{8,10}\\+1'受理$";
	public static String REGEX_NEW_CASE_ACCEPT="回复'\\d{8,10}\\+1'受理";
	
	//public static String REGEX_CUSTOMER_NAME="^\\[.*\\]\\[";
	
	public static String REGEX_SITE_NAME="\\[.*\\]";
	
	public static String REGEX_REPLACE_NAME="\\[|\\]";
	
	public static String REGEX_NEW_CASE_IDSTR="\\d{8,10}\\+1";
	public static String REGEX_NEW_CASE_IDSTR_LAST="\\+1";
	
	public static String REGEX_RESPONSE_ENGINEER="\\d{8,10}\\+0$";
	public static String REGEX_RESPONSE_ENGINEER_LAST="\\+0$";
	
	
	public static String REGEX_CASE_ACCEPTED="^\\[.*\\].*\\d{10}.*\\d{11}受理$";
	public static String REGEX_CASE_ACCEPTED_CASE="为\\d{10}";
	public static String REGEX_CASE_ACCEPTED_CASE_START="为";
	
	public static String REGEX_CASE_ACCEPTED_CASE_ENGINEE="\\d{11}受理$";
	public static String REGEX_CASE_ACCEPTED_CASE_ENGINEE_LAST="受理$";
	
	/**
	 * 正则表达式 余额查询
	 */
	public static String REGEX_BALANCE_SEARCH = "\\d+\\.\\d{2,}元";
	
	/**
	 * 正则表达式  余额
	 */
	public static String REGEX_BALANCE_VALUE = "\\d+\\.\\d{2,}";
	
	

	/**
	 * 正则表达式 : IP
	 */
	public static String REGEX_IP = "(\\d{1}|\\d{2}|([0-1][0-9][0-9]|[0-2][0-5][0-5]))\\.(\\d{1}|\\d{2}|([0-1][0-9][0-9]|[0-2][0-5][0-5]))\\.(\\d{1}|\\d{2}|([0-1][0-9][0-9]|[0-2][0-5][0-5]))\\.(\\d{1,3})";
	
	
	
	
	public static void main(String[] args) {
		String msg = "[中保南中心]:Alert: 192.168.1.112 is Down.回复'10000112+1'受理";
		if(matcher(msg, REGEX_NEW_CASE)) {
			//System.out.println("  " + replaceAll(group(msg , REGEX_CUSTOMER_NAME) , REGEX_REPLACE_NAME, ""));
			System.out.println("  " + replaceAll(group(msg , REGEX_SITE_NAME), REGEX_REPLACE_NAME, ""));
			System.out.println("  " + group(msg , REGEX_RESPONSE_ENGINEER));
			System.out.println("  " + replace(msg , REGEX_RESPONSE_ENGINEER , ""));
		} else {
			System.out.println("in else ");
		}
		
	}
	
}
