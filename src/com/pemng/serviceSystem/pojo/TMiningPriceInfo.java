package com.pemng.serviceSystem.pojo;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TMiningPriceInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MINING_PRICE_INFO")
public class TMiningPriceInfo extends com.pemng.serviceSystem.base.pojo.BasePO<TMiningPriceInfo> implements
		java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -5076751351776674934L;
	private Long id;
	private TMiningPriceSort TMiningPriceSort;
	private String documentNo;
	private String name;
	private String sortName;
	private String typeName;
	private String guidePrice;
	private String status;
	private Date referenceDate;
	private String miningPrice;
	private TUserInfo TUserInfo;
	private String miningAddress;
	private String source;
	private Date miningDate;
	private String remark;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private String markForDel;


	
	/** default constructor */
	public TMiningPriceInfo() {
	}

	/** full constructor */
	public TMiningPriceInfo(
			TMiningPriceSort tMiningPriceSort,
			String documentNo, String name, String sortName, String typeName,
			String guidePrice, String status, Date referenceDate,
			String miningPrice,TUserInfo TUserInfo,
			String miningAddress, String source, Date miningDate,
			String remark, Long crtUsrId, Date crtTime, Long updUsrId,
			Date updTime, String markForDel) {
		TMiningPriceSort = tMiningPriceSort;
		this.documentNo = documentNo;
		this.name = name;
		this.sortName = sortName;
		this.typeName = typeName;
		this.guidePrice = guidePrice;
		this.status = status;
		this.referenceDate = referenceDate;
		this.miningPrice = miningPrice;
		this.TUserInfo = TUserInfo;
		this.miningAddress = miningAddress;
		this.source = source;
		this.miningDate = miningDate;
		this.remark = remark;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.markForDel = markForDel;
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
	@JoinColumn(name = "MINING_PRICE_SORT_ID")
	public TMiningPriceSort getTMiningPriceSort() {
		return this.TMiningPriceSort;
	}

	public void setTMiningPriceSort(TMiningPriceSort TMiningPriceSort) {
		this.TMiningPriceSort = TMiningPriceSort;
	}

	@Column(name = "DOCUMENT_NO", length = 80)
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "NAME", length = 600)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SORT_NAME", length = 50)
	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	@Column(name = "TYPE_NAME",columnDefinition="TEXT")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "GUIDE_PRICE", length = 20)
	public String getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(String guidePrice) {
		this.guidePrice = guidePrice;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REFERENCE_DATE", length = 7)
	public Date getReferenceDate() {
		return this.referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	@Column(name = "MINING_PRICE", length = 20)
	public String getMiningPrice() {
		return this.miningPrice;
	}

	public void setMiningPrice(String miningPrice) {
		this.miningPrice = miningPrice;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	public TUserInfo getTUserInfo() {
		return this.TUserInfo;
	}

	public void setTUserInfo(TUserInfo TUserInfo) {
		this.TUserInfo = TUserInfo;
	}

	@Column(name = "MINING_ADDRESS", length = 300)
	public String getMiningAddress() {
		return this.miningAddress;
	}

	public void setMiningAddress(String miningAddress) {
		this.miningAddress = miningAddress;
	}

	@Column(name = "SOURCE", length = 300)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MINING_DATE", length = 7)
	public Date getMiningDate() {
		return this.miningDate;
	}

	public void setMiningDate(Date miningDate) {
		this.miningDate = miningDate;
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

}