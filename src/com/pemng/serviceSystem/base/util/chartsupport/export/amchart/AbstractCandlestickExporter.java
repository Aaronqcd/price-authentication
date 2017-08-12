package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import java.util.Map;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;

public abstract class AbstractCandlestickExporter implements
		CandlestickExporter {
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_CSV_DATA_KEY = "csv_data";

	private String csvDataKey = DEFAULT_CSV_DATA_KEY;
	private String datePattern = DEFAULT_DATE_PATTERN;

	
	public String getCsvDataKey() {
		return csvDataKey;
	}

	
	public void setCsvDataKey(String csvDataKey) {
		this.csvDataKey = csvDataKey;
	}

	
	public String getDatePattern() {
		return datePattern;
	}

	
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	
	public Map<String, String> exportChart(Chart chart)
			throws NullPointerException, ClassCastException,
			SeriesDataNotMatchedException {
		return exportChart(chart, getDatePattern());
	}

	/**
	 * 输出一个图表数据
	 * 
	 * 图表数据以变量csvDataKey(可设置)为key值放入返回的Map中
	 * 
	 * 事件数据以变量eventDataKey(可设置)为key值放入返回的Map中
	 * 
	 * 图表数据CSV的次序为date,open,high,low,close...
	 * 
	 * close以后的数据以数据序列的id值升序排序(注意!!!)
	 * 
	 * 其中id的排序规则遵循String的排序规则，即与String的compareTo方法匹配
	 * 
	 * @param chart
	 *            待输出的图表
	 * @param datePattern
	 *            对于K线图，这个值决定了日期的格式化格式，如yyyy-MM-dd等；
	 * @return 图表数据
	 * @throws NullPointerException
	 *             如果chart或者chart的数据序列或者datePattern为null，则抛出此异常
	 * @throws ClassCastException
	 *             如果对于所操作的图表类型转换失败则抛出此异常
	 * @throws IllegalArgumentException
	 *             如果日期格式不合法，则抛出此异常
	 * @throws SeriesDataNotMatchedException
	 *             如果数据序列不匹配，则抛出此异常
	 */
	public abstract Map<String, String> exportChart(Chart chart,
			String datePattern) throws NullPointerException,
			ClassCastException, IllegalArgumentException,
			SeriesDataNotMatchedException;

}
