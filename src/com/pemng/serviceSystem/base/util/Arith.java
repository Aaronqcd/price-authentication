package com.pemng.serviceSystem.base.util;

import java.math.BigDecimal;

/** 
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。 
 */
/*******************************************************************************
 * 如果需要精确计算，非要用String来够造BigDecimal不可！在《Effective
 * Java》一书中的例子也是用String来够造BigDecimal的. BigDecimal bd = new BigDecimal("56.787");
 * //return 56.787 BigDecimal bd = new BigDecimal(56.787); //return
 * 56.78699999999999903366187936626374721527099609375 一般小数计算尽量使用double，不要用float
 * 使用BigDecimal缺点：要创建对象并占用内存；其floatValue()方法有时会精度丢失而造成计算错误(所以一般小数计算尽量使用double)。
 * 
 * 也正因为此，浮点数不能用于循环判断，否则容易造成死循环。
 * 
 * 
 * 金融系统一分钱误差的解决方法： 1.
 * 数据库中的小数位不可设置为2位，最好使用4位，否则在平均计算时候，四舍五入为2位小数，再用于其他计算，就会出现一分钱误差。
 * 
 * 2. 算术运算使用本类
 ******************************************************************************/
public class Arith {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	// 这个类不能实例化
	private Arith() {
	}

	// =======================
	// 提供精确的加法运算

	// =======================

	public static double add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue();
	}

	public static double add(double v1, double v2) {
		return add(Double.toString(v1), Double.toString(v2));
	}

	public static double add(double v1, double v2, double v3) {
		return add(add(v1, v2), v3);
	}

	public static double add(double v1, double v2, double v3, double v4) {
		return add(add(v1, v2, v3), v4);
	}

	// =======================
	// 提供精确的减法运算。

	// =======================

	/**
	 * @return v1-v2
	 */
	public static double sub(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * @return v1-v2
	 */
	public static double sub(double v1, double v2) {
		return sub(Double.toString(v1), Double.toString(v2));
	}

	/**
	 * @return v1-v2-v3
	 */
	public static double sub(double v1, double v2, double v3) {
		return sub(sub(v1, v2), v3);
	}

	/**
	 * @return v1-v2-v3-v4
	 */
	public static double sub(double v1, double v2, double v3, double v4) {
		return sub(sub(v1, v2, v3), v4);
	}

	// =======================
	// 提供精确的乘法运算。

	// =======================

	public static double mul(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	public static double mul(String v1, String v2, String v3) {
		return mul(Double.toString(mul(v1, v2)), v3);
	}

	public static double mul(double v1, double v2) {
		return mul(Double.toString(v1), Double.toString(v2));
	}

	public static double mul(double v1, double v2, double v3) {
		return mul(mul(v1, v2), v3);
	}

	// =======================
	// 提供（相对）精确的除法运算的乘法运算。

	// =======================

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @return v1/v2
	 */
	public static double div(String v1, String v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @return v1/v2
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return v1/v2
	 */
	public static double div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return v1/v2
	 */
	public static double div(double v1, double v2, int scale) {
		return div(Double.toString(v1), Double.toString(v2), scale);
	}

	// =======================
	// 提供精确的小数位四舍五入处理。

	// =======================

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(String v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		return round(Double.toString(v), scale);
	}

}
