package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point;

import com.pemng.serviceSystem.base.util.chartsupport.chart.label.Label;

/**
 * 实现该接口的数据节点支持自定义标签
 * 
 */
public interface LabelPoint extends Point {
	/**
	 * 设置标签
	 * 
	 * @param label
	 *            标签
	 */
	void setLabel(Label label);

	/**
	 * 获取标签
	 * 
	 * @return 标签
	 */
	Label getLabel();

	/**
	 * 设置是否显示标签
	 * 
	 * @param labelVisable
	 *            true表示显示，false表示不显示
	 */
	void setLabelVisable(boolean labelVisable);

	/**
	 * 是否显示标签
	 * 
	 * @return true表示显示，false表示不显示
	 */
	boolean isLabelVisable();
}
