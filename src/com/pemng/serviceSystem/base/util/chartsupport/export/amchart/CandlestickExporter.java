package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import com.pemng.serviceSystem.base.util.chartsupport.export.Exporter;

/**
 * 该接口标识一个K线输出器
 * 
 * @author WANGYEPING
 * 
 */
public interface CandlestickExporter extends Exporter {

	/**
	 * 设置K线日期格式
	 * 
	 * @param pattern
	 *            日期格式
	 */
	void setDatePattern(String pattern);
	
	/**
	 * 获取K线日期格式
	 * @return K线日期格式
	 */
	String getDatePattern();

	/**
	 * 设置CSV数据导出时放入MAP的KEY值
	 * 
	 * @param csvDataKey
	 */
	void setCsvDataKey(String csvDataKey);
	
	/**
	 * 获取CSV数据导出时放入MAP的KEY值
	 * 
	 * @return
	 */
	String getCsvDataKey();
}
