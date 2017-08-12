package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.CategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;

/**
 * 描述一个多数据序列C/V图表
 *
 */
public interface MultiSeriesCategoryValueChart extends CategoryValueChart {
	/**
	 * 添加一组C/V数据序列
	 * 
	 * 添加时，序列id必须唯一，如果待添加的序列出现重复id，后添加的序列将取代已有序列
	 * 
	 * @param categoryvalueSeries
	 *            C/V数据序列
	 * 
	 * @throws NullPointerException
	 *             如果待添加的categoryvalueSeries为空，则抛出此异常
	 */
	void addCategoryValueSeries(CategoryValueSeries categoryValueSeries)
			throws NullPointerException;

	/**
	 * 移除一组C/V数据序列
	 * 
	 * @param categoryvalueSeries
	 *            C/V数据序列
	 * @return 如果找到并移除成功则返回true，否则返回false
	 */
	boolean removeCategoryValueSeries(CategoryValueSeries categoryValueSeries);

	/**
	 * 移除指定id的一组C/V数据序列
	 * 
	 * @param categoryvalueSeries
	 *            C/V数据序列
	 * @return 如果找到并移除成功则返回true，否则返回false
	 */
	boolean removeCategoryValueSeries(String id);

	/**
	 * 获取所包含的所有数据序列
	 * 
	 * @return 所包含的所有数据序列
	 */
	List<CategoryValueSeries> listCategoryValueSeries();
}
