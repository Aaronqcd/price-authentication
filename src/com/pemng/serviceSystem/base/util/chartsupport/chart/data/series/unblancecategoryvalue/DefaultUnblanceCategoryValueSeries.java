package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.unblancecategoryvalue;

import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.unblancecategoryvalue.UnblanceCategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.Series;

public class DefaultUnblanceCategoryValueSeries implements Series, UnblanceCategoryValueSeries {
	private String id;
	private List<UnblanceCategoryValuePoint> points;

	/**
	 * 默认构造器
	 * 
	 * 设置标示id为空字符串，初始化节点列表
	 */
	public DefaultUnblanceCategoryValueSeries() {
		this("");
	}

	/**
	 * 构造一个K线数据序列 同时设置相应的标示id，初始化节点列表
	 * 
	 * @param id
	 *            数据序列标示，如果id为null，则它将被自动赋值为空字符串
	 */
	public DefaultUnblanceCategoryValueSeries(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
		points = new ArrayList<UnblanceCategoryValuePoint>();
	}

	
	public void addCategoryValuePoint(UnblanceCategoryValuePoint point)
			throws NullPointerException {
		if (point == null) {
			throw new NullPointerException("point can't be null");
		}

		if (points == null) {
			points = new ArrayList<UnblanceCategoryValuePoint>();
		}

		points.add(point);
	}

	
	public List<UnblanceCategoryValuePoint> categoryValuePoints() {
		if (points == null) {
			points = new ArrayList<UnblanceCategoryValuePoint>();
		}
		return points;
	}

	
	public boolean removeCategoryValuePoint(UnblanceCategoryValuePoint point) {
		if (points == null) {
			points = new ArrayList<UnblanceCategoryValuePoint>();
		}
		return points.remove(point);
	}

	public boolean removeCategoryValuePoint(String category) {
		if (points == null) {
			points = new ArrayList<UnblanceCategoryValuePoint>();
		}

		for (UnblanceCategoryValuePoint point : points) {
			if (point.getCategory().equals(category)) {
				return removeCategoryValuePoint(point);
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
