package com.pemng.serviceSystem.common;

import java.io.Serializable;
import java.util.List;

import javax.management.relation.RoleInfo;

import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;

/**
 * 用户基本信息类
 */
public class WebUser extends TUserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6672823150140310178L;
	private List<TRoleInfo> roles;
	/**
	 * 获得用户是否存在该角色
	 * @param roleCode 角色编号
	 * @return
	 */
	public RoleInfo getRole(String roleCode){
		if(roles!=null && roleCode!=null){
//			for(RoleInfo role : roles){
//				if(role!=null&&role.getRoleCode()!=null)
//				if(role.getRoleCode().equals(roleCode)){
//					return role;
//				}
//			}
		}
		return null;
	}
	public void setRoles(List<TRoleInfo> roles) {
		this.roles = roles;
	}
	
	
}
