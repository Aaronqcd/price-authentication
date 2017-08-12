package com.pemng.serviceSystem.pojo;

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

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "T_ACTN_INFO")
public class TActnInfo extends com.pemng.serviceSystem.base.pojo.BasePO<TActnInfo> implements
		java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -6819224030465670679L;
	private Long id;
	private TFnctn TFnctn;
	private String nm;
	private String action;
	private String actnUrl;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private Set<TRoleActn> TRoleActns = new HashSet<TRoleActn>(0);

	// Constructors

	/** default constructor */
	public TActnInfo() {
	}

	/** full constructor */
	public TActnInfo(TFnctn TFnctn, String nm, String action,
			String actnUrl, Long crtUsrId, Date crtTime,
			Long updUsrId, Date updTime, Set<TRoleActn> TRoleActns) {
		this.TFnctn = TFnctn;
		this.nm = nm;
		this.action = action;
		this.actnUrl = actnUrl;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.TRoleActns = TRoleActns;
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
	@JoinColumn(name = "FNCTN_ID")
	public TFnctn getTFnctn() {
		return this.TFnctn;
	}

	public void setTFnctn(TFnctn TFnctn) {
		this.TFnctn = TFnctn;
	}

	@Column(name = "NM", length = 200)
	public String getNm() {
		return this.nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	@Column(name = "ACTION", length = 20)	
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "ACTN_URL", length = 100)	
	public String getActnUrl() {
		return this.actnUrl;
	}

	public void setActnUrl(String actnUrl) {
		this.actnUrl = actnUrl;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TRoleInfo")
	public Set<TRoleActn> getTRoleActns() {
		return this.TRoleActns;
	}

	public void setTRoleActns(Set<TRoleActn> TRoleActns) {
		this.TRoleActns = TRoleActns;
	}

}