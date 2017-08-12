package com.pemng.serviceSystem.common;

/**
 *	用于chartsSuport的配置
 */
public class SystemGlobalConstants {
	private static String resourcePath;  //
	/**
	 * 静态html文件的生成根路径
	 */
	private static String htmlRootPath;
	private static String ftpXmlDocPath;

	public static String getFtpXmlDocPath() {
		return ftpXmlDocPath;
	}

	public void setFtpXmlDocPath(String ftpXmlDocPath) {
		SystemGlobalConstants.ftpXmlDocPath = ftpXmlDocPath;
	}

	public static String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		SystemGlobalConstants.resourcePath = resourcePath;
	}

	public static String getHtmlRootPath() {
		return htmlRootPath;
	}

	public void setHtmlRootPath(String htmlRootPath) {
		SystemGlobalConstants.htmlRootPath = htmlRootPath;
	}

}
