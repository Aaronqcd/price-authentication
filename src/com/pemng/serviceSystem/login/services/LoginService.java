package com.pemng.serviceSystem.login.services;

import java.util.List;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.pojo.TActnInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;

public interface LoginService extends BaseService{

	public TUserInfo login(String accountNo,String password);
	public List<TActnInfo> getActions(Long userId);
	public TUserInfo login(String username);
	public void upLoginInfo(Long userId);
}
