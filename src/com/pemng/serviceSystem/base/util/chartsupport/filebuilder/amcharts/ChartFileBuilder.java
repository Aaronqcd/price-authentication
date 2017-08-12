package com.pemng.serviceSystem.base.util.chartsupport.filebuilder.amcharts;

import com.pemng.serviceSystem.base.util.chartsupport.filebuilder.BuildFileException;

/**
 * 描述一个图表生成器
 * 
 * @author Administrator
 * 
 */
public interface ChartFileBuilder {
	/**
	 * 生成文件
	 * 
	 * @throws BuildFileException
	 *             如果生成文件时发生错误则抛出此异常
	 */
	void buildFile() throws BuildFileException;
}
