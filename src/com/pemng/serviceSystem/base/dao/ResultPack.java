package com.pemng.serviceSystem.base.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息结果返回包装
 * 
 * @author shaojie
 * 
 */
public class ResultPack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3227740000839022511L;

	private List list;

	private int totalCount;

	public static final String SIZE = "size";

	public static final String CURPAGE = "curPage";

	public static final String START = "start";

	public static final String ORDER_SENTENCE = "ordersentence";

	public static final String ORDERS = "orders";

	public static final String SORT_RULES = "sortRules";

	public static final ResultPack NULL = new ResultPack(new ArrayList(),0);

	/**
	 * 
	 * @param pagedList
	 * @param totalCount
	 *            -1 if no count
	 */
	public ResultPack(List pagedList, int totalCount) {

		this.list = pagedList;
		this.totalCount = totalCount;
	}

	public ResultPack() {

	}

	public static ResultPack createEmptyResultPack() {

		return new ResultPack(new ArrayList(), 0);

	}

	public List getList() {
		return list;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
