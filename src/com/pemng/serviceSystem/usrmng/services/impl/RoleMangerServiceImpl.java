package com.pemng.serviceSystem.usrmng.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.pojo.TActnInfo;
import com.pemng.serviceSystem.pojo.TRoleActn;
import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.pojo.TUserRoleRel;
import com.pemng.serviceSystem.usrmng.dao.RoleInfoDao;
import com.pemng.serviceSystem.usrmng.dto.RoleDto;
import com.pemng.serviceSystem.usrmng.services.RoleMangerService;

public class RoleMangerServiceImpl extends BaseServiceImpl implements RoleMangerService{

	private RoleInfoDao roleInfoDao;
	
	public List<TRoleInfo> roleList(Pager page,RoleDto roleDto) {
		StringBuilder hql = new StringBuilder(" from TRoleInfo t ")
		.append("left join fetch t.TRoleActns ra left join fetch ra.TActnInfo ")
		.append("where 1=1 and t.markForDel <> :markForDel");
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("markForDel", Constants.MARK_FOR_DELETE_YES);
		if(roleDto != null ){
			if(StringUtils.isNotEmpty(roleDto.getName())){
				hql.append(" and t.name like :name");
				params.put("name","%"+roleDto.getName().trim()+"%");
			}
			if(StringUtils.isNotEmpty(roleDto.getRoleDesc()) ){
				hql.append(" and t.roleDesc like :roleDesc");
				params.put("roleDesc","%"+roleDto.getRoleDesc().trim()+"%");
			}
		}
		hql.append(" order by t.id desc");
		page = roleInfoDao.pagedQuery(hql.toString(), page, params, "t.id");
		return  page.getData();
	}

	public List<TUserInfo> getUnallocatedUserList(Long roleId){
		Map<String,Object> params =new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("select t1 from TUserInfo t1 where t1.markForDel = :markForDel");
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		if(roleId!=null){
			hql.append(" and t1.id not in (select t2.userInfo from TUserRoleRel t2 where t2.TRoleInfo.id = :roleId)");
			params.put("roleId", roleId);
		}
		return roleInfoDao.findObjectsByHql(hql.toString(), params);
	}
	
	public TRoleInfo getRoleInfo(Long id) {
		return (TRoleInfo) roleInfoDao.getObject(id);
	}
	
	
	public List queryOfExisting(Long userId) {
		StringBuffer hql=new StringBuffer("select distinct ri from TUserRoleRel ur,TRoleInfo ri");
		hql.append(" where 1=1  and ri.id = ur.TRoleInfo.id  and ri.markForDel = :markForDel ");	 
		Map params =new HashMap();
		if(userId!=null){
		hql.append(" and ur.TUserInfo.id = :uId ");
		params.put("uId", userId);
		}
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		return  roleInfoDao.findObjectsByHql(hql.toString(), params);
	}

	
	public List queryOfUncheck(Long userId) {
		String hql=" from TRoleInfo t where t.markForDel = :markForDel ";
		Map params =new HashMap();
		params.put("markForDel", Constants.MARK_FOR_DELETE_NO);
		if(userId != null){
			hql += " and t.id not in (select ts.TRoleInfo from TUserRoleRel ts where ts.TUserInfo.id =:uId) ";
			params.put("uId", userId);
		}
		hql += " order by t.id";
		return  roleInfoDao.findObjectsByHql(hql, params);
	}
	
	
	public List uniqueRole(String roleName,String id){
		// TODO Auto-generated method stub
		String hql=" from TRoleInfo ui where ui.nm =:roleName and ui.markForDel <> :markForDel";
		Map params =new HashMap();
		params.put("markForDel", Constants.MARK_FOR_DELETE_YES);
		params.put("roleName", roleName);
		if(StringUtils.isNotEmpty(id)){
			hql=hql+" and ui.id <> '"+Long.parseLong(id)+"'";
		}
		return  roleInfoDao.findObjectsByHql(hql, params);
	}

	public void delRoleUser(Long roleId){
		String sql = "delete T_USER_ROLE_REL where ROLE_ID = "+roleId;
		roleInfoDao.executeSql(sql);
	}
	public TRoleInfo saveRoleInfoAll(RoleDto roleDto) {
		TRoleInfo roleInfo = new TRoleInfo();
		roleInfo.setId(roleDto.getId());
		roleInfo.setName(roleDto.getName());
		roleInfo.setRoleDesc(roleDto.getRoleDesc());
		roleInfo.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
		roleInfo = (TRoleInfo)roleInfoDao.saveObject(roleInfo);
		if(roleDto.getId()!=null){
			delRoleUser(roleDto.getId());
		}
		if(roleDto.getUserIds()!=null){
			for(Long userId : roleDto.getUserIds()){
				TUserRoleRel userRoleRel = new TUserRoleRel();
				TUserInfo userInfo = new TUserInfo();
				userInfo.setId(userId);
				userRoleRel.setTUserInfo(userInfo);
				userRoleRel.setTRoleInfo(roleInfo);
				roleInfoDao.saveObject(userRoleRel);
			}
		}
		return roleInfo;
	}
	public TRoleInfo saveRoleInfo(RoleDto roleDto) {
		TRoleInfo roleInfo = getRoleInfo(roleDto.getId());
		roleInfo.setName(roleDto.getName());
		roleInfo.setRoleDesc(roleDto.getRoleDesc());
		return (TRoleInfo)roleInfoDao.saveObject(roleInfo);
	}
	
	public void deleteRole(Long[] roleIds) {
		for(Long roleId:roleIds){
			TRoleInfo roleInfo = getRoleInfo(roleId);
			roleInfo.setMarkForDel(Constants.MARK_FOR_DELETE_YES);//删除标记
			roleInfoDao.saveObject(roleInfo);
		}
	}
	
	public void delRoleAction(Long roleId){
		String hql = "delete TRoleActn where TRoleInfo.id = :roleId";
		Map params =new HashMap();
		params.put("roleId", roleId);
		roleInfoDao.executeHql(hql, params);
	}
	
	public void saveRoleAction(RoleDto roleDto){
		//删除角色中现有权限
		if (null != roleDto.getId()) {
			delRoleAction(roleDto.getId());
		}
		//保存角色权限
		Long[] actionIds = roleDto.getActionIds();
		if(null != actionIds){
			List<TRoleActn> roleActnList = new ArrayList<TRoleActn>();
			for(Long actionId : actionIds){
				TRoleActn roleActn = new TRoleActn();
				TRoleInfo roleInfo = new TRoleInfo();
				TActnInfo actnInfo = new TActnInfo();
				roleInfo.setId(roleDto.getId());
				actnInfo.setId(actionId);
				roleActn.setTActnInfo(actnInfo);
				roleActn.setTRoleInfo(roleInfo);
				roleActnList.add(roleActn);
			}
			this.roleInfoDao.saveObjectList(roleActnList);
		}
	}
	public RoleInfoDao getRoleInfoDao() {
		return roleInfoDao;
	}

	public void setRoleInfoDao(RoleInfoDao roleInfoDao) {
		this.roleInfoDao = roleInfoDao;
	}
}
