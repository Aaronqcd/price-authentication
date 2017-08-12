/**    
 * description: 
 * Create on Sep 8, 2008 4:26:41 PM    
 *      
 */
package com.pemng.serviceSystem.base.util;

import java.math.BigDecimal;

/**
 * @author luohanbin</a>
 * @version 1.0
 */

public class NumeralUtils {

	public static final Integer ZERO_INTEGER = new Integer(0);
	public static final Double ZERO = new Double(0);

	public static boolean isNumber(String obj) {
		if (obj == null)
			return false;

		char[] chars = obj.toCharArray();

		if (chars.length == 0)
			return false;

		for (int i = 0; i < chars.length; i++) {
			if (!Character.isDigit(chars[i]))
				return false;
		}

		return true;
	}

	/**
	 * ��������
	 * 
	 * @param value
	 *            ��������ֵ
	 * @param scale
	 *            ����
	 * @return
	 */
	public static double round(double value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(value));
		return b.divide(ROUND_HELP_BIGDECIMAL, scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	public static double maskNull(Double d) {
		if (d == null) {
			return 0d;
		}
		return d.doubleValue();
	}

	public static Double getNoNullUseZeroMask(Double d) {
		if (d == null) {
			d = ZERO;
		}
		return d;
	}

	public static long maskNull(Long val) {
		if (val == null) {
			return 0l;
		}
		return val.longValue();
	}

	/**
	 * �ж��Ƿ�������
	 * 
	 * @param strV
	 *            ������ַ�
	 * @param digitN
	 *            �����λ��
	 * @return
	 */
	public static boolean isNumber(String strV, int digitN) {
		int tem = digitN - 1;
		return strV.matches("([0-9]\\d{0," + tem + "})");
	}

	/**
	 * �ж�����������Ƿ��ǹ̶���λ��
	 * 
	 * @param strV
	 * @param digitN
	 * @return
	 */
	public static boolean isSolidNumber(String strV, int digitN) {
		int tem = digitN - 1;
		return strV.matches("([0-9]\\d{" + tem + "})");
	}

	/**
	 * �ж��Ƿ����������С��
	 * 
	 * @param strV
	 *            ������ַ�
	 * @param digitN
	 *            ���������λ��
	 * @param digitS
	 *            �����С��λ��
	 * @return
	 */
	public static boolean isDecimal(String strV, int digitN, int digitS) {
		int tem = digitN - 1;
		return strV.matches("([0-9]\\d{0," + tem + "})(\\.\\d{0," + digitS
				+ "})?");
	}

	/**
	 * �ж��Ƿ����������С��,���?��
	 * 
	 * @param strV
	 * @param digitN
	 * @param digitS
	 * @param isNegative
	 * @return
	 */
	public static boolean isDecimal(String strV, int digitN, int digitS,
			boolean isNegative) {
		int tem = digitN - 1;
		if (isNegative)
			return strV.matches("(-)?([0-9]\\d{0," + tem + "})(\\.\\d{0,"
					+ digitS + "})?");
		else
			return strV.matches("([0-9]\\d{0," + tem + "})(\\.\\d{0," + digitS
					+ "})?");
	}

	private static final BigDecimal ROUND_HELP_BIGDECIMAL = new BigDecimal("1");

	/**
	 * �ж�(a1,a2)����Ƿ���(b1,b2)�ص�
	 * 
	 * @param a1
	 * @param a2
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean inScope(double a1, double a2, double b1, double b2) {
		if ((a1 < b1 && a2 < b1) || (a1 > b2 && a2 > b2)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �ж�(a1,a2]����Ƿ���(b1,b2]�ص�,��˰��׼ר��
	 * 
	 * @param a1
	 * @param a2
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean inScopeForTax(double a1, double a2, double b1,
			double b2) {
		if ((a1 < b1 && a2 <= b1) || (a1 >= b2 && a2 > b2)) {
			return false;
		} else {
			return true;
		}
	}

	public static BigDecimal add(BigDecimal one, BigDecimal two) {
		if (one == null) {
			return two;
		} else {
			if (two == null) {
				return one;
			} else {
				return one.add(two);
			}
		}
	}

	public static BigDecimal divide(BigDecimal one, BigDecimal two) {

		return one.divide(two, BigDecimal.ROUND_HALF_EVEN);

	}

	public static BigDecimal multiply(BigDecimal one, BigDecimal two) {
		if (one == null)
			return two;
		if (two == null)
			return one;
		return one.multiply(two);

	}

	public static void main(String[] args) {
		// String s = "-023";
		// System.out.println(Integer.parseInt(s));
		// System.out.println(isNumber(s));
		//		
		// s = " 23";
		// Integer.parseInt(s);
		System.out.println(inScope(80, 90, 40, 80));
		System.out.println(inScopeForTax(80, 90, 40, 80));
		System.out.println(isDecimal("-988", 3, 0, true));
	}
}
