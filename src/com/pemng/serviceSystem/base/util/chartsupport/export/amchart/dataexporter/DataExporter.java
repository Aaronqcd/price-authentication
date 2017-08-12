package com.pemng.serviceSystem.base.util.chartsupport.export.amchart.dataexporter;

import com.pemng.serviceSystem.base.util.chartsupport.export.DataExportException;

/**
 * 数据导出器
 * 
 * @author WANGYEPING
 *
 */
public interface DataExporter {
	/**
	 * 导出数据
	 * @return
	 * @throws DataExportException
	 */
	String export() throws DataExportException;
}
