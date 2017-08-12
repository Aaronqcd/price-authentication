package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.CandlestickSeries;

/**
 * K线图
 * 
 */
public interface CandlestickChart extends Chart {
	/**
	 * 设置一个K线序列
	 * 
	 * @param candlestickSeries
	 *            K线序列
	 * 
	 * @throws NullPointerException
	 *             如果candlestickSeries为空，则抛出此异常
	 */
	void setCandlestickSeries(CandlestickSeries candlestickSeries)
			throws NullPointerException;

	/**
	 * 返回K线图的数据序列
	 * 
	 * @return K线图的数据序列
	 */
	CandlestickSeries getCandlestickSeries();

}
