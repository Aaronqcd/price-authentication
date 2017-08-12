package com.pemng.serviceSystem.cms.dto;

import com.pemng.serviceSystem.pojo.TAprislArtclsInfo;

public class AprislDto extends TAprislArtclsInfo {

	private static final long serialVersionUID = -33075485106311336L;

	private String[] postArr;
	private String beginTime1;
	private String endTime1;
	private String beginTime2;
	private String endTime2;
	private Double aprislPrc1;
	private Double aprislPrc2;
	
	public Double getAprislPrc1() {
		return aprislPrc1;
	}

	public void setAprislPrc1(Double aprislPrc1) {
		this.aprislPrc1 = aprislPrc1;
	}

	public Double getAprislPrc2() {
		return aprislPrc2;
	}

	public void setAprislPrc2(Double aprislPrc2) {
		this.aprislPrc2 = aprislPrc2;
	}

	public String[] getPostArr() {
		return postArr;
	}

	public void setPostArr(String[] postArr) {
		this.postArr = postArr;
	}
	public String getBeginTime1() {
		return beginTime1;
	}

	public void setBeginTime1(String beginTime1) {
		this.beginTime1 = beginTime1;
	}

	public String getEndTime1() {
		return endTime1;
	}

	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}

	public String getBeginTime2() {
		return beginTime2;
	}

	public void setBeginTime2(String beginTime2) {
		this.beginTime2 = beginTime2;
	}

	public String getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(String endTime2) {
		this.endTime2 = endTime2;
	}	
}
