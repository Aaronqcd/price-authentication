package com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter.candlestick;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter.DataExporter;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.candlestick.ChartNotSupportException;

/**
 * K线图数据导出
 * @author WANGYEPING
 *
 */
public interface CandlestickDataExporter extends DataExporter {
	/**
	 * 设置K线数据序列
	 * 
	 * @param series
	 *            K线数据序列
	 * @throws NullPointerException
	 *             如果series为空，则抛出此异常
	 */
	void setCandlestickSeries(List<CandlestickPoint> series)
			throws NullPointerException;

	/**
	 * 添加曲线数据序列
	 * 
	 * 数据序列的ID将从0开始自增长
	 * 
	 * @param series
	 *            曲线数据序列
	 * @throws NullPointerException
	 *             如果series为空则抛出此异常
	 * @throws ChartNotSupportException
	 *             如果图表不支持添加曲线数据序列的操作(例如DefaultCandlestickChart)，则抛出此异常
	 */
	void addCurveSeries(List<CurvePoint> series) throws NullPointerException,
			ChartNotSupportException;

	/**
	 * 添加一个曲线图表数据序列，同时命名该序列id，在生成文件时以File builder会以此名字命名文件，例如id.csv
	 * 
	 * 方法不校验序列id值是否重复，调用该方法时应避免使用相同的值对不同的数据序列id值赋值，否则在生成文件时可能发生错误
	 * 
	 * @param series
	 *            数据序列
	 * @param seriesId
	 *            序列id
	 * @throws NullPointerException
	 *             如果series为空则抛出此异常
	 * @throws ChartNotSupportException
	 *             如果图表不支持添加曲线数据序列的操作(例如DefaultCandlestickChart)，则抛出此异常
	 */
	void addCurveSeries(List<CurvePoint> series, String seriesId)
			throws NullPointerException, ChartNotSupportException;
	

	/**
	 * 清空图表数据
	 * 
	 * 在生成图表文件前、后，建议运行该方法来清空图表，以防造成数据混乱
	 */
	void clearChartSeries();
	
	/**
	 * 设置曲线ID顺序，这个顺序决定导出曲线数据的顺序
	 * @param curveSeriesIds
	 */
	void setCurveSeriesIdOrder(String[] curveSeriesIds);
}
