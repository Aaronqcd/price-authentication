package com.pemng.serviceSystem.base.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pemng.serviceSystem.common.WebActions;
import com.pemng.serviceSystem.common.WebInfoMgmt;
import com.pemng.serviceSystem.common.WebUser;


/**
 * 基类接口实现
 * 
 * @author shaojie
 * 
 * 
 */
public class BaseServiceImpl implements BaseService {

	//TODO 文件参数文件到时候重新设置
	transient private Log log = LogFactory.getLog(getClass());
//	transient protected Properties messagesEn = PropertiesUtil
//			.getPropByClassPath("resource/messages_en.properties");
//	transient protected Properties messagesZh = PropertiesUtil
//			.getPropByClassPath("resource/messages_zh.properties");

	
	public Log getLog() {
		if (null == log)
			log = LogFactory.getLog(getClass());
		return log;
	}

	public WebUser getCurrentWebUser(){
		return WebInfoMgmt.getWebInfo().getWebUser();
	}
	
	public WebActions getCurrentActions(){
		return WebInfoMgmt.getWebInfo().getWebActions();
	}

//	public WebOrg getCurrentOrg() {
//		
//		return WebInfoMgmt.getWebInfo().getWebOrg();
//	}

}
