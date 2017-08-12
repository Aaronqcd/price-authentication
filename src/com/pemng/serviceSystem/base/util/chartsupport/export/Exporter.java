package com.pemng.serviceSystem.base.util.chartsupport.export;

import java.util.Map;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.SeriesDataNotMatchedException;

/**
 * 文件图表输出
 * 
 */
public interface Exporter {
	/**
	 * 输出一个图表数据
	 * 
	 * @param chart
	 *            待输出的图表
	 * @return 图表数据。输出Map的Key值标示输出数据的类型，Value值为具体的数据。其中输出的类型由具体的子类定义
	 */
	/**
	 * 输出一个图表数据
	 * 
	 * @param chart
	 *            待输出的图表
	 * @return 图表数据。输出Map的Key值标示输出数据的标示，Value值为具体的数据。其中输出的标示可以由具体的子类定义，或由业务方法协议
	 * @throws NullPointerException
	 *             如果chart为null或者chart的所要操作的数据序列为null，则抛出此异常
	 * @throws ClassCastException
	 *             子类输出数据前，可能会将输入的chart转换为Chart的特定子类型，如果转换出错，则抛出此异常 throws
	 *             SeriesDataNotMatchedException 如果数据序列相互不匹配则抛出此异常
	 */
	Map<String, String> exportChart(Chart chart) throws NullPointerException,
			ClassCastException, SeriesDataNotMatchedException;
}
