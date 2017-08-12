/**    
 * description: 
 * Create on Sep 8, 2008 4:26:19 PM    
 *      
 */
package com.pemng.serviceSystem.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 */
public class Propertiesconfiguration {
	private final static String APP_PROPERTIES_FILE = "resources/system.properties";

	protected final static Log logger = LogFactory.getLog(Propertiesconfiguration.class);

	protected static Properties p = null;

	static {
		init();
	}

	/**
	 * init
	 */
	protected static void init() {
		InputStream in = null;
		try {
			in = Propertiesconfiguration.class.getClassLoader().getResourceAsStream(APP_PROPERTIES_FILE);
			if (in != null) {
				if (p == null)
					p = new Properties();
				p.load(in);
			}
		} catch (IOException e) {
			logger.error("load " + APP_PROPERTIES_FILE + " into Constants error!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close " + APP_PROPERTIES_FILE + " error!");
				}
			}
		}
	}

	/**
	 * 
	 * @param key
	 *            property key.
	 * @param defaultValue
	 */
	protected static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 *            property key.
	 */
	public static String getStringProperty(String key) {
		return p.getProperty(key);
	}

	/**
	 * 获取文件临时目录
	 * 
	 * @return
	 */
	public static String getFileTempDir() {
		return getStringProperty("FILE_TEMP_DIR");
	}

	/**
	 * 获取上传文件保存的根目录
	 * 
	 * @return
	 */
	public static String getFileStorageDir() {
		return getStringProperty("FILE_STORAGE_DIR");
	}
}
