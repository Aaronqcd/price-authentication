package com.pemng.serviceSystem.pojo;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pemng.serviceSystem.base.pojo.BasePO;

/**
 * BasicData entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BASIC_DATA")
public class TBasicData extends BasePO<TBasicData> implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -3303442205655462607L;
	private Long id;
	private String theCode;
	private String remark;

	// Constructors

	/** default constructor */
	public TBasicData() {
	}

	/** minimal constructor */
	public TBasicData(Long id) {
		this.id = id;
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

	@Column(name = "THE_CODE", length = 50)
	public String getTheCode() {
		return this.theCode;
	}

	public void setTheCode(String theCode) {
		this.theCode = theCode;
	}

	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}