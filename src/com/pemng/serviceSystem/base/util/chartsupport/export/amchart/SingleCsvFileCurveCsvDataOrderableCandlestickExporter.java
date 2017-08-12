package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import java.util.Arrays;

import com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick.CombinationCandlestickChart;

public class SingleCsvFileCurveCsvDataOrderableCandlestickExporter extends
		SingleCsvFileCandlestickExporter implements CurveCsvDataOrderable {
	private String[] curveSeriesId;

	
	public void setOrder(String[] curveSeriesId) {
		this.curveSeriesId = curveSeriesId;
	}

	
	public String[] getOrder() {
		return curveSeriesId;
	}

	
	protected void sortCurveSeries(String[] curveSeriesIds) {
		curveSeriesIds = Arrays.copyOf(curveSeriesId, curveSeriesId.length);
	}

	
	protected String[] getCurveSeriesIds(CombinationCandlestickChart chart) {
		String[] curveSeriesIds = Arrays.copyOf(curveSeriesId,
				curveSeriesId.length);
		return curveSeriesIds;
	}

}
