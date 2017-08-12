package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.unblanceseries;

import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.unblancecategoryvalue.UnblanceCategoryValueSeries;

/**
 * Category/Value图表默认实现
 * 
 */
public class DefaultUnblanceSeriesCategoryValueChart implements Chart, UnblanceSeriesCategoryValueChart {
	private List<UnblanceCategoryValueSeries> categoryValueSerieses;

	/**
	 * 默认构造器
	 * 
	 * 初始化数据序列表
	 */
	public DefaultUnblanceSeriesCategoryValueChart() {
		categoryValueSerieses = new ArrayList<UnblanceCategoryValueSeries>();
	}

	
	public void addCategoryValueSeries(UnblanceCategoryValueSeries categoryValueSeries)
			throws NullPointerException {
		if (categoryValueSeries == null) {
			throw new NullPointerException(
					"categoryValueSeries can't be null");
		}

		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<UnblanceCategoryValueSeries>();
		}
		removeCategoryValueSeries(categoryValueSeries.getId());
		categoryValueSerieses.add(categoryValueSeries);
	}

	
	public List<UnblanceCategoryValueSeries> listCategoryValueSeries() {
		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<UnblanceCategoryValueSeries>();
		}
		return categoryValueSerieses;
	}

	
	public boolean removeCategoryValueSeries(
			UnblanceCategoryValueSeries categoryValueSeries) {
		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<UnblanceCategoryValueSeries>();
		}
		return categoryValueSerieses.remove(categoryValueSeries);
	}

	
	public boolean removeCategoryValueSeries(String id) {
		if (categoryValueSerieses == null) {
			categoryValueSerieses = new ArrayList<UnblanceCategoryValueSeries>();
		}
		for (UnblanceCategoryValueSeries series : categoryValueSerieses) {
			if (id.equals(series.getId())) {
				return removeCategoryValueSeries(series);
			}
		}

		return false;
	}

}
