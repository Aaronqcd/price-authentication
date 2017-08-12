package com.pemng.serviceSystem.login.actions;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.StringUtil;
import com.pemng.serviceSystem.common.CSession;
import com.pemng.serviceSystem.common.WebActions;
import com.pemng.serviceSystem.common.WebInfo;
import com.pemng.serviceSystem.common.WebUser;
import com.pemng.serviceSystem.login.services.LoginService;
import com.pemng.serviceSystem.pojo.TActnInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;

public class LoginAction extends BaseAction {

	private LoginService loginService;
	private String username;
	private String password;
	private String type;
	private String error_msg;
	

	public String toLogin(){
		return SUCCESS;
	}
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			error_msg = ERROR;
			return ERROR;
		}
		TUserInfo userInfo = loginService.login(username, password);;
		if(userInfo==null){
			error_msg = ERROR;
			return ERROR;
		}else{
			//更新登陆时和登陆次数
			this.loginService.upLoginInfo(userInfo.getId());
			//添加session
			WebUser webUser=(WebUser)PojoUtils.convert2Dto(WebUser.class, userInfo);
			WebInfo webInfo = new WebInfo();
			webInfo.setWebUser(webUser);
			//用户的访问权限 start
			WebActions webActions = WebActions.fakeActions();
			List<TActnInfo> actnInfoList = loginService.getActions(userInfo.getId());
			webActions.setAcitonlist(actnInfoList);
			//用户的访问权限 end 
			webInfo.setWebActions(webActions);
			ActionContext ctx = ActionContext.getContext();
			ctx.getSession().put(CSession.SESSION_WEBINFO, webInfo);
			return SUCCESS;
		}
	}
	
	/**
	 * 注销登录
	 * @return
	 */
	public String loginOut(){
		ActionContext ctx = ActionContext.getContext(); 
		ctx.getSession().remove(CSession.SESSION_WEBINFO);
		
		HttpSession session = this.getRequest().getSession(false);//防止创建Session  
        if(session == null){  
        	return  SUCCESS;  
        }
        session.removeAttribute(CSession.SESSION_WEBINFO);  
		return  SUCCESS;
	}
	
	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String errorMsg) {
		error_msg = errorMsg;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
