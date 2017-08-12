package com.pemng.serviceSystem.base.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/*
 * =============================================
 * Copyright 2006 IBM AMS
 *
 * Change Revision
 * ---------------------------------------------
 *   Date     Author        Remarks
 *  Jul 03, 2006   Zhang Feng   Initial Creation
 * =============================================
 */
/**
 * 日期工具类
 */
/**
 * Created on Apr 24, 2012
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 */
public class DateUtil {

	public final static String CN_DATE_FORMAT = "yyyy-MM-dd";

	public final static String CN_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public final static String CN_TIME_FORMAT = "HH:mm:ss";
	
	public final static String CN_EXCEL_DATE_FORMAT = "yyyy.MM.dd";

	public final static String CN_SHORT_TIME_FORMAT = "HH:mm";

	public static final String DDMONTHYYYY_DATE_FORMAT = "dd MMM yyyy";

	public static final String YYMM_DATE_FORMAT = "yyMM";
	
	public static final String YYYY_DATE_FORMAT = "yyyy";
	
	public static final String YYYYMMDD_DATE_FORMAT = "yyyyMMdd";

	public static final String YYYYMM_DATE_FORMAT = "yyyy-MM";

	public static final int CAL_YR_POSITION = 2;

	public static final int CAL_MTH_POSITION = 1;

	public static final int CAL_DD_POSITION = 0;
	
	
	/*
	 * public static java.sql.Timestamp getTimestampDDMMYYYY(String s){
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat(CN_DATE_FORMAT);
	 * java.util.Date date = sdf.parse(s, new ParsePosition(0));
	 * 
	 * return new Timestamp(date.getTime()); }
	 */

	/**
	 * Function Name               getCurrentDate                                   
	 * @param                      日期格式
	 * @return                     当前日期
	 * @description                获取当前日期
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Apr 24, 2012             chenguanjun         Initial
	 **********************************************************************
	 */
	public static java.util.Date getCurrentDate(String formatStr) {
		java.util.Date currentDate = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
		String dateStr = dateFormate.format(cal.getTime());
		try {
			currentDate = dateFormate.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return currentDate;
	}
	/**
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static java.sql.Timestamp getTimestampDDMMYYYY(String s)
			throws Exception {

		if (s.length() != 10) {
			throw new Exception("Invalid Date Format");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(CN_DATE_FORMAT);
		sdf.setLenient(false);
		java.util.Date date = sdf.parse(s, new ParsePosition(0));

		return new Timestamp(date.getTime());

	}

	// no exception thrown

	/**
	 * @param s
	 * @return
	 */
	public static java.sql.Timestamp getTimestampFromDDMMYYYY(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat(CN_DATE_FORMAT);
		sdf.setLenient(false);
		java.util.Date date = sdf.parse(s, new ParsePosition(0));
		return new Timestamp(date.getTime());

	}
	

	/**
	 * This method is used to get the Oracle date String for database queries
	 * 
	 * @param t
	 * @return
	 */
	public static String getOracleDateStr(Timestamp t) {

		final String format = "YYYY-MM-DD";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer convertedDate = new StringBuffer();
		convertedDate.append("'" + sdf.format(t) + "'");
		convertedDate.append(",");
		convertedDate.append("'" + format + "'");
		return convertedDate.toString();
	}

	/**
	 * This method is used to get the Oracle date in month and year
	 * 
	 * @param t
	 * @return
	 */
	public static String getOracleMonthYear(Timestamp t) {

		final String format = "MM YYYY";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		StringBuffer convertedDate = new StringBuffer();
		convertedDate.append("'" + sdf.format(t) + "'");
		convertedDate.append(",");
		convertedDate.append("'" + format + "'");
		return convertedDate.toString();

	}

	/**
	 * This method will give a date string that will reflect the addition of
	 * month to the given month and year
	 * 
	 * @param month
	 * @param year
	 * @param noOfMth
	 * @return String
	 */
	public static String addOracleMonth(String month, String year,
			String noOfMth) {
		final String format = "MM YYYY";
		StringBuffer sb = new StringBuffer();
		sb.append("ADD_MONTHS((TO_DATE('");
		sb.append(month);
		sb.append(" ");
		sb.append(year + "','");
		sb.append(format);
		sb.append("')),");
		sb.append(noOfMth);
		sb.append(")");
		return sb.toString();
	}

	/**
	 * This method will get the system date
	 * 
	 * @return
	 */
	public static Timestamp getSysdate() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * This method will check if the timestamp is of the same date
	 * 
	 * @param ts1
	 * @param ts2
	 * @return true if ts1 and ts2 is null or equal in DD/MM/YYYY format
	 */
	public static boolean isSameDate(Timestamp ts1, Timestamp ts2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		if (ts1 == null) {
			if (ts2 == null) {
				return true;
			} else {
				return false;
			}
		} else if (ts2 == null) {
			return false;
		}

		cal1.setTime(new java.util.Date(ts1.getTime()));
		cal2.setTime(new java.util.Date(ts2.getTime()));

		if ((cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE))
				&& (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
				&& (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * This method will check if the timestamp is of the same date
	 * 
	 * @param ts1
	 * @param ts2
	 * @return true if ts1 and ts2 is null or equal in DD/MM/YYYY format
	 */
	public static boolean isSameDate2(java.util.Date  ts1, java.util.Date  ts2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		if (ts1 == null) {
			if (ts2 == null) {
				return true;
			} else {
				return false;
			}
		} else if (ts2 == null) {
			return false;
		}

		cal1.setTime(ts1);
		cal2.setTime(ts2);

		if ((cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE))
				&& (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
				&& (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method will compare the date
	 * 
	 * @param ts1
	 * @param ts2
	 * @return 1 if ts1 > ts2 else -1
	 */
	public static int compareDate(Timestamp ts1, Timestamp ts2) {
		if (isSameDate(ts1, ts2)) {
			return 0;
		}

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.setTime(new java.util.Date(ts1.getTime()));
		cal2.setTime(new java.util.Date(ts2.getTime()));

		if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
			return 1;
		} else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
			return -1;
		} else {
			if (cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
				return 1;
			} else if (cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
				return -1;
			} else {
				if (cal1.get(Calendar.DATE) > cal2.get(Calendar.DATE)) {
					return 1;
				} else if (cal1.get(Calendar.DATE) < cal2.get(Calendar.DATE)) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * This method will compare the date
	 * 
	 * @param date
	 * @param date2
	 * @return 1 if ts1 > ts2 else -1
	 */
	public static int compareDate2(java.util.Date date, java.util.Date date2) {
		if (isSameDate2(date, date2)) {
			return 0;
		}

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.setTime(date);
		cal2.setTime(date2);

		if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
			return 1;
		} else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
			return -1;
		} else {
			if (cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
				return 1;
			} else if (cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
				return -1;
			} else {
				if (cal1.get(Calendar.DATE) > cal2.get(Calendar.DATE)) {
					return 1;
				} else if (cal1.get(Calendar.DATE) < cal2.get(Calendar.DATE)) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	/**
	 * This method will get the Calendar year in yyyy format
	 * 
	 * @return
	 */
	public static String getCurrentYear() {

		Calendar cal1 = Calendar.getInstance();
		Timestamp currentTime = getSysdate();// new
		// Timestamp(System.currentTimeMillis());

		cal1.setTime(new java.util.Date(currentTime.getTime()));
		Integer year = new Integer(cal1.get(Calendar.YEAR));
		return year.toString();
	}

	/**
	 * This method will get the Calendar year in yy format
	 * 
	 * @return
	 */
	public static String getCurrentYY() {
		String yyyy = getCurrentYear();
		return yyyy.substring(2);
	}

	/**
	 * This method will get the current month
	 * 
	 * @return
	 */
	public static String getCurrentMM() {
		Calendar cal = Calendar.getInstance();
		Timestamp currentTime = getSysdate();
		cal.setTime(new java.util.Date(currentTime.getTime()));
		Integer month = new Integer(cal.get(Calendar.MONTH) + 1);
		return month.toString();
	}

	/**
	 * This method will get the current day
	 * 
	 * @return
	 */
	public static String getCurrentMonthDay() {
		Calendar cal = Calendar.getInstance();
		Timestamp currentTime = getSysdate();
		cal.setTime(new java.util.Date(currentTime.getTime()));
		Integer month = new Integer(cal.get(Calendar.DAY_OF_MONTH));
		return month.toString();
	}

	/**
	 * This method will get the date in DD/MM/YYYY format
	 * 
	 * @param ptsDate
	 * @return
	 */
	public static String getDDMMYYYYStr(Timestamp ptsDate) {
		final String DF_DDMMYYYY = CN_DATE_FORMAT;
		DateFormat oDateFormat = new SimpleDateFormat(DF_DDMMYYYY);

		if (ptsDate == null) {
			return "";
		}
		return oDateFormat.format(ptsDate);
	}

	public static String getDDMMMYYYYStr(Timestamp ptsDate) {
		DateFormat oDateFormat = new SimpleDateFormat("dd MMM yyyy");
		String dateStr = "";
		if (ptsDate == null) {
			return "";
		} else {
			dateStr = oDateFormat.format(ptsDate);
			if (dateStr.startsWith("0")) {
				dateStr = dateStr.substring(1, dateStr.length());
			}
		}
		return dateStr;
	}

	/**
	 * This method will get the time in DD/MM/YYYY hh:mm am/pm format
	 * 
	 * @param ptsDate
	 * @return
	 */
	public static String getDDMMYYYYHHMMStr(Timestamp ptsDate) {
		final String DF_DDMMYYYY = CN_DATE_FORMAT;
		final String TF_HHMM = " hh:mm a";
		DateFormat oDateFormat = new SimpleDateFormat(DF_DDMMYYYY + TF_HHMM);

		if (ptsDate == null) {
			return "";
		}
		return oDateFormat.format(ptsDate);
	}

	/**
	 * Convert date and time in String format into Timestamp format.
	 * 
	 * @param psDay -
	 *            the String of the day.
	 * @param psMonth -
	 *            the String of the month.
	 * @param psYear -
	 *            the String of the year.
	 * @param psHour -
	 *            the String of the hour.
	 * @param psMinutes -
	 *            the String of the minutes.
	 * @return a timestamp of date and time.
	 */
	public static Timestamp getTimeStamp(String psDay, String psMonth,
			String psYear, String psHour, String psMinutes) {
		StringBuffer sb = new StringBuffer();
		sb.append(psYear).append("-").append(psMonth).append("-").append(psDay)
				.append(" ");
		sb.append(psHour).append(":").append(psMinutes).append(":0.0");
		return Timestamp.valueOf(sb.toString());
	}

	/**
	 * This methoid will get the system date string
	 * 
	 * @return
	 */
	public static String getSysdateString() {
		return new SimpleDateFormat(CN_DATE_FORMAT).format(getSysdate());
	}
	public static String getSysdateString(String format) {
	    return new SimpleDateFormat(format).format(getSysdate());
	}

	/**
	 * @return
	 */
	public static String getCurrentAMPM() {
		return getAMPM(System.currentTimeMillis());
	}

	public static String getCurrentHourMin() {
		return getHHMMFromDate(Calendar.getInstance().getTime());
	}

	public static String getHHMMFromDate(java.util.Date date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(CN_SHORT_TIME_FORMAT);

		return sdf.format(date);

	}

	/**
	 * @param ts
	 * @return returm AM or PM
	 */
	public static String getAMPM(Timestamp ts) {
		return getAMPM(ts.getTime());
	}

	public static String getAMPM(long time) {
		Calendar cal = Calendar.getInstance(Locale.UK);
		cal.setTime(new Date(time));
		int ampm = cal.get(Calendar.AM_PM);
		return ampm == Calendar.AM ? "AM" : "PM";
	}

	/**
	 * @param date
	 * @return int[] where 0 - day, 1 - month, 2 - year
	 */
	public static int[] getParseDate(Timestamp date) {

		String dateStr = getDDMMYYYYStr(date);
		StringTokenizer st = new StringTokenizer(dateStr, "/");
		int[] dateArray = new int[3];
		int iDD = Integer.parseInt(st.nextToken());
		int iMM = Integer.parseInt(st.nextToken()) - 1;
		int iYYYY = Integer.parseInt(st.nextToken());
		GregorianCalendar c = new GregorianCalendar(iYYYY, iMM, iDD);

		dateArray[0] = c.get(Calendar.DAY_OF_MONTH);
		dateArray[1] = c.get(Calendar.MONTH) + 1;
		dateArray[2] = c.get(Calendar.YEAR);
		return dateArray;
	}

	/**
	 * This method will get the date in the format format Eg. 10 Jun 2004
	 * 
	 * @param t
	 * @return
	 */
	public static String getDDMONTHYYYYStr(Timestamp t) {

		SimpleDateFormat sdf = new SimpleDateFormat(DDMONTHYYYY_DATE_FORMAT);
		StringBuffer formattedDate = new StringBuffer();
		formattedDate = sdf.format(t, formattedDate, new FieldPosition(0));
		return formattedDate.toString();
	}

	/**
	 * 
	 * @param baseDate -
	 *            must be in dd/mm/yyyy format
	 * @param noOfDays
	 * @return null if baseDate is null
	 */
	public static Timestamp getOffsetDate(Timestamp baseDate, int noOfDays) {

		String sysDateStr = "";

		if (baseDate == null) {
			return null;
		}
		try {
			sysDateStr = DateUtil.getDDMMYYYYStr(baseDate);
		} catch (Exception e) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(sysDateStr, "/");
		String dd = st.nextToken();
		String mm = st.nextToken();
		String yyyy = st.nextToken();
		GregorianCalendar c = new GregorianCalendar(Integer.parseInt(yyyy),
				Integer.parseInt(mm) - 1, Integer.parseInt(dd));
		c.add(Calendar.DAY_OF_MONTH, noOfDays);

		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		month += 1;
		int year = c.get(Calendar.YEAR);

		if (day < 10)
			dd = "0" + day;
		else
			dd = "" + day;
		if (month < 10)
			mm = "0" + month;
		else
			mm = "" + month;

		yyyy = "" + year;

		String expDateStr = dd + "/" + mm + "/" + yyyy;
		Timestamp newOffsetTime = null;
		try {
			newOffsetTime = DateUtil.getTimestampDDMMYYYY(expDateStr);
		} catch (Exception e) {
			newOffsetTime = DateUtil.getSysdate();
		}
		return newOffsetTime;

	}

	/**
	 * Added by Della
	 */
	/**
	 * This method will check if the calendars is of the same date
	 * 
	 * @param cal1
	 * @param cal2
	 * @return true if equal in DD/MM/YYYY format
	 */
	public static boolean isSameDate(Calendar cal1, Calendar cal2) {
		if ((cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE))
				&& (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
				&& (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method will compare the date
	 * 
	 * @param cal1
	 * @param cal2
	 * @return 1 if cal1 > cal2 else -1
	 */
	public static boolean compareDate(Calendar cal1, Calendar cal2) {
		if (isSameDate(cal1, cal2)) {
			return true;
		}

		if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
			return true;
		} else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
			return false;
		} else {
			if (cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
				return true;
			} else if (cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
				return false;
			} else {
				if (cal1.get(Calendar.DATE) > cal2.get(Calendar.DATE)) {
					return true;
				} else if (cal1.get(Calendar.DATE) < cal2.get(Calendar.DATE)) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

	// 只比较到月份
	// 大则返回true，小则返回false
	public static int compareDateToMonth(java.util.Date date1,
			java.util.Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
			return 1;
		} else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
			return -1;
		} else {
			if (cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH)) {
				return 1;
			} else if (cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH)) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public static String getYYYYMMDDFromDate(java.util.Date date) {

		if (date == null)
			return null;

		DateFormat df = new SimpleDateFormat(CN_DATE_FORMAT);

		return df.format(date);

	}

	public static String getYYYYMMFromDate(java.util.Date date) {

		if (date == null) {
			return null;
		}

		DateFormat df = new SimpleDateFormat(YYYYMM_DATE_FORMAT);

		return df.format(date);

	}

	public static String getYYYYMMDDHHMMSSFromDate(java.util.Date date) {

		if (date == null) {
			return null;
		}

		DateFormat df = new SimpleDateFormat(CN_DATETIME_FORMAT);

		return df.format(date);

	}
	
	public static int getYYYYFromDate(java.util.Date date) {
		if (date == null) {
			return 0;
		}
		DateFormat df = new SimpleDateFormat(YYYY_DATE_FORMAT);
		String s = df.format(date);
		return Integer.parseInt(s);
	}

	public static String getYYMMFromDate(java.util.Date date) {

		if (date == null) {
			return null;
		}

		DateFormat df = new SimpleDateFormat(YYMM_DATE_FORMAT);

		return df.format(date);

	}

	public static java.util.Date getDateFromYYYYMMDD(String dateString)
			throws ParseException {

		DateFormat df = new SimpleDateFormat(CN_DATE_FORMAT);
		df.setLenient(false);

		return df.parse(dateString);

	}
	
	public static java.util.Date getDateFromToYYYYMMDD(String dateString)
		throws ParseException {

		DateFormat df = new SimpleDateFormat(YYYYMMDD_DATE_FORMAT);
		df.setLenient(false);
		
		return df.parse(dateString);
	
	}
	
	public static java.util.Date getExcelDateFromYYYYMMDD(String dateString)
	throws ParseException {

        DateFormat df = new SimpleDateFormat(CN_EXCEL_DATE_FORMAT);
        df.setLenient(false);

        return df.parse(dateString);

    }

	public static java.util.Date getDateFromString(String dateString,
			String format) throws ParseException {

		DateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);

		return df.parse(dateString);

	}
	
	public static String getStringFromDate(java.util.Date date, String format){
		if (date == null) {
			return null;
		}

		DateFormat df = new SimpleDateFormat(format);

		return df.format(date);
	}

	public static java.util.Date getDateFromYYYYMMDDHHMMSS(String dateString)
			throws ParseException {

		DateFormat df = new SimpleDateFormat(CN_DATETIME_FORMAT);
		df.setLenient(false);

		return df.parse(dateString);

	}

	public static java.util.Date getNewerDate(java.util.Date date1,
			java.util.Date date2) {

		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;

		return date1.after(date2) ? date1 : date2;
	}

	public static java.util.Date getOlderDate(java.util.Date date1,
			java.util.Date date2) {

		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;

		return date1.before(date2) ? date1 : date2;
	}

	public static java.util.Date getOneAfterDay(java.util.Date oriDate) {

		if (oriDate == null)
			return null;

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(oriDate);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();

	}

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return in long,if either time null then return -1;
	 */
	public static long getTimeDiffSeconds(java.util.Date startTime,
			java.util.Date endTime) {

		if (startTime == null || endTime == null)
			return -1;

		long secondsUsed = (endTime.getTime() - startTime.getTime()) / 1000;
		return secondsUsed;
	}

	/**
	 * 
	 * @return hhH mm'ss" if input<0 return "NULL"
	 */
	public static String getFormatedJobTime(long secondsInAll) {

		if (secondsInAll < 0)
			return "NULL";

		int hours = (int) (secondsInAll / (60 * 60));
		int minutes = (int) ((secondsInAll / 60) % 60);
		int seconds = (int) (secondsInAll % 60);

		return hours + "H " + minutes + "'" + seconds + "\"";
	}

	/**
	 * get a one day addtional date to meet the to date query
	 * 
	 * @param toDate
	 *            the input date. should be at 0:00
	 * @return
	 */
	public static java.util.Date toDateForQuery(java.util.Date toDate) {
		if (toDate == null)
			return null;

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(toDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

	public static java.util.Date getLastMonthDate(java.util.Date toDate) {
		if (toDate == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * function: 获取两个时间中较大的一个时间.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static java.util.Date getBigDate(java.util.Date date1,
			java.util.Date date2) {
		java.util.Date bigDate = null;
		if (null != date1 && null != date2) {
			int i = date1.compareTo(date2);
			if (i >= 0) {
				bigDate = date1;
			} else {
				bigDate = date2;
			}
		} else if (null == date1) {
			bigDate = date2;
		} else if (null == date2) {
			bigDate = date1;
		}
		return bigDate;
	}
	
	public static java.util.Date getFirstMonthDate(java.util.Date date){
		java.util.Date returnDate = null;
		if(date != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			returnDate = calendar.getTime();
		}
		
		return returnDate;
	}

	public static void main(String args[]) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date date1 = sdf.parse("2008-01-02");
			java.util.Date date2 = sdf.parse("2008-01-01");
			System.out.println(DateUtil.moveDay(date1,-1) + "");
			System.out.println(DateUtil.moveDay(date2,-1) + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在“今天”的基础上，取得前num天或者后num天的日期
	 * 例如，假设今天是:2008-12-10, 如果num等于5，返回2008-12-15；如果num等于-3，返回2008-12-07
	 */
	public static java.util.Date moveDay(int num) {

		Calendar calendar = new GregorianCalendar();
//		calendar.set(Calendar.HOUR, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.add(Calendar.DAY_OF_MONTH, num);
		return calendar.getTime();
	}
	
	//----------add by ChenXiaoming@2010/02/21------Start------------
	public static java.util.Date moveDay(java.util.Date date, int num) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, num);
		return calendar.getTime();
	}
	
	
	//----------add by ChenXiaoming@2010/02/21-------End-------------	
	
	
	/**
	 * Function Name               addYear                                   
	 * @param                      //增加或减少年数
	 * @return                     //返回增加后的日期对象
	 * @description                //在“今天”的基础上，取得前N年后的日期
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Apr 24, 2012             yunxiangfu         Initial
	 **********************************************************************
	 */
	public static java.util.Date addYear(int n){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		calendar.add(Calendar.YEAR, n);
		return calendar.getTime();
	}
	/**
	 * Function Name               addYear                                   
	 * @param                      //日期对象                        
	 * @param                      //增加或减少年数
	 * @return                     //返回增加后的日期对象
	 * @description                //主要用于对时间对象增加年		             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Apr 24, 2012             yunxiangfu         Initial
	 **********************************************************************
	 */
	public static java.util.Date addYear(java.util.Date date,int n){
		if(date == null)return date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, n);
		return calendar.getTime();
	}
	/**
	 * Function Name               addMonth                                   
	 * @param                      //日期对象                        
	 * @param                      //增加或减少月数
	 * @return                     //返回增加后的日期对象
	 * @description                //主要用于对时间对象增加月		             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Apr 24, 2012             yunxiangfu         Initial
	 **********************************************************************
	 */
	public static java.util.Date addMonth(java.util.Date date,int n){
		if(date == null)return date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, n);
		return calendar.getTime();
	}
	/**
	 * Function Name               getYYYYMMDDDate                                   
	 * @param                      //时间对象
	 * @return                     //返回8位的yyyyMMdd格式的日期字符串
	 * @description                //yyyyMMdd格式的日期字符串 			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Apr 24, 2012             yunxiangfu         Initial
	 **********************************************************************
	 */
	public static String getYYYYMMDDDate(java.util.Date date) {

		if (date == null)
			return null;

		DateFormat df = new SimpleDateFormat(YYYYMMDD_DATE_FORMAT);

		return df.format(date);

	}
	/**
	 * Function Name               getWeekBegEndDay                                   
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @return                     //函数返回值的说明
	 * @description                获取一周的开始结束日期
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            May 17, 2012             chenguanjun         Initial
	 **********************************************************************
	 */
	public static java.util.Date[] getWeekBegEndDay(java.util.Date weekDay){
		java.util.Date[] dates = new java.util.Date[2];
		Calendar cal = Calendar.getInstance();
		cal.setTime(weekDay);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		dates[0] = cal.getTime();
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 6);
		dates[1] = cal.getTime();
		return dates;
	}
	
	/**
	 * 判断是否闰年
	 */
	public static boolean isLeapYear(int year) {
		return ((year % 100 == 0) ? (year % 400 == 0) : (year % 4 == 0));
	}
	
	/**
	 * 获得某个月有几天
	 */
	public static int getMonthDay(int year, int month) {
		switch (month) {
			case 1 :
				return 31;
			case 2 :
				return (isLeapYear(year) ? 29 : 28);
			case 3 :
				return 31;
			case 4 :
				return 30;
			case 5 :
				return 31;
			case 6 :
				return 30;
			case 7 :
				return 31;
			case 8 :
				return 31;
			case 9 :
				return 30;
			case 10 :
				return 31;
			case 11 :
				return 30;
			case 12 :
				return 31;
			default :
				return -1;
		}
	}

	/**
	 * 获得某个月有几天
	 */
	public static int getMonthDay(String yyyyMM) {
        String yyyyStr = yyyyMM.substring(0, 4);
        String mmStr = yyyyMM.substring(4, 6);
		return getMonthDay(Integer.parseInt(yyyyStr), Integer.parseInt(mmStr));
	}

    /**
     * 获得下个月（字符串操作） eg.
     * getNextMonth("199909") return "199910"
     * getNextMonth("199912") return "200001"
     */
    public static String getNextMonth(String yyyyMM) {
        String yyyyStr = yyyyMM.substring(0, 4);
        String mmStr = yyyyMM.substring(4, 6);
        int yyyy = Integer.parseInt(yyyyStr);
        int mm = Integer.parseInt(mmStr);
        
        if (mm == 12) {
            yyyy++;
            mm = 1; 
        } else {
            mm ++;
        }
        return "" + yyyy + (mm >= 10 ? mm : "0" + mm);
    }

    public static java.util.Date clearTime(java.util.Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
    }
	/**
	 * 获得指定格式的日期时间字符串 FORMAT如"yyyy-MM-dd HH:mm:ss SSS"
	 * ex1. getStringFormated("2006-01-29", "yyyy-MM-dd", "yyyy/MM/dd")    			return "2006/01/01"
	 * ex2. getStringFormated("20060131", "yyyyMMdd", "yy_MM")    					return "06_01"
	 * ex3. getStringFormated("2006-01-29", "yyyy-MM-dd", "yyyy/MM/dd hh:mm:ss")    	return "2006/01/29 12:00:00"
	 * @author ybh 2009-07-03
	 */
	public static String getStringFormated(String inputDateStr, String inputFormat, String rtnFoamat) {
		if (inputDateStr == null || inputDateStr.trim().length() <= 0)
			return null;

		if (inputFormat == null
			|| inputFormat.trim().length() <= 0
			|| rtnFoamat == null
			|| rtnFoamat.trim().length() <= 0)
			return null;

		Calendar curCalendar = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(inputFormat);
		try {
			curCalendar.setTime(df.parse(inputDateStr));
		}
		catch (ParseException e) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(rtnFoamat);
		return sdf.format(curCalendar.getTime());
	}
	/**
	 * Function Name               initQuarterList                                   
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @return                     //函数返回值的说明
	 * @throws ParseException 
	 * @description                定义一年4个季度的时间范围  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 20, 2012             chenguanjun         Initial
	 **********************************************************************
	 */
	private static List<java.util.Date[]> initQuarterList(int year) throws ParseException{
		List<java.util.Date[]> list = new ArrayList<java.util.Date[]>(4);
		// 第一季度
		java.util.Date[] date = new java.util.Date[2];
		date[0] = DateUtil.getDateFromYYYYMMDD(year+"-01-01");
		date[1] = DateUtil.getDateFromYYYYMMDD(year+"-03-31");
		list.add(date);
		// 第二季度
		date = new java.util.Date[2];
		date[0] = DateUtil.getDateFromYYYYMMDD(year+"-04-01");
		date[1] = DateUtil.getDateFromYYYYMMDD(year+"-06-30");
		list.add(date);
		// 第三季度
		date = new java.util.Date[2];
		date[0] = DateUtil.getDateFromYYYYMMDD(year+"-07-01");
		date[1] = DateUtil.getDateFromYYYYMMDD(year+"-09-30");
		list.add(date);
		// 第四季度
		date = new java.util.Date[2];
		date[0] = DateUtil.getDateFromYYYYMMDD(year+"-10-01");
		date[1] = DateUtil.getDateFromYYYYMMDD(year+"-12-31");
		list.add(date);
		return list;
	}
	
	/**
	 * Function Name               getCurrentQuarter                                   
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @return                     //函数返回值的说明
	 * @description                获取当前日期所在季度范围  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 20, 2012             chenguanjun         Initial
	 **********************************************************************
	 */
	public static java.util.Date[] getCurrentQuarter(java.util.Date currentDate) throws ParseException{
		List<java.util.Date[]> quarterList = DateUtil.initQuarterList(currentDate.getYear());
		for(java.util.Date[] quarter : quarterList){
			if(!currentDate.before(quarter[0]) && !currentDate.after(quarter[1])){
				return quarter;
			}
		}
		return null;
	}
	/**
	 * Function Name               getPerQuarter                                   
	 * @param                      currentDate
	 * @return                     //函数返回值的说明
	 * @description                获取上一季度的日期范围  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 20, 2012             chenguanjun         Initial
	 **********************************************************************
	 */
	public static java.util.Date[] getPerQuarter(java.util.Date currentDate) throws ParseException{
		List<java.util.Date[]> quarterList = DateUtil.initQuarterList(currentDate.getYear());
		int index = 0;
		for(java.util.Date[] quarter : quarterList){
			if(!currentDate.before(quarter[0]) && !currentDate.after(quarter[1])){
				break;
			}
			index ++;
		}
		if(index == 0){// 当前为今年第一季度，上一季度为去年最后一个季度
			quarterList = DateUtil.initQuarterList(currentDate.getYear() - 1);
			return quarterList.get(3);
		} else{ // 上一个季度
			return quarterList.get(index - 1);
		}
	}
	/**
	 * Function Name               getPerQuarter                                   
	 * @param                      currentDate
	 * @return                     //函数返回值的说明
	 * @description                获取下一季度的日期范围  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 20, 2012             chenguanjun         Initial
	 **********************************************************************
	 */
	public static java.util.Date[] getNextQuarter(java.util.Date currentDate) throws ParseException{
		List<java.util.Date[]> quarterList = DateUtil.initQuarterList(currentDate.getYear());
		int index = 0;
		for(java.util.Date[] quarter : quarterList){
			if(!currentDate.before(quarter[0]) && !currentDate.after(quarter[1])){
				break;
			}
			index ++;
		}
		if(index == 3){// 当前为最后一个季度，下一季度为下一年的第一季度
			quarterList = DateUtil.initQuarterList(currentDate.getYear() + 1);
			return quarterList.get(0);
		} else{ // 下一个季度
			return quarterList.get(index + 1);
		}
	}
	/**
	 * Function Name               getDayReportTime                                   
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @return                     //函数返回值的说明
	 * @description                获取日报自动跑批时间点：每天16:30  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 25, 2012             malun         Initial
	 **********************************************************************
	 */
	public static java.util.Date getDayReportTime(java.util.Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,16);
		calendar.set(Calendar.MINUTE,30);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * Function Name               getDayReportTime                                   
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @return                     //函数返回值的说明
	 * @description                获取周报自动跑批时间点：18:00  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 25, 2012             malun         Initial
	 **********************************************************************
	 */
	public static java.util.Date getWeekReportTime(java.util.Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,18);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
