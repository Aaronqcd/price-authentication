package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

import com.pemng.serviceSystem.base.util.chartsupport.export.Exporter;

/**
 * 实现该接口的输出器支持事件处理(信息地雷)
 * @author WANGYEPING
 *
 */
public interface EventSupportExporter extends Exporter {
	/**
	 * 设置事件(信息地雷)导出时放入MAP的KEY值
	 * @param eventDataKey
	 */
	void setEventDataKey(String eventDataKey);
	
	/**
	 * 获取事件(信息地雷)导出时放入MAP的KEY值
	 * @return
	 */
	String getEventDataKey();
}
