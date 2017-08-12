package com.pemng.serviceSystem.base.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 属性文件工具类
 * @author shaojie
 *
 */
public class PropertiesUtil {

	transient private static Log log = LogFactory.getLog(PropertiesUtil.class);

	public static Properties getPropByClassPath(String fileName) {

		Properties prop = new Properties();

		try {
			InputStream is = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(fileName);
			prop.load(is);

			if (is != null)
				is.close();

			return prop;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			return null;
		}
	}

}
