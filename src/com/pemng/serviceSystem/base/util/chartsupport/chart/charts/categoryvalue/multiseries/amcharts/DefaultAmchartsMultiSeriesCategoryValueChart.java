package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.amcharts;

import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.AmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.DefaultAmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.DefaultMultiSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;

public class DefaultAmchartsMultiSeriesCategoryValueChart extends
		DefaultMultiSeriesCategoryValueChart implements AmchartsMultiSeriesCategoryValueChart {

	private AmchartsCategoryAxis amchartsCategoryAxis;

	/**
	 * 默认构造器
	 * 
	 * 初始化数据序列及Category轴
	 */
	public DefaultAmchartsMultiSeriesCategoryValueChart() {
		super();
		amchartsCategoryAxis = new DefaultAmchartsCategoryAxis();
	}
	

	
	public AmchartsCategoryAxis getAmchartsCategoryAxis() {
		if (amchartsCategoryAxis == null) {
			amchartsCategoryAxis = new DefaultAmchartsCategoryAxis();
		}
		return amchartsCategoryAxis;
	}

	
	public void setAmchartsCategoryAxis(AmchartsCategoryAxis categoryAxis)
			throws NullPointerException {
		if (categoryAxis == null) {
			throw new NullPointerException("categoryAxis can't be null");
		}
		this.amchartsCategoryAxis = categoryAxis;
	}

	
	public void addCategoryValueSeries(CategoryValueSeries categoryValueSeries,
			boolean iniCategoryAxis) {
		super.addCategoryValueSeries(categoryValueSeries);
		if (iniCategoryAxis) {
			initCategoryAxis(categoryValueSeries);
		}
	}

	// 根据一个数据序列，初始化Category轴刻度
	private void initCategoryAxis(CategoryValueSeries categoryValueSeries) {
		amchartsCategoryAxis.clearScales();
		for (CategoryValuePoint point : categoryValueSeries
				.categoryValuePoints()) {
			amchartsCategoryAxis.addAmchartsCategoryScale(point.getCategory());
		}
	}
}
