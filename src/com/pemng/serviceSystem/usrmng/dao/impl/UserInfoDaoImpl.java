package com.pemng.serviceSystem.usrmng.dao.impl;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.usrmng.dao.UserInfoDao;


public class UserInfoDaoImpl extends BaseDaoHibernate<TUserInfo> implements UserInfoDao{

	public UserInfoDaoImpl() {
		super(TUserInfo.class);
	}
	

}
