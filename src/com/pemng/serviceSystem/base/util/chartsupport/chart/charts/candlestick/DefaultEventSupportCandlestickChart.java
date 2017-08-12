package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick;

import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.event.Event;

/**
 * 支持事件的K线图默认实现
 * 
 * 继承自复合图形，支持多曲线
 * 
 * @author WANGYEPING
 * 
 */
public class DefaultEventSupportCandlestickChart extends
		DefaultCombinationCandlestickChart implements
		EventSupportCandlestickChart {
	private List<Event> events;

	public DefaultEventSupportCandlestickChart() {
		super();
		events = new ArrayList<Event>();
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	public void clear() {
		events.clear();
	}

	public List<Event> listEvents() {
		return events;
	}

	public void removeEvent(Event event) {
		events.remove(event);
	}

}
