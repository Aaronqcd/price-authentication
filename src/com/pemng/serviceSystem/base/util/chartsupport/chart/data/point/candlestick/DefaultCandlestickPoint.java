package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick;

import java.util.Date;

/**
 * K线数据节点默认实现
 * 
 */
public class DefaultCandlestickPoint implements CandlestickPoint {

	private Number open;
	private Number high;
	private Number low;
	private Number close;
	private Number volume;
	private Date date;

	/**
	 * 默认构造器
	 */
	public DefaultCandlestickPoint() {
		/*
		date = new Date();
		open = new BigDecimal(0);
		high = new BigDecimal(0);
		low = new BigDecimal(0);
		close = new BigDecimal(0);
		volume = new BigDecimal(0);
		*/
	}

	/**
	 * 构造一个K线数据节点对象s
	 * 
	 * @param date
	 *            日期时间
	 * @param open
	 *            开盘价
	 * @param high
	 *            最高价
	 * @param low
	 *            最低价
	 * @param close
	 *            收盘价
	 * @param volume
	 *            成交量
	 */
	public DefaultCandlestickPoint(Date date, Number open, Number high,
			Number low, Number close, Number volume)
			throws NullPointerException {
		if (date == null || open == null || high == null || low == null
				|| close == null || volume == null) {
			throw new NullPointerException("All parameter can't be null");
		}

		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}

	
	public Number getOpen() {
		return open;
	}

	
	public void setOpen(Number open) {
		if (open == null) {
			throw new NullPointerException("open can't be null");
		}
		this.open = open;
	}

	
	public Number getHigh() {
		return high;
	}

	
	public void setHigh(Number high) {
		if (high == null) {
			throw new NullPointerException("high can't be null");
		}
		this.high = high;
	}

	
	public Number getLow() {
		return low;
	}

	
	public void setLow(Number low) {
		if (low == null) {
			throw new NullPointerException("low can't be null");
		}
		this.low = low;
	}

	
	public Number getClose() {
		return close;
	}

	
	public void setClose(Number close) {
		if (close == null) {
			throw new NullPointerException("close can't be null");
		}
		this.close = close;
	}

	
	public Number getVolume() {
		return volume;
	}

	
	public void setVolume(Number volume) {
		if (volume == null) {
			throw new NullPointerException("volume can't be null");
		}
		this.volume = volume;
	}

	
	public Date getDate() {
		return date;
	}

	
	public void setDate(Date date) {
		if (date == null) {
			throw new NullPointerException("date can't be null");
		}
		this.date = date;
	}

}
