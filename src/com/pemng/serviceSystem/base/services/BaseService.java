package com.pemng.serviceSystem.base.services;

import org.apache.commons.logging.Log;

import com.pemng.serviceSystem.base.exception.BaseSysException;
import com.pemng.serviceSystem.base.exception.BaseUnknownException;
import com.pemng.serviceSystem.common.WebActions;
import com.pemng.serviceSystem.common.WebUser;


/**
 * 基类接口。可调用Dao接口中的方法实现。<br>
 * 但应将数据层的异常都包装为应用中定义的系统异常
 * 

 * @see BaseSysException
 * @see BaseUnknownException
 */
public interface BaseService {
	public abstract Log getLog();

	
	public WebUser getCurrentWebUser();
	
	public WebActions getCurrentActions();
	
//	public WebOrg getCurrentOrg();
	
}
