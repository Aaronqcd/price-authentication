package com.pemng.serviceSystem.base.util.chartsupport.chart.charts.candlestick;

import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.series.candlestick.curve.CurveSeries;

public class DefaultCombinationCandlestickChart extends DefaultCandlestickChart
		implements CombinationCandlestickChart {
	private List<CurveSeries> curveSerieses;
	
	public DefaultCombinationCandlestickChart() {
		super();
		curveSerieses = new ArrayList<CurveSeries>();
	}

	public void addCurveSeries(CurveSeries series) throws NullPointerException {
		if (series == null) {
			throw new NullPointerException("Series can't be null");
		}

		if (curveSerieses == null) {
			curveSerieses = new ArrayList<CurveSeries>();
		}
		removeCurveSeries(series.getId());
		curveSerieses.add(series);
	}

	public List<CurveSeries> listCurveSeries() {
		if (curveSerieses == null) {
			curveSerieses = new ArrayList<CurveSeries>();
		}
		return curveSerieses;
	}

	public boolean removeCurveSeries(CurveSeries curveSeries) {
		if (curveSerieses == null) {
			curveSerieses = new ArrayList<CurveSeries>();
		}
		return curveSerieses.remove(curveSeries);
	}

	public boolean removeCurveSeries(String id) {
		if (curveSerieses == null) {
			curveSerieses = new ArrayList<CurveSeries>();
		}
		for (CurveSeries series : curveSerieses) {
			if (id.equals(series.getId())) {
				return removeCurveSeries(series);
			}
		}

		return false;
	}

}
