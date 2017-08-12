package com.pemng.serviceSystem.common.interceptors;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.common.Result;
import com.pemng.serviceSystem.common.services.ActionAuthorityControl;

public class SessionAuthorityInterceptor extends AbstractInterceptor {

	transient private static Log log = LogFactory.getLog(SessionAuthorityInterceptor.class);

	// 未登录，无需验证method
	protected Set excludeActions = Collections.EMPTY_SET;
	// 已登录，无需验证method
	protected Set butLoginExcludeActions = Collections.EMPTY_SET;
	
	private static final String SESSION_LOST ="sessionLost";
	
	private static final String NO_RIGHT="noRight";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		//权限验证
		String auth = ActionAuthorityControl.actionAuth(invocation, excludeActions,butLoginExcludeActions);
		String action = invocation.getProxy().getNamespace() +"/" + invocation.getProxy().getActionName() + ".action";
		if(ActionAuthorityControl.AUTH_PASS.equals(auth)){  //权限验证通过
			//校验通过
		}else if(ActionAuthorityControl.AUTH_NOT_PASS_AND_NOT_LOGIN.equals(auth)){  //权限验证不通过且未登录
			log.warn("-------------not right----------------- " + action);
			if(isAjax(request)){//如果是ajax请求
				writeSessionResultToResponse(request,SESSION_LOST);
				return null;
			}else{
				return "loginPage";
			}
		}else if(ActionAuthorityControl.AUTH_NOT_PASS_BUT_LOGIN.equals(auth)){  //权限验证不通过、但已登录
			log.warn("-------------not right but already login----------------- " + action);
			if(isAjax(request)){//如果是ajax请求
				writeSessionResultToResponse(request,NO_RIGHT);
				return null;
			}else{
				return "login_not_right";
			}
		}
		
		String result = invocation.invoke();
		
		return result;
	}
	private void writeSessionResultToResponse(HttpServletRequest request,String message) throws IOException {
				
		String accept = request.getHeader("Accept");//jquery 框架在ajax请求头中会把dataType放在Accept中
		String msg = "";
		if(accept.contains("text")){//dataType类型
			msg = message;
		}else if(accept.contains("json")){
			Result result = new Result("sessionLost");
			msg = JsonUtil.bean2json(result);
			
		}
		ServletActionContext.getResponse().getWriter().write(msg);
	}

	private boolean isAjax(HttpServletRequest request) {
		//目前项目的所有ajax请求使用的都是jquery api,该框架会自动在ajax请求头中加入X-Requested-With,故使用该策略判断是否是ajax请求，如自己实现ajax可能不会起作用
		String requestType = request.getHeader("X-Requested-With");		
		return requestType!=null;
	}


	public void setExcludeActions(String excludeActions) {
		this.excludeActions = TextParseUtil.commaDelimitedStringToSet(excludeActions);
	}
	public void setButLoginExcludeActions(String butLoginExcludeActions) {
		this.butLoginExcludeActions = TextParseUtil.commaDelimitedStringToSet(butLoginExcludeActions);
	}
	

}
