package com.pemng.serviceSystem.usrmng.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.base.util.StringUtil;
import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.usrmng.dto.RoleDto;
import com.pemng.serviceSystem.usrmng.dto.UserDto;
import com.pemng.serviceSystem.usrmng.services.RoleMangerService;
import com.pemng.serviceSystem.usrmng.services.UserMangerService;

public class UserMangerAction extends
		com.pemng.serviceSystem.base.actions.BaseAction {

	private UserMangerService userMangerService;
	private RoleMangerService roleMangerService;
	private Pager pager = new Pager();
	private UserDto userDto = new UserDto();
	private UserDto saveDto = new UserDto();
	private RoleDto roleDto = new RoleDto();

	private List dtoList;
	private List<TRoleInfo> roleInfoList = new ArrayList<TRoleInfo>();
	private List<TRoleInfo> roleUncheckList = new ArrayList<TRoleInfo>();

	/**
	 * 查询用户列表
	 * @return
	 */
	public String userList() {
		if (null != userDto) {
			pager = userMangerService.userList(pager, userDto);
		}
		return SUCCESS;
	}
	
	public void onlyUser() throws IOException {
		// 验证唯一性
		if (null == saveDto || StringUtil.isEmpty(saveDto.getUsername())) {
			this.writeJsonToResponse("0");
			return;
		}
		int uCount = userMangerService.checkUser(saveDto.getUsername(), saveDto.getId());
		this.writeJsonToResponse(uCount > 0 ? "1":"0");
	}

	/**
	 * 查询上级领导/工程师信息
	 * @return
	 */
	public String popuUser() {
		if (null != userDto) {
			pager = userMangerService.userList(pager, userDto);
		}
		return SUCCESS;
	}

	public String selectName(){
		if (null != userDto) {
			pager = userMangerService.userList(pager, userDto);
		}
		return SUCCESS;
	}
	/**
	 * 修改或保存用户信息
	 * @return
	 * @throws IOException
	 */
	public String saveUser() throws IOException {
		//修改用户基本资料,包含部门和上级领导,但不包含角色
		if (null != saveDto) {
			this.userMangerService.saveUser(saveDto);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除用户--逻辑删除
	 * @return
	 */
	public String deleteUser() {
		if (null != userDto && null != userDto.getIds()) {
			userMangerService.deleteUser(userDto);
		}
		return SUCCESS;
	}


	/**
	 * 跳转到创建用户界面
	 * @return
	 */
	public String toCreateUser() {
		//查询所有角色操作
		roleUncheckList = roleMangerService.queryOfUncheck(null);
		return SUCCESS;
	}
	
	/**
	 * 查询用户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryUser() {
		if (null != saveDto && null !=saveDto.getId()) {
			TUserInfo userInfo = this.userMangerService.queryUserInfo(saveDto.getId());
			saveDto = (UserDto) PojoUtils.convert2Dto(UserDto.class, userInfo);
			if (null != userInfo) {
				TUserInfo crtUser = userMangerService.getObjectByUserId(saveDto.getId());
				if (null != crtUser) {
					saveDto.setCrtName(crtUser.getName());
				}
			}
			// 已有角色
			roleInfoList = roleMangerService.queryOfExisting(saveDto.getId());
			// 未选角色
			roleUncheckList = roleMangerService.queryOfUncheck(saveDto.getId());
		}
		return SUCCESS;
	}

	public UserMangerService getUserMangerService() {
		return userMangerService;
	}

	public void setUserMangerService(UserMangerService userMangerService) {
		this.userMangerService = userMangerService;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public RoleMangerService getRoleMangerService() {
		return roleMangerService;
	}

	public void setRoleMangerService(RoleMangerService roleMangerService) {
		this.roleMangerService = roleMangerService;
	}

	public RoleDto getRoleDto() {
		return roleDto;
	}

	public void setRoleDto(RoleDto roleDto) {
		this.roleDto = roleDto;
	}

	public List getDtoList() {
		return dtoList;
	}

	public void setDtoList(List dtoList) {
		this.dtoList = dtoList;
	}

	public UserDto getSaveDto() {
		return saveDto;
	}

	public void setSaveDto(UserDto saveDto) {
		this.saveDto = saveDto;
	}

	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public List<TRoleInfo> getRoleUncheckList() {
		return roleUncheckList;
	}

	public void setRoleUncheckList(List<TRoleInfo> roleUncheckList) {
		this.roleUncheckList = roleUncheckList;
	}

	public List<TRoleInfo> getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(List<TRoleInfo> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}
	
}