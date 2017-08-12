package com.pemng.serviceSystem.units.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.pojo.TUnitsSort;
import com.pemng.serviceSystem.units.dao.UnitsSortDao;
import com.pemng.serviceSystem.units.dto.UnitsSortDto;



public class UnitsSortDaoImpl extends BaseDaoHibernate<TUnitsSort> implements UnitsSortDao {

	public UnitsSortDaoImpl()
	{
		super(TUnitsSort.class);
	}



}
