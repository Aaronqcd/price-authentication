package com.pemng.serviceSystem.usrmng.services.impl;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.usrmng.dao.UserRoleRelDao;
import com.pemng.serviceSystem.usrmng.services.UserRoleRelMangerService;

public class UserRoleRelMangerServiceImpl extends BaseServiceImpl implements UserRoleRelMangerService{
   private UserRoleRelDao userRoleRelDao;
	public UserRoleRelDao getUserRoleRelDao() {
	return userRoleRelDao;
}
public void setUserRoleRelDao(UserRoleRelDao userRoleRelDao) {
	this.userRoleRelDao = userRoleRelDao;
}
	
}
