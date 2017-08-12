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
 * TApprovalInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_APPROVAL_INFO")
public class TApprovalInfo extends BasePO<TApprovalInfo> implements java.io.Serializable {

    // Fields    

	 private static final long serialVersionUID = 2214351719802736074L;
	 private Long id;
     private TCommission TCommission;
     private TUserInfo TUserInfo;
     private String approvalTp;
     private String remark;
     private Long crtUsrId;
     private Date crtTime;
     private Long updUsrId;
     private Date updTime;
     private String markForDel;

    // Constructors

    /** default constructor */
    public TApprovalInfo() {
    }

    
    /** full constructor */
    public TApprovalInfo(TCommission TCommission, TUserInfo TUserInfo, String approvalTp, String remark, Long crtUsrId, Date crtTime, Long updUsrId, Date updTime, String markForDel) {
        this.TCommission = TCommission;
        this.TUserInfo = TUserInfo;
        this.approvalTp = approvalTp;
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
    @JoinColumn(name = "CMS_ID")
    public TCommission getTCommission() {
        return this.TCommission;
    }
    
    public void setTCommission(TCommission TCommission) {
        this.TCommission = TCommission;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPRD_USR_ID")
    public TUserInfo getTUserInfo() {
        return this.TUserInfo;
    }
    
    public void setTUserInfo(TUserInfo TUserInfo) {
        this.TUserInfo = TUserInfo;
    }

    @Column(name = "APPROVAL_TP", length = 1)
    public String getApprovalTp() {
        return this.approvalTp;
    }
    
    public void setApprovalTp(String approvalTp) {
        this.approvalTp = approvalTp;
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