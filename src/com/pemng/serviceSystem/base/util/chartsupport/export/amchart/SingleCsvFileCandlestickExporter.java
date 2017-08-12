package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CombinationCandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.CandlestickSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.CurveSeries;

/**
 * 该k线图表数据导出器只导出一个CSV数据和一个event数据
 * 
 * CSV数据同时包括了K线和曲线的数据
 * 
 * 数据可以以常量值CSV_DATA为key值从exportChart方法返回的Map中获取
 * 
 * event数据可以以常量值EVENT_DATA为key值获取
 * 
 * 需要注意的是
 * 
 * 1. CSV文件的格式：
 * 
 * date,open,high,low,close,x1,x2,x3...
 * 
 * 其中x1、x2、x3...表示曲线数据序列对应date的值
 * 
 * 从x1开始的曲线将以数据序列id做升序排序
 * 
 * 2. date包含了所有数据序列中的date值
 * 
 * 
 * @since 2010-03-24
 */
public class SingleCsvFileCandlestickExporter extends
		AbstractCandlestickExporter implements CandlestickExporter {

	public static final String KEY_OPEN = "open";
	public static final String KEY_HIGH = "high";
	public static final String KEY_LOW = "low";
	public static final String KEY_CLOSE = "close";
	public static final String KEY_VOLUME = "volume";

	@Override
	public Map<String, String> exportChart(Chart chart, String datePattern)
			throws NullPointerException, ClassCastException,
			IllegalArgumentException, SeriesDataNotMatchedException {

		Map<String, String> chartData = new HashMap<String, String>();

		// 导出K线数据，以属性值csvDataKey为Key放入Map
		exportCsvData(chartData, chart, datePattern);

		return chartData;
	}

	/**
	 * 导出K线数据，以属性值csvDataKey为Key放入Map
	 * 
	 * @param chartData
	 *            图表数据结果集
	 * @param chart
	 *            图表
	 * @param datePattern
	 *            日期格式
	 */
	protected void exportCsvData(Map<String, String> chartData, Chart chart,
			String datePattern) {
		CandlestickChart cChart = (CandlestickChart) chart;
		
		String candlestickData = getCsvData(cChart, datePattern);
		chartData.put(getCsvDataKey(), candlestickData);
	}

	/**
	 * 获取CSV数据
	 * 
	 * @param chart
	 *            K线图表
	 * @return CSV数据
	 */
	protected String getCsvData(CandlestickChart chart, String datePattern) {

		
		Map<String, Map<String, String>> dataset = getDataSet(chart,
				datePattern);

		// 对日期进行升序排序
		Set<String> temp = dataset.keySet();
		String[] keySetArray = new String[temp.size()];
		temp.toArray(keySetArray);
		Arrays.sort(keySetArray);

		// 对复合K线图的曲线数据序列id进行排序，生成的数据以这个排序顺序为准
		String[] curveSeriesIds = null;
		if (chart instanceof CombinationCandlestickChart) {

			CombinationCandlestickChart ccChart = (CombinationCandlestickChart) chart;

			curveSeriesIds = getCurveSeriesIds(ccChart);
			
			sortCurveSeries(curveSeriesIds);
			
			System.out.println();
		}

		// 依照排序结果取出数据，并生成字符串
		Map<String, String> params = null;
		StringBuilder sb = new StringBuilder();

		sb.append("\r\n");

		// for (String key : keySetArray) {
		for (int i = (keySetArray.length - 1); i >= 0; i--) {
			String key = keySetArray[i];
			sb.append(key);
			sb.append(",");

			params = dataset.get(key);

			String open = params.get(KEY_OPEN) != null ? params.get(KEY_OPEN)
					: "";
			sb.append(open);
			sb.append(",");

			String high = params.get(KEY_HIGH) != null ? params.get(KEY_HIGH)
					: "";
			sb.append(high);
			sb.append(",");

			String low = params.get(KEY_LOW) != null ? params.get(KEY_LOW) : "";
			sb.append(low);
			sb.append(",");

			String close = params.get(KEY_CLOSE) != null ? params
					.get(KEY_CLOSE) : "";
			sb.append(close);
			sb.append(",");

			String volume = params.get(KEY_VOLUME) != null ? params
					.get(KEY_VOLUME) : "";
			sb.append(volume);

			// 添加曲线数据，如果有的话
			if (curveSeriesIds != null) {
				for (String id : curveSeriesIds) {

					String value = params.get(id);
					if (value == null) {
						// throw new SeriesDataNotMatchedException("Curve
						// series["
						// + id + "] has no data on date[" + key + "]");
						// 如果当前数据中没有值，则以空字符串代替
						value = "";
					}
					sb.append(",");
					sb.append(value);

				}
			}
			sb.append("\r\n");
		}

		return sb.toString();
	}

	protected Map<String, Map<String, String>> getDataSet(
			CandlestickChart chart, String datePattern) {
		Map<String, Map<String, String>> dataset = new HashMap<String, Map<String, String>>();

		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

		// 将K线数据导入数据集
		CandlestickSeries cs = chart.getCandlestickSeries();
		List<CandlestickPoint> pts = cs.candlestickPoints();
		for (CandlestickPoint cp : pts) {

			String date = sdf.format(cp.getDate());
			Number high = cp.getHigh();
			Number open = cp.getOpen();
			Number close = cp.getClose();
			Number low = cp.getLow();
			Number volume = cp.getVolume();

			Map<String, String> params = new HashMap<String, String>();
			params.put(KEY_OPEN, "" + pushData(open, ""));
			params.put(KEY_HIGH, "" + pushData(high, ""));
			params.put(KEY_LOW, "" + pushData(low, ""));
			params.put(KEY_CLOSE, "" + pushData(close, ""));
			params.put(KEY_VOLUME, "" + pushData(volume, ""));

			dataset.put(date, params);
		}

		if (chart instanceof CombinationCandlestickChart) {
			CombinationCandlestickChart ccChart = (CombinationCandlestickChart) chart;
			// 将其他序列数据导入数据集
			List<CurveSeries> s = ccChart.listCurveSeries();

			for (CurveSeries cvs : s) {
				String id = cvs.getId();

				List<CurvePoint> cvpts = cvs.curvePoints();

				for (CurvePoint cvpt : cvpts) {

					String date = sdf.format(cvpt.getDate());
					Number value = cvpt.getValue();

					Map<String, String> params = dataset.get(date);
					if (params == null) { // 如果在添加K线时，没有指定日期date的数据则创建一个新的Map对象
						params = new HashMap<String, String>();
					}
					params.put(id, pushData(value, ""));

					dataset.put(date, params);
				}

			}
		}

		return dataset;
	}

	/**
	 * 从一个符合数据K线图中导出曲线数据序列ID到一个字符串数组中
	 * 
	 * @param curveSeriesIds
	 *            目标数组
	 * @param chart
	 */
	protected String[] getCurveSeriesIds(
			CombinationCandlestickChart chart) {
		List<String> seriesIds = new ArrayList<String>();
		List<CurveSeries> s = chart.listCurveSeries();
		for (CurveSeries cs : s) {
			seriesIds.add(cs.getId());
		}

		String[] curveSeriesIds = new String[seriesIds.size()];
		seriesIds.toArray(curveSeriesIds);
		return curveSeriesIds;
	}

	/**
	 * 设定曲线数据序列导出顺序
	 * 
	 * @param curveSeriesId
	 */
	protected void sortCurveSeries(String[] curveSeriesIds) {
		Arrays.sort(curveSeriesIds);
	}

	private String pushData(Number value, String substitute) {
		String result = substitute;
		if (value != null) {
			result = "" + value.doubleValue();
		}
		return result;
	}
	
}
