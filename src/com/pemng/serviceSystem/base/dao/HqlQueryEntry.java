package com.pemng.serviceSystem.base.dao;

import org.apache.commons.lang.StringUtils;
/**
 * HQL查询列
 * @author shaojie
 *
 */
public final class HqlQueryEntry {

	private String fieldName;

	private String aliasName;

	protected HqlQueryEntry(String fieldName, String aliasName) {
		super();
		this.fieldName = fieldName;
		this.aliasName = aliasName;
	}

	public static HqlQueryEntry create(String fieldNames[], String aliasName) {
		return new HqlQueryEntry(StringUtils.join(fieldNames, "."), aliasName);
	}

	public static HqlQueryEntry create(String fieldName, String aliasName) {
		return new HqlQueryEntry(fieldName, aliasName);
	}

	/**
	 * use the last part of the fieldName as the aliasName
	 * 
	 * @param fieldName
	 * @return
	 */
	public static HqlQueryEntry create(String fieldName) {

		String[] names = fieldName.split("\\.");
		String alias = names[names.length - 1];

		return new HqlQueryEntry(fieldName, alias);
	}

	/**
	 * use the last part of the fieldName as the aliasName
	 * 
	 * @param fieldName
	 * @return
	 */
	public static HqlQueryEntry create(String[] fieldNames) {

		String alias = fieldNames[fieldNames.length - 1];

		return HqlQueryEntry.create(fieldNames, alias);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public static String generateQueryMap(HqlQueryEntry[] hqlQueryEntries) {

		return "new Map(" + StringUtils.join(hqlQueryEntries, ", ") + ")";
	}

	@Override
	public String toString() {

		return this.fieldName + " as " + this.aliasName;
	}

	public static void main(String[] args) {
		System.out.println(HqlQueryEntry.create("test1.test2.ttt"));
		System.out.println(generateQueryMap(new HqlQueryEntry[] {
				HqlQueryEntry.create("test1.test2"),
				HqlQueryEntry.create("test1", "test1a") }));
	}

}
