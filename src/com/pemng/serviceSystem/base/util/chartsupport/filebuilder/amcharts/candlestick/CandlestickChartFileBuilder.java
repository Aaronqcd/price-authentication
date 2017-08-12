package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.candlestick;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.event.Event;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.ChartFileBuilder;

/**
 * K线图表文件生成器
 * 
 */
public interface CandlestickChartFileBuilder extends ChartFileBuilder {
	/**
	 * 设置生成器基准路径
	 * 
	 * @param basePath
	 */
	void setBasePath(String basePath);

	/**
	 * 设置K线数据文件名字
	 * 
	 * @param fileName
	 *            数据文件名字
	 */
	void setCandlestickDataFileName(String fileName);

	/**
	 * 设置事件文件名字
	 * 
	 * @param fileName
	 *            事件文件名字
	 */
	void setEventFileName(String fileName);

	/**
	 * 生成文件
	 * 
	 * 该方法将生成一个K线数据文件、一个K线事件文件（文件名分别由设置方法setCandlestickDataFileName、setEventFileName设置的值决定）
	 * 
	 * 和若干个曲线数据文件（如果存在曲线数据的话），其中曲线数据文件的名字将以曲线数据序列的id值决定
	 * 
	 * @throws BuildFileException
	 *             如果生成文件时发生错误则抛出此异常
	 */
	void buildFile() throws BuildFileException;

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
	 * 设置事件(信息地雷)列表
	 * 
	 * @param events
	 * 
	 * @throws NullPointerException
	 *             如果events为null，则抛出此异常
	 * @throws ChartNotSupportException
	 *             如果图表不支持事件(信息地雷)的操作(例如DefaultCandlestickChart)，则抛出此异常
	 * 
	 * @since 2010-03-17
	 */
	void setEvents(List<Event> events) throws NullPointerException,
			ChartNotSupportException;

	/**
	 * 清空事件(信息地雷)列表
	 * 
	 * 在生成图表文件前、后，建议运行该方法来清空图表，以防造成数据混乱
	 * 
	 * @throws ChartNotSupportException
	 *             如果图表不支持添加曲线数据序列的操作(例如DefaultCandlestickChart)，则抛出此异常
	 */
	void clearEvents() throws ChartNotSupportException;
}
