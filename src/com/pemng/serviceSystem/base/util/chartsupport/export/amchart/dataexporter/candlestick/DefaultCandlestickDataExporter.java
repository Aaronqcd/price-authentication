package com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter.candlestick;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CombinationCandlestickChart;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.CandlestickPoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.candlestick.curve.CurvePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.CandlestickSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.DefaultCandlestickSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.CurveSeries;
import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.DefaultCurveSeries;
import com.pemng.serviceSystem.base.util.chartsupport.export.DataExportException;
import com.pemng.serviceSystem.base.util.chartsupport.export.amchart.SingleCsvFileCurveCsvDataOrderableCandlestickExporter;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.candlestick.ChartNotSupportException;

public class DefaultCandlestickDataExporter implements CandlestickDataExporter {
	private SingleCsvFileCurveCsvDataOrderableCandlestickExporter innerExporter;
	private CombinationCandlestickChart chart;

	public void setChart(CombinationCandlestickChart chart) {
		this.chart = chart;
	}

	public void setInnerExporter(
			SingleCsvFileCurveCsvDataOrderableCandlestickExporter innerExporter) {
		this.innerExporter = innerExporter;
	}

	public DefaultCandlestickDataExporter() {
	}

	
	public void addCurveSeries(List<CurvePoint> series)
			throws NullPointerException, ChartNotSupportException {
		CurveSeries cs = new DefaultCurveSeries();
		for (CurvePoint pt : series) {
			cs.addCurvePoint(pt);
		}
		chart.addCurveSeries(cs);
	}

	
	public void addCurveSeries(List<CurvePoint> series, String seriesId)
			throws NullPointerException, ChartNotSupportException {
		CurveSeries cs = new DefaultCurveSeries();
		cs.setId(seriesId);
		for (CurvePoint pt : series) {
			cs.addCurvePoint(pt);
		}
		chart.addCurveSeries(cs);
	}

	
	public void clearChartSeries() {
		chart.listCurveSeries().clear();
	}

	
	public void setCandlestickSeries(List<CandlestickPoint> series)
			throws NullPointerException {
		CandlestickSeries cs = new DefaultCandlestickSeries();

		for (CandlestickPoint pt : series) {
			cs.addCandlestickPoint(pt);
		}

		chart.setCandlestickSeries(cs);
	}

	
	public String export() throws DataExportException {
		return innerExporter.exportChart(chart).get(
				innerExporter.getCsvDataKey());
	}

	
	public void setCurveSeriesIdOrder(String[] curveSeriesIds) {
		innerExporter.setOrder(curveSeriesIds);
	}

}
