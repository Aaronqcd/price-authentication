package com.pemng.serviceSystem.base.util.chartsupport.chart.axis.categoryvalue;

import java.util.List;

import com.pemng.serviceSystem.base.util.chartsupport.chart.scale.CategoryScale;

/**
 * 描述一个Am chart 的Category坐标轴
 * 
 */
public interface AmchartsCategoryAxis extends CategoryAxis {
	/**
	 * 添加一个刻度值
	 * 
	 * 添加的刻度值会自动加入到最后一个刻度位置
	 * 
	 * @param scaleValue
	 *            刻度值
	 * @throws NullPointerException
	 *             如果scaleValue为null，则抛出此异常
	 */
	void addAmchartsCategoryScale(String scaleValue)
			throws NullPointerException;

	/**
	 * 添加一个刻度值
	 * 
	 * @param scaleValue
	 *            刻度值
	 * @param show
	 *            是否强制显示
	 * @throws NullPointerException
	 *             如果scaleValue为null，则抛出此异常
	 */
	void addAmchartsCategoryScale(String scaleValue, boolean show)
			throws NullPointerException;

	/**
	 * 添加一个刻度值
	 * 
	 * 添加的刻度值会被插入到id指定的位置上，后方的刻度值会依次顺延
	 * 
	 * 如果添加的刻度标示超过现有最大标示，则刻度值会被追加到最后一个刻度上
	 * 
	 * @param id
	 *            指定的刻度标示
	 * @param scaleValue
	 *            刻度值
	 * @throws NullPointerException
	 *             如果scaleValue为null，则抛出此异常
	 */
	void addAmchartsCategoryScale(int id, String scaleValue)
			throws NullPointerException;

	/**
	 * 添加一个刻度值
	 * 
	 * 添加的刻度值会被插入到id指定的位置上，后方的刻度值会依次顺延
	 * 
	 * 如果添加的刻度标示超过现有最大标示，则刻度值会被追加到最后一个刻度上
	 * @param id 指定的刻度标示
	 * @param scaleValue 刻度值
	 * @param show 是否强制显示
	 * @throws NullPointerException 如果scaleValue为null，则抛出此异常
	 */
	void addAmchartsCategoryScale(int id, String scaleValue, boolean show)
			throws NullPointerException;

	/**
	 * 移除一个刻度值
	 * 
	 * 移除指定值的刻度，如果包含多个相同的刻度值，则只会移除第一个找到的刻度
	 * 
	 * @param scaleValue
	 *            指定的刻度
	 * @return 如果找到并移除则返回true，否则返回false
	 */
	boolean removeAmchartsCategoryScale(String scaleValue);

	/**
	 * 移除一个刻度值
	 * 
	 * 移除指定刻度标示的值
	 * 
	 * @param id
	 *            刻度标示
	 * @return 如果找到并移除成功则返回true，否则返回false
	 */
	boolean removeAmchartsCategoryScale(int id);

	/**
	 * 根据刻度标示查找刻度值
	 * 
	 * @param id
	 *            刻度标示
	 * @return 如果未找到则返回null，否则返回对应的刻度值
	 * @throws IndexOutOfBoundsException
	 */
	String getScale(int id) throws IndexOutOfBoundsException;
	
	/**
	 * 根据刻度标示查找刻度
	 * @param id
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	CategoryScale getCategoryScale(int id) throws IndexOutOfBoundsException;

	/**
	 * 列出所有刻度
	 * 
	 * 刻度顺序以List顺序为准
	 * 
	 * 返回的刻度值列表是实际刻度的一份拷贝
	 * 
	 * @return 所有刻度值
	 */
	List<String> listScale();
	
	/**
	 * 列出所有刻度
	 * 
	 * 刻度顺序以List顺序为准
	 * 
	 * 返回的刻度值列表是实际刻度的一份拷贝
	 * @return
	 */
	List<CategoryScale> listCategoryScale();

	/**
	 * 清除所有刻度
	 */
	void clearScales();
}
