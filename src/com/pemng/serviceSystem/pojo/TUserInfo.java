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
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "T_USER_INFO")
public class TUserInfo extends com.pemng.serviceSystem.base.pojo.BasePO<TUserInfo> implements
		java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1272110721490003630L;
	private Long id;
	private String name;
	private String username;
	private String password;
	private String tel;
	private String status;
	private String userDesc;
	private String markForDel;
	private Integer loginSum;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private Date lastDate;
	private Set<TUserRoleRel> TUserRoleRels = new HashSet<TUserRoleRel>(0);

	// Constructors

	/** default constructor */
	public TUserInfo() {
	}

	/** full constructor */
	public TUserInfo(String name, String username, String password, String tel,
			String status, String userDesc, String markForDel,
			Integer loginSum, Long crtUsrId, Date crtTime,
			Long updUsrId, Date updTime, Date lastDate,Set<TUserRoleRel> TUserRoleRels) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.tel = tel;
		this.status = status;
		this.userDesc = userDesc;
		this.markForDel = markForDel;
		this.loginSum = loginSum;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.lastDate = lastDate;
		this.TUserRoleRels = TUserRoleRels;
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

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "USERNAME", length = 30)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "USER_DESC", length = 500)
	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	@Column(name = "MARK_FOR_DEL", length = 1)
	public String getMarkForDel() {
		return this.markForDel;
	}

	public void setMarkForDel(String markForDel) {
		this.markForDel = markForDel;
	}

	@Column(name = "LOGIN_SUM", precision = 8, scale = 0)
	public Integer getLoginSum() {
		return this.loginSum;
	}

	public void setLoginSum(Integer loginSum) {
		this.loginSum = loginSum;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_DATE", length = 7)
	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUserInfo")
	public Set<TUserRoleRel> getTUserRoleRels() {
		return this.TUserRoleRels;
	}
	
	public void setTUserRoleRels(Set<TUserRoleRel> TUserRoleRels) {
		this.TUserRoleRels = TUserRoleRels;
	}
	
	@Transient
	public String getStatusStr(){
		String value = "";
		if (StringUtils.isBlank(this.getStatus()) || this.getStatus().equals("-1")) {
			return value;
		}
		if (this.getStatus().equals("0")) {
			value = "禁用";
		}else if (this.getStatus().equals("1")) {
			value = "正常";
		}
		return value;
	}


}