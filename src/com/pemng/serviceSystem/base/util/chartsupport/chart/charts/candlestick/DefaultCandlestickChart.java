package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.CandlestickSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.DefaultCandlestickSeries;

public class DefaultCandlestickChart implements Chart, CandlestickChart {
	private CandlestickSeries candlestickSeries;

	/**
	 * 默认构造器
	 * 
	 * 以DefaultCandlestickSeries初始化数据序列
	 */
	public DefaultCandlestickChart() {
		candlestickSeries = new DefaultCandlestickSeries();
	}

	/**
	 * 用一个数据序列candlestickSeries出示化图表
	 * 
	 * @param candlestickSeries
	 *            数据序列
	 * @throws NullPointerException
	 *             如果用于初始化的candlestickSeries为空，则抛出此异常
	 */
	public DefaultCandlestickChart(CandlestickSeries candlestickSeries)
			throws NullPointerException {
		if (candlestickSeries == null) {
			throw new NullPointerException(
					"candlestickSeries can't be null");
		}
		this.candlestickSeries = candlestickSeries;
	}

	public CandlestickSeries getCandlestickSeries() {
		return candlestickSeries;
	}

	public void setCandlestickSeries(CandlestickSeries candlestickSeries) {
		this.candlestickSeries = candlestickSeries;
	}

}
