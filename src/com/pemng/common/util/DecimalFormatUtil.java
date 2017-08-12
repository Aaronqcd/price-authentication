package com.pemng.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DecimalFormatUtil {
	private static DecimalFormat df6 = new DecimalFormat("0.000000");
	private static DecimalFormat df5 = new DecimalFormat("0.00000");
	private static DecimalFormat df4 = new DecimalFormat("0.0000");
	private static DecimalFormat df3 = new DecimalFormat("0.000");
	private static DecimalFormat df2 = new DecimalFormat("0.00");
	private static DecimalFormat df1 = new DecimalFormat("0.0");
	private static DecimalFormat df0 = new DecimalFormat("0");
	private static DecimalFormat df = new DecimalFormat("###,###.##");
	private static DecimalFormat dfi = new DecimalFormat("###,###,###");
	private static DecimalFormat df_4 = new DecimalFormat("###.####");
	
	public static BigDecimal absDecimal(BigDecimal bigDecimal) {
		BigDecimal decimal = null;
		
		if (null != bigDecimal) {
			decimal = bigDecimal.abs();
		}
		
		return decimal;
	}
	/**
	 * 将小数点后面第二位多余的0省略掉
	 */
	public static String formatOtherZero(BigDecimal bigDecimal) {
		String result = "---";
		
		if (null != bigDecimal) {
			bigDecimal = round(bigDecimal, 2);
			
			result = format2(bigDecimal);
			
			if (result.trim().length() > 0) {
				if (result.indexOf(".00") > 0) {
					result = result.substring(0,result.length() - 1);
				}
			}
		}
		
		return result;
	}
	/**
	 * 将小数点后面第四位多余的0省略掉
	 */
	public static String formatOtherZeroFor4(BigDecimal bigDecimal) {
		String result = "---";
		
		if (null != bigDecimal) {
			bigDecimal = round(bigDecimal, 4);
			
			result = format4(bigDecimal);
			
			if (result.trim().length() > 0) {
				if (result.indexOf(".0000") > 0) {
					result = result.substring(0,result.length() - 3);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 将小数点后面第五位多余的0省略掉
	 */
	public static String formatOtherZeroFor5(BigDecimal bigDecimal) {
		String result = "---";
		
		if (null != bigDecimal) {
			bigDecimal = round(bigDecimal, 5);
			
			result = format5(bigDecimal);
			
			if (result.trim().length() > 0) {
				if (result.indexOf(".00000") > 0) {
					result = result.substring(0,result.length() - 4);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 四舍五入
	 * @param bd 需要四舍五入的数据
	 * @param roundNum 四舍五入的位数
	 * @return
	 */
	public static BigDecimal round(BigDecimal bd, int roundNum) {
		BigDecimal roundBd = null;
		
		if (null != bd) {
			roundBd = bd.setScale(roundNum, BigDecimal.ROUND_HALF_UP);
		}
		
		return roundBd;
	}
	//将BigDecimal类型的值转化为千分位格式（去金钱符号,默认位数格式化为2个零）
	public static String numberFormatCurrecy(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 2);
		if (null != bd) {
			//result = DecimalFormat.getNumberInstance().format(bd);
			result = NumberFormat.getCurrencyInstance().format(bd).substring(1);			
		}
		
		return result;
	}
	//将BigDecimal类型的值转化为千分位格式（去金钱符号,位数格式化为3个零）
	public static String numberFormatCurrecyForThree(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 2);
		if (null != bd) {
			//result = DecimalFormat.getNumberInstance().format(bd);
			result = NumberFormat.getCurrencyInstance().format(bd).substring(1) + "0";			
		}
		
		return result;
	}
	//格式化为整数
	public static String format0(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 0);
		if (null != bd) {
			result = df0.format(bd);
		}
		return result;
	}
	//格式化为整数，但不四舍五入
	public static String format0ByNoRound(BigDecimal bd) {
		String result = "---";
		
		if (null != bd) {
			result = df0.format(bd.intValue());
		}
		return result;
	}
	//格式化为小数点1位
	public static String format1(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 1);
		if (null != bd) {
			result = df1.format(bd);
		}
		return result;
	}
	//格式化为小数点2位
	public static String format2(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 2);
		if (null != bd) {
			result = df2.format(bd);
		}
		return result;
	}
	//格式化为小数点3位
	public static String format3(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 3);
		if (null != bd) {
			result = df3.format(bd);
		}
		return result;
	}
	//格式化为小数点4位
	public static String format4(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 4);
		if (null != bd) {
			result = df4.format(bd);
		}
		return result;
	}
	//格式化为小数点5位
	public static String format5(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 5);
		if (null != bd) {
			result = df5.format(bd);
		}
		return result;
	}
	//格式化为小数点6位
	public static String format6(BigDecimal bd) {
		String result = "---";
		
		bd = round(bd, 6);
		if (null != bd) {
			result = df6.format(bd);
		}
		return result;
	}
	
	public static String format(Double prc){
		return df.format(prc);
	}
	
	public static String formati(int prc){
		return dfi.format(prc);
	}
	
	public static String format_4(BigDecimal num){
		return df_4.format(num);
	}
	
	/**
	 * 
	 * @param bd
	 * @param precision 0-6 ,如果超出范围，按照4长度来处理
	 * @return
	 */
	public static String format(BigDecimal bd , int precision) {
		String result = "---";
		
		switch (precision) {
		case 0:result = format0(bd);break;
		case 1:result = format1(bd);break;
		case 2:result = format2(bd);break;
		case 3:result = format3(bd);break;
		case 4:result = format4(bd);break;
		case 5:result = format5(bd);break;
		case 6:result = format6(bd);break;
		default:result = format4(bd);break;
		
		}
		
		return result;
	}
}
