package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.event.Event;

/**
 * 支持信息地雷的复合K线图
 * 
 * @author WANGYEPING
 * @since 2010-03-17
 * 
 */
public interface EventSupportCandlestickChart extends CandlestickChart {
	/**
	 * 添加一个事件(信息地雷)
	 * 
	 * @param event
	 */
	void addEvent(Event event);

	/**
	 * 移除一个事件(信息地雷)
	 * 
	 * @param event
	 */
	void removeEvent(Event event);

	/**
	 * 清空所有事件(信息地雷)
	 */
	void clear();

	/**
	 * 返回所有事件(信息地雷)
	 * 
	 * @return 如果没有任何事件(信息地雷)，则返回一个长度为0的列表，否则返回事件列表
	 */
	List<Event> listEvents();
}
