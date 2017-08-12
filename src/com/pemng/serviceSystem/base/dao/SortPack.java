package com.pemng.serviceSystem.base.dao;

/**
 * 排序信息包装
 * 
 * @author shaojie
 * 
 */
public class SortPack {

	private String fieldNames;

	private boolean ascending;

	protected SortPack(String fieldNames, boolean ascending) {

		this.fieldNames = fieldNames;
		this.ascending = ascending;

	}

	public static SortPack create(String fieldNames, boolean ascending) {

		if (fieldNames == null)
			return null;
		else
			return new SortPack(fieldNames, ascending);
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}
}
