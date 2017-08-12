package com.pemng.serviceSystem.common;


import java.io.Serializable;

public class ZtreeObj implements Serializable{

	private String name;
	private Long id;
	private Long pId;
	private boolean isParent;
	private boolean open;
	
	public ZtreeObj(){}
	
	public ZtreeObj(Long id,String name) {
		this.id = id;
		this.pId = 0l;
		this.name = name;
	}
	
	public ZtreeObj(Long id, Long pId, String name) {
		this.id = id;
		if (null == pId) {
			this.pId = 0l;
		}else{
			this.pId = pId;
		}
		this.name = name;
		this.open = true;
	}
	
	public ZtreeObj(Long id, Long pId, String name, boolean open, boolean isParent) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.isParent = isParent;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	
	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
}

