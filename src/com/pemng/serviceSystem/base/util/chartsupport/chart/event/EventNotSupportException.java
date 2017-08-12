package com.pemng.serviceSystem.base.util.chartsupport.chart.event;

/**
 * 此异常描述当图表元素不支持事件时引发的错误
 * 
 */
@SuppressWarnings("serial")
public class EventNotSupportException extends RuntimeException {
	public EventNotSupportException() {
	}

	public EventNotSupportException(String msg) {
		super(msg);
	}

	public EventNotSupportException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
