package com.pemng.serviceSystem.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户信息管理方法，在线程中保存信息，使得整个应用各层都可获取
 * @author shaojie
 *
 */
public class WebInfoMgmt {

	private static ThreadLocal<WebInfo> webinfo = new ThreadLocal<WebInfo>();
	
	public static WebInfo getWebInfo(){
		WebInfo webInfo = null; //改为从session里读取
		ActionContext context = ActionContext.getContext();
		if(context != null){
			Map<String, Object> session = context.getSession();
			if(session != null){
				webInfo = (WebInfo)session.get(CSession.SESSION_WEBINFO);
			}
		}
		if(webInfo == null){
			webInfo = new WebInfo();
		}
		return webInfo;
	}
	
	
}
