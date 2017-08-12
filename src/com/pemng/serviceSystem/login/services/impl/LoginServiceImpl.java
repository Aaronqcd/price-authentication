package com.pemng.serviceSystem.login.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pemng.common.util.MD5;
import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.login.services.LoginService;
import com.pemng.serviceSystem.pojo.TActnInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.usrmng.dao.UserInfoDao;

public class LoginServiceImpl extends BaseServiceImpl implements
   LoginService {
	private UserInfoDao  userInfoDao;

	
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	//判断登陆
	@Override
	public TUserInfo login(String username,String password){
		String hql = "select t from TUserInfo t where t.username = :username and t.password = :password and t.markForDel = :markForDel and t.status = :status";
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("username", username);
		params.put("status", Constants.USER_STATUS_VALID);
		// 密码加密
		MD5 md5 = new MD5();
		params.put("password", md5.getMD5ofStr(password));
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		List<TUserInfo> list = (List<TUserInfo>) userInfoDao.findObjectsByHql(hql, params);
		if(list.size()!=0)
			return list.get(0);
		return null;
	}
	
	//判断登陆
	@Override
	public TUserInfo login(String username){
		String hql = "select t from TUserInfo t where t.username = :username and t.markForDel = :markForDel and t.status = :status";
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("username", username);
		params.put("status", Constants.USER_STATUS_VALID);
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		List<TUserInfo> list = (List<TUserInfo>) userInfoDao.findObjectsByHql(hql, params);
		if(list.size()!=0)
			return list.get(0);
		return null;
	}
	
	@Override
	public List<TActnInfo> getActions(Long userId){
		String hql = "select distinct t3 from TUserRoleRel t1,TRoleActn t2,TActnInfo t3,TRoleInfo t4 where t4.id = t1.TRoleInfo.id and t1.TRoleInfo.id = t2.TRoleInfo.id and t2.TActnInfo.id = t3.id and t1.TUserInfo.id = :userId and t4.markForDel = :markForDel";
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		List<TActnInfo> list = (List<TActnInfo>) userInfoDao.findObjectsByHql(hql, params);
		return list;
	}

	@Override
	public void upLoginInfo(Long userId) {
		TUserInfo ui = this.userInfoDao.getObject(userId);
		ui.setLoginSum(ui.getLoginSum()==null?0:ui.getLoginSum() + 1);
		ui.setLastDate(new Date());
		this.userInfoDao.saveObjectPure(ui);
	}

}
