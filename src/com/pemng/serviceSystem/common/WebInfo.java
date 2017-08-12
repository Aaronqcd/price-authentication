package com.pemng.serviceSystem.common;
/**
 * 储存用户基本信息、角色、设置等。保存在Session中
 * @author sherlockq
 *
 */
public class WebInfo {

	WebUser webUser = null;
	WebActions webActions = null;
//	WebOrg webOrg =null;

	

//	public WebOrg getWebOrg() {
//		return webOrg;
//	}
//	public void setWebOrg(WebOrg webOrg) {
//		this.webOrg = webOrg;
//	}
	public WebUser getWebUser() {
		return webUser;
	}
	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}
	public WebActions getWebActions() {
		return webActions;
	}
	public void setWebActions(WebActions webActions) {
		this.webActions = webActions;
	}
	
}
