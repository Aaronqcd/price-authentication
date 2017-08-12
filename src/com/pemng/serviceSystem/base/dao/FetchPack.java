package com.pemng.serviceSystem.base.dao;

import org.hibernate.FetchMode;
/**
 * 查询结果获取方法设置类
 * @author shaojie
 *
 */
public final class FetchPack {
	private String property;
	private FetchMode fetchMode;

	public FetchPack(String property, FetchMode fetchMode) {
		super();
		this.property = property;
		this.fetchMode = fetchMode;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public FetchMode getFetchMode() {
		return fetchMode;
	}

	public void setFetchMode(FetchMode fetchMode) {
		this.fetchMode = fetchMode;
	}

}
