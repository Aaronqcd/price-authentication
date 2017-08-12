package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

/**
 * 用于描述当数据序列不匹配时发生的异常
 *
 */
@SuppressWarnings("serial")
public class SeriesDataNotMatchedException extends RuntimeException {
	public SeriesDataNotMatchedException() {
		super();
	}

	public SeriesDataNotMatchedException(String msg) {
		super(msg);
	}

	public SeriesDataNotMatchedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
