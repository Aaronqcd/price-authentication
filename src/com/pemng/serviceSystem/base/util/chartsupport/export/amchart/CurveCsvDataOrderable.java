package com.pemng.serviceSystem.base.util.chartsupport.export.amchart;

/**
 * 实现该接口的导出器可以定义曲线数据序列生成排列顺序
 * 
 * @author WANGYEPING
 * 
 */
public interface CurveCsvDataOrderable {
	/**
	 * 设置曲线数据序列顺序
	 * 
	 * @param curveSeriesId
	 */
	void setOrder(String[] curveSeriesId);
	
	/**
	 * 获取曲线数据序列顺序
	 * @return
	 */
	String[] getOrder();
}
