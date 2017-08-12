package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.unblanceseries.amcharts;

import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.AmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.DefaultAmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.unblanceseries.DefaultUnblanceSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.unblancecategoryvalue.UnblanceCategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.unblancecategoryvalue.UnblanceCategoryValueSeries;

public class DefaultAmchartsUnblanceSeriesCategoryValueChart extends
		DefaultUnblanceSeriesCategoryValueChart implements AmchartsUnblanceSeriesCategoryValueChart {

	private AmchartsCategoryAxis amchartsCategoryAxis;

	/**
	 * 默认构造器
	 * 
	 * 初始化数据序列及Category轴
	 */
	public DefaultAmchartsUnblanceSeriesCategoryValueChart() {
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

	
	public void addCategoryValueSeries(UnblanceCategoryValueSeries categoryValueSeries,
			boolean iniCategoryAxis) {
		super.addCategoryValueSeries(categoryValueSeries);
		if (iniCategoryAxis) {
			initCategoryAxis(categoryValueSeries);
		}
	}

	// 根据一个数据序列，初始化Category轴刻度
	private void initCategoryAxis(UnblanceCategoryValueSeries categoryValueSeries) {
		amchartsCategoryAxis.clearScales();
		for (UnblanceCategoryValuePoint point : categoryValueSeries
				.categoryValuePoints()) {
			amchartsCategoryAxis.addAmchartsCategoryScale(point.getCategory());
		}
	}
}
