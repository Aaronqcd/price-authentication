package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.candlestick;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CombinationCandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.EventSupportCandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.CandlestickSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.DefaultCandlestickSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.CurveSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.DefaultCurveSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.event.Event;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.CandlestickExporter;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.EventSupportExporter;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.SeriesDataNotMatchedException;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;
import com.pemng.serviceSystem.common.SystemGlobalConstants;

public class DefaultCandlestickChartFileBuilder implements
		CandlestickChartFileBuilder {
	// 生成器的基准目录
	private String basePath;

	// K线数据文件名字
	private String candlestickDataFileName;

	// 事件文件名字
	private String eventFileName;

	// 数据导出器
	private CandlestickExporter exporter;

	// 数据加载时使用的图表
	private CandlestickChart chart;

	/**
	 * 设置数据输出器
	 * 
	 * @param exporter
	 *            数据输出器
	 */
	public void setExporter(CandlestickExporter exporter) {
		this.exporter = exporter;
	}

	/**
	 * 数据加载时使用的图表
	 * 
	 * @param chart
	 *            操作的K线图表
	 */
	public void setChart(CandlestickChart chart) {
		this.chart = chart;
	}

	
	public void addCurveSeries(List<CurvePoint> series) {
		CombinationCandlestickChart ccc = null;
		try {
			ccc = (CombinationCandlestickChart) chart;
		} catch (ClassCastException e) {
			throw new ChartNotSupportException(
					"Convert chart type[to CombinationCandlestickChart] error",
					e);
		}

		CurveSeries s = getCurveSeries(series);
		ccc.addCurveSeries(s);
	}

	
	public void addCurveSeries(List<CurvePoint> series, String seriesId) {
		CombinationCandlestickChart ccc = null;
		try {
			ccc = (CombinationCandlestickChart) chart;
		} catch (ClassCastException e) {
			throw new ChartNotSupportException(
					"Convert chart type[to CombinationCandlestickChart] error",
					e);
		}

		CurveSeries s = getCurveSeries(series, seriesId);
		ccc.addCurveSeries(s);
	}

	
	public void buildFile() throws BuildFileException {
		Map<String, String> data = null;

		try {
			try {

				try {
					data = exporter.exportChart(chart);
				} catch (SeriesDataNotMatchedException e) { // 如果捕获数据序列不匹配异常则以空数据输出

					// 清空所有KEY值指定的value
					Iterator<String> it = data.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						data.put(key, "");
					}
					// 抛出异常
					throw e;
				}

			} finally {
				buildCandlestickCsvDataFile(data);
				buildEventXmlDataFile(data);
				buildCategoryValueCsvDataFile(data);
			}
		} catch (SeriesDataNotMatchedException e) {
			throw new BuildFileException("", e);
		}
	}

	
	public void clearChartSeries() {
		if (chart instanceof CombinationCandlestickChart) {
			((CombinationCandlestickChart) chart).listCurveSeries().clear();
		}
		chart.getCandlestickSeries().clear();
	}

	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	
	public void setCandlestickDataFileName(String fileName) {
		this.candlestickDataFileName = fileName;
	}

	
	public void setCandlestickSeries(List<CandlestickPoint> series) {
		CandlestickSeries s = getCandlestickSeries(series);
		chart.setCandlestickSeries(s);
	}

	
	public void setEventFileName(String fileName) {
		this.eventFileName = fileName;
	}

	
	public void clearEvents() throws ChartNotSupportException {
		EventSupportCandlestickChart esccc = null;

		try {
			esccc = (EventSupportCandlestickChart) chart;
		} catch (ClassCastException e) {
			throw new ChartNotSupportException(
					"Convert chart type[to EventSupportCombinationCandlestickChart] error",
					e);
		}

		esccc.clear();
	}

	
	public void setEvents(List<Event> events) throws NullPointerException,
			ChartNotSupportException {

		EventSupportCandlestickChart esccc = null;

		try {
			esccc = (EventSupportCandlestickChart) chart;
		} catch (ClassCastException e) {
			throw new ChartNotSupportException(
					"Convert chart type[to EventSupportCombinationCandlestickChart] error",
					e);
		}

		for (Event e : events) {
			esccc.addEvent(e);
		}

	}

	// 将一个CandlestickPoint列表转换为一个CandlestickSeries数据序列
	private CandlestickSeries getCandlestickSeries(List<CandlestickPoint> points) {
		CandlestickSeries s = new DefaultCandlestickSeries();
		s.setId(candlestickDataFileName);
		for (CandlestickPoint point : points) {
			s.addCandlestickPoint(point);
		}
		return s;
	}

	// 将一个CategoryValuePoint列表转换为一个CategoryValueSeries数据序列
	private CurveSeries getCurveSeries(List<CurvePoint> series) {
		CombinationCandlestickChart ccc = null;
		try {
			ccc = (CombinationCandlestickChart) chart;
		} catch (ClassCastException e) {
			throw new ChartNotSupportException("Convert chart type error", e);
		}
		int number = ccc.listCurveSeries().size();
		CurveSeries s = new DefaultCurveSeries();
		s.setId("" + number);
		for (CurvePoint point : series) {
			s.addCurvePoint(point);
		}
		return s;
	}

	// 将一个CurvePoint列表转换为一个CurveSeries数据序列
	// 同时赋值序列id
	private CurveSeries getCurveSeries(List<CurvePoint> series, String seriesId) {
		CurveSeries s = new DefaultCurveSeries();
		s.setId(seriesId);
		for (CurvePoint point : series) {
			s.addCurvePoint(point);
		}
		return s;
	}

	// 生成K线图CSV数据文件
	private void buildCandlestickCsvDataFile(Map<String, String> data)
			throws BuildCandlestickCsvDataFileException {
//		String rootPath = SystemGlobalConstants.getHtmlRootPath();
		String csvData = data.get(exporter.getCsvDataKey());
		if (csvData == null || candlestickDataFileName == null) {
			return;
		}
		try {
			FileUtils.writeStringToFile(new File(/*rootPath + "/" +*/ basePath
					+ "/" + candlestickDataFileName), csvData);
		} catch (IOException e) {
			throw new BuildCandlestickCsvDataFileException(
					"Build Candlestick CSV data file[" /*+ rootPath + "/"*/
							+ basePath + "/" + candlestickDataFileName
							+ "] error", e);
		}
	}

	// 生成事件XML数据文件
	private void buildEventXmlDataFile(Map<String, String> data)
			throws BuildEventXmlDataFileException {
		String rootPath = SystemGlobalConstants.getHtmlRootPath();
		EventSupportExporter eventExporter = null;
		if (exporter instanceof EventSupportExporter) { // 确认导出器是否支持事件(信息地雷)处理
			eventExporter = (EventSupportExporter) exporter;
		} else { // 导出器不支持事件(信息地雷)处理则推出事件(信息地雷)文件生成
			return;
		}
		String xmlData = data.get(eventExporter.getEventDataKey());
		if (xmlData == null || eventFileName == null) {
			return;
		}
		try {
			FileUtils.writeStringToFile(new File(rootPath + "/" + basePath
					+ "/" + eventFileName), xmlData, "UTF-8");
		} catch (IOException e) {
			throw new BuildEventXmlDataFileException(
					"Build Candlestick event data file[" + rootPath + "/"
							+ basePath + "/" + eventFileName + "] error", e);
		}
	}

	// 生成曲线数据文件
	private void buildCategoryValueCsvDataFile(Map<String, String> data)
			throws BuildCategoryValueCsvDataFileException {
//		String rootPath = SystemGlobalConstants.getHtmlRootPath();
		String csvData = "";
		String fileName = "";
		Iterator<String> it = data.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (!exporter.getCsvDataKey().equals(key)) { // 判断当前值是否是CSV数据，如果是则不对当前数据做处理
				if (exporter instanceof EventSupportExporter) { // 如果当前的数据导出器支持事件(信息地雷)处理，则需要判断当前数据是否是被该导出器导出的事件(信息地雷)数据
					EventSupportExporter eventExporter = (EventSupportExporter) exporter;

					if (eventExporter.getEventDataKey().equals(key)) { // 判断当前值是否是事件(信息地雷)数据，如果是则不对当前数据做处理
						continue;
					}
				}
				csvData = data.get(key);
				fileName = key + ".csv";
				try {
					FileUtils.writeStringToFile(new File(/*rootPath + "/"
							+ */basePath + "/" + fileName), csvData);
				} catch (IOException e) {
					throw new BuildCategoryValueCsvDataFileException(
							"Build Candlestick category/value data file["
									/*+ rootPath + "/" */+ basePath + "/"
									+ fileName + "] error", e);
				}
			}
		}
	}

}
