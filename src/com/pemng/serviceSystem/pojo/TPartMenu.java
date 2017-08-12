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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_PART_MENU")
public class TPartMenu extends com.pemng.serviceSystem.base.pojo.BasePO<TPartMenu> implements
		java.io.Serializable {

	private static final long serialVersionUID = -5306679113826153740L;
	// Fields

	private Long id;
	private TPartMenu TPartMenu;
	private TFnctn TFnctn;
	private String partName;
	private String partLevel;
	private String partUrl;
	private String partImg;
	private String remark;
	private Date crtTime;
	private Long crtUsrId;
	private Date updTime;
	private Long updUsrId;
	private Set<TPartMenu> TPartMenus = new HashSet<TPartMenu>(0);

	// Constructors

	/** default constructor */
	public TPartMenu() {
	}

	/** full constructor */
	public TPartMenu(TPartMenu TPartMenu, TFnctn TFnctn, String partName,
			String partLevel, String partUrl, String partImg, String remark,
			Date crtTime, Long crtUsrId, Date updTime,
			Long updUsrId, Set<TPartMenu> TPartMenus) {
		this.TPartMenu = TPartMenu;
		this.TFnctn = TFnctn;
		this.partName = partName;
		this.partLevel = partLevel;
		this.partUrl = partUrl;
		this.partImg = partImg;
		this.remark = remark;
		this.crtTime = crtTime;
		this.crtUsrId = crtUsrId;
		this.updTime = updTime;
		this.updUsrId = updUsrId;
		this.TPartMenus = TPartMenus;
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
	@JoinColumn(name = "PARENT_ID")
	public TPartMenu getTPartMenu() {
		return this.TPartMenu;
	}

	public void setTPartMenu(TPartMenu TPartMenu) {
		this.TPartMenu = TPartMenu;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FNCTN_ID")
	public TFnctn getTFnctn() {
		return this.TFnctn;
	}

	public void setTFnctn(TFnctn TFnctn) {
		this.TFnctn = TFnctn;
	}

	@Column(name = "PART_NAME", length = 100)
	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	@Column(name = "PART_LEVEL", length = 1)
	public String getPartLevel() {
		return this.partLevel;
	}

	public void setPartLevel(String partLevel) {
		this.partLevel = partLevel;
	}

	@Column(name = "PART_URL", length = 200)
	public String getPartUrl() {
		return this.partUrl;
	}

	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	@Column(name = "PART_IMG", length = 50)
	public String getPartImg() {
		return this.partImg;
	}

	public void setPartImg(String partImg) {
		this.partImg = partImg;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRT_TIME", length = 7)
	public Date getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	@Column(name = "CRT_USR_ID", precision = 10, scale = 0)
	public Long getCrtUsrId() {
		return this.crtUsrId;
	}

	public void setCrtUsrId(Long crtUsrId) {
		this.crtUsrId = crtUsrId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPD_TIME", length = 7)
	public Date getUpdTime() {
		return this.updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	@Column(name = "UPD_USR_ID", precision = 10, scale = 0)
	public Long getUpdUsrId() {
		return this.updUsrId;
	}

	public void setUpdUsrId(Long updUsrId) {
		this.updUsrId = updUsrId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPartMenu")
	public Set<TPartMenu> getTPartMenus() {
		return this.TPartMenus;
	}

	public void setTPartMenus(Set<TPartMenu> TPartMenus) {
		this.TPartMenus = TPartMenus;
	}

}