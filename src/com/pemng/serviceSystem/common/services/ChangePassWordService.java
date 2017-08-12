package com.pemng.serviceSystem.common.services;

import com.pemng.serviceSystem.base.services.BaseService;

public interface ChangePassWordService extends BaseService {
	//用户修改密码
	public void changePassWord(String password,Long id);
	//检查原密码
	public Boolean checkPassWord(String password,Long id);
}
