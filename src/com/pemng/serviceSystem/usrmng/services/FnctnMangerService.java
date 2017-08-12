package com.pemng.serviceSystem.usrmng.services;

import java.util.List;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.pojo.TFnctn;

public interface FnctnMangerService extends BaseService{
	
	

	
	public List fnctnList();
	//进入修改页面方法
	
	public TFnctn queryFnctn(long id);
	
}
