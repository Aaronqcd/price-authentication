package com.pemng.serviceSystem.base.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;


/*
 * =============================================
 * Copyright 2006 IBM AMS
 *
 * Change Revision
 * ---------------------------------------------
 *   Date     Author        Remarks
 *  Jul 03, 2006   Zhang Feng     Initial Creation
 *  Oct 23, 2007   Qiao Zhi-qiang For SOLII
 * =============================================
 */
/**
 * 字符串工具类，用于格式化等
 * 
 * 
 */
public class StringUtil {
	public static final Class[] BRIDGE_MAP_PARAM = { Map.class };
	public static final String regEx = "[\\u4e00-\\u9fa5]"; // 汉字的Unicode范围

	/**
	 * 缩写，提供StringUtils接口
	 * 
	 * @param ori
	 *            原字符串
	 * @param num
	 *            长度，保证缩写后不超过
	 * 
	 * 
	 * @return 缩写后
	 * 
	 * 
	 */
	public static String abbreviate(String ori, Integer num) {
		return StringUtils.abbreviate(ori, num);
	}

	public static String get2DigitCurrentYear() {
		Calendar currTime = Calendar.getInstance();
		String sYear = String.valueOf(currTime.get(Calendar.YEAR));
		return sYear.substring(2);
	}

	public static String getCurrentYear() {
		Calendar currTime = Calendar.getInstance();
		String sYear = String.valueOf(currTime.get(Calendar.YEAR));
		return sYear;
	}

	public static String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(8);
	}

	/**
	 * convert a string from selectlist to a boolean
	 * 
	 * @param ori
	 *            should be #null# true false
	 * @return
	 */
	public static Boolean getNullableBoolean(String ori) {
		if ("#NULL#".equals(ori)) {
			return null;
		} else {
			return Boolean.valueOf(ori);
		}
	}

	public static String getNullableString(String ori) {
		if ("#NULL#".equals(ori)) {
			return null;
		} else {
			return ori;
		}
	}

	/**
	 * Compatible to Unix and windows style
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {

		int lastSplitPos1 = path.lastIndexOf("\\");
		int lastSplitPos2 = path.lastIndexOf("/");
		int lastSplitPos = NumberUtils.max(lastSplitPos1, lastSplitPos2, -1);

		return path.substring(lastSplitPos + 1);

	}

	public static String parseDownloadZhString(String ori) {
		try {
			return new String(ori.getBytes("gb2312"), "iso8859-1");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Format number to a String in style of "0.00##"
	 * 
	 * @param ori
	 * @return "#,##0.00##"
	 */
	public static String formatDecimal(Number ori) {

		if (ori == null) {
			return null;
		}
		try {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(4);
			nf.setMinimumFractionDigits(2);
			nf.setMinimumIntegerDigits(1);
			nf.setGroupingUsed(true);

			return nf.format(ori);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Format number to a String in style of "0.00##", but depends on its real
	 * scale. the min fraction digits = min(4,max(2,scale))
	 * 
	 * @param ori
	 * @return "#,##0.00##"
	 */
	public static String formatDecimal(BigDecimal ori) {

		if (ori == null) {
			return null;
		}
		try {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(4);
			int maxScale = ori.scale() > 2 ? ori.scale() : 2;
			nf.setMinimumFractionDigits(maxScale < 4 ? maxScale : 4);
			nf.setMinimumIntegerDigits(1);
			nf.setGroupingUsed(true);

			return nf.format(ori);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Format number to a String in style of "0"
	 * 
	 * @param ori
	 * @return "#,##0"
	 */
	public static String formatInteger(Number ori) {

		if (ori == null) {
			return null;
		}
		try {

			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(0);
			nf.setMinimumIntegerDigits(1);
			nf.setGroupingUsed(true);

			return nf.format(ori);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Format number to a String in style of "0.00##%"
	 * 
	 * @param ori
	 * @return
	 */
	public static String formatPercent(Number ori) {

		if (ori == null) {
			return null;
		}
		try {

			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(2);
			nf.setMinimumFractionDigits(2);
			nf.setMinimumIntegerDigits(1);
			nf.setGroupingUsed(true);

			return nf.format(ori);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Format number to a String in style of "#,##0.####%"
	 * 
	 * @param ori
	 * @return
	 */
	public static String formatRawPercent(Number ori) {

		if (ori == null) {
			return null;
		}

		return formatNumber(ori) + "%";

	}

	/**
	 * Format number to a String in style of "0"
	 * 
	 * @param ori
	 * @return "#,##0.####"
	 */
	public static String formatNumber(Number ori) {

		if (ori == null) {
			return null;
		}
		try {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(4);
			nf.setMinimumFractionDigits(0);
			nf.setMinimumIntegerDigits(1);
			nf.setGroupingUsed(true);

			return nf.format(ori);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * function: 获取指定时间的指定域. add by aaron at 2007-10-25
	 * 
	 * @param dat_date
	 * @param s_field
	 * @return
	 */
	public static int getDateField(java.util.Date dat_date, String s_field) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(dat_date);
		if (s_field.equals("d"))
			return cld.get(Calendar.DAY_OF_MONTH);
		else if (s_field.equals("w"))
			return cld.get(Calendar.DAY_OF_WEEK);
		else if (s_field.equals("m"))
			return cld.get(Calendar.MONTH);
		else if (s_field.equals("y"))
			return cld.get(Calendar.YEAR);
		else if (s_field.equals("h"))
			return cld.get(Calendar.HOUR_OF_DAY);
		else if (s_field.equals("mi"))
			return cld.get(Calendar.MINUTE);
		else if (s_field.equals("s"))
			return cld.get(Calendar.SECOND);
		else
			return 0;
	}

	// ][ Add by LiuYanLu 2007-10-24

	public static String getObjString(Object obj) {
		if (null == obj)
			return "";
		else
			return obj.toString();
	}

	public static String getMapString(Map map, String key) {
		Object obj = map.get(key);

		return getObjString(obj);
	}

	/**
	 * function: 时间日期的加减 add by aaron 2007-10-28
	 * 
	 * @param dat_date
	 * @param s_field
	 *            d: 天 m: 月 y: 年 h: 小时 mi: 分 s: 秒
	 * 
	 * 
	 * @param i_amount
	 * @return
	 */
	public static java.util.Date DateAdd(java.util.Date dat_date, String s_field, int i_amount) {
		/*
		 * add day or month or year or hour to datetime
		 */
		Calendar cld = Calendar.getInstance();
		cld.setTime(dat_date);
		if (s_field.equals("d"))
			cld.add(Calendar.DAY_OF_MONTH, i_amount);
		else if (s_field.equals("m"))
			cld.add(Calendar.MONTH, i_amount);
		else if (s_field.equals("y"))
			cld.add(Calendar.YEAR, i_amount);
		else if (s_field.equals("h"))
			cld.add(Calendar.HOUR_OF_DAY, i_amount);
		else if (s_field.equals("mi"))
			cld.add(Calendar.MINUTE, i_amount);
		else if (s_field.equals("s"))
			cld.add(Calendar.SECOND, i_amount);
		return cld.getTime();
	}

	public static String splitWithComma(String ori) {
		if (ori == null)
			return null;
		return ori.replaceAll("\\|", ", ");
	}

	public static String splitWithBr(String ori) {
		if (ori == null)
			return null;
		return ori.replaceAll("\\|", "<br>");
	}

	public static String join(Collection<?> collection) {

		return StringUtils.trimToNull(StringUtils.join(collection.iterator(), "|"));

	}

	public static String[] split(String ori) {
		if (ori == null) {
			return null;
		} else {
			return StringUtils.splitPreserveAllTokens(ori, "|");
		}
	}

	public static String joinThreeWithDot(Object first, Object second, Object third) {

		return StringUtils.join(new Object[] { first, second, third }, ".");
	}

	/**
	 * convert any possible object to big decimal
	 * 
	 * @param ori
	 * @return null if can't convert
	 */
	public static BigDecimal parseBigDecimal(Object ori) {
		if (ori == null)
			return null;
		return (BigDecimal) new BigDecimalConverter(null).convert(ori.getClass(), ori);
	}

	/**
	 * convert any possible object to big decimal
	 * 
	 * @param ori
	 * @return null if can't convert
	 */
	public static BigDecimal parseBigDecimal(String ori) {
		if (ori == null)
			return null;
		try {
			Number number = NumberFormat.getNumberInstance().parse(ori);
			return parseBigDecimal(number);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 转换成BigDecimal。如果能够转换成BigDecimal，但是结果小于0.00001，也返回null
	 */
	public static BigDecimal parseToBigDecimal(String ori) {
		if (ori == null || ori.trim().equals("")) {
			return null;
		}

		try {
			Number number = NumberFormat.getNumberInstance().parse(ori);
			BigDecimal num = parseBigDecimal(number);

			if (num.compareTo(new BigDecimal("0.00001")) < 0) {
				return null;
			}

			return num;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 获取URL访问中的文件名，例如
	 * http://100.1.1.112:7080/CSCWeb/pages/acl/employee_read.jsf?param=1000
	 * 返回结果为 employee_read.jsf 如果输入为NULL， 则返回""空字符串
	 * 
	 * 用途： 用来判断用户访问的URL权限
	 */
	public static String filenameOfURL(String url) {

		String result_str = "";
		if (null == url)
			return result_str;

		int param_pos = url.lastIndexOf("?");
		int begin_pos = url.lastIndexOf("/") + 1;

		if (begin_pos <= 0) {
			begin_pos = 0;
		}

		if (param_pos < 0) {
			param_pos = url.length(); // 没有参数，直接截取到最后一位

		}

		result_str = url.substring(begin_pos, param_pos);

		return result_str;
	}

	/*
	 * 获取URL访问中的文件名带参数，例如
	 * 
	 * 
	 * http://100.1.1.112:7080/CSCWeb/pages/acl/employee_read.jsf?param=1000
	 * 返回结果为 employee_read.jsf?param=1000 如果输入为NULL， 则返回""空字符串
	 * 
	 * 用途： 用来判断用户访问的URL权限
	 */
	public static String filenameAndParamOfURL(String url) {

		String result_str = "";
		if (null == url)
			return result_str;

		int param_pos = url.lastIndexOf("?");
		int begin_pos = url.lastIndexOf("/") + 1;

		if (begin_pos <= 0) {
			begin_pos = 0;
		}

		// if(param_pos<0){
		param_pos = url.length(); // 保留参数，直接截取到最后一位

		// }

		result_str = url.substring(begin_pos, param_pos);

		return result_str;
	}

	public static String trimForOpenWindow(String ori) {
		if (ori == null) {
			return null;
		} else {
			return ori.replaceAll("[()'\"/\\\\ .]", "");
		}
	}

	// /**
	// *
	// * @param toChange
	// * @param example
	// * @return
	// */
	// public static <T extends Number> T adjustSameFractions(T toChange,
	// Number example) {
	//
	// if (example == null || toChange == null)
	// return null;
	// String exampleString = example.toString();
	// int fracDigits = exampleString.length() - exampleString.indexOf(".")
	// - 1;
	// fracDigits = fracDigits < 2 ? 2 : fracDigits;
	//
	// NumberFormat nf = NumberFormat.getNumberInstance();
	// nf.setGroupingUsed(false);
	// nf.setMaximumFractionDigits(fracDigits);
	//
	// return (T) NumberUtils.cre(nf.format(toChange));
	// }

	// /**
	// * str1, str2, str3, str4 -> 'str1', 'str2', 'str3', 'str4'
	// */
	// public static String mergeInString(Collection collection) {
	//
	// if (collection.size() == 0)
	// return new String();
	//
	// String str = StringUtils.join(collection.iterator(), "', '");
	// return "'" + str + "'";
	//
	// }
	public static String parseBooleanDe(Boolean value) {
		if (value == null) {
			value = false;
		}

		return value ? "Ja" : "Nein";
	}

	public static String parseWindowFilename(String source) {
		if (source == null) {
			return null;
		} else {
			return source.replaceAll("[/\\\\:*?\"<>|]", "_");
		}
	}

	public static BigDecimal formartBigDecimal(BigDecimal data) {
		BigDecimal formatData = new BigDecimal(0);
		if (null != data) {
			formatData = data.setScale(0, BigDecimal.ROUND_HALF_UP);
		}
		return formatData;
	}

	/**
	 * 取得前1000个字符（一个汉字算成三个字符）
	 */
	public static String get1000Char(String str) {
		return trimToLength(str, 1000);
	}

	public static String appendParameter(String oriUrl, String newParameter) {
		int pos = oriUrl.indexOf("?");
		if (pos == -1) {
			return oriUrl + "?" + newParameter;
		} else {
			return oriUrl + "&" + newParameter;
		}
	}

	/**
	 * 适用于所有可比较对象(包括null值)的比较。<br/> 首先如果两者完全一致，或者都为null，则相同。<br/>
	 * 之后根据isNullBigger标签确定null的一方与有值一方相比哪个更大。<br/> 最后再调用两者的compareTo方法进行比较。
	 * 
	 * 
	 * 
	 * @return 比较值，o1大返回1，相同返回0，o1小返回-1
	 */
	public static <T extends Comparable<? super T>> int generalCompare(T o1, T o2, boolean isNullBigger) {

		if (o1 == o2) { // 针对全为null或者对象也完全相同的情况

			return 0;
		}

		if (o1 == null) {
			return isNullBigger ? 1 : -1;
		} else if (o2 == null) {
			return isNullBigger ? -1 : 1;
		} else {
			return o1.compareTo(o2);
		}
	}

	/**
	 * Returns the byte length in given charset of the String.
	 * 
	 * @param value
	 * @param charset
	 *            Charset under which to measure.
	 * @return Returns 0 if String is null, or the charset is not supported.
	 * @see CharsetConstant
	 */
	public static int getByteLength(String value, String charset) {
		if (value == null) {
			return 0; // TODO: 将null字符作为0长度处理，需要研究

		} else {
			try {
				return value.getBytes(charset).length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return 0;
			}
		}

	}

	/**
	 * Returns the byte length in UTF8 of the String.<br/> Generally, Chinese
	 * characters are three bytes each, ASCII characters are one byte each.
	 * 
	 * @param value
	 * @return Returns 0 if String is null.
	 * @see CharsetConstant
	 */
	public static int getUTF8Length(String value) {

		return getByteLength(value, "UTF-8");

	}

	/**
	 * 获取指定UTF-8模式字节长度的字符串.如果是null则返回null.
	 * 
	 * @param strValue
	 *            原字符串
	 * @param bytelen
	 *            字节长度
	 * @return 如果是null则返回null.
	 */
	public static String trimToLength(String strValue, int bytelen) {

		// 中文汉字占用三个字节
		int strlen = bytelen / 3;
		int real_bytelen = strlen * 3;

		// 如果为NULL，则直接返回
		if (null == strValue) {
			return null;
		}

		try {
			byte[] utf8_bytes = strValue.getBytes("UTF-8");
			if (utf8_bytes.length <= bytelen)
				return strValue;

			byte[] cutoff_bytes = new byte[real_bytelen];
			System.arraycopy(utf8_bytes, 0, cutoff_bytes, 0, real_bytelen);

			String strResult = new String(cutoff_bytes, "UTF-8");

			return strResult;

		} catch (Exception e) {
			if (strValue.length() < strlen)
				return strValue;
			return strValue.substring(0, strlen);
		}

	}

	/**
	 * 删除字符串两头的空格
	 * 
	 * @param strValue
	 * @return
	 */
	public static String trim(String strValue) {
		if (null == strValue)
			return null;
		else
			return strValue.trim();
	}

	/**
	 * 当字符串为NULL或者全部是空格时返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str)
			return true;
		if (str.trim().length() == 0)
			return true;
		return false;
	}
	
	/**
	 * 补充两个空格
	 * @param partNum
	 * @return
	 */
	public static String appendBlank(String str){
		if(!isEmpty(str)){
			str = str.concat(" ").concat(" ");
		}
		return str;
	}

	/**
	 * 在字符串的左边补充字符到制定长度
	 * 
	 * @param str
	 * @param ch
	 * @param length
	 * @return
	 */
	public static String padLeft(String str, char ch, int length) {
		if (null == str)
			return null;
		if (str.length() >= length)
			return str;
		length = length - str.length();
		for (int i = 0; i < length; i++) {
			str = String.valueOf(ch) + str;
		}
		return str;
	}

	public static void fieldsToUpperCase(Object obj, String[] fieldNames) {
		List<String> fieldNameList = Arrays.asList(fieldNames);
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (fieldNameList.contains(field.getName()) && field.getType().equals(String.class)) {
				field.setAccessible(true);
				try {
					if (field.get(obj) != null)
						field.set(obj, String.valueOf(field.get(obj)).toUpperCase());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static boolean isEqual(String str1, String str2) {
		if (isEmpty(str1)) {
			if (isEmpty(str2))
				return true;
			else
				return false;
		} else {
			if (isEmpty(str2))
				return false;
			else
				return (0 == str1.compareTo(str2));
		}
	}
	
	//判断字符串是否为Double型数值
	public static  boolean isDouble(String str){
		return (str != null)&&str.matches("^\\d+(\\.\\d+)?$");
	}

	//判断字符串是否为整型数值
	public static boolean isInteger(String str){
		return (str != null)&&str.matches("^[1-9]\\d*$");
	}
	
	/**
	 * 字符串截取, 中文算两个字符,当截取的最后一位为半个中文时, 此中文不计算在内
	 * 
	 * @param value
	 * @param size
	 * @return
	 */
	public static String subStrZhChar(int size, String value) {

		if (size <= 0) {
			throw new IllegalArgumentException(" size 必须大于0");
		}

		if (isEmpty(value)) {
			return value;
		}

		StringBuffer buf = new StringBuffer();
		int idx = 0;
		char[] charArray = value.trim().toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (isChinese(c)) {
				idx += 2;
			} else {
				idx++;
			}

			if (idx > size) { // 如果大于截取的长度,则不算在内
				break;
			} else if (idx == size) { // 如果刚好等于截取长度
				buf.append(c);
				break;
			} else { //
				buf.append(c);
			}
		}
		return buf.toString();
	}
	
	public static String subStrZhChar(int size, String value, String endStr) {
		if (size <= 0 || isEmpty(value)) {
			return "";
		}else if(value.getBytes().length <= size){
			return value;
		}
		StringBuffer buf = new StringBuffer();
		int idx = 0;
		char[] charArray = value.trim().toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (isChinese(c)) {
				idx += 2;
			} else {
				idx++;
			}
			if (idx > size) { // 如果大于截取的长度,则不算在内
				break;
			} else if (idx == size) { // 如果刚好等于截取长度
				buf.append(c);
				break;
			} else { //
				buf.append(c);
			}
		}
		return buf.append(endStr).toString();
	}
	
	/**
	 * 获得中文字符长度
	 * 
	 * @param c
	 * @return
	 */
	public static int getCharLength(String value){
		int idx = 0;
		if (isEmpty(value)) {
			return 0;
		}
		char[] cArray = value.trim().toCharArray();
		for(char c : cArray){
			if (isChinese(c)) {
				idx += 2;
			} else {
				idx++;
			}
		}
		return idx;
	}
	/**
	 * 判断是否时中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;

		}

		return false;

	}	
	public static String getClassShortName(Class c){
		return c.getName().substring(c.getName().lastIndexOf('.')+1);
	}
	
	public static String addSqlLikeSymbol(String val){
		return "%" + val + "%";
	}
	
    /**
     * 例如Collection的内容为[a, b, c]
     * 执行 armCollection(collection, "'", "'", " or ") 则返回字符串 'a' or 'b' or 'c' . 这个很适合拼凑SQL的like语句。
     * 执行 armCollection(collection, "{", "}", ";") 则返回字符串 {a};{b};{c}
     */
    public static String armCollection(Collection collection, String bracketLeft, String bracketRight, String seg) {
        if (collection == null) return "";
        if (bracketLeft == null) bracketLeft = "";
        if (bracketRight == null) bracketRight = "";
        if (seg == null) seg = "";
        return bracketLeft + colletionToString(collection, bracketRight + seg + bracketLeft) + bracketRight;
    }
    
    /**
     * 将一个Collection转化为用seg作为分割符的字符串
     */
    public static String colletionToString(Collection col, String seg) {
        StringBuffer rtn = new StringBuffer("");
        if ((col == null) || (col.size() == 0)) {
            return "";
        }
        for (Iterator it = col.iterator(); it.hasNext();) { //逐个将备选项加入
            Object str = it.next();
            rtn.append(String.valueOf(str));
            if (it.hasNext()) { //最后一项不添加换行符号.
                rtn.append(seg);
            }
        }

        return rtn.toString();
    }

	/**
	 * Function Name               getStringBlankEnter                                   
	 * @param                      //需要替换的字符串
	 * @return                     //返回替换后的空格与回车符 
	 * @description                //替换空格 回车符 			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Aug 21, 2012             yun         Initial
	 **********************************************************************
	 */
	public static String getStringBlankEnter(String str){
		String newStr = null;
		Pattern p = null;
		Matcher m = null;
		if(str!=null && !"".equals(str)){
			//替换回车符
			p = Pattern.compile("\t|\r\n");
			m = p.matcher(str);
			newStr = m.replaceAll("<br>");
			//替换空格
			p = Pattern.compile("\\s");
			m = p.matcher(newStr);
			newStr = m.replaceAll("&nbsp;");
			return newStr;
		}else{
			return str;
		}
	}
}