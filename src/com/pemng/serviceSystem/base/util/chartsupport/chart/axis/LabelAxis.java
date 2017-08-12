package com.pemng.serviceSystem.base.util.chartsupport.chart.axis;

/**
 * 描述一个用户可自定义坐标轴标题的坐标轴
 *
 */
public interface LabelAxis extends Axis {
	/**
	 * 设置坐标轴标题
	 * 
	 * @param title
	 *            坐标轴标题
	 */
	void setLabel(String label);

	/**
	 * 设置坐标轴标题是否可见
	 * 
	 * @param labelVisable
	 *            true表示可见，false表示不可见
	 */
	void setLabelVisable(boolean labelVisable);

	/**
	 * 设置坐标轴是否可见
	 * 
	 * @param visable
	 *            true表示坐标轴可见，false表示坐标轴不可见
	 */
	void setVisable(boolean visable);
}
