package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.Series;

/**
 * 描述K线图中的曲线数据序列
 * 
 */
public interface CurveSeries extends Series {
	/**
	 * 添加一个数据节点到当前的数据序列中
	 * 
	 * 方法不区分是否有重复添加
	 * 
	 * @param point
	 *            待添加的数据节点
	 * @throws 如果point为null，则抛出此异常
	 */
	void addCurvePoint(CurvePoint point) throws NullPointerException;

	/**
	 * 从数据序列中移除一个数据节点
	 * 
	 * 移除时，如果存在重复节点，则被移除的节点不确定
	 * 
	 * @param point
	 *            待移除的数据节点
	 * @return 找到并移除成功返回true，否则返回false
	 */
	boolean removeCurvePoint(CurvePoint point);

	/**
	 * 移除对应分类的数据节点
	 * 
	 * 移除时，如果存在重复节点，则被移除的节点不确定
	 * 
	 * @param date
	 *            对应日期
	 * @param 对应日期格式
	 * @return 找到并移除成功返回true，否则返回false
	 */
	boolean removeCurvePoint(String date, String datePattern);

	/**
	 * 返回该数据序列中所有的数据节点
	 * 
	 * @return 所有的数据节点
	 */
	List<CurvePoint> curvePoints();
}
