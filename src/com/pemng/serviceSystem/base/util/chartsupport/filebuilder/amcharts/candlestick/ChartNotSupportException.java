package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.candlestick;

/**
 * 描述一个图表不支持某种操作的错误
 * @author WANGYEPING
 * @since 2010-03-17
 *
 */
@SuppressWarnings("serial")
public class ChartNotSupportException extends RuntimeException {
	public ChartNotSupportException() {
		super();
	}

	public ChartNotSupportException(String msg) {
		super(msg);
	}

	public ChartNotSupportException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
