package com.pemng.serviceSystem.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pemng.serviceSystem.base.pojo.BasePO;

/**
 * TDataS entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_DATA_S", schema = "dbo", catalog = "cssdb")
public class Data extends BasePO<Object> implements java.io.Serializable {

	// Fields

	private Long id;
	private String mainCode;
	private String key;
	private String value1;
	private String value2;
	private String value3;
	private String remark;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	

	// Constructors

	/** default constructor */
	public Data() {
	}

	/** minimal constructor */
	public Data(Long id, String mainCode) {
		this.id = id;
		this.mainCode = mainCode;
	}

	/** full constructor */
	public Data(Long id, String mainCode, String key, String value1,
			String value2, String value3, String remark, Long crtUsrId,
			Date crtTime, Long updUsrId, Date updTime) {
		this.id = id;
		this.mainCode = mainCode;
		this.key = key;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.remark = remark;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
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

	@Column(name = "MAIN_CODE", nullable = false, length = 20)
	public String getMainCode() {
		return this.mainCode;
	}

	public void setMainCode(String mainCode) {
		this.mainCode = mainCode;
	}

	@Column(name = "[KEY]", length = 20)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VALUE1", length = 200)
	public String getValue1() {
		return this.value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	@Column(name = "VALUE2", length = 200)
	public String getValue2() {
		return this.value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	@Column(name = "VALUE3", length = 200)
	public String getValue3() {
		return this.value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	@Column(name = "REMARK", length = 300)
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

	@Temporal(TemporalType.DATE)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "UPD_TIME", length = 23)
	public Date getUpdTime() {
		return this.updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

}