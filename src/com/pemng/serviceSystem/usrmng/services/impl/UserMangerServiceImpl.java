package com.pemng.serviceSystem.usrmng.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.pemng.common.util.MD5;
import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.base.util.DateUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.pojo.TActnInfo;
import com.pemng.serviceSystem.pojo.TFnctn;
import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.pojo.TUserRoleRel;
import com.pemng.serviceSystem.usrmng.dao.UserInfoDao;
import com.pemng.serviceSystem.usrmng.dao.UserRoleRelDao;
import com.pemng.serviceSystem.usrmng.dto.UserDto;
import com.pemng.serviceSystem.usrmng.services.UserMangerService;

public class UserMangerServiceImpl extends BaseServiceImpl implements UserMangerService {
	private UserInfoDao userInfoDao;
	private UserRoleRelDao userRoleRelDao;

	public Pager userList(Pager page, UserDto userDto) {
		Map<String, Object> map=new HashMap<String, Object>();
		StringBuffer hql=new StringBuffer(" select t from TUserInfo t where 1=1 ");
		if (StringUtils.isNotBlank(userDto.getUsername())) { 
			hql.append(" and t.username like :username ");
			map.put("username", "%"+userDto.getUsername().trim()+"%");
		}
		if (StringUtils.isNotBlank(userDto.getName())) {
			hql.append(" and t.name like :name ");
			map.put("name", "%"+userDto.getName().trim()+"%");
		}
		//创建日期
		if(StringUtils.isNotBlank(userDto.getBeginTime())){
			hql.append(" and t.crtTime >= :beginTime");
			try {
				map.put("beginTime", DateUtil.getDateFromYYYYMMDD(userDto.getBeginTime().trim()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(userDto.getEndTime())){
			hql.append(" and t.crtTime <= :endTime");
			try {
				if (StringUtils.isNotBlank(userDto.getEndTime()) && userDto.getEndTime().length() == 10) {
					map.put("endTime", DateUtil.getDateFromYYYYMMDDHHMMSS(userDto.getEndTime().trim() +" 23:59:59"));
				}else{
					map.put("endTime", DateUtil.getDateFromYYYYMMDDHHMMSS(userDto.getEndTime().trim()));
				}
			} catch (ParseException e) {
			}
		}
		//用户角色
		if (StringUtils.isNotBlank(userDto.getQueryRoleName())) {
			hql.append(" and exists(select ur.id from  TUserRoleRel ur where ur.TUserInfo.id = t.id and ur.TRoleInfo.name like :roleName )");
			map.put("roleName","%"+ userDto.getQueryRoleName().trim()+"%");
		}
	    if(StringUtils.isNotBlank(userDto.getStatus()) && !userDto.getStatus().equals("-1")){
			 hql.append(" and t.status =:status ");
			 map.put("status", userDto.getStatus());
		}
		hql.append(" and t.markForDel=:markForDel ");
		hql.append("order by t.id desc ");
		map.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		page= userInfoDao.pagedQuery(hql.toString(), page, map);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TUserInfo queryUserInfo(Long id) {
		String hql = "select ui from TUserInfo ui where ui.id = :uId and ui.markForDel='V' ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uId", id);
		List<TUserInfo> uiList = (List<TUserInfo>) userInfoDao.findObjectsByHql(hql, params);
		if (uiList.size() <= 0) {
			return null;
		}
		return (TUserInfo) uiList.get(0);
	}
	public int getUserCount(String userName){
		String hql = " from TUserInfo where username = :username";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		return userInfoDao.getRowsCountByHql(hql, params);
	}
	public TUserInfo getUserInfo(String userName){
		String hql = " from TUserInfo where username = :username";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", userName);
		List<TUserInfo> list = (List<TUserInfo>) userInfoDao.findObjectsByHql(hql, params);
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
	/**
	 * 禁用AD中不存在的用户
	 */
	public void disableUser(List<String> excludeUsers){
		String hql2 = " from TUserInfo where status <> :status";
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("status", Constants.USER_STATUS_DISABLE);
		List<TUserInfo> userList = (List<TUserInfo>) userInfoDao.findObjectsByHql(hql2, params2);
//		List<UserInfo> userList = userInfoDao.findObjects();
		for(TUserInfo ui : userList){
			if(!excludeUsers.contains(ui.getUsername())){//不存在的用户
				String hql = "update TUserInfo set status = :status,updTime = :updTime where username = :username";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("username", ui.getUsername());
				params.put("status", Constants.USER_STATUS_DISABLE);
				params.put("updTime", new Date());
				userInfoDao.executeHql(hql, params);
			}
		}
		
//		String hql = "update TUserInfo set status = :status,updTime = :updTime where not username in :excludeUsers";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("excludeUsers", excludeUsers);
//		params.put("status", Constants.USER_STATUS_DISABLE);
//		params.put("updTime", new Date());
//		userInfoDao.executeHql(hql, params);
	}
	//根据id查询用户信息,信息包含用户所属部门的信息和上级领导信息
	@Override
	public TUserInfo queryUser(Long id) {
		return (TUserInfo) userInfoDao.getObject(id);
	}
	// 逻辑删除
	public void deleteUser(UserDto userDto) {
		List userList=new ArrayList();
		TUserInfo userInfo=new TUserInfo();
		for(long roleId:userDto.getIds()){
			userInfo=queryUser(roleId);
			userInfo.setMarkForDel(Constants.MARK_FOR_DELETE_YES);
			userList.add(userInfo);
		}  
		userInfoDao.saveObjectList(userList);
	}
	
	@Override
	public void saveUser(UserDto saveDto) {
		TUserInfo userInfo = null;
		if (null != saveDto.getId() && saveDto.getId() >0) {
			userInfo = this.userInfoDao.getObject(saveDto.getId());
		}else{
			userInfo = new TUserInfo();
			userInfo.setLoginSum(0); //默认登陆次数0
		}
		//UserInfo TUserInfo = this.userInfoDao.getObject(saveDto.getId());
		//设置页面传过来的值
		if (StringUtils.isNotBlank(saveDto.getPassword()) && !saveDto.getPassword().equals(userInfo.getPassword())) {
			MD5 md = new MD5();
			userInfo.setPassword(md.getMD5ofStr(saveDto.getPassword()));
		}
		userInfo.setUsername(saveDto.getUsername());
		userInfo.setName(saveDto.getName());
		userInfo.setStatus(saveDto.getStatus());
		userInfo.setTel(saveDto.getTel());
		userInfo.setUserDesc(saveDto.getUserDesc());
		if (StringUtils.isBlank(saveDto.getStatus()) || saveDto.getStatus().equals("-1")) {
			userInfo.setStatus(null);
		}
		//角色修改/新建
		if (StringUtils.isBlank(saveDto.getFlag()) || !saveDto.getFlag().equals("1")) {
			//删除角色
			if (null != saveDto.getId()) {
				String hql = "delete TUserRoleRel ur where ur.TUserInfo.id = :uId";
				Map<String, Object> params =new HashMap<String, Object>();
				params.put("uId", saveDto.getId());
				this.userRoleRelDao.executeHql(hql, params);
			}
			if (saveDto.getRightValues() != null) {
				Set<TUserRoleRel> userRoleRels = new HashSet<TUserRoleRel>();
				for (Long roleId : saveDto.getRightValues()) {
					// 做插入user role 中间表操作
					TUserRoleRel userRole = new TUserRoleRel();
					TRoleInfo roleInfo = new TRoleInfo();
					roleInfo.setId(roleId);
					userRole.setTRoleInfo(roleInfo);
					userRole.setTUserInfo(userInfo);
					userRoleRels.add(userRole);
				}
				this.userRoleRelDao.saveObjectList(Arrays.asList(userRoleRels.toArray()));
				userInfo.setTUserRoleRels(userRoleRels);
			}
		}
		if (null != saveDto.getId() && saveDto.getId() > 0) {
			this.userInfoDao.saveObject(userInfo);
		}else{
			this.userInfoDao.saveTransientObject(userInfo);
		}
	}

	public int checkUser(String username, Long uId) {
		StringBuffer hql=new StringBuffer("select count(ui.id) from TUserInfo ui where ui.username =:username and ui.markForDel = 'V' "); 
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username",username);
		if (null != uId)  {
			params.put("id", uId);
			hql.append(" and ui.id != :id");
		} 
		return this.userInfoDao.getRowsCountByHql(hql.toString(), params);
	}

	public void delUserRole(Long id) {
		userRoleRelDao.removeObject(id);  
	}

	public List queryUserRoleRel(Long id) {
		Map params = new HashMap();
		params.put("uId", id);
		String hql = " from UserRoleRel ui where ui.userInfo.id = :uId ";
		return userRoleRelDao.findObjectsByHql(hql, params);
	}

	// 接口调用 返回该用户所有的角色
	public List findUserRoleById(Long id) {
		String hql = "from TUserInfo ui,TUserRoleRel ur,TRoleInfo ri " + "where ui.id = ur.userId " + "and ur.roleId = ri.id" + "and ui.id =:user_id " + "and ui.markForDel = '"
				+ Constants.MARK_FOR_DELETE_NO + "'" + "and ur.markForDel = '" + Constants.MARK_FOR_DELETE_NO + "'" + "and ri.markForDel = '" + Constants.MARK_FOR_DELETE_NO + "'";
		Map params = new HashMap();
		params.put("user_id", id);
		return userInfoDao.findObjectsByHql(hql, params);
	}

	// 接口调用 返回用户所有的资源
	public List findUserResourceById(Long id) {
		String hql=" from TUserInfo ui,TUserRoleRel ur,TRoleInfo ri,TRoleFnctn rf,TFnctn f,";
		Map params = new HashMap();
		params.put("user_id", id);
		return userInfoDao.findObjectsByHql(hql, params);
	}

	public UserRoleRelDao getUserRoleRelDao() {
		return userRoleRelDao;
	}

	public void setUserRoleRelDao(UserRoleRelDao userRoleRelDao) {
		this.userRoleRelDao = userRoleRelDao;
	}

	@Override
	public TUserInfo getObjectByUserId(Long userId) {
		TUserInfo user = (TUserInfo) userInfoDao.getObject(userId);
		return user;
	}

	@Override
	public List<TRoleInfo> getRoleListByUserId(Long userId) {
		String hql = "select ri from TUserRoleRel ur,TRoleInfo ri  where ur.roleId = ri.id  and ur.userId = :userId";
		Map params = new HashMap();
		params.put("userId", userId);
		return (List<TRoleInfo>) userInfoDao.findObjectsByHql(hql, params);
	}

	@Override
	public List userList1(Pager pager, UserDto userDto) {
		String hql = "from TUserInfo u where 1=1";
	List values = new ArrayList();
	if (userDto != null) {
		if (StringUtils.isNotBlank(userDto.getName())) {
			hql += " and u.username like '%"
					+ userDto.getName() + "%'";
		}
	}
//	hql += " order by u.id desc";
	return userInfoDao.pagedQuery(hql, pager,
			values.toArray(new Object[values.size()])).getData();
	}
	
	private void getFnctnActnList(List fnctnList,List actnInfoList,Long userId){
		TUserInfo TUserInfo = (TUserInfo) userInfoDao.getObject(userId);
	}

	@Override
	public List<TActnInfo> getActnListByUserId(Long userId) {
		List fnctnList = new ArrayList<TFnctn>();
		List actnInfoList  = new ArrayList<TActnInfo>();
		getFnctnActnList(fnctnList, actnInfoList, userId);
		return actnInfoList;
	}

	@Override
	public List<TFnctn> getFnctnListByUserId(Long userId) {
		List fnctnList = new ArrayList<TFnctn>();
		List actnInfoList  = new ArrayList<TActnInfo>();
		getFnctnActnList(fnctnList, actnInfoList, userId);
		return fnctnList;
	}

	@Override
	public Pager findUserByName(Pager page ,String name) {
//		String hql = "from TUserInfo ui where ui.markForDel= '" + Constants.MARK_FOR_DELETE_NO + "' and ui.name='"+ name + "' " ;
		Map params=new HashMap();
		params.put("username", name);
		StringBuffer hql=new StringBuffer(" from TUserInfo ui where ui.username:=username ");
//		hql.append(" order by ui.id desc"); 
		page=userInfoDao.pagedQuery(hql.toString(), page, params);
		return  page;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}
	

 
}