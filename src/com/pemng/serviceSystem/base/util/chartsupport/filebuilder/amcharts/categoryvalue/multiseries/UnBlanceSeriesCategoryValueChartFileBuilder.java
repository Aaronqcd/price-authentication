package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.categoryvalue.multiseries;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.data.point.unblancecategoryvalue.UnblanceCategoryValuePoint;
import com.pemng.serviceSystem.base.util.chartsupport.chart.scale.CategoryScale;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;
import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts.ChartFileBuilder;

/**
 * C/V图表文件生成器
 * 
 */
public interface UnBlanceSeriesCategoryValueChartFileBuilder extends
		ChartFileBuilder {
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
	 * 生成文件
	 * 
	 * @throws BuildFileException
	 *             如果生成文件时发生错误则抛出此异常
	 */
	void buildFile() throws BuildFileException;

	/**
	 * 添加一个Category/Value图表数据序列
	 * 
	 * 这个方法等同于addChartSeries(List<CategoryValuePoint> series, false)
	 * 
	 * 数据序列的ID将从0开始自增长
	 * 
	 * @param series
	 *            数据序列
	 * @throws NullPointerException
	 *             如果series为null则抛出此异常
	 */
	void addChartSeries(List<UnblanceCategoryValuePoint> series)
			throws NullPointerException;

	/**
	 * 添加一个Category/Value图表数据序列，同时命名该序列id
	 * 
	 * 这个方法等同于addChartSeries(List<CategoryValuePoint> series, String seriesId,
	 * false)
	 * 
	 * 方法不校验序列id值是否重复
	 * 
	 * @param series
	 *            数据序列
	 * @param seriesId
	 *            序列id
	 * @throws NullPointerException
	 *             如果series为null则抛出此异常
	 */
	void addChartSeries(List<UnblanceCategoryValuePoint> series, String seriesId)
			throws NullPointerException;

	/**
	 * 添加一个Category/Value图表数据序列
	 * 
	 * 数据序列的ID将从0开始自增长
	 * 
	 * 图表的最终category刻度值将以最后一次进行设置为准
	 * 
	 * @param series
	 *            数据序列
	 * @param isCategory
	 *            true表示直接以当前数据序列的category作为整个图表的category，false则相反
	 * @throws NullPointerException
	 *             如果series为null则抛出此异常
	 */
	void addChartSeries(List<UnblanceCategoryValuePoint> series, boolean isCategory)
			throws NullPointerException;

	/**
	 * 添加一个Category/Value图表数据序列，同时命名该序列id
	 * 
	 * 方法不校验序列id值是否重复
	 * 
	 * 图表的最终category刻度值将以最后一次进行设置为准
	 * 
	 * @param series
	 *            数据序列
	 * @param seriesId
	 *            序列id
	 * @param isCategory
	 *            true表示直接以当前数据序列的category作为整个图表的category，false则相反
	 * @throws NullPointerException
	 *             如果series为null则抛出此异常
	 */
	void addChartSeries(List<UnblanceCategoryValuePoint> series, String seriesId,
			boolean isCategory) throws NullPointerException;

	/**
	 * 设置Category轴的刻度显示
	 * 
	 * 如果在调用addChartSeries的方法时没有指定过图表刻度，则一定要调用这个方法进行设置
	 * 
	 * @param categories
	 *            刻度值数组
	 * 
	 * @throws NullPointerException
	 *             如果数组为null，则抛出此异常
	 * 
	 */
	void setCategory(String[] categories) throws NullPointerException;
	
	/**
	 * 设置Category轴的刻度显示
	 * @param categories
	 * @throws NullPointerException
	 */
	void setCategory(CategoryScale[] categories) throws NullPointerException;

	/**
	 * 获取显示刻度
	 * 
	 * @return 如果没有设置则返回的是一个长度为0的String数组
	 */
	String[] getCategory();
	
	/**
	 * 获取显示刻度
	 * @return
	 */
	CategoryScale[] getCategorySclae();

	/**
	 * 清空图表数据
	 * 
	 * 在生成图表文件前、后，建议运行该方法来清空图表，以防造成数据混乱
	 */
	void clearChartSeries();
}
