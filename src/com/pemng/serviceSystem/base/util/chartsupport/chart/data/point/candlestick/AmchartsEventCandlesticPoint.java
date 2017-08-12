package com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.AmchartsEventPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.event.AmchartsPointEvent;

/**
 * 支持事件的K线数据节点 Am charts 中信息地雷的使用依赖于这个类
 * 
 * 事件(信息地雷)现在不从属于数据节点
 * 
 * 请在Candlestick chart中设置events属性
 * 
 * 使用EventSupportCombinationCandlestickChart的实现类
 * 
 * @since 2010-03-17
 * 
 * @author WANGYEPING
 */
@Deprecated
public class AmchartsEventCandlesticPoint extends DefaultCandlestickPoint
		implements CandlestickPoint, AmchartsEventPoint {

	private List<AmchartsPointEvent> events;

	public AmchartsEventCandlesticPoint() {
		this.events = new ArrayList<AmchartsPointEvent>();
	}

	public AmchartsEventCandlesticPoint(Date date, Number open, Number high,
			Number low, Number close, Number amount) {
		super(date, open, high, low, close, amount);
		this.events = new ArrayList<AmchartsPointEvent>();
	}

	
	public void clearEvent() {
		events.clear();
	}

	
	public void addEvent(AmchartsPointEvent pointEvent)
			throws NullPointerException {
		if (pointEvent == null) {
			throw new NullPointerException("pointEvent can't be null");
		}
		events.add(pointEvent);
	}

	
	public List<AmchartsPointEvent> events() {
		return events;
	}
}
