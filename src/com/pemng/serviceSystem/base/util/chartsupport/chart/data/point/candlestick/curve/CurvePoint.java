package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve;

import java.util.Date;

/**
 * 用于K线图的曲线数据
 * @author Administrator
 *
 */
public interface CurvePoint {
	/**
	 * 设置日期时间
	 * @param date 日期时间
	 * @throws 如果date为null，则抛出此异常
	 */
	void setDate(Date date) throws NullPointerException;
	
	/**
	 * 获取日期时间
	 * @return 日期时间
	 */
	Date getDate();
	
	/**
	 * 设置值
	 * @param value 值
	 */
	void setValue(Number value);
	
	/**
	 * 获取值
	 * @return 值
	 */
	Number getValue();
}
