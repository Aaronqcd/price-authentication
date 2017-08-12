package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.candlestick;

import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;

@SuppressWarnings("serial")
public class BuildCandlestickCsvDataFileException extends BuildFileException {
	public BuildCandlestickCsvDataFileException() {
		super();
	}

	public BuildCandlestickCsvDataFileException(String msg) {
		super(msg);
	}

	public BuildCandlestickCsvDataFileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
