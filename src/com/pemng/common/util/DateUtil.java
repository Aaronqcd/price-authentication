
package com.pemng.common.util;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.pemng.framework.exceptions.InvalidDateException;

public class DateUtil {
	
	/**
	 * return current date.
	 * @param format eg."yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getCurrentDate(String format){
		SimpleDateFormat sdf = new SimpleDateFormat (format);
	    Date currentTime = new Date();
	    return sdf.format(currentTime);
	}
	
	public static String getDate(String format, Date date){
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat (format);
	    return sdf.format(date);
	}
	
	public static String getFileName(int year,int month,int day){
		Calendar cal = Calendar.getInstance();
		cal.set(year,month - 1, day, 12, 0, 0);
		return DateUtil.getNewDate(cal,"",true,true,false);
	}
	
	public static String getStringDate(int year,int month,int day){
		Calendar cal = Calendar.getInstance();
		cal.set(year,month - 1, day, 12, 0, 0);
		return DateUtil.getNewDate(cal,"-",true,true,true);
	}
	
	/**
	 * 取得指定日期的字符串型
	 * @return 返回字符串日期
	 */
	public static String getDate(Calendar date) {
		StringBuffer dateBuff = new StringBuffer();
		dateBuff.append(String.valueOf(date.get(Calendar.YEAR)));
		dateBuff.append("-");
		if(date.get(Calendar.MONTH) < 9){
			dateBuff.append("0");
		}
		dateBuff.append(String.valueOf(date.get(Calendar.MONTH) + 1));
		dateBuff.append("-");
		if(date.get(Calendar.DATE) <=  9) {
			dateBuff.append("0");
		}
		dateBuff.append(String.valueOf(date.get(Calendar.DATE)));
		return dateBuff.toString();
	}
	
	/**
	 * 取得指定日期的字符串型
	 * @return 返回字符串日期
	 */
	public static String getNewDate(Calendar date,String append,boolean year,boolean month,boolean day) {
		StringBuffer dateBuff = new StringBuffer();
		if(append == null){
			append = "";
		}
		if(year){
			dateBuff.append(String.valueOf(date.get(Calendar.YEAR)));
			dateBuff.append(append);
		}
		if(month){
			if(date.get(Calendar.MONTH) < 9){
				dateBuff.append("0");
			}
			dateBuff.append(String.valueOf(date.get(Calendar.MONTH) + 1));
			dateBuff.append(append);
		}
		if(day){
			if(date.get(Calendar.DATE) <=  9) {
				dateBuff.append("0");
			}
			dateBuff.append(String.valueOf(date.get(Calendar.DATE)));
		}
		
		return dateBuff.toString();
	}
	
	
	
	public static String getDate(){
		return getCurrentDate("yyyy-MM-dd");
	}
	
	public static String getDateTime(){
		return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 格式化数据库中日期，当返回1-01-01是，格式为0001-01-01，若isReturnNull is true，返回0000-00-00 
	 * @param inDateStr
	 * @param isReturnNull
	 * @return
	 * @default "0000-00-00"
	 */
	public static String setDateFormateStr(String inDateStr,
			boolean isReturnNull) {
		if (inDateStr != null) {
			if (inDateStr.length() == 10)
				return (isReturnNull && inDateStr.equals("0001-01-01")) ? "0000-00-00"
						: inDateStr;
			else {
				int size = inDateStr.length();
				String str = "0000".concat(inDateStr).substring(4, 4 + size);
				return (isReturnNull && str.equals("0001-01-01")) ? "0000-00-00"
						: inDateStr;
			}
		} else {
			return "0000-00-00";
		}
	}
	
	public static Date get9999Date(){
		Calendar cal = Calendar.getInstance();
		cal.set(9999,12-1,31);
		return cal.getTime();
	}
	
    public static int getDateDiffDays(Date first,Date second) {
        final long ONE_DATE_MILLES=24*3600*1000;
        long miles_first = first.getTime();
        long miles_second = second.getTime();
        miles_first = (miles_first/ONE_DATE_MILLES)*ONE_DATE_MILLES;
        miles_second = (miles_second/ONE_DATE_MILLES)*ONE_DATE_MILLES;
        return (int) ((miles_first-miles_second)/ONE_DATE_MILLES);
    }
    
	public static Date setDate(String str, String format) {
		if (str == null || str.trim().length() == 0)
			return null;

		if ("yyyy-MM-dd".equals(format) && str.trim().length() == 10) {
				Calendar cal = Calendar.getInstance();
				cal.set(Integer.parseInt(str.substring(0, 4)), Integer
						.parseInt(str.substring(5, 7))-1, Integer.parseInt(str
						.substring(8, 10)));
				return cal.getTime();
		} else if ("yyyy-MM-dd HH:mm:ss".equals(format) && str.trim().length() == 19) {
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(str.substring(0, 4)), Integer
					.parseInt(str.substring(5, 7))-1, Integer.parseInt(str
							.substring(8, 10)), Integer.parseInt(str.substring(11,13)),
							Integer.parseInt(str.substring(14,16)),
							Integer.parseInt(str.substring(17,19)));
			return cal.getTime();
		} else if ("HH:mm:ss".equals(format) && str.trim().length() == 8) {
			Calendar cal = Calendar.getInstance();
			cal.set(9999, 11, 31, Integer.parseInt(str.substring(0,2)),
							Integer.parseInt(str.substring(3,5)),
							Integer.parseInt(str.substring(6,8)));
			return cal.getTime();
		} else {
			return null;
		}
	}
	
	/**
	 * 取得指定日期的dayCount天后的日期
	 * @return 返回日期
	 */
	public static String getDateAfter(String beginDate, int dayCount) {
		Calendar begin = new GregorianCalendar();

		int[] tempArr = new int[3];
		int i = 0;
		StringTokenizer st = new StringTokenizer(beginDate, "-");
		while (st.hasMoreTokens()) {
			if(i >= 3){
				throw new IllegalArgumentException("输入的指定日期不规范：" + beginDate);
			}
			tempArr[i ++] = Integer.parseInt(st.nextToken());
		}

		begin.set(tempArr[0],
		          tempArr[1] - 1,
				  tempArr[2] + dayCount, 12, 0, 0);

		return getDate(begin);
	}
	
	/**
	 * 取得指定日期的dayCount天前的日期
	 * @return 返回日期
	 */
	public static String getDateBefore(String beginDate, int dayCount) {
		Calendar begin = new GregorianCalendar();

		int[] tempArr = new int[3];
		int i = 0;
		StringTokenizer st = new StringTokenizer(beginDate, "-");
		while (st.hasMoreTokens()) {
			if(i >= 3){
				throw new IllegalArgumentException("输入的指定日期不规范：" + beginDate);
			}
			tempArr[i ++] = Integer.parseInt(st.nextToken());
		}

		begin.set(tempArr[0],
				  tempArr[1] - 1,
				  tempArr[2] - dayCount, 12, 0, 0);

		return getDate(begin);
	}

	 private static void checkNullDate(Object value) throws InvalidDateException {
         if(value == null){
                 throw new InvalidDateException("null");
         }
	 }

	 public static Date stringToDate(String string) throws InvalidDateException {
         checkNullDate(string);
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         Date date = formatter.parse(string, new ParsePosition(0));
         if(date == null){
                 throw new InvalidDateException(string);
         }
         return date;
	 }
	 public static Date stringToTime(String string) throws InvalidDateException {
         checkNullDate(string);
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date date = formatter.parse(string, new ParsePosition(0));
         if(date == null){
                 throw new InvalidDateException(string);
         }
         return date;
	 }
	
	 public static Calendar stringToCalendar(String string) throws InvalidDateException {
         checkNullDate(string);
         Calendar c = new GregorianCalendar();
         c.setTime(stringToDate(string));
         return c;
	 }
	 
	/**
	 * 取得指定日期的之间的天数
	 * @return 返回天数
	 */
	public static int getDaysBetween(String beginDate, String endDate) {
		Calendar begin = getCalendar(beginDate);
		Calendar end = getCalendar(endDate);
		
		long beginMS = begin.getTime().getTime();
		long endMS = end.getTime().getTime();
//		long scope = Math.abs(endMS - beginMS) + 10000000; //略微增大除数
		long scope = endMS - beginMS ; 
		if(scope<0){
			scope = 0;
		}else{
			scope = endMS - beginMS + 10000000; //略微增大除数
		}
		return (int)(scope/(1000*3600*24));
	}
	
	
	/**
	 * 取得指定日期的之间的天数
	 * @return 返回天数
	 */
	public static int getRealDaysBetween(String beginDate, String endDate) {
		Calendar begin = getCalendar(beginDate);
		Calendar end = getCalendar(endDate);
		long beginMS = begin.getTime().getTime();
		long endMS = end.getTime().getTime();
		long scope = endMS - beginMS ; 
		return (int)(scope/(1000*3600*24));
	}
	
	

    public static int getMonthsBetween(String beginDate, String endDate) throws InvalidDateException {
    	Calendar begin = stringToCalendar(beginDate);
    	Calendar end = stringToCalendar(endDate);
    	int bYear = begin.get(Calendar.YEAR);
    	int eYear = end.get(Calendar.YEAR);
    	int bMonth = begin.get(Calendar.MONTH);
    	int eMonth = end.get(Calendar.MONTH);

    	return Math.abs((bYear-eYear)*12 + bMonth - eMonth);
   }

   public static int getYearsBetween(String beginDate, String endDate) throws InvalidDateException {
    	Calendar begin = stringToCalendar(beginDate);
    	Calendar end = stringToCalendar(endDate);
    	int bYear = begin.get(Calendar.YEAR);
    	int eYear = end.get(Calendar.YEAR);

    	return Math.abs(bYear-eYear);
   }
   
	/**
	 * getCalendar
	 * @param date
	 * @return
	 * Created on: 2005-1-27 13:52:12
	 */
	public static Calendar getCalendar(String date) {
		int[] tempArr = new int[6];
		int[] isDateTime = new int[1];
		if(checkDateValidity(date, tempArr, isDateTime) == true){
			Calendar inDate = new GregorianCalendar();
			if(isDateTime[0] == 0){
				inDate.set(tempArr[0], tempArr[1] - 1, tempArr[2], 12, 0, 0);
			}
			else{
				inDate.set(tempArr[0], tempArr[1] - 1, tempArr[2], tempArr[3], tempArr[4], tempArr[5]);
			}
			return inDate;
		}
		else{
			throw new IllegalArgumentException("输入的指定日期不规范：" + date);
		}
	}
	
	/**
	 * 检测输入的日期字符串有效性
	 * @return 返回是否有效
	 * @add by katlao,2004-0827
	 */
	public static boolean checkDateValidity(String inDate){
		return checkDateValidity(inDate, new int[6], new int[1]);
	}
	/**
	 * 检测输入的日期字符串有效性
	 * @param inDate
	 * @param returnDateArr
	 * @param isDateTime
	 * @return
	 * Created on: 2005-1-27 13:55:46
	 * @author katlao
	 */
	public static boolean checkDateValidity(String inDate, int[] returnDateArr, int[] isDateTime){
		if(inDate == null || inDate.length() > 19){
			return false;
		}
		inDate = inDate.trim();
		int spacePos = inDate.indexOf(' ');
		boolean ifDateTime = spacePos > 0;
		String tmpDate = "";
		String tmpTime = "";
		if(ifDateTime == false){
			tmpDate = inDate;	
			isDateTime[0] = 0;
		}
		else{
			tmpDate = inDate.substring(0, spacePos);
			tmpTime = inDate.substring(spacePos+1);
			isDateTime[0] = 1;
		}
		
		//Date
		int i = 0;
		StringTokenizer st = new StringTokenizer(tmpDate, "-");
		try {
			while (st.hasMoreTokens()) {
				if(i >= 3){
					return false;
				}
				int temp = Integer.parseInt(st.nextToken());
				//System.out.println("temp() = "+temp);
				switch(i){
					case 0://check year
					    if(temp < 0 || temp > 9999)
							return false;						
						break;

					case 1://check month
						if(temp < 1 || temp > 12)
							return false;
						break;

					case 2://check day
						if(temp < 1 || temp > 31)
							return false;
						break;
				}
				returnDateArr[i] = temp;
				i ++;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		if(i != 3)
			return false;
			
		//Time
		if(ifDateTime == true){
			i = 3;
			st = new StringTokenizer(tmpTime, ":");
			try {
				while (st.hasMoreTokens()) {
					if(i >= 6){
						return false;
					}
					int temp = Integer.parseInt(st.nextToken());
					//System.out.println("temp() = "+temp);
					switch(i){
						case 0://check hour
							if(temp < 0 || temp > 23)
								return false;						
							break;

						case 1://check minute
						case 2://check second
							if(temp < 0 || temp > 59)
								return false;
							break;
					}
					returnDateArr[i] = temp;
					i ++;
				}
			} catch (NumberFormatException e) {
				return false;
			}
			if(i != 6)
				return false;
		}
		
		return true;
	}
	
	
	public static String getMonthDaysForLoan(int beginMonthint,int beginDayint,int alldays,int addYear){
		if(beginMonthint==2){
			if(beginDayint<=28){
				return new BigDecimal(alldays).divide(new BigDecimal(365),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(addYear)).toString();
			}else{
				return new BigDecimal(alldays).divide(new BigDecimal(366),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(addYear)).toString();
			}
		}else if(beginMonthint<2){
			return new BigDecimal(alldays).divide(new BigDecimal(365),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(addYear)).toString();
		}else{
			return new BigDecimal(alldays).divide(new BigDecimal(366),6,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(addYear)).toString();
		}
	}
	
	// --相隔的天数                                                                                
    public static int getBaysbetween(Calendar d1, Calendar d2) {              
        if (d1.after(d2)) {                                                                        
            Calendar swap = d1;                                                          
            d1 = d2;                                                                               
            d2 = swap;                                                                             
        }                                                                                          
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);                                                  
        if (d1.get(Calendar.YEAR) != y2) {                                               
            d1 = (Calendar) d1.clone();                                                  
            do {                                                                                   
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);                       
                d1.add(Calendar.YEAR, 1);                                                
            } while (d1.get(Calendar.YEAR) != y2);                                       
        }                                                                                          
        return days;                                                                               
    }
	
    //剩余期限
	public static String getLeaveYears(String endDate,int precision){
		
		return getLeaveYears(getDate(),endDate,precision);
	}
	public static String getLeaveYears(String begDate,String endDate,int precision){
		
		Calendar begin = getCalendar(begDate);
		Calendar end = getCalendar(endDate);
		Calendar tmp = getCalendar(endDate);
		int years = 0;
		
		//超过到期还款日返回0
		if(!begin.before(end)){
			return "0";
		}
		
		while(begin.before(tmp)){
			years++;
			tmp = getCalendar(endDate);
			tmp.add(Calendar.MONTH, (-12 * years));
		}
		years--;
		end.add(Calendar.MONTH, (-12 * years));
		
		BigDecimal dec1 = new BigDecimal(getBaysbetween(begin, end));
		BigDecimal dec2 = new BigDecimal(getBaysbetween(tmp, end));
		
		return dec1.divide(dec2,precision,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(years)).toString();
	}
	
	public static void main(String argv[]){
//		if("1234".charAt(0) == '1'){
//			System.out.print("d");
//		}else {
//			System.out.print("c");
//		}
//		System.out.println(get9999Date());
//		System.out.println(setDate("2000-11-22", "yyyy-MM-dd"));
//		System.out.println(setDate("2000-11-22 23:12:11", "yyyy-MM-dd HH:mm:ss"));
//		System.out.print(getDateDiffDays(new Date(), get9999Date()));
		
		System.out.println(getLeaveYears("2012-02-29", 6));
		System.out.println(getLeaveYears("2010-04-21","2012-02-29", 6));
	}
	
	
	
}
