package com.pemng.serviceSystem.base.dao;

/**
 * 分页信息类
 * 
 * @author shaojie
 * 
 */
public class PagePack {

	private int startIndex;

	private int pageSize;

	protected PagePack(int startIndex, int pageSize) {

		this.startIndex = startIndex;
		this.pageSize = pageSize;

	}

	public static PagePack create(int startIndex, int pageSize) {

		if (pageSize == 0 && startIndex == 0)
			return createGetAll();
		else
			return new PagePack(startIndex, pageSize);
	}

	public static PagePack createGetAll(){
		return new PagePack(0,0);
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

}
