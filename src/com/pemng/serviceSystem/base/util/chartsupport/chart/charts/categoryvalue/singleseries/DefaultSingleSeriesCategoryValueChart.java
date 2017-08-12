package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.singleseries;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.DefaultCategoryValueSeries;

/**
 * 单数据序列C/V图表默认实现
 * 
 */
public class DefaultSingleSeriesCategoryValueChart implements Chart,
		SingleSeriesCategoryValueChart {
	private CategoryValueSeries categoryValueSeries;

	public DefaultSingleSeriesCategoryValueChart() {

	}

	
	public CategoryValueSeries getCategoryValueSeries() {
		if (categoryValueSeries == null) {
			categoryValueSeries = new DefaultCategoryValueSeries();
		}
		return categoryValueSeries;
	}

	
	public void setCategoryValueSeries(CategoryValueSeries categoryValueSeries)
			throws NullPointerException {
		if (categoryValueSeries == null) {
			throw new NullPointerException("categoryValueSeries can't be null");
		}
		this.categoryValueSeries = categoryValueSeries;
	}

}
