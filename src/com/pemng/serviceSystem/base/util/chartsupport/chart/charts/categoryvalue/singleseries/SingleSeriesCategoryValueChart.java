package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.singleseries;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.CategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;

/**
 * 单数据序列图表
 * 
 */
public interface SingleSeriesCategoryValueChart extends CategoryValueChart {
	/**
	 * 设置一个数据序列
	 * 
	 * @param categoryValueSeries
	 *            待设置的C/V数据序列
	 * @throws NullPointerException
	 *             如果categoryValueSeries为null则抛出此异常
	 */
	void setCategoryValueSeries(CategoryValueSeries categoryValueSeries)
			throws NullPointerException;

	/**
	 * 获取图表的数据序列
	 * 
	 * @return 图表的数据序列
	 */
	CategoryValueSeries getCategoryValueSeries();
}
