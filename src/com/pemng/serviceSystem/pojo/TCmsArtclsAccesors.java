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
 * TCmsArtclsAccesors entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CMS_ARTCLS_ACCESORS")
public class TCmsArtclsAccesors extends BasePO<TCmsArtclsAccesors> implements java.io.Serializable {


	 private static final long serialVersionUID = 4201771081255411953L;

     private Long id;
     private TAprislArtclsInfo TAprislArtclsInfo;
     private Double valutnAmnt;
     private String valutn;
     private String nm;
     private String remark;
     private Long crtUsrId;
     private Date crtTime;
     private Long updUsrId;
     private Date updTime;
     private String markForDel;


    // Constructors

    /** default constructor */
    public TCmsArtclsAccesors() {
    }

    
    /** full constructor */
    public TCmsArtclsAccesors(TAprislArtclsInfo TAprislArtclsInfo, Double valutnAmnt, String valutn, String nm, String remark, Long crtUsrId, Date crtTime, Long updUsrId, Date updTime, String markForDel) {
        this.TAprislArtclsInfo = TAprislArtclsInfo;
        this.valutnAmnt = valutnAmnt;
        this.valutn = valutn;
        this.nm = nm;
        this.remark = remark;
        this.crtUsrId = crtUsrId;
        this.crtTime = crtTime;
        this.updUsrId = updUsrId;
        this.updTime = updTime;
        this.markForDel = markForDel;
    }

   
    // Property accessors
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
	@JoinColumn(name = "APRSL_ARTCLS_ID")
    public TAprislArtclsInfo getTAprislArtclsInfo() {
        return this.TAprislArtclsInfo;
    }
    
    public void setTAprislArtclsInfo(TAprislArtclsInfo TAprislArtclsInfo) {
        this.TAprislArtclsInfo = TAprislArtclsInfo;
    }

    @Column(name = "VALUTN_AMNT", precision = 10, scale = 2)
    public Double getValutnAmnt() {
        return this.valutnAmnt;
    }
    
    public void setValutnAmnt(Double valutnAmnt) {
        this.valutnAmnt = valutnAmnt;
    }

    @Column(name = "VALUTN", length = 1)
    public String getValutn() {
        return this.valutn;
    }
    
    public void setValutn(String valutn) {
        this.valutn = valutn;
    }

    @Column(name = "NM", length = 50)
    public String getNm() {
        return this.nm;
    }
    
    public void setNm(String nm) {
        this.nm = nm;
    }

    @Column(name = "REMARK", length = 500)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
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

	@Column(name = "MARK_FOR_DEL", length = 1)
    public String getMarkForDel() {
        return this.markForDel;
    }
    
    public void setMarkForDel(String markForDel) {
        this.markForDel = markForDel;
    }
   


}