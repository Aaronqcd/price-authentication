package com.pemng.serviceSystem.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.common.util.MD5;
import com.pemng.serviceSystem.base.dao.BaseDaoHibernate;
import com.pemng.serviceSystem.common.dao.ChangePassWordDao;
import com.pemng.serviceSystem.pojo.TUserInfo;

public class ChangePassWordDaoImpl extends BaseDaoHibernate<TUserInfo> implements ChangePassWordDao {

	
	public ChangePassWordDaoImpl() {
		super(TUserInfo.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 用户修改密码
	 */
	@Override
	public void changePassWord(String password,Long id) {
		String pwd = new MD5().getMD5ofStr(password.trim());
		Map<String,Object> params = new HashMap<String, Object>();
		String hql="update TUserInfo set password=:password where id=:id";
		params.put("password", pwd.trim());
		params.put("id", id);
		this.executeHql(hql, params);
	}
	/**
	 * 判断用户的原密码是否正确
	 */
	public Boolean checkPassWord(String password,Long id) {
		String pwd = new MD5().getMD5ofStr(password.trim());
		Map<String,Object> params = new HashMap<String, Object>();
		String hql="from TUserInfo where id=:id and password=:password";
		params.put("id", id);
		params.put("password", pwd);
		List<TUserInfo> list = (List<TUserInfo>)this.findObjectsByHql(hql, params);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	

}
