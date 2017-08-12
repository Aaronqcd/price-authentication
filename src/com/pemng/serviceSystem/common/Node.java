package com.pemng.serviceSystem.common;

import java.util.List;

/**
 * Created on May 18, 2012
 * <p>Description: [描述该类概要功能介绍]</p>
 * @version        1.0
 */
public class Node {
	
	
	private Long id;
	private String title;
	private String url = "#";
	private List<Node> children;
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
	
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
