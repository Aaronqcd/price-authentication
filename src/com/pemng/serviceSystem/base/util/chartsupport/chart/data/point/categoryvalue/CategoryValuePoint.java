package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue;

import java.math.BigDecimal;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.Point;

/**
 * 描述一个类别、值对的数据节点 通常用于柱状图、曲线图、折线图等
 */
public interface CategoryValuePoint extends Point {
	/**
	 * 设置类别
	 * @param category 类别
	 * @throws NullPointerException 如果category为null，则抛出此异常
	 */
	void setCategory(String category) throws NullPointerException;
	
	/**
	 * 获取类别
	 * @return 类别
	 */
	String getCategory();

	/**
	 * 设置数据节点值
	 * @param value 数据节点值
	 * @throws NullPointerException 如果value为null，则抛出此异常
	 */
	void setValue(Number value) throws NullPointerException;
	
	/**
	 * 获取数据节点值
	 * @return 节点值
	 */
	Number getValue();
	
	/**
	 * @return the scale
	 */
	public int getScale();

	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale);
	
	/**
	 * @return the rawValue
	 */
	public BigDecimal getRawXValue();

	/**
	 * @param rawValue the rawValue to set
	 */
	public void setRawXValue(BigDecimal rawValue);
	
	/**
	 * @return the rawYValue
	 */
	public BigDecimal getRawYValue();
	/**
	 * @param rawYValue the rawYValue to set
	 */
	public void setRawYValue(BigDecimal rawYValue);

}
