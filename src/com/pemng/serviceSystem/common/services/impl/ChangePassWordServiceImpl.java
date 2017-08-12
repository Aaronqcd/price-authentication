package com.pemng.serviceSystem.common.services.impl;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.common.dao.ChangePassWordDao;
import com.pemng.serviceSystem.common.services.ChangePassWordService;

public class ChangePassWordServiceImpl extends BaseServiceImpl implements ChangePassWordService {

	private ChangePassWordDao changePassWordDao;
	
	
	
	
	/**
	 * 用户修改密码
	 */
	@Override
	public void changePassWord(String password,Long id) {
		changePassWordDao.changePassWord(password, id);
	}
	/**
	 * 判断用户的原密码是否正确
	 */
	public Boolean checkPassWord(String password,Long id) {
		return changePassWordDao.checkPassWord(password, id);
	}




	public ChangePassWordDao getChangePassWordDao() {
		return changePassWordDao;
	}

	public void setChangePassWordDao(ChangePassWordDao changePassWordDao) {
		this.changePassWordDao = changePassWordDao;
	}
	
	
	
	
	
}
