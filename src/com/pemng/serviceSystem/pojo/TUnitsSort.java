package com.pemng.serviceSystem.pojo;
// default package

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

import com.pemng.serviceSystem.base.pojo.BasePO;

@Entity
@Table(name = "T_UNITS_SORT")
public class TUnitsSort extends BasePO<TUnitsSort> implements java.io.Serializable {

	private static final long serialVersionUID = 3990215125536630702L;
	// Fields
	private Long id;
	private TUnitsSort TUnitsSort;
	private Long sortLevel;
	private String sortName;
	private String remark;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private String markForDel;
	private Set<TUnitsInfo> TUnitsInfos = new HashSet<TUnitsInfo>(0);

	// Constructors

	/** default constructor */
	public TUnitsSort() {
	}

	/** full constructor */
	public TUnitsSort(TUnitsSort TUnitsSort, Long sortLevel, String sortName,
			String remark, Long crtUsrId, Date crtTime, Long updUsrId,
			Date updTime, String markForDel, Set<TUnitsInfo> TUnitsInfos) {
		this.TUnitsSort = TUnitsSort;
		this.sortLevel = sortLevel;
		this.sortName = sortName;
		this.remark = remark;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.markForDel = markForDel;
		this.TUnitsInfos = TUnitsInfos;
	}


	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	public TUnitsSort getTUnitsSort() {
		return this.TUnitsSort;
	}

	public void setTUnitsSort(TUnitsSort TUnitsSort) {
		this.TUnitsSort = TUnitsSort;
	}

	@Column(name = "SORT_LEVEL")
	public Long getSortLevel() {
		return this.sortLevel;
	}

	public void setSortLevel(Long sortLevel) {
		this.sortLevel = sortLevel;
	}

	@Column(name = "SORT_NAME", length = 50)
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

	@Column(name = "CRT_USR_ID")
	public Long getCrtUsrId() {
		return this.crtUsrId;
	}

	public void setCrtUsrId(Long crtUsrId) {
		this.crtUsrId = crtUsrId;
	}

	@Column(name = "CRT_TIME", length = 23)
	public Date getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Column(name = "UPD_USR_ID")
	public Long getUpdUsrId() {
		return this.updUsrId;
	}

	public void setUpdUsrId(Long updUsrId) {
		this.updUsrId = updUsrId;
	}

	@Column(name = "UPD_TIME", length = 23)
	public Date getUpdTime() {
		return this.updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	@Column(name="MARK_FOR_DEL")
	public String getMarkForDel() {
		return this.markForDel;
	}

	public void setMarkForDel(String markForDel) {
		this.markForDel = markForDel;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUnitsSort")
	public Set<TUnitsInfo> getTUnitsInfos() {
		return TUnitsInfos;
	}

	public void setTUnitsInfos(Set<TUnitsInfo> TUnitsInfos) {
		this.TUnitsInfos = TUnitsInfos;
	}

}