/**
 * Title:       China Money Web System
 * Name:        StringUtil.java  
 * Description: 
 * Copyright:   Copyright (c) 2005
 * Company:     FuDan Kingstar Corp.
 * @author      martin
 * @version     0.1
 *
 **************************************************************
 * Created on 2005-11-2  21:44:39
 **************************************************************
 * Modify History:
 *
 */
package com.pemng.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;



public class StringUtil {
	
	public static String replace(String queryStr, String oldstr, String replacement) {
		if (queryStr == null || oldstr == null) {
			return queryStr;
		}
		int lengthqueryStr = queryStr.length();
		int lengtholdstr = oldstr.length();
		int pos = queryStr.indexOf(oldstr);
		int beginIndex = 0;
		List strlist = new ArrayList(4);

		while (beginIndex < lengthqueryStr) {
			if (pos == -1) break;
			strlist.add(queryStr.substring(beginIndex, pos));
			beginIndex = pos + lengtholdstr;
			pos = queryStr.indexOf(oldstr, beginIndex);
		}
		strlist.add(queryStr.substring(beginIndex));
		StringBuffer sb = new StringBuffer("");
		for(int i = 0 ; i < strlist.size()-1; i++){
			sb.append(strlist.get(i)).append(replacement);
		}
		sb.append(strlist.get(strlist.size()-1));
		return sb.toString();
	}
	
	public static String hidePass(String password) {
		return encrypt(password);
	}
	
	public static String showPass(String password){
		return encrypt(password);
	}
	
	private static String encrypt(String pass){
		char[] orig = pass.toCharArray();
		byte[] dest = new byte[orig.length];
		for (int i = 0; i < orig.length; i++)
			dest[i] = (byte) (orig[i] ^ (0x10 + i));
		return new String(dest);
	}
	
	public static String getUTF8String(String src){
		try {
			return new String(src.getBytes("ISO8859_1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return src;
		}
	}
	
	public static String getISOString(String src){
		try {
			return new String(src.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return src;
		}
	}
	
	public static java.util.List conv2List(String value, String delim) {

		java.util.List stringList = new ArrayList();
		StringTokenizer parser = new StringTokenizer(value, delim);
		while (parser.hasMoreTokens()) {
			stringList.add(parser.nextToken());
		} //end of while
		return stringList;
	} 
	
	public static java.util.List conv2List(String value){
	    return conv2List(value, "#");
	}
	

    /**
     * 数字不足前面补0
     * @param num  -- 
     * @return
     */
    public static String getBitString(int num, int length){
    	StringBuilder str = new StringBuilder();
    	int len = String.valueOf(num).length();
		if(len < length)
		for(int i=0;i<(length - len);i++){
			str.append("0");
		}
    	str.append(num);
    	return str.toString();
    } 
    /**
     * 将输入的字符串转换为UTF8字符串
     * @param s  -- 输入的字符串
     * @return 转换后的字符串
     */
	public static String toUTF8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
			sb.append(c);
			} else {
			byte[] b;
			try {
				b = Character.toString(c).getBytes("utf-8");
			} catch (Exception ex) {
				System.out.println(ex);
				b = new byte[0];
			}
			for (int j = 0; j < b.length; j++) {
				int k = b[j];
				if (k < 0) k += 256;
				sb.append("%" + Integer.toHexString(k).
				toUpperCase());
			}
			}
		}
		return sb.toString();
	}
    
	/*
	 *四舍五入方法
	 *返回值为String 
	 *无效返回值为 -100
	 */
	public  static double getValueForDb(String v,int scale){
		try {
			double dv = Double.parseDouble(v) / 100;
		    BigDecimal b = new BigDecimal(dv);
		    BigDecimal one = new BigDecimal("1");
		    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e1){
			System.err.println(e1.getMessage());
			return -100;
		}
	  }
	
	//用于直接显示的HTML的转译
	public static String HTMLViewEncode(String aTagFragment){
		return StringEscapeUtils.escapeHtml(aTagFragment);
	}
	
	//用于JS中要绘制HTML的转译
	public static String HTMLEncode(String aTagFragment){
		String newStr =  StringEscapeUtils.escapeHtml(aTagFragment);
		if(newStr==null){
			return "";
		}
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(newStr);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE ){
			if (character == '\'') {
				result.append("&#039;");
			}else if (character == '\\') {
				result.append("&#092;");
			}else if (character == ' ') {
				result.append("&nbsp;");
			}else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
	
	//完整转译
	public static String FullHTMLEncode(String aTagFragment){
		if(aTagFragment==null){
			return "";
		}
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE ){
			if (character == '<') {
				result.append("&lt;");
			}else if (character == '>') {
				result.append("&gt;");
			}else if (character == '\"') {
				result.append("&quot;");
			}else if (character == '\'') {
				result.append("&#039;");
			}else if (character == '\\') {
				result.append("&#092;");
			}else if (character == '&') {
				result.append("&amp;");
			}else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
	
	//用于查询语句的转译
	public static String SQLEncode(String aTagFragment){
		String newStr = StringEscapeUtils.escapeSql(aTagFragment);
		if(newStr==null){
			return "";
		}
		final StringBuffer result = new StringBuffer();
		final StringCharacterIterator iterator = new StringCharacterIterator(newStr);
		char character = iterator.current();
		char lastChar =' ';
		int countFlag = 0;
		while (character != StringCharacterIterator.DONE ){
			if (character == '{') {
				if(countFlag == 0){
					result.append("chr(123)||'");
				}else{
					result.append("'||chr(123)||'");
				}
			}else if (character == '}') {
				if(countFlag == 0){
					result.append("chr(125)||'");
				}else{
					result.append("'||chr(125)||'");
				}
			}else {
				result.append(character);
			}
			lastChar = character;
			character = iterator.next();
			countFlag++;
		}
		if(lastChar == '}' || lastChar == '{'){
			return result.toString().substring(0,(result.toString().length()-3));
		}else{
			return result.toString();
		}
	}
	
	//用于JavaScript的转译
	public static String JSEncode(String aTagFragment){
		return StringEscapeUtils.escapeJavaScript(aTagFragment);
	}
	
	public static String JSPEncode(String aTagFragment){
		return StringEscapeUtils.escapeJava(aTagFragment);
//		if(aTagFragment==null){
//			return "";
//		}
//		final StringBuffer result = new StringBuffer();
//		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
//		char character = iterator.current();
//		while (character != StringCharacterIterator.DONE ){
//			if (character == '\"') {
//				result.append("\\\"");
//			}else if (character == '\'') {
//				result.append("\\\'");
//			}else if (character == '\\') {
//				result.append("\\\\");
////			}else if (character == '&') {
////				result.append("&amp;");
//			}else {
//				result.append(character);
//			}
//			character = iterator.next();
//		}
//		return result.toString();
	}
	
	public static boolean haveHTMLcode(String aTagFragment){
		if(aTagFragment==null){
			return false;
		}
		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE ){
			if (character == '<') {
				return true;
			}else if (character == '>') {
				return true;
			}else if (character == '\"') {
				return true;
			}else if (character == '\'') {
				return true;
			}else if (character == '\\') {
				return true;
			}else if (character == '&') {
				return true;
			}
			character = iterator.next();
		}
		return false;
	}
	
	
	public static boolean haveDownHTMLcode(String aTagFragment){
		if(aTagFragment==null){
			return false;
		}
		final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE ){
			if (character == '/') {
				return true;
			}
			character = iterator.next();
		}
		return false;
	}
	
	/**
	 * 把为空字段转化成页面的空格&nbsp;
	 */
	public static String convertNbsp(String str){
		if(str==null||str.trim().length()==0||str.trim().equals("null")){
			str=" ";
		}
		return str;
	}
	
	/**
	 * 把为空字段转化成页面的空格&nbsp;
	 */
	public static String convertNull(String str){
		try {
			if(str==null||str.trim().length()==0||str.trim().equals("null")){
				str="";
			}
			return str.trim();
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 把为空字段转化成页面的空格&nbsp;
	 */
	public static String convertNull(String str, String start){
		try {
			if(str==null||str.trim().length()==0||str.trim().equals("null")){
				return "";
			}
			return start + str.trim();
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 把为空字段转化成页面的空格&nbsp;
	 */
	public static String convertNull(Double d){
		if (null == d) {
			return "";
		}
		try {
			  int i = d.intValue();
			  if(d.doubleValue() - i != 0) {
				  return DecimalFormatUtil.format(d);
			  } else {
				  return DecimalFormatUtil.formati(i);
			  }
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 把为空字段转化成页面的空格&nbsp;
	 */
	public static String convertNull(BigDecimal d){
		if (null == d) {
			return "";
		}
		try {
			return DecimalFormatUtil.format_4(d);
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String replaceBlack(String value) {
		if(StringUtils.isBlank(value)) {
			return "";
		} 
		
		return value;
	}
	public static void main(String[] args) {
		 Double d = Double.valueOf("1001001");
		System.out.println(StringUtil.convertNull(d));
	}
	
	/**1:返回派工单号 2:
	 * &nbsp;
	 */
	public static String dellRepairNo(int i,String repairNo){
		String str="";
		if(repairNo==null||repairNo.trim().length()==0||repairNo.trim().equals("null")){
			str="";
		}
		else if(i==1){
			str= repairNo.trim().split("_")[0];
		}
		
		return str;
	}
	public static String getSelectListToString(List<?> list,String id,String name,String key,String value){
		StringBuffer selctBuffer=new StringBuffer();
		try {
			selctBuffer.append("<select ").append("id =\"").append(id).append("\" ").append("name =\"").append(name).append("\" >");
			selctBuffer.append(" <option value=\"\" ></option> ");
			for(Object obj : list){
				Method getKey = obj.getClass().getMethod("get"+key, new Class[] {});
				Object keyObj=  getKey.invoke(obj, new Object[] {});
				
				Method getValue = obj.getClass().getMethod("get"+value, new Class[] {});
				Object valueObj=  getValue.invoke(obj, new Object[] {});
				
				selctBuffer.append("<option ").append("  value=\"").append(keyObj.toString()).append("\" >");
				selctBuffer.append(valueObj.toString());	
				selctBuffer.append(" </option>");
			}
			selctBuffer.append(" </select>");
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		return selctBuffer.toString();
	}
	
	public static String getSelectListToString(List<?> list,String id,String name,String key,String value,String className){
		StringBuffer selctBuffer=new StringBuffer();
		try {
			selctBuffer.append("<select ").append("id =\"").append(id).append("\" ").append("name =\"").append(name).append("\" ").append("class =\"").append(className).append("\" >");
			selctBuffer.append(" <option value=\"\" ></option> ");
			for(Object obj : list){
				Method getKey = obj.getClass().getMethod("get"+key, new Class[] {});
				Object keyObj=  getKey.invoke(obj, new Object[] {});
				
				Method getValue = obj.getClass().getMethod("get"+value, new Class[] {});
				Object valueObj=  getValue.invoke(obj, new Object[] {});
				
				selctBuffer.append("<option ").append("  value=\"").append(keyObj.toString()).append("\" >");
				selctBuffer.append(valueObj.toString());	
				selctBuffer.append(" </option>");
			}
			selctBuffer.append(" </select>");
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		return selctBuffer.toString();
	}
	

	
	/**
	 * 解码escape
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		if (src == null)
			return null;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	public static String addJsonParameter(String name,Object value){
		StringBuilder json = new StringBuilder();
		json.append("\"").append(name).append("\":").append("\"");
		if(value==null)
			json.append("").append("\"");
		else{
			if(value instanceof Boolean){
				json.append(value).append("\"");
			}else{
				json.append(value.toString()).append("\"");
			}
		}
		return json.toString();
	}
	
	public static Object convertNull(Object o) {
		if(o == null)
			return "";
		return o;
	}
	
	//字符替换
	public static  String changValue(String strSource, String strFrom, String strTo) {
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		// 循环替换字符串
		while((intPos = strSource.indexOf(strFrom)) != -1)
		{
		// 获取匹配字符串的左边子串
		strDest = strDest + strSource.substring(0,intPos);
		// 加上替换后的子串
		strDest = strDest + strTo;
		// 修改源串为匹配子串后的子串
		strSource = strSource.substring(intPos + intFromLen);
		}
		// 加上没有匹配的子串
		strDest = strDest + strSource;
		// 返回
		return strDest;
		
	}
}
