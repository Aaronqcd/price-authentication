package com.pemng.serviceSystem.pojo;
// default package

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



/**
 * UnitsInfoDto entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_UNITS_INFO")
public class TUnitsInfo extends com.pemng.serviceSystem.base.pojo.BasePO<TUnitsInfo> implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -9119733718898709224L;

	private Long id;

	private TUnitsSort TUnitsSort;
	private String no;
	private String type;
	private String name;
	private String shortName;
	private String address;
	private String post;
	private String contactMan;
	private String contactTel;
	private String remark;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private String markForDel;
	
	// Constructors

	/** default constructor */
	public TUnitsInfo() {
	}

	/** full constructor */
	public TUnitsInfo(TUnitsSort TUnitsSort, String no, String type,
			String name, String shortName, String address, String post,
			String contactMan, String contactTel, String remark, Long crtUsrId,
			Date crtTime, Long updUsrId, Date updTime, String markForDel) {
		this.TUnitsSort = TUnitsSort;
		this.no = no;
		this.type = type;
		this.name = name;
		this.shortName = shortName;
		this.address = address;
		this.post = post;
		this.contactMan = contactMan;
		this.contactTel = contactTel;
		this.remark = remark;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.markForDel = markForDel;
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
	@JoinColumn(name = "UNITS_SORT")
	public TUnitsSort getTUnitsSort() {
		return this.TUnitsSort;
	}

	public void setTUnitsSort(TUnitsSort TUnitsSort) {
		this.TUnitsSort = TUnitsSort;
	}

	@Column(name = "NO", length = 10)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SHORT_NAME", length = 200)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "POST", length = 10)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "CONTACT_MAN", length = 20)
	public String getContactMan() {
		return this.contactMan;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	@Column(name = "CONTACT_TEL", length = 20)
	public String getContactTel() {
		return this.contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CRT_USR_ID", length = 10)
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

}