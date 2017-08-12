package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.categoryvalue.multiseries;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.amcharts.AmchartsMultiSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.multiseries.amcharts.DefaultAmchartsMultiSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.DefaultCategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.MultiSeriesCategoryValueXmlDataAmchartsExporter;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;
import com.pemng.serviceSystem.base.util.chartsupport.util.textsize.TextSizeCalculator;
import com.pemng.serviceSystem.common.SystemGlobalConstants;

@Deprecated
public class DefaultCategoryValueChartFileBuilder2 implements
		CategoryValueChartFileBuilder2 {
	// 生成器的基准目录
	private String basePath;

	// 图表数据文件
	private String xmlDataFileName;

	// 图表设置文件以及其模板文件
	private String settingsFileName;
	private String settingsFileTemplate;
	private String backgroundFileName;

	// HTML文件及其模板
	private String htmlFileName;
	private String htmlFileTemplate;
	private String swfObjectJsPath;
	private String chartId;
	private String amlineSwfPath;
	private int chartWidth;
	private int chartHeight;
	private String backgroundFilePath;
	private TextSizeCalculator calculator;

	private AmchartsMultiSeriesCategoryValueChart chart = new DefaultAmchartsMultiSeriesCategoryValueChart();

	// 系统根目录
	private static final String rootPath = SystemGlobalConstants
			.getResourcePath();

	
	public void addChartSeries(List<CategoryValuePoint> series) {
		CategoryValueSeries s = getSeries(series);
		chart.addCategoryValueSeries(s, true);
	}

	
	public void buildFile() throws BuildFileException {
		// 生成文件
		// 生成XML数据文件
		buildXmlDataFile();

		// 生成settings文件
		buildSettingsFile();

		// 生成HTML文件
		buildHtmlFile();

		// 清空chart
		clearChart();
	}

	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	
	public void setBackgroundFileName(String backgroundFile) {
		this.backgroundFileName = backgroundFile;
	}

	
	public void setChartHeight(int height) {
		this.chartHeight = height;
	}

	
	public void setChartWidth(int width) {
		this.chartWidth = width;
	}

	
	public void setHtmlFileName(String fileName) {
		this.htmlFileName = fileName;
	}

	public void setHtmlFileTemplate(String template) {
		this.htmlFileTemplate = template;
	}

	
	public void setSettingsFileName(String fileName) {
		this.settingsFileName = fileName;
	}

	public void setSettingsFileTemplate(String template) {
		this.settingsFileTemplate = template;
	}

	
	public void setXmlDataFileName(String fileName) {
		this.xmlDataFileName = fileName;
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

	// 生成数据文件
	private void buildXmlDataFile() throws BuildFileException {
		MultiSeriesCategoryValueXmlDataAmchartsExporter exporter = new MultiSeriesCategoryValueXmlDataAmchartsExporter();
		Map<String, String> data = exporter.exportChart(chart);
		String xmldata = data
				.get(MultiSeriesCategoryValueXmlDataAmchartsExporter.CATEGORY_VALUE_XML_DATA);
		try {
			FileUtils.writeStringToFile(new File(rootPath + "/" + basePath
					+ "/" + xmlDataFileName), xmldata);
		} catch (IOException ie) {
			throw new BuildFileException(
					"Build Category/Value chart xml data file[" + rootPath
							+ "/" + basePath + "/" + xmlDataFileName
							+ "] error", ie);
		}
	}

	// 生成图表设置文件
	@SuppressWarnings("unchecked")
	private void buildSettingsFile() throws BuildFileException {
//		HtmlMaker maker = new HtmlMaker();
//		maker.setBasePath(basePath);
//		maker.setFileName(settingsFileName);
//
//		maker.setTemplateFile(settingsFileTemplate);
//		// 设置必要的配置参数
//		Map param = getSettingsFileParam();
//		try {
//			maker.buildHtml(param);
//		} catch (IOException ie) {
//			throw new BuildFileException(
//					"Build Category/Value chart settings file[" + rootPath
//							+ "/" + basePath + "/" + settingsFileName
//							+ "] error", ie);
//		} catch (TemplateException te) {
//			throw new BuildFileException(
//					"Build Category/Value chart settings file[" + rootPath
//							+ "/" + basePath + "/" + settingsFileName
//							+ "] error", te);
//		}
	}

	// 生成HTTP文件
	@SuppressWarnings("unchecked")
	private void buildHtmlFile() throws BuildFileException {
//		HtmlMaker maker = new HtmlMaker();
//		maker.setBasePath(basePath);
//		maker.setFileName(settingsFileName);
//		maker.setTemplateFile(htmlFileTemplate);
//		// 设置必要的配置参数
//		Map param = getHtmlParam();
//		try {
//			maker.buildHtml(param);
//		} catch (IOException ie) {
//			throw new BuildFileException(
//					"Build Category/Value chart HTML file[" + rootPath + "/"
//							+ basePath + "/" + htmlFileName + "] error", ie);
//		} catch (TemplateException te) {
//			throw new BuildFileException(
//					"Build Category/Value chart HTML file[" + rootPath + "/"
//							+ basePath + "/" + htmlFileName + "] error", te);
//		}
	}

	// 清空图表数据
	private void clearChart() {
		chart.listCategoryValueSeries().clear();
	}

	// 设置settings file必要的参数
	@SuppressWarnings("unchecked")
	private Map getSettingsFileParam() {
		String textSizeString = "" + getTextSize(chartWidth, chartHeight);

		Map param = new HashMap();
		param.put("textSize", textSizeString);
		param.put("backgroundFileName", backgroundFileName);

		return param;
	}

	// 根据图表的长宽属性计算字体大小
	private int getTextSize(int width, int height) {
		return calculator.getTextSize(width, height);
	}

	// 设置HTML文件参数
	@SuppressWarnings("unchecked")
	private Map getHtmlParam() {
		Map param = new HashMap();

		param.put("swfObjectJsPath", swfObjectJsPath);
		param.put("chartId", chartId);
		param.put("amlineSwfPath", amlineSwfPath);
		param.put("chartWidth", chartWidth);
		param.put("chartHeight", chartHeight);
		param.put("backgroundFilePath", backgroundFilePath);
		param.put("settingsFileName", settingsFileName);
		param.put("xmlDataFileName", xmlDataFileName);

		return param;
	}

	
	public void setBackgroundFilePath(String path) {
		this.backgroundFilePath = path;
	}

	
	public void setAmlineSwfPath(String path) {
		this.amlineSwfPath = path;
	}

	
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}

	
	public void setSwfObjectJsPath(String path) {
		this.swfObjectJsPath = path;
	}

	
	public void setTextSizeCalculator(TextSizeCalculator calculator) {
		this.calculator = calculator;
	}
}
