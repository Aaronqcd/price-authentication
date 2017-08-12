package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.event.AmchartsPointEvent;

/**
 * 实现该接口的数据节点支持事件
 * 
 */
public interface AmchartsEventPoint extends Point {

	/**
	 * 添加节点事件
	 * 
	 * @param pointEvent
	 *            节点事件
	 * @throws NullPointerException
	 *             如果pointEvent为null则抛出此异常
	 */
	void addEvent(AmchartsPointEvent pointEvent) throws NullPointerException;

	/**
	 * 获取从属于该数据节点的所有event
	 * 
	 * @return 从属于该数据节点的所有event
	 */
	List<AmchartsPointEvent> events();

	/**
	 * 清除所有节点事件
	 */
	void clearEvent();

}
