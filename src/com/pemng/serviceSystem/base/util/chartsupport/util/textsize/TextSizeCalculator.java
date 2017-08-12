package com.pemng.serviceSystem.base.util.chartsupport.util.textsize;

/**
 * 描述一个文本大小计算器
 *
 */
public interface TextSizeCalculator {

	/**
	 * 根据图表的宽、高值，计算出文本的大小
	 * @param width 图表的宽度
	 * @param height 图表的高度
	 * @return 文本大小
	 */
	int getTextSize(int width, int height);
}
