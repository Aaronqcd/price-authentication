package com.pemng.serviceSystem.usrmng.services;

import java.util.List;

import com.pemng.serviceSystem.base.services.BaseService;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.pojo.TActnInfo;
import com.pemng.serviceSystem.pojo.TFnctn;
import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.usrmng.dto.UserDto;

public interface UserMangerService extends BaseService{
	
	/**
	 * 根据id查询用户信息,不包含部门和负责人信息
	 * @param id
	 * @return
	 */
	public TUserInfo queryUser(Long  id);
	
	/**
	 * 根据id查询用户信息,信息包含用户所属部门的信息和上级领导信息
	 * @param id
	 * @return
	 */
	public TUserInfo queryUserInfo(Long id);
	
	/**
	 * 修改用户信息
	 * @param userDto
	 */
	public void saveUser(UserDto userDto);  
	
	public void deleteUser(UserDto userDto); 
	

	public Pager userList(Pager page,UserDto userDto);
	
	/**
	 * 验证用户唯一性
	 * @param st
	 * @param id
	 * @return
	 */
	public int checkUser(String st, Long id);
	
	public List queryUserRoleRel(Long id);
	
	public void delUserRole(Long id);
	
	//
	public List findUserRoleById(Long id);
	
	
	public List findUserResourceById(Long id);
	
	/**
	 * 根据用户id返回Object（维修站，驻外中心，旧件中心）
	 * @param userId
	 * @return
	 */
	public TUserInfo getObjectByUserId(Long userId);
	
	/**
	 * 根据用户id返回role list
	 * @param userId
	 * @return
	 */
	public List<TRoleInfo> getRoleListByUserId(Long userId);

	public List userList1(Pager pager, UserDto userDto);
	
	/**
	 * 根据用户id返回action list
	 * @param userId
	 * @return
	 */
	public List<TActnInfo> getActnListByUserId(Long userId);
	
	/**
	 * 根据用户id返回function list
	 * @param userId
	 * @return
	 */
	public List<TFnctn> getFnctnListByUserId(Long userId);

	public Pager findUserByName(Pager page, String name);

	int getUserCount(String userName);

	void disableUser(List<String> excludeUsers);

	TUserInfo getUserInfo(String userName);
}
