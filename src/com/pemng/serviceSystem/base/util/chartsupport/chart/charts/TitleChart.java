package com.pemng.serviceSystem.base.util.chartsupport.chart.charts;

/**
 * 可由用户自定义标题的图表
 *
 */
public interface TitleChart extends Chart {
	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	void setTitle(String title);

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	String getTitle();

	/**
	 * 设置标题是否可见
	 * 
	 * @param titleVisable
	 *            true表示可见，false表示不可见
	 */
	void setTitleVisable(boolean titleVisable);

	/**
	 * 标题是否可见
	 * 
	 * @return true表示可见，false表示不可见
	 */
	boolean isTitleVisable();
}
