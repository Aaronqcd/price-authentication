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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_FNCTN")
public class TFnctn extends com.pemng.serviceSystem.base.pojo.BasePO<TFnctn> implements
		java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8012984085820418574L;
	private Long id;
	private String name;
	private String fnctnDesc;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private Set<TPartMenu> TPartMenus = new HashSet<TPartMenu>(0);
	private Set<TActnInfo> TActnInfos = new HashSet<TActnInfo>(0);

	// Constructors

	/** default constructor */
	public TFnctn() {
	}

	/** full constructor */
	public TFnctn(String name, String fnctnDesc, Long crtUsrId,
			Date crtTime, Long updUsrId, Date updTime, Set<TPartMenu> TPartMenus,
			Set<TActnInfo> TActnInfos) {
		this.name = name;
		this.fnctnDesc = fnctnDesc;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.TPartMenus = TPartMenus;
		this.TActnInfos = TActnInfos;
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

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FNCTN_DESC", length = 100)
	public String getFnctnDesc() {
		return this.fnctnDesc;
	}

	public void setFnctnDesc(String fnctnDesc) {
		this.fnctnDesc = fnctnDesc;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TFnctn")
	public Set<TPartMenu> getTPartMenus() {
		return this.TPartMenus;
	}

	public void setTPartMenus(Set<TPartMenu> TPartMenus) {
		this.TPartMenus = TPartMenus;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TFnctn")
	public Set<TActnInfo> getTActnInfos() {
		return this.TActnInfos;
	}

	public void setTActnInfos(Set<TActnInfo> TActnInfos) {
		this.TActnInfos = TActnInfos;
	}


}