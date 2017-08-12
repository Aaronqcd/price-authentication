package com.pemng.serviceSystem.usrmng.services.impl;

import java.util.List;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.pojo.TFnctn;
import com.pemng.serviceSystem.usrmng.dao.FnctnDao;
import com.pemng.serviceSystem.usrmng.services.FnctnMangerService;

public class FnctnMangerServiceImpl extends BaseServiceImpl implements FnctnMangerService{
   private FnctnDao fnctnDao;
	public FnctnDao getFnctnDao() {
	return fnctnDao;
	}
public void setFnctnDao(FnctnDao fnctnDao) {
	this.fnctnDao = fnctnDao;
	}
	
	public List fnctnList() {
		// TODO Auto-generated method stub
		return fnctnDao.findObjects();
	}
	public TFnctn queryFnctn(long id){
		return (TFnctn) fnctnDao.getObject(id);
	}
}
