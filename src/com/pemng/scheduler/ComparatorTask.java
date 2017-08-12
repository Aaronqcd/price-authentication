package com.pemng.scheduler;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;


public class ComparatorTask implements Comparator {

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		TaskBase fi0=(TaskBase) o1;
		TaskBase fi1=(TaskBase) o2;
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		/*** 定制每日00：24：00执行方法 ***/
		calendar.set(year, month, day, fi0.getTaskHour(), fi0.getTaskMinute(), 00);
		Date date0 = calendar.getTime();
		calendar.set(year, month, day, fi1.getTaskHour(), fi1.getTaskMinute(), 00);
		Date date1 = calendar.getTime();
		
		return (int)(date0.getTime()-date1.getTime());
	}

}
