package com.pemng.serviceSystem.common.actions;

import com.pemng.serviceSystem.base.actions.BaseAction;
import com.pemng.serviceSystem.common.WebInfoMgmt;
import com.pemng.serviceSystem.common.WebUser;
import com.pemng.serviceSystem.common.services.ChangePassWordService;
import com.pemng.serviceSystem.pojo.TUserInfo;

public class ChangePassWordAction extends BaseAction {
	
	private ChangePassWordService changePassWordService;
	private TUserInfo userInfo;
	private String oldPwd;
	
	/**
	 * 初始化密码修改页面
	 */
	public String inint(){
		return "sucess";
	}
	
	/**
	 * 用户修改密码
	 * @return
	 */
	public String changePassWord(){
		WebUser webUser = WebInfoMgmt.getWebInfo().getWebUser();
		Long id = webUser.getId();
		String password = userInfo.getPassword();
		String aa=this.oldPwd;
		if(changePassWordService.checkPassWord(oldPwd, id)==true){
			changePassWordService.changePassWord(password, id);
			return "sucess";
		}
		return "fail";
	}
	
	
	public TUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(TUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public ChangePassWordService getChangePassWordService() {
		return changePassWordService;
	}

	public void setChangePassWordService(ChangePassWordService changePassWordService) {
		this.changePassWordService = changePassWordService;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	
	
}
