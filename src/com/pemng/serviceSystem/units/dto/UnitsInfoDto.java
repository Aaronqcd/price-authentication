package com.pemng.serviceSystem.units.dto;

import com.pemng.serviceSystem.pojo.TUnitsInfo;

// default package
public class UnitsInfoDto extends TUnitsInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -266591303778370555L;
	private Long sortId;
	private Long[] ids;
	

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}


}