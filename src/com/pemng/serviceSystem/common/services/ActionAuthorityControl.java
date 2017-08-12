package com.pemng.serviceSystem.common.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.pemng.serviceSystem.common.CSession;
import com.pemng.serviceSystem.common.WebActions;
import com.pemng.serviceSystem.common.WebInfo;
import com.pemng.serviceSystem.pojo.TActnInfo;

/**
 * Created on Jun 19, 2012
 * <p>Description: 权限控制</p>
 * @version        1.0
 */
public class ActionAuthorityControl {
	
	public static final String AUTH_NOT_PASS_BUT_LOGIN = "AUTH_NOT_PASS_BUT_LOGIN";	
	public static final String AUTH_PASS = "AUTH_PASS";
	public static final String AUTH_NOT_PASS_AND_NOT_LOGIN = "AUTH_NOT_PASS_AND_NOT_LOGIN";
	

	/**
	 * 	//权限分三个等级， 无需登录、登录、角色, 目前只需要登录的权限都会加入到所有的角色中
		//step1 验证excludeAction
		//step2 验证是否登录
		//step3 验证butLoginExcludeActions 登录就有的权限
		//step4 验证是否是登录拥有的角色
		//setp5 没有权限访问
	 * Function Name               actionAuth
	 * @param                      //参数1说明
	 * @param                      //参数2说明
	 * @param                      //参数3说明
	 * @return                     //函数返回值的说明
	 * @description                //函数功能、性能等的描述  			             
	 * Modify History:              Date             Programmer       Notes
	 *                            ---------        ---------------  ---------
	 *                            Jun 19, 2012             yunxiangfu         Initial
	 **********************************************************************
	 */
	public static String actionAuth(ActionInvocation invocation, Set excludeActions, Set butLoginExcludeActions){
		Map<String, Object> session = ActionContext.getContext().getSession();
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

		String actionName = null;
		if(invocation.getProxy().getNamespace().equals("/")){
			actionName = invocation.getProxy().getNamespace() + invocation.getProxy().getActionName() + ".action";
		}else{
			actionName = invocation.getProxy().getNamespace() +"/" + invocation.getProxy().getActionName() + ".action";
		}
		if(actionName.startsWith("/")){//去除首字符'/'
			actionName = actionName.substring(1);
		}
		//step1 无需登录登陆验证的，排除过滤处理
		if(excludeActions.contains(actionName)){
			return AUTH_PASS;
		}

		//step2 验证session是否登录
		if(null == session.get(CSession.SESSION_WEBINFO)){
			return AUTH_NOT_PASS_AND_NOT_LOGIN;
		}

		//step3 验证登录后的，排除过滤处理
		if(butLoginExcludeActions.contains(actionName)){//包含则通过
			return AUTH_PASS;
		}
		
		//step4 是否是角色包含的
		List<TActnInfo> list = new ArrayList<TActnInfo>();
		WebInfo webInfo = (WebInfo)session.get(CSession.SESSION_WEBINFO);
		WebActions webActions = webInfo.getWebActions();
		if(webActions != null){
			for (TActnInfo acl : webActions.getAcitonlist()) {
				if (acl!=null && acl.getActnUrl()!=null && acl.getActnUrl().contains(actionName)){
					list.add(acl);
				}
			}
		}
		if(list.size()>0){
			//将该页面中可操作的动作传递给页面
			request.setAttribute("aclList", list);
			return AUTH_PASS;
		}
		
		//step5 没有权限访问
		return AUTH_NOT_PASS_BUT_LOGIN;
	}
}