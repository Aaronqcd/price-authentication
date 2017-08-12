package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point;

import com.pemng.serviceSystem.base.util.chartsupport.chart.tooltip.ToolTip;

/**
 * 实现该接口的数据节点支持自定义提示符
 * 
 */
public interface ToolTipPoint extends Point {
	/**
	 * 设置一个提示符
	 * 
	 * @param toolTip
	 *            提示符
	 */
	void setToolTip(ToolTip toolTip);

	/**
	 * 获取提示符
	 * 
	 * @return 提示符
	 */
	ToolTip getToolTip();

	/**
	 * 设置提示符是否可见
	 * 
	 * @param toolTipVisable
	 *            true表示显示，false表示不显示
	 */
	void setToolTipVisable(boolean toolTipVisable);

	/**
	 * 是否显示提示符
	 * 
	 * @return true表示显示，false表示不显示
	 */
	boolean isToolTipVisable();
}
