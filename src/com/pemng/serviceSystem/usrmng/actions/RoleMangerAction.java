package com.pemng.serviceSystem.usrmng.actions;

import java.io.IOException;
import java.util.List;

import com.pemng.serviceSystem.base.util.CopyBeanUtils;
import com.pemng.serviceSystem.base.util.JsonUtil;
import com.pemng.serviceSystem.base.util.Pager;
import com.pemng.serviceSystem.base.util.PojoUtils;
import com.pemng.serviceSystem.common.Constants;
import com.pemng.serviceSystem.frame.services.FrameService;
import com.pemng.serviceSystem.pojo.TPartMenu;
import com.pemng.serviceSystem.pojo.TRoleInfo;
import com.pemng.serviceSystem.pojo.TUserInfo;
import com.pemng.serviceSystem.usrmng.dto.RoleDto;
import com.pemng.serviceSystem.usrmng.services.FnctnMangerService;
import com.pemng.serviceSystem.usrmng.services.RoleMangerService;

public class RoleMangerAction extends com.pemng.serviceSystem.base.actions.BaseAction{
	private static final long serialVersionUID = -1403775343736547093L;
	
	private RoleMangerService roleMangerService;
	private FnctnMangerService fnctnMangerService;
	private FrameService frameService;
	private RoleDto roleDto;
	
	private TRoleInfo roleInfo;
	private Pager pager=new Pager();
	
	private List<TRoleInfo> roleInfoList;
	private List<TPartMenu> partMenuList;
	
	private List<TUserInfo> unallocatedUserList;
	
	/**
	 * 角色
	 * @throws IOException
	 */
	public void getRoleJson() throws IOException{
		TRoleInfo roleInfo = null;
		if (null != roleDto && null != roleDto.getId()) {
			roleInfo = roleMangerService.getRoleInfo(roleDto.getId());
			roleInfo.setTRoleActns(null);
			roleInfo.setTUserRoleRels(null);
		}
		String json = JsonUtil.object2json(roleInfo);
		writeJsonData(json);
	}
	/**
	 * 角色查询列表
	 * @return
	 */
	public String roleList(){
		roleInfoList=roleMangerService.roleList(pager, roleDto);
		return SUCCESS;
	}
	
	/**
	 * 更新角色权限
	 * @return
	 */
	public String updateActionRole(){
		if (null != roleDto) {
			roleMangerService.saveRoleAction(roleDto);
		}
		return SUCCESS;
	}
	/**
	 * 删除角色
	 * @return
	 */
	public String deleteRole(){
		if (null != roleDto && null != roleDto.getRoleIds()) {
			roleMangerService.deleteRole(roleDto.getRoleIds());
		}
		return SUCCESS;
	}
	/**
	 * 进入创建角色界面
	 * @return
	 */
	public String toAddRole(){
		unallocatedUserList = roleMangerService.getUnallocatedUserList(null);
        return SUCCESS;		
	}
	/**
	 * 创建角色
	 * @return
	 */
	public String addRole(){
		if (null != roleDto) {
			roleDto.setMarkForDel(Constants.MARK_FOR_DELETE_NO);
			roleMangerService.saveRoleInfoAll(roleDto);
		}
		return SUCCESS;
	}
	
	/**
	 * 进入修改角色信息
	 * @return
	 * @throws IOException
	 */
	public String toUpdateRole() throws IOException{
		if (null != roleDto && null != roleDto.getId()) {
			roleInfo=roleMangerService.getRoleInfo(roleDto.getId());
			roleDto = (RoleDto) PojoUtils.convert2Dto(RoleDto.class, roleInfo);
			unallocatedUserList = roleMangerService.getUnallocatedUserList(roleDto.getId());
		}
		return SUCCESS;
	}
	/**
	 * 修改角色信息
	 * @return
	 * @throws IOException
	 */
	public String updateRole() throws IOException{
		if (null != roleDto) {
			roleMangerService.saveRoleInfoAll(roleDto);
		}
		return SUCCESS;
	}
	/**
	 * 修改角色基本信息
	 * @return
	 * @throws IOException
	 */
	public void updateRoleInfo() throws IOException{
		if(null == roleDto || null == roleDto.getId()){
			addFieldErrorToAjaxResponse("result", ERROR);
			addFieldErrorToAjaxResponse("info", "角色信息不完整.");
		}
		if(!hasAjaxErrors()){
			TRoleInfo roleInfo=roleMangerService.getRoleInfo(roleDto.getId());
			CopyBeanUtils.copyPropsWithoutNull(roleInfo, roleDto);
			roleMangerService.saveRoleInfo(roleDto);
			addFieldErrorToAjaxResponse("result", SUCCESS);
			addFieldErrorToAjaxResponse("info", "保存成功.");
		}
		writeAllErrorsToResponse();
	}
	
	public String onlyRole() throws IOException{
		//验证唯一性 
		List roleInfo=roleMangerService.uniqueRole(this.parseStringParameter("rName"),this.parseStringParameter("rId"));
		if(roleInfo.size()!=0){
//			this.addActionErrorToAjaxResponse("1");	
			this.writeJsonToResponse("1");
			}
		return null;
	}
	
	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	public FnctnMangerService getFnctnMangerService() {
		return fnctnMangerService;
	}

	public void setFnctnMangerService(FnctnMangerService fnctnMangerService) {
		this.fnctnMangerService = fnctnMangerService;
	}

	public List<TRoleInfo> getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(List<TRoleInfo> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}

	public RoleDto getRoleDto() {
		return roleDto;
	}

	public void setRoleDto(RoleDto roleDto) {
		this.roleDto = roleDto;
	}

	public RoleMangerService getRoleMangerService() {
		return roleMangerService;
	}

	public void setRoleMangerService(RoleMangerService roleMangerService) {
		this.roleMangerService = roleMangerService;
	}

	public TRoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(TRoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public FrameService getFrameService() {
		return frameService;
	}

	public void setFrameService(FrameService frameService) {
		this.frameService = frameService;
	}

	public List<TPartMenu> getPartMenuList() {
		return partMenuList;
	}

	public void setPartMenuList(List<TPartMenu> partMenuList) {
		this.partMenuList = partMenuList;
	}
	public List<TUserInfo> getUnallocatedUserList() {
		return unallocatedUserList;
	}
	public void setUnallocatedUserList(List<TUserInfo> unallocatedUserList) {
		this.unallocatedUserList = unallocatedUserList;
	}
}
