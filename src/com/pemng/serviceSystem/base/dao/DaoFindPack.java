package com.pemng.serviceSystem.base.dao;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

/**
 * 常用查询方法集合，与Criteria查询合用
 * @author shaojie
 *
 * @param <T>
 */
public class DaoFindPack<T> {

	private T example = null;

	private Criterion criterion = null;

	private Map exampleMap = null;

	private Order[] orders = null;

	private boolean likeSearch = false;

	private Projection projection = null;

	private FetchPack[] fetchPacks = null;

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public DaoFindPack() {

	}

	public DaoFindPack(T example, Criterion criterion, Map exampleMap,
			Order[] orders, boolean likeSearch) {
		super();
		this.example = example;
		this.criterion = criterion;
		this.exampleMap = exampleMap;
		this.orders = orders;
		this.likeSearch = likeSearch;
	}

	public T getExample() {
		return example;
	}

	public void setExample(T example) {
		this.example = example;
	}

	public Map getExampleMap() {
		return exampleMap;
	}

	public void setExampleMap(Map exampleMap) {
		this.exampleMap = exampleMap;
	}

	public boolean isLikeSearch() {
		return likeSearch;
	}

	public void setLikeSearch(boolean likeSearch) {
		this.likeSearch = likeSearch;
	}

	public Order[] getOrders() {
		return orders;
	}

	public void setOrders(Order[] order) {
		this.orders = order;
	}

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	public FetchPack[] getFetchPacks() {
		return fetchPacks;
	}

	public void setFetchPacks(FetchPack[] fetchPacks) {
		this.fetchPacks = fetchPacks;
	}

}
