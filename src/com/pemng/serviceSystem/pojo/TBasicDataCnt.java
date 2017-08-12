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

import com.pemng.serviceSystem.base.pojo.BasePO;

/**
 * BasicData entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BASIC_DATA_CNT")
public class TBasicDataCnt extends BasePO<TBasicDataCnt> implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 4134061117072812289L;
	private Long id;
	private TBasicData TBasicData;
	private String theKey;
	private String value;
	private String value2;
	private String value3;
	private String markForDel;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;

	// Constructors

	/** default constructor */
	public TBasicDataCnt() {
	}

	/** minimal constructor */
	public TBasicDataCnt(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TBasicDataCnt(Long id, String theCode, String remark, String theKey, String value1, String value2, String value3, String markForDel, Long crtUsrId, Date crtTime,
			Long updUsrId, Date updTime) {
		this.id = id;
		this.theKey = theKey;
		this.value = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.markForDel = markForDel;
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

	@Column(name = "THE_KEY", length = 20)
	public String getTheKey() {
		return this.theKey;
	}

	public void setTheKey(String theKey) {
		this.theKey = theKey;
	}

	@Column(name = "VALUE1", length = 50)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value1) {
		this.value = value1;
	}

	@Column(name = "VALUE2", length = 50)
	public String getValue2() {
		return this.value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	@Column(name = "VALUE3", length = 50)
	public String getValue3() {
		return this.value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	@Column(name = "MARK_FOR_DEL", length = 1)
	public String getMarkForDel() {
		return this.markForDel;
	}

	public void setMarkForDel(String markForDel) {
		this.markForDel = markForDel;
	}

	@Column(name = "CRT_USR_ID", precision = 20, scale = 0)
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

	@Column(name = "UPD_USR_ID", precision = 20, scale = 0)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATA_ID")
	public TBasicData getTBasicData() {
		return TBasicData;
	}

	public void setTBasicData(TBasicData TBasicData) {
		this.TBasicData = TBasicData;
	}

}