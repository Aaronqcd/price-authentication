package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue;

import java.math.BigDecimal;

/**
 * CategoryValuePoint 最基本的实现
 * 
 */
public class DefaultCategoryValuePoint implements CategoryValuePoint,Comparable {
	private String category;
	private Number value;
	private int scale;
	//原始数据,可以存储原始X坐标值
	private BigDecimal rawXValue;
	//原始数据,可以存储原始Y坐标值
	private BigDecimal rawYValue;

	/**
	 * 默认构造器
	 */
	public DefaultCategoryValuePoint() {
	}

	/**
	 * 根据category/value对，构造一个数据节点
	 * 
	 * @param category
	 *            类别或节点名称
	 * @param value
	 *            节点值
	 * @throws NullPointerException
	 *             如果category或者value为null，则抛出此异常
	 */
	public DefaultCategoryValuePoint(String category, Number value)
			throws NullPointerException {
//		if (category == null || value == null) {
//			throw new NullPointerException("category and value can't be null");
//		}
		this.category = category;
		this.value = value;
	}

	
	public String getCategory() {
		return category;
	}

	
	public void setCategory(String category) throws NullPointerException {
		if (category == null) {
			throw new NullPointerException("category can't be null");
		}
		this.category = category;
	}

	
	public Number getValue() {
		return value;
	}

	
	public void setValue(Number value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("category can't be null");
		}
		this.value = value;
	}

	
	public int compareTo(Object o) {
		try{
			DefaultCategoryValuePoint other = (DefaultCategoryValuePoint)o;
			if( other.getCategory().equals(this.category)) return 0;
			double d1 = Double.parseDouble(this.category);
			double d2 = Double.parseDouble(other.getCategory());
			return d1>d2 ? 1:-1;
		}catch(Exception ex){
			return 1;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DefaultCategoryValuePoint other = (DefaultCategoryValuePoint) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	

	/**
	 * @return the rawYValue
	 */
	public BigDecimal getRawYValue() {
		return rawYValue;
	}

	/**
	 * @param rawYValue the rawYValue to set
	 */
	public void setRawYValue(BigDecimal rawYValue) {
		this.rawYValue = rawYValue;
	}

	/**
	 * @return the rawXValue
	 */
	public BigDecimal getRawXValue() {
		return rawXValue;
	}

	/**
	 * @param rawXValue the rawXValue to set
	 */
	public void setRawXValue(BigDecimal rawXValue) {
		this.rawXValue = rawXValue;
	}

}
