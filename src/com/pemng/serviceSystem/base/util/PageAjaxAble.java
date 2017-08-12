package com.pemng.serviceSystem.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.pemng.serviceSystem.base.dao.ResultPack;

public abstract class PageAjaxAble {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7428411633240459193L;

	/**
	 * 2D数组，封装结果返回表格中的实际数据
	 */
	private String[][] aaData;
	/**
	 * 给前台的数据
	 */
	private DataTableReturnObject tableReturnObject = null;

	private DataTableRequest request = null;

	public DataTableReturnObject execute() throws Exception {

		//获取request,自己从request中解析排序列参数
		ActionContext ct = ActionContext.getContext();
		HttpServletRequest req = (HttpServletRequest) ct
				.get(ServletActionContext.HTTP_REQUEST);
		request = new DataTableRequest(req);

		List page = null;
		int totle = 0;
		ResultPack resultPack = this.loadData(request2Map());
		page = resultPack.getList();
		totle = resultPack.getTotalCount();
		aaData = this.packageResult2Array(page);
		tableReturnObject = new DataTableReturnObject(totle, totle, request
				.getSEcho(), aaData);
		return tableReturnObject;

	}

	public String[][] getaaData() {
		return aaData;
	}

	public void setaaData(String[][] aaData) {
		this.aaData = aaData;
	}

	public DataTableReturnObject getTableReturnObject() {
		return tableReturnObject;
	}

	public void setTableReturnObject(DataTableReturnObject tableReturnObject) {
		this.tableReturnObject = tableReturnObject;
	}
	
	
	private Map<String,Object> request2Map(){
		Map<String, Object> params = new HashMap<String, Object>();
		final int iDisplayStart = request.getIDisplayStart();
		final int iDisplayLength = request.getIDisplayLength();
		int cup = (int) (iDisplayStart / iDisplayLength) + 1;
		//当前页
		params.put(ResultPack.CURPAGE, Integer.valueOf(cup));
		//每页记录数
		params.put(ResultPack.SIZE, Integer.valueOf(iDisplayLength));
		//该页索引
		params.put(ResultPack.START, Integer.valueOf(iDisplayStart));
		//排序语句
		params.put(ResultPack.ORDER_SENTENCE, request.generateOrderBy());
		//排序Orders
		params.put(ResultPack.ORDERS, request.generateOrders());
		//排序SortRule
		params.put(ResultPack.SORT_RULES, request.generateSortRuls());
		
		
		return params;
	}

	//可覆盖,自己封装
	protected abstract String[][] packageResult2Array(List page);

	protected abstract ResultPack loadData(Map<String, Object> params);

	public DataTableRequest getRequest() {
		return request;
	}

}
