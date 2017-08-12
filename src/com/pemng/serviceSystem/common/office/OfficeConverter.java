package com.pemng.serviceSystem.common.office;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 转换文件
 * @author liuyucheng
 *
 */
public class OfficeConverter {

	private Log log = LogFactory.getLog(getClass());
	
	/**
	 * 转换文件,把源文件转换成目标文件，可以有doc->html或者html->doc
	 * @param sourceFilePath	: 源文件（doc html）
	 * @param destFilePath		: 目标文件（html doc）
	 * @return
	 */
	public boolean convert(String sourceFilePath , String destFilePath) {
		log.info("文件转换 : 源文件[" + sourceFilePath+"] --> 目标文件[" + destFilePath +"]");
		boolean  flag =  false;
		OpenOfficeConnection connection = null;
		try {
			File sourceFile = new File(sourceFilePath);
			File destFile = new File(destFilePath);
			connection = new SocketOpenOfficeConnection(8100);
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(sourceFile, destFile);
			flag = true;
		} catch (Exception e) {
			log.error("文件转化出现错误：" + e.getMessage());
			flag = false;
			e.printStackTrace();
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
			return flag;
		}
		
		
	}
}
