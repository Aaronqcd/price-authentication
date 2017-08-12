package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve;

import java.util.Date;

/**
 * K线图的曲线数据
 *
 */
public class DefaultCurvePoint implements CurvePoint {
	private Date date;
	private Number value;

	/**
	 * 构造器
	 * 
	 * @param date
	 *            日期
	 * @param value
	 *            值
	 * @throws NullPointerException
	 *             如果日期或值为null，则抛出此异常
	 */
	public DefaultCurvePoint(Date date, Number value)
			throws NullPointerException {
		if (date == null) {
			throw new NullPointerException("date can't be null");
		}

		this.date = date;
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) throws NullPointerException {
		if (date == null) {
			throw new NullPointerException("date can't be null");
		}
		this.date = date;
	}

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

}
