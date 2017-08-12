package com.pemng.serviceSystem.base.util.chartsupport.export;

/**
 * 数据导出异常
 * 
 * @author WANGYEPING
 * 
 */
@SuppressWarnings("serial")
public class DataExportException extends Exception {
	public DataExportException() {
		super();
	}

	public DataExportException(String msg) {
		super(msg);
	}

	public DataExportException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
