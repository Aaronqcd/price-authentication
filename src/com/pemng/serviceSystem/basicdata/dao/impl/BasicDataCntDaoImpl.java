package com.pemng.serviceSystem.basicdata.dao.impl;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.basicdata.dao.BasicDataCntDao;
import com.pemng.serviceSystem.pojo.TBasicDataCnt;

public class BasicDataCntDaoImpl extends BaseDaoHibernate implements BasicDataCntDao{

	public BasicDataCntDaoImpl() {
		super(TBasicDataCnt.class);
	}
}

