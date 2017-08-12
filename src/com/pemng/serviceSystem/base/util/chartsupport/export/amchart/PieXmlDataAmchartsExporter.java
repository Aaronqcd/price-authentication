package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import java.util.HashMap;
import java.util.Map;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.singleseries.SingleSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.categoryvalue.CategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.export.Exporter;

/**
 * 针对饼图的数据转换AM Charts
 * 
 * 数据输出的图表只支持单数据序列SingleSeriesCategoryValueChart
 * 
 * 如果图表包含多数据序列则可能发生错误
 */
public class PieXmlDataAmchartsExporter implements Exporter {
	public static final String PIE_XML_DATA = "pie_xml_data";

	// public static final String PIE_CSV_DATA = "pie_csv_data";

	
	public Map<String, String> exportChart(Chart chart)
			throws NullPointerException, ClassCastException {
		SingleSeriesCategoryValueChart sChart = (SingleSeriesCategoryValueChart) chart;
		Map<String, String> result = new HashMap<String, String>();
		String xmlData = exportXmlData(sChart);
		result.put(PIE_XML_DATA, xmlData);
		return result;
	}

	public String exportXmlData(SingleSeriesCategoryValueChart sChart) {

		CategoryValueSeries series = sChart.getCategoryValueSeries();

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<pie>");
		for (CategoryValuePoint point : series.categoryValuePoints()) {
			String category = point.getCategory();
			double value = point.getValue().doubleValue();
			sb.append("<slice title='");
			sb.append(category);
			sb.append("'>");
			sb.append(value);
			sb.append("</slice>");
		}
		sb.append("</pie>");
		return sb.toString();
	}
}
