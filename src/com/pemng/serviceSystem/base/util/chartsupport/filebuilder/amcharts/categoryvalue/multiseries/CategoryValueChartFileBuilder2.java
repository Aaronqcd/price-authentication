package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.categoryvalue.multiseries;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.categoryvalue.CategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;
import com.pemng.serviceSystem.base.util.chartsupport.util.textsize.TextSizeCalculator;

/**
 * C/V图表文件生成器
 * 
 * 
 */
@Deprecated
public interface CategoryValueChartFileBuilder2 {
	/**
	 * 设置生成器基准路径
	 * 
	 * @param basePath
	 */
	void setBasePath(String basePath);

	/**
	 * 设置数据文件名字
	 * 
	 * @param fileName
	 *            数据文件名字
	 */
	void setXmlDataFileName(String fileName);

	/**
	 * 设置图表设置文件名字
	 * 
	 * @param fileName
	 *            图表设置文件名字
	 */
	void setSettingsFileName(String fileName);

	/**
	 * 设置背景图片
	 * 
	 * @param backgroundFile
	 *            背景图片文件
	 */
	void setBackgroundFileName(String backgroundFile);

	/**
	 * 设置图表嵌入的HTTP文件名字
	 * 
	 * @param fileName
	 *            图表嵌入的HTTP文件名字
	 */
	void setHtmlFileName(String fileName);

	/**
	 * 设置生成HTML文件时，指定的JS文件所在路径
	 * 
	 * @param path
	 *            指定的JS文件所在路径
	 */
	void setSwfObjectJsPath(String path);

	/**
	 * 设置生成HTML文件时，图表生成DIV中的id
	 * 
	 * @param chartId
	 */
	void setChartId(String chartId);

	/**
	 * 设置生成HTML文件时，生成图表所依赖的SWF文件的完整路径，相对于应用的网站目录
	 * 
	 * @param path
	 */
	void setAmlineSwfPath(String path);

	/**
	 * 设置背景图片的路径
	 * 
	 * 这个路径是指相对于使用图表文件（包括HTML、XMLData、settings_file）的网站应用的web root目录
	 * 
	 * @param path
	 *            背景图片的路径
	 */
	void setBackgroundFilePath(String path);

	/**
	 * 设置图表宽度
	 * 
	 * @param width
	 *            图表宽度
	 */
	void setChartWidth(int width);

	/**
	 * 设置图表高度
	 * 
	 * @param height
	 *            图表高度
	 */
	void setChartHeight(int height);

	/**
	 * 设置一个用根据图表大小于计算文本字体大小的计算器
	 * @param calculator 文本大小计算器
	 */
	void setTextSizeCalculator(TextSizeCalculator calculator);

	/**
	 * 生成文件
	 * 
	 * @throws BuildFileException
	 *             如果生成文件时发生错误则抛出此异常
	 */
	void buildFile() throws BuildFileException;

	/**
	 * 添加一个Category/Value图表数据序列
	 * 
	 * @param series
	 *            数据序列
	 */
	void addChartSeries(List<CategoryValuePoint> series);
}
