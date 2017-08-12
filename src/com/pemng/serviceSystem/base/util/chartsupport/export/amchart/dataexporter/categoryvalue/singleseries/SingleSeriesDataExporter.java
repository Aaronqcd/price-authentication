package com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter.categoryvalue.singleseries;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter.DataExporter;

public interface SingleSeriesDataExporter extends DataExporter {
	/**
	 * 设置一个图表数据序列
	 * 
	 * @param series
	 *            数据序列
	 * @throws NullPointerException
	 *             如果series为null，则抛出此异常
	 */
	void setChartSeries(List<CategoryValuePoint> series)
			throws NullPointerException;

	/**
	 * 清空图表数据
	 * 
	 * 在生成图表文件前、后，建议运行该方法来清空图表，以防造成数据混乱
	 */
	void clearChartSeries();
}
