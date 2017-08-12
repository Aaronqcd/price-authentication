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
 * TAttachment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ATTACHMENT")
public class TAttachment extends BasePO<TAttachment> implements
		java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7424387569065244909L;
	private Long id;
	private TCommission TCommission;
	private String atTp;
	private String fileTp;
	private String fileNm;
	private String saveFileNm;
	private Long fileSize;
	private TUserInfo TUserInfo;
	private Long downloads;
	private String remark;
	private Long tmpCmsId;
	private Long crtUsrId;
	private Date crtTime;
	private Long updUsrId;
	private Date updTime;
	private String markForDel;

	// Constructors

	/** default constructor */
	public TAttachment() {
	}

	/** full constructor */
	public TAttachment(TCommission TCommission, String atTp, String fileTp,
			String fileNm, String saveFileNm, Long fileSize, TUserInfo TUserInfo,
			Long downloads, String remark, Long crtUsrId, Date crtTime,
			Long updUsrId, Date updTime, String markForDel) {
		this.TCommission = TCommission;
		this.atTp = atTp;
		this.fileTp = fileTp;
		this.fileNm = fileNm;
		this.saveFileNm = saveFileNm;
		this.fileSize = fileSize;
		this.TUserInfo = TUserInfo;
		this.downloads = downloads;
		this.remark = remark;
		this.crtUsrId = crtUsrId;
		this.crtTime = crtTime;
		this.updUsrId = updUsrId;
		this.updTime = updTime;
		this.markForDel = markForDel;
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
	@JoinColumn(name = "CMS_ID")
	public TCommission getTCommission() {
		return this.TCommission;
	}

	public void setTCommission(TCommission TCommission) {
		this.TCommission = TCommission;
	}

	@Column(name = "AT_TP", length = 1)
	public String getAtTp() {
		return this.atTp;
	}

	public void setAtTp(String atTp) {
		this.atTp = atTp;
	}

	@Column(name = "FILE_TP", length = 20)	
	public String getFileTp() {
		return this.fileTp;
	}

	public void setFileTp(String fileTp) {
		this.fileTp = fileTp;
	}

	@Column(name = "FILE_NM", length = 50)	
	public String getFileNm() {
		return this.fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	@Column(name = "SAVE_FILE_NM", length = 50)	
	public String getSaveFileNm() {
		return this.saveFileNm;
	}

	public void setSaveFileNm(String saveFileNm) {
		this.saveFileNm = saveFileNm;
	}

	@Column(name = "FILE_SIZE", precision = 10, scale = 0)
	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPLD_USR_ID")
	public TUserInfo getTUserInfo() {
		return this.TUserInfo;
	}

	public void setTUserInfo(TUserInfo TUserInfo) {
		this.TUserInfo = TUserInfo;
	}

	@Column(name = "DOWNLOADS", precision = 10, scale = 0)
	public Long getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Long downloads) {
		this.downloads = downloads;
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

	@Column(name = "TMP_CMS_ID", precision = 28, scale = 0)
	public Long getTmpCmsId() {
		return tmpCmsId;
	}

	public void setTmpCmsId(Long tmpCmsId) {
		this.tmpCmsId = tmpCmsId;
	}


}