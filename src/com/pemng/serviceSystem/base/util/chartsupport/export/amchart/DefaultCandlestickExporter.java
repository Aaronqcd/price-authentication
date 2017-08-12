package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CombinationCandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.CandlestickSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.CurveSeries;

/**
 * 默认K线导出器
 * 
 * @author WANGYEPING
 * 
 */
public class DefaultCandlestickExporter extends AbstractCandlestickExporter implements CandlestickExporter {

	@Override
	public Map<String, String> exportChart(Chart chart)
			throws NullPointerException, ClassCastException {

		return exportChart(chart, getDatePattern());
	}

	/**
	 * 输出一个图表数据
	 * 
	 * @param chart
	 *            待输出的图表
	 * @param datePattern
	 *            对于K线图，这个值决定了日期的格式化格式，如yyyy-MM-dd等；
	 * @return 图表数据
	 * @throws NullPointerException
	 *             如果chart或者chart的数据序列或者datePattern为null，则抛出此异常
	 * @throws ClassCastException
	 *             如果对于所操作的图表类型转换失败则抛出此异常
	 * @throws IllegalArgumentException
	 *             如果日期格式不合法，则抛出此异常
	 */
	public Map<String, String> exportChart(Chart chart, String datePattern)
			throws NullPointerException, ClassCastException,
			IllegalArgumentException {
		Map<String, String> chartData = new HashMap<String, String>();

		// 导出K线数据，以属性值csvDataKey为Key放入Map
		exportCsvData(chartData, chart, datePattern);

		// 如果K线图是一个复合型图表，将曲线CSV数据导出，以SeriesId为Key值放入Map
		exportCurveData(chartData, chart, datePattern);

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
		CandlestickSeries series = cChart.getCandlestickSeries();
		String candlestickSeriesData = getCandlestickSeriesCsvData(series,
				datePattern);
		chartData.put(getCsvDataKey(), candlestickSeriesData);
	}

	/**
	 * 方法会先判断chart是否是一个复合型图表CombinationCandlestickChart
	 * 
	 * 如果确认是一个复合型图表则导出曲线CSV数据，并以曲线SeriesId为Key值放入Map
	 * 
	 * @param chartData
	 *            图表数据结果集
	 * @param chart
	 *            图表
	 * @param datePattern
	 *            日期格式
	 */
	protected void exportCurveData(Map<String, String> chartData, Chart chart,
			String datePattern) {
		if (chart instanceof CombinationCandlestickChart) {
			CombinationCandlestickChart ccChart = (CombinationCandlestickChart) chart;
			List<CurveSeries> listSeries = ccChart.listCurveSeries();
			for (CurveSeries cvSeries : listSeries) {
				String data = getCurveSeriesCsvData(cvSeries);
				chartData.put(cvSeries.getId(), data);
			}
		}
	}

	private String getCandlestickSeriesCsvData(CandlestickSeries series,
			String datePattern) {
		List<CandlestickPoint> points = series.candlestickPoints();
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n");
		// 格式化日期为yyyy-MM-dd
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		for (CandlestickPoint point : points) {
			Date date = point.getDate();
			String dateString = sdf.format(date);
			Number open = point.getOpen();
			Number high = point.getHigh();
			Number low = point.getLow();
			Number close = point.getClose();
			Number volume = point.getVolume();

			String openString = pushData(open, "");
			String highString = pushData(high, "");
			String lowString = pushData(low, "");
			String closeString = pushData(close, "");
			String volumeString = pushData(volume, "");

			sb.append(dateString);
			sb.append(",");
			sb.append(openString);
			sb.append(",");
			sb.append(highString);
			sb.append(",");
			sb.append(lowString);
			sb.append(",");
			sb.append(closeString);
			sb.append(",");
			sb.append(volumeString);
			sb.append("\r\n");
		}
		return sb.toString();
	}

	private String getCurveSeriesCsvData(CurveSeries series) {
		List<CurvePoint> points = series.curvePoints();
		SimpleDateFormat sdf = new SimpleDateFormat(getDatePattern());
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n");
		for (CurvePoint point : points) {
			String date = sdf.format(point.getDate());
			Number value = point.getValue();
			String valueString = pushData(value, "");
			sb.append(date);
			sb.append(",");
			sb.append(valueString);
			sb.append("\r\n");
		}
		return sb.toString();
	}

	private String pushData(Number value, String substitute) {
		String result = substitute;
		if (value != null) {
			result = "" + value.doubleValue();
		}
		return result;
	}
}
