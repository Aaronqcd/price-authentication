package com.pemng.serviceSystem.base.util;

import com.pemng.serviceSystem.common.WebInfoMgmt;
import com.pemng.serviceSystem.common.WebUser;

/**
 * 对用户的身份验证工具类，实现方法有：
 * 	1. 校验是否是金融机构用户/监管机构用户/场务用户
 *  2. 判断当前用户是否是场务
 * @author chenguanjun
 * @deprecated
 */
public class AuthenticationUtil {

	/**
	 * 判断当前用户所属机构类型(金融机构/监管机构/场务)
	 * @return 
	 */
	public static String checkOrgType(){
		if(AuthenticationUtil.isAdmin()){
			return "";
		} else {
			WebUser loginUser = WebInfoMgmt.getWebInfo().getWebUser();
		}
		return  null;
	}
	/**
	 * 判断是否是场务
	 * @return true:是；false:否
	 */
	public static boolean isAdmin(){
		return false;
	}
}
