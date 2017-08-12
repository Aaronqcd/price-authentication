package com.pemng.serviceSystem.base.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;

public class BeanCopyUtils extends BeanUtils {
	static {
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
		ConvertUtils.register(new DateConvert(), java.sql.Date.class);
		ConvertUtils.register(new LongConverter(null), java.lang.Long.class);
		ConvertUtils.register(new IntegerConverter(null),
				java.lang.Integer.class);
		ConvertUtils
				.register(new DoubleConverter(null), java.lang.Double.class);
		ConvertUtils.register(new FloatConverter(null), java.lang.Float.class);
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
	}

	public static void copyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
	}

	public static void copySamePerfixProperties(Object source, Object target,
			String perfix) {

	}

	static class DateConvert implements Converter {

		public static final String[] DATE_FORMAT = { "yyyy-MM-dd HH:mm:ss",
				"yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd", "yyyyMMdd" };

		public static final int dayWeek = 6; // 一周7天

		public Object convert(Class arg0, Object arg1) {

			if (arg0.isInstance(arg1)) {
				return arg1;
			}
			try {

				String p = (String) arg1;
				if (p == null || p.trim().length() == 0) {
					return null;
				}
				for (int i = 0; i < DATE_FORMAT.length; i++) {

					try {
						SimpleDateFormat df = new SimpleDateFormat(
								DATE_FORMAT[i]);
						return df.parse(p.trim());
					} catch (Exception e) {

					}
				}
			} catch (Exception e) {

			}
			return null;
		}

		/**
		 * 
		 * @param date
		 *            20100101
		 * @return 2010-01-01
		 */
		public static String convertDate(String date) {
			String str = "";
			if (org.apache.commons.lang.StringUtils.isNotBlank(date)) {
				return str;
			}
			String y = date.substring(0, 4);
			String m = date.substring(4, 6);
			String d = date.substring(6, 8);

			str = y + "-" + m + "-" + d;
			return str;
		}

		/**
		 * 
		 * @return
		 */
		public static List getDayInWeek(Date date) {
			if (date == null) {
				return null;
			}
			List list = new ArrayList();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			Date d = cal.getTime();
			list.add(d);
			for (int i = 0; i < dayWeek; i++) {
				cal.add(Calendar.DATE, 1);
				Date dateTemp = cal.getTime();
				list.add(dateTemp);
			}
			return list;
		}

		/**
		 * 判断当天日期是否在周末
		 * 
		 * @param date
		 * @return
		 */
		public static boolean isWeekEnd(Date date) {
			if (date == null) {
				return false;
			}
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
					"星期六" };
			// 一周中的第几天(国外周日是第一天,所以需要减1)
			int n = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (n < 0)
				n = 0;

			if (n == 0 || n == 6) {
				return true;
			}
			return false;
		}

		/**
		 * 对指定的日期加上天数
		 * 
		 * @param date
		 *            日期
		 * @param dateCount
		 *            天数
		 * @return
		 */
		public static String dateAdd(Date date, String dateCount) {
			// 日期处理模块 (将日期加上某些天或减去天数)返回字符串

			int strTo;
			try {
				strTo = Integer.parseInt(dateCount);
			} catch (Exception e) {
				System.out.println("日期标识转换出错! :\n::: " + date + "不能转为数字型 ");
				e.printStackTrace();
				strTo = 0;
			}
			Calendar strDate = Calendar.getInstance();// java.util包

			if (date != null) {
				strDate.setTime(date);
			}
			strDate.add(strDate.DATE, strTo);// 日期减 如果不够减会将月变动
			// 生成 (年-月-日字符串
			String meStrDate = strDate.get(strDate.YEAR) + "-"
					+ String.valueOf(strDate.get(strDate.MONTH) + 1) + "-"
					+ strDate.get(strDate.DATE);
			return meStrDate;
		}

		public static void test() {
			System.out.println("11111111111111");
		}
	}
}
