package com.pemng.serviceSystem.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuInfo implements Serializable,Cloneable {
	
	private String title;
	private String url;
	private String code;
	private List<MenuInfo> children = new ArrayList<MenuInfo>(0);
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<MenuInfo> getChildren() {
		return children;
	}
	public void setChildren(List<MenuInfo> children) {
		this.children = children;
	}

}
