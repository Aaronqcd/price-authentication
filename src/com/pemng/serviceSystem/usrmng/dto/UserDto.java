package com.pemng.serviceSystem.usrmng.dto;

import com.pemng.serviceSystem.pojo.TUserInfo;

public class UserDto extends TUserInfo {
	private static final long serialVersionUID = 1L;

	private String isSearchOpen = "0";//搜索条件是否为打开，默认为收缩状态
	private Long[] ids;
	private String beginTime;
	private String endTime;
	private String userDesc;
	private String top; 
	private String telExtensio;
	private Long leaderId;
	private Long[] leftValues;
	private Long[] rightValues;
	private String uname;//上级领导
	private String roleName; 
    private String queryRoleName;
    private String flag;//是否是修改基本信息
    private String crtName;	//创建人
    
    public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getQueryRoleName() {
		return queryRoleName;
	}

	public void setQueryRoleName(String queryRoleName) {
		this.queryRoleName = queryRoleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Long[] getLeftValues() {
		return leftValues;
	}


	public void setLeftValues(Long[] leftValues) {
		this.leftValues = leftValues;
	}

	public Long[] getRightValues() {
		return rightValues;
	}

	public void setRightValues(Long[] rightValues) {
		this.rightValues = rightValues;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) { 
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getTelExtensio() {
		return telExtensio;
	}

	public void setTelExtensio(String telExtensio) {
		this.telExtensio = telExtensio;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public String getIsSearchOpen() {
		return isSearchOpen;
	}

	public void setIsSearchOpen(String isSearchOpen) {
		this.isSearchOpen = isSearchOpen;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCrtName() {
		return crtName;
	}

	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}

}
