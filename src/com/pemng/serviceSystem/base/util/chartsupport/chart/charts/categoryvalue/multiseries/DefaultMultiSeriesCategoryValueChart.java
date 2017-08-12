package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries;

import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;

/**
 * Category/Value图表默认实现
 * 
 */
public class DefaultMultiSeriesCategoryValueChart implements Chart, MultiSeriesCategoryValueChart {
	private List<CategoryValueSeries> categoryValueSerieses;

	/**
	 * 默认构造器
	 * 
	 * 初始化数据序列表
	 */
	public DefaultMultiSeriesCategoryValueChart() {
		categoryValueSerieses = new ArrayList<CategoryValueSeries>();
	}

	
	public void addCategoryValueSeries(CategoryValueSeries categoryValueSeries)
			throws NullPointerException {
		if (categoryValueSeries == null) {
			throw new NullPointerException(
					"categoryValueSeries can't be null");
		}

		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<CategoryValueSeries>();
		}
		removeCategoryValueSeries(categoryValueSeries.getId());
		categoryValueSerieses.add(categoryValueSeries);
	}

	
	public List<CategoryValueSeries> listCategoryValueSeries() {
		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<CategoryValueSeries>();
		}
		return categoryValueSerieses;
	}

	
	public boolean removeCategoryValueSeries(
			CategoryValueSeries categoryValueSeries) {
		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<CategoryValueSeries>();
		}
		return categoryValueSerieses.remove(categoryValueSeries);
	}

	
	public boolean removeCategoryValueSeries(String id) {
		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<CategoryValueSeries>();
		}
		for (CategoryValueSeries series : categoryValueSerieses) {
			if (id.equals(series.getId())) {
				return removeCategoryValueSeries(series);
			}
		}

		return false;
	}

}
