package com.pemng.serviceSystem.base.dao;

import org.hibernate.criterion.Order;

/**
 * 查询结果获取参数类
 * @author shaojie
 *
 */
public class DaoOptionPack {

	private int offsetIndex = 0;

	private int pageSize = 0;

	private Order[] orders = null;

	private int getMode = GET_ALL;

	public final static int GET_ALL = 0;

	public final static int GET_LIST = 1;

	public final static int GET_COUNT = 2;

	protected DaoOptionPack(int offsetIndex, int pageSize, Order[] orders,
			int getMode) {
		super();
		this.offsetIndex = offsetIndex;
		this.pageSize = pageSize;
		this.orders = orders;
		this.getMode = getMode;
	}

	/**
	 * 
	 * @param pagePack
	 * @param sortPack
	 * @param getModel
	 *            public static int GET_ALL = 0; public static int GET_LIST = 1;
	 *            public static int GET_COUNT = 2;
	 */
	public static DaoOptionPack create(int offsetIndex, int pageSize,
			Order[] orders, int getMode) {

		return new DaoOptionPack(offsetIndex, pageSize, orders, getMode);
	}

	public static DaoOptionPack create(int offsetIndex, int pageSize,
			Order[] orders) {

		return new DaoOptionPack(offsetIndex, pageSize, orders, GET_ALL);
	}

	public static DaoOptionPack createGetOnlyCount() {

		return new DaoOptionPack(0, 0, null, GET_COUNT);
	}

	public static DaoOptionPack createGetOnlyUnpagedList() {

		return new DaoOptionPack(0, 0, null, GET_LIST);

	}

	public int getGetMode() {
		return getMode;
	}

	public void setGetMode(int getMode) {
		this.getMode = getMode;
	}

	public int getOffsetIndex() {
		return offsetIndex;
	}

	public void setOffsetIndex(int offsetIndex) {
		this.offsetIndex = offsetIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Order[] getOrders() {
		return orders;
	}

	public void setOrders(Order[] orders) {
		this.orders = orders;
	}

}
