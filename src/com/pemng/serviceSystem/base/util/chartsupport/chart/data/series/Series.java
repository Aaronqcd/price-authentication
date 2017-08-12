package com.pemng.serviceSystem.base.util.chartsupport.chart.data.series;

/**
 * 描述一组数据（数据序列） 通常包含多个Point（数据节点）
 */
public interface Series {
	/**
	 * 设置数据系列标示
	 * 
	 * @param id
	 *            数据系列标示
	 */
	void setId(String id);

	/**
	 * 获取数据序列标示
	 * 
	 * @return 数据序列标示
	 */
	String getId();
}
