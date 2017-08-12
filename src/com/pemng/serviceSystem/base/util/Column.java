package com.pemng.serviceSystem.base.util;

public class Column
{
    private String name ;        // 列名
    private boolean sortable ;      // 是否可排序
    private boolean searchable;    // 是否可搜索
    private String search;      // 搜索串
    private boolean escapeRegex;   // 是否正则
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public boolean isEscapeRegex() {
		return escapeRegex;
	}
	public void setEscapeRegex(boolean escapeRegex) {
		this.escapeRegex = escapeRegex;
	}
}