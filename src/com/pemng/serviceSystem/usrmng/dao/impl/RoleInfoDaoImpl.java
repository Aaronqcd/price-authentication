package com.pemng.serviceSystem.usrmng.dao.impl;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.usrmng.dao.RoleInfoDao;


public class RoleInfoDaoImpl extends BaseDaoHibernate implements RoleInfoDao{

	public RoleInfoDaoImpl() {
		super(TRoleInfo.class);
	}


	
	

}
