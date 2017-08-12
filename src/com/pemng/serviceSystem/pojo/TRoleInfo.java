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
@Table(name = "T_ROLE_INFO")
public class TRoleInfo extends com.pemng.serviceSystem.base.pojo.BasePO<TRoleInfo> implements
		java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7765700201365835025L;
	private Long id;
	private String name;
	private String roleDesc;
	private String markForDel;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private Set<TUserRoleRel> TUserRoleRels = new HashSet<TUserRoleRel>(0);
	private Set<TRoleActn> TRoleActns = new HashSet<TRoleActn>(0);

	// Constructors

	/** default constructor */
	public TRoleInfo() {
	}

	/** full constructor */
	public TRoleInfo(String name, String roleDesc, String markForDel,
			Long crtUsrId, Date crtTime, Long updUsrId,
			Date updTime, Set<TUserRoleRel> TUserRoleRels, Set<TRoleActn> TRoleActns) {
		this.name = name;
		this.roleDesc = roleDesc;
		this.markForDel = markForDel;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.TUserRoleRels = TUserRoleRels;
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

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ROLE_DESC", length = 100)
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(name = "MARK_FOR_DEL", length = 1)
	public String getMarkForDel() {
		return this.markForDel;
	}

	public void setMarkForDel(String markForDel) {
		this.markForDel = markForDel;
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
	public Set<TUserRoleRel> getTUserRoleRels() {
		return this.TUserRoleRels;
	}

	public void setTUserRoleRels(Set<TUserRoleRel> TUserRoleRels) {
		this.TUserRoleRels = TUserRoleRels;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TRoleInfo")
	public Set<TRoleActn> getTRoleActns() {
		return this.TRoleActns;
	}

	public void setTRoleActns(Set<TRoleActn> TRoleActns) {
		this.TRoleActns = TRoleActns;
	}

}