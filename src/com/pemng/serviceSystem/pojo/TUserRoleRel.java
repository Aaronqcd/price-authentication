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

@Entity
@Table(name = "T_USER_ROLE_REL")
public class TUserRoleRel extends com.pemng.serviceSystem.base.pojo.BasePO<TUserRoleRel> implements
		java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -7817918219062803868L;
	private Long id;
	private TUserInfo TUserInfo;
	private TRoleInfo TRoleInfo;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;

	// Constructors

	/** default constructor */
	public TUserRoleRel() {
	}

	/** full constructor */
	public TUserRoleRel(TUserInfo TUserInfo, TRoleInfo TRoleInfo,
			Long crtUsrId, Date crtTime, Long updUsrId, Date updTime) {
		this.TUserInfo = TUserInfo;
		this.TRoleInfo = TRoleInfo;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
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
	@JoinColumn(name = "USER_ID")
	public TUserInfo getTUserInfo() {
		return this.TUserInfo;
	}

	public void setTUserInfo(TUserInfo TUserInfo) {
		this.TUserInfo = TUserInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	public TRoleInfo getTRoleInfo() {
		return this.TRoleInfo;
	}

	public void setTRoleInfo(TRoleInfo TRoleInfo) {
		this.TRoleInfo = TRoleInfo;
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

}