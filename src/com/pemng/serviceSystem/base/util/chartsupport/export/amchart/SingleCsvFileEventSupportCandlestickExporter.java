package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.EventSupportCandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.event.Event;

public class SingleCsvFileEventSupportCandlestickExporter extends
		SingleCsvFileCandlestickExporter implements CandlestickExporter,
		EventSupportExporter {

	public static final String DEFAULT_EVENT_DATA_KEY = "event_data";

	private String eventDataKey = DEFAULT_EVENT_DATA_KEY;

	
	public void setEventDataKey(String eventDataKey) {
		this.eventDataKey = eventDataKey;
	}

	
	public String getEventDataKey() {
		return eventDataKey;
	}

	
	public Map<String, String> exportChart(Chart chart, String datePattern)
			throws NullPointerException, ClassCastException,
			IllegalArgumentException, SeriesDataNotMatchedException {

		Map<String, String> chartData = new HashMap<String, String>();

		// 导出K线数据，以属性值csvDataKey为Key放入Map
		exportCsvData(chartData, chart, datePattern);

		// 如果K线图是一个事件(信息地雷)支持图表则导出事件(信息地雷)数据，并以属性eventDataKey值为Key值放入Map
		exportEventData(chartData, chart, datePattern);

		return chartData;
	}

	/**
	 * 方法会先判断chart是否是一个事件(信息地雷)支持图表EventSupportCandlestickChart
	 * 
	 * 如果确认是一个事件(信息地雷)支持图表则导出事件(信息地雷)数据，并以属性eventDataKey值为Key值放入Map
	 * 
	 * @param chartData
	 *            图表数据结果集
	 * @param chart
	 *            图表
	 * @param datePattern
	 *            日期格式
	 */
	protected void exportEventData(Map<String, String> chartData, Chart chart,
			String datePattern) {
		if (chart instanceof EventSupportCandlestickChart) {
			EventSupportCandlestickChart eventChart = (EventSupportCandlestickChart) chart;
			List<Event> events = eventChart.listEvents();
			String eventData = getEventXmlData(events, datePattern);
			chartData.put(eventDataKey, eventData);
		}
	}

	/**
	 * 获取事件(信息地雷)数据
	 * 
	 * @param events
	 *            事件列表
	 * @param datePattern
	 *            用于日期格式化的模式
	 * @return 事件(信息地雷)数据
	 */
	private String getEventXmlData(List<Event> events, String datePattern) {
		String letter = "";
		String desc = "";
		String dateString = "";
		String eid = "";

		// 日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

		StringBuilder sb = new StringBuilder();
		sb.append("<events>");

		for (Event event : events) {
			Date date = event.getDate();
			if (date != null) {
				dateString = sdf.format(date);
			} else {
				dateString = "";
			}
			eid = event.getId();
			letter = event.getLetter();
			desc = event.getDescription();

			sb.append("<event");
			if (eid != null && !"".equals(eid)) {
				sb.append(" eid='");
				sb.append(eid);
				sb.append("'");
			}
			sb.append(">");
			sb.append("<date>");
			sb.append(dateString);
			sb.append("</date>");
			sb.append("<letter>");
			if (letter != null) {
				sb.append(letter);
			}
			sb.append("</letter>");
			sb.append("<description><![CDATA[");
			sb.append(desc);
			sb.append("]]></description>");
			sb.append("</event>");
		}

		sb.append("</events>");
		return sb.toString();
	}
}
