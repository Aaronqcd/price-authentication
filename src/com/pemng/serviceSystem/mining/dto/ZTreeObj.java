package com.pemng.serviceSystem.mining.dto;

/**
 * @version        1.0
 */
public class ZTreeObj {

	private String id;
	
	private String pId;
	
	private String name;
	
	private boolean open;

	public ZTreeObj(){}
	
	public ZTreeObj(String id, String pId, String name) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		if (pId.equals("-1")) {
			this.open = true;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ZTreeObj))
			return false;
		ZTreeObj castOther = (ZTreeObj) other;
		return (((this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))||this.getId().equals(castOther.getId()) );
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		return result;
	}
	
	
}
