package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.Series;

public class DefaultCurveSeries implements Series, CurveSeries {
	private String id;
	private List<CurvePoint> points;
	
	/**
	 * 默认构造器
	 * 
	 * 设置标示id为空字符串，初始化节点列表
	 */
	public DefaultCurveSeries() {
		this("");
	}
	
	/**
	 * 构造一个K线曲线数据序列 同时设置相应的标示id，初始化节点列表
	 * 
	 * @param id
	 *            数据序列标示，如果id为null，则它将被自动赋值为空字符串
	 */
	public DefaultCurveSeries(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
		points = new ArrayList<CurvePoint>();
	}
	
	
	public void addCurvePoint(CurvePoint point) throws NullPointerException {
		if (point == null) {
			throw new NullPointerException("point can't be null");
		}

		if (points == null) {
			points = new ArrayList<CurvePoint>();
		}

		points.add(point);
	}

	
	public List<CurvePoint> curvePoints() {
		if (points == null) {
			points = new ArrayList<CurvePoint>();
		}
		return points;
	}

	
	public boolean removeCurvePoint(CurvePoint point) {
		if (points == null) {
			points = new ArrayList<CurvePoint>();
		}
		return points.remove(point);
	}

	
	public boolean removeCurvePoint(String date, String datePattern) {
		if (points == null) {
			points = new ArrayList<CurvePoint>();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

		for (CurvePoint point : points) {
			if (sdf.format(point.getDate()).equals(date)) {
				return removeCurvePoint(point);
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

}
