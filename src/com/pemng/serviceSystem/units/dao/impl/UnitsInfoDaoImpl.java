package com.pemng.serviceSystem.units.dao.impl;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.pojo.TUnitsInfo;
import com.pemng.serviceSystem.units.dao.UnitsInfoDao;

public class UnitsInfoDaoImpl extends BaseDaoHibernate<TUnitsInfo> implements UnitsInfoDao {
	public UnitsInfoDaoImpl(){
		super(TUnitsInfo.class);
	}
}
