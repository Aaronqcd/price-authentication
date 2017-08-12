package com.pemng.serviceSystem.mining.dto;

import com.pemng.serviceSystem.pojo.TMiningPriceInfo;

public class MinDto extends TMiningPriceInfo{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3636388904416849933L;

	private Long[] ids;
	
	private String minNames;	//价格库采价信息导入时目录名称多个并且使用“/”符号分割
	
	private String minUsers;	//采价人:价格库采价信息导入时设置

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getMinNames() {
		return minNames;
	}

	public void setMinNames(String minNames) {
		this.minNames = minNames;
	}

	public String getMinUsers() {
		return minUsers;
	}

	public void setMinUsers(String minUsers) {
		this.minUsers = minUsers;
	}
	
	
}
