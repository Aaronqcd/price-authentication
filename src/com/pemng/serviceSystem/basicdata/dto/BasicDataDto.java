package com.pemng.serviceSystem.basicdata.dto;

import com.pemng.serviceSystem.pojo.TBasicDataCnt;

public class BasicDataDto extends TBasicDataCnt {
	private static final long serialVersionUID = -1388662663757461885L;
	private String ids;
	private String delIds;
	private String code;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
}
