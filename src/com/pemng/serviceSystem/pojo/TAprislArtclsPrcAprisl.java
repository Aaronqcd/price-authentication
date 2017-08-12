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

import com.pemng.serviceSystem.base.pojo.BasePO;


/**
 * TCmsArtclsPrcAprisl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APRISL_ARTCLS_PRC_APRISL")
public class TAprislArtclsPrcAprisl extends BasePO<TAprislArtclsPrcAprisl> implements java.io.Serializable {


     // Fields    
	 private static final long serialVersionUID = -4716342698577386416L;
	 private Long id;
     private String aprislMthd;
     private String bscFml;
     private Double aprislMny;
     private String aprislPrcs;
     private Double mktAvgPrc;
     private Double ctRplcmnt;
     private Integer ctNewRate;
     private Integer ctEntyDvltnRate;
     private Integer ctFnctnlDvltnRate;
     private Integer ctEcnmcDvltnRate;
     private Double ctEntyDvltn;
     private Double ctFnctnlDvltn;
     private Double ctEcnmcDvltn;
     private Double mktATxnCrctFctr;
     private Double mktBTrddtCrctFctr;
     private Double mktCAreaCrctFctr;
     private Double mktDIdvdlCrctFctr;
     private Double ikmAAvgAnnIcm;
     private Integer ikmRDscntRate;
     private Integer ikmNYr;
     private Integer clrCmplxNewRate;
     private Integer clrLqdtDscntRate;
     private String remark;
     private Long crtUsrId;
     private Date crtTime;
     private Long updUsrId;
     private Date updTime;
     private String markForDel;
     private Set<TAprislArtclsInfo> TAprislArtclsInfos = new HashSet<TAprislArtclsInfo>(0);


    // Constructors

    /** default constructor */
    public TAprislArtclsPrcAprisl() {
    }

    
    /** full constructor */
    public TAprislArtclsPrcAprisl(String aprislMthd, String bscFml, Double aprislMny, String aprislPrcs, Double mktAvgPrc, Double ctRplcmnt, Integer ctNewRate, Integer ctEntyDvltnRate, Integer ctFnctnlDvltnRate, Integer ctEcnmcDvltnRate, Double ctEntyDvltn, Double ctFnctnlDvltn, Double ctEcnmcDvltn, Double mktATxnCrctFctr, Double mktBTrddtCrctFctr, Double mktCAreaCrctFctr, Double mktDIdvdlCrctFctr, Double ikmAAvgAnnIcm, Integer ikmRDscntRate, Integer ikmNYr, Integer clrCmplxNewRate, Integer clrLqdtDscntRate, String remark, Long crtUsrId, Date crtTime, Long updUsrId, Date updTime, String markForDel, Set<TAprislArtclsInfo> TAprislArtclsInfos) {
        this.aprislMthd = aprislMthd;
        this.bscFml = bscFml;
        this.aprislMny = aprislMny;
        this.aprislPrcs = aprislPrcs;
        this.mktAvgPrc = mktAvgPrc;
        this.ctRplcmnt = ctRplcmnt;
        this.ctNewRate = ctNewRate;
        this.ctEntyDvltnRate = ctEntyDvltnRate;
        this.ctFnctnlDvltnRate = ctFnctnlDvltnRate;
        this.ctEcnmcDvltnRate = ctEcnmcDvltnRate;
        this.ctEntyDvltn = ctEntyDvltn;
        this.ctFnctnlDvltn = ctFnctnlDvltn;
        this.ctEcnmcDvltn = ctEcnmcDvltn;
        this.mktATxnCrctFctr = mktATxnCrctFctr;
        this.mktBTrddtCrctFctr = mktBTrddtCrctFctr;
        this.mktCAreaCrctFctr = mktCAreaCrctFctr;
        this.mktDIdvdlCrctFctr = mktDIdvdlCrctFctr;
        this.ikmAAvgAnnIcm = ikmAAvgAnnIcm;
        this.ikmRDscntRate = ikmRDscntRate;
        this.ikmNYr = ikmNYr;
        this.clrCmplxNewRate = clrCmplxNewRate;
        this.clrLqdtDscntRate = clrLqdtDscntRate;
        this.remark = remark;
        this.crtUsrId = crtUsrId;
        this.crtTime = crtTime;
        this.updUsrId = updUsrId;
        this.updTime = updTime;
        this.markForDel = markForDel;
        this.TAprislArtclsInfos = TAprislArtclsInfos;
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

    @Column(name = "APRISL_MTHD", length = 1)
    public String getAprislMthd() {
        return this.aprislMthd;
    }
    
    public void setAprislMthd(String aprislMthd) {
        this.aprislMthd = aprislMthd;
    }

    @Column(name = "BSC_FML", length = 1)
    public String getBscFml() {
        return this.bscFml;
    }
    
    public void setBscFml(String bscFml) {
        this.bscFml = bscFml;
    }

    @Column(name = "APRISL_MNY", precision = 10, scale = 2)
    public Double getAprislMny() {
        return this.aprislMny;
    }
    
    public void setAprislMny(Double aprislMny) {
        this.aprislMny = aprislMny;
    }

    @Column(name = "APRISL_PRCS", length = 200)
    public String getAprislPrcs() {
        return this.aprislPrcs;
    }
    
    public void setAprislPrcs(String aprislPrcs) {
        this.aprislPrcs = aprislPrcs;
    }

    @Column(name = "MKT_AVG_PRC", precision = 10, scale = 2)
    public Double getMktAvgPrc() {
        return this.mktAvgPrc;
    }
    
    public void setMktAvgPrc(Double mktAvgPrc) {
        this.mktAvgPrc = mktAvgPrc;
    }

    @Column(name = "CT_RPLCMNT", precision = 10, scale = 2)
    public Double getCtRplcmnt() {
        return this.ctRplcmnt;
    }
    
    public void setCtRplcmnt(Double ctRplcmnt) {
        this.ctRplcmnt = ctRplcmnt;
    }

    @Column(name = "CT_NEW_RATE",  length = 3)
    public Integer getCtNewRate() {
        return this.ctNewRate;
    }
    
    public void setCtNewRate(Integer ctNewRate) {
        this.ctNewRate = ctNewRate;
    }

    @Column(name = "CT_ENTY_DVLTN_RATE", length = 3)
    public Integer getCtEntyDvltnRate() {
        return this.ctEntyDvltnRate;
    }
    
    public void setCtEntyDvltnRate(Integer ctEntyDvltnRate) {
        this.ctEntyDvltnRate = ctEntyDvltnRate;
    }

    @Column(name = "CT_FNCTNL_DVLTN_RATE", length = 3)
    public Integer getCtFnctnlDvltnRate() {
        return this.ctFnctnlDvltnRate;
    }
    
    public void setCtFnctnlDvltnRate(Integer ctFnctnlDvltnRate) {
        this.ctFnctnlDvltnRate = ctFnctnlDvltnRate;
    }

    @Column(name = "CT_ECNMC_DVLTN_RATE", length = 3)
    public Integer getCtEcnmcDvltnRate() {
        return this.ctEcnmcDvltnRate;
    }
    
    public void setCtEcnmcDvltnRate(Integer ctEcnmcDvltnRate) {
        this.ctEcnmcDvltnRate = ctEcnmcDvltnRate;
    }

    @Column(name = "CT_ENTY_DVLTN", precision = 10, scale = 2)
    public Double getCtEntyDvltn() {
        return this.ctEntyDvltn;
    }
    
    public void setCtEntyDvltn(Double ctEntyDvltn) {
        this.ctEntyDvltn = ctEntyDvltn;
    }

    @Column(name = "CT_FNCTNL_DVLTN", precision = 10, scale = 2)
    public Double getCtFnctnlDvltn() {
        return this.ctFnctnlDvltn;
    }
    
    public void setCtFnctnlDvltn(Double ctFnctnlDvltn) {
        this.ctFnctnlDvltn = ctFnctnlDvltn;
    }

    @Column(name = "CT_ECNMC_DVLTN", precision = 10, scale = 2)
    public Double getCtEcnmcDvltn() {
        return this.ctEcnmcDvltn;
    }
    
    public void setCtEcnmcDvltn(Double ctEcnmcDvltn) {
        this.ctEcnmcDvltn = ctEcnmcDvltn;
    }
    
    @Column(name = "MKT_A_TXN_CRCT_FCTR", precision = 10, scale = 2)
    public Double getMktATxnCrctFctr() {
        return this.mktATxnCrctFctr;
    }
    
    public void setMktATxnCrctFctr(Double mktATxnCrctFctr) {
        this.mktATxnCrctFctr = mktATxnCrctFctr;
    }

    @Column(name = "MKT_B_TRDDT_CRCT_FCTR", precision = 10, scale = 2)
    public Double getMktBTrddtCrctFctr() {
        return this.mktBTrddtCrctFctr;
    }
    
    public void setMktBTrddtCrctFctr(Double mktBTrddtCrctFctr) {
        this.mktBTrddtCrctFctr = mktBTrddtCrctFctr;
    }

    @Column(name = "MKT_C_AREA_CRCT_FCTR", precision = 10, scale = 2)
    public Double getMktCAreaCrctFctr() {
        return this.mktCAreaCrctFctr;
    }
    
    public void setMktCAreaCrctFctr(Double mktCAreaCrctFctr) {
        this.mktCAreaCrctFctr = mktCAreaCrctFctr;
    }

    @Column(name = "MKT_D_IDVDL_CRCT_FCTR", precision = 10, scale = 2)
    public Double getMktDIdvdlCrctFctr() {
        return this.mktDIdvdlCrctFctr;
    }
    
    public void setMktDIdvdlCrctFctr(Double mktDIdvdlCrctFctr) {
        this.mktDIdvdlCrctFctr = mktDIdvdlCrctFctr;
    }

    @Column(name = "IKM_A_AVG_ANN_ICM", precision = 10, scale = 2)
    public Double getIkmAAvgAnnIcm() {
        return this.ikmAAvgAnnIcm;
    }
    
    public void setIkmAAvgAnnIcm(Double ikmAAvgAnnIcm) {
        this.ikmAAvgAnnIcm = ikmAAvgAnnIcm;
    }

    @Column(name = "IKM_R_DSCNT_RATE", length = 3)
    public Integer getIkmRDscntRate() {
        return this.ikmRDscntRate;
    }
    
    public void setIkmRDscntRate(Integer ikmRDscntRate) {
        this.ikmRDscntRate = ikmRDscntRate;
    }

    @Column(name = "IKM_N_YR", length = 3)
    public Integer getIkmNYr() {
        return this.ikmNYr;
    }
    
    public void setIkmNYr(Integer ikmNYr) {
        this.ikmNYr = ikmNYr;
    }

    @Column(name = "CLR_CMPLX_NEW_RATE", length = 3)
    public Integer getClrCmplxNewRate() {
        return this.clrCmplxNewRate;
    }
    
    public void setClrCmplxNewRate(Integer clrCmplxNewRate) {
        this.clrCmplxNewRate = clrCmplxNewRate;
    }

    @Column(name = "CLR_LQDT_DSCNT_RATE", length = 3)
    public Integer getClrLqdtDscntRate() {
        return this.clrLqdtDscntRate;
    }
    
    public void setClrLqdtDscntRate(Integer clrLqdtDscntRate) {
        this.clrLqdtDscntRate = clrLqdtDscntRate;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAprislArtclsPrcAprisl")
    public Set<TAprislArtclsInfo> getTAprislArtclsInfos() {
        return this.TAprislArtclsInfos;
    }
    
    public void setTAprislArtclsInfos(Set<TAprislArtclsInfo> TAprislArtclsInfos) {
        this.TAprislArtclsInfos = TAprislArtclsInfos;
    }
   








}