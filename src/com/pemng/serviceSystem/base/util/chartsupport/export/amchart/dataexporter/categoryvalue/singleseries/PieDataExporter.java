package com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter.categoryvalue.singleseries;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.singleseries.amcharts.AmchartsPieChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.DefaultCategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.export.DataExportException;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.PieXmlDataAmchartsExporter;

public class PieDataExporter implements SingleSeriesDataExporter {
	// 图表数据输出器
	private PieXmlDataAmchartsExporter innerExporter;

	// 数据加载时使用的图表
	private AmchartsPieChart chart;

	public PieDataExporter() {
		innerExporter = new PieXmlDataAmchartsExporter();
	}

	public void setChart(AmchartsPieChart chart) {
		this.chart = chart;
	}
	
	
	public String export() throws DataExportException {
		return innerExporter.exportXmlData(chart);
	}

	
	public void clearChartSeries() {
		chart.getCategoryValueSeries().categoryValuePoints().clear();
	}

	
	public void setChartSeries(List<CategoryValuePoint> series)
			throws NullPointerException {
		CategoryValueSeries s = getSeries(series);
		chart.setCategoryValueSeries(s);
	}

	// 将一个CategoryValuePoint列表转换为一个CategoryValueSeries数据序列
	private CategoryValueSeries getSeries(List<CategoryValuePoint> series) {
		CategoryValueSeries s = new DefaultCategoryValueSeries();
		s.setId("");
		for (CategoryValuePoint point : series) {
			s.addCategoryValuePoint(point);
		}
		return s;
	}

}
