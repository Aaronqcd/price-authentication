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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TMiningPriceSort entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MINING_PRICE_SORT")
public class TMiningPriceSort extends com.pemng.serviceSystem.base.pojo.BasePO<TMiningPriceSort> implements
		java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -4702851940350519333L;
	private Long id;
	private TMiningPriceSort TMiningPriceSort;
	private Integer sortLevel;
	private String sortName;
	private String remark;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private String markForDel;
	private Set<TMiningPriceInfo> TMiningPriceInfos = new HashSet<TMiningPriceInfo>(0);
	private Set<TMiningPriceSort> TMiningPriceSorts = new HashSet<TMiningPriceSort>(0);

	// Constructors

	/** default constructor */
	public TMiningPriceSort() {
	}

	/** full constructor */
	public TMiningPriceSort(TMiningPriceSort TMiningPriceSort, Integer sortLevel, String sortName,
			String remark, Long crtUsrId, Date crtTime, Long updUsrId,
			Date updTime, String markForDel, Set<TMiningPriceInfo> TMiningPriceInfos) {
		this.TMiningPriceSort = TMiningPriceSort;
		this.sortLevel = sortLevel;
		this.sortName = sortName;
		this.remark = remark;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.markForDel = markForDel;
		this.TMiningPriceInfos = TMiningPriceInfos;
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
	@JoinColumn(name = "PARENT_ID")
	public TMiningPriceSort getTMiningPriceSort() {
		return this.TMiningPriceSort;
	}

	public void setTMiningPriceSort(TMiningPriceSort TMiningPriceSort) {
		this.TMiningPriceSort = TMiningPriceSort;
	}
	
	@Column(name = "SORT_LEVEL", length = 2)
	public Integer getSortLevel() {
		return this.sortLevel;
	}

	public void setSortLevel(Integer sortLevel) {
		this.sortLevel = sortLevel;
	}

	@Column(name = "SORT_NAME", length = 100)
	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMiningPriceSort")
	public Set<TMiningPriceInfo> getTMiningPriceInfos() {
		return this.TMiningPriceInfos;
	}

	public void setTMiningPriceInfos(Set<TMiningPriceInfo> TMiningPriceInfos) {
		this.TMiningPriceInfos = TMiningPriceInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TMiningPriceSort")
	public Set<TMiningPriceSort> getTMiningPriceSorts() {
		return TMiningPriceSorts;
	}

	public void setTMiningPriceSorts(Set<TMiningPriceSort> tMiningPriceSorts) {
		TMiningPriceSorts = tMiningPriceSorts;
	}

}