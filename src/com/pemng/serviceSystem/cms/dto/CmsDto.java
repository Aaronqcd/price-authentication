package com.pemng.serviceSystem.cms.dto;

import com.pemng.serviceSystem.pojo.TCommission;

public class CmsDto extends TCommission{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3636388904416849933L;

	private Long[] ids;
	private String beginTime;
	private String endTime;
	private String beginTime1;
	private String endTime1;
	private Long unitId1; // 一级单位类型ID
	private Long unitId2; // 二级级单位类型ID
	private Long unitId3; // 三级单位ID
	private String aprislArtclsNm; // 标的物名称
	private String acptUsrNm;	//受理员
	private int cmsType;		//委托书类型
	private int flag;			//委托书管理菜单标志，1鉴定认定管理，2，复核裁定管理，3，审核管理，4，历史委托书
	private String suspktOfens1;
	private String suspktOfens2;
	private String recUsrIds;
	private String auditFlag;	//1:初审 2复审 3终审
	private Long tmpCmsId;	
	private boolean otherCase;
	private String userNames;	//勘验人员
	private int overDueNum;		//剩余天数
	private boolean downloadFlag;//下载标志

	public Long getUnitId1() {
		return unitId1;
	}

	public void setUnitId1(Long unitId1) {
		this.unitId1 = unitId1;
	}

	public Long getUnitId2() {
		return unitId2;
	}

	public void setUnitId2(Long unitId2) {
		this.unitId2 = unitId2;
	}

	public Long getUnitId3() {
		return unitId3;
	}

	public void setUnitId3(Long unitId3) {
		this.unitId3 = unitId3;
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

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getAprislArtclsNm() {
		return aprislArtclsNm;
	}

	public void setAprislArtclsNm(String aprislArtclsNm) {
		this.aprislArtclsNm = aprislArtclsNm;
	}

	public String getAcptUsrNm() {
		return acptUsrNm;
	}

	public void setAcptUsrNm(String acptUsrNm) {
		this.acptUsrNm = acptUsrNm;
	}

	public int getCmsType() {
		return cmsType;
	}

	public void setCmsType(int cmsType) {
		this.cmsType = cmsType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getSuspktOfens1() {
		return suspktOfens1;
	}

	public void setSuspktOfens1(String suspktOfens1) {
		this.suspktOfens1 = suspktOfens1;
	}

	public String getSuspktOfens2() {
		return suspktOfens2;
	}

	public void setSuspktOfens2(String suspktOfens2) {
		this.suspktOfens2 = suspktOfens2;
	}

	public String getRecUsrIds() {
		return recUsrIds;
	}

	public void setRecUsrIds(String recUsrIds) {
		this.recUsrIds = recUsrIds;
	}
	
	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public Long getTmpCmsId() {
		return tmpCmsId;
	}

	public void setTmpCmsId(Long tmpCmsId) {
		this.tmpCmsId = tmpCmsId;
	}

	public boolean getOtherCase() {
		return otherCase;
	}

	public void setOtherCase(boolean otherCase) {
		this.otherCase = otherCase;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public int getOverDueNum() {
		return overDueNum;
	}

	public void setOverDueNum(int overDueNum) {
		this.overDueNum = overDueNum;
	}

	public boolean isDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(boolean downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
	

}
