package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.Series;

/**
 * 描述一个包含多个数据节点（CategValuePoint）的数据序列
 * 
 */
public interface CategoryValueSeries extends Series {

	/**
	 * 添加一个数据节点到当前的数据序列中
	 * 
	 * 方法不区分是否有重复添加
	 * 
	 * @param point
	 *            待添加的数据节点
	 * @throws 如果point为null，则抛出此异常
	 */
	void addCategoryValuePoint(CategoryValuePoint point)
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
	boolean removeCategoryValuePoint(CategoryValuePoint point);

	/**
	 * 移除对应分类的数据节点
	 * 
	 * 移除时，如果存在重复节点，则被移除的节点不确定
	 * 
	 * @param category
	 *            对应分类
	 * @return 找到并移除成功返回true，否则返回false
	 */
	boolean removeCategoryValuePoint(String category);

	/**
	 * 返回该数据序列中所有的数据节点
	 * 
	 * @return 所有的数据节点
	 */
	List<CategoryValuePoint> categoryValuePoints();
}
