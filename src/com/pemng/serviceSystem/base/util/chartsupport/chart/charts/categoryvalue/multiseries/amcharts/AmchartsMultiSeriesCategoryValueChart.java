package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.amcharts;

import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.AmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.MultiSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;

/**
 * 描述一个Am charts CategoryValueChart
 * 
 * 这种图表含有Category Axis用来指定X坐标的刻度值，所有数据的显示都依赖于这个刻度值
 * 
 */
public interface AmchartsMultiSeriesCategoryValueChart extends MultiSeriesCategoryValueChart {
	/**
	 * 设置一个刻度坐标轴
	 * 
	 * @param categoryAxis
	 *            刻度坐标轴
	 * @throws NullPointerException
	 *             如果categoryAxis为空则抛出此异常
	 */
	void setAmchartsCategoryAxis(AmchartsCategoryAxis categoryAxis)
			throws NullPointerException;

	/**
	 * 返回当前图表的刻度坐标轴
	 * 
	 * 如果未进行设置，则返回的是一个空的AmchartsCategoryAxis，即永远不返回null
	 * 
	 * @return 一个刻度坐标轴
	 */
	AmchartsCategoryAxis getAmchartsCategoryAxis();
	
	/**
	 * 添加一个数据序列
	 * 
	 * 添加规则与addCategoryValueSeries(CategoryValueSeries)相同
	 * 
	 * 但是当iniCategoryAxis为true时，会以当前数据序列初始化X轴刻度
	 * @param categoryValueSeries 待添加的序列
	 * @param iniCategoryAxis 如果为true，则以当前序列设置X轴刻度
	 */
	void addCategoryValueSeries(CategoryValueSeries categoryValueSeries,boolean iniCategoryAxis);
}
