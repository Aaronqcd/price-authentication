package com.pemng.serviceSystem.pojo;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.pemng.serviceSystem.base.pojo.BasePO;

/**
 * TCommission entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_COMMISSION")
public class TCommission extends BasePO<TCommission> implements
		java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -8123041498302030789L;
	private Long id;
	private TUnitsInfo tunitsInfo;
	private TCommission TCommission;
	private Long orgnlCmsId;
	private TUserInfo acptUsrId;
	private TUserInfo firstTrialId;
	private TUserInfo retrialId;
	private TUserInfo lastTrialId;
	private String cmsTp;
	private String isEnty;
	private String splmntryMaterlDesc;
	private String suspktOfens;
	private String cmsUsr;
	private String cmsUnitAdd;
	private Long postcode;
	private String unitPhon;
	private String prcAprislRqrms;
	private String prcAprislDocmsNo;
	private Date prcAprislBaseDt;
	private String aprislTp;
	private String cmsUsrTel;
	private String cmsCnt;
	private String artclsDetail;
	private String st;
	private String achiv;
	private Date achivDt;
	private String firstTrialOpinis;
	private String retrialOpinis;
	private String lastTrialOpinis;
	private Date recDt;
	private String recAdrs;
	@Deprecated
	private String prcAprislMtd;
	private String prcAprislPrc;
	private String prcAprislCln;
	private String padCode;
	private Long cmsUnitAreaId;
	private String cmsUnitNm;
	private Double aprislMnySum;
	private Double ociAprislMny;
	private Date acptDt;
	private Date prtDt;
	private Date lastTrialDt;
	private Date gnlSendBkDt;
	private Long athrzUser;
	private String remark;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private String markForDel;
	private Set<TAttachment> TAttachments = new HashSet<TAttachment>(0);
	private Set<TRecUsr> TRecUsrs = new HashSet<TRecUsr>(0);
	private Set<TAprislArtclsInfo> TAprislArtclsInfos = new HashSet<TAprislArtclsInfo>(0);
	private Set<TApprovalInfo> TApprovalInfos = new HashSet<TApprovalInfo>(0);

	// Constructors

	/** default constructor */
	public TCommission() {
	}

	/** full constructor */
	public TCommission(TUnitsInfo tunitsInfo, TCommission TCommission, Long orgnlCmsId,
			TUserInfo acptUsrId, TUserInfo firstTrialId, TUserInfo retrialId,
			TUserInfo lastTrialId, String cmsTp, String isEnty,
			String splmntryMaterlDesc, String suspktOfens, String cmsUsr,
			String cmsUnitAdd, Long postcode, String unitPhon,
			String prcAprislRqrms, String prcAprislDocmsNo,
			Date prcAprislBaseDt, String aprislTp, String cmsUsrTel,
			String cmsCnt, String artclsDetail, String st, String achiv, String firstTrialOpinis,
			String retrialOpinis, String lastTrialOpinis, Date recDt,
			String recAdrs, String remark, Long crtUsrId, Date crtTime,
			Long updUsrId, Date updTime, String markForDel,
			String prcAprislMtd,String prcAprislPrc,String prcAprislCln,
			String padCode,Long cmsUnitAreaId,String cmsUnitNm,
			Double aprislMnySum,Double ociAprislMny, 
			Set<TAttachment> TAttachments, Set<TRecUsr> TRecUsrs,
			Set<TAprislArtclsInfo> TAprislArtclsInfos, Set<TApprovalInfo> TApprovalInfos) {
		this.tunitsInfo = tunitsInfo;
		this.TCommission = TCommission;
		this.orgnlCmsId = orgnlCmsId;
		this.acptUsrId = acptUsrId;
		this.firstTrialId = firstTrialId;
		this.retrialId = retrialId;
		this.lastTrialId = lastTrialId;
		this.cmsTp = cmsTp;
		this.isEnty = isEnty;
		this.splmntryMaterlDesc = splmntryMaterlDesc;
		this.suspktOfens = suspktOfens;
		this.cmsUsr = cmsUsr;
		this.cmsUnitAdd = cmsUnitAdd;
		this.postcode = postcode;
		this.unitPhon = unitPhon;
		this.prcAprislRqrms = prcAprislRqrms;
		this.prcAprislDocmsNo = prcAprislDocmsNo;
		this.prcAprislBaseDt = prcAprislBaseDt;
		this.aprislTp = aprislTp;
		this.cmsUsrTel = cmsUsrTel;
		this.cmsCnt = cmsCnt;
		this.artclsDetail = artclsDetail;
		this.st = st;
		this.achiv = achiv;
		this.firstTrialOpinis = firstTrialOpinis;
		this.retrialOpinis = retrialOpinis;
		this.lastTrialOpinis = lastTrialOpinis;
		this.recDt = recDt;
		this.recAdrs = recAdrs;
		this.remark = remark;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.markForDel = markForDel;
		this.prcAprislMtd = prcAprislMtd;
		this.prcAprislPrc = prcAprislPrc;
		this.prcAprislCln = prcAprislCln;
		this.padCode = padCode;
		this.cmsUnitAreaId = cmsUnitAreaId;
		this.cmsUnitNm = cmsUnitNm;
		this.aprislMnySum = aprislMnySum;
		this.ociAprislMny = ociAprislMny;
		this.TAttachments = TAttachments;
		this.TRecUsrs = TRecUsrs;
		this.TAprislArtclsInfos = TAprislArtclsInfos;
		this.TApprovalInfos = TApprovalInfos;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CMS_UNIT_ID")
	public TUnitsInfo getTunitsInfo() {
		return this.tunitsInfo;
	}

	public void setTunitsInfo(TUnitsInfo tunitsInfo) {
		this.tunitsInfo = tunitsInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HIS_CASE_ID")
	public TCommission getTCommission() {
		return this.TCommission;
	}

	public void setTCommission(TCommission TCommission) {
		this.TCommission = TCommission;
	}

	@Column(name = "ORGNL_CMS_ID", precision = 10, scale = 0)
	public Long getOrgnlCmsId() {
		return this.orgnlCmsId;
	}

	public void setOrgnlCmsId(Long orgnlCmsId) {
		this.orgnlCmsId = orgnlCmsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACPT_USR_ID")
	public TUserInfo getAcptUsrId() {
		return this.acptUsrId;
	}

	public void setAcptUsrId(TUserInfo acptUsrId) {
		this.acptUsrId = acptUsrId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIRST_TRIAL_ID")
	public TUserInfo getFirstTrialId() {
		return this.firstTrialId;
	}

	public void setFirstTrialId(TUserInfo firstTrialId) {
		this.firstTrialId = firstTrialId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RETRIAL_ID")
	public TUserInfo getRetrialId() {
		return this.retrialId;
	}

	public void setRetrialId(TUserInfo retrialId) {
		this.retrialId = retrialId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAST_TRIAL_ID")
	public TUserInfo getLastTrialId() {
		return this.lastTrialId;
	}

	public void setLastTrialId(TUserInfo lastTrialId) {
		this.lastTrialId = lastTrialId;
	}

	@Column(name = "CMS_TP", length = 1)
	public String getCmsTp() {
		return this.cmsTp;
	}

	public void setCmsTp(String cmsTp) {
		this.cmsTp = cmsTp;
	}

	@Column(name = "IS_ENTY", length = 1)
	public String getIsEnty() {
		return this.isEnty;
	}

	public void setIsEnty(String isEnty) {
		this.isEnty = isEnty;
	}

	@Column(name = "SPLMNTRY_MATERL_DESC", length = 500)
	public String getSplmntryMaterlDesc() {
		return this.splmntryMaterlDesc;
	}

	public void setSplmntryMaterlDesc(String splmntryMaterlDesc) {
		this.splmntryMaterlDesc = splmntryMaterlDesc;
	}
	
	@Column(name = "SUSPKT_OFENS", length = 200)
	public String getSuspktOfens() {
		return this.suspktOfens;
	}

	public void setSuspktOfens(String suspktOfens) {
		this.suspktOfens = suspktOfens;
	}

	@Column(name = "CMS_USR", length = 20)
	public String getCmsUsr() {
		return this.cmsUsr;
	}

	public void setCmsUsr(String cmsUsr) {
		this.cmsUsr = cmsUsr;
	}

	@Column(name = "CMS_UNIT_ADD", length = 200)
	public String getCmsUnitAdd() {
		return this.cmsUnitAdd;
	}

	public void setCmsUnitAdd(String cmsUnitAdd) {
		this.cmsUnitAdd = cmsUnitAdd;
	}

	@Column(name = "POSTCODE", precision = 10, scale = 0)
	public Long getPostcode() {
		return this.postcode;
	}

	public void setPostcode(Long postcode) {
		this.postcode = postcode;
	}

	@Column(name = "UNIT_PHON", length = 20)
	public String getUnitPhon() {
		return this.unitPhon;
	}

	public void setUnitPhon(String unitPhon) {
		this.unitPhon = unitPhon;
	}

	@Column(name = "PRC_APRISL_RQRMS", length = 500)
	public String getPrcAprislRqrms() {
		return this.prcAprislRqrms;
	}

	public void setPrcAprislRqrms(String prcAprislRqrms) {
		this.prcAprislRqrms = prcAprislRqrms;
	}
	
	@Column(name = "PRC_APRISL_DOCMS_NO", length = 50)
	public String getPrcAprislDocmsNo() {
		return this.prcAprislDocmsNo;
	}

	public void setPrcAprislDocmsNo(String prcAprislDocmsNo) {
		this.prcAprislDocmsNo = prcAprislDocmsNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRC_APRISL_BASE_DT", length = 7)
	public Date getPrcAprislBaseDt() {
		return this.prcAprislBaseDt;
	}

	public void setPrcAprislBaseDt(Date prcAprislBaseDt) {
		this.prcAprislBaseDt = prcAprislBaseDt;
	}

	@Column(name = "APRISL_TP", length = 1)
	public String getAprislTp() {
		return this.aprislTp;
	}

	public void setAprislTp(String aprislTp) {
		this.aprislTp = aprislTp;
	}

	@Column(name = "CMS_USR_TEL", length = 11)
	public String getCmsUsrTel() {
		return this.cmsUsrTel;
	}

	public void setCmsUsrTel(String cmsUsrTel) {
		this.cmsUsrTel = cmsUsrTel;
	}

	@Column(name = "CMS_CNT", length = 300)
	public String getCmsCnt() {
		return this.cmsCnt;
	}

	public void setCmsCnt(String cmsCnt) {
		this.cmsCnt = cmsCnt;
	}
	
	@Column(name = "ARTCLS_DETAIL", length = 300)
	public String getArtclsDetail() {
		return this.artclsDetail;
	}
	
	public void setArtclsDetail(String artclsDetail) {
		this.artclsDetail = artclsDetail;
	}
	

	@Column(name = "ST", length = 2)
	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	@Column(name = "ACHIV", length = 1)
	public String getAchiv() {
		return this.achiv;
	}

	public void setAchiv(String achiv) {
		this.achiv = achiv;
	}

	@Column(name = "FIRST_TRIAL_OPINIS", length = 500)
	public String getFirstTrialOpinis() {
		return this.firstTrialOpinis;
	}

	public void setFirstTrialOpinis(String firstTrialOpinis) {
		this.firstTrialOpinis = firstTrialOpinis;
	}

	@Column(name = "RETRIAL_OPINIS", length = 500)
	public String getRetrialOpinis() {
		return this.retrialOpinis;
	}

	public void setRetrialOpinis(String retrialOpinis) {
		this.retrialOpinis = retrialOpinis;
	}

	@Column(name = "LAST_TRIAL_OPINIS", length = 500)
	public String getLastTrialOpinis() {
		return this.lastTrialOpinis;
	}

	public void setLastTrialOpinis(String lastTrialOpinis) {
		this.lastTrialOpinis = lastTrialOpinis;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REC_DT", length = 7)
	public Date getRecDt() {
		return this.recDt;
	}

	public void setRecDt(Date recDt) {
		this.recDt = recDt;
	}

	@Column(name = "REC_ADRS", length = 100)
	public String getRecAdrs() {
		return this.recAdrs;
	}

	public void setRecAdrs(String recAdrs) {
		this.recAdrs = recAdrs;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACHIV_DT", length = 7)
	public Date getAchivDt() {
		return achivDt;
	}

	public void setAchivDt(Date achivDt) {
		this.achivDt = achivDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACPT_DT", length = 7)
	public Date getAcptDt() {
		return acptDt;
	}

	public void setAcptDt(Date acptDt) {
		this.acptDt = acptDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_TRIAL_DT", length = 7)
	public Date getLastTrialDt() {
		return lastTrialDt;
	}

	public void setLastTrialDt(Date lastTrialDt) {
		this.lastTrialDt = lastTrialDt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GNL_SEND_BK_DT", length = 7)
	public Date getGnlSendBkDt() {
		return gnlSendBkDt;
	}

	public void setGnlSendBkDt(Date gnlSendBkDt) {
		this.gnlSendBkDt = gnlSendBkDt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRT_DT", length = 7)
	public Date getPrtDt() {
		return prtDt;
	}

	public void setPrtDt(Date prtDt) {
		this.prtDt = prtDt;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CRT_USR_ID", precision = 10, scale = 0)
	public Long getCrtUsrId() {
		return this.crtUsrId;
	}

	public void setCrtUsrId(Long crtUsrId) {
		this.crtUsrId = crtUsrId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRT_TIME", length = 7)
	public Date getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Column(name = "UPD_USR_ID", precision = 10, scale = 0)
	public Long getUpdUsrId() {
		return this.updUsrId;
	}

	public void setUpdUsrId(Long updUsrId) {
		this.updUsrId = updUsrId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPD_TIME", length = 7)
	public Date getUpdTime() {
		return this.updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	@Column(name = "MARK_FOR_DEL", length = 1)
	public String getMarkForDel() {
		return this.markForDel;
	}

	public void setMarkForDel(String markForDel) {
		this.markForDel = markForDel;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TCommission")
	public Set<TAttachment> getTAttachments() {
		return this.TAttachments;
	}

	public void setTAttachments(Set<TAttachment> TAttachments) {
		this.TAttachments = TAttachments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TCommission")
	public Set<TRecUsr> getTRecUsrs() {
		return this.TRecUsrs;
	}

	public void setTRecUsrs(Set<TRecUsr> TRecUsrs) {
		this.TRecUsrs = TRecUsrs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TCommission")
	@OrderBy(value = "id ASC")
	public Set<TAprislArtclsInfo> getTAprislArtclsInfos() {
		return TAprislArtclsInfos;
	}

	public void setTAprislArtclsInfos(Set<TAprislArtclsInfo> tAprislArtclsInfos) {
		TAprislArtclsInfos = tAprislArtclsInfos;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TCommission")
	@OrderBy(value = "id asc")
	public Set<TApprovalInfo> getTApprovalInfos() {
		return TApprovalInfos;
	}

	public void setTApprovalInfos(Set<TApprovalInfo> tApprovalInfos) {
		TApprovalInfos = tApprovalInfos;
	}

	@Transient
	public String getPrcAprislMtd() {
		return prcAprislMtd;
	}

	public void setPrcAprislMtd(String prcAprislMtd) {
		this.prcAprislMtd = prcAprislMtd;
	}

	@Column(name = "PRC_APRISL_PRC", length = 1000)
	public String getPrcAprislPrc() {
		return prcAprislPrc;
	}

	public void setPrcAprislPrc(String prcAprislPrc) {
		this.prcAprislPrc = prcAprislPrc;
	}

	@Column(name = "PRC_APRISL_CLN", length = 1000)
	public String getPrcAprislCln() {
		return prcAprislCln;
	}

	public void setPrcAprislCln(String prcAprislCln) {
		this.prcAprislCln = prcAprislCln;
	}

	@Column(name = "PAD_CODE", length = 20)
	public String getPadCode() {
		return padCode;
	}

	public void setPadCode(String padCode) {
		this.padCode = padCode;
	}

	@Column(name = "CMS_UNIT_AREA_ID", precision = 10, scale = 0)
	public Long getCmsUnitAreaId() {
		return cmsUnitAreaId;
	}

	public void setCmsUnitAreaId(Long cmsUnitAreaId) {
		this.cmsUnitAreaId = cmsUnitAreaId;
	}

	@Column(name = "CMS_UNIT_NM", length = 100)
	public String getCmsUnitNm() {
		return cmsUnitNm;
	}

	public void setCmsUnitNm(String cmsUnitNm) {
		this.cmsUnitNm = cmsUnitNm;
	}

	@Column(name = "APRISL_MNY_SUM", precision = 10, scale = 0)
	public Double getAprislMnySum() {
		return aprislMnySum;
	}

	public void setAprislMnySum(Double aprislMnySum) {
		this.aprislMnySum = aprislMnySum;
	}

	@Column(name = "OCI_APRISL_MNY", precision = 10, scale = 0)
	public Double getOciAprislMny() {
		return ociAprislMny;
	}

	public void setOciAprislMny(Double ociAprislMny) {
		this.ociAprislMny = ociAprislMny;
	}

	@Column(name = "ATHRZ_USER", precision = 10, scale = 0)
	public Long getAthrzUser() {
		return athrzUser;
	}

	public void setAthrzUser(Long athrzUser) {
		this.athrzUser = athrzUser;
	}
	
}