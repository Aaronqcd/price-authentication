package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.Series;

/**
 * K线数据序列默认实现
 * 
 */
public class DefaultCandlestickSeries implements Series, CandlestickSeries {
	private String id;
	private List<CandlestickPoint> points;

	/**
	 * 默认构造器 设置标示id为空字符串，初始化节点列表
	 */
	public DefaultCandlestickSeries() {
		this("");
	}

	/**
	 * 构造一个K线数据序列 同时设置相应的标示id，初始化节点列表
	 * 
	 * @param id
	 *            数据序列标示，如果id为null，则它将被自动赋值为空字符串
	 */
	public DefaultCandlestickSeries(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
		points = new ArrayList<CandlestickPoint>();
	}

	
	public void addCandlestickPoint(CandlestickPoint point)
			throws NullPointerException {
		if (point == null) {
			throw new NullPointerException("point can't be null");
		}

		if (points == null) {
			points = new ArrayList<CandlestickPoint>();
		}

		points.add(point);
	}

	
	public List<CandlestickPoint> candlestickPoints() {
		if (points == null) {
			points = new ArrayList<CandlestickPoint>();
		}
		return points;
	}

	
	public boolean removeCandlestickPoint(CandlestickPoint point) {
		if (points == null) {
			points = new ArrayList<CandlestickPoint>();
		}
		return points.remove(point);
	}

	
	public boolean removeCandlestickPoint(Date pointDate) {
		if (points == null) {
			points = new ArrayList<CandlestickPoint>();
		}

		for (CandlestickPoint point : points) {
			if (point.getDate().equals(pointDate)) {
				return removeCandlestickPoint(point);
			}
		}

		return false;
	}

	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	
	public void clear() {
		if (points == null) {
			points = new ArrayList<CandlestickPoint>();
		}
		points.clear();
	}

}
