package com.pemng.serviceSystem.usrmng.services;

import java.util.List;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.usrmng.dto.RoleDto;

public interface RoleMangerService extends BaseService{
	
	public TRoleInfo saveRoleInfoAll(RoleDto roleDto);
	public TRoleInfo saveRoleInfo(RoleDto roleDto);
	
	public void deleteRole(Long[] roleIds);

	public List<TRoleInfo> roleList(Pager page,RoleDto roleDto);
	
	public TRoleInfo getRoleInfo(Long  id);
	
	// 用户选择角色	
	public List queryOfExisting(Long id);
	
	public List queryOfUncheck(Long id);
	
	public List uniqueRole(String roleName,String id);
	
	/**
	 * 保存角色权限
	 * @param roleDto
	 */
	public void saveRoleAction(RoleDto roleDto);
	/**
	 * 获得没有指定角色的用户列表
	 * @param roleId
	 * @return
	 */
	public List<TUserInfo> getUnallocatedUserList(Long roleId);

	

}
