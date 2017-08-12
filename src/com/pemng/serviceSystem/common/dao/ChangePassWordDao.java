package com.pemng.serviceSystem.common.dao;

import com.pemng.serviceSystem.base.dao.Dao;
import com.pemng.serviceSystem.pojo.TUserInfo;

public interface ChangePassWordDao extends Dao<TUserInfo> {

	/**
	 * 用户修改密码
	 */
	public void changePassWord(String password,Long id);
	
	/**
	 * 判断用户的原密码是否正确
	 */
	public Boolean checkPassWord(String password,Long id);
}
