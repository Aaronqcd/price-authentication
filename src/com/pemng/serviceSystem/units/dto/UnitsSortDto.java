package com.pemng.serviceSystem.units.dto;
// default package

import com.pemng.serviceSystem.pojo.TUnitsSort;

public class UnitsSortDto extends TUnitsSort {
	
	private Long[] ids;

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
}