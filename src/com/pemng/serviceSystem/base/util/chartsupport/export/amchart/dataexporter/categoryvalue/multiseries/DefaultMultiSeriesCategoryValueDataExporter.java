package com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter.categoryvalue.multiseries;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.AmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.amcharts.AmchartsMultiSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.DefaultCategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.scale.CategoryScale;
import com.pemng.serviceSystem.base.util.chartsupport.export.DataExportException;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.MultiSeriesCategoryValueXmlDataAmchartsExporter;

public class DefaultMultiSeriesCategoryValueDataExporter implements
		MultiSeriesDataExporter {
	private MultiSeriesCategoryValueXmlDataAmchartsExporter innerExporter;
	private AmchartsMultiSeriesCategoryValueChart chart;

	public DefaultMultiSeriesCategoryValueDataExporter() {
		innerExporter = new MultiSeriesCategoryValueXmlDataAmchartsExporter();
	}

	public void setChart(AmchartsMultiSeriesCategoryValueChart chart) {
		this.chart = chart;
	}

	
	public void addChartSeries(List<CategoryValuePoint> series)
			throws NullPointerException {
		addChartSeries(series, false);
	}

	
	public void addChartSeries(List<CategoryValuePoint> series, String seriesId)
			throws NullPointerException {
		addChartSeries(series, seriesId, false);
	}

	
	public void addChartSeries(List<CategoryValuePoint> series,
			boolean isCategory) throws NullPointerException {
		CategoryValueSeries s = getSeries(series);
		chart.addCategoryValueSeries(s, isCategory);
	}

	
	public void addChartSeries(List<CategoryValuePoint> series,
			String seriesId, boolean isCategory) throws NullPointerException {
		CategoryValueSeries s = getSeries(series, seriesId);
		chart.addCategoryValueSeries(s, isCategory);
	}

	
	public void clearChartSeries() {
		chart.listCategoryValueSeries().clear();
	}

	
	public String[] getCategory() {
		AmchartsCategoryAxis cAxis = chart.getAmchartsCategoryAxis();

		List<String> scales = cAxis.listScale();

		String[] category = new String[scales.size()];

		for (int i = 0; i < scales.size(); i++) {
			category[i] = scales.get(i);
		}

		return category;
	}

	
	public void setCategory(String[] categories) throws NullPointerException {
		if (categories == null) {
			throw new NullPointerException("Categories can't be null");
		}

		AmchartsCategoryAxis cAxis = chart.getAmchartsCategoryAxis();

		// 清空X轴坐标刻度
		cAxis.clearScales();

		// 以数组categories设置X轴坐标刻度
		for (String scaleValue : categories) {
			cAxis.addAmchartsCategoryScale(scaleValue);
		}
	}

	
	public String export() throws DataExportException {
		return innerExporter.exportXmlData(chart);
	}

	// 将一个CategoryValuePoint列表转换为一个CategoryValueSeries数据序列
	private CategoryValueSeries getSeries(List<CategoryValuePoint> series) {
		int number = chart.listCategoryValueSeries().size();
		CategoryValueSeries s = new DefaultCategoryValueSeries();
		s.setId("" + number);
		for (CategoryValuePoint point : series) {
			s.addCategoryValuePoint(point);
		}
		return s;
	}

	// 将一个CategoryValuePoint列表转换为一个CategoryValueSeries数据序列
	// 同时赋值序列id
	private CategoryValueSeries getSeries(List<CategoryValuePoint> series,
			String seriesId) {
		CategoryValueSeries s = new DefaultCategoryValueSeries();
		s.setId(seriesId);
		for (CategoryValuePoint point : series) {
			s.addCategoryValuePoint(point);
		}
		return s;
	}

	
	public CategoryScale[] getCategorySclae() {
		AmchartsCategoryAxis cAxis = chart.getAmchartsCategoryAxis();

		List<CategoryScale> scales = cAxis.listCategoryScale();

		CategoryScale[] category = new CategoryScale[scales.size()];

		scales.toArray(category);

		return category;
	}

	
	public void setCategory(CategoryScale[] categories)
			throws NullPointerException {
		if (categories == null) {
			throw new NullPointerException("Categories can't be null");
		}

		AmchartsCategoryAxis cAxis = chart.getAmchartsCategoryAxis();

		// 清空X轴坐标刻度
		cAxis.clearScales();

		// 以数组categories设置X轴坐标刻度
		for (CategoryScale scale : categories) {
			if (scale != null) {
				String scaleValue = scale.getValue();
				boolean show = scale.isShow();
				cAxis.addAmchartsCategoryScale(scaleValue, show);
			}

		}
	}
}
