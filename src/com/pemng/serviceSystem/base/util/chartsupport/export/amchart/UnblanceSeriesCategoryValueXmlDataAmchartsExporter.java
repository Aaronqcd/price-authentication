package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue.AmchartsCategoryAxis;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.Chart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.categoryvalue.unblanceseries.amcharts.AmchartsUnblanceSeriesCategoryValueChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.unblancecategoryvalue.UnblanceCategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.unblancecategoryvalue.UnblanceCategoryValueSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.scale.CategoryScale;
import com.pemng.serviceSystem.base.util.chartsupport.export.Exporter;

/**
 * 该类负责C/V类型的图表数据导出
 * 
 * 该类所接受的所有数据必须是C/V类型的，包括Chart、Series、Point，否则可能发生类型转换异常
 * 
 * 对于多数据序列的图表，如果数据序列的Category标示不统一，则可能在输出数据时发生错误
 * 
 * 数据序列的次序由数据序列自身List次序决定，数据输出不予排序
 * 
 */
public class UnblanceSeriesCategoryValueXmlDataAmchartsExporter implements
		Exporter {
	public static final String CATEGORY_VALUE_XML_DATA = "category_value_xml_data";

	
	public Map<String, String> exportChart(Chart chart)
			throws NullPointerException, ClassCastException {
		Map<String, String> result = new HashMap<String, String>();
		String xmlData = exportXmlData(chart);
		result.put(CATEGORY_VALUE_XML_DATA, xmlData);
		return result;
	}

	// 导出XML格式的数据
	public String exportXmlData(Chart chart) {
		AmchartsUnblanceSeriesCategoryValueChart cvChart = (AmchartsUnblanceSeriesCategoryValueChart) chart;

		AmchartsCategoryAxis categoryAxis = cvChart.getAmchartsCategoryAxis();

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<chart>");
		// 添加X轴刻度
		sb.append(getXAxisData(categoryAxis));
		// 添加数据
		sb.append(getGraphData(cvChart));

		sb.append("</chart>");
		return sb.toString();
	}

	// 获取Category数据
	private String getXAxisData(AmchartsCategoryAxis categoryAxis) {
		if (categoryAxis.listCategoryScale().size() <= 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<xaxis>");
		for (int i = 0; i < categoryAxis.listCategoryScale().size(); i++) {
			CategoryScale scale = categoryAxis.getCategoryScale(i);
			if (scale != null) {
				sb.append("<value xid='").append(i).append("'");
				if (scale.isShow()) {
					sb.append(" show='true'");
				}
				sb.append(">").append(
						categoryAxis.getCategoryScale(i).getValue()).append("</value>");
			}
		}
		sb.append("</xaxis>");
		return sb.toString();
	}

	// 获取图表数据
	private String getGraphData(AmchartsUnblanceSeriesCategoryValueChart chart) {

		List<UnblanceCategoryValueSeries> listSeries = chart.listCategoryValueSeries();
		AmchartsCategoryAxis cAxis = chart.getAmchartsCategoryAxis();
		StringBuilder sb = new StringBuilder();
		sb.append("<graphs>");
		for (UnblanceCategoryValueSeries series : listSeries) {
			sb.append(getSingleSeriesData(series, cAxis));
		}
		sb.append("</graphs>");
		return sb.toString();
	}

	// 获取单个数据序列数据
	private String getSingleSeriesData(UnblanceCategoryValueSeries series,
			AmchartsCategoryAxis cAxis) {

		List<UnblanceCategoryValuePoint> points = series.categoryValuePoints();

		List<String> scales = cAxis.listScale();

		StringBuilder sb = new StringBuilder();
		sb.append("<graph gid='").append(series.getId()).append("'>");
		for (int i = 0; i < points.size(); i++) {
			UnblanceCategoryValuePoint point = points.get(i);
			if (scales.indexOf(point.getCategory()) != -1) {
				sb.append("<value xid='");

				sb.append(scales.indexOf(point.getCategory()));
                
				// sb.append(i);
                if(point.getValue()==null){
                	sb.append("'>").append("").append("</value>");
                }else{
                	sb.append("'>").append(point.getValue()).append("</value>");
                }
			}
		}
		sb.append("</graph>");
		return sb.toString();
	}
}
