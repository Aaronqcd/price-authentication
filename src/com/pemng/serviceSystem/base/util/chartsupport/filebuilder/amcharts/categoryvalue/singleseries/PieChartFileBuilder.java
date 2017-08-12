package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.categoryvalue.singleseries;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.ChartFileBuilder;

/**
 * 饼图文件生成器
 * 
 */
public interface PieChartFileBuilder extends ChartFileBuilder {
	/**
	 * 设置生成器基准路径
	 * 
	 * @param path
	 */
	void setBasePath(String path);

	/**
	 * 设置数据文件名字
	 * 
	 * @param fileName
	 *            数据文件名字
	 */
	void setXmlDataFileName(String fileName);

	/**
	 * 生成文件
	 * 
	 * @throws BuildFileException
	 *             如果生成文件时发生错误则抛出此异常
	 */
	void buildFile() throws BuildFileException;

	/**
	 * 设置一个图表数据序列
	 * 
	 * @param series
	 *            数据序列
	 * @throws NullPointerException
	 *             如果series为null，则抛出此异常
	 */
	void setChartSeries(List<CategoryValuePoint> series)
			throws NullPointerException;

	/**
	 * 清空图表数据
	 * 
	 * 在生成图表文件前、后，建议运行该方法来清空图表，以防造成数据混乱
	 */
	void clearChartSeries();
}
