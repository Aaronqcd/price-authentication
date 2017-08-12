package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue;

import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.Series;

public class DefaultCategoryValueSeries implements Series, CategoryValueSeries {
	private String id;
	private List<CategoryValuePoint> points;

	/**
	 * 默认构造器
	 * 
	 * 设置标示id为空字符串，初始化节点列表
	 */
	public DefaultCategoryValueSeries() {
		this("");
	}

	/**
	 * 构造一个K线数据序列 同时设置相应的标示id，初始化节点列表
	 * 
	 * @param id
	 *            数据序列标示，如果id为null，则它将被自动赋值为空字符串
	 */
	public DefaultCategoryValueSeries(String id) {
		if (id == null) {
			id = "";
		}
		this.id = id;
		points = new ArrayList<CategoryValuePoint>();
	}

	
	public void addCategoryValuePoint(CategoryValuePoint point)
			throws NullPointerException {
		if (point == null) {
			throw new NullPointerException("point can't be null");
		}

		if (points == null) {
			points = new ArrayList<CategoryValuePoint>();
		}

		points.add(point);
	}

	
	public List<CategoryValuePoint> categoryValuePoints() {
		if (points == null) {
			points = new ArrayList<CategoryValuePoint>();
		}
		return points;
	}

	
	public boolean removeCategoryValuePoint(CategoryValuePoint point) {
		if (points == null) {
			points = new ArrayList<CategoryValuePoint>();
		}
		return points.remove(point);
	}

	public boolean removeCategoryValuePoint(String category) {
		if (points == null) {
			points = new ArrayList<CategoryValuePoint>();
		}

		for (CategoryValuePoint point : points) {
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
