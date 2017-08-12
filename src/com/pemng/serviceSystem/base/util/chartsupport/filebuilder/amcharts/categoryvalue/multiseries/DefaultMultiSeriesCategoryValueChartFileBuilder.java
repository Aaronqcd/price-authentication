package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.categoryvalue.multiseries;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.AmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.amcharts.AmchartsMultiSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.DefaultCategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.scale.CategoryScale;
import com.pemng.serviceSystem.base.util.chartsupport.export.Exporter;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.MultiSeriesCategoryValueXmlDataAmchartsExporter;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;

public class DefaultMultiSeriesCategoryValueChartFileBuilder implements
		MultiSeriesCategoryValueChartFileBuilder {
	// 生成器的基准目录
	private String basePath;

	// 图表数据文件
	private String xmlDataFileName;

	// 图表数据输出器
	private Exporter exporter;

	// 数据加载时使用的图表
	private AmchartsMultiSeriesCategoryValueChart chart;

	// 系统根目录
	// private static final String rootPath = SystemGlobalConstants
	// .getResourcePath();

	/**
	 * 设置数据输出器
	 * 
	 * @param exporter
	 *            数据输出器
	 */
	public void setExporter(Exporter exporter) {
		this.exporter = exporter;
	}

	/**
	 * 设置所操作的C/V图表
	 * 
	 * @param chart
	 *            操作的C/V图表
	 */
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

	
	public void setCategory(String[] categories) {
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

	
	public String[] getCategory() {
		AmchartsCategoryAxis cAxis = chart.getAmchartsCategoryAxis();

		List<String> scales = cAxis.listScale();

		String[] category = new String[scales.size()];

		for (int i = 0; i < scales.size(); i++) {
			category[i] = scales.get(i);
		}

		return category;
	}

	
	public void buildFile() throws BuildFileException {
		// 生成文件
		// 生成XML数据文件
		buildXmlDataFile();

	}

	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
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

	// 生成数据文件
	protected void buildXmlDataFile() throws BuildFileException {
//		String rootPath = SystemGlobalConstants.getHtmlRootPath();
		Map<String, String> data = exporter.exportChart(chart);
		String xmldata = data
				.get(MultiSeriesCategoryValueXmlDataAmchartsExporter.CATEGORY_VALUE_XML_DATA);
		try {
			FileUtils.writeStringToFile(new File(/*rootPath + "/" + */basePath
					+ "/" + xmlDataFileName), xmldata, "UTF-8");
		} catch (IOException ie) {
			throw new BuildFileException(
					"Build Category/Value chart xml data file[" /*+ rootPath
							+ "/"*/ + basePath + "/" + xmlDataFileName
							+ "] error", ie);
		}
	}

	
	public void clearChartSeries() {
		chart.listCategoryValueSeries().clear();
	}

	
	public void setXmlDataFileName(String fileName) {
		this.xmlDataFileName = fileName;
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

	
	public CategoryScale[] getCategorySclae() {
		AmchartsCategoryAxis cAxis = chart.getAmchartsCategoryAxis();

		List<CategoryScale> scales = cAxis.listCategoryScale();

		CategoryScale[] category = new CategoryScale[scales.size()];

		scales.toArray(category);

		return category;
	}

}
