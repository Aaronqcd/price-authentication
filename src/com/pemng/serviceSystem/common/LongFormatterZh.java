package com.pemng.serviceSystem.common;

public class LongFormatterZh {

	 /** 大写数字 */
	  private static final String[] NUMBERS = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	  /** 整数部分的单位 */
	  private static final String[] IUNIT = {  "", "十", "百", "千", "万", "十", "百",
	      "千", "亿", "十", "百", "千", "万", "十", "百", "千"};

	  private static final String TEN_TEMP = "一十";
	  private static final String TEN = "十";
	  
	  
	  /**
	   * 得到大写金额。
	   */
	  public static String toChinese(Long d) {
		  try {
			  return toChinese(d.toString());
		  } catch(Exception e) {
			  return "";
		  }
	  }
	  /**
	   * 得到大写金额。
	   */
	  public static String toChinese(String str) {
	    String integerStr;// 整数部分数字

	    integerStr = str;
	    // integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
	    if (!integerStr.equals("")) {
	      integerStr = Long.toString(Long.parseLong(integerStr));
	      if (integerStr.equals("0")) {
	        integerStr = "";
	      }
	    }
	    // overflow超出处理能力，直接返回
	    if (integerStr.length() > IUNIT.length) {
	      System.out.println(str + ":超出处理能力");
	      return str;
	    }

	    int[] integers = toArray(integerStr);// 整数部分数字
	    boolean isMust5 = isMust5(integerStr);// 设置万单位
	    return getChineseInteger(integers, isMust5) ;
	  }

	  /**
	   * 整数部分和小数部分转换为数组，从高位至低位
	   */
	  private static int[] toArray(String number) {
	    int[] array = new int[number.length()];
	    for (int i = 0; i < number.length(); i++) {
	      array[i] = Integer.parseInt(number.substring(i, i + 1));
	    }
	    return array;
	  }

	  /**
	   * 得到中文金额的整数部分。
	   */
	  private static String getChineseInteger(int[] integers, boolean isMust5) {
	    StringBuffer chineseInteger = new StringBuffer("");
	    int length = integers.length;
	    for (int i = 0; i < length; i++) {
	      // 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
	      // 特殊情况：10(十元、壹十元、壹十万元、十万元)
	      String key = "";
	      if (integers[i] == 0) {
	        if ((length - i) == 13)// 万(亿)(必填)
	          key = IUNIT[4];
	        else if ((length - i) == 9)// 亿(必填)
	          key = IUNIT[8];
	        else if ((length - i) == 5 && isMust5)// 万(不必填)
	          key = IUNIT[4];
	        else if ((length - i) == 1)// 元(必填)
	          key = IUNIT[0];
	        // 0遇非0时补零，不包含最后一位
	        if ((length - i) > 1 && integers[i + 1] != 0)
	          key += NUMBERS[0];
	      }
	      chineseInteger.append(integers[i] == 0 ? key
	          : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
	    }
	    return chineseInteger.toString();
	  }


	  /**
	   * 判断第5位数字的单位"万"是否应加。
	   */
	  private static boolean isMust5(String integerStr) {
	    int length = integerStr.length();
	    if (length > 4) {
	      String subInteger = "";
	      if (length > 8) {
	        // 取得从低位数，第5到第8位的字串
	        subInteger = integerStr.substring(length - 8, length - 4);
	      } else {
	        subInteger = integerStr.substring(0, length - 4);
	      }
	      return Integer.parseInt(subInteger) > 0;
	    } else {
	      return false;
	    }
	  }

	  public static void main(String[] args) {
	    String number = "111111111111111";
	    System.out.println(number + " " + LongFormatterZh.toChinese(number));
	    
	  }
}
