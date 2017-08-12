package com.pemng.serviceSystem.base.util;

import javax.servlet.http.HttpServletRequest;

import net.fckeditor.handlers.PropertiesLoader;
import net.fckeditor.requestcycle.UserPathBuilder;


public class ArticleImgPathBuilder  implements UserPathBuilder {

	public String getUserFilesAbsolutePath(HttpServletRequest request) {
		return PropertiesLoader.getUserFilesAbsolutePath();
	}

	public String getUserFilesPath(HttpServletRequest request) {
		return request.getContextPath() + "/customservice/cms/getImage.action?imageName=";
	}

}
