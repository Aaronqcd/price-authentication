package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick;

import java.util.Date;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.Series;

/**
 * 描述一个K线图的数据序列 通常包含多个CandlestickPoint（数据节点）
 * 
 */
public interface CandlestickSeries extends Series {

	/**
	 * 添加一个数据节点到当前的数据序列中
	 * 
	 * 方法不区分是否有重复添加
	 * 
	 * @param point
	 *            待添加的数据节点
	 * @throws NullPointerException
	 *             如果添加数据节点为null，则抛出此异常
	 */
	void addCandlestickPoint(CandlestickPoint point)
			throws NullPointerException;

	/**
	 * 从数据序列中移除一个数据节点
	 * 
	 * 移除时，如果存在重复节点，则被移除的节点不确定
	 * 
	 * @param point
	 *            待移除的数据节点
	 * @return 找到并移除成功返回true，否则返回false
	 */
	boolean removeCandlestickPoint(CandlestickPoint point);

	/**
	 * 移除序列中对应日期的数据节点
	 * 
	 * 移除时，如果存在重复节点，则被移除的节点不确定
	 * 
	 * 需要注意的是，查找指定日期时日期的比较依赖于java.util.Date的equals方法，即比较getTime返回的毫秒数
	 * 
	 * @param pointDate
	 *            指定日期的数据节点
	 * @return 找到并移除成功返回true，否则返回false
	 */
	boolean removeCandlestickPoint(Date pointDate);

	/**
	 * 返回该数据序列中所有的数据节点
	 * 
	 * @return 所有的数据节点
	 */
	List<CandlestickPoint> candlestickPoints();
	
	/**
	 * 清空数据节点
	 */
	void clear();
}
