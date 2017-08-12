package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick;

import java.util.Date;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.Point;

/**
 * K线图数据节点
 *
 */
public interface CandlestickPoint extends Point {
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
	 * 设置开盘价
	 * @param open 开盘价
	 * @throws 如果open为null，则抛出此异常
	 */
	void setOpen(Number open) throws NullPointerException;
	
	/**
	 * 获取开盘价
	 * @return 开盘价
	 */
	Number getOpen();

	/**
	 * 设置最高价
	 * @param high 最高价
	 * @throws 如果high为null，则抛出此异常
	 */
	void setHigh(Number high) throws NullPointerException;
	
	/**
	 * 获取最高价
	 * @return 最高价
	 */
	Number getHigh();

	/**
	 * 设置最低价
	 * @param low 最低价
	 * @throws 如果low为null，则抛出此异常
	 */
	void setLow(Number low) throws NullPointerException;

	/**
	 * 获取最低价
	 * @return 最低价
	 */
	Number getLow();
	
	/**
	 * 设置收盘价
	 * @param close 收盘价
	 * @throws 如果close为null，则抛出此异常
	 */
	void setClose(Number close) throws NullPointerException;
	
	/**
	 * 获取收盘价
	 * @return 收盘价
	 */
	Number getClose();

	/**
	 * 设置成交量
	 * @param amount 成交量
	 * @throws 如果volume为null，则抛出此异常
	 */
	void setVolume(Number volume) throws NullPointerException;
	
	/**
	 * 获取成交量
	 * @return 成交量
	 */
	Number getVolume();
}
