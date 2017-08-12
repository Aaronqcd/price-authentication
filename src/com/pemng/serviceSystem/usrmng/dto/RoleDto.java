package com.pemng.serviceSystem.usrmng.dto;


import com.pemng.serviceSystem.pojo.TRoleInfo;

public class RoleDto extends TRoleInfo {
	private Long[] roleIds;
    private Long[] actionIds;
    private Long[] userIds;
    
	public Long[] getActionIds() {
		return actionIds;
	}

	public void setActionIds(Long[] actionIds) {
		this.actionIds = actionIds;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}
	
}
