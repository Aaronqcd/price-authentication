package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.CurveSeries;

/**
 * 复合K线图 复合数据比普通的K线数据多包含了一组Category/Value数据序列，一般可用于标示均线等信息
 */
public interface CombinationCandlestickChart extends CandlestickChart {
	/**
	 * 添加一个数据序列
	 * 
	 * 添加时，序列id必须唯一，如果待添加的C/V序列出现重复id，后添加的序列将取代已有序列
	 * 
	 * @param series
	 *            待添加的数据序列
	 * 
	 * @throws NullPointerException
	 *             如果待添加的数据序列为空，则抛出此异常
	 */
	void addCurveSeries(CurveSeries series)
			throws NullPointerException;

	/**
	 * 移除一个数据序列
	 * 
	 * @param series
	 *            待移除的数据序列
	 * @return 移除成功返回true，否则返回false
	 */
	boolean removeCurveSeries(CurveSeries series);

	/**
	 * 移除指定id的一组C/V数据序列
	 * 
	 * @param categoryvalueSeries
	 *            C/V数据序列
	 * @return 如果找到并移除成功则返回true，否则返回false
	 */
	boolean removeCurveSeries(String id);

	/**
	 * 返回该图表数据中所有的数据序列
	 * 
	 * @return 所有的数据序列
	 */
	List<CurveSeries> listCurveSeries();
}
