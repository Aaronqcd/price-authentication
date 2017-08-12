package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.categoryvalue.singleseries;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.singleseries.amcharts.AmchartsPieChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.DefaultCategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.export.Exporter;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.PieXmlDataAmchartsExporter;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;

public class DefaultPieChartFileBuilder implements PieChartFileBuilder {
	// 生成器的基准目录
	private String basePath;

	// 图表数据文件
	private String xmlDataFileName;

	// 图表数据输出器
	private Exporter exporter;

	// 数据加载时使用的图表
	private AmchartsPieChart chart;


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
	public void setChart(AmchartsPieChart chart) {
		this.chart = chart;
	}

	
	public void buildFile() throws BuildFileException {
		// 生成文件
		// 生成XML数据文件
		buildXmlDataFile();
	}

	
	public void clearChartSeries() {
		chart.getCategoryValueSeries().categoryValuePoints().clear();
	}

	
	public void setBasePath(String path) {
		this.basePath = path;
	}

	
	public void setChartSeries(List<CategoryValuePoint> series) throws NullPointerException {
		CategoryValueSeries s = getSeries(series);
		chart.setCategoryValueSeries(s);
	}

	
	public void setXmlDataFileName(String fileName) {
		this.xmlDataFileName = fileName;
	}

	// 生成数据文件
	protected void buildXmlDataFile() throws BuildFileException {
//		String rootPath = SystemGlobalConstants.getHtmlRootPath();
		Map<String, String> data = exporter.exportChart(chart);
		String xmldata = data
				.get(PieXmlDataAmchartsExporter.PIE_XML_DATA);
		try {
//			FileUtils.writeStringToFile(new File(rootPath + "/" + basePath
//					+ "/" + xmlDataFileName), xmldata,"UTF-8");
			FileUtils.writeStringToFile(new File(basePath
					+ "/" + xmlDataFileName), xmldata,"UTF-8");
		} catch (IOException ie) {
			throw new BuildFileException("Build pie chart xml data file["
					+ basePath + "/" + xmlDataFileName
					+ "] error", ie);
		}
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
