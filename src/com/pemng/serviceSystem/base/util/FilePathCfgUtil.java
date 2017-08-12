package com.pemng.serviceSystem.base.util;


/**
 * Created on May 31, 2012
 * <p>Description: 文件存储路径配置文件</p>
 * @version        1.0
 */
public class FilePathCfgUtil {
	
	private static final String tempDir;//附件上传路径
	private static final String cmsDir;//委托书附件
	//private static final String techDir;//技术报告附件
	//private static final String recDir;//实物勘验附件
	
	static {
		tempDir = Propertiesconfiguration.getStringProperty("FILE_TEMP_DIR");
		cmsDir = Propertiesconfiguration.getStringProperty("FILE_CMS_ATTR_DIR");
		//techDir = Propertiesconfiguration.getStringProperty("FILE_TECH_ATTR_DIR");
		//recDir = Propertiesconfiguration.getStringProperty("FILE_REC_ATTR_DIR");
	}

	public static String getCmsdir() {
		return cmsDir;
	}

	/*public static String getTechdir() {
		return techDir;
	}

	public static String getRecdir() {
		return recDir;
	}*/

	public static String getTempDir() {
		return tempDir;
	}

}
