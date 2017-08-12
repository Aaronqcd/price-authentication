package com.pemng.serviceSystem.basicdata.dao.impl;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.basicdata.dao.BasicDataDao;
import com.pemng.serviceSystem.pojo.TBasicData;


public class BasicDataDaoImpl extends BaseDaoHibernate implements BasicDataDao{

	public BasicDataDaoImpl() {
		super(TBasicData.class);
	}


	
	

}
