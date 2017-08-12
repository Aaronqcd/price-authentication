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
 * TPrfrncPrcLib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PRFRNC_PRC_LIB")
public class TPrfrncPrcLib extends BasePO<TPrfrncPrcLib> implements java.io.Serializable {


    // Fields    
	 private static final long serialVersionUID = 2562670631731542829L;
	 private Long id;
     private TMiningPriceInfo TMiningPriceInfo;
     private TAprislArtclsInfo TAprislArtclsInfo;
     private Long crtUsrId;
     private Date crtTime;
     private Long updUsrId;
     private Date updTime;


    // Constructors

    /** default constructor */
    public TPrfrncPrcLib() {
    }

    
    /** full constructor */
    public TPrfrncPrcLib(TMiningPriceInfo TMiningPriceInfo, TAprislArtclsInfo TAprislArtclsInfo, Long crtUsrId, Date crtTime, Long updUsrId, Date updTime) {
        this.TMiningPriceInfo = TMiningPriceInfo;
        this.TAprislArtclsInfo = TAprislArtclsInfo;
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
    @JoinColumn(name = "MINING_PRICE_ID")
    public TMiningPriceInfo getTMiningPriceInfo() {
        return this.TMiningPriceInfo;
    }
    
    public void setTMiningPriceInfo(TMiningPriceInfo TMiningPriceInfo) {
        this.TMiningPriceInfo = TMiningPriceInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APRSL_ARTCLS_ID")
    public TAprislArtclsInfo getTAprislArtclsInfo() {
        return this.TAprislArtclsInfo;
    }
    
    public void setTAprislArtclsInfo(TAprislArtclsInfo TAprislArtclsInfo) {
        this.TAprislArtclsInfo = TAprislArtclsInfo;
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