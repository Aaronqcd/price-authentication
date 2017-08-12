package com.pemng.serviceSystem.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pemng.serviceSystem.base.pojo.BasePO;


/**
 * TAprislArtclsInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APRISL_ARTCLS_INFO")
public class TAprislArtclsInfo extends BasePO<TAprislArtclsInfo> implements java.io.Serializable {

    // Fields    

	 private static final long serialVersionUID = -3584060392672508989L;
	 private Long id;
     private TCommission TCommission;
     private TAprislArtclsPrcAprisl TAprislArtclsPrcAprisl;
     private TUserInfo TUserInfo;
     private String aprislSt;
     private String artclTp;
     private String artclNm;
     private BigDecimal qnty;
     private String unit;
     private String brndNm;
     private String specTp;
     private Double orgnlPrc;
     private Double totl;
     private Date prcArtclRefDt;
     private Integer newRate;
     private Date buyTm;
     private String isNmlUse;
     private String isLos;
     private String isMjrChng;
     private String othInfo;
     private Double aprislPrc;
     private String prcArtclRmk;
     private String remark;
     private String mblImeiTp;
     private String mblNtwkLcns;
     private String vcPlNm;
     private String vcFrmNm;
     private String vcEgnNm;
     private String vcCarClr;
     private Date vcInitRegDt;
     private Long vcTotlTrip;
     private String vcUseNtr;
     private String vcLosFuelKnd;
     private String vcLosStruct;
     private Date vcLosInitRegDt;
     private Date vcLosAnnPassDt;
     private String vcLosPymntStatn;
     private String vcLosBuyAdrs;
     private String vcLosArtclRefDtSt;
     private String jjcSap;
     private String jjcSize;
     private String jjcWght;
     private String jjcClr;
     private String jjcGrd;
     private String jjcFnns;
     private String jjcIsQltArtclCert;
     private String jjcCertRprtNo;
     private String mfArea;
     private String mfBuyAdd;
     private String blOwnrshpCertNo;
     private String blAdrs;
     private String prcAprislMtd;
     private Long crtUsrId;
     private Date crtTime;
     private Long updUsrId;
     private Date updTime;
     private String markForDel;
     private Long tmpCmsId;
     //private Set<TCmsArtclsAccesors> TCmsArtclsAccesorses = new HashSet<TCmsArtclsAccesors>(0);
     private List<TCmsArtclsAccesors> TCmsArtclsAccesorses = new ArrayList<TCmsArtclsAccesors>(0);
     private List<TPrfrncPrcLib> TPrfrncPrcLib = new ArrayList<TPrfrncPrcLib>(0);
    // Constructors

    /** default constructor */
    public TAprislArtclsInfo() {
    }

    public TAprislArtclsInfo(TCommission TCommission, TAprislArtclsPrcAprisl TAprislArtclsPrcAprisl, TUserInfo TUserInfo, 
    		String aprislSt, String artclTp, String artclNm, BigDecimal qnty, String unit, String brndNm, String specTp, Double orgnlPrc, 
    		Double totl, Date prcArtclRefDt, Integer newRate, Date buyTm, String isNmlUse, String isLos, String isMjrChng, 
    		String othInfo, Double aprislPrc, String prcArtclRmk, String remark, String mblImeiTp, String mblNtwkLcns, 
    		String vcPlNm, String vcFrmNm, String vcEgnNm, String vcCarClr, Date vcInitRegDt, Long vcTotlTrip, String vcUseNtr, 
    		String vcLosFuelKnd, String vcLosStruct, Date vcLosInitRegDt, Date vcLosAnnPassDt, String vcLosPymntStatn, 
    		String vcLosBuyAdrs, String vcLosArtclRefDtSt, String jjcSap, String jjcSize, String jjcWght, String jjcClr, 
    		String jjcGrd, String jjcFnns, String jjcIsQltArtclCert, String jjcCertRprtNo, String mfArea, String mfBuyAdd, String prcAprislMtd,
    		String blOwnrshpCertNo, String blAdrs, Long crtUsrId, Date crtTime, Long updUsrId, Date updTime, String markForDel, 
    		List<TCmsArtclsAccesors> TCmsArtclsAccesorses,List<TPrfrncPrcLib> TPrfrncPrcLib) {
        this.TCommission = TCommission;
        this.TAprislArtclsPrcAprisl = TAprislArtclsPrcAprisl;
        this.TUserInfo = TUserInfo;
        this.aprislSt = aprislSt;
        this.artclTp = artclTp;
        this.artclNm = artclNm;
        this.qnty = qnty;
        this.unit = unit;
        this.brndNm = brndNm;
        this.specTp = specTp;
        this.orgnlPrc = orgnlPrc;
        this.totl = totl;
        this.prcArtclRefDt = prcArtclRefDt;
        this.newRate = newRate;
        this.buyTm = buyTm;
        this.isNmlUse = isNmlUse;
        this.isLos = isLos;
        this.isMjrChng = isMjrChng;
        this.othInfo = othInfo;
        this.aprislPrc = aprislPrc;
        this.prcArtclRmk = prcArtclRmk;
        this.remark = remark;
        this.mblImeiTp = mblImeiTp;
        this.mblNtwkLcns = mblNtwkLcns;
        this.vcPlNm = vcPlNm;
        this.vcFrmNm = vcFrmNm;
        this.vcEgnNm = vcEgnNm;
        this.vcCarClr = vcCarClr;
        this.vcInitRegDt = vcInitRegDt;
        this.vcTotlTrip = vcTotlTrip;
        this.vcUseNtr = vcUseNtr;
        this.vcLosFuelKnd = vcLosFuelKnd;
        this.vcLosStruct = vcLosStruct;
        this.vcLosInitRegDt = vcLosInitRegDt;
        this.vcLosAnnPassDt = vcLosAnnPassDt;
        this.vcLosPymntStatn = vcLosPymntStatn;
        this.vcLosBuyAdrs = vcLosBuyAdrs;
        this.vcLosArtclRefDtSt = vcLosArtclRefDtSt;
        this.jjcSap = jjcSap;
        this.jjcSize = jjcSize;
        this.jjcWght = jjcWght;
        this.jjcClr = jjcClr;
        this.jjcGrd = jjcGrd;
        this.jjcFnns = jjcFnns;
        this.jjcIsQltArtclCert = jjcIsQltArtclCert;
        this.jjcCertRprtNo = jjcCertRprtNo;
        this.mfArea = mfArea;
        this.mfBuyAdd = mfBuyAdd;
        this.blOwnrshpCertNo = blOwnrshpCertNo;
        this.blAdrs = blAdrs;
        this.prcAprislMtd = prcAprislMtd;
        this.crtUsrId = crtUsrId;
        this.crtTime = crtTime;
        this.updUsrId = updUsrId;
        this.updTime = updTime;
        this.markForDel = markForDel;
        this.TCmsArtclsAccesorses = TCmsArtclsAccesorses;
        this.TPrfrncPrcLib=TPrfrncPrcLib;
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
    @JoinColumn(name = "CMS_ID")
    public TCommission getTCommission() {
        return this.TCommission;
    }
    
    public void setTCommission(TCommission TCommission) {
        this.TCommission = TCommission;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTCL_PRC_APRISL_ID")
    public TAprislArtclsPrcAprisl getTAprislArtclsPrcAprisl() {
        return this.TAprislArtclsPrcAprisl;
    }
    
    public void setTAprislArtclsPrcAprisl(TAprislArtclsPrcAprisl TAprislArtclsPrcAprisl) {
        this.TAprislArtclsPrcAprisl = TAprislArtclsPrcAprisl;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APRISL_USR")
    public TUserInfo getTUserInfo() {
        return this.TUserInfo;
    }
    
    public void setTUserInfo(TUserInfo TUserInfo) {
        this.TUserInfo = TUserInfo;
    }

    @Column(name = "APRISL_ST", length = 1)
    public String getAprislSt() {
        return this.aprislSt;
    }
    
    public void setAprislSt(String aprislSt) {
        this.aprislSt = aprislSt;
    }

    @Column(name = "ARTCL_TP", length = 3)
    public String getArtclTp() {
        return this.artclTp;
    }
    
    public void setArtclTp(String artclTp) {
        this.artclTp = artclTp;
    }

    @Column(name = "ARTCL_NM", length = 50)
    public String getArtclNm() {
        return this.artclNm;
    }
    
    public void setArtclNm(String artclNm) {
        this.artclNm = artclNm;
    }

    @Column(name = "QNTY", precision = 10, scale = 4)	
    public BigDecimal getQnty() {
        return this.qnty;
    }
    
    public void setQnty(BigDecimal qnty) {
        this.qnty = qnty;
    }

    @Column(name = "UNIT", length = 50)
    public String getUnit() {
        return this.unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "BRND_NM", length = 50)
    public String getBrndNm() {
        return this.brndNm;
    }
    
    public void setBrndNm(String brndNm) {
        this.brndNm = brndNm;
    }

    @Column(name = "SPEC_TP", length = 50)
    public String getSpecTp() {
        return this.specTp;
    }
    
    public void setSpecTp(String specTp) {
        this.specTp = specTp;
    }

    @Column(name = "ORGNL_PRC", precision = 10, scale = 0)	
    public Double getOrgnlPrc() {
        return this.orgnlPrc;
    }
    
    public void setOrgnlPrc(Double orgnlPrc) {
        this.orgnlPrc = orgnlPrc;
    }

    @Column(name = "TOTL", precision = 10, scale = 0)	
    public Double getTotl() {
        return this.totl;
    }
    
    public void setTotl(Double totl) {
        this.totl = totl;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PRC_ARTCL_REF_DT", length = 7)
    public Date getPrcArtclRefDt() {
        return this.prcArtclRefDt;
    }
    
    public void setPrcArtclRefDt(Date prcArtclRefDt) {
        this.prcArtclRefDt = prcArtclRefDt;
    }

    @Column(name = "NEW_RATE", length = 3)
    public Integer getNewRate() {
        return this.newRate;
    }
    
    public void setNewRate(Integer newRate) {
        this.newRate = newRate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BUY_TM", length = 7)
    public Date getBuyTm() {
        return this.buyTm;
    }
    
    public void setBuyTm(Date buyTm) {
        this.buyTm = buyTm;
    }

    @Column(name = "IS_NML_USE", length = 1)
    public String getIsNmlUse() {
        return this.isNmlUse;
    }
    
    public void setIsNmlUse(String isNmlUse) {
        this.isNmlUse = isNmlUse;
    }

    @Column(name = "IS_LOS", length = 1)
    public String getIsLos() {
        return this.isLos;
    }
    
    public void setIsLos(String isLos) {
        this.isLos = isLos;
    }

    @Column(name = "IS_MJR_CHNG", length = 1)
    public String getIsMjrChng() {
        return this.isMjrChng;
    }
    
    public void setIsMjrChng(String isMjrChng) {
        this.isMjrChng = isMjrChng;
    }

    @Column(name = "OTH_INFO", length = 1)
    public String getOthInfo() {
        return this.othInfo;
    }
    
    public void setOthInfo(String othInfo) {
        this.othInfo = othInfo;
    }

    @Column(name = "APRISL_PRC", precision = 10, scale = 0)	
    public Double getAprislPrc() {
        return this.aprislPrc;
    }
    
    public void setAprislPrc(Double aprislPrc) {
        this.aprislPrc = aprislPrc;
    }

    @Column(name = "PRC_ARTCL_RMK", columnDefinition="TEXT")
    public String getPrcArtclRmk() {
        return this.prcArtclRmk;
    }
    
    public void setPrcArtclRmk(String prcArtclRmk) {
        this.prcArtclRmk = prcArtclRmk;
    }

    @Column(name = "REMARK", length = 500)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "MBL_IMEI_TP", length = 100)
    public String getMblImeiTp() {
        return this.mblImeiTp;
    }
    
    public void setMblImeiTp(String mblImeiTp) {
        this.mblImeiTp = mblImeiTp;
    }

    @Column(name = "MBL_NTWK_LCNS", length = 100)
    public String getMblNtwkLcns() {
        return this.mblNtwkLcns;
    }
    
    public void setMblNtwkLcns(String mblNtwkLcns) {
        this.mblNtwkLcns = mblNtwkLcns;
    }

    @Column(name = "VC_PL_NM", length = 100)
    public String getVcPlNm() {
        return this.vcPlNm;
    }
    
    public void setVcPlNm(String vcPlNm) {
        this.vcPlNm = vcPlNm;
    }

    @Column(name = "VC_FRM_NM", length = 100)
    public String getVcFrmNm() {
        return this.vcFrmNm;
    }
    
    public void setVcFrmNm(String vcFrmNm) {
        this.vcFrmNm = vcFrmNm;
    }

    @Column(name = "VC_EGN_NM", length = 100)
    public String getVcEgnNm() {
        return this.vcEgnNm;
    }
    
    public void setVcEgnNm(String vcEgnNm) {
        this.vcEgnNm = vcEgnNm;
    }

    @Column(name = "VC_CAR_CLR", length = 100)
    public String getVcCarClr() {
        return this.vcCarClr;
    }
    
    public void setVcCarClr(String vcCarClr) {
        this.vcCarClr = vcCarClr;
    }

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VC_INIT_REG_DT", length = 7)
    public Date getVcInitRegDt() {
        return this.vcInitRegDt;
    }
    
    public void setVcInitRegDt(Date vcInitRegDt) {
        this.vcInitRegDt = vcInitRegDt;
    }

    @Column(name = "VC_TOTL_TRIP", precision = 10, scale = 0)	
    public Long getVcTotlTrip() {
        return this.vcTotlTrip;
    }
    
    public void setVcTotlTrip(Long vcTotlTrip) {
        this.vcTotlTrip = vcTotlTrip;
    }

    @Column(name = "VC_USE_NTR", length = 1)
    public String getVcUseNtr() {
        return this.vcUseNtr;
    }
    
    public void setVcUseNtr(String vcUseNtr) {
        this.vcUseNtr = vcUseNtr;
    }

    @Column(name = "VC_LOS_FUEL_KND", length = 20)
    public String getVcLosFuelKnd() {
        return this.vcLosFuelKnd;
    }
    
    public void setVcLosFuelKnd(String vcLosFuelKnd) {
        this.vcLosFuelKnd = vcLosFuelKnd;
    }

    @Column(name = "VC_LOS_STRUCT", length = 1)
    public String getVcLosStruct() {
        return this.vcLosStruct;
    }
    
    public void setVcLosStruct(String vcLosStruct) {
        this.vcLosStruct = vcLosStruct;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VC_LOS_INIT_REG_DT", length = 7)
    public Date getVcLosInitRegDt() {
        return this.vcLosInitRegDt;
    }
    
    public void setVcLosInitRegDt(Date vcLosInitRegDt) {
        this.vcLosInitRegDt = vcLosInitRegDt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VC_LOS_ANN_PASS_DT", length = 7)
    public Date getVcLosAnnPassDt() {
        return this.vcLosAnnPassDt;
    }
    
    public void setVcLosAnnPassDt(Date vcLosAnnPassDt) {
        this.vcLosAnnPassDt = vcLosAnnPassDt;
    }

    @Column(name = "VC_LOS_PYMNT_STATN", length = 200)
    public String getVcLosPymntStatn() {
        return this.vcLosPymntStatn;
    }
    
    public void setVcLosPymntStatn(String vcLosPymntStatn) {
        this.vcLosPymntStatn = vcLosPymntStatn;
    }

    @Column(name = "VC_LOS_BUY_ADRS", length = 200)
    public String getVcLosBuyAdrs() {
        return this.vcLosBuyAdrs;
    }
    
    public void setVcLosBuyAdrs(String vcLosBuyAdrs) {
        this.vcLosBuyAdrs = vcLosBuyAdrs;
    }

    @Column(name = "VC_LOS_ARTCL_REF_DT_ST", length = 200)
    public String getVcLosArtclRefDtSt() {
        return this.vcLosArtclRefDtSt;
    }
    
    public void setVcLosArtclRefDtSt(String vcLosArtclRefDtSt) {
        this.vcLosArtclRefDtSt = vcLosArtclRefDtSt;
    }

    @Column(name = "JJC_SAP", length = 100)
    public String getJjcSap() {
        return this.jjcSap;
    }
    
    public void setJjcSap(String jjcSap) {
        this.jjcSap = jjcSap;
    }

    @Column(name = "JJC_SIZE", length = 100)
    public String getJjcSize() {
        return this.jjcSize;
    }
    
    public void setJjcSize(String jjcSize) {
        this.jjcSize = jjcSize;
    }

    @Column(name = "JJC_WGHT", length = 100)
    public String getJjcWght() {
        return this.jjcWght;
    }
    
    public void setJjcWght(String jjcWght) {
        this.jjcWght = jjcWght;
    }

    @Column(name = "JJC_CLR", length = 100)
    public String getJjcClr() {
        return this.jjcClr;
    }
    
    public void setJjcClr(String jjcClr) {
        this.jjcClr = jjcClr;
    }

    @Column(name = "JJC_GRD", length = 100)
    public String getJjcGrd() {
        return this.jjcGrd;
    }
    
    public void setJjcGrd(String jjcGrd) {
        this.jjcGrd = jjcGrd;
    }

    @Column(name = "JJC_FNNS", length = 100)
    public String getJjcFnns() {
        return this.jjcFnns;
    }
    
    public void setJjcFnns(String jjcFnns) {
        this.jjcFnns = jjcFnns;
    }

    @Column(name = "JJC_IS_QLT_ARTCL_CERT", length = 1)
    public String getJjcIsQltArtclCert() {
        return this.jjcIsQltArtclCert;
    }
    
    public void setJjcIsQltArtclCert(String jjcIsQltArtclCert) {
        this.jjcIsQltArtclCert = jjcIsQltArtclCert;
    }

    @Column(name = "JJC_CERT_RPRT_NO", length = 100)
    public String getJjcCertRprtNo() {
        return this.jjcCertRprtNo;
    }
    
    public void setJjcCertRprtNo(String jjcCertRprtNo) {
        this.jjcCertRprtNo = jjcCertRprtNo;
    }

    @Column(name = "MF_AREA", length = 100)
    public String getMfArea() {
        return this.mfArea;
    }
    
    public void setMfArea(String mfArea) {
        this.mfArea = mfArea;
    }

    @Column(name = "MF_BUY_ADD", length = 100)
    public String getMfBuyAdd() {
        return this.mfBuyAdd;
    }
    
    public void setMfBuyAdd(String mfBuyAdd) {
        this.mfBuyAdd = mfBuyAdd;
    }

    @Column(name = "BL_OWNRSHP_CERT_NO", length = 100)
    public String getBlOwnrshpCertNo() {
        return this.blOwnrshpCertNo;
    }
    
    public void setBlOwnrshpCertNo(String blOwnrshpCertNo) {
        this.blOwnrshpCertNo = blOwnrshpCertNo;
    }

    @Column(name = "BL_ADRS", length = 100)
    public String getBlAdrs() {
        return this.blAdrs;
    }
    
    public void setBlAdrs(String blAdrs) {
        this.blAdrs = blAdrs;
    }

	@Column(name = "PRC_APRISL_MTD", length = 50)
	public String getPrcAprislMtd() {
		return prcAprislMtd;
	}

	public void setPrcAprislMtd(String prcAprislMtd) {
		this.prcAprislMtd = prcAprislMtd;
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

    /* public Set<TCmsArtclsAccesors> getTCmsArtclsAccesorses() {
        return this.TCmsArtclsAccesorses;
    }
    
    public void setTCmsArtclsAccesorses(Set<TCmsArtclsAccesors> TCmsArtclsAccesorses) {
        this.TCmsArtclsAccesorses = TCmsArtclsAccesorses;
    }*/
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAprislArtclsInfo")
    public List<TCmsArtclsAccesors> getTCmsArtclsAccesorses() {
		return TCmsArtclsAccesorses;
	}
	public void setTCmsArtclsAccesorses(
			List<TCmsArtclsAccesors> tCmsArtclsAccesorses) {
		TCmsArtclsAccesorses = tCmsArtclsAccesorses;
	}
   
	@Column(name = "TMP_CMS_ID", precision = 28, scale = 0)
	public Long getTmpCmsId() {
		return tmpCmsId;
	}

	public void setTmpCmsId(Long tmpCmsId) {
		this.tmpCmsId = tmpCmsId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAprislArtclsInfo")
	public List<TPrfrncPrcLib> getTPrfrncPrcLib() {
		return TPrfrncPrcLib;
	}

	public void setTPrfrncPrcLib(List<TPrfrncPrcLib> tPrfrncPrcLib) {
		TPrfrncPrcLib = tPrfrncPrcLib;
	}


}